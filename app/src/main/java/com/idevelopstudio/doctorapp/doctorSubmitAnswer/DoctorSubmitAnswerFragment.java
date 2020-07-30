package com.idevelopstudio.doctorapp.doctorSubmitAnswer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorSubmitAnswerBinding;

public class DoctorSubmitAnswerFragment extends Fragment {

    private FragmentDoctorSubmitAnswerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentDoctorSubmitAnswerBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }
}