package com.vishalandroid.visdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.vishalandroid.visdemo.adpter.OrderListAdapter;
import com.vishalandroid.visdemo.dbpojo.AddData;
import com.vishalandroid.visdemo.extra.DividerItemDecoration;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vishal on 22/9/16.
 */
public class HomeActivity extends AppCompatActivity {

    private RecyclerView  recyclerView;
    private Realm mRealm;
    private RealmResults<AddData> addOrderList;
    private OrderListAdapter orderListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);

        mRealm = Realm.getDefaultInstance();

        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        addOrderList = mRealm.where(AddData.class).findAll();
        orderListAdapter = new OrderListAdapter(this, addOrderList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setAdapter(orderListAdapter);
    }
}
