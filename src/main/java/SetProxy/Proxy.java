package SetProxy;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Proxy {

	private static String username = "18042318775";
	private static String password = "liu19910406";
	private static String redirectURL = "http://www.renren.com/home";
	private static String renRenLoginURL = "http://www.renren.com/PLogin.do";

	private CloseableHttpResponse response;
	private CloseableHttpClient httpClient = HttpClientBuilder.create().build();


	//设置代理 Proxy
	private void setProxy(){
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(new AuthScope("192.168.10.1", 1080), new UsernamePasswordCredentials("useranme",
				"password"));
		CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}

	private boolean login(){
		HttpPost httpPost = new HttpPost(renRenLoginURL);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("origURL", redirectURL));
		nvps.add(new BasicNameValuePair("domain", "renren.com"));
		nvps.add(new BasicNameValuePair("isplogin", "true"));
		nvps.add(new BasicNameValuePair("formName", ""));
		nvps.add(new BasicNameValuePair("method", ""));
		nvps.add(new BasicNameValuePair("submit", "登录"));
		nvps.add(new BasicNameValuePair("email", username));
		nvps.add(new BasicNameValuePair("password", password));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));
			response = httpClient.execute(httpPost);
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}finally {
			httpPost.abort();
		}
		return true;
	}

	private String getRedirectLocation(){
		Header locationHeader = response.getFirstHeader("Location");
		if (locationHeader == null){
			return null;
		}
		return locationHeader.getValue();
	}

	private String getText(String redirectLocation) throws IOException {
		HttpGet httpGet = new HttpGet(redirectLocation);
		ResponseHandler<String> responseHandler = new BasicResponseHandler();
		String responseBody = "";
		try {
			responseBody = httpClient.execute(httpGet, responseHandler);
		}catch (Exception e){
			e.printStackTrace();
			responseBody = null;
		}finally {
			httpGet.abort();
			httpClient.close();
		}
		return responseBody;
	}

	private void printText() throws IOException {
		if (login()){
			String redirectLocation = getRedirectLocation();
			if (redirectLocation != null){
				System.out.println(getText(redirectLocation));
			}
		}
	}
}
