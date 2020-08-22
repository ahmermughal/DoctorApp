package com.idevelopstudio.doctorapp.authUserLogin;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class AuthUserLoginViewModel extends ParentViewModel {

    public AuthUserLoginViewModel(){
        _states.setValue(States.NOT_EMPTY);
    }
    private MutableLiveData<Boolean> _navigateToUserCreate = new MutableLiveData<>();
    public LiveData<Boolean> navigateToDoctorCreate = _navigateToUserCreate;
    public void navigateToCreate(){
        _navigateToUserCreate.postValue(true);
    }
    public void doneNavigating(){
        _navigateToUserCreate.postValue(false);
    }

    public void loginUser(String uid){
        Timber.d("Apollo login called");
//        UserLoginMutation userLoginMutation = UserLoginMutation.builder().p_id(uid).build();
//
//        NetworkManager.getInstance().getApolloClient().mutate(userLoginMutation).enqueue(new ApolloCall.Callback<UserLoginMutation.Data>() {
//            @Override
//            public void onResponse(@NotNull Response<UserLoginMutation.Data> response) {
//
//                if(response.hasErrors()){
//                    Timber.d("User Does not exist");
//                    navigateToCreate();
//                }else{
//                    Timber.d("User exists: " + response.getData().patientLogin());
//                    hasData();
//                }
//            }
//
//            @Override
//            public void onFailure(@NotNull ApolloException e) {
//                Timber.d(e);
//                connectionError();
//            }
//        });
    }

}
