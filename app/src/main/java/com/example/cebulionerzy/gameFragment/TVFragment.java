package com.example.cebulionerzy.gameFragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.cebulionerzy.MainActivity;
import com.example.cebulionerzy.R;

import pl.droidsonroids.gif.GifImageView;

public class TVFragment extends Fragment {
    private ImageView aid_help_icon, reward_list_icon, exit_icon, tv_icon;
    private LinearLayout next_channel_button;
    private GifImageView tv_kanal;
    private String[] tvChannals = {"taka_sytuacja", "janusz_gif", "wielcy_polacy", "adam_malysz", "bede_gral", "bolek_i_lolek",
            "czterej_pancerni", "dlaczego_ja", "familiada", "gargamel", "gumisie", "hans_closs", "janosik", "jeden_z_10",
            "kabaret1", "kabaret2", "kevin", "kiepscy", "kuba_wojewodzki", "kubica", "magda_gessler", "maklowicz",
            "mam_talent", "miesny_jez", "milonerzy", "miodowe_lata", "nat_geo_wild", "pilka_nozna", "pilsudski",
            "pogoda", "pudzian", "ramsey_gordon", "ranczo", "reksio", "shrek", "siatkowka", "smok_wawelski", "tabaluga",
            "teleexpress", "tenis", "wilk_i_zajac", "wydarzenia", "zenek", "zuzel", "zwirek_i_muchomorek"};

    private final Handler handler = new Handler(Looper.getMainLooper());
    private int channelId, channel;

    public TVFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_t_v, container, false);

        aid_help_icon = v.findViewById(R.id.aid_help_icon);
        tv_icon = v.findViewById(R.id.tv_icon);
        reward_list_icon = v.findViewById(R.id.reward_list_icon);
        exit_icon = v.findViewById(R.id.exit_icon);
        tv_kanal = v.findViewById(R.id.tv_kanal);
        next_channel_button = v.findViewById(R.id.next_channel_button);

        channelId = (int) ((Math.random() * (tvChannals.length)) + 0);
        channel = getResources().getIdentifier(tvChannals[channelId],"drawable", getActivity().getPackageName());
        tv_kanal.setImageResource(channel);

        aid_help_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LifebuoyFragment()).commit();
            }
        });

        reward_list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AwardBoardFragment()).commit();
            }
        });

        exit_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        // Zmiana kanału w TV
        next_channel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int number = (int) (Math.random() * 10);

                if(number%2 == 0) {
                    tv_kanal.setImageResource(R.drawable.szum);
                }
                else {
                    tv_kanal.setImageResource(R.drawable.tv_glitch);
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        channelId = (int) ((Math.random() * (tvChannals.length)) + 0);
                        channel = getResources().getIdentifier(tvChannals[channelId],"drawable", getActivity().getPackageName());
                        tv_kanal.setImageResource(channel);
                    }
                },1000);
            }
        });

        return v;
    }
}