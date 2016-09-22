package com.vishalandroid.visdemo;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.vishalandroid.visdemo.adpter.SetListAdapter;
import com.vishalandroid.visdemo.dbpojo.AddData;
import com.vishalandroid.visdemo.extra.ApiClient;
import com.vishalandroid.visdemo.extra.DividerItemDecoration;
import com.vishalandroid.visdemo.model.setValue;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import io.realm.Realm;

/**
 * Created by vishal on 22/9/16.
 */
public class AddDataActivity extends AppCompatActivity implements View.OnClickListener, LocationListener {
    private EditText name, address, activityselect;
    private RadioGroup gender;
    private Button btnTakePhoto, btnLatLong, btnSubmit;
    private String st_Gender = "male";
    private String st_name;
    private String st_address;
    private double st_lat;
    private double st_long;
    private Realm mRealm;
    LocationManager locationManager;
    String provider;

    private static final int REQUEST_CAMERA = 0;
    private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
    public static final int MEDIA_TYPE_IMAGE = 1;
    private static final String IMAGE_DIRECTORY_NAME = "Photos";
    private Uri picUri;
    public static String timeStamp;
    private String image_url = "";
    private ArrayList<String> stringArrayList = new ArrayList<>();

    private SetListAdapter orderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddata);

        name = (EditText) findViewById(R.id.edt_name_add);
        address = (EditText) findViewById(R.id.edt_address_add);
        activityselect = (EditText) findViewById(R.id.edt_activity);

        activityselect.setOnClickListener(this);

        gender = (RadioGroup) findViewById(R.id.rg_gender);

        btnTakePhoto = (Button) findViewById(R.id.btntakephoto);
        btnLatLong = (Button) findViewById(R.id.btnlatlong);
        btnSubmit = (Button) findViewById(R.id.btnsubmit);

        btnSubmit.setOnClickListener(this);
        btnLatLong.setOnClickListener(this);
        btnTakePhoto.setOnClickListener(this);

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rb_male) {

                    st_Gender = "male";
                    ApiClient.showLog("gender male", st_Gender);
                } else {

                    st_Gender = "female";
                    ApiClient.showLog("gender  female", st_Gender);
                }
            }
        });


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnsubmit:

                if (!validateFirstName()) {
                    return;
                }

                if (image_url.equalsIgnoreCase("")){

                    Toast.makeText(AddDataActivity.this, "Please Take Photo after submit data", Toast.LENGTH_SHORT).show();

                    return;
                }


                st_name = name.getText().toString();
                st_address = address.getText().toString();

                SaveDataIntoDatabase(st_name, st_address, st_Gender, st_lat, st_long, image_url);

                Intent i = new Intent(AddDataActivity.this, HomeActivity.class);
                startActivity(i);
                finish();

                break;

            case R.id.btntakephoto:

                TakePhoto();

                break;

            case R.id.btnlatlong:

                getLatLong();

                break;

            case R.id.edt_activity:

                ShowAlertDialogActivity();

                break;
        }
    }

    private void ShowAlertDialogActivity() {


        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dailog_select_activity);
        dialog.show();


        stringArrayList.add("Cricket");
        stringArrayList.add("Basketball");
        stringArrayList.add("Swimming");
        stringArrayList.add("Shooting");
        stringArrayList.add("Music");

        RecyclerView recyclerView = (RecyclerView) dialog.findViewById(R.id.rec_product_list1);
        Button btnadd = (Button) dialog.findViewById(R.id.btn_save);


        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        orderListAdapter = new SetListAdapter(this, stringArrayList);
        recyclerView.setAdapter(orderListAdapter);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                dialog.dismiss();

                for (setValue aa : orderListAdapter.getAllData()) {

                    activityselect.append(aa.getName());
                    activityselect.append(",");

                }
            }
        });


    }

    private void TakePhoto() {


        if (ActivityCompat.checkSelfPermission(AddDataActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {

            //intent call direct

            Intent i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);

            File file = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            picUri = Uri.fromFile(file); // create
            i.putExtra(MediaStore.EXTRA_OUTPUT, picUri); // set the image file

            startActivityForResult(i, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);


        }

    }

    private void getLatLong() {

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        provider = locationManager.getBestProvider(criteria, false);

        if (provider != null && !provider.equals("")) {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            Location location = locationManager.getLastKnownLocation(provider);
            locationManager.requestLocationUpdates(provider, 20000, 1, this);

            if (location != null) {

                onLocationChanged(location);

            } else {

                Toast.makeText(AddDataActivity.this, "Location can't be retrived", Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText(AddDataActivity.this, "No Provider Found", Toast.LENGTH_SHORT).show();
        }
    }

    private void SaveDataIntoDatabase(String st_name, String st_address, String st_gender, double st_lat, double st_long, String image_url) {

        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        AddData addOrder = mRealm.createObject(AddData.class);

        addOrder.setName(st_name);
        addOrder.setGender(st_gender);
        addOrder.setImgurl(image_url);
        addOrder.setLat(st_lat);
        addOrder.setLongi(st_long);

        mRealm.commitTransaction();
        mRealm.close();

    }

    @Override
    public void onLocationChanged(Location location) {

        System.out.println("Latitude" + location.getLatitude());
        System.out.println("Longitude" + location.getLongitude());

        Toast.makeText(AddDataActivity.this, "Latitude " + location.getLatitude() + "\n" + "Longitude" + location.getLongitude(), Toast.LENGTH_SHORT).show();

        st_lat = location.getLatitude();
        st_long = location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    private void requestCameraPermission() {

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(AddDataActivity.this,
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);

        } else {
            // Camera permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    REQUEST_CAMERA);
        }
        // END_INCLUDE(camera_permission_request)
    }


    private static File getOutputMediaFile(int type) {

        // External sdcard location
        File mediaStorageDir = new File(
                Environment
                        .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                IMAGE_DIRECTORY_NAME);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
//                    MyApplication.getInstance().showLog(TAG, "Oops! Failed create " + IMAGE_DIRECTORY_NAME + " directory");
                return null;
            }
        }
        // Create a media file name
        timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
                Locale.getDefault()).format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {


            if (resultCode == -1) {

                // successfully captured the image

                Uri uri = picUri;
//                String captuepath = uri.getPath();

                image_url = String.valueOf(uri);


 //               ApiClient.showLog("camarea image path ", "" + captuepath);


            } else if (resultCode == 0) {


                // user cancelled Image capture
                Toast.makeText(AddDataActivity.this, "You cancelled image capture", Toast.LENGTH_SHORT).show();


            } else {


                // failed to capture image


                Toast.makeText(AddDataActivity.this,


                        "Sorry! Failed to capture image", Toast.LENGTH_SHORT)


                        .show();


            }


        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private boolean validateFirstName() {
        if (name.getText().toString().trim().isEmpty()) {
            name.setError(getString(R.string.err_msg_first_name));
            requestFocus(name);
            return false;
        } else {
            return true;
        }
    }



    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
