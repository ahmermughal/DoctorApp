package com.idevelopstudio.doctorapp.userAskedQuestions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentUserAskedQuestionsBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorQueriesCategoryBinding;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class UserAskedQuestionsFragment extends Fragment {

    private FragmentUserAskedQuestionsBinding binding;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAskedQuestionsBinding.inflate(getLayoutInflater());

        setupAdapters();

        return binding.getRoot();
    }

    private void setupAdapters(){
        myRecyclerViewAdapter = new MyRecyclerViewAdapter<ListItemDoctorQueriesCategoryBinding, String>(new ArrayList<>(), R.layout.list_item_doctor_queries_category){

            @Override
            public void bind(ListItemDoctorQueriesCategoryBinding dataBinding, String item) {

            }

            @Override
            public void onItemPressed(View view, String item, int position) {

            }
        };
    }

}