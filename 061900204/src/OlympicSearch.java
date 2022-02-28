import java.io.*;
import java.util.ArrayList;

public class OlympicSearch {
    public static void main(String[] args) {

        long startTime=System.currentTimeMillis(); //获取开始时间



        //去掉文件末尾的换行
        //多种输入文件格式

        //性能
        //改进前
        //4000 平均3000ms
        //性能
        //改进后
        //4000 平均1100ms



        //一开始的思路 每次都访问文件 再输出文件
        //第一次优化 访问一次文件后就在内存保存 再输出文件
        //第二次优化 根据输出文件只记住需要输出的内容 最后一次性输出


        if(args.length!=2) {
            System.out.println("运行格式异常，正确格式需要加上输入文件和输出文件");
            return;
        }

        ArrayList<String> requestType =new ArrayList<>();
        //按行读取input文件内容
        try {
            FileInputStream inputStream = new FileInputStream(args[0]);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
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

        new OutputData(requestType,args[1]);




        long endTime=System.currentTimeMillis(); //获取结束时间

        System.out.println("程序运行时间： "+(endTime-startTime)+"ms");


    }

}