package com.example.cebulionerzy.gameFragment.lifebuoyFragments;

import static com.example.cebulionerzy.MainActivity.listOfQuestions;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cebulionerzy.ItemVievModel;
import com.example.cebulionerzy.QuizModelClass;
import com.example.cebulionerzy.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class HalvesFragment extends DialogFragment {

    private QuizModelClass quizModelClass;
    private String correct_Ans = "";
    private int ansToDel1, ansToDel2;
    private List<String> listAnsToDelete = new ArrayList<>();

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

    /**
     * Funkcja obsługująca mechanizm wyboru odpowiedzi 50 : 50
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ItemVievModel itemVievModel = new ViewModelProvider(requireActivity()).get(ItemVievModel.class);
        itemVievModel.getSelectedItem().observe(getActivity(), item -> {
            try {
                quizModelClass = listOfQuestions.get(item);
                correct_Ans = quizModelClass.getAns();

                // sprawdzanie czy poprawnoą odpowiedzią jest A,B,C lub D, jeżeli nie to
                // odpowiedź jest dodawana do listy do odsztrzelenia
                if(quizModelClass.getoA().equals(correct_Ans)) {
                    correct_Ans = quizModelClass.getoA();
                }
                else {
                    listAnsToDelete.add(quizModelClass.getoA());
                }
                if(quizModelClass.getoB().equals(correct_Ans)) {
                    correct_Ans = quizModelClass.getoB();
                }
                else {
                    listAnsToDelete.add(quizModelClass.getoB());
                }
                if(quizModelClass.getoC().equals(correct_Ans)) {
                    correct_Ans = quizModelClass.getoC();
                }
                else {
                    listAnsToDelete.add(quizModelClass.getoC());
                }
                if(quizModelClass.getoD().equals(correct_Ans)) {
                    correct_Ans = quizModelClass.getoD();
                }
                else {
                    listAnsToDelete.add(quizModelClass.getoD());
                }

                genIdxToDel();

                System.out.println(listAnsToDelete);
                System.out.println("ansToDel1: " + ansToDel1 + " ansToDel2: " + ansToDel2);

            } catch (RuntimeException e) {
                e.getStackTrace();
            }
        });
    }

    /**
     * Funkcja losująca losowe indexy z zakresu 0...2 (bo 3 odp złe a 1 dobra więc nie ma co losować)
     */
    private void genIdxToDel() {
        Random r = new Random();

        while (ansToDel1 == ansToDel2) {
            ansToDel1 = r.nextInt(3);
            ansToDel2 = r.nextInt(3);
        }
    }
}