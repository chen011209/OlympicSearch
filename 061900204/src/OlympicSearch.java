import java.io.*;
import java.util.ArrayList;

public class OlympicSearch {
    public static void main(String[] args) {
        if(args.length!=2) {
            System.out.println("运行格式异常，正确格式需要加上输入文件和输出文件");
            return;
        }

        ArrayList<String> requestType =new ArrayList<>();
        //按行读取input文件内容
        try {
            FileInputStream inputStream = new FileInputStream(args[0]);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));
            String str;
            while((str = bufferedReader.readLine()) != null)
            {
                requestType.add(str);
            }
            //close
            inputStream.close();
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("未找到该文件");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO异常");
        }


        OutputData out=new OutputData(requestType);
        out.outPutToFile(args[1]);
    }
}