package Javascript;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URLEncoder;

public class JinShanCiBa {

	public static void main(String[] args) throws IOException {
		String word = "word";
		String url = "http://www.iciba.com/ajax_sugg.php?key=" + URLEncoder.encode(word, "UTF-8");
		CloseableHttpClient httpClient = HttpClientBuilder.create().build();
		HttpGet httpGet = new HttpGet(url);
		CloseableHttpResponse response = httpClient.execute(httpGet);
	}

}
