package Javascript;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dynamic {

	public static void main(String[] args) throws IOException {
		WebClient webClient = new WebClient();
		HtmlPage htmlPage = webClient.getPage("http://news.baidu.com/");
		List<HtmlAnchor> anchors = htmlPage.getAnchors();
		List<HtmlAnchor> jsAnchors = new ArrayList<HtmlAnchor>();
		anchors.stream().forEach(l -> {
			String href = l.getAttribute("href");
			if (href.startsWith("javascript:")){
				jsAnchors.add(l);
			}else if ("#".equals(href)){
				if (l.hasAttribute("onclick")){
					jsAnchors.add(l);
				}
			}
		});
		//通过HtmlAnchor对象得到实际链接地址
		List<String> list = new ArrayList<String>();
		anchors.stream().forEach(l -> {
			try {
				final HtmlPage newPage = l.click();
//				list.add(newPage.executeJavaScript("document.location").getJavaScriptResult().toString());
				list.add(newPage.getUrl().toString());
				System.out.println(newPage.getUrl());
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

}
