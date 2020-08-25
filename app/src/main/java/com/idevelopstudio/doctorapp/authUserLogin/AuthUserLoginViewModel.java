package com.idevelopstudio.doctorapp.authUserLogin;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.singleton.TokenSingleton;
import com.idevelopstudio.doctorapp.utils.Helper;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class AuthUserLoginViewModel extends ParentViewModel {
    private Disposable disposable;

    public AuthUserLoginViewModel(){
        _states.setValue(States.NOT_EMPTY);
    }
    private MutableLiveData<Boolean> _navigateToUserCreate = new MutableLiveData<>();
    public LiveData<Boolean> navigateToUserCreate = _navigateToUserCreate;
    public void navigateToCreate(){
        _navigateToUserCreate.postValue(true);
    }
    public void doneNavigating(){
        _navigateToUserCreate.postValue(false);
    }

    private MutableLiveData<Boolean> _didNavigateToUserMain = new MutableLiveData<>();
    public LiveData<Boolean> didNavigateToUserMain = _didNavigateToUserMain;
    private void navigateToUserMain(){
        _didNavigateToUserMain.postValue(false);
    }
    public void doneNavigateToUserMain(){
        _didNavigateToUserMain.postValue(true);
    }

    public void loginUser(String uid, Activity activity){
        disposable = NetworkManager.getInstance().getUserApi().userSignIn(uid)
                .subscribeOn(Schedulers.io())
                .doOnNext(token -> showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        token -> {
                            Timber.d("OnNext called");
                            //Timber.d(token.getToken());
                            if (token.getToken() == null) {
                                navigateToCreate();
                                hasNoData();
                            }
                            else {
                                hasData();
                                //Helper.saveToken(token.getToken(), activity);
                                Timber.d(token.getToken());
                                TokenSingleton.getInstance().setToken(token.getToken());
                                navigateToUserMain();
                            }
                        },
                        throwable -> {
                            Timber.d("OnError called");
                            connectionError();
                            Timber.d(throwable);
                            navigateToCreate();
                        }
                );
    }

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }
}
