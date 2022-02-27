package util;

import java.io.*;


//该类作用 读取文件 输出内容到文件 检查文件或文件夹是否存在 不存在则创建
public class file {
    public static String readFile(String filePath)
    {
        //读取文件,返回文件的字符串
        StringBuilder fileString=new StringBuilder();
        try {
            FileInputStream inputStream = new FileInputStream(filePath);
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

        return fileString.toString();
    }


    //writeFile为追加模式
    public static void writeFile(String filePath,String content)
    {



        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(filePath,true));

            out.write(content);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //检查文件是否存在，如果不存在则创建文件并返回false
    public static boolean isFileExist(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return false;

        } else {
            return true;
        }

    }

    public static boolean isFolderExist(String folderName)
    {
        File folder = new File(folderName);
        //不存在文件夹则新建文件夹
        if (!folder.exists() && !folder.isDirectory()) {
            folder.mkdirs();

            return false;
        }else {
            return true;
        }
    }


}



