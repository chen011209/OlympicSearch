import bean.medal.totalFile;
import bean.schedule.scheduleFile;
import com.google.gson.Gson;
import util.file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;

import static util.file.isFileExist;
import static util.file.isFolderExist;

//该类根据输入文件输出对应的数据到输出
public class OutputData {
    public String []fileString =new String[22];
    Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");//判断字符串是否为数字的正则表达式
    //执行getOutput和getSchedule后fileString会有对应的输出



    //业务逻辑变量
    ArrayList<String> requestType;
    String outputFile;
    boolean []isOut=new boolean[20];
    ArrayList<Integer> outputCode = new ArrayList<>();//输出的字符串代号    0为total 1到19为对应日期 2到20  20为N/A 21为Error

    //getOutput和getSchedule逻辑：getOutput和getSchedule函数会获取对应的输出到fileString中
    //业务逻辑：isOut用于检查是否存在对应的fileString  .outputCode用于输出对应的fileString


    public void getOutput()
    {
        Gson gson = new Gson();
        String jsonString = file.readFile("data/total.json");

        try{
            totalFile tf = gson.fromJson(jsonString, totalFile.class);
            fileString[0]=tf.data.getListByRank();

        }catch (Exception e){
            //解析文件夹中的json文件失败后需要重新爬取json文件
            if(!getData.getFile("total")){
                System.out.println("json文件损坏，并且爬取数据失败");
            }
            else {
                System.out.println("json文件损坏,已经重新爬取文件");

                jsonString = file.readFile("data/total.json");
                totalFile tf = gson.fromJson(jsonString, totalFile.class);
                fileString[0]=tf.data.getListByRank();
            }
        }

    }

    public void getSchedule(String date)
    {
        int dateInt=Integer.parseInt(date);


        Gson gson = new Gson();
        String jsonString = file.readFile("data/schedule/"+date+".json");


        try{
            scheduleFile sf = gson.fromJson(jsonString, scheduleFile.class);
            fileString[dateInt-201]=sf.data.getSchedule();


        }catch (Exception e){
            //解析文件夹中的json文件失败后需要重新爬取json文件
            if(!getData.getFile(date)){
                System.out.println("json文件损坏，并且爬取数据失败");
            }
            else {
                System.out.println("json文件损坏,已经重新爬取文件");

                jsonString = file.readFile("data/schedule/"+date+".json");
                scheduleFile sf = gson.fromJson(jsonString, scheduleFile.class);
                fileString[dateInt-201]=sf.data.getSchedule();
            }
        }


    }


    //根据requestType输出到outputFile
    public OutputData(ArrayList<String> requestType)
    {

        this.requestType=requestType;
        fileString[20]="N/A\n----\n";
        fileString[21]="Error\n----\n";

        //判断data文件夹是否存在,不存在则创建文件夹
        isFolderExist("data");
        isFolderExist("data/schedule");

    }



    //业务逻辑部分
    public void outPutToFile(String outputFile)
    {
        this.outputFile=outputFile;

        for (String s:requestType) {
            String[] sArray = s.split("\\s+"); //分割一个或者多个空格

            //第一个可能是空字符串
            if(sArray[0].length()==0){
                sArray= Arrays.copyOfRange(sArray,1,sArray.length);
            }

            if(sArray.length==1&&sArray[0].equals("total")) {
                //文件夹中不存在total.json文件则爬取数据,isFileExist函数中创建对应文件
                if(!isFileExist("data/total.json")){
                    //getData函数在爬取成功后返回true在爬取失败后返回false
                    if(!getData.getFile("total")){
                        System.out.println("爬取数据失败");
                        System.exit(0);
                    }
                }
                if(isOut[0]==false){
                    getOutput();
                    isOut[0]=true;
                }
                outputCode.add(0);


            }else if (sArray.length==2&&sArray[0].equals("schedule")){
                String date=sArray[1];
                //冬奥会时间为2月2日到2月20日
                //第一个判断字符串是否是数字
                if(date.length()==4&&pattern.matcher(date).matches()&&202<=Integer.parseInt(date)&&Integer.parseInt(date)<=220)
                {
                    if(!isFileExist("data/schedule/"+sArray[1]+".json")){
                        //getData函数在爬取成功后返回true在爬取失败后返回false
                        if(!getData.getFile(date)){
                            System.out.println("爬取数据失败");
                        }
                    }
                    int dateInt=Integer.parseInt(date);
                    if(isOut[dateInt-201]==false){
                        getSchedule(date);
                        isOut[dateInt-201]=true;
                    }
                    outputCode.add(dateInt-201);
                }else {
                    outputCode.add(20);
                }

            }else {
                outputCode.add(21);

            }
        }

        StringBuilder s=new StringBuilder();
        for (int i:outputCode) {
            s.append(fileString[i]);
        }
        //删除末尾的换行
        s.deleteCharAt(s.length()-1);
        s.deleteCharAt(s.length()-1);


        //到此 s为需要输出的字符串 之后可以根据需要输出到文件或者其他地方
        file.writeFile(outputFile,s.toString(),true);
    }




}