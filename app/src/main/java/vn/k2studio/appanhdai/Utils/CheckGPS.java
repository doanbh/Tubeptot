package vn.k2studio.appanhdai.Utils;

import android.content.Context;
import android.location.LocationManager;

public class CheckGPS {
    public static boolean checkGpsStatus(Context context) {
        final LocationManager manager =
                (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //return false,set dialog turn on gps
            return false;
        }
        return true;
    }
}
