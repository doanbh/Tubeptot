package vn.k2studio.appanhdai.fragment;

import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import vn.k2studio.appanhdai.R;
import vn.k2studio.appanhdai.Utils.ConfigJson;
import vn.k2studio.appanhdai.Utils.ConfigRetrofitApi;
import vn.k2studio.appanhdai.Utils.ConfigSpinner;
import vn.k2studio.appanhdai.Utils.Constant;
import vn.k2studio.appanhdai.Utils.InterfaceAPI;
import vn.k2studio.appanhdai.Utils.SharedPrefs;
import vn.k2studio.appanhdai.adapter.ListImageAdapter;
import vn.k2studio.appanhdai.adapter.SpinnerAdapter;
import vn.k2studio.appanhdai.model.UserInfo;

import static android.app.Activity.RESULT_OK;

public class CreateNewFragment extends BaseFragment
        implements ListImageAdapter.OnHandleClickImageListener {

    private static final int PICK_IMAGE_MULTIPLE = 1;
    @BindView(R.id.frm_create_new_spn_city)
    Spinner mFrmCreateNewSpnCity;
    @BindView(R.id.frm_create_new_txt_city)
    TextView mFrmCreateNewTxtCity;
    @BindView(R.id.ln_choose_city)
    LinearLayout mLnChooseCity;
    @BindView(R.id.frm_create_new_spn_district)
    Spinner mFrmCreateNewSpnDistrict;
    @BindView(R.id.frm_create_new_txt_district)
    TextView mFrmCreateNewTxtDistrict;
    @BindView(R.id.frm_create_new_edt_content)
    EditText mFrmCreateNewEdtContent;
    @BindView(R.id.frm_create_new_img_add)
    ImageView mFrmCreateNewImgAdd;
    @BindView(R.id.frm_create_new_rcl_list_image)
    RecyclerView mFrmCreateNewRclListImage;
    @BindView(R.id.frm_create_new_btn_post)
    Button mFrmCreateNewBtnPost;
    @BindView(R.id.frm_create_new_fab_post)
    FloatingActionButton mFrmCreateNewFabPost;
    @BindView(R.id.my_progess_bar)
    RelativeLayout mMyProgessBar;
    Unbinder unbinder;
    //    @BindView(R.id.ln_multi)
    //    LinearLayout pickedImageContainer;
    private ConfigSpinner mConfigSpinner;
    private ArrayList<String> listCity;
    private SpinnerAdapter adapterCity;
    private SpinnerAdapter adapterDistrict;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListImageAdapter mListImageAdapter;
    private List<Uri> mUriList;
    private String imageEncoded;
    private List<String> imagesEncodedList;
    private UserInfo mUserInfo;

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
        ConfigJson configJson = new ConfigJson();
        mUserInfo =
                configJson.getUser(SharedPrefs.getInstance().get(Constant.USER_INFO, String.class));
        mConfigSpinner = new ConfigSpinner(getActivity());
        listCity = new ArrayList<>();
        mUriList = new ArrayList<>();
        mConfigSpinner.setupSpinnerCity(mFrmCreateNewSpnCity, adapterCity, listCity);
        mFrmCreateNewSpnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mFrmCreateNewTxtCity.setText(mFrmCreateNewSpnCity.getSelectedItem().toString());
                mConfigSpinner.setupSpinnerDistrict(mFrmCreateNewSpnDistrict, adapterDistrict,
                        position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        mFrmCreateNewSpnDistrict.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position,
                            long id) {
                        mFrmCreateNewTxtDistrict.setText(
                                mFrmCreateNewSpnDistrict.getSelectedItem().toString());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
    }

    @OnClick({
            R.id.frm_create_new_txt_city, R.id.frm_create_new_txt_district,
            R.id.frm_create_new_img_add, R.id.frm_create_new_btn_post
    })
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.frm_create_new_txt_city:

                break;
            case R.id.frm_create_new_txt_district:

                break;
            case R.id.frm_create_new_img_add:
                Intent intent = new Intent();
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_MULTIPLE);
                break;
            case R.id.frm_create_new_btn_post:
                postNew();
                break;
        }
    }

    private void postNew() {
        mMyProgessBar.setVisibility(View.VISIBLE);
        String nameCity = listCity.get(mFrmCreateNewSpnCity.getSelectedItemPosition());
        String nameDistrict = mFrmCreateNewSpnDistrict.getSelectedItem().toString();
        String content = mFrmCreateNewEdtContent.getText().toString();
        String title = "abc";
        Retrofit retrofit = ConfigRetrofitApi.getInstance();
        retrofit.create(InterfaceAPI.class)
                .postNew(nameCity, nameDistrict, content, mUserInfo.getNumberPhone(), title,
                        parseListImageToBase64())
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.body() != null) {
                            if (response.body().equalsIgnoreCase("1")) {
                                Toast.makeText(getActivity(), "Đăng bài thành công",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getActivity(), "Đăng bài thất bại",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                        mMyProgessBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        mMyProgessBar.setVisibility(View.GONE);
                    }
                });
    }

    private ArrayList<String> parseListImageToBase64() {
        ArrayList<String> listImages = new ArrayList<>();
        for (Uri uri : mUriList) {
            InputStream imageStream = null;
            try {
                imageStream = getActivity().getContentResolver().openInputStream(uri);
                Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                String encodedImage = encodeImage(selectedImage);
                listImages.add(encodedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return listImages;
    }

    private String encodeImage(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            // When an Image is picked
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    imagesEncodedList = new ArrayList<String>();
                    if (data.getData() != null) {
                        //pick 1 image
                        Uri mImageUri = data.getData();
                        mUriList.add(mImageUri);
                        setUpListImage();
                    } else {
                        if (data.getClipData() != null) {
                            ClipData mClipData = data.getClipData();

                            Toast.makeText(getActivity(),
                                    "Bạn đã chọn " + (mClipData.getItemCount() > 1
                                            ? mClipData.getItemCount() + " ảnh"
                                            : mClipData.getItemCount() + " ảnh"),
                                    Toast.LENGTH_SHORT).show();
                            if (mClipData.getItemCount() < 50) {
                                if (mListImageAdapter == null) {
                                    addListImage(mClipData);
                                    setUpListImage();
                                } else {
                                    addListImage(mClipData);
                                    mListImageAdapter.notifyDataSetChanged();
                                }
                            } else {
                                Toast.makeText(getActivity(), "Bạn chỉ được chọn tối đa 50 ảnh",
                                        Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getActivity(), "Bạn không chọn bất kì ảnh nào",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Lỗi: Không thêm được ảnh " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void addListImage(ClipData mClipData) {
        int pickedImageCount;

        for (pickedImageCount = 0; pickedImageCount < mClipData.getItemCount();
                pickedImageCount++) {
            mUriList.add(mClipData.getItemAt(pickedImageCount).getUri());
        }
    }

    private void setUpListImage() {
        mFrmCreateNewRclListImage.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mListImageAdapter = new ListImageAdapter(getActivity(), mUriList);
        //                            mListImageAdapter.setHasStableIds(true);
        mFrmCreateNewRclListImage.setLayoutManager(mLayoutManager);
        mFrmCreateNewRclListImage.setAdapter(mListImageAdapter);
        mListImageAdapter.setListener(this);
    }

    @Override
    public void onCancelImage(int position) {
        mUriList.remove(position);
        mListImageAdapter.notifyItemRemoved(position);
        mListImageAdapter.notifyItemRangeChanged(position, mUriList.size());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.frm_create_new_fab_post)
    public void onViewClicked() {
        postNew();
    }
}
