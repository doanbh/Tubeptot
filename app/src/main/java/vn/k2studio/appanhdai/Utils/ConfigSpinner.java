package vn.k2studio.appanhdai.Utils;

import android.content.Context;
import android.os.Build;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.adapter.SpinnerAdapter;
import vn.k2studio.appanhdai.model.City;
import vn.k2studio.appanhdai.model.District;

import static java.util.Collections.addAll;

public class ConfigSpinner {
    private static Context context;
    private ConfigJson configJson;
    private static ArrayList<City> mCities;

    public ConfigSpinner(Context context) {
        this.context = context;
        configJson = new ConfigJson();
        getListCity();
    }

    public void getListCity() {
        mCities = new ArrayList<>();
        mCities = (ArrayList<City>) configJson.getListCity(
                SharedPrefs.getInstance().get(Constant.LIST_CITY, String.class));
    }

    public static void setupSpinnerCity(Spinner spinner, SpinnerAdapter mAdapterCity,
            ArrayList<String> listCityString) {
        for (int i = 0; i < mCities.size(); i++) {
            listCityString.add(mCities.get(i).getNameCity());
        }
        mAdapterCity =
                new SpinnerAdapter(context, android.R.layout.simple_spinner_item, listCityString);
        spinner.setAdapter(mAdapterCity);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setDropDownVerticalOffset(Utils.dpToPx(50));
        }
    }

    public static void setupSpinnerDistrict(Spinner spinner, SpinnerAdapter mAdapterDistrict,
            int position) {
        ArrayList<String> listDistrictString = new ArrayList<>();
        ArrayList<District> districtArrayList = mCities.get(position).getListDistricts();
        for (int i = 0; i < districtArrayList.size(); i++) {
            listDistrictString.add(districtArrayList.get(i).getNameDistrict());
        }
        mAdapterDistrict = new SpinnerAdapter(context, android.R.layout.simple_spinner_item,
                listDistrictString);
        spinner.setAdapter(mAdapterDistrict);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            spinner.setDropDownVerticalOffset(Utils.dpToPx(50));
        }
    }
}
