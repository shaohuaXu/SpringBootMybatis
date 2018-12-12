//package com.xu.demo.ctrl;
//
//import com.xu.demo.utils.FastDFSClientUtil;
//import org.csource.fastdfs.FileInfo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//
///**
// * 描述
// *
// * @author xushaohua on 2017-12-14.
// */
//@Controller
//@RequestMapping("/api/file")
//public class FileCtrl {
//
//	@Autowired
//	FastDFSClientUtil fastDFSClientUtil;
//
//	/*
//	 * 测试过一下几种形式的写法，都能正常访问（前提是只有一个参数，如果有其它参数如：method，则需要写上value）
//	 * @RequestMapping("download")
//	 * @RequestMapping("/download")
//	 * @RequestMapping(value = "download")
//	 * @RequestMapping(value = "/download")
//	 */
//	// @RequestMapping("/download")
//	@RequestMapping(value = "download", method = RequestMethod.GET)
//	public void downloadFile() {
//		FileInfo fileInfo = fastDFSClientUtil.getFileInfo("group1/M00/00/9D/CgoYNlmmfwWASNSxAAAEOPvh4xI380.ftl");
//		System.out.println(fileInfo.getFileSize());
//	}
//}
