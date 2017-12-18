package com.xu.demo.ctrl;


import com.xu.demo.utils.DownLoadImage;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.util.List;

/**
 * 扩展PipeLine
 *
 * @author xushaohua on 2017-12-18.
 */
public class DownloadPipeLine implements Pipeline {

	int downLoadPosition = 1;

	@Override
	public void process(ResultItems resultItems, Task task) {
		System.out.println("图片抓取页为：" + resultItems.getRequest().getUrl());
		List<String> urlList = resultItems.get("picName");
		System.out.println("抓取图片序号为" + downLoadPosition + "开始进入下载");
		try {
			for (int i = 0; i < urlList.size(); i++) {
				DownLoadImage.downLoad(urlList.get(i), "pic" + i + ".jpg", "E:\\picture\\" + downLoadPosition);
			}
			downLoadPosition++;
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("抓取图片序号为" + downLoadPosition + "下载结束");
	}
}
