import util.HttpRequest;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

//该类用来从网站上爬取数据到data文件夹中
public class getData {
    //getFile函数在爬取成功后返回true在爬取失败后返回false
    //参数为total返回total.json   参数为日期返回对应日期的json
    public static boolean getFile(String fileType)
    {
        String fileString;
        int len;
        HttpRequest  request= new HttpRequest(fileType);
        try {
            fileString = request.getFileString();
            len=fileString.length();

            if(fileType.equals("total"))
                fileString=fileString.substring(9,len-2);
            else
                fileString=fileString.substring(3,len-2);
            //奖牌文件和赛程文件前有不符合json标准的字符串 奖牌文件前有omedals1( 后有); 赛程文件前有OM( 后有);

        } catch (IOException exception) {
            return false;
        }

        try {
            String filePath;
            if(fileType.equals("total"))
                filePath="data/total.json";
            else
                filePath="data/schedule/"+fileType+".json";

            BufferedWriter out = new BufferedWriter(new FileWriter(filePath));
            out.write(fileString);
            out.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}
