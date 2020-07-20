package com.idevelopstudio.doctorapp.authDoctorSpeciality;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.Helper;

import java.util.ArrayList;

public class SpecialityAdapter extends RecyclerView.Adapter<SpecialityAdapter.SpecialityViewHolder> {

    private ArrayList<Speciality> specialities;

    public SpecialityAdapter(ArrayList<Speciality> specialities){
        this.specialities = specialities;
    }

    @NonNull
    @Override
    public SpecialityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return SpecialityViewHolder.from(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull SpecialityViewHolder holder, int position) {
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

    public static class SpecialityViewHolder extends RecyclerView.ViewHolder {
        private ListItemDoctorSpecialityBinding binding;

        public SpecialityViewHolder(ListItemDoctorSpecialityBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public static SpecialityViewHolder from(ViewGroup parent){
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            ListItemDoctorSpecialityBinding binding = ListItemDoctorSpecialityBinding.inflate(layoutInflater, parent, false);
            return new SpecialityViewHolder(binding);
        }

        public void bind (Speciality speciality){
            binding.textView.setText(speciality.getTitle());
            binding.imageView.setImageDrawable(speciality.getImage());
            binding.cardLayout.setBackgroundResource(speciality.getBackgroundColor());
        }
    }


}
