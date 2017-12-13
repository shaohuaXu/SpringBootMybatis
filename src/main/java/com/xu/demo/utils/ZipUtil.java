package com.xu.demo.utils;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

/**
 * 压缩工具类，支持中文编码，递归文件夹压缩 采用gb2312编码， 文件路径采用"C:/aa/aaa.txt"
 */
public class ZipUtil {
	private final static String ENCODING = "gb2312";
	private final static String FILESEPARATE = "/";

	/**
	 * 压缩文件
	 *
	 * @param source      准备压缩的文件路径及名称
	 * @param zipFileName 压缩后的文件路径及名称
	 */
	public static void toZip(String source, String zipFileName) throws IOException {
		File destFile = new File(zipFileName);
		File srcdir = new File(source);
		if (!srcdir.exists()) {
			throw new RuntimeException(source + "不存在！");
		}
		Project prj = new Project();
		Zip zip = new Zip();
		zip.setProject(prj);
		zip.setDestFile(destFile);
		FileSet fileSet = new FileSet();
		fileSet.setProject(prj);
		fileSet.setDir(srcdir);
		// 设置包括哪些文件或文件夹 eg:zip.setIncludes("*.java")
		// fileSet.setIncludes("**/*.java");
		// 设置排除文件或文件夹
		// fileSet.setExcludes(...);
		zip.addFileset(fileSet);
		zip.execute();
	}

	/**
	 * zip 压缩单个文件。 除非有特殊需要，否则请调用ZIP方法来压缩文件！
	 *
	 * @param sourceFileName 要压缩的原文件
	 * @param zos            压缩到的zip文件
	 * @param tager          压缩到的目标位置
	 * @throws IOException 抛出文件异常
	 */
	public static void toZipFile(String sourceFileName, ZipOutputStream zos, String tager) throws IOException {
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);
		// 读取要压缩文件并将其添加到压缩文件中
		FileInputStream fis = new FileInputStream(new File(sourceFileName));
		byte[] bf = new byte[2048];
		int location = 0;
		while ((location = fis.read(bf)) != -1) {
			zos.write(bf, 0, location);
		}
		fis.close();
	}

	/**
	 * 压缩目录。 除非有特殊需要，否则请调用ZIP方法来压缩文件！
	 *
	 * @param sourceDir 需要压缩的目录位置
	 * @param zos       压缩到的zip文件
	 * @param tager     压缩到的目标位置
	 * @throws IOException 压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
	 */
	public static void toZipDir(String sourceDir, ZipOutputStream zos, String tager) throws IOException {
		ZipEntry ze = new ZipEntry(tager);
		zos.putNextEntry(ze);
		// 提取要压缩的文件夹中的所有文件
		File f = new File(sourceDir);
		File[] flist = f.listFiles();
		if (flist != null) {
			// 如果该文件夹下有文件则提取所有的文件进行压缩
			for (File fsub : flist) {
				if (fsub.isDirectory()) {
					// 如果是目录则进行目录压缩
					toZipDir(fsub.getPath(), zos, tager + fsub.getName() + FILESEPARATE);
				} else {
					// 如果是文件，则进行文件压缩
					toZipFile(fsub.getPath(), zos, tager + fsub.getName());
				}
			}
		}
	}

	/**
	 * 解压缩zip文件
	 *
	 * @param sourceFileName 要解压缩的zip文件
	 * @param desDir         解压缩到的目录
	 * @throws IOException 压缩文件的过程中可能会抛出IO异常，请自行处理该异常。
	 */
	public static void unZip(String sourceFileName, String desDir) throws IOException {
		// 创建压缩文件对象
		ZipFile zf = new ZipFile(new File(sourceFileName), ENCODING);

		// 获取压缩文件中的文件枚举
		Enumeration<ZipEntry> en = zf.getEntries();
		int length = 0;
		byte[] b = new byte[2048];

		// 提取压缩文件夹中的所有压缩实例对象
		while (en.hasMoreElements()) {
			ZipEntry ze = en.nextElement();
			// System.out.println("压缩文件夹中的内容："+ze.getName());
			// System.out.println("是否是文件夹："+ze.isDirectory());
			// 创建解压缩后的文件实例对象
			File f = new File(desDir + ze.getName());
			// System.out.println("解压后的内容："+f.getPath());
			// System.out.println("是否是文件夹："+f.isDirectory());
			// 如果当前压缩文件中的实例对象是文件夹就在解压缩后的文件夹中创建该文件夹
			if (ze.isDirectory()) {
				f.mkdirs();
			} else {
				// 如果当前解压缩文件的父级文件夹没有创建的话，则创建好父级文件夹
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				// 将当前文件的内容写入解压后的文件夹中。
				OutputStream outputStream = new FileOutputStream(f);
				InputStream inputStream = zf.getInputStream(ze);
				while ((length = inputStream.read(b)) > 0) {
					outputStream.write(b, 0, length);
				}
				inputStream.close();
				outputStream.close();
			}
		}
		zf.close();
	}

	/**
	 * 测试
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			long starTime = 0;
			long endTime = 0;

			System.out.println("开始压缩...");
			starTime = System.currentTimeMillis();
			toZip("E:/测试压缩", "E:/测试压缩.zip");
			endTime = System.currentTimeMillis();
			System.out.println("压缩完毕！花费时间: " + (endTime - starTime) + " 毫秒！");

			// System.out.println("开始解压...");
			// starTime = System.currentTimeMillis();
			// UnZIP("d:/系统备份.zip", "d:/测试目录/");
			// endTime = System.currentTimeMillis();
			// System.out.println("解压完毕！花费时间: " + (endTime - starTime) + " 毫秒！");
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
}
