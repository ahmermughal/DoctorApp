package com.idevelopstudio.doctorapp.authDoctorSpeciality;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class AuthDoctorSpeciality extends Fragment {

    private FragmentAuthDoctorSpecialityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthDoctorSpecialityBinding.inflate(getLayoutInflater());

        SpecialityAdapter adapter = new SpecialityAdapter(getSpecialities());
        binding.recyclerViewSpecialities.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerViewSpecialities.setAdapter(new MyRecyclerViewAdapter<ListItemDoctorSpecialityBinding, Speciality>(getSpecialities(), R.layout.list_item_doctor_speciality){
            @Override
            public void bind(ListItemDoctorSpecialityBinding dataBinding, Speciality item) {
                dataBinding.setSpeciality(item);
            }

            @Override
            public void onItemPressed(View view, Speciality item, int position) {

            }
        });
        return binding.getRoot();
    }

    private ArrayList<Speciality> getSpecialities(){
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality("Anesthesiology", getResources().getDrawable(R.drawable.syringe), R.color.pastelBlue));
        specialities.add(new Speciality("Dentistry", getResources().getDrawable(R.drawable.tooth), R.color.pastelGreen));
        specialities.add(new Speciality("ENT", getResources().getDrawable(R.drawable.ear), R.color.pastelPink));
        specialities.add(new Speciality("Eye", getResources().getDrawable(R.drawable.eye), R.color.pastelSoftPink));
        specialities.add(new Speciality("Obstetrics & Gynaecology", getResources().getDrawable(R.drawable.gynaecology), R.color.pastelYellow));
        specialities.add(new Speciality("Medicine", getResources().getDrawable(R.drawable.pill), R.color.pastelPurple));
        specialities.add(new Speciality("Paediatrics", getResources().getDrawable(R.drawable.toys), R.color.pastelBabyBlue));
        specialities.add(new Speciality("Surgery", getResources().getDrawable(R.drawable.surgery), R.color.pastelLightPurple));
        specialities.add(new Speciality("Others", getResources().getDrawable(R.drawable.doctor_other), R.color.pastelMaroon));


        return specialities;
    }
}