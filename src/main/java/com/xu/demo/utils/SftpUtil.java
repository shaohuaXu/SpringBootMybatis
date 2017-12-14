package com.xu.demo.utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;
import java.util.Vector;

/**
 * 利用jsch.jar针对sftp的上传和下载
 *
 * @author xushaohua
 */
public class SftpUtil {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(SftpUtil.class);

	/**
	 * 利用JSch包实现SFTP下载文件
	 * (这里是将serverPath下的指定扩展名的文件，全部下载到localPath下)
	 *
	 * @param ip             主机IP
	 * @param user           主机登陆用户名
	 * @param psw            主机登陆密码
	 * @param port           主机ssh2登陆端口，如果取默认值，传<=0的值，如-1
	 * @param localPath      下载的本地目录的路径
	 * @param serverPath     服务器目录
	 * @param fileExetension 文件扩展名,格式如：'.txt'
	 * @param isDelete       是否删除该路径下扩展名为fileExetension的远程文件
	 * @throws Exception
	 */
	public static void sshSftpDownload(String ip, String user, String psw, int port, String serverPath, String localPath, String fileExtension, boolean isDelete) throws Exception {
		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}
		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			throw new Exception("session is null");
		}
		// 设置登陆主机的密码
		session.setPassword(psw);
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		// session.setConfig("StrictHostKeyChecking", "no");
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		session.setConfig(sshConfig);
		// 设置登陆超时时间ms
		// session.connect(640);
		session.connect();
		LOGGER.info("Session connected.");
		LOGGER.info("Opening Channel.");
		try {
			// 创建sftp通信通道
			channel = session.openChannel("sftp");
			channel.connect();
			// channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			LOGGER.info("Connected to " + ip + ".");
			// 进入服务器指定的文件夹
			sftp.cd(serverPath);
			/**
			 * 列出服务器指定的文件列表(可以加参数，指明要下载的文件类型)
			 * 说明：如果fileExtension不为空，则下载指定扩展名的文件集；
			 * 如果fileExtension为"",则下载该目录下所有类型的文件,如果是文件夹的话，会报错,如果您路径下有以.连接的文件夹，请注意，这是不可以的，程序会在过滤到该文件夹时中断
			 */
			Vector<?> v = null;
			if (fileExtension != null && !"".equals(fileExtension)) {
				v = sftp.ls("*" + fileExtension);
			} else {
				try {
					v = sftp.ls("*.*");//ls -al | grep \"^-\"只显示文件---// ls -al | grep "^d"只显示目录包括.和..
				} catch (Exception e) {
					LOGGER.info("您操作的是一个文件夹!");
				}
			}

			for (int i = 0; i < v.size(); i++) {
				// LOGGER.info("fileInfos: "+v.get(i));
				String[] fileInfos = v.get(i).toString().replaceAll("\t", " ").split(" ");
				String fileName = fileInfos[fileInfos.length - 1];
				LOGGER.info("fileName is: " + fileName);
				// 以下代码实现从服务器下载一个文件到 本地
				InputStream instream = sftp.get(fileName);
				OutputStream outstream = new FileOutputStream(new File(localPath + "/" + fileName));
				byte b[] = new byte[1024];
				int n;
				while ((n = instream.read(b)) != -1) {
					outstream.write(b, 0, n);
				}
				outstream.flush();
				outstream.close();
				instream.close();
				LOGGER.info("文件[" + fileName + "]下载成功!---->>>>下载到目录[" + localPath + "]中.");
				// 下载成功后，删除文件
				if (isDelete) {
					deleteOneFile(serverPath, fileName, sftp);
					LOGGER.info("文件[" + fileName + "]删除成功!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}

	/**
	 * 利用JSch包实现SFTP上传文件
	 *
	 * @param ip            主机IP
	 * @param user          主机登陆用户名
	 * @param psw           主机登陆密码
	 * @param port          主机ssh2登陆端口，如果取默认值，传<=0的值，如-1
	 * @param localPath     本地目录
	 * @param serverPath    服务器目录
	 * @param fileExtension 上传文件的扩展名 格式如：'.txt'
	 */
	public static void sshSftpUpload(String ip, String user, String psw, int port, String localPath, String serverPath, String fileExtension) throws Exception {
		Session session = null;
		Channel channel = null;
		JSch jsch = new JSch();
		if (port <= 0) {
			// 连接服务器，采用默认端口
			session = jsch.getSession(user, ip);
		} else {
			// 采用指定的端口连接服务器
			session = jsch.getSession(user, ip, port);
		}
		// 如果服务器连接不上，则抛出异常
		if (session == null) {
			LOGGER.info("session is null，服务器连接失败");
			throw new Exception("session is null，服务器连接失败");
		} else {
			LOGGER.info("Connected Success, ip is [" + ip + "]");
		}
		// 设置登陆主机的密码
		session.setPassword(psw);// 设置密码
		// 设置第一次登陆的时候提示，可选值：(ask | yes | no)
		session.setConfig("StrictHostKeyChecking", "no");
		// 设置登陆超时时间ms
		session.connect(960);
		try {
			// 创建sftp通信通道
			channel = (Channel) session.openChannel("sftp");
			channel.connect(1000);
			ChannelSftp sftp = (ChannelSftp) channel;
			// 进入服务器指定的文件夹
			sftp.cd(serverPath);
			// 列出服务器指定的文件列表
			// Vector v = sftp.ls("*.sh");
			// for (int i = 0; i < v.size(); i++) {
			//		System.out.println(v.get(i));
			// }
			// 以下代码实现从本地上传一个文件到服务器，如果要实现下载，对换一下流就可以了
			String[] files = getLocalFileNameArray(localPath);//获取所有文件名数组
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i];
				if (fileExtension != null && !"".equals(fileExtension)) {// 如果扩展名不为空，则上传该路径下指定扩展名的文件
					if (fileName.endsWith(fileExtension)) {
						OutputStream outstream = sftp.put(fileName);// 要上传到服务器的文件，可以另外设个名字，也可以用本地的名称
						InputStream instream = new FileInputStream(new File(localPath + "/" + fileName));// 读取本地文件
						byte b[] = new byte[1024];
						int n;
						while ((n = instream.read(b)) != -1) {
							outstream.write(b, 0, n);
						}
						outstream.flush();
						outstream.close();
						instream.close();
						LOGGER.info("文件[" + localPath + "/" + fileName + "]上传成功!---->>>>上传到目录[" + serverPath + "]中.");
					} else {
						LOGGER.info("警告：文件[" + fileName + "]不是指定类型[" + fileExtension + "]的文件");
					}
				} else {// 如果扩展名为空，则上传该路径下的所有文件
					OutputStream outstream = sftp.put(fileName);// 要上传到服务器的文件，可以另外设个名字，也可以用本地的名称
					InputStream instream = new FileInputStream(new File(localPath + "/" + fileName));// 本地文件
					byte b[] = new byte[1024];
					int n;
					while ((n = instream.read(b)) != -1) {
						outstream.write(b, 0, n);
					}
					outstream.flush();
					outstream.close();
					instream.close();
					LOGGER.info("文件[" + fileName + "]上传成功!---->>>>上传到目录[" + serverPath + "]中.");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.disconnect();
			channel.disconnect();
		}
	}

	/**
	 * 读取指定路径下的所有文件
	 *
	 * @param localPath 指定路径
	 * @return String[] 文件名数组
	 */
	public static String[] getLocalFileNameArray(String localPath) {
		File diskFile = new File(localPath);
		String[] fileNameList = diskFile.list();
		if (fileNameList != null) {
			// 按照文件名倒序排序
			Arrays.sort(fileNameList, Collections.reverseOrder());
		}
		return fileNameList;
	}

	/**
	 * 删除指定目录的,指定扩展名的远程文件
	 *
	 * @param directory     要删除文件的目录
	 * @param sftp          ChannelSftp实体
	 * @param fileExtension 文件扩展名（删除远程文件，扩展名不能为空）
	 */
	public void deleteAll(String directory, ChannelSftp sftp, String fileExtension) {
		try {
			sftp.cd(directory);
			Vector<?> v = null;
			if (fileExtension != null && "".equals(fileExtension)) {
				v = sftp.ls("*" + fileExtension);
			} else {
				// v=sftp.ls("");// 此处有风险
				LOGGER.warn("FileExtension is not null! Please Check");
			}
			for (int i = 0; i < v.size(); i++) {
				String[] fileInfos = v.get(i).toString().replaceAll("\t", " ").split(" ");
				String fileName = fileInfos[fileInfos.length - 1];
				sftp.rm(fileName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除单个文件
	 *
	 * @param directory  要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 * @throws Exception
	 */
	public static void deleteOneFile(String directory, String deleteFile, ChannelSftp sftp) throws Exception {
		sftp.cd(directory);
		sftp.rm(deleteFile);
	}
}

