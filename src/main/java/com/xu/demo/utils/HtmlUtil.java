package com.xu.demo.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.util.HashMap;

/**
 * html工具类
 *
 * @author xushaohua on 2017-12-13.
 */
public class HtmlUtil {

	public static InputStream read(String fileName) {
		// ClassLoader loader = Thread.currentThread().getContextClassLoader();
		ClassLoader loader = HtmlUtil.class.getClassLoader();
		InputStream is = loader.getResourceAsStream(fileName);
		return is;
	}

	public static String getHtml(String fileName) {
		String html = "";
		try {
			Document doc = Jsoup.parse(read(fileName), "UTF-8", "");
			html = doc.html();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return html;
	}

	public static Document getDocument(String html) {
		Document doc = null;
		try {
			doc = Jsoup.parse(html, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static String replaceTextById(String html, String id, String text) {
		Document doc = getDocument(html);
		Element element = doc.getElementById(id);
		element.text(text);
		return doc.toString();
	}

	/**
	 * html 模板填充内容
	 *
	 * @param html
	 * @param paras：参数集合
	 * @return
	 * @Author masl - 2017/9/29 14:05
	 */
	public static String replaceTextById(String html, HashMap<String, String> paras) {
		if (paras == null || paras.size() == 0) {
			return html;
		}
		Document doc = getDocument(html);
		for (String id : paras.keySet()) {
			//不包含 id
			if (!html.contains("id=\"" + id + "\"") && !html.contains("id='" + id + "'")) {
				continue;
			}
			try {
				doc.getElementById(id).text(paras.get(id));
			} catch (Exception ex) {
				System.out.println("HtmlUtil.replaceTextById 异常：" + ex.toString());
			}
		}
		return doc.toString();
	}

	public static String replaceAttributeById(String html, String id, String attrKey, String attrValue) {
		Document doc = getDocument(html);
		Element element = doc.getElementById(id);
		element.attr(attrKey, attrValue);
		return doc.toString();
	}

	public static String appendHtmlById(String html, String id, String text) {
		Document doc = getDocument(html);
		Element element = doc.getElementById(id);
		element.append(text);
		return doc.toString();
	}
}
