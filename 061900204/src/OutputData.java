import bean.totalFile;
import com.google.gson.Gson;
import test.User;

import java.io.*;
import java.util.ArrayList;


//该类根据输入文件输出对应的数据到输出文件中
public class OutputData {
    String outputFile;




    //如果输出失败重新爬取一遍到文件后再输出
    public void outputTotal()
    {



        //读取total.json文件
        StringBuilder fileString=new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream("data/total.json");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String str;

            while((str = bufferedReader.readLine()) != null)
            {
                fileString.append(str);
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

        Gson gson = new Gson();
        String jsonString =fileString.toString();

        String outString = null;
        try{
            totalFile tf = gson.fromJson(jsonString, totalFile.class);
            outString=tf.data.getListByRank();




        }catch (Exception e){

            //解析文件夹中的json文件失败后需要重新爬取json文件
            if(!getData.getFile("total")){
                System.out.println("json文件损坏，并且爬取数据失败");
            }
            else {
                System.out.println("json文件损坏,已经重新爬取文件");



                //重新读取total.json文件
                fileString=new StringBuilder();
                try {
                    FileInputStream inputStream = new FileInputStream("data/total.json");
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String str;

                    while((str = bufferedReader.readLine()) != null)
                    {
                        fileString.append(str);
                    }
                    //close
                    inputStream.close();
                    bufferedReader.close();
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    System.out.println("未找到该文件");
                } catch (IOException e2) {
                    e2.printStackTrace();
                    System.out.println("IO异常");
                }


                jsonString=fileString.toString();

                totalFile tf = gson.fromJson(jsonString, totalFile.class);
                outString=tf.data.getListByRank();
            }
        }



        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(outputFile));
            out.write(outString);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public void outputScedule(String date)
    {
        System.out.println("scheduleok");
    }


    //根据requestType输出到outputFile
    public OutputData(ArrayList<String> requestType,String outputFile)
    {
        this.outputFile=outputFile;


        //判断data文件夹是否存在
        File folder = new File("data");
        //不存在文件夹则新建文件夹
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();
        }



        for (String s:requestType)
        {
            if(s.equals("total")) {

                //文件夹中不存在total.json文件则爬取数据,isFileExist函数中创建对应文件
                if(!isFileExist("data/total.json")){
                    //getData函数在爬取成功后返回true在爬取失败后返回false
                    if(!getData.getFile("total")){
                        System.out.println("爬取数据失败");
                    }
                }

                outputTotal();


            }else if (s.length()==13&&s.substring(0,8).equals("schedule")){
                String date=s.substring(9,13);


                //冬奥会时间为2月4日到2月20日
                if(204<=Integer.parseInt(date)&&Integer.parseInt(date)<=220)
                {
                    if(!isFileExist("data/schedule/"+s.substring(9,13)+".json")){
                        //getData函数在爬取成功后返回true在爬取失败后返回false
                        if(!getData.getFile(date)){
                            System.out.println("爬取数据失败");
                        }
                    }

                    outputScedule(date);
                }else {
                    System.out.println("N/A");
                }

            }else {
                System.out.println("Error");
            }


        }
    }


    //检查文件是否存在，如果不存在则创建文件
    public static boolean isFileExist(String fileName)
    {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        }
        else {
            return true;
        }



    }





}



//OlympicSearch.java
//javac OlympicSearch.java
//javac -encoding UTF-8 OlympicSearch.java
//java OlympicSearch input.txt output.txt