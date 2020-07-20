package com.idevelopstudio.doctorapp.authUserLogin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthUserLoginBinding;


public class AuthUserLoginFragment extends Fragment {

    private FragmentAuthUserLoginBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthUserLoginBinding.inflate(getLayoutInflater());

        binding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(AuthUserLoginFragmentDirections.actionAuthUserLoginFragmentToAuthUserDetailsFragment());
            }
        });

        return binding.getRoot();
    }
}