package com.idevelopstudio.doctorapp.customViews;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.MySearchBarLayoutBinding;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.functions.Cancellable;
import io.reactivex.rxjava3.functions.Predicate;

public class MySearchBar extends ConstraintLayout {

    private MySearchBarLayoutBinding binding;

    public MySearchBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public MySearchBar(Context context) {
        super(context);
    }

    public MySearchBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.my_search_bar_layout, this, true);
    }

    public Observable<String> createTextChangeObservable(){
        Observable<String> textChangeObservable = Observable.create(emitter -> {

            TextWatcher textWatcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void afterTextChanged(Editable s) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    emitter.onNext(s.toString());
                }
            };

            binding.editTextSearch.addTextChangedListener(textWatcher);

            emitter.setCancellable(() -> {
                binding.editTextSearch.removeTextChangedListener(textWatcher);
            });

        });

        return textChangeObservable.filter(s -> s.length() != 1)
                .debounce(800, TimeUnit.MILLISECONDS);
    }

}
