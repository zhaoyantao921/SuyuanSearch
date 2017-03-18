package com.shou.zhao.suyuansearch.makeorder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.shou.zhao.suyuansearch.R;
import com.shou.zhao.suyuansearch.dao.Type;
import com.shou.zhao.suyuansearch.net.Config;

import java.util.List;

/**
 * Created by haohao on 2017/3/1.
 */

public class TypeAdapter  extends RecyclerView.Adapter<TypeAdapter.ViewHolder> {
    private List<Type> typelist;
    private Context mContext;
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.store_list_item_layout,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*需要typeID*/
                Intent intent=new Intent(mContext,OrderActivity.class);
                typelist.get(holder.getLayoutPosition()).getTypeid();
                Log.d("选择的种类编号：",typelist.get(holder.getLayoutPosition()).getTypeid());
                Log.d("选择的种类名称：",typelist.get(holder.getLayoutPosition()).getTypename());
                Log.d("选择的种类单价：", String.valueOf(typelist.get(holder.getLayoutPosition()).getPricekg()));
                intent.putExtra("typeID",typelist.get(holder.getLayoutPosition()).getTypeid());
                intent.putExtra("typeName",typelist.get(holder.getLayoutPosition()).getTypename());
                intent.putExtra("typePrice",typelist.get(holder.getLayoutPosition()).getPricekg());//float 类型
                intent.putExtra("typePicture",typelist.get(holder.getLayoutPosition()).getTypepicture());//float 类型
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Type type=typelist.get(position);
        holder.typeName.setText(type.getTypename());
        holder.typeInfo.setText(type.getIntroduce());
        /*是否热卖*/

       /* if(type.getIs_hot()==1){
            holder.isHotImage.setVisibility(View.VISIBLE);
        }else {
            holder.isHotImage.setVisibility(View.VISIBLE);
        }*/
        holder.isHotImage.setVisibility(View.VISIBLE);

        Log.d("图片地址：",Config.API_GET_IMAGE+type.getTypepicture());
        Glide.with(mContext).load(Config.API_GET_IMAGE+type.getTypepicture())
                .thumbnail(0.1f).error(R.drawable.error_pic).into(holder.typeImage);
    }

    @Override
    public int getItemCount() {
        return typelist.size();
    }

    static  class  ViewHolder extends RecyclerView.ViewHolder{
        TextView typeName;
        TextView typeInfo;
        ImageView typeImage;
        ImageView  isHotImage;
        public ViewHolder(View itemView) {
            super(itemView);
            typeName=(TextView)itemView.findViewById(R.id.item_name);
            typeInfo=(TextView)itemView.findViewById(R.id.item_info);
            typeImage=(ImageView) itemView.findViewById(R.id.item_image);
            isHotImage=(ImageView)itemView.findViewById(R.id.im_id_Hot);
        }
    }

    public TypeAdapter(Context mContext ,List<Type> typelist) {
        this.typelist = typelist;
        this.mContext=mContext;
    }



}
