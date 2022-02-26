package test;


import com.google.gson.Gson;

public class jsontest {
    public static void main(String[] args) {
        Gson gson = new Gson();
        String jsonString = "{\"name\":\"张三\",\"age\":24,\"age2\":[{\"a\":1,\"b\":2,\"c\":6},{\"a\":3,\"b\":4}]}";
        User user = gson.fromJson(jsonString, User.class);
        System.out.println(jsonString);
        System.out.println(user.age2[0].b);
    }
}
