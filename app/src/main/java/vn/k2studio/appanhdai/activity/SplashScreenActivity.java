package vn.k2studio.appanhdai.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.Utils.CheckGPS;
import vn.k2studio.appanhdai.Utils.CheckInternet;
import vn.k2studio.appanhdai.Utils.ConfigCity;
import vn.k2studio.appanhdai.Utils.Constant;
import vn.k2studio.appanhdai.Utils.SharedPrefs;

public class SplashScreenActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_splashscreen;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        checkApp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        checkApp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkApp();
    }

    private void checkApp() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!CheckInternet.haveNetworkConnection(SplashScreenActivity.this)) {
                    showDialogInternet();
                } else if (!CheckGPS.checkGpsStatus(getApplicationContext())) {
                    setDialogGPS();
                } else {
                    startActivity(RegisterActivity.class);
                    finish();
                }
            }
        }, 1000);
        if (!SharedPrefs.getInstance().get(Constant.LOAD_APP, Boolean.class)) {
            goToConfigCity();
        }
    }

    private void goToConfigCity() {
        ConfigCity configCity = (ConfigCity) new ConfigCity(this) {
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                SharedPrefs.getInstance().put(Constant.LOAD_APP, true);
            }
        }.execute();
    }

    private void showDialogInternet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreenActivity.this);
        builder.setTitle("K2 Studio !")
                .setCancelable(true)
                .setMessage("Vui lòng kiểm tra kết nối Wifi hoặc 3G")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .show();
    }

    private void setDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("K2 Studio !")
                .setMessage("Bạn chưa bật dịch vụ định vị")
                .setCancelable(true)
                .setPositiveButton("Bật ngay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .show();
    }
}
