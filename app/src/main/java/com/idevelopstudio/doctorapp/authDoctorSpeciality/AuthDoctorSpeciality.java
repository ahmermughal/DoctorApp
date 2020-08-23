package com.idevelopstudio.doctorapp.authDoctorSpeciality;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.auth.AuthDoctorViewModel;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorQueriesCategoryBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.doctor.DoctorActivity;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.Helper;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;


public class AuthDoctorSpeciality extends Fragment {

    private FragmentAuthDoctorSpecialityBinding binding;
    private AuthDoctorViewModel mainViewModel;
    private AuthDoctorSpecialityViewModel viewModel;
    private MyRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthDoctorSpecialityBinding.inflate(getLayoutInflater());
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryLight));
        mainViewModel = new ViewModelProvider(getActivity()).get(AuthDoctorViewModel.class);
        viewModel = new ViewModelProvider(this).get(AuthDoctorSpecialityViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setupAdapters();
        setupListeners();
        setupObservers();
        return binding.getRoot();
    }

    private void setupObservers() {
        viewModel.states.observe(getViewLifecycleOwner(), states -> {
            if (states == States.NO_CONNECTION) {
                Snackbar.make(binding.getRoot(), "Connection error, try again.", Snackbar.LENGTH_SHORT).show();
            } else if (states == States.EMPTY) {
                Snackbar.make(binding.getRoot(), "Error, try again.", Snackbar.LENGTH_SHORT).show();
            }
        });

        viewModel.didNavigateToDoctorMain.observe(getViewLifecycleOwner(), didNavigate -> {
            if(!didNavigate){
                Intent intent = new Intent(getContext(), DoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                viewModel.doneNavigateToDoctorMain();
            }
        });

    }

    private void setupListeners() {
        binding.buttonSubmit.setOnClickListener(v -> {
            ArrayList<Speciality> list = adapter.getItemList();
            String email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            Timber.d(email);
            Timber.d(FirebaseAuth.getInstance().getUid());
            if (email != null) {
                if (mainViewModel.pdmcNumber.getValue() != null)
                    viewModel.createDoctorPakistan(FirebaseAuth.getInstance().getUid(), email, mainViewModel.firstName.getValue(), mainViewModel.lastName.getValue(), "Pakistan", mainViewModel.pdmcNumber.getValue(), list);
                else
                    viewModel.createDoctorOther(FirebaseAuth.getInstance().getUid(), email, mainViewModel.firstName.getValue(), mainViewModel.lastName.getValue(), mainViewModel.country.getValue(), list, mainViewModel.getCompressedImagesObserver(getContext()));
            }
        });
    }

    private void setupAdapters() {
        adapter = new MyRecyclerViewAdapter<ListItemDoctorSpecialityBinding, Speciality>(getSpecialities(), R.layout.list_item_doctor_speciality) {
            @Override
            public void bind(ListItemDoctorSpecialityBinding dataBinding, Speciality item) {
                dataBinding.setSpeciality(item);
            }

            @Override
            public void onItemPressed(View view, Speciality item, int position) {
                item.toggle();
                ListItemDoctorSpecialityBinding binding = DataBindingUtil.getBinding(view);

                if (item.isSelected()) {
                    binding.cardLayout.setBackgroundResource(Helper.getSelectedBgByColor(item.getBackgroundColor()));
                } else {
                    binding.cardLayout.setBackgroundResource(item.getBackgroundColor());
                    //view.
                }
            }
        };
        binding.recyclerViewSpecialities.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.recyclerViewSpecialities.setAdapter(adapter);
    }

    private ArrayList<Speciality> getSpecialities() {
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality("Anesthesiology", R.drawable.syringe, R.color.pastelBlue));
        specialities.add(new Speciality("Dentistry", R.drawable.tooth, R.color.pastelGreen));
        specialities.add(new Speciality("ENT", R.drawable.ear, R.color.pastelPink));
        specialities.add(new Speciality("Eye", R.drawable.eye, R.color.pastelSoftPink));
        specialities.add(new Speciality("Obstetrics & Gynaecology", R.drawable.gynaecology, R.color.pastelYellow));
        specialities.add(new Speciality("Medicine", R.drawable.pill, R.color.pastelPurple));
        specialities.add(new Speciality("Paediatrics", R.drawable.toys, R.color.pastelBabyBlue));
        specialities.add(new Speciality("Surgery", R.drawable.surgery, R.color.pastelLightPurple));
        specialities.add(new Speciality("Others", R.drawable.doctor_other, R.color.pastelMaroon));
        return specialities;
    }
}