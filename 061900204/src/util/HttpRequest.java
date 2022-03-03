package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

//该类用于HTTP连接 并且得到连接的文件字符串
public class HttpRequest {
    //HTTP连接超时时间
    private static final int TIMEOUT = 1000;
    private String urlString;
    private URL url;
    private static final String TOTAL = "https://api.cntv.cn/olympic/getBjOlyMedals?serviceId=2022dongao&itemcode=GEN-------------------------------&t=jsonp&cb=omedals1";
    private static final String SCHEDULE = "https://api.cntv.cn/Olympic/getBjOlyMatchList?startdatecn=2022****&t=jsonp&cb=OM&serviceId=2022dongao";


// 奖牌榜
// https://api.cntv.cn/olympic/getBjOlyMedals?serviceId=2022dongao&itemcode=GEN-------------------------------&t=jsonp&cb=omedals1
// 赛程表(2022****改为需要的日期)
// https://api.cntv.cn/Olympic/getBjOlyMatchList?startdatecn=2022****&t=jsonp&cb=OM&serviceId=2022dongao



    //requestType为请求的类型(请求奖牌或者赛程)
    //为赛程时requestType为请求的时间
    public HttpRequest(String requestType)
    {
        if(requestType.equals("total"))
            urlString=TOTAL;
        else
            urlString=SCHEDULE.replace("****",requestType);


        try {
            this.url = new URL(urlString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            System.out.println("HTTP连接异常");
        }

    }

    //返回文件内容的字符串
    public String getFileString() throws IOException {
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
