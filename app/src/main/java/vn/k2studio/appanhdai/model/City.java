package vn.k2studio.appanhdai.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class City {
    private int idCity;
    @SerializedName("name")
    @Expose
    private String nameCity;
    @SerializedName("districts")
    @Expose
    private ArrayList<String> listDistricts;
}
