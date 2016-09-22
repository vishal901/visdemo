package com.vishalandroid.visdemo.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.vishalandroid.visdemo.R;
import com.vishalandroid.visdemo.extra.ApiClient;
import com.vishalandroid.visdemo.model.setValue;

import java.util.ArrayList;

/**
 * Created by vishal on 22/9/16.
 */
public class SetListAdapter extends RecyclerView.Adapter<SetListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<String> data;
    private ArrayList<setValue> setValueArrayList = new ArrayList<>();

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
                .inflate(R.layout.set_layout_activity, parent, false);

        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        holder.name.setText(data.get(position));

        holder.ck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {

                    setValue ailment1 = new setValue();
                    ailment1.setName(data.get(position));

                    setValueArrayList.add(ailment1);

                    for (setValue s : setValueArrayList) {

                        ApiClient.showLog("data", s.getName());
                    }
                } else {

                    setValueArrayList.remove(position);

                    for (setValue s : setValueArrayList) {

                        ApiClient.showLog("data", s.getName());
                    }

                }
            }
        });

    }

    public ArrayList<setValue> getAllData(){
        return setValueArrayList;
    }

    @Override
    public int getItemCount() {
        return (null != data ? data.size() : 0);
    }
}