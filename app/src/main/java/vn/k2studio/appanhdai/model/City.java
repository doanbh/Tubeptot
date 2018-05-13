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
    private ArrayList<District> listDistricts;

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getNameCity() {
        return nameCity;
    }

    public void setNameCity(String nameCity) {
        this.nameCity = nameCity;
    }

    public ArrayList<District> getListDistricts() {
        return listDistricts;
    }

    public void setListDistricts(ArrayList<District> listDistricts) {
        this.listDistricts = listDistricts;
    }
}
