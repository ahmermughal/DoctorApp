package com.idevelopstudio.doctorapp.doctorQueriesWithAnswers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentDoctorQueriesWithAnswersBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorAnswerBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.models.DoctorAnswer;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class DoctorQueriesWithAnswersFragment extends Fragment {

    private FragmentDoctorQueriesWithAnswersBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentDoctorQueriesWithAnswersBinding.inflate(getLayoutInflater());

        binding.recyclerView.setAdapter(new MyRecyclerViewAdapter<ListItemDoctorAnswerBinding, DoctorAnswer>(getAnswers(), R.layout.list_item_doctor_answer){
            @Override
            public void bind(ListItemDoctorAnswerBinding dataBinding, DoctorAnswer item) {
                dataBinding.setDoctorAnswer(item);
            }
        });
        return binding.getRoot();
    }

    private ArrayList<DoctorAnswer> getAnswers(){
        ArrayList<DoctorAnswer> specialities = new ArrayList<>();
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Batman", "12/12/2020"));
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Robin", "10/02/2020"));
        specialities.add(new DoctorAnswer(getString(R.string.place_holder_text), "Dr. Freeze", "01/04/2019"));

        return specialities;
    }
}