package com.idevelopstudio.doctorapp.userAskedQuestions;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.idevelopstudio.doctorapp.models.UserQuery;

public class UserAskedQueryDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, UserQuery>> userQueryLiveDataSource = new MutableLiveData<>();

    @NonNull
    @Override
    public DataSource create() {
        UserAskedQueryDataSource userAskedQueryDataSource = new UserAskedQueryDataSource();
        userQueryLiveDataSource.postValue(userAskedQueryDataSource);
        return userAskedQueryDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, UserQuery>> getUserQueryLiveDataSource() {
        return userQueryLiveDataSource;
    }
}
