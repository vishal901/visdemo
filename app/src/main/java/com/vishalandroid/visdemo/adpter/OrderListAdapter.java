package com.vishalandroid.visdemo.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;
import com.vishalandroid.visdemo.R;
import com.vishalandroid.visdemo.dbpojo.AddData;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by vishal on 22/9/16.
 */
public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.MyViewHolder> {

    private Context mContext;
    private RealmResults<AddData> addOrderRealmResults = null;
    private Realm mRealm;
    private AddData addOrder;




    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView u_id, name, gender;
        private ImageView imgpic;

        public MyViewHolder(View view) {
            super(view);
            u_id = (TextView) view.findViewById(R.id.txt_id);
            name = (TextView) view.findViewById(R.id.txt_name);
            gender = (TextView) view.findViewById(R.id.txt_gender);
            imgpic = (ImageView) view.findViewById(R.id.img_pic);


        }
    }


    public OrderListAdapter(Context mContext, RealmResults<AddData> addOrderRealmResults) {
        this.mContext = mContext;
        this.addOrderRealmResults = addOrderRealmResults;
        mRealm = Realm.getDefaultInstance();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_raw, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        addOrder = addOrderRealmResults.get(position);

        //  AddContact results = mRealm.where(AddContact.class).findFirst();
        holder.u_id.setText(String.valueOf(position+1));
        holder.name.setText(addOrder.getName());
        holder.gender.setText(addOrder.getGender());

        Picasso picasso = new Picasso.Builder(mContext)
                .memoryCache(new LruCache(24000))
                .build();

        picasso.with(mContext)
                .load(addOrder.getImgurl())
                .placeholder(R.mipmap.ic_launcher)
                .resize(50,50)
//                .error(Your Drawable Resource)         //this is also optional if some error has occurred in downloading the image this image would be displayed
                .into(holder.imgpic);


    }

    @Override
    public int getItemCount() {
        return (null != addOrderRealmResults ? addOrderRealmResults.size() : 0);
    }
}