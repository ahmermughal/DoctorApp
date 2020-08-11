package com.idevelopstudio.doctorapp.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import timber.log.Timber;

public class ParentViewModel extends ViewModel {

    public MutableLiveData<States> _states = new MutableLiveData<States>();
    public LiveData<States> states;

    public LiveData<States> getStates() {
        return _states;
    }

    public void showLoading() {
        _states.setValue(States.LOADING);
    }

    public void hasData() {
        _states.setValue(States.NOT_EMPTY);
        Timber.d("Has NO Data");
    }

    public void hasNoData() {
        _states.setValue(States.EMPTY);
        Timber.d("Has Data");

    }

}
