package com.idevelopstudio.doctorapp.userSubmitQuery;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.idevelopstudio.doctorapp.customViews.CriticalStatus;
import com.idevelopstudio.doctorapp.userMain.UserMainViewModel;

import io.reactivex.rxjava3.core.Observable;

public class UserSubmitQueryViewModelFactory implements ViewModelProvider.Factory {
    private Observable<CriticalStatus> observable;

    public UserSubmitQueryViewModelFactory(Observable<CriticalStatus> observable) {
        this.observable = observable;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UserSubmitQueryViewModel.class)){
            return (T) new UserSubmitQueryViewModel(observable);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
