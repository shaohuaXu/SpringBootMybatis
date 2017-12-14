package com.xu.demo.configuration;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * 配置类
 *
 * @author xushaohua on 2017-12-14.
 */
@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	/**
	 * 配置文件
	 * 需要带上目录，如果不带目录则不能读取到文件，提示it does not exist
	 */
	private static final String FASTDFS_CONFIG = "conf/fdfs_client.conf";

	@Bean
	public StorageClient1 initStorageClient() {
		StorageClient1 storageClient = null;
		try {
			// 读取到fsdf的配置文件
			Resource resource = new ClassPathResource(FASTDFS_CONFIG);
			File file = resource.getFile();
			// 初始化配置文件
			ClientGlobal.init(file.getAbsolutePath());
			TrackerClient trackerClient = new TrackerClient(ClientGlobal.g_tracker_group);
			TrackerServer trackerServer = trackerClient.getConnection();
			if (trackerServer == null) {
				throw new IllegalStateException("getConnection return null");
			}
			StorageServer storageServer = trackerClient.getStoreStorage(trackerServer);
			if (storageServer == null) {
				throw new IllegalStateException("getStoreStorage return null");
			}
			storageClient = new StorageClient1(trackerServer, storageServer);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return storageClient;
	}
}
