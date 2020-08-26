package com.idevelopstudio.doctorapp.userAskedQuestions;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentUserAskedQuestionsBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorQueriesCategoryBinding;
import com.idevelopstudio.doctorapp.models.UserQuery;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.util.ArrayList;


public class UserAskedQuestionsFragment extends Fragment {

    private FragmentUserAskedQuestionsBinding binding;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private UserAskedQuestionsViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserAskedQuestionsBinding.inflate(getLayoutInflater());
        viewModel = new ViewModelProvider(this).get(UserAskedQuestionsViewModel.class);

        setupAdapters();

        return binding.getRoot();
    }

    private void setupAdapters(){
        binding.recyclerView.setHasFixedSize(true);
        UserAskedAdapter adapter = new UserAskedAdapter(getContext());
        viewModel.userQueryPagedList.observe(getViewLifecycleOwner(), new Observer<PagedList<UserQuery>>() {
            @Override
            public void onChanged(PagedList<UserQuery> userQueries) {
                adapter.submitList(userQueries);
            }
        });
        binding.recyclerView.setAdapter(adapter);
    }

}