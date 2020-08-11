package com.idevelopstudio.doctorapp.userSubmitQuery;

import com.idevelopstudio.doctorapp.customViews.CriticalStatus;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

public class UserSubmitQueryViewModel extends ParentViewModel {

    Flowable<CriticalStatus> observable;

    public UserSubmitQueryViewModel(Observable<CriticalStatus> observable){
        this.observable = observable.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
        _states.setValue(States.NOT_EMPTY);
    }

}
