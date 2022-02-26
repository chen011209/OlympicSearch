package bean.medal;

public class totalData {
    public medalItem[] medalsList;
    public int total;

    public String getListByRank()
    {

        StringBuilder s=new StringBuilder();

        for (medalItem item:medalsList) {
            s.append("rank"+item.rank+":"+item.countryid+"\ngold:"+item.gold+"\nsilver:"+item.silver+"\nbronze:"+item.bronze+"\ntotal:"+item.count+"\n-----\n");

        }


        return s.toString();
    }
}
