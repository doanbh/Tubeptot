package vn.k2studio.appanhdai.Utils;

import android.app.Activity;
import android.os.AsyncTask;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import vn.k2studio.appanhdai.model.City;
import vn.k2studio.appanhdai.model.District;

public class ConfigCity extends AsyncTask<Void, Void, Void> {
    private String datafromJson;
    private ArrayList<City> listCity;
    private ArrayList<String> listDistrict;
    private Activity mActivity;
    private ConfigJson mConfigJson;

    public ConfigCity(Activity activity) {
        mActivity = activity;
        mConfigJson = new ConfigJson();
    }

    private String loadJsonFromAssets() {
        String json = null;
        try {
            InputStream inputStream = mActivity.getAssets().open("sql_city.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        datafromJson = loadJsonFromAssets();
        getListCity();
        saveListCity();
        return null;
    }

    private void saveListCity() {
        SharedPrefs.getInstance().put(Constant.LIST_CITY, mConfigJson.setListCity(listCity));
    }

    private void getListCity() {
        listCity = new ArrayList<>();
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(datafromJson);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                City city = getCity(jsonObject);
                city.setIdCity(i);
                listCity.add(city);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private City getCity(JSONObject jsonObject) {
        City city = new City();
        city.setNameCity(jsonObject.optString("name"));
        try {
            city.setListDistricts(getListDistricts(jsonObject.getJSONArray("districts")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return city;
    }

    private ArrayList<District> getListDistricts(JSONArray jsonArray) {
        ArrayList<District> districtArrayList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            District district = new District();
            try {
                district.setNameDistrict(jsonArray.getString(i));
                districtArrayList.add(district);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return districtArrayList;
    }
}
