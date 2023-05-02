package com.example.cebulionerzy.gameFragment;

import static com.example.cebulionerzy.GameActivity.HALVES_USED;
import static com.example.cebulionerzy.GameActivity.NATION_USED;
import static com.example.cebulionerzy.GameActivity.PHONE_USED;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.fragment.app.Fragment;

import com.example.cebulionerzy.MainActivity;
import com.example.cebulionerzy.R;
import com.example.cebulionerzy.gameFragment.lifebuoyFragments.HalvesFragment;
import com.example.cebulionerzy.gameFragment.lifebuoyFragments.NationFragment;
import com.example.cebulionerzy.gameFragment.lifebuoyFragments.PhoneFragment;

public class LifebuoyFragment extends Fragment {
    private ImageView aid_help_icon, reward_list_icon, exit_icon, tv_icon;
    private LinearLayout narod, poloweczka, telefon_do_pjotera;

    public LifebuoyFragment() {
        // Required empty public constructor
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lifebuoy, container, false);

        aid_help_icon = v.findViewById(R.id.aid_help_icon);
        tv_icon = v.findViewById(R.id.tv_icon);
        reward_list_icon = v.findViewById(R.id.reward_list_icon);
        exit_icon = v.findViewById(R.id.exit_icon);

        narod = v.findViewById(R.id.narod);
        poloweczka = v.findViewById(R.id.poloweczka);
        telefon_do_pjotera = v.findViewById(R.id.telefon_do_pjotera);

        if(NATION_USED) {
            narod.setBackground(getActivity().getDrawable(R.drawable.inactive_nation_icon));
        }
        if(HALVES_USED) {
            poloweczka.setBackground(getActivity().getDrawable(R.drawable.inactive_halves_icon));
        }
        if(PHONE_USED) {
            telefon_do_pjotera.setBackground(getActivity().getDrawable(R.drawable.inactive_phone_icon));
        }

        reward_list_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AwardBoardFragment()).commit();
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
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        narod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!NATION_USED) {
                    NATION_USED = true;
                    NationFragment nationFragment = new NationFragment();
                    nationFragment.show(getActivity().getSupportFragmentManager(),"LastChangesFragment");
                    narod.setBackground(getActivity().getDrawable(R.drawable.inactive_nation_icon));
                }
            }
        });

        poloweczka.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                if(!HALVES_USED) {
                    HALVES_USED = true;
                    HalvesFragment halvesFragment = new HalvesFragment();
                    halvesFragment.show(getActivity().getSupportFragmentManager(),"HalvesFragment");
                    poloweczka.setBackground(getActivity().getDrawable(R.drawable.inactive_halves_icon));
                }
            }
        });

        telefon_do_pjotera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!PHONE_USED ) {
                    PHONE_USED = true;
                    PhoneFragment phoneFragment = new PhoneFragment();
                    phoneFragment.show(getActivity().getSupportFragmentManager(),"LastChangesFragment");
                    telefon_do_pjotera.setBackground(getActivity().getDrawable(R.drawable.inactive_phone_icon));
                }
            }
        });
        return v;
    }
}