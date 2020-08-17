package com.idevelopstudio.doctorapp.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public abstract class MyRecyclerViewAdapter<T, D> extends RecyclerView.Adapter<MyRecyclerViewAdapter<T, D>.MyViewHolder> {

    private ArrayList<D> dataList;
    private int resourceId;

    public abstract void bind (T dataBinding, D item);

    public abstract void onItemPressed(View view, D item, int position);

    public MyRecyclerViewAdapter(ArrayList<D> dataList, int resourceId) {
        this.dataList = dataList;
        this.resourceId = resourceId;
    }

    public void setItemList(ArrayList<D> list){
        this.dataList = list;
        notifyDataSetChanged();
    }

    public ArrayList<D> getItemList(){
        return dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding dataBinding = DataBindingUtil.inflate(layoutInflater, resourceId, parent, false);
        return new MyViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        bind(holder.binding, dataList.get(position));
        holder.itemView.setOnClickListener(v -> {
            onItemPressed(v, dataList.get(position), position);
        });
    }

    @Override
    public int getItemCount() {
        return dataList != null && dataList.size() > 0 ? dataList.size() : 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private T binding;

        MyViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = (T) binding;
        }
    }

}
