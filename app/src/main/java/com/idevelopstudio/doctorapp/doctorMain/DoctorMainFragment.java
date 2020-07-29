package com.idevelopstudio.doctorapp.doctorMain;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorMainBinding;

public class DoctorMainFragment extends Fragment {

    FragmentDoctorMainBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorMainBinding.inflate(getLayoutInflater());

        binding.cardQueries.setOnClickListener(v -> {
            //Navigation.findNavController(v).navigate(DoctorMainFragmentDirections.actionDoctorMainFragmentToDoctorQueriesCategoriesFragment());
            Navigation.findNavController(v).navigate(DoctorMainFragmentDirections.actionDoctorMainFragmentToDoctorQueriesWithAnswersFragment());

        });
        return binding.getRoot();
    }
}