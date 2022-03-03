package bean.medal;

public class totalData implements totalJsonToString{
    public medalItem[] medalsList;
    public int total;

    public String getListByRank()
    {
        StringBuilder s=new StringBuilder();
        for (medalItem item:medalsList) {
            s.append("rank").append(item.rank).append(':').append(item.countryid).append("\ngold:").append(item.gold).append("\nsilver:").append(item.silver).append("\nbronze:").append(item.bronze).append("\ntotal:").append(item.count).append("\n-----\n");

        }
        return s.toString();
    }
}
