package vn.k2studio.appanhdai.activity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by K2 studio
 */

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
        initVariables(savedInstanceState);
    }

    protected abstract int getLayoutId();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void initVariables(Bundle savedInstanceState);

    protected void startActivity(Class<?> c) {
        Intent intent = new Intent(this, c);
        startActivity(intent);
    }

    protected void hiddenInputType() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
