package vn.k2studio.appanhdai.presenter;

import vn.k2studio.appanhdai.view.ProgessView;


public class RegisterPresenterImpl implements RegisterPresenter, OnRegisterFinishedListener {
    private ProgessView mProgessView;
    private RegisterView mRegisterView;
    private RegisterInterator mInterator;

    public RegisterPresenterImpl(ProgessView progessView, RegisterView registerView) {
        mProgessView = progessView;
        mRegisterView = registerView;
        mInterator = new RegisterInteratorImpl();
    }

    @Override
    public void onSuccess(String sMessage) {
        mProgessView.hideProgess();
        mRegisterView.navigationToHome(sMessage);
    }

    @Override
    public void register(String user, String phone, String birthday, String hometown) {
        mInterator.register(user, phone, birthday, hometown, this);
                mProgessView.showProgess();
    }
}
