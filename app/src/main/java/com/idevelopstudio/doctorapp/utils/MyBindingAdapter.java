package com.idevelopstudio.doctorapp.utils;

import android.view.View;
import android.widget.ProgressBar;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;

public final class MyBindingAdapter{

    @BindingAdapter("progressBarState")
    public static void showProgressBarBinding(ProgressBar progressBar, States state){
        switch (state){
            case EMPTY:
            case NOT_EMPTY:
            case NO_CONNECTION:
                progressBar.setVisibility(View.GONE);
                break;
            case LOADING:
                progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @BindingAdapter("recyclerViewState")
    public static void recyclerViewState(RecyclerView recyclerView, States state){
        switch (state){
            case EMPTY:
            case NOT_EMPTY:
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case LOADING:
            case NO_CONNECTION:
                recyclerView.setVisibility(View.INVISIBLE);
        }
    }

    @BindingAdapter("materialViewState")
    public static void materialViewState(MaterialCardView cardView, States state){
        switch (state){
            case EMPTY:
            case NOT_EMPTY:
                cardView.setVisibility(View.VISIBLE);
                break;
            case LOADING:
            case NO_CONNECTION:
                cardView.setVisibility(View.INVISIBLE);
        }
    }
}

