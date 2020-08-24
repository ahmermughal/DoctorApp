package com.idevelopstudio.doctorapp.userSubmitQuery;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.customViews.CriticalStatus;
import com.idevelopstudio.doctorapp.databinding.FragmentUserSubmitQueryBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemCameraImageViewBinding;
import com.idevelopstudio.doctorapp.utils.Helper;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;
import java.util.ArrayList;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;
import timber.log.Timber;


public class UserSubmitQueryFragment extends Fragment {

    private FragmentUserSubmitQueryBinding binding;
    private UserSubmitQueryFragmentArgs args;
    private Disposable disposable;
    private UserSubmitQueryViewModel viewModel;
    private UserSubmitQueryViewModelFactory viewModelFactory;
    private MyRecyclerViewAdapter myRecyclerViewAdapter;
    private ArrayList<String> selectedImages;
    private String selectedSpecialization;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserSubmitQueryBinding.inflate(getLayoutInflater());
        args = UserSubmitQueryFragmentArgs.fromBundle(getArguments());
        selectedSpecialization = String.valueOf(args.getSelectedSpecialization());
        viewModelFactory = new UserSubmitQueryViewModelFactory(binding.criticalStatusBar.createBarStatusObservable());
        viewModel = viewModelFactory.create(UserSubmitQueryViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel.setCriticalStatus(CriticalStatus.MODERATE);
        setupObservers();
        setupListeners();
        setupAdapter();
        return binding.getRoot();
    }

    private void setupAdapter() {
        myRecyclerViewAdapter = new MyRecyclerViewAdapter<ListItemCameraImageViewBinding, String>(new ArrayList<String>(), R.layout.list_item_camera_image_view) {
            @Override
            public void bind(ListItemCameraImageViewBinding dataBinding, String item) {
                dataBinding.imageView.setImageURI(Uri.parse(item));
            }

            @Override
            public void onItemPressed(View view, String item, int position) {

            }
        };
        binding.recyclerViewImages.setAdapter(myRecyclerViewAdapter);
    }

    private void setupObservers() {
        disposable = viewModel.observable.subscribe(criticalStatus -> {
            viewModel.setCriticalStatus(criticalStatus);
        });
    }

    private void setupListeners(){
        binding.buttonUploadImages.setOnClickListener(v -> {
            Options options = Options.init()
                    .setRequestCode(100)                                           //Request code for activity results
                    .setCount(5)                                                   //Number of images to restict selection count
                    .setFrontfacing(false)                                         //Front Facing camera on start
                    .setExcludeVideos(true)                                       //Option to exclude videos
                    .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                    .setPath("/doctor/images")
                    .setPreSelectedUrls(selectedImages != null ? selectedImages : new ArrayList<>());
            Pix.start(this, options);
        });
        binding.buttonSubmit.setOnClickListener(v -> {

            String question = binding.editTextTitle.getText().toString().trim();
            String questionDesc = binding.editTextQuestionDesc.getText().toString().trim();

            if (question.isEmpty()) {
                binding.editTextTitle.setError("Enter a title.");
                binding.editTextTitle.requestFocus();
                return;
            }

            if (questionDesc.isEmpty()) {
                binding.editTextQuestionDesc.setError("Describe your condition.");
                binding.editTextQuestionDesc.requestFocus();
                return;
            }
            Timber.d(Helper.getToken(getActivity()));
            viewModel.createUserQuery(Helper.getToken(getActivity()),FirebaseAuth.getInstance().getUid(), question, questionDesc, selectedSpecialization, getContext());
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            selectedImages = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
            ArrayList<Uri> uris = new ArrayList<>();
            for(String string : selectedImages){
                uris.add(Uri.parse(string));
            }
            viewModel.setimageUris(uris);
            myRecyclerViewAdapter.setItemList(selectedImages);
            viewModel.setUriListObservable(Observable.just(uris));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, Options.init().setRequestCode(100));
                } else {
                    Toast.makeText(getContext(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
}