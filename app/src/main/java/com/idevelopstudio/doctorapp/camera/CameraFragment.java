package com.idevelopstudio.doctorapp.camera;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.common.util.concurrent.ListenableFuture;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.auth.AuthDoctorViewModel;
import com.idevelopstudio.doctorapp.databinding.FragmentCameraBinding;
import com.idevelopstudio.doctorapp.databinding.ListItemCameraImageViewBinding;
import com.idevelopstudio.doctorapp.utils.MyRecyclerViewAdapter;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import timber.log.Timber;


public class CameraFragment extends Fragment {
    private static final String REQUIRED_PERMISSIONS = Manifest.permission.CAMERA;
    private static final String FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS";
    private static final int REQUEST_CODE_PERMISSIONS = 10;

    private FragmentCameraBinding binding;
    private CameraFragmentArgs args;
    private ImageCapture imageCapture = null;
    private File outputDir;
    private ArrayList<Uri> imagesUris = new ArrayList<>();
    private AuthDoctorViewModel mainViewModel;

    MyRecyclerViewAdapter myRecyclerViewAdapter;

    private int numberOfPicturesRequired;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCameraBinding.inflate(getLayoutInflater());
        args = CameraFragmentArgs.fromBundle(getArguments());
        mainViewModel = new ViewModelProvider(getActivity()).get(AuthDoctorViewModel.class);

        numberOfPicturesRequired = args.getNumberOfPicturesRequired();
        if (cameraPermissionsGranted()) {
            Timber.d("Camera Started");
            startCamera();
        } else {
            Timber.d("Permission Asked");
            requestPermissions(new String[]{REQUIRED_PERMISSIONS}, REQUEST_CODE_PERMISSIONS);

        }
        outputDir = getOutputDirectory();
        setupAdapter();
        setupListeners();
        return binding.getRoot();
    }

    private void setupListeners() {
        binding.cameraCaptureButton.setOnClickListener(v -> {
            if (imagesUris.size() >= numberOfPicturesRequired -1) {
                hideCamera();
            }
            takePhoto();
            if (imagesUris.size() < numberOfPicturesRequired) {
                ObjectAnimator animator = ObjectAnimator.ofFloat(v, View.ROTATION, 360f);
                disableDuringAnimation(v, animator);
                animator.setDuration(600);
                animator.start();
            }
        });

        binding.buttonClear.setOnClickListener(v -> {
            Timber.d("Clear called");
            imagesUris = new ArrayList<>();
            myRecyclerViewAdapter.setItemList(imagesUris);
            myRecyclerViewAdapter.notifyDataSetChanged();
            showCamera();
        });

        binding.fabDone.setOnClickListener(v -> {
            mainViewModel.setimageUris(imagesUris);
            Navigation.findNavController(v).navigate(CameraFragmentDirections.actionCameraFragmentToAuthDoctorDetailsFragment());
        });
    }

    private void setupAdapter() {
        myRecyclerViewAdapter = new MyRecyclerViewAdapter<ListItemCameraImageViewBinding, Uri>(imagesUris, R.layout.list_item_camera_image_view) {
            @Override
            public void bind(ListItemCameraImageViewBinding dataBinding, Uri item) {
                dataBinding.imageView.setImageURI(item);
            }

            @Override
            public void onItemPressed(View view, Uri item, int position) {

            }
        };
        binding.recyclerView.setAdapter(myRecyclerViewAdapter);
    }

    private boolean cameraPermissionsGranted() {
        return ContextCompat.checkSelfPermission(getContext(), REQUIRED_PERMISSIONS) == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if ((requestCode == REQUEST_CODE_PERMISSIONS)) {
            startCamera();
        } else {
            Timber.d("Permission Not granted");

        }
    }

    private void startCamera() {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getContext());

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                Preview preview = new Preview.Builder().build();
                preview.setSurfaceProvider(binding.viewFinder.createSurfaceProvider());

                imageCapture = new ImageCapture.Builder().build();

                CameraSelector cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA;

                cameraProvider.unbindAll();
                cameraProvider.bindToLifecycle(getViewLifecycleOwner(), cameraSelector, preview, imageCapture);

            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, ContextCompat.getMainExecutor(getContext()));
    }

    private void takePhoto() {
        ImageCapture imageCapture = this.imageCapture;
        if (this.imageCapture == null) return;

        String fileName = new SimpleDateFormat(FILENAME_FORMAT, Locale.UK).format(System.currentTimeMillis()) + ".jpg";
        File file = new File(outputDir, fileName);

        ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
        imageCapture.takePicture(outputFileOptions, ContextCompat.getMainExecutor(getContext()), new ImageCapture.OnImageSavedCallback() {
            @Override
            public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                imagesUris.add(Uri.fromFile(file));
                Timber.d(Uri.fromFile(file).toString());
                myRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(@NonNull ImageCaptureException exception) {
                Timber.d("Photo capture failed" + exception);
            }
        });

    }

    private void hideCamera() {
        ObjectAnimator rotator = ObjectAnimator.ofFloat(binding.cameraCaptureButton, View.ROTATION, 360f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 0.2f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 0.2f);
        ObjectAnimator scaler = ObjectAnimator.ofPropertyValuesHolder(binding.cameraCaptureButton, scaleX, scaleY);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(binding.cameraCaptureButton, View.ALPHA, 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotator, scaler, alphaAnim);
        animatorSet.setDuration(600);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                binding.cameraCaptureButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.cameraCaptureButton.setVisibility(View.INVISIBLE);
            }
        });
        animatorSet.start();

    }

    private void showCamera(){
        ObjectAnimator rotator = ObjectAnimator.ofFloat(binding.cameraCaptureButton, View.ROTATION, 360f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat(View.SCALE_X, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat(View.SCALE_Y, 1f);
        ObjectAnimator scaler = ObjectAnimator.ofPropertyValuesHolder(binding.cameraCaptureButton, scaleX, scaleY);
        ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(binding.cameraCaptureButton, View.ALPHA, 1f);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(rotator, scaler, alphaAnim);
        animatorSet.setDuration(600);
        animatorSet.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                binding.cameraCaptureButton.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                binding.cameraCaptureButton.setEnabled(true);
                binding.cameraCaptureButton.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.start();
    }

    private File getOutputDirectory() {
        File file = getActivity().getExternalMediaDirs()[0];
        File newFile = null;
        if (file != null) {
            newFile = new File(file, "DoctorApp");
            newFile.mkdirs();
        }
        if (newFile != null && newFile.exists()) {
            return newFile;
        } else {
            return getActivity().getFilesDir();
        }
    }

    private void disableDuringAnimation(View buttonLow, ObjectAnimator animator) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttonLow.setEnabled(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                buttonLow.setEnabled(false);
            }
        });
    }

}