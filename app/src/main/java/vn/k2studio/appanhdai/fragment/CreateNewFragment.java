package vn.k2studio.appanhdai.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
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
import com.bumptech.glide.Glide;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import static vn.k2studio.appanhdai.Utils.Constant.TAKE_PHOTO_CODE;

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
    private String dir;
    private Uri outputFileUri;
    String mCurrentPhotoPath;
    private static final int PERMISSION_REQUEST_CAMERA = 0;
    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

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
                checkPermission();
                creatDialogPickImage();
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
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        switch (requestCode) {
            case TAKE_PHOTO_CODE:
                if (resultCode == RESULT_OK) {
                    galleryAddPic();
                    //                    Log.d("CameraDemo", "Pic saved");
                    //                    Bundle extras = data.getExtras();
                    //                    ((CompleteCvEmployerActivity) getActivity()).setImage(
                    //                            String.valueOf(outputFileUri));
                    //                        Bitmap help1 = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),outputFileUri);
                    Log.e("URI", outputFileUri.toString());
                    //
                    //                    Bitmap bmp = (Bitmap) extras.get("data");
                }
                break;
        }
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

    private void creatDialogPickImage() {
        LayoutInflater layoutInflater = getLayoutInflater();
        View dialog = layoutInflater.inflate(R.layout.dialog_pick_image, null, false);
        Button btnPickImage = dialog.findViewById(R.id.btn_pick_image);
        Button btnTakePhoto = dialog.findViewById(R.id.btn_take_photo);
        final AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setView(dialog);
        alert.setCancelable(true);
        final AlertDialog dialog1 = alert.create();
        dialog1.show();
        btnPickImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                }
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                        PICK_IMAGE_MULTIPLE);
                dialog1.dismiss();
            }
        });
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                getActivity().getPackageName() + ".share", photoFile);
                        outputFileUri = photoURI;
                        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cameraIntent, TAKE_PHOTO_CODE);
                    }
                }
                dialog1.dismiss();
            }
        });
    }

    private void takePhoto() {
        dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                + "/picFolder/";
        File newdir = new File(dir);
        newdir.mkdirs();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mUriList.add(contentUri);
        setUpListImage();
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
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

    public boolean checkPermissionREAD_EXTERNAL_STORAGE() {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", getActivity(),
                            Manifest.permission.READ_EXTERNAL_STORAGE);
                } else {
                    ActivityCompat.requestPermissions(getActivity(),
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                            MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context, final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions((Activity) context, new String[] { permission },
                        MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            }
        });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    private void checkPermission() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            // Request the permission
        } else {
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getActivity(),
                    new String[] { Manifest.permission.CAMERA }, PERMISSION_REQUEST_CAMERA);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[],
            int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CAMERA: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}
