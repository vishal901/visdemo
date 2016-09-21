package com.vishalandroid.visdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.vishalandroid.visdemo.dbpojo.AddData;
import com.vishalandroid.visdemo.extra.ApiClient;
import com.vishalandroid.visdemo.extra.ApiInterface;
import com.vishalandroid.visdemo.response.GetData;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText username,password;
    private Button login;
    private ProgressBar  progressBar;
    private Realm mRealm;
    private String uid,name,gender,image_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRealm = Realm.getDefaultInstance();

        username = (EditText) findViewById(R.id.edt_username);
        password = (EditText) findViewById(R.id.edt_password);
        login = (Button) findViewById(R.id.btnlogin);

        progressBar = (ProgressBar) findViewById(R.id.pb_load);

        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {



        netwrokCall();

    }

    private void netwrokCall() {

        progressBar.setVisibility(View.VISIBLE);

        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<GetData> getDataCall = apiInterface.findus();

        getDataCall.enqueue(new Callback<GetData>() {
            @Override
            public void onResponse(Call<GetData> call, Response<GetData> response) {

                ApiClient.showLog("code",""+response.code());

                if (response.code() == 200){

                    progressBar.setVisibility(View.GONE);

                    List<GetData.LabelsEntity> labelsEntities = response.body().getData();

                    for (GetData.LabelsEntity  data : labelsEntities){

                        uid = data.getId();
                        name = data.getName();
                        gender = data.getGender();
                        image_url = data.getPhotoId();

                        savedataintodatabase(uid,name,gender,image_url);
                    }


                    Intent i =new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);


                }else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(MainActivity.this, "No Response Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetData> call, Throwable t) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(MainActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void savedataintodatabase(String uid, String name, String gender, String image_url) {
        mRealm = Realm.getDefaultInstance();
        mRealm.beginTransaction();

        AddData addOrder = mRealm.createObject(AddData.class);

        addOrder.setId(uid);
        addOrder.setName(name);
        addOrder.setGender(gender);
        addOrder.setImgurl(image_url);

        mRealm.commitTransaction();
        mRealm.close();


    }
}