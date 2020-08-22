package com.idevelopstudio.doctorapp.auth;

import android.content.Context;
import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import id.zelory.compressor.Compressor;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class AuthDoctorViewModel extends ViewModel {

    private MutableLiveData<String> _firstName = new MutableLiveData<>();
    public LiveData<String> firstName = _firstName;

    private MutableLiveData<String> _lastName = new MutableLiveData<>();
    public LiveData<String> lastName = _lastName;

    private MutableLiveData<String> _country = new MutableLiveData<>();
    public LiveData<String> country = _country;

    private MutableLiveData<String> _pdmcNumber = new MutableLiveData<>();
    public LiveData<String> pdmcNumber = _pdmcNumber;

    private MutableLiveData<List<Uri>> _imageUris = new MutableLiveData<>();
    public LiveData<List<Uri>> imageUris = _imageUris;



    private Observable<List<Uri>> uriListObservable;

//    public LiveData<List<Uri>> compressedImages = Transformations.map(imageUris, (list) ->{
//        File file = new File(list.get(0).getPath());
//        //File file =  Compressor.INSTANCE.compress(this,  )
//
//    });


    public Observable<List<Uri>> getUriListObservable() {
        return uriListObservable;
    }

    public void setUriListObservable(Observable<List<Uri>> uriListObservable) {
        this.uriListObservable = uriListObservable;
    }

    public void setfirstName(String firstName) {
        this._firstName.setValue(firstName);
    }

    public void setlastName(String lastName) {
        this._lastName.setValue(lastName);
    }

    public void setpdmcNumber(String pdmcNumber) {
        this._pdmcNumber.setValue(pdmcNumber);
    }

    public void setimageUris(List<Uri> imageUris) {
        this._imageUris.setValue(imageUris);
    }

    public void setCountry(String country){ this._country.setValue(country);}

    public Observable<List<File>> getCompressedImagesObserver(Context context){
        if (uriListObservable == null) return null;

        return uriListObservable
                .observeOn(Schedulers.computation())
                .map(uriList -> {
                    List<File> compressedFiles = new ArrayList<>();
                    for (Uri uri: uriList){
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

}
