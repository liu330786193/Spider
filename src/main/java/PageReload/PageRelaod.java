package PageReload;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class PageRelaod {
	public static void main(String[] args) throws IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL("https://www.baidu.com").openConnection();
		connection.setRequestMethod("HEAD");
		System.out.println("更新时间为:"+ new Date(connection.getLastModified()));
	}
}
