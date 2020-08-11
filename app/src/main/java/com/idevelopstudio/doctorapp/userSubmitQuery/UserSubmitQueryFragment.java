package com.idevelopstudio.doctorapp.userSubmitQuery;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.idevelopstudio.doctorapp.databinding.FragmentUserSubmitQueryBinding;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;


public class UserSubmitQueryFragment extends Fragment {

    private FragmentUserSubmitQueryBinding binding;
    private Disposable disposable;
    private UserSubmitQueryViewModel viewModel;
    private UserSubmitQueryViewModelFactory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentUserSubmitQueryBinding.inflate(getLayoutInflater());

        viewModelFactory = new UserSubmitQueryViewModelFactory(binding.criticalStatusBar.createBarStatusObservable());
        viewModel = viewModelFactory.create(UserSubmitQueryViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        disposable = viewModel.observable.subscribe(criticalStatus -> {
            Timber.d(criticalStatus.getValue());
        });

        return binding.getRoot();
    }
}