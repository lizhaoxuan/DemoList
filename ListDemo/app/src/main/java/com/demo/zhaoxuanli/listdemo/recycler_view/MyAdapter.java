package com.demo.zhaoxuanli.listdemo.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.demo.zhaoxuanli.listdemo.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by zhaoxuan.li on 2015/10/12.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    private Context context;
    private List<ItemValue> myDatas;
    private ItemClickListener itemClickListener;

    public MyAdapter(Context context ,List myDatas){
        this.context = context;
        this.myDatas = myDatas;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_recycle_view, parent,
                false),itemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        holder.titleText.setText(myDatas.get(position).getTitle());
        holder.explainText.setText(myDatas.get(position).getExplain());
    }

    @Override
    public int getItemCount()
    {
        return myDatas.size();
    }


    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        protected TextView titleText;
        protected TextView explainText;
        protected ItemClickListener itemClickListener;
        public MyViewHolder(View view,ItemClickListener itemClickListener)
        {
            super(view);
            titleText = (TextView) view.findViewById(R.id.titleText);
            explainText = (TextView) view.findViewById(R.id.explainText);
            this.itemClickListener = itemClickListener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener != null)
                itemClickListener.onItemClick(v,getPosition());
        }
    }

    public interface ItemClickListener{
        void onItemClick(View view , int postion);
    }
}
