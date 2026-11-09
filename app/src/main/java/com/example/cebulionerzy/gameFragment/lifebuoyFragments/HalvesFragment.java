package com.example.cebulionerzy.gameFragment.lifebuoyFragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.DialogFragment;

import com.example.cebulionerzy.R;

import java.util.Objects;

public class HalvesFragment extends DialogFragment {

    public HalvesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.fragment_halves, container, false);

        Handler timeHandler = new Handler(Looper.getMainLooper());

        // wychodzenie z fragmentu HalvesFragment po czasie 1500 milisekund
        timeHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Objects.requireNonNull(getDialog()).dismiss();
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }
            }
        }, 1500);
        return v;
    }
}
