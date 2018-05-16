package vn.k2studio.appanhdai.presenter;

import android.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.k2studio.appanhdai.Utils.ConfigJson;
import vn.k2studio.appanhdai.Utils.ConfigRetrofitApi;
import vn.k2studio.appanhdai.Utils.Constant;
import vn.k2studio.appanhdai.Utils.InterfaceAPI;
import vn.k2studio.appanhdai.Utils.SharedPrefs;
import vn.k2studio.appanhdai.model.UserInfo;

public class RegisterInteratorImpl implements RegisterInterator {

    @Override
    public void register(String user, String phone, String birthday, String hometown,
            OnRegisterFinishedListener listener) {
        registerRetrofit(user, phone, birthday, hometown, listener);
    }

    private void registerRetrofit(final String user, final String phone, final String birthday,
            final String hometown, final OnRegisterFinishedListener listener) {
        Retrofit retrofit = ConfigRetrofitApi.getInstance();
        retrofit.create(InterfaceAPI.class)
                .register(user, birthday, hometown, phone)
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null) {
                            if (response.body().equalsIgnoreCase("1")) {
                                UserInfo userInfo = new UserInfo(user, birthday, hometown, phone);
                                saveUser(userInfo);
                                listener.onSuccess("Đăng kí thành công");
                            } else {
                                listener.onError("Đăng kí thất bại");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {

                    }
                });
    }

    private void saveUser(UserInfo userInfo) {
        ConfigJson configJson = new ConfigJson();
        SharedPrefs.getInstance().put(Constant.USER_INFO, configJson.setUser(userInfo));
    }
}
