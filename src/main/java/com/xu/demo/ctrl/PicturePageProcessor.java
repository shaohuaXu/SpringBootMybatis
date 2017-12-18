package com.xu.demo.ctrl;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * 描述
 *
 * @author xushaohua on 2017-12-15.
 */
public class PicturePageProcessor implements PageProcessor {
	private Site site = Site.me().setDomain("www.87g.com").setSleepTime(1000);

	@Override
	public void process(Page page) {
		List<String> list = page.getHtml().xpath("//div[@class=\"picnew clearfix\"]/ul/li/a/img[@src]").regex("(http:\\/\\/mm\\.chinasareview\\.com\\/wp-content\\/uploads\\/(.*)\\.jpg)").all();
		page.putField("picName", list);
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new PicturePageProcessor())
				.addUrl("http://www.meizitu.com/a/5585.html")
				.addPipeline(new DownloadPipeLine())
				.run();
	}
}
