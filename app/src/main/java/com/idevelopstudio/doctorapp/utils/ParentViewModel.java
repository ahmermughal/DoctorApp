package com.idevelopstudio.doctorapp.utils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import timber.log.Timber;

public class ParentViewModel extends ViewModel {

    public MutableLiveData<States> _states = new MutableLiveData<States>();
    public LiveData<States> states = _states;

    public LiveData<States> getStates() {
        return _states;
    }

    public void showLoading() {
        _states.postValue(States.LOADING);
    }

    public void hasData() {
        _states.postValue(States.NOT_EMPTY);
    }

    public void hasNoData() {
        _states.postValue(States.EMPTY);
    }

    public void connectionError(){
        _states.postValue(States.NO_CONNECTION);
    }

}
