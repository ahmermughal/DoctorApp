package com.idevelopstudio.doctorapp.userMain;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UserMainViewModel extends ParentViewModel {

    Flowable<ArrayList<Speciality>> observable;


    public UserMainViewModel(Observable<String> observable) {
        this.observable = observable.toFlowable(BackpressureStrategy.BUFFER)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(s -> showLoading())
                .observeOn(Schedulers.io())
                .map(this::getFilteredSpecialities)
                .observeOn(AndroidSchedulers.mainThread());
        _states.setValue(States.NOT_EMPTY);
    }

    ArrayList<Speciality> getSpecialities() {
        ArrayList<Speciality> specialities = new ArrayList<>();
        specialities.add(new Speciality("Anesthesiology", R.drawable.syringe, R.color.pastelBlue));
        specialities.add(new Speciality("Dentistry", R.drawable.tooth, R.color.pastelGreen));
        specialities.add(new Speciality("ENT", R.drawable.ear, R.color.pastelPink));
        specialities.add(new Speciality("Eye", R.drawable.eye, R.color.pastelSoftPink));
        specialities.add(new Speciality("Obstetrics & Gynaecology", R.drawable.gynaecology, R.color.pastelYellow));
        specialities.add(new Speciality("Medicine", R.drawable.pill, R.color.pastelPurple));
        specialities.add(new Speciality("Paediatrics", R.drawable.toys, R.color.pastelBabyBlue));
        specialities.add(new Speciality("Surgery", R.drawable.surgery, R.color.pastelLightPurple));
        specialities.add(new Speciality("Others", R.drawable.doctor_other, R.color.pastelMaroon));
        return specialities;
    }

    private ArrayList<Speciality> getFilteredSpecialities(String str) {
        if (str.isEmpty()) return getSpecialities();
        ArrayList<Speciality> list = getSpecialities();
        ArrayList<Speciality> newList = new ArrayList();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getTitle().toLowerCase().contains(str.toLowerCase()))
                newList.add(list.get(i));
        }
        return newList;
    }

}
