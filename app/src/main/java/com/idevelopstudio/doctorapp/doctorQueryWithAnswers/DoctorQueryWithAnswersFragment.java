package com.idevelopstudio.doctorapp.doctorQueryWithAnswers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorQueryWithAnswersBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorAnswerBinding;
import com.idevelopstudio.doctorapp.models.DoctorAnswer;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class DoctorQueryWithAnswersFragment extends Fragment {

    private FragmentDoctorQueryWithAnswersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDoctorQueryWithAnswersBinding.inflate(getLayoutInflater());
        setupAdapters();
        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.fabAnswer.setOnClickListener(v -> Navigation.findNavController(v).navigate(DoctorQueryWithAnswersFragmentDirections.actionDoctorQueryWithAnswersFragmentToDoctorSubmitAnswerFragment()));
    }

    private void setupAdapters() {
        binding.recyclerView.setAdapter(new MyRecyclerViewAdapter<ListItemDoctorAnswerBinding, DoctorAnswer>(getAnswers(), R.layout.list_item_doctor_answer){
            @Override
            public void bind(ListItemDoctorAnswerBinding dataBinding, DoctorAnswer item) {
                dataBinding.setDoctorAnswer(item);
            }

            @Override
            public void onItemPressed(View view, DoctorAnswer item, int position) {
            }
        });
    }

    private ArrayList<DoctorAnswer> getAnswers(){
        ArrayList<DoctorAnswer> specialities = new ArrayList<>();
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Batman", "12/12/2020"));
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Robin", "10/02/2020"));
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Freeze", "01/04/2019"));

        return specialities;
    }
}