package com.idevelopstudio.doctorapp.authDoctorSpeciality;

import android.view.View;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.models.Token;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class AuthDoctorSpecialityViewModel extends ParentViewModel {

    public AuthDoctorSpecialityViewModel() {
        compositeDisposable = new CompositeDisposable();
        _states.setValue(States.NOT_EMPTY);
    }

    private MutableLiveData<Boolean> _didNavigateToDoctorMain = new MutableLiveData<>();
    public LiveData<Boolean> didNavigateToDoctorMain = _didNavigateToDoctorMain;

    private void navigateToDoctorMain(){
        _didNavigateToDoctorMain.postValue(false);
    }
    public void doneNavigateToDoctorMain(){
        _didNavigateToDoctorMain.postValue(true);
    }

    private CompositeDisposable compositeDisposable;

    public LiveData<Integer> fabVisible  = Transformations.map(states, state -> {
       if (state == States.LOADING){
           return View.GONE;
       } else{
           return View.VISIBLE;
       }
    });

    public void createDoctorPakistan(String uid, String email, String firstName, String lastName, String country, String pmdcNumber, ArrayList<Speciality> specializationsList) {
        showLoading();
        StringBuilder specializations = new StringBuilder();
        for (Speciality speciality : specializationsList) {
            if (specializations.length() == 0) {
                specializations.append(speciality.getTitle());
            } else {
                specializations.append("," + speciality.getTitle());
            }
        }
        MultipartBody.Part uidPart = MultipartBody.Part.createFormData("d_id", uid);
        MultipartBody.Part emailPart = MultipartBody.Part.createFormData("email", email);
        MultipartBody.Part fNamePart = MultipartBody.Part.createFormData("f_name", firstName);
        MultipartBody.Part lNamePart = MultipartBody.Part.createFormData("l_name", lastName);
        MultipartBody.Part countryPart = MultipartBody.Part.createFormData("country", country);
        MultipartBody.Part pmdcPart = MultipartBody.Part.createFormData("pmdc_id", pmdcNumber);
        MultipartBody.Part specPart = MultipartBody.Part.createFormData("specializations", specializations.toString());
        MultipartBody.Part uploadPart = MultipartBody.Part.createFormData("uploads", "123");



        Disposable disposable = NetworkManager.getInstance().getDoctorApi().doctorSignUpPakistan(
                uidPart,
                emailPart,
                fNamePart,
                lNamePart,
                countryPart,
                pmdcPart,
                specPart,
                uploadPart
        ).subscribeOn(Schedulers.io())
                .doOnNext(token -> showLoading())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(token -> {
                            if (token.getToken() != null) {
                                hasData();
                                navigateToDoctorMain();
                                Timber.d(token.getToken());
                            } else {
                                hasNoData();
                                Timber.d("User Not Created");
                            }
                        },
                        throwable -> {
                            connectionError();
                            Timber.d(throwable);
                        });

        compositeDisposable.add(disposable);
    }

    public void createDoctorOther(String uid, String email, String firstName, String lastName, String country, ArrayList<Speciality> specializationsList, Observable<List<File>> imageObserver) {
        Disposable imageDisposable = imageObserver.doOnNext(files -> showLoading())
                .subscribe(files -> {
                    StringBuilder specializations = new StringBuilder();
                    for (Speciality speciality : specializationsList) {
                        if (specializations.length() == 0) {
                            specializations.append(speciality.getTitle());
                        } else {
                            specializations.append("," + speciality.getTitle());
                        }
                    }

                    MultipartBody.Part uidPart = MultipartBody.Part.createFormData("d_id", uid);
                    MultipartBody.Part emailPart = MultipartBody.Part.createFormData("email", email);
                    MultipartBody.Part fNamePart = MultipartBody.Part.createFormData("f_name", firstName);
                    MultipartBody.Part lNamePart = MultipartBody.Part.createFormData("l_name", lastName);
                    MultipartBody.Part countryPart = MultipartBody.Part.createFormData("country", country);
                    MultipartBody.Part specPart = MultipartBody.Part.createFormData("specializations", specializations.toString());
                    List<MultipartBody.Part> imageParts = new ArrayList<>();

                    for (File file : files) {
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        imageParts.add(MultipartBody.Part.createFormData("uploads", file.getName(), requestBody));
                    }

                    Disposable networkDisposable = NetworkManager.getInstance().getDoctorApi().doctorSignUpOther(
                            uidPart,
                            emailPart,
                            fNamePart,
                            lNamePart,
                            countryPart,
                            specPart,
                            imageParts
                    ).subscribeOn(Schedulers.io())
                            .doOnNext(token -> showLoading())
                            .subscribe(token -> {
                                        if (token.getToken() != null) {
                                            hasData();
                                            navigateToDoctorMain();
                                            Timber.d(token.getToken());
                                        } else {
                                            hasNoData();
                                            Timber.d("User Not Created");
                                        }
                                    },
                                    throwable -> {
                                        Timber.d(throwable);
                                    });

                    compositeDisposable.add(networkDisposable);
                });
        compositeDisposable.add(imageDisposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
