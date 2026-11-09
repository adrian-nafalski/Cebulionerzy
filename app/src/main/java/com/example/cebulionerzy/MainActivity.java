package com.example.cebulionerzy;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cebulionerzy.mainFragment.AuthorFragment;
import com.example.cebulionerzy.mainFragment.LastChangesFragment;
import com.example.cebulionerzy.mainFragment.OptionsFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private LinearLayout button_play, button_options, button_author, button_exit;
    private ImageView last_changes_icon;
    public static List<QuizModelClass> listOfQuestions; // lista pytań
    public static boolean PLAY_MAIN_THEME = true; // zmienna do ustalenia czy muzyka ma grać czy nie
    public static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        listOfQuestions = new ArrayList<>();

        // lista pytań z firebase (Firestore)
        FirebaseFirestore.getInstance().collection("Cebulionerzy-pytania").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()) {
                    listOfQuestions.clear(); // aby nie dodawać podwójnych pytań przy powtórnej rozgrywce

                    for(QueryDocumentSnapshot doc : task.getResult()) {
                        Map<String,Object> ques = doc.getData();
                        String question = (String) ques.get("question");
                        String oA = (String) ques.get("oA");
                        String oB = (String) ques.get("oB");
                        String oC = (String) ques.get("oC");
                        String oD = (String) ques.get("oD");
                        String ans = (String) ques.get("ans");

                        Log.d("Document", question);
                        Log.d("Document", oA);
                        Log.d("Document", oB);
                        Log.d("Document", oC);
                        Log.d("Document", oD);
                        Log.d("Document", ans);

                        listOfQuestions.add(new QuizModelClass(question, oA, oB, oC, oD, ans));
                    }
//                    System.out.println(listOfQuestions); // do sprawdzenia zawartości listy przy użyciu debbugera
                }
            }
        });

        // lista pytań dla testów (offilne)
//        listOfQuestions.add(new QuizModelClass("1Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("2Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("3Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("4Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("5Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("6Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("7Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("8Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("9Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("10Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("11Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("12Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("13Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("14Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));
//        listOfQuestions.add(new QuizModelClass("15Co jest na początku Pana Tadeusza?", "słowo", "pismo","inwokacja", "banderola","inwokacja"));

        // odtwarzanie muzyki
        try {
            if(PLAY_MAIN_THEME) {
                if(mediaPlayer != null && mediaPlayer.isPlaying()) {

                }
                if(mediaPlayer == null) {
                    mediaPlayer = MediaPlayer.create(this,R.raw.main_theme);
                    mediaPlayer.setLooping(true);
                    mediaPlayer.start();
                }
            }
            else {
                mediaPlayer.stop();
                mediaPlayer = null;
            }
        } catch (NullPointerException e) {
            e.getStackTrace();
        }

        Intent intent = getIntent();

        last_changes_icon = findViewById(R.id.last_changes_icon);
        button_play = findViewById(R.id.button_play);
        button_options = findViewById(R.id.button_options);
        button_author = findViewById(R.id.button_author);
        button_exit = findViewById(R.id.button_exit);

        last_changes_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LastChangesFragment lastChangesFragment = new LastChangesFragment();
                lastChangesFragment.show(getSupportFragmentManager(),"LastChangesFragment");
            }
        });

        button_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) throws IndexOutOfBoundsException {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button_options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.game_main_menu, new OptionsFragment()).addToBackStack("Options").commit();
            }
        });

        button_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.game_main_menu, new AuthorFragment()).addToBackStack("Author").commit();
            }
        });

        button_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    mediaPlayer.stop();
                    mediaPlayer = null; // konieczne, ponieważ przy kolejnym uruchomieniu bez tego nie będzie muzyki
                } catch (NullPointerException e) {
                    e.getStackTrace();
                }

                Toast.makeText(MainActivity.this,"Kurła",Toast.LENGTH_SHORT).show();
                finishAndRemoveTask();
            }
        });
    }
}