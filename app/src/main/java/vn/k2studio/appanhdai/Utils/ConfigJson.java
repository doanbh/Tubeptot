package vn.k2studio.appanhdai.Utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import vn.k2studio.appanhdai.model.City;
import vn.k2studio.appanhdai.model.UserInfo;

public class ConfigJson {
    public Gson gson;

    public ConfigJson() {
        gson = new Gson();
    }

    public String setListCity(ArrayList<City> textList) {
        return gson.toJson(textList);
    }

    public List<City> getListCity(String textListCity) {
        Type type = new TypeToken<List<City>>() {
        }.getType();
        List<City> listCity = gson.fromJson(textListCity, type);
        return listCity;
    }

    public String setUser(UserInfo userInfo) {
        return gson.toJson(userInfo);
    }

    public UserInfo getUser(String userInfo) {
        Type type = new TypeToken<UserInfo>() {
        }.getType();
        UserInfo userInfo1 = gson.fromJson(userInfo, type);
        return userInfo1;
    }
}
