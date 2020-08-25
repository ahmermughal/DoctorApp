package com.idevelopstudio.doctorapp.user;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.ActivityUserBinding;

public class UserActivity extends AppCompatActivity {

    private ActivityUserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_user_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.userMainFragment
        ).build();

        NavigationUI.setupWithNavController(binding.myActionBar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.bottomNav, navController);

    }
}