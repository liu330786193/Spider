package DownloadImage;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DownloadImage {

	public static void main(String[] args) throws IOException {
		String url = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1497375319205&di=cb56a0928bc1ffc4fbf9ab3d8f12806a&imgtype=0&src=http%3A%2F%2Fimg4.duitang.com%2Fuploads%2Fblog%2F201512%2F12%2F20151212120309_BduTC.thumb.700_0.jpeg";
		HttpGet get = new HttpGet(url);
		HttpResponse response = HttpClientBuilder.create().build().execute(get);
		File file = new File("C:\\Users\\lyl\\Desktop\\1.jpeg");
		response.getEntity().writeTo(new FileOutputStream(file));
	}

}
