package DNSCache;

import org.apache.http.HttpResponse;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.impl.io.DefaultHttpResponseParserFactory;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.io.IOException;

/**
 * 对系统的dns解析
 */
public class DNSCache {

	public static void main(String[] args) throws IOException {
		DnsResolver dnsResolver = new SystemDefaultDnsResolver();
		NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
		SSLContext sslContext = SSLContexts.createDefault();
		RedirectStrategy redirectStrategy = new LaxRedirectStrategy();
		ManagedHttpClientConnectionFactory factory = new ManagedHttpClientConnectionFactory(
				new DefaultHttpRequestWriterFactory(),
				new DefaultHttpResponseParserFactory()
		);
		//对系统de
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory>create()
				.register("https", new SSLConnectionSocketFactory(sslContext, hostnameVerifier))
				.register("http", new PlainConnectionSocketFactory()).build();

		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(
				socketFactoryRegistry,factory,dnsResolver
		);
		manager.setMaxTotal(1000);
		CloseableHttpClient httpClient = HttpClientBuilder
				.create()
				.setUserAgent("Mozilla")
				.setConnectionManager(manager)
				.setRedirectStrategy(redirectStrategy)
				.setMaxConnPerRoute(-1)
				.build();

		RequestConfig defaultConfig = RequestConfig
				.custom()
				.setCookieSpec(CookieSpecs.IGNORE_COOKIES)
				.setExpectContinueEnabled(false)
				.setStaleConnectionCheckEnabled(false)
				.setRedirectsEnabled(true)
				.setStaleConnectionCheckEnabled(false)
				.setMaxRedirects(5)
				.build();

		RequestConfig requestConfig = RequestConfig
				.copy(defaultConfig)
				.setSocketTimeout(15000)
				.setConnectionRequestTimeout(-1)
				.setConnectTimeout(15000)
				.build();
		HttpGet httpGet = new HttpGet("http://www.sina.com.cn");
		httpGet.setConfig(requestConfig);
		HttpResponse response = httpClient.execute(httpGet);
		System.out.println(response);
	}

}
