package com.example.cebulionerzy.mainFragment;

import static com.example.cebulionerzy.MainActivity.PLAY_MAIN_THEME;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.cebulionerzy.R;

public class OptionsFragment extends Fragment {

    private LinearLayout button_return;
    private CheckBox main_theme_settings;
    public static SharedPreferences sharedPreferences;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_CHECKBOX = "CheckBox";

    public OptionsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_options, container, false);

        button_return = v.findViewById(R.id.button_return);
        main_theme_settings = v.findViewById(R.id.main_theme_settings);

        // Shared Preferences
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        PLAY_MAIN_THEME = sharedPreferences.getBoolean(KEY_CHECKBOX, true);
        main_theme_settings.setChecked(sharedPreferences.getBoolean(KEY_CHECKBOX,true));

        // ustawianie flagi PLAY_MAIN_THEME
        main_theme_settings.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                PLAY_MAIN_THEME = isChecked;
                main_theme_settings.setChecked(isChecked);
                editor.putBoolean(KEY_CHECKBOX, isChecked);
                editor.apply();
                Toast.makeText(getActivity(),"Aby zobaczyć zmiany, ponownie uruchom grę!", Toast.LENGTH_SHORT).show();
            }
        });

        button_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
/*
                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
*/
            }
        });

        return v;
    }
}