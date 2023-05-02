package com.example.cebulionerzy;

import static com.example.cebulionerzy.MainActivity.listOfQuestions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.cebulionerzy.gameFragment.LoseFragment;
import com.example.cebulionerzy.gameFragment.TVFragment;
import com.example.cebulionerzy.gameFragment.WonFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameActivity extends AppCompatActivity {
    private TextView question, answer_A, answer_B, answer_C, answer_D, number_question;
    private LinearLayout A, B, C, D;
    private QuizModelClass quizModelClass;
    public static boolean HALVES_USED = false, NATION_USED = false, PHONE_USED = false;
    int index = 0;
    boolean clickedAnswer = false;
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game);

        Intent intent = getIntent();

        if(index == 0) {
            HALVES_USED = false;
            NATION_USED = false;
            PHONE_USED = false;
        }

        if(findViewById(R.id.fragment_container) != null) {
            if(savedInstanceState != null) {
                return;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new TVFragment()).commit();
        }

        question = findViewById(R.id.question);
        number_question = findViewById(R.id.number_question);
        answer_A = findViewById(R.id.answer_A);
        answer_B = findViewById(R.id.answer_B);
        answer_C = findViewById(R.id.answer_C);
        answer_D = findViewById(R.id.answer_D);

        A = findViewById(R.id.A);
        B = findViewById(R.id.B);
        C = findViewById(R.id.C);
        D = findViewById(R.id.D);

        List<QuizModelClass> allQuestions = listOfQuestions;
        Collections.shuffle(allQuestions);
        quizModelClass = listOfQuestions.get(index);

        setAllData();
    }

    @SuppressLint("SetTextI18n")
    private void setAllData() {
        clickedAnswer = false;

        question.setText(quizModelClass.getQuestion());
        number_question.setText("Pytanie " + (index + 1) + " / 12");
        answer_A.setText("A:\t\t" + quizModelClass.getoA());
        answer_B.setText("B:\t\t" + quizModelClass.getoB());
        answer_C.setText("C:\t\t" + quizModelClass.getoC());
        answer_D.setText("D:\t\t" + quizModelClass.getoD());

        // wysyłanie indexu pytania do AwardBoardFragment, PhoneFragment i NationFragment
        ItemVievModel itemVievModel = new ViewModelProvider(GameActivity.this).get(ItemVievModel.class);
        itemVievModel.setData(index);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void correctAnswer(LinearLayout answer) throws InterruptedException {

/*
        // do animacij menu głównego

        answer.setBackground(getDrawable(R.drawable.checking_answer_animation));
        AnimationDrawable animationDrawable = (AnimationDrawable) answer.getBackground();
        animationDrawable.setEnterFadeDuration(1000);
        animationDrawable.setExitFadeDuration(1000);
        animationDrawable.start();
*/
        answer.setBackground(getDrawable(R.drawable.answer_check));
        handler.postDelayed(new Runnable() {
            public void run() {
                answer.setBackground(getDrawable(R.drawable.answer_correct));
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(index<11) {
                            try {
                                index++;
                                quizModelClass = listOfQuestions.get(index);
                                setAllData();
                                resetColor();
                            } catch (IndexOutOfBoundsException e) {
                                e.getStackTrace();
                            }
                        }
                        else {
                            gameWon();
                        }
                    }
                }, 2500);
            }
        }, 2500);

    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void wrongAnswer(LinearLayout answer) throws InterruptedException {

        answer.setBackground(getDrawable(R.drawable.answer_check));
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                answer.setBackground(getDrawable(R.drawable.answer_wrong));

                if(quizModelClass.getoA().equals(quizModelClass.getAns())) {
                    if(index < listOfQuestions.size()) {
                        A.setBackground(getDrawable(R.drawable.answer_correct));
                    }
                }

                if(quizModelClass.getoB().equals(quizModelClass.getAns())) {
                    if(index < listOfQuestions.size()) {
                        B.setBackground(getDrawable(R.drawable.answer_correct));
                    }
                }

                if(quizModelClass.getoC().equals(quizModelClass.getAns())) {
                    if(index < listOfQuestions.size()) {
                        C.setBackground(getDrawable(R.drawable.answer_correct));
                    }
                }

                if(quizModelClass.getoD().equals(quizModelClass.getAns())) {
                    if(index < listOfQuestions.size()) {
                        D.setBackground(getDrawable(R.drawable.answer_correct));
                    }
                }

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        gameLose();
                    }
                }, 2500);
            }
        }, 2500);
    }

    private void gameWon() {
        WonFragment wonFragment = new WonFragment();
        wonFragment.setCancelable(false);
        wonFragment.show(getSupportFragmentManager(),"WonFragment");
    }

    private void gameLose() {
        LoseFragment loseFragment = new LoseFragment();
        loseFragment.setCancelable(false);
        loseFragment.show(getSupportFragmentManager(),"LoseFragment");
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void resetColor() {
        A.setBackground(getDrawable(R.drawable.odpowiedz));
        B.setBackground(getDrawable(R.drawable.odpowiedz));
        C.setBackground(getDrawable(R.drawable.odpowiedz));
        D.setBackground(getDrawable(R.drawable.odpowiedz));
    }

    public void optionAClick(View view) throws InterruptedException {
        if(!clickedAnswer) {
            clickedAnswer = true;
            if(quizModelClass.getoA().equals(quizModelClass.getAns())) {
                if(index < listOfQuestions.size()) {
                    correctAnswer(A);
                }
                else {
                    gameWon();
                }
            }
            else {
                wrongAnswer(A);
            }
        }
    }

    public void optionBClick(View view) throws InterruptedException {
        if(!clickedAnswer) {
            clickedAnswer = true;
            if(quizModelClass.getoB().equals(quizModelClass.getAns())) {
                if(index < listOfQuestions.size()) {
                    correctAnswer(B);
                }
                else {
                    gameWon();
                }
            }
            else {
                wrongAnswer(B);
            }
        }
    }

    public void optionCClick(View view) throws InterruptedException {
        if(!clickedAnswer) {
            clickedAnswer = true;
            if(quizModelClass.getoC().equals(quizModelClass.getAns())) {
                if(index < listOfQuestions.size()) {
                    correctAnswer(C);
                }
                else {
                    gameWon();
                }
            }
            else {
                wrongAnswer(C);
            }
        }
    }

    public void optionDClick(View view) throws InterruptedException {
        if(!clickedAnswer) {
            clickedAnswer = true;
            if(quizModelClass.getoD().equals(quizModelClass.getAns())) {
                if(index < listOfQuestions.size()) {
                    correctAnswer(D);
                }
                else {
                    gameWon();
                }
            }
            else {
                wrongAnswer(D);
            }
        }
    }
}