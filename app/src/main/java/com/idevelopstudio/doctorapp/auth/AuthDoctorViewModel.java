package com.idevelopstudio.doctorapp.auth;

import android.net.Uri;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class AuthDoctorViewModel extends ViewModel {

    private MutableLiveData<String> _firstName = new MutableLiveData<>();
    public LiveData<String> firstName = _firstName;

    private MutableLiveData<String> _lastName = new MutableLiveData<>();
    public LiveData<String> lastName = _lastName;

    private MutableLiveData<String> _pdmcNumber = new MutableLiveData<>();
    public LiveData<String> pdmcNumber = _pdmcNumber;

    private MutableLiveData<List<Uri>> _imageUris = new MutableLiveData<>();
    public LiveData<List<Uri>> imageUris = _imageUris;

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
}
