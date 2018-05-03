package vn.k2studio.appanhdai.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import vn.k2studio.appanhdai.R;

public class RegisterActivity extends BaseActivity {
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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
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
                startActivity(HomeActivity.class);
                break;
        }
    }
}
