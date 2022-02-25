import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


//该类输出数据到输出文件中
public class OutputData {
    String outputFile;






// 奖牌榜
// https://api.cntv.cn/olympic/getBjOlyMedals?serviceId=2022dongao&itemcode=GEN-------------------------------&t=jsonp&cb=omedals1
// 赛程表(20220212改为需要的日期)
// https://api.cntv.cn/Olympic/getBjOlyMatchList?startdatecn=20220212&t=jsonp&cb=OM&serviceId=2022dongao






    //如果输出失败重新爬取一遍到文件后再输出
    public void outputTotal()
    {
        System.out.println("ok");
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

                //文件夹中不存在total.json文件则爬取数据
                if(!isFileExist("data/total.json")){
                    //getTotal函数在爬取成功后返回true在爬取失败后返回false
                    if(!getData.getTotal()){
                        System.out.println("爬取数据失败");
                    }
                }

                outputTotal();


            }else if (s.length()==13&&s.substring(0,8).equals("schedule")){
                String date=s.substring(9,13);


                //冬奥会时间为2月4日到2月20日
                if(204<=Integer.parseInt(date)&&Integer.parseInt(date)<=220)
                {
                    if(!isFileExist("data/schedule/"+s.substring(9,13))){
                        //getData函数在爬取成功后返回true在爬取失败后返回false
                        if(!getData.getSchedule(date)){
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