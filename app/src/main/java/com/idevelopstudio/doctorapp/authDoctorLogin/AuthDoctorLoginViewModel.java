package com.idevelopstudio.doctorapp.authDoctorLogin;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idevelopstudio.doctorapp.models.Token;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class AuthDoctorLoginViewModel extends ParentViewModel {

    private Disposable disposable;

    private MutableLiveData<Boolean> _navigateToDoctorCreate = new MutableLiveData<>();
    public LiveData<Boolean> navigateToDoctorCreate = _navigateToDoctorCreate;

    public void navigateToCreate() {
        hasNoData();
        _navigateToDoctorCreate.postValue(true);
    }
    public void doneNavigating() {
        _navigateToDoctorCreate.postValue(false);
    }

    private MutableLiveData<Boolean> _didNavigateToDoctorMain = new MutableLiveData<>();
    public LiveData<Boolean> didNavigateToDoctorMain = _didNavigateToDoctorMain;

    private void navigateToDoctorMain(){
        _didNavigateToDoctorMain.postValue(false);
    }
    public void doneNavigateToDoctorMain(){
        _didNavigateToDoctorMain.postValue(true);
    }

    public AuthDoctorLoginViewModel() {
        _states.setValue(States.NOT_EMPTY);
    }

    public void loginUser(String uid) {
        Timber.d("Apollo login called");
        disposable = NetworkManager.getInstance().getDoctorApi().doctorSignIn(uid)
                .subscribeOn(Schedulers.io())
                .doOnNext(token -> showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        token -> {
                            Timber.d("OnNext called");
                            Timber.d(token.getToken());
                            if (token.getToken() == null) {
                                navigateToCreate();
                                hasNoData();
                            }
                            else {
                                hasData();
                                navigateToDoctorMain();
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
