package vn.k2studio.appanhdai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.text.ParseException;
import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.Utils.ConfigJson;
import vn.k2studio.appanhdai.Utils.ConfigRetrofitApi;
import vn.k2studio.appanhdai.Utils.Constant;
import vn.k2studio.appanhdai.Utils.InterfaceAPI;
import vn.k2studio.appanhdai.Utils.SharedPrefs;
import vn.k2studio.appanhdai.adapter.ListNewsAdapter;
import vn.k2studio.appanhdai.model.ItemNew;
import vn.k2studio.appanhdai.model.ItemNewFromApi;
import vn.k2studio.appanhdai.model.UserInfo;

public class ListNewsFragment extends BaseFragment {
    @BindView(R.id.frm_list_news_rcl)
    RecyclerView mFrmListNewsRcl;
    @BindView(R.id.my_progess_bar)
    RelativeLayout mMyProgessBar;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListNewsAdapter mListNewsAdapter;
    private ArrayList<ItemNew> mItemNews;
    private UserInfo mUserInfo;

    public static ListNewsFragment newInstance() {
        ListNewsFragment listNewsFragment = new ListNewsFragment();
        Bundle args = new Bundle();
        listNewsFragment.setArguments(args);
        return listNewsFragment;
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_list_news;
    }

    @Override
    protected void init(View mRoot, Bundle savedInstanceState) throws ParseException {
        mMyProgessBar.setVisibility(View.VISIBLE);
        ConfigJson configJson = new ConfigJson();
        mUserInfo =
                configJson.getUser(SharedPrefs.getInstance().get(Constant.USER_INFO, String.class));
        makeDataForList();
    }

    private void makeDataForList() {
        mItemNews = new ArrayList<>();
        Retrofit retrofit = ConfigRetrofitApi.getInstance();
        retrofit.create(InterfaceAPI.class)
                .getListNews(mUserInfo.getNumberPhone())
                .enqueue(new Callback<ArrayList<ItemNewFromApi>>() {
                    @Override
                    public void onResponse(Call<ArrayList<ItemNewFromApi>> call,
                            Response<ArrayList<ItemNewFromApi>> response) {
                        if (response.body() != null) {
                            for (ItemNewFromApi itemNew : response.body()) {
                                mItemNews.add(new ItemNew(mUserInfo.getName(), R.drawable.doan,
                                        itemNew.getNew_time(), itemNew.getNew_title(),
                                        itemNew.getNew_content(),
                                        (ArrayList<String>) itemNew.getNew_list_image()));
                            }
                            mLayoutManager =
                                    new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                            mListNewsAdapter = new ListNewsAdapter(mItemNews, getActivity());
                            mFrmListNewsRcl.setLayoutManager(mLayoutManager);
                            mFrmListNewsRcl.setAdapter(mListNewsAdapter);
                            mMyProgessBar.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ArrayList<ItemNewFromApi>> call, Throwable t) {
                        mMyProgessBar.setVisibility(View.GONE);
                    }
                });
        //        mItemNews.add(new ItemNew("Đoàn Bùi", R.drawable.doan, "3 giây trước",
        //                "Lorem Ipsum - All the facts - Lipsum generator",
        //                "What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown ...",
        //                setDataListImage(3)));
        //        mItemNews.add(new ItemNew("Đoàn Bùi", R.drawable.doan, "3 giây trước",
        //                "Lorem Ipsum - All the facts - Lipsum generator",
        //                "What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown ...",
        //                setDataListImage(8)));
    }

    private ArrayList<Integer> setDataListImage(int countImage) {
        ArrayList<Integer> listImage = new ArrayList<>();
        for (int i = 0; i < countImage; i++) {
            listImage.add(R.drawable.doan);
        }
        return listImage;
    }
}
