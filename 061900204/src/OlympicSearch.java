public class OlympicSearch {


    //检查是否已经爬取数据文件到data文件夹中
    public static boolean dataExist()
    {
        return true;
    }


    public static void search(String inputFile,String outputFile)
    {

    }

    public static void main(String[] args) {
        if(args.length!=2)
        {
            System.out.println("运行格式异常，正确格式需要加上输入文件和输出文件");
            return;
        }
        else
        {
            //先检查数据是否已经爬取
            if(dataExist())
            {
                search(args[0],args[1]);
            }

        }



    }

}