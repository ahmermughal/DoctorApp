package com.idevelopstudio.doctorapp.authDoctorSpeciality;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.idevelopstudio.doctorapp.CreateDoctorMutation;
import com.idevelopstudio.doctorapp.models.Speciality;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import timber.log.Timber;

public class AuthDoctorSpecialityViewModel extends ParentViewModel {

    public AuthDoctorSpecialityViewModel(){
        _states.setValue(States.NOT_EMPTY);
    }

    public void createDoctorPakistan(String uid,String email, String firstName, String lastName, String country, String pmdcNumber, ArrayList<Speciality> specializationsList){
        showLoading();
        StringBuilder specializations = new StringBuilder();
        for(Speciality speciality: specializationsList){
            if(specializations.length() == 0){
                specializations.append(speciality.getTitle());
            }else{
                specializations.append(","+speciality.getTitle());
            }
        }

        CreateDoctorMutation createDoctorMutation = CreateDoctorMutation.builder().d_id(uid)
                .email(email)
                .f_name(firstName)
                .l_name(lastName)
                .country(country)
                .pmdc_id(pmdcNumber)
                .specializations(specializations.toString())
                .build();

        NetworkManager.getInstance().getApolloClient().mutate(createDoctorMutation).enqueue(new ApolloCall.Callback<CreateDoctorMutation.Data>() {
            @Override
            public void onResponse(@NotNull Response<CreateDoctorMutation.Data> response) {
                if(!response.hasErrors()) {
                    _states.postValue(States.NOT_EMPTY);
                    Timber.d(response.toString());
                    Timber.d(response.getData().doctorSignUp().token());
                }else {
                    _states.postValue(States.EMPTY);
                }
            }

            @Override
            public void onFailure(@NotNull ApolloException e) {
                _states.postValue(States.NO_CONNECTION);
            }
        });


    }

}
