package com.idevelopstudio.doctorapp.doctorQueriesCategories;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.shape.CornerFamily;
import com.google.android.material.shape.CornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorQueriesCategoriesBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorQueriesCategoryBinding;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class DoctorQueriesCategoriesFragment extends Fragment {

    FragmentDoctorQueriesCategoriesBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorQueriesCategoriesBinding.inflate(getLayoutInflater());

       // binding.recyclerView.setAdapter(new DoctorQueriesCategoriesAdapter(getSpecialities()));
        setupAdapter();
        binding.layoutBg.setClipChildren(true);
        return binding.getRoot();
    }

    private void setupAdapter() {
        binding.recyclerView.setAdapter(new MyRecyclerViewAdapter<ListItemDoctorQueriesCategoryBinding, Speciality>(getSpecialities(), R.layout.list_item_doctor_queries_category) {
            @Override
            public void bind(ListItemDoctorQueriesCategoryBinding dataBinding, Speciality item) {
                dataBinding.setSpeciality(item);
            }

            @Override
            public void onItemPressed(View view, Speciality item, int position) {
                Navigation.findNavController(view).navigate(DoctorQueriesCategoriesFragmentDirections.actionDoctorQueriesCategoriesFragmentToDoctorQueryWithAnswersFragment());

            }
        });
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