package com.idevelopstudio.doctorapp.authUserDetails;

import com.idevelopstudio.doctorapp.utils.ParentViewModel;
import com.idevelopstudio.doctorapp.utils.States;

import org.jetbrains.annotations.NotNull;

import timber.log.Timber;

public class AuthUserDetailsViewModel extends ParentViewModel {

    public AuthUserDetailsViewModel(){_states.setValue(States.NOT_EMPTY);}

    public void createUser(String uid, String email,String firstName, String lastName, String age, String weight, String height){

//        CreateUserMutation createUserMutation = CreateUserMutation.builder().p_id(uid)
//                .email(email)
//                .f_name(firstName)
//                .l_name(lastName)
//                .age(age)
//                .height(height)
//                .weight(weight).build();
//
//        NetworkManager.getInstance().getApolloClient().mutate(createUserMutation).enqueue(new ApolloCall.Callback<CreateUserMutation.Data>() {
//            @Override
//            public void onResponse(@NotNull Response<CreateUserMutation.Data> response) {
//                Timber.d(response.toString());
//            }
//
//            @Override
//            public void onFailure(@NotNull ApolloException e) {
//                Timber.d(e);
//            }
//        });


    }

}
