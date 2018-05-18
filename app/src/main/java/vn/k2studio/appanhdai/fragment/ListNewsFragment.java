package vn.k2studio.appanhdai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
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

import static vn.k2studio.appanhdai.fragment.ListNewsFragment.getTimeAgoClass.getTimeAgo;

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
                                mItemNews.add(new ItemNew(mUserInfo.getName(), R.drawable.user,
                                        makeTimeAgo(itemNew.getNew_time()), itemNew.getNew_title(),
                                        itemNew.getNew_content(),
                                        (ArrayList<String>) itemNew.getNew_list_image()));
                            }
                            mLayoutManager = new LinearLayoutManager(getActivity(),
                                    LinearLayoutManager.VERTICAL, false);
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

    public static class getTimeAgoClass {
        private static final int SECOND_MILLIS = 1000;
        private static final int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        private static final int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        private static final int DAY_MILLIS = 24 * HOUR_MILLIS;

        public static String getTimeAgo(long time) {
            if (time < 1000000000000L) {
                // if timestamp given in seconds, convert to millis
                time *= 1000;
            }

            long now = System.currentTimeMillis();
            ;
            if (time > now || time <= 0) {
                return null;
            }

            // TODO: localize
            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "bây giờ";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "một phút trước";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " phút trước";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "một giờ trước";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " giờ trước";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "ngày hôm qua";
            } else if (diff < 30 * DAY_MILLIS) {
                return diff / DAY_MILLIS + " ngày trước";
            } else {
                int div = (int) (diff / DAY_MILLIS) / 30;
                if (div == 0) {
                    return diff / DAY_MILLIS + " ngày trước";
                } else {
                    return div + " tháng trước";
                }
            }
        }
    }

    private String makeTimeAgo(String timeInt) {
        long time = Long.parseLong(timeInt) * 1000;
        String sAgoTime = getTimeAgo(time);
        return sAgoTime;
    }
}
