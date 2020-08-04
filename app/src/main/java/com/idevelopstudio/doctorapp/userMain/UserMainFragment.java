package com.idevelopstudio.doctorapp.userMain;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentUserMainBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemDoctorSpecialityBinding;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;

public class UserMainFragment extends Fragment {

    private FragmentUserMainBinding binding;
    private Disposable disposable;

    private UserMainViewModel viewModel;
    private UserMainViewModelFactory viewModelFactory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserMainBinding.inflate(getLayoutInflater());

        viewModelFactory = new UserMainViewModelFactory(binding.searchBar.createTextChangeObservable());
        viewModel = viewModelFactory.create(UserMainViewModel.class);

        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        setupAdapters();
        setupObservers();

        return binding.getRoot();
    }

    private void setupObservers() {
        disposable = viewModel.observable.subscribe(
                specialities -> {

                    if (specialities.size() == 0){
                        viewModel.hasNoData();
                    }else{
                        viewModel.hasData();
                        ((MyRecyclerViewAdapter) binding.recyclerView.getAdapter()).setItemList(specialities);
                    }
                },
                Timber::d);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (!disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    private void setupAdapters() {
        binding.recyclerView.setAdapter(new MyRecyclerViewAdapter<ListItemDoctorSpecialityBinding, Speciality>(viewModel.getSpecialities(), R.layout.list_item_doctor_speciality) {

            @Override
            public void bind(ListItemDoctorSpecialityBinding dataBinding, Speciality item) {
                dataBinding.setSpeciality(item);
            }

            @Override
            public void onItemPressed(View view, Speciality item, int position) {

            }
        });
    }


}