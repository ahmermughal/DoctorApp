package com.idevelopstudio.doctorapp.authDoctorLogin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.idevelopstudio.doctorapp.DoctorLoginMutation;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

import timber.log.Timber;

public class AuthDoctorLoginViewModel extends ParentViewModel {

    public AuthDoctorLoginViewModel(){
        _states.setValue(States.NOT_EMPTY);
    }

    private MutableLiveData<Boolean> _navigateToDoctorCreate = new MutableLiveData<>();
    public LiveData<Boolean> navigateToDoctorCreate = _navigateToDoctorCreate;
    public void navigateToCreate(){
        _navigateToDoctorCreate.postValue(true);
    }

    public void doneNavigating(){
        _navigateToDoctorCreate.postValue(false);
    }

    public void loginUser(String uid){
        Timber.d("Apollo login called");
        DoctorLoginMutation doctorLoginMutation = DoctorLoginMutation.builder().d_id(uid).build();

        NetworkManager.getInstance().getApolloClient().mutate(doctorLoginMutation).enqueue(new ApolloCall.Callback<DoctorLoginMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<DoctorLoginMutation.Data> response) {

                if(response.hasErrors()){
                    Timber.d("User Does not exist");
                    navigateToCreate();
                }else{
                    Timber.d("User exists: " + response.getData().doctorLogin());
                    hasData();
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                Timber.d(e);
                connectionError();
            }
        });
    }

}
