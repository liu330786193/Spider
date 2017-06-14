package MultiDownload;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * 对系统的dns解析
 */
public class MultiDownload extends Thread {

	private final CloseableHttpClient httpClient;
	private final HttpContext httpContext;
	private final HttpGet httpGet;

	public MultiDownload(CloseableHttpClient httpClient, HttpContext httpContext, HttpGet httpGet) {
		this.httpClient = httpClient;
		this.httpContext = httpContext;
		this.httpGet = httpGet;
	}

	@Override
	public void run() {
		try {
			HttpResponse response = httpClient.execute(this.httpGet, this.httpContext);
			HttpEntity entity = response.getEntity();
			if (entity != null){
			}
			EntityUtils.consume(entity);
		} catch (IOException e) {
			e.printStackTrace();
			this.httpGet.abort();
		}
	}
}
