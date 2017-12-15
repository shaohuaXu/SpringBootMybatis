package com.xu.demo.ctrl;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.example.GithubRepo;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * 基于注解写法的爬虫处理类
 *
 * @author xushaohua on 2017-12-15.
 */
// 目标URL
@TargetUrl("https://github.com/\\w+/\\w+")
@HelpUrl("https://github.com/\\w+")
public class GithubRepoPageProcessor2 {
	// 抽取规则，结果放到name字段里
	@ExtractBy(value = "//h1[@class='entry-title public']/strong/a/text()", notNull = true)
	private String name;

	// 抽取规则，结果放到author字段里
	@ExtractByUrl("https://github\\.com/(\\w+)/.*")
	private String author;

	// 抽取规则，结果放到readme字段里
	@ExtractBy("//div[@id='readme']/tidyText()")
	private String readme;

	public static void main(String[] args) {
		OOSpider.create(Site.me()
				.setSleepTime(1000), new ConsolePageModelPipeline(), GithubRepo.class)
				.addUrl("https://github.com/code4craft")
				.thread(5).run();
	}
}
