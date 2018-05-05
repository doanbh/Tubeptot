package vn.k2studio.appanhdai.fragment;

import android.os.Bundle;
import android.view.View;
import java.text.ParseException;
import vn.k2studio.appanhdai.R;

public class CreateNewFragment extends BaseFragment {

    public static CreateNewFragment newInstance() {
        CreateNewFragment createNewFragment = new CreateNewFragment();
        Bundle args = new Bundle();
        createNewFragment.setArguments(args);
        return createNewFragment;
    }
    @Override
    protected int layoutId() {
        return R.layout.fragment_create_new;
    }

    @Override
    protected void init(View mRoot, Bundle savedInstanceState) throws ParseException {

    }
}
