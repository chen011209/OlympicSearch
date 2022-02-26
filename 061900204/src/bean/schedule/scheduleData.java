package bean.schedule;




public class scheduleData {
    public matchItem[] matchList;
    public int total;


    public String getSchedule()
    {

        StringBuilder s=new StringBuilder();


        String time,matchCountry;
        //如果比赛有参赛的双方国家matchCountry为XXX VS XXX无则为空
        int len;
        for (matchItem item:matchList) {

            if(item.homename.length()==0)
                matchCountry=" ";
            else
                matchCountry=" "+item.homename+"VS"+item.awayname;

            len=item.startdatecn.length();
            time=item.startdatecn.substring(len-8,len-3);
            s.append("time:"+time+"\nsport:"+item.itemcodename+"\nname:"
                    +item.title+matchCountry+"\nvenue:"+item.venuename+"\n-----\n");

        }

        return s.toString();

    }
}