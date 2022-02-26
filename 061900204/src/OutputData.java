import bean.medal.totalFile;
import bean.schedule.scheduleFile;
import com.google.gson.Gson;
import util.file;
import java.util.ArrayList;
import static util.file.isFileExist;
import static util.file.isFolderExist;

//该类根据输入文件输出对应的数据到输出文件中
public class OutputData {
    String outputFile;

    //如果输出失败重新爬取一遍到文件后再输出
    public void outputTotal()
    {
        Gson gson = new Gson();
        String jsonString = file.readFile("data/total.json");
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

                jsonString = file.readFile("data/total.json");
                totalFile tf = gson.fromJson(jsonString, totalFile.class);
                outString=tf.data.getListByRank();
            }
        }

      file.writeFile(outputFile,outString);
    }


    public void outputScedule(String date)
    {
        Gson gson = new Gson();
        String jsonString = file.readFile("data/schedule/"+date+".json");
        String outString = null;

        try{
            scheduleFile sf = gson.fromJson(jsonString, scheduleFile.class);
            outString=sf.data.getSchedule();


        }catch (Exception e){
            //解析文件夹中的json文件失败后需要重新爬取json文件
            if(!getData.getFile(date)){
                System.out.println("json文件损坏，并且爬取数据失败");
            }
            else {
                System.out.println("json文件损坏,已经重新爬取文件");

                jsonString = file.readFile("data/schedule/"+date+".json");
                scheduleFile sf = gson.fromJson(jsonString, scheduleFile.class);
                outString=sf.data.getSchedule();
            }
        }

        file.writeFile(outputFile,outString);
    }


    //根据requestType输出到outputFile
    public OutputData(ArrayList<String> requestType,String outputFile)
    {
        this.outputFile=outputFile;

        //判断data文件夹是否存在,不存在则创建文件夹
        isFolderExist("data");

        for (String s:requestType)
        {
            if(s.equals("total")) {
                //文件夹中不存在total.json文件则爬取数据,isFileExist函数中创建对应文件
                if(!isFileExist("data/total.json")){
                    //getData函数在爬取成功后返回true在爬取失败后返回false
                    if(!getData.getFile("total")){
                        System.out.println("爬取数据失败");
                        System.exit(0);
                    }
                }

                outputTotal();

            }else if (s.length()==13&&s.substring(0,8).equals("schedule")){
                String date=s.substring(9,13);

                //冬奥会时间为2月2日到2月20日
                if(202<=Integer.parseInt(date)&&Integer.parseInt(date)<=220)
                {
                    if(!isFileExist("data/schedule/"+s.substring(9,13)+".json")){
                        //getData函数在爬取成功后返回true在爬取失败后返回false
                        if(!getData.getFile(date)){
                            System.out.println("爬取数据失败");
                        }
                    }

                    outputScedule(date);
                }else {
                    file.writeFile(outputFile,  "N/A\n----\n");
                }

            }else {
                file.writeFile(outputFile,  "Error\n----\n");
            }


        }
    }

}



//OlympicSearch.java
//javac OlympicSearch.java
//javac -encoding UTF-8 OlympicSearch.java
//java OlympicSearch input.txt output.txt