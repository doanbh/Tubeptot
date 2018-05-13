package vn.k2studio.appanhdai.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.presenter.RegisterPresenter;
import vn.k2studio.appanhdai.presenter.RegisterPresenterImpl;
import vn.k2studio.appanhdai.presenter.RegisterView;
import vn.k2studio.appanhdai.view.ProgessView;

public class RegisterActivity extends BaseActivity implements ProgessView, RegisterView {
    @BindView(R.id.register_txt_user_name)
    EditText mRegisterTxtUserName;
    @BindView(R.id.register_txt_birth_day)
    TextView mRegisterTxtBirthDay;
    @BindView(R.id.register_txt_home_town)
    EditText mRegisterTxtHomeTown;
    @BindView(R.id.register_txt_number_phone)
    EditText mRegisterTxtNumberPhone;
    @BindView(R.id.register_btn)
    Button mRegisterBtn;
    @BindView(R.id.my_progess_bar)
    RelativeLayout mMyProgessBar;
    private Calendar myCalendar;
    private DatePickerDialog.OnDateSetListener date;
    private RegisterPresenter mRegisterPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mRegisterPresenter = new RegisterPresenterImpl(this, this);
    }

    @Override
    protected void initVariables(Bundle savedInstanceState) {
        setDialogPickDate();
    }

    private void setDialogPickDate() {
        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        mRegisterTxtBirthDay.setText(sdf.format(myCalendar.getTime()));
    }

    @OnClick({ R.id.register_txt_birth_day, R.id.register_btn })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_txt_birth_day:
                new DatePickerDialog(RegisterActivity.this, date, 1990,
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                hideSoftKeyboard(RegisterActivity.this);
                break;
            case R.id.register_btn:
//                checkRegister();
                hideSoftKeyboard(RegisterActivity.this);
                startActivity(HomeActivity.class);
                break;
        }
    }

    private void checkRegister() {
        String username = mRegisterTxtUserName.getText().toString();
        String phone = mRegisterTxtNumberPhone.getText().toString();
        String birthday = mRegisterTxtBirthDay.getText().toString();
        String hometown = mRegisterTxtHomeTown.getText().toString();
        if (username.equals("") || username == null) {
            mRegisterTxtUserName.setError("Vui lòng nhập họ tên");
        } else if (phone.equals("") || phone == null) {
            mRegisterTxtNumberPhone.setError("Vui lòng nhập số điện thoại");
        } else if (birthday.equals("") || birthday == null) {
            mRegisterTxtBirthDay.setError("Vui lòng chọn ngày sinh");
        } else if (hometown.equals("") || hometown == null) {
            mRegisterTxtHomeTown.setError("Vui lòng chọn quê quán");
        } else {
            mRegisterPresenter.register(username, phone, birthday, hometown);
        }
    }

    @Override
    public void showProgess() {
        mMyProgessBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgess() {
        mMyProgessBar.setVisibility(View.GONE);
    }

    @Override
    public void navigationToHome(String sMessage) {
        Toast.makeText(this, sMessage, Toast.LENGTH_LONG).show();
    }
}
