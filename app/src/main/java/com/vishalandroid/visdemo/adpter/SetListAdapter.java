package com.vishalandroid.visdemo.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.vishalandroid.visdemo.R;

import java.util.ArrayList;

/**
 * Created by vishal on 22/9/16.
 */
public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> data;

    public SetListAdapter(Context mContext, ArrayList<String> stringArrayList) {
        this.mContext = mContext;
        this.data= stringArrayList;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView  name;
        private CheckBox ck;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.txt_name_set);
            ck = (CheckBox) view.findViewById(R.id.cb_select);



        }
    }




    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_view_raw, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {



    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }
}