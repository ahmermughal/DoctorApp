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
import com.idevelopstudio.doctorapp.models.Speciality;

import java.util.ArrayList;


public class AuthDoctorSpeciality extends Fragment {

    private FragmentAuthDoctorSpecialityBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthDoctorSpecialityBinding.inflate(getLayoutInflater());

        SpecialityAdapter adapter = new SpecialityAdapter(getSpecialities());
        binding.recyclerViewSpecialities.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.recyclerViewSpecialities.setAdapter(adapter);
        return binding.getRoot();
    }

    private ArrayList<Speciality> getSpecialities(){
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality("Anesthesiology", getResources().getDrawable(R.drawable.tooth), R.color.pastelBlue));
        specialities.add(new Speciality("Dentistry", getResources().getDrawable(R.drawable.tooth), R.color.pastelGreen));
        specialities.add(new Speciality("ENT", getResources().getDrawable(R.drawable.tooth), R.color.pastelPink));
        specialities.add(new Speciality("Eye", getResources().getDrawable(R.drawable.covid), R.color.pastelSoftPink));
        specialities.add(new Speciality("Obstetrics & Gynaecology", getResources().getDrawable(R.drawable.syringe), R.color.pastelYellow));
        specialities.add(new Speciality("Anesthesiology", getResources().getDrawable(R.drawable.syringe), R.color.pastelPurple));
        return specialities;
    }
}