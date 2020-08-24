package com.idevelopstudio.doctorapp.userSubmitQuery;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.idevelopstudio.doctorapp.customViews.CriticalStatus;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.BackpressureStrategy;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import timber.log.Timber;

public class UserSubmitQueryViewModel extends ParentViewModel {
    private CompositeDisposable compositeDisposable;

    Flowable<CriticalStatus> observable;

    private MutableLiveData<List<Uri>> _imageUris = new MutableLiveData<>();
    public LiveData<List<Uri>> imageUris = _imageUris;

    private Observable<List<Uri>> uriListObservable;

    private CriticalStatus criticalStatus;

    public CriticalStatus getCriticalStatus() {
        return criticalStatus;
    }

    public void setCriticalStatus(CriticalStatus criticalStatus) {
        this.criticalStatus = criticalStatus;
    }

    public UserSubmitQueryViewModel(Observable<CriticalStatus> observable) {
        this.observable = observable.toFlowable(BackpressureStrategy.LATEST)
                .observeOn(AndroidSchedulers.mainThread());
        _states.setValue(States.NOT_EMPTY);
        compositeDisposable = new CompositeDisposable();
    }


    public Observable<List<Uri>> getUriListObservable() {
        return uriListObservable;
    }

    public void setUriListObservable(Observable<List<Uri>> uriListObservable) {
        this.uriListObservable = uriListObservable;
    }

    public void setimageUris(List<Uri> imageUris) {
        this._imageUris.setValue(imageUris);
    }


    public Observable<List<File>> getCompressedImagesObserver(Context context) {
        if (uriListObservable == null) return null;

        return uriListObservable
                .observeOn(Schedulers.computation())
                .map(uriList -> {
                    List<File> compressedFiles = new ArrayList<>();
                    for (Uri uri : uriList) {
                        File imageFile = new File(uri.getPath());
                        Timber.d("Before: %s", imageFile.length() / 1024);
                        File compressedImage = new Compressor(context).setQuality(60).compressToFile(imageFile);
                        Timber.d("After: %s", compressedImage.length() / 1024);
                        compressedFiles.add(compressedImage);
                    }
                    return compressedFiles;
                })
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void createUserQuery(String token, String uid, String question, String questionDesc, String specialization, Context context) {

        Disposable disposable = getCompressedImagesObserver(context).doOnNext(files -> showLoading())
                .subscribe(files -> {

                    MultipartBody.Part uidPart = MultipartBody.Part.createFormData("patient_id", uid);
                    MultipartBody.Part questionPart = MultipartBody.Part.createFormData("question", question);
                    MultipartBody.Part questionDescPart = MultipartBody.Part.createFormData("question_desc", questionDesc);
                    MultipartBody.Part specializationPart = MultipartBody.Part.createFormData("specialization_id", specialization);
                    MultipartBody.Part criticalStatusPart = MultipartBody.Part.createFormData("critical_status", getCriticalStatus().getValue());
                    List<MultipartBody.Part> imageParts = new ArrayList<>();

                    for (File file : files) {
                        Timber.d(file.toString());
                        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
                        imageParts.add(MultipartBody.Part.createFormData("uploads", file.getName(), requestBody));
                    }

                    Disposable networkDisposable = NetworkManager.getInstance().getUserApi().userPostQuery(token,uidPart, questionPart, questionDescPart, specializationPart, criticalStatusPart, imageParts)
                            .subscribeOn(Schedulers.io())
                            .doOnNext(queryPostResponse -> Timber.d("queery called"))
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(queryPostResponse -> {
                                      hasData();
                                      Timber.d(queryPostResponse.getMessage());
                                        Timber.d(queryPostResponse.getError());
                                        Timber.d(String.valueOf(queryPostResponse.getId()));

                                    },
                                    throwable -> {
                                        hasNoData();
                                        Timber.d(throwable);
                                    });
                    compositeDisposable.add(networkDisposable);
                });
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        compositeDisposable.dispose();
        super.onCleared();
    }
}
