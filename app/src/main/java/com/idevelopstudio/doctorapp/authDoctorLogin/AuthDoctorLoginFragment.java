package com.idevelopstudio.doctorapp.authDoctorLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorLoginBinding;


public class AuthDoctorLoginFragment extends Fragment {

    private FragmentAuthDoctorLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthDoctorLoginBinding.inflate(getLayoutInflater());
        binding.googleSignIn.setOnClickListener(v -> Navigation.findNavController(v).navigate(AuthDoctorLoginFragmentDirections.actionAuthDoctorLoginFragmentToAuthDoctorDetailsFragment()));
        return binding.getRoot();
    }
}