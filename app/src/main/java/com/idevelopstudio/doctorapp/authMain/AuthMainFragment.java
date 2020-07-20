package com.idevelopstudio.doctorapp.authMain;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthMainBinding;


public class AuthMainFragment extends Fragment {

    private FragmentAuthMainBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthMainBinding.inflate(getLayoutInflater());
        binding.cardUser.setOnClickListener(v -> Navigation.findNavController(v).navigate(AuthMainFragmentDirections.actionAuthMainFragmentToAuthUserLoginFragment()));

        binding.cardDoctor.setOnClickListener( v -> Navigation.findNavController(v).navigate(AuthMainFragmentDirections.actionAuthMainFragmentToAuthDoctorLoginFragment()));
        return binding.getRoot();
    }
}