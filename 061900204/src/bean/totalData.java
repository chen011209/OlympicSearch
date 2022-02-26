package bean;

public class totalData {
    public CountryMedal[] medalsList;
    public int total;

    public String getListByRank()
    {

        StringBuilder s=new StringBuilder();

//
//        rank1:NOR
//        gold:9
//        silver:6
//        bronze:7
//        total:22
//                -----
//
//
//        public String countryid;
//        public String countryname;
//        public int gold;
//        public int silver;
//        public int bronze;
//        public int count;
//        public int rank;

        for (CountryMedal c:medalsList) {
            s.append("rank"+c.rank+":"+c.countryid+"\ngold:"+c.gold+"\nsilver:"+c.silver+"\nbronze:"+c.bronze+"\ntotal:"+c.count+"\n-----\n");

        }


        return s.toString();
    }
}
