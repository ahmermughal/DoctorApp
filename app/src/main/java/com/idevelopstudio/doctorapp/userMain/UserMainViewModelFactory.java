package com.idevelopstudio.doctorapp.userMain;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import io.reactivex.rxjava3.core.Observable;

public class UserMainViewModelFactory implements ViewModelProvider.Factory {
    private Observable<String> observable;

    public UserMainViewModelFactory(Observable<String> observable) {
        this.observable = observable;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserMainViewModel.class)){
            return (T) new UserMainViewModel(observable);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
