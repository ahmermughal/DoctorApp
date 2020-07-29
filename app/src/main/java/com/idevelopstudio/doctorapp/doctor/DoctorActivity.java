package com.idevelopstudio.doctorapp.doctor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.ActivityDoctorBinding;

public class DoctorActivity extends AppCompatActivity {

    private ActivityDoctorBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor);

        getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryExtraLight));
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.myActionBar, navController);

    }
}