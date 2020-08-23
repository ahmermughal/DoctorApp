package com.idevelopstudio.doctorapp.authDoctorDetails;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.DialogCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
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
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.auth.AuthDoctorViewModel;
import com.idevelopstudio.doctorapp.databinding.DialogCountryListBinding;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorDetailsBinding;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.Helper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;


public class AuthDoctorDetailsFragment extends Fragment {

    private FragmentAuthDoctorDetailsBinding binding;
    private AuthDoctorDetailsViewModel viewModel;
    private AuthDoctorViewModel mainViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // TODO add main ViewModel shared between this fragment and Camera Fragment
        binding = FragmentAuthDoctorDetailsBinding.inflate(getLayoutInflater());
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryLight));

        viewModel = new ViewModelProvider(this).get(AuthDoctorDetailsViewModel.class);
        mainViewModel = new ViewModelProvider(getActivity()).get(AuthDoctorViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());

        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.buttonCountry.setOnClickListener(v -> setupAndShowCountryDialog(Helper.getCountries()));
        binding.buttonUploadCard.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(AuthDoctorDetailsFragmentDirections.actionAuthDoctorDetailsFragmentToCameraFragment(2));
        });

        binding.buttonSave.setOnClickListener(v -> {
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            String uid = FirebaseAuth.getInstance().getUid();
            if (email != null && uid != null) {
                String firstName = binding.firstNameEditText.getText().toString().trim();
                String lastName = binding.lastNameEditText.getText().toString().trim();
                String pmdcNumber = binding.pmdcEditText.getVisibility() == View.VISIBLE ? binding.pmdcEditText.getText().toString() : "";

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

                if (viewModel.isCountryEmpty()) {
                    Snackbar.make(binding.getRoot(), "Choose a country", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (binding.pmdcEditText.getVisibility() == View.VISIBLE && pmdcNumber.isEmpty()) {
                    binding.pmdcEditText.requestFocus();
                    binding.pmdcEditText.setError("Enter PMDC number");
                    return;
                }

                if (binding.buttonUploadCard.getVisibility() == View.VISIBLE && mainViewModel.imageUris.getValue() == null) {
                    Snackbar.make(binding.getRoot(), "Upload Doctor ID.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                if (binding.buttonUploadCard.getVisibility() == View.VISIBLE && mainViewModel.imageUris.getValue().size() <= 0) {
                    Snackbar.make(binding.getRoot(), "Upload Doctor ID.", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                mainViewModel.setfirstName(firstName);
                mainViewModel.setlastName(lastName);
                if (binding.pmdcEditText.getVisibility() == View.VISIBLE) {
                    mainViewModel.setpdmcNumber(pmdcNumber);
                }

                Navigation.findNavController(v).navigate(AuthDoctorDetailsFragmentDirections.actionAuthDoctorDetailsFragmentToAuthDoctorSpeciality());
            }
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

    @Override
    public void onPause() {
        super.onPause();
        saveData();
    }

    @Override
    public void onResume() {
        super.onResume();
        getAndSetDataIfSaved();
    }

    private void saveData() {
        String firstName = binding.firstNameEditText.getText().toString().trim();
        String lastName = binding.lastNameEditText.getText().toString().trim();
        String country = binding.buttonCountry.getText().toString();
        Timber.d("Button Text: " + country);
        if (!country.equals("Country")) {
            mainViewModel.setCountry(country);
        }
        if (!firstName.isEmpty())
            mainViewModel.setfirstName(firstName);

        if (!lastName.isEmpty())
            mainViewModel.setlastName(lastName);

        Timber.d(mainViewModel.firstName.getValue());
        Timber.d(mainViewModel.lastName.getValue());
    }

    private void getAndSetDataIfSaved() {
        Timber.d(mainViewModel.firstName.getValue());
        Timber.d(mainViewModel.lastName.getValue());

        binding.firstNameEditText.setText(mainViewModel.firstName.getValue());
        binding.lastNameEditText.setText(mainViewModel.lastName.getValue());
        if (mainViewModel.country.getValue() != null && !mainViewModel.country.getValue().isEmpty()) {
            binding.buttonCountry.setText(mainViewModel.country.getValue());
            viewModel.setSelectedCountry(mainViewModel.country.getValue());
            binding.buttonCountry.setTextColor(getResources().getColor(android.R.color.primary_text_light));
        }
        List<Uri> uris = mainViewModel.imageUris.getValue();

        if (uris != null && uris.size() > 0) {
            binding.imageViewFront.setImageURI(uris.get(0));
            binding.imageViewBack.setImageURI(uris.get(1));
        }
    }
}