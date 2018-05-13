package vn.k2studio.appanhdai.presenter;

/**
 * Created by Windows 10 Now on 1/22/2018.
 */

public interface RegisterInterator {
    public void register(String user, String phone, String birthday, String hometown,
            OnRegisterFinishedListener listener);
}
