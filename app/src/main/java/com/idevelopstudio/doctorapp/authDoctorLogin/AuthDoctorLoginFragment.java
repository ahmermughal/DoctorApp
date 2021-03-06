package com.idevelopstudio.doctorapp.authDoctorLogin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.FragmentAuthDoctorLoginBinding;
import com.idevelopstudio.doctorapp.doctor.DoctorActivity;
import com.idevelopstudio.doctorapp.models.ParcelableUriList;
import com.idevelopstudio.doctorapp.utils.States;

import java.util.concurrent.Executor;

import timber.log.Timber;

import static android.content.ContentValues.TAG;


public class AuthDoctorLoginFragment extends Fragment {
    private static final int RC_SIGN_IN = 100;

    private FragmentAuthDoctorLoginBinding binding;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private AuthDoctorLoginViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAuthDoctorLoginBinding.inflate(getLayoutInflater());
        mAuth = FirebaseAuth.getInstance();
        getActivity().getWindow().setStatusBarColor(getResources().getColor(R.color.colorSecondaryLight));

        viewModel = new ViewModelProvider(this).get(AuthDoctorLoginViewModel.class);
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        binding.googleSignIn.setOnClickListener(v -> signIn());
        setupGoogleSignIn();
        setupObservers();
        return binding.getRoot();
    }

    private void setupObservers() {

        viewModel.getStates().observe(getViewLifecycleOwner(), states -> {
            switch (states) {
                case NOT_EMPTY:
                    // go to doctor main
                    Snackbar.make(binding.getRoot(), "Doctor Exists", Snackbar.LENGTH_SHORT).show();
                    break;
                case NO_CONNECTION:
                    // try again
                    Snackbar.make(binding.getRoot(), "Connection Error, Try Again", Snackbar.LENGTH_SHORT).show();
                    break;
            }
        });

        viewModel.navigateToDoctorCreate.observe(getViewLifecycleOwner(), aBoolean -> {
            if(aBoolean){
                viewModel.doneNavigating();
                Navigation.findNavController(binding.getRoot()).navigate(AuthDoctorLoginFragmentDirections.actionAuthDoctorLoginFragmentToAuthDoctorDetailsFragment(new ParcelableUriList(null)));
            }
        });

        viewModel.didNavigateToDoctorMain.observe(getViewLifecycleOwner(), didNavigate ->{
            if(!didNavigate){
                Intent intent = new Intent(getContext(), DoctorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                viewModel.doneNavigateToDoctorMain();
            }
        });
    }

    private void setupGoogleSignIn() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getContext(), gso);
    }

    private void signIn() {
        viewModel.showLoading();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Timber.d("firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Timber.tag(TAG).w(e, "Google sign in failed");
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCredential:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Timber.d("UID:" + user.getUid());
                        viewModel.loginUser(user.getUid());
                        //updateUI(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.tag(TAG).w(task.getException(), "signInWithCredential:failure");
                        //Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                        //updateUI(null);
                        viewModel.connectionError();
                    }

                });
    }

}