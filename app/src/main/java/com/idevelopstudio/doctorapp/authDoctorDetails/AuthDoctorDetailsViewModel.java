package com.idevelopstudio.doctorapp.authDoctorDetails;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.idevelopstudio.doctorapp.utils.ParentViewModel;

public class AuthDoctorDetailsViewModel extends ParentViewModel {

    public  AuthDoctorDetailsViewModel(){
        setSelectedCountry("");
    }

    private MutableLiveData<String> selectedCountry = new MutableLiveData<>();
    private MutableLiveData<String> doctorFirstName = new MutableLiveData<>();
    private MutableLiveData<String> doctorLastName = new MutableLiveData<>();

    // checks if selected country is not Pakistan then sets the visibility to true else false
    public LiveData<Integer> uploadIDButtonVisible = Transformations.map(selectedCountry, (selectedCountry)->{
        if (selectedCountry != null && !selectedCountry.isEmpty()){
            if (!selectedCountry.equals("Pakistan")){
                return View.VISIBLE;
            }else{
                return View.GONE;
            }
        }else {
            return View.GONE;
        }
    });

    // checks if selected country is Pakistan then sets the visibility to true else false
    public LiveData<Integer> pdmcFieldVisible = Transformations.map(selectedCountry, (selectedCountry)->{
        if (selectedCountry != null && !selectedCountry.isEmpty()){
            if (selectedCountry.equals("Pakistan")){
                return View.VISIBLE;
            }else{
                return View.GONE;
            }
        }else {
            return View.GONE;
        }
    });

    public void setSelectedCountry(String selectedCountry){
        this.selectedCountry.setValue(selectedCountry);
    }

    public Boolean isCountryEmpty(){
        return selectedCountry.getValue().isEmpty();
    }

}
