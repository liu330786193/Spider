package SetProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by lyl on 2017/11/15.
 */
public class Stock {

    private static String loginUrl = "http://hq.sinajs.cn/list=sh601006,sh601001";

    public static void main(String[] args) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(loginUrl);
        CloseableHttpResponse response = httpClient.execute(httpGet);
        byte[] bytes = null;
        response.getEntity().getContent().read(bytes);
        StringBuilder entityStringBuilder=new StringBuilder();
        BufferedReader reader = new BufferedReader
                (new InputStreamReader(response.getEntity().getContent(), "UTF-8"), 8*1024);
        String line=null;
        while ((line= reader.readLine())!=null) {
            entityStringBuilder.append(line+"/n");
        }
        //利用从HttpEntity中得到的String生成JsonObject
        resultJsonObject=new JSONObject(entityStringBuilder.toString());
        bytes.toString();
        System.out.println(response.getEntity().getContent());
    }

}
