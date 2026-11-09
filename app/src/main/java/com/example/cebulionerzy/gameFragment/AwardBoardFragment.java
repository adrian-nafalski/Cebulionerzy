package com.example.cebulionerzy.gameFragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cebulionerzy.ItemVievModel;
import com.example.cebulionerzy.MainActivity;
import com.example.cebulionerzy.R;

public class AwardBoardFragment extends Fragment {
    private TextView award_1, award_2, award_3, award_4, award_5, award_6, award_7, award_8, award_9, award_10, award_11, award_12;

    public AwardBoardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_award_board, container, false);

        ImageView aid_help_icon = v.findViewById(R.id.aid_help_icon);
        ImageView tv_icon = v.findViewById(R.id.tv_icon);
        ImageView exit_icon = v.findViewById(R.id.exit_icon);

        award_1 = v.findViewById(R.id.award_1);
        award_2 = v.findViewById(R.id.award_2);
        award_3 = v.findViewById(R.id.award_3);
        award_4 = v.findViewById(R.id.award_4);
        award_5 = v.findViewById(R.id.award_5);
        award_6 = v.findViewById(R.id.award_6);
        award_7 = v.findViewById(R.id.award_7);
        award_8 = v.findViewById(R.id.award_8);
        award_9 = v.findViewById(R.id.award_9);
        award_10 = v.findViewById(R.id.award_10);
        award_11 = v.findViewById(R.id.award_11);
        award_12 = v.findViewById(R.id.award_12);

        aid_help_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LifebuoyFragment()).commit();
            }
        });

        tv_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TVFragment()).commit();
            }
        });

        exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return v;
    }

    /**
     * Funkcja obsługująca mechanizm przechodzenia po tablicy wyników.
     * Sprawdzany jest nr_indexu i porównywany z textem odpowiadającym
     * nagrodzie jaka jest przyznawana za pytanie.
     */
    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemVievModel itemVievModel = new ViewModelProvider(requireActivity()).get(ItemVievModel.class);
        itemVievModel.getSelectedItem().observe(getActivity(), item -> {
            try {
                if(item == 0) {
                    award_1.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 1) {
                    award_1.setBackgroundDrawable(null);
                    award_2.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 2) {
                    award_2.setBackgroundDrawable(null);
                    award_3.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 3) {
                    award_3.setBackgroundDrawable(null);
                    award_4.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 4) {
                    award_4.setBackgroundDrawable(null);
                    award_5.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 5) {
                    award_5.setBackgroundDrawable(null);
                    award_6.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 6) {
                    award_6.setBackgroundDrawable(null);
                    award_7.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 7) {
                    award_7.setBackgroundDrawable(null);
                    award_8.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 8) {
                    award_8.setBackgroundDrawable(null);
                    award_9.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 9) {
                    award_9.setBackgroundDrawable(null);
                    award_10.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 10) {
                    award_10.setBackgroundDrawable(null);
                    award_11.setBackground(getActivity().getDrawable(R.color.light_green));
                }
                if(item == 11) {
                    award_11.setBackgroundDrawable(null);
                    award_12.setBackground(getActivity().getDrawable(R.color.light_green));
                }
            } catch (RuntimeException e) {
                e.getStackTrace();
            }
        });
    }
}