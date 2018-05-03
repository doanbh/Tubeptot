package vn.k2studio.appanhdai.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.text.ParseException;
import vn.k2studio.appanhdai.R;

/**
 * Created by Dell Precision on 01/25/2018.
 */

public abstract class BaseFragment extends Fragment {
    private View mRoot;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(layoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRoot);
        try {
            init(mRoot, savedInstanceState);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mRoot;
    }

    protected abstract int layoutId();

    protected abstract void init(View mRoot, Bundle savedInstanceState) throws ParseException;

    protected void startActivityFragment(Class<?> c) {
        Intent intent = new Intent(mRoot.getContext(), c);
        startActivity(intent);
    }

    protected void hideSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager softKeyboard = (InputMethodManager) getActivity().getSystemService(
                    Context.INPUT_METHOD_SERVICE);
            softKeyboard.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                    0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        // unbind the view to free some memory
        mUnbinder.unbind();
    }
}
