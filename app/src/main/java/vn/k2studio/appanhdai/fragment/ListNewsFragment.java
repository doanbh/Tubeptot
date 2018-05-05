package vn.k2studio.appanhdai.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import java.text.ParseException;
import java.util.ArrayList;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.adapter.ListNewsAdapter;
import vn.k2studio.appanhdai.model.ItemNew;

public class ListNewsFragment extends BaseFragment {
    @BindView(R.id.frm_list_news_rcl)
    RecyclerView mFrmListNewsRcl;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListNewsAdapter mListNewsAdapter;
    private ArrayList<ItemNew> mItemNews;

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
        makeDataForList();
        mLayoutManager =
                new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mListNewsAdapter = new ListNewsAdapter(mItemNews, getActivity());
        mFrmListNewsRcl.setLayoutManager(mLayoutManager);
        mFrmListNewsRcl.setAdapter(mListNewsAdapter);
    }

    private void makeDataForList() {
        mItemNews = new ArrayList<>();
        mItemNews.add(new ItemNew("Đoàn Bùi", R.drawable.doan, "3 giây trước",
                "Lorem Ipsum - All the facts - Lipsum generator",
                "What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown ...",
                setDataListImage(3)));
        mItemNews.add(new ItemNew("Đoàn Bùi", R.drawable.doan, "3 giây trước",
                "Lorem Ipsum - All the facts - Lipsum generator",
                "What is Lorem Ipsum? Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown ...",
                setDataListImage(8)));
    }

    private ArrayList<Integer> setDataListImage(int countImage) {
        ArrayList<Integer> listImage = new ArrayList<>();
        for (int i = 0; i < countImage; i++) {
            listImage.add(R.drawable.doan);
        }
        return listImage;
    }
}
