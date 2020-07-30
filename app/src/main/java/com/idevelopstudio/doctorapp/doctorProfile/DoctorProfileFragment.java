package com.idevelopstudio.doctorapp.doctorProfile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorProfileBinding;


public class DoctorProfileFragment extends Fragment {

    private FragmentDoctorProfileBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorProfileBinding.inflate(getLayoutInflater());

        binding.fabEditProfile.setOnClickListener(v -> Navigation.findNavController(v).navigate(DoctorProfileFragmentDirections.actionDoctorProfileFragmentToDoctorProfileEditFragment()));

        return binding.getRoot();
    }
}