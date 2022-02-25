package HTTPTEST;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpRequest {
    //HTTP连接超时时间
    private static final int TIMEOUT = 1000;

    private URL url;


    public HttpRequest(String url)
    {
        try {
            this.url = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("连接URL异常");
        }
    }


    //返回文件内容的字符串
    public String getFile() throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(TIMEOUT);
        connection.setReadTimeout(TIMEOUT);


        if (connection.getResponseCode() != 200) {
            System.out.println("连接失败,返回码为"+connection.getResponseCode());
        }


        //buffer用于接收url的缓冲 builder用于生成网址内容的字符串
        BufferedReader buffer = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder builder = new StringBuilder();


        String s;
        while ((s = buffer.readLine()) != null) {
            builder.append(s);
        }

        connection.disconnect();
        return builder.toString();
    }
}
