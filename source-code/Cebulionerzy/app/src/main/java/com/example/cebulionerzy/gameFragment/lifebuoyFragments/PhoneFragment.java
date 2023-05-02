package com.example.cebulionerzy.gameFragment.lifebuoyFragments;

import static com.example.cebulionerzy.MainActivity.listOfQuestions;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.cebulionerzy.ItemVievModel;
import com.example.cebulionerzy.QuizModelClass;
import com.example.cebulionerzy.R;

public class PhoneFragment extends DialogFragment {

    private TextView description;
    private QuizModelClass quizModelClass;
    private String correct_Ans = "";

    public PhoneFragment() {
        // Required empty public constructor
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View v = inflater.inflate(R.layout.fragment_phone, container, false);

        description = v.findViewById(R.id.description);

        return v;
    }

    /**
     * Funkcja obsługująca mechanizm poprawnej odpowiedzi Pjotera.
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

                String desc = "- Słuchaj Pjoter masz bojowe zadanie\n - Ta tate o co chodzi?\n - Pomóż łojcu na takie pytanko odpowiedzieć bo ty młody to pewnie będziesz wiedział. W nagrodę dam ci umyć mojego passata ;) A więc słuchaj synek ...\n - Wiesz tate co? To trudne pytanie ale wydaje mi się że poprawna odpowiedź to: ";

                if(quizModelClass.getoA().equals(correct_Ans)) {
                    correct_Ans = "A";
                }
                if(quizModelClass.getoB().equals(correct_Ans)) {
                    correct_Ans = "B";
                }
                if(quizModelClass.getoC().equals(correct_Ans)) {
                    correct_Ans = "C";
                }
                if(quizModelClass.getoD().equals(correct_Ans)) {
                    correct_Ans = "D";
                }

                description.setText(desc + correct_Ans);

            } catch (RuntimeException e) {
                e.getStackTrace();
            }
        });
    }
}