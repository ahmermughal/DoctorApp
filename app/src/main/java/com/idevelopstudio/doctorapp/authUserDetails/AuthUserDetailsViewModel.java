package com.idevelopstudio.doctorapp.authUserDetails;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class AuthUserDetailsViewModel extends ParentViewModel {

    private Disposable disposable;

    private MutableLiveData<Boolean> _didNavigateToUserMain = new MutableLiveData<>();
    public LiveData<Boolean> didNavigateToUserMain = _didNavigateToUserMain;
    private void navigateToUserMain(){
        _didNavigateToUserMain.postValue(false);
    }
    public void doneNavigateToUserMain(){
        _didNavigateToUserMain.postValue(true);
    }

    public AuthUserDetailsViewModel() {
        _states.setValue(States.NOT_EMPTY);
    }

    public void createUser(String uid, String email, String firstName, String lastName, String age, String weight, String height) {
        Timber.d(uid);
        Timber.d(email);
        Timber.d(firstName);
        Timber.d(lastName);
        Timber.d(age);
        Timber.d(weight);
        Timber.d(height);

        disposable = NetworkManager.getInstance().getUserApi().userSignUp(
                uid,
                email,
                firstName,
                lastName,
                age,
                weight,
                height,
                "Male"
        ).subscribeOn(Schedulers.io())
                .doOnNext(token -> showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                            if (token.getToken() != null) {
                                hasData();
                                Timber.d(token.getToken());
                                navigateToUserMain();
                            } else {
                                hasNoData();
                            }
                        },
                        Timber::d);
    }

    @Override
    protected void onCleared() {
        if(disposable != null) disposable.dispose();
        super.onCleared();
    }
}
