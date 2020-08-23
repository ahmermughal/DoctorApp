package com.idevelopstudio.doctorapp.authUserDetails;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthUserDetailsBinding;
import com.idevelopstudio.doctorapp.user.UserActivity;

public class AuthUserDetailsFragment extends Fragment {
    private FragmentAuthUserDetailsBinding binding;
    private AuthUserDetailsViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthUserDetailsBinding.inflate(getLayoutInflater());
        // Inflate the layout for this fragment
        viewModel = new ViewModelProvider(this).get(AuthUserDetailsViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setupListeners();
        setupObservers();
        return binding.getRoot();
    }

    private void setupObservers() {
        viewModel.didNavigateToUserMain.observe(getViewLifecycleOwner(), didNavigate -> {
            if (!didNavigate) {
                Intent intent = new Intent(getContext(), UserActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                viewModel.doneNavigateToUserMain();
            }
        });
    }

    private void setupListeners() {
        binding.buttonSubmit.setOnClickListener(v -> {

            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String uid = FirebaseAuth.getInstance().getUid();
            if (email != null && uid != null) {
                String firstName = binding.firstNameEditText.getText().toString().trim();
                String lastName = binding.lastNameEditText.getText().toString().trim();
                String age = binding.ageEditText.getText().toString().trim();
                String weight = binding.weightEditText.getText().toString().trim();
                String height = binding.heightEditText.getText().toString().trim();

                if (firstName.isEmpty()) {
                    binding.firstNameEditText.setError("Enter first name");
                    binding.firstNameEditText.requestFocus();
                    return;
                }

                if (lastName.isEmpty()) {
                    binding.lastNameEditText.setError("Enter last name");
                    binding.lastNameEditText.requestFocus();
                    return;
                }

                if (age.isEmpty()) {
                    binding.ageEditText.setError("Enter age");
                    binding.ageEditText.requestFocus();
                }

                if (weight.isEmpty()) {
                    binding.weightEditText.setError("Enter weight");
                    binding.weightEditText.requestFocus();
                }

                if (height.isEmpty()) {
                    binding.weightEditText.setError("Enter height");
                    binding.heightEditText.requestFocus();
                }

                viewModel.createUser(uid, email, firstName, lastName, age, weight, height);
            }
        });
    }
}
