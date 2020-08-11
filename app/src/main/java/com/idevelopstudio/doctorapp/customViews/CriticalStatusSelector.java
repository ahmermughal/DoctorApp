package com.idevelopstudio.doctorapp.customViews;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.idevelopstudio.doctorapp.R;
import com.idevelopstudio.doctorapp.databinding.CriticalStatusSelectorLayoutBinding;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import timber.log.Timber;


public class CriticalStatusSelector extends ConstraintLayout {

    private CriticalStatusSelectorLayoutBinding binding;

    private CriticalStatus criticalStatus;

    public CriticalStatusSelector(Context context) {
        super(context);
    }

    public CriticalStatusSelector(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CriticalStatusSelector(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.critical_status_selector_layout, this, true);
        criticalStatus = CriticalStatus.LOW;
    }


    public Observable<CriticalStatus> createBarStatusObservable() {
        Observable<CriticalStatus> barStatusObservable = Observable.create(emitter -> {
            OnClickListener onClickListener = v -> {
                settingButtonStatus((String) v.getTag());
                emitter.onNext(criticalStatus);
            };
            binding.buttonLow.setOnClickListener(onClickListener);
            binding.buttonMod.setOnClickListener(onClickListener);
            binding.buttonHigh.setOnClickListener(onClickListener);
        });
        return barStatusObservable;
    }

    private void settingButtonStatus(String s) {
        switch (s) {
            case "1":
                setLowAsActive();
                break;
            case "2":
                setModAsActive();
                break;
            case "3":
                setHighAsActive();
                break;
        }
    }

    private void setLowAsActive() {
        criticalStatus = CriticalStatus.LOW;
        Timber.d(String.valueOf(binding.buttonLow.getX()));
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.moveableSlider, View.TRANSLATION_X, binding.buttonLow.getX() - binding.moveableSlider.getWidth());
        disableDuringAnimation(binding.buttonLow, binding.buttonMod, binding.buttonHigh, animator);
        animator.start();
        binding.moveableSliderTextView.setText("LOW");
    }

    private void setModAsActive() {
        criticalStatus = CriticalStatus.MODERATE;
        Timber.d(String.valueOf(binding.buttonMod.getX()));
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.moveableSlider, View.TRANSLATION_X, binding.buttonMod.getX() - binding.moveableSlider.getWidth());
        disableDuringAnimation(binding.buttonLow, binding.buttonMod, binding.buttonHigh, animator);
        animator.start();
        binding.moveableSliderTextView.setText("MODERATE");
    }

    private void setHighAsActive() {
        criticalStatus = CriticalStatus.HIGH;
        ObjectAnimator animator = ObjectAnimator.ofFloat(binding.moveableSlider, View.TRANSLATION_X, binding.buttonHigh.getX() - binding.moveableSlider.getWidth());
        disableDuringAnimation(binding.buttonLow, binding.buttonMod, binding.buttonHigh, animator);
        animator.start();
        binding.moveableSliderTextView.setText("HIGH");
    }

    private void disableDuringAnimation(View buttonLow, View buttonMod, View buttonHigh, ObjectAnimator animator) {
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                buttonLow.setEnabled(true);
                buttonMod.setEnabled(true);
                buttonHigh.setEnabled(true);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                buttonLow.setEnabled(false);
                buttonMod.setEnabled(false);
                buttonHigh.setEnabled(false);
            }
        });
    }

}
