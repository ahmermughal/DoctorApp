package com.idevelopstudio.doctorapp.userAskedQuestions;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.idevelopstudio.doctorapp.models.UserQuery;
import com.idevelopstudio.doctorapp.utils.ParentViewModel;

public class UserAskedQuestionsViewModel extends ParentViewModel {

    public LiveData<PagedList<UserQuery>> userQueryPagedList;

    private LiveData<PageKeyedDataSource<Integer, UserQuery>> liveDataSource;


    public UserAskedQuestionsViewModel() {
        UserAskedQueryDataSourceFactory userAskedQueryDataSourceFactory = new UserAskedQueryDataSourceFactory();
        liveDataSource = userAskedQueryDataSourceFactory.getUserQueryLiveDataSource();

        PagedList.Config config = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setPageSize(50)
                .build();
        userQueryPagedList = (new LivePagedListBuilder(userAskedQueryDataSourceFactory, config)).build();

    }
}
