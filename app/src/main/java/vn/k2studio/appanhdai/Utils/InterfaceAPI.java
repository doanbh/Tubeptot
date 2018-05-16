package vn.k2studio.appanhdai.Utils;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import vn.k2studio.appanhdai.model.ItemNewFromApi;

public interface InterfaceAPI {
    //api register
    @POST("dk_user.php")
    Call<String> register(@Query("name") String name, @Query("birthday") String birthday,
            @Query("hometown") String hometown, @Query("numberphone") String numberphone);

    //api get list news
    @POST("list_new.php")
    Call<ArrayList<ItemNewFromApi>> getListNews(@Query("num_phone") String num_phone);

    //api post new
    @FormUrlEncoded
    @POST("post_new.php")
    Call<String> postNew(@Field("name_city") String name_city,
            @Field("name_district") String name_district, @Field("content") String content,
            @Field("num_phone") String num_phone, @Field("name") String name,
            @Field("list_image[]") ArrayList<String> list_image);
}
