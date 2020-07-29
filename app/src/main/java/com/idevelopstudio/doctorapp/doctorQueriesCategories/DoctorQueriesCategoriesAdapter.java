package com.idevelopstudio.doctorapp.doctorQueriesCategories;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.idevelopstudio.doctorapp.databinding.ListItemDoctorQueriesCategoryBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.Helper;

import java.util.ArrayList;

public class DoctorQueriesCategoriesAdapter extends RecyclerView.Adapter<DoctorQueriesCategoriesAdapter.QueriesCategoriesViewHolder> {

    private ArrayList<Speciality> specialities;

    public DoctorQueriesCategoriesAdapter(ArrayList<Speciality> specialities){
        this.specialities = specialities;
    }

    @NonNull
    @Override
    public QueriesCategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return QueriesCategoriesViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull QueriesCategoriesViewHolder holder, int position) {
        holder.bind(specialities.get(position));
        holder.itemView.setOnClickListener(v -> {
            specialities.get(position).toggle();
            Speciality speciality = specialities.get(position);
            if (!speciality.isSelected()){
                holder.binding.cardLayout.setBackgroundResource(speciality.getBackgroundColor());
            }else{
                holder.binding.cardLayout.setBackgroundResource(Helper.getSelectedBgByColor(speciality.getBackgroundColor()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialities != null ? specialities.size() : 0;
    }

    public static class QueriesCategoriesViewHolder extends RecyclerView.ViewHolder {
        private ListItemDoctorQueriesCategoryBinding binding;

        public QueriesCategoriesViewHolder(ListItemDoctorQueriesCategoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static QueriesCategoriesViewHolder from(ViewGroup parent){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ListItemDoctorQueriesCategoryBinding binding = ListItemDoctorQueriesCategoryBinding.inflate(layoutInflater, parent, false);
            return new QueriesCategoriesViewHolder(binding);
        }

        public void bind (Speciality speciality){
            binding.textView.setText(speciality.getTitle() + " has 5 unanswered queries");
            binding.imageView.setImageDrawable(speciality.getImage());
            binding.cardLayout.setBackgroundResource(speciality.getBackgroundColor());
        }
    }


}
