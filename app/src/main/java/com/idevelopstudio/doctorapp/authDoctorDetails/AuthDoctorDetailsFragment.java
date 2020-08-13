package com.idevelopstudio.doctorapp.authDoctorDetails;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.DialogCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.os.Debug;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.DialogCountryListBinding;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorDetailsBinding;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthUserDetailsBinding;
import com.idevelopstudio.doctorapp.utils.Helper;

import java.util.ArrayList;
import java.util.Locale;

import timber.log.Timber;


public class AuthDoctorDetailsFragment extends Fragment {

    private FragmentAuthDoctorDetailsBinding binding;
    private AuthDoctorDetailsFragmentArgs args;

    private AuthDoctorDetailsViewModel viewModel;
    private ArrayList<Uri> imageUris = new ArrayList<>();
    private SharedPreferences sharedPref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAuthDoctorDetailsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(AuthDoctorDetailsViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
        try {
            args = AuthDoctorDetailsFragmentArgs.fromBundle(getArguments());
            if (args.getImagesUris().getImageUris().size() > 0) {
                this.imageUris = args.getImagesUris().getImageUris();
                for (Uri uri : args.getImagesUris().getImageUris()) {
                    Timber.d(uri.toString());
                }
            }
        } catch (IllegalArgumentException ignored) {
        }
        getAndSetNamesIfSaved();
        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.buttonCountry.setOnClickListener(v -> setupAndShowCountryDialog(Helper.getCountries()));
        binding.buttonUploadIdCard.setOnClickListener(v -> {
            saveName();
            Navigation.findNavController(v).navigate(AuthDoctorDetailsFragmentDirections.actionAuthDoctorDetailsFragmentToCameraFragment(2));
        });
        binding.buttonSave.setOnClickListener(v -> {
            String firstName = binding.firstNameEditText.getText().toString().trim();
            String lastName = binding.lastNameEditText.getText().toString().trim();
            String pmdcNumber = binding.pmdcEditText.getVisibility() == View.VISIBLE ? binding.pmdcEditText.getText().toString() : "";

            if (firstName.isEmpty()) {
                binding.firstNameEditText.setError("Enter First Name.");
                binding.firstNameEditText.requestFocus();
                return;
            }

            if (lastName.isEmpty()) {
                binding.lastNameEditText.setError("Enter Last Name.");
                binding.lastNameEditText.requestFocus();
                return;
            }

            if (viewModel.isCountryEmpty()) {
                Snackbar.make(binding.getRoot(), "Choose a country", Snackbar.LENGTH_SHORT).show();
                return;
            }

            if (binding.pmdcEditText.getVisibility() == View.VISIBLE && pmdcNumber.isEmpty()) {
                binding.pmdcEditText.requestFocus();
                binding.pmdcEditText.setError("Enter PMDC Number");
                return;
            }

            if (binding.buttonUploadIdCard.getVisibility() == View.VISIBLE && imageUris.size() <= 0) {
                Snackbar.make(binding.getRoot(), "Upload Doctor ID.", Snackbar.LENGTH_SHORT).show();
                return;
            }

            Timber.d("All Good, Now can sign in");

        });
    }


    private void setupAndShowCountryDialog(ArrayList<String> countries) {
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        DialogCountryListBinding dialogBinding = DialogCountryListBinding.inflate(getLayoutInflater());
        dialog.setContentView(dialogBinding.getRoot());
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, countries);

        dialogBinding.listViewCountries.setAdapter(adapter);
        dialogBinding.listViewCountries.setTextFilterEnabled(true);
        dialogBinding.listViewCountries.setOnItemClickListener((parent, view, position, id) -> {
            binding.buttonCountry.setText(((TextView) view).getText().toString());
            binding.buttonCountry.setTextColor(getResources().getColor(android.R.color.primary_text_light));
            viewModel.setSelectedCountry(((TextView) view).getText().toString());
            dialog.dismiss();
        });
        dialogBinding.countrySearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    private void saveName() {
        String firstName = binding.firstNameEditText.getText().toString().trim();
        String lastName = binding.lastNameEditText.getText().toString().trim();
        SharedPreferences.Editor editor = sharedPref.edit();
        if (!firstName.isEmpty())
            editor.putString(getString(R.string.SHARED_PREF_FIRST_NAME), firstName);

        if (!lastName.isEmpty())
            editor.putString(getString(R.string.SHARED_PREF_LAST_NAME), lastName);
        editor.apply();
    }

    private void getAndSetNamesIfSaved() {
        binding.firstNameEditText.setText(sharedPref.getString(getString(R.string.SHARED_PREF_FIRST_NAME), ""));
        binding.lastNameEditText.setText(sharedPref.getString(getString(R.string.SHARED_PREF_LAST_NAME), ""));
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.SHARED_PREF_FIRST_NAME), "");
        editor.putString(getString(R.string.SHARED_PREF_LAST_NAME), "");
        editor.apply();
    }


}