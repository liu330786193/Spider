package URLGrab;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashSet;

public class URLGrab {

	public static void main(String[] args) throws IOException {
		HashSet<String> links = new HashSet<String>();
		String pageUrl = "http://news.baidu.com/";
		Document document = Jsoup.connect(pageUrl).get();
		Elements elements = document.select("a[href]");
		for (Element element : elements){
			String href = element.attr("href");
			if (href == null || href == ""){
				continue;
			}
			if (href.charAt(0) == '#'){
				continue;
			}
			if (href.indexOf("mailto:") != -1){
				continue;
			}
			if (href.toLowerCase().indexOf("javascript") != -1){
				continue;
			}
			if (href.charAt(0) == '/'){
				href = pageUrl + "//" + href;
			}
			if (href.indexOf("://") == -1){
				//处理绝对URL
				if (href.charAt(0) != '/'){
					href = pageUrl + "//" + href;
				}else {
					//处理相对url
				}
			}
			int index = href.indexOf("#");
			if (index != -1){
				href = href.substring(0, index);
			}
			System.out.println(href);
			links.add(href);

		}
	}

}
