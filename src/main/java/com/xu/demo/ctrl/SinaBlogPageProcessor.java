package com.xu.demo.ctrl;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 抓取新浪博客数据eg
 *
 * @author xushaohua on 2017-12-15.
 */
public class SinaBlogPageProcessor implements PageProcessor {
	// 文章列表URL正则匹配
	public static final String URL_LIST = "http://blog\\.sina\\.com\\.cn/s/articlelist_1487828712_0_\\d+\\.html";
	// 具体文章URL正则匹配
	public static final String URL_POST = "http://blog\\.sina\\.com\\.cn/s/blog_\\w+\\.html";

	private Site site = Site.me()
			.setDomain("blog.sina.com.cn")
			.setSleepTime(3000)
			.setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko)Chrome/26.0.1410.65 Safari/537.31");


	@Override
	public void process(Page page) {
		// 列表页
		if (page.getUrl().regex(URL_LIST).match()) {
			page.addTargetRequests(page.getHtml().xpath("//div[@class=\"articleList\"]").links().regex(URL_POST).all());
			page.addTargetRequests(page.getHtml().links().regex(URL_LIST).all());
			// 文章页
		} else {
			page.putField("title", page.getHtml().xpath("//div[@class='articalTitle']/h2"));
			page.putField("content", page.getHtml().xpath("//div[@id='articlebody']//div[@class='articalContent']"));
			page.putField("date", page.getHtml().xpath("//div[@id='articlebody']//span[@class='time SG_txtc']").regex("\\((.*)\\)"));
		}
	}

	@Override
	public Site getSite() {
		return site;
	}

	public static void main(String[] args) {
		Spider.create(new SinaBlogPageProcessor())
				.addUrl("http://blog.sina.com.cn/s/articlelist_1487828712_0_1.html")
				.addPipeline(new JsonFilePipeline("E:\\webmagic\\"))
				.run();
	}
}
