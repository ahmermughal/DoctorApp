package com.idevelopstudio.doctorapp.userAskedQuestions;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.idevelopstudio.doctorapp.models.UserQuery;
import com.idevelopstudio.doctorapp.network.NetworkManager;
import com.idevelopstudio.doctorapp.singleton.TokenSingleton;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import timber.log.Timber;

public class UserAskedQueryDataSource extends PageKeyedDataSource<Integer, UserQuery> {

    private static final int FIRST_PAGE = 1;
    private static final String SITE_NAME = "DoctorApp";

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, UserQuery> callback) {
        NetworkManager.getInstance().getUserApi().getUserQueries(TokenSingleton.getInstance().getToken(), "SGUmdxRwTucLfJ2mlkrNbpvID8i1", FIRST_PAGE)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userQueriesResponse -> {
                            if (userQueriesResponse != null) {
                                for (UserQuery userQuery : userQueriesResponse.getUserQueries()){
                                    Timber.d(userQuery.getQuestionTitle());
                                }
                                callback.onResult(userQueriesResponse.getUserQueries(), null, FIRST_PAGE + 1);
                            }
                        },
                        Timber::d);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserQuery> callback) {
        NetworkManager.getInstance().getUserApi().getUserQueries(TokenSingleton.getInstance().getToken(), "SGUmdxRwTucLfJ2mlkrNbpvID8i1", params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userQueriesResponse -> {
                            if (userQueriesResponse != null) {
                                Integer key = (params.key > 1) ? params.key - 1 : null;
                                Timber.d(userQueriesResponse.getUserQueries().toString());

                                callback.onResult(userQueriesResponse.getUserQueries(), key);
                            }
                        },
                        Timber::d);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, UserQuery> callback) {
        NetworkManager.getInstance().getUserApi().getUserQueries(TokenSingleton.getInstance().getToken(), "SGUmdxRwTucLfJ2mlkrNbpvID8i1", params.key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userQueriesResponse -> {
                            if (userQueriesResponse != null) {

                                Integer key = (params.key < userQueriesResponse.getNumberOfPages()) ? params.key + 1 : null;
                                Timber.d(userQueriesResponse.getUserQueries().toString());

                                callback.onResult(userQueriesResponse.getUserQueries(), key);
                            }
                        },
                        Timber::d);
    }


}
