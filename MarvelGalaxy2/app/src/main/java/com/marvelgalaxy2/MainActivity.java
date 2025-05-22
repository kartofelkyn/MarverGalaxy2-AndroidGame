package com.marvelgalaxy2;

import android.view.animation.Animation;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioManager;
import android.os.IBinder;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.google.firebase.database.DataSnapshot;


public class MainActivity extends AppCompatActivity {
    TextView txtv_time, txtv_turn;
    TextView txtv_finaltime, txtv_finalturn, txtv_finalscore;
    CountDownTimer timer;
    VideoView videoView;
    ImageView iv_11, iv_12, iv_13, iv_14, iv_15, iv_16, iv_17, iv_18,
            iv_21, iv_22, iv_23, iv_24, iv_25, iv_26, iv_27, iv_28,
            iv_31, iv_32, iv_33, iv_34, iv_35, iv_36, iv_37, iv_38,
            iv_41, iv_42, iv_43, iv_44, iv_45, iv_46, iv_47, iv_48,
            iv_51, iv_52, iv_53, iv_54, iv_55, iv_56, iv_57, iv_58,
            iv_61, iv_62, iv_63, iv_64, iv_65, iv_66, iv_67, iv_68,
            iv_71, iv_72, iv_73, iv_74, iv_75, iv_76, iv_77, iv_78,
            iv_81, iv_82, iv_83, iv_84, iv_85, iv_86, iv_87, iv_88,

            btnReset, btnHome, btnStart, btnSetting, btnDone, btnPlayAgain, btnLeaderboards,
            btnIconLeaderboard, btnNext, iconTrophy;

    AudioManager audioManager;
    Integer[] cardsArray = {101, 102, 103, 104, 105, 106, 107, 108,
            201, 202, 203, 204, 205, 206, 207, 208};

    Integer[] cardsArrayNormal = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            111, 112, 113, 114, 115, 116, 117, 118,
            201, 202, 203, 204, 205, 206, 207, 208, 209, 210,
            211, 212, 213, 214, 215, 216, 217, 218};

    Integer[] cardsArrayHard = {101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
            111, 112, 113, 114, 115, 116, 117, 118, 119, 120,
            121, 122, 123, 124, 125, 126, 127, 128, 129, 130,
            131, 132,
            201, 202, 203, 204, 205, 206, 207, 208, 209, 210,
            211, 212, 213, 214, 215, 216, 217, 218, 219, 220,
            221, 222, 223, 224, 225, 226, 227, 228, 229, 230,
            231, 232};

    int image101, image102, image103, image104, image105, image106, image107, image108, image109, image110,
            image111, image112, image113, image114, image115, image116, image117, image118, image119, image120,
            image121, image122, image123, image124, image125, image126, image127, image128, image129, image130,
            image131, image132,
            image201, image202, image203, image204, image205, image206, image207, image208, image209, image210,
            image211, image212, image213, image214, image215, image216, image217, image218, image219, image220,
            image221, image222, image223, image224, image225, image226, image227, image228, image229, image230,
            image231, image232;

    int check11, check12, check13, check14, check15, check16, check17, check18,
            check21, check22, check23, check24, check25, check26, check27, check28,
            check31, check32, check33, check34, check35, check36, check37, check38,
            check41, check42, check43, check44, check45, check46, check47, check48,
            check51, check52, check53, check54, check55, check56, check57, check58,
            check61, check62, check63, check64, check65, check66, check67, check68,
            check71, check72, check73, check74, check75, check76, check77, check78,
            check81, check82, check83, check84, check85, check86, check87, check88;


    long hours, minutes, seconds, time;
    long initialDuration = 1 * 60 * 1000;;
    int finalScore, scoreCount;

    int firstCard, secondCard;
    int clickedFirst, clickedSecond;
    int cardNumber = 1;

    int start = 0;
    int turnCount = 0;

    int difficulty, setMusic, level;
    int savedDifficulty, savedSetMusic;
    EditText inputUsername;

    //Leaderboard
    TextView R1Username, R2Username, R3Username, R4Username, R5Username,
            R1Turn, R2Turn, R3Turn, R4Turn, R5Turn,
            R1Time, R2Time, R3Time, R4Time, R5Time,
            R1Score, R2Score, R3Score, R4Score, R5Score;

    String lbUsername = "Guest", lbDifficulty;
    String getUsernameR1, getScoreR1, getTimeR1, getTurnR1,
            getUsernameR2, getScoreR2, getTimeR2, getTurnR2,
            getUsernameR3, getScoreR3, getTimeR3, getTurnR3,
            getUsernameR4, getScoreR4, getTimeR4, getTurnR4,
            getUsernameR5, getScoreR5, getTimeR5, getTurnR5;
    int R1FinalScore = 1, R2FinalScore = 1, R3FinalScore = 1, R4FinalScore = 1, R5FinalScore = 1;
    String  R1ScoreLB, R2ScoreLB, R3ScoreLB, R4ScoreLB, R5ScoreLB,
            R1TurnLB, R2TurnLB, R3TurnLB, R4TurnLB, R5TurnLB,
            R1TimeLB, R2TimeLB, R3TimeLB, R4TimeLB, R5TimeLB,
            R1UsernameLB, R2UsernameLB, R3UsernameLB, R4UsernameLB, R5UsernameLB;

    SharedPreferences sharedPreferences;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Firabase Database
        database = FirebaseDatabase.getInstance();

        //Saving music preference
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Intent serviceIntent1 = new Intent(this, MusicService.class);
        Intent serviceIntent2 = new Intent(this, MusicService2.class);

        SharedPreferences preferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        savedDifficulty = preferences.getInt("difficulty", 1); // Default to easy if not saved
        savedSetMusic = preferences.getInt("setMusic", 1); // Default to easy if not saved
        difficulty = savedDifficulty;
        setMusic = savedSetMusic;

        if(savedSetMusic == 1){
            startService(serviceIntent1);
        }
        if(savedSetMusic == 2){
            startService(serviceIntent2);
        }
        mainMenu();
    }

    private void mainMenu(){
        Animation animShrink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonclickedanimation);

        setContentView(R.layout.screen_mainmenu);
        buttonAnimationStart();
        btnStart = findViewById(R.id.btnStart);
        btnSetting = findViewById(R.id.btnSetting);
        btnLeaderboards = findViewById(R.id.btnLeaderboards);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnStart.startAnimation(animShrink);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        difficultyS();
                    }
                }, 300);
            }
        });

        btnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSetting.startAnimation(animShrink);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Execute difficultyS() after the delay
                        setting();
                    }
                }, 300);

            }
        });

        btnLeaderboards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnLeaderboards.startAnimation(animShrink);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewLeaderboard();
                    }
                }, 300);
            }
        });

    }
    private void leaderboardS(){
    }
    private void difficultyS(){
        if(difficulty == 1 || level == 1) {
            homeGame();
        }
        if(difficulty == 2 || level == 2) {
            homeGameNormal();
        }
        if(difficulty == 3 || level == 3) {
            homeGameHard();
        }
    }
    private void mainGame(){
        setContentView(R.layout.screen_maingame_easy);
        txtv_time = findViewById(R.id.txtTime);
        txtv_turn  = findViewById(R.id.txtFlips);
        cardNumber = 1;
        btnReset = findViewById(R.id.btnReset);
        btnHome = findViewById(R.id.btnHome);
        btnPlayAgain = findViewById(R.id.btnPlayAgain);

        initialDuration = 1 * 60 * 1000;;
        startTime();

        turnCount = 30;
        String turn = "FLIPS: ";
        String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

        txtv_turn.setText(turnFormatted);

        findViewById(R.id.layout_score).setVisibility(View.INVISIBLE);
        btnPlayAgain.setEnabled(false);

        restartCheck();

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);

        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);

        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);

        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);


        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");

        iv_21.setTag("4");
        iv_22.setTag("5");
        iv_23.setTag("6");
        iv_24.setTag("7");

        iv_31.setTag("8");
        iv_32.setTag("9");
        iv_33.setTag("10");
        iv_34.setTag("11");

        iv_41.setTag("12");
        iv_42.setTag("13");
        iv_43.setTag("14");
        iv_44.setTag("15");

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);

        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);

        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArray));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;

        for (int i = 1; i <= 4; i++) {
            for (int j = 1; j <= 4; j++) {
                int resourceId = getResources().getIdentifier("iv_" + i + j, "id", getPackageName());
                ImageView imageView = findViewById(resourceId);
                animateImageViewToOriginalPosition(imageView, centerX, centerY, imageView.getX(), imageView.getY());
            }
        }


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to Reset?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                mainGame();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainGame();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to go back to Main Menu?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                backToHome();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check11 == 1){
                    return;
                }
                flipCard(iv_11);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check12 == 1){
                    return;
                }
                flipCard(iv_12);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check13 == 1){
                    return;
                }
                flipCard(iv_13);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check14 == 1){
                    return;
                }
                flipCard(iv_14);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_14, theCard);
            }
        });


        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check21 == 1){
                    return;
                }
                flipCard(iv_21);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check22 == 1){
                    return;
                }
                flipCard(iv_22);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check23 == 1){
                    return;
                }
                flipCard(iv_23);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check24 == 1){
                    return;
                }
                flipCard(iv_24);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_24, theCard);
            }
        });


        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check31 == 1){
                    return;
                }
                flipCard(iv_31);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check32 == 1){
                    return;
                }
                flipCard(iv_32);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check33 == 1){
                    return;
                }
                flipCard(iv_33);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check34 == 1){
                    return;
                }
                flipCard(iv_34);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_34, theCard);
            }
        });


        iv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check41 == 1){
                    return;
                }
                flipCard(iv_41);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_41, theCard);
            }
        });
        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check42 == 1){
                    return;
                }
                flipCard(iv_42);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_42, theCard);
            }
        });
        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check43 == 1){
                    return;
                }
                flipCard(iv_43);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_43, theCard);
            }
        });
        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check44 == 1){
                    return;
                }
                flipCard(iv_44);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuff(iv_44, theCard);
            }
        });
    }

    private void doStuff(ImageView iv, int card){
        if(cardsArray[card] == 101)
            iv.setImageResource(image101);
        else if(cardsArray[card] == 102)
            iv.setImageResource(image102);
        else if(cardsArray[card] == 103)
            iv.setImageResource(image103);
        else if(cardsArray[card] == 104)
            iv.setImageResource(image104);
        else if(cardsArray[card] == 105)
            iv.setImageResource(image105);
        else if(cardsArray[card] == 106)
            iv.setImageResource(image106);
        else if(cardsArray[card] == 107)
            iv.setImageResource(image107);
        else if(cardsArray[card] == 108)
            iv.setImageResource(image108);

        if(cardsArray[card] == 201)
            iv.setImageResource(image201);
        else if(cardsArray[card] == 202)
            iv.setImageResource(image202);
        else if(cardsArray[card] == 203)
            iv.setImageResource(image203);
        else if(cardsArray[card] == 204)
            iv.setImageResource(image204);
        else if(cardsArray[card] == 205)
            iv.setImageResource(image205);
        else if(cardsArray[card] == 206)
            iv.setImageResource(image206);
        else if(cardsArray[card] == 207)
            iv.setImageResource(image207);
        else if(cardsArray[card] == 208)
            iv.setImageResource(image208);


        if(cardNumber == 1){
            firstCard = cardsArray[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArray[card];
            if(secondCard > 200){
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);

            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);

            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);

            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);

            if(turnCount == 0){
                score();
            }else{
                turnCount--;
            }

            String turn = "FLIPS: ";
            String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

            txtv_turn.setText(turnFormatted);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculate();
                }
            }, 500);
        }
    }

    private void calculate(){
        if (firstCard == secondCard){
            if (clickedFirst == 0)
            {
                iv_11.setEnabled(false);
                scoreCount+=5;
                check11 = 1;
            }
            else if (clickedFirst == 1)
            {
                iv_12.setEnabled(false);
                scoreCount+=5;
                check12 = 1;
            }
            else if (clickedFirst == 2)
            {
                iv_13.setEnabled(false);
                scoreCount+=5;
                check13 = 1;
            }
            else if (clickedFirst == 3)
            {
                iv_14.setEnabled(false);
                scoreCount+=5;
                check14 = 1;
            }
            else if (clickedFirst == 4)
            {
                iv_21.setEnabled(false);
                scoreCount+=5;
                check21 = 1;
            }
            else if (clickedFirst == 5)
            {
                iv_22.setEnabled(false);
                scoreCount+=5;
                check22 = 1;
            }
            else if (clickedFirst == 6)
            {
                iv_23.setEnabled(false);
                scoreCount+=5;
                check23 = 1;
            }
            else if (clickedFirst == 7)
            {
                iv_24.setEnabled(false);
                scoreCount+=5;
                check24 = 1;
            }
            else if (clickedFirst == 8)
            {
                iv_31.setEnabled(false);
                scoreCount+=5;
                check31 = 1;
            }
            else if (clickedFirst == 9)
            {
                iv_32.setEnabled(false);
                scoreCount+=5;
                check32 = 1;
            }
            else if (clickedFirst == 10)
            {
                iv_33.setEnabled(false);
                scoreCount+=5;
                check33 = 1;
            }
            else if (clickedFirst == 11)
            {
                iv_34.setEnabled(false);
                scoreCount+=5;
                check34 = 1;
            }
            else if (clickedFirst == 12)
            {
                iv_41.setEnabled(false);
                scoreCount+=5;
                check41 = 1;
            }
            else if (clickedFirst == 13)
            {
                iv_42.setEnabled(false);
                scoreCount+=5;
                check42 = 1;
            }
            else if (clickedFirst == 14)
            {
                iv_43.setEnabled(false);
                scoreCount+=5;
                check43 = 1;
            }
            else if (clickedFirst == 15)
            {
                iv_44.setEnabled(false);
                scoreCount+=5;
                check44 = 1;
            }

            if (clickedSecond == 0)
            {
                iv_11.setEnabled(false);
                scoreCount+=5;
                check11 = 1;
            }
            else if (clickedSecond == 1)
            {
                iv_12.setEnabled(false);
                scoreCount+=5;
                check12 = 1;
            }
            else if (clickedSecond == 2)
            {
                iv_13.setEnabled(false);
                scoreCount+=5;
                check13 = 1;
            }
            else if (clickedSecond == 3)
            {
                iv_14.setEnabled(false);
                scoreCount+=5;
                check14 = 1;
            }
            else if (clickedSecond == 4)
            {
                iv_21.setEnabled(false);
                scoreCount+=5;
                check21 = 1;
            }
            else if (clickedSecond == 5)
            {
                iv_22.setEnabled(false);
                scoreCount+=5;
                check22 = 1;
            }
            else if (clickedSecond == 6)
            {
                iv_23.setEnabled(false);
                scoreCount+=5;
                check23 = 1;
            }
            else if (clickedSecond == 7)
            {
                iv_24.setEnabled(false);
                scoreCount+=5;
                check24 = 1;
            }
            else if (clickedSecond == 8)
            {
                iv_31.setEnabled(false);
                scoreCount+=5;
                check31 = 1;
            }
            else if (clickedSecond == 9)
            {
                iv_32.setEnabled(false);
                scoreCount+=5;
                check32 = 1;
            }
            else if (clickedSecond == 10)
            {
                iv_33.setEnabled(false);
                scoreCount+=5;
                check33 = 1;
            }
            else if (clickedSecond == 11)
            {
                iv_34.setEnabled(false);
                scoreCount+=5;
                check34 = 1;
            }
            else if (clickedSecond == 12)
            {
                iv_41.setEnabled(false);
                scoreCount+=5;
                check41 = 1;
            }
            else if (clickedSecond == 13)
            {
                iv_42.setEnabled(false);
                scoreCount+=5;
                check42 = 1;
            }
            else if (clickedSecond == 14)
            {
                iv_43.setEnabled(false);
                scoreCount+=5;
                check43 = 1;
            }
            else if (clickedSecond == 15)
            {
                iv_44.setEnabled(false);
                scoreCount+=5;
                check44 = 1;
            }

        }else{
            if(check11 == 0)
                iv_11.setImageResource(R.drawable.backcard);
            if(check12 == 0)
                iv_12.setImageResource(R.drawable.backcard);
            if(check13 == 0)
                iv_13.setImageResource(R.drawable.backcard);
            if(check14 == 0)
                iv_14.setImageResource(R.drawable.backcard);

            if(check21 == 0)
                iv_21.setImageResource(R.drawable.backcard);
            if(check22 == 0)
                iv_22.setImageResource(R.drawable.backcard);
            if(check23 == 0)
                iv_23.setImageResource(R.drawable.backcard);
            if(check24 == 0)
                iv_24.setImageResource(R.drawable.backcard);

            if(check31 == 0)
                iv_31.setImageResource(R.drawable.backcard);
            if(check32 == 0)
                iv_32.setImageResource(R.drawable.backcard);
            if(check33 == 0)
                iv_33.setImageResource(R.drawable.backcard);
            if(check34 == 0)
                iv_34.setImageResource(R.drawable.backcard);

            if(check41 == 0)
                iv_41.setImageResource(R.drawable.backcard);
            if(check42 == 0)
                iv_42.setImageResource(R.drawable.backcard);
            if(check43 == 0)
                iv_43.setImageResource(R.drawable.backcard);
            if(check44 == 0)
                iv_44.setImageResource(R.drawable.backcard);
        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);

        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);

        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);

        checkEnd();
    }
    private void mainGameNormal(){
        setContentView(R.layout.screen_maingame_normal);
        txtv_time = findViewById(R.id.txtTime);
        txtv_turn  = findViewById(R.id.txtFlips );

        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnReset = findViewById(R.id.btnReset);
        btnHome = findViewById(R.id.btnHome);
        cardNumber = 1;
        initialDuration = 3 * 60 * 1000;;
        startTime();
        turnCount = 50;
        String turn = "FLIPS: ";
        String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

        txtv_turn.setText(turnFormatted);
        findViewById(R.id.layout_score).setVisibility(View.INVISIBLE);
        btnPlayAgain.setEnabled(false);

        restartCheck();

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_15 = (ImageView) findViewById(R.id.iv_15);
        iv_16 = (ImageView) findViewById(R.id.iv_16);

        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_25 = (ImageView) findViewById(R.id.iv_25);
        iv_26 = (ImageView) findViewById(R.id.iv_26);

        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);
        iv_35 = (ImageView) findViewById(R.id.iv_35);
        iv_36 = (ImageView) findViewById(R.id.iv_36);

        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);
        iv_45 = (ImageView) findViewById(R.id.iv_45);
        iv_46 = (ImageView) findViewById(R.id.iv_46);

        iv_51 = (ImageView) findViewById(R.id.iv_51);
        iv_52 = (ImageView) findViewById(R.id.iv_52);
        iv_53 = (ImageView) findViewById(R.id.iv_53);
        iv_54 = (ImageView) findViewById(R.id.iv_54);
        iv_55 = (ImageView) findViewById(R.id.iv_55);
        iv_56 = (ImageView) findViewById(R.id.iv_56);

        iv_61 = (ImageView) findViewById(R.id.iv_61);
        iv_62 = (ImageView) findViewById(R.id.iv_62);
        iv_63 = (ImageView) findViewById(R.id.iv_63);
        iv_64 = (ImageView) findViewById(R.id.iv_64);
        iv_65 = (ImageView) findViewById(R.id.iv_65);
        iv_66 = (ImageView) findViewById(R.id.iv_66);

        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_15.setTag("4");
        iv_16.setTag("5");

        iv_21.setTag("6");
        iv_22.setTag("7");
        iv_23.setTag("8");
        iv_24.setTag("9");
        iv_25.setTag("10");
        iv_26.setTag("11");

        iv_31.setTag("12");
        iv_32.setTag("13");
        iv_33.setTag("14");
        iv_34.setTag("15");
        iv_35.setTag("16");
        iv_36.setTag("17");

        iv_41.setTag("18");
        iv_42.setTag("19");
        iv_43.setTag("20");
        iv_44.setTag("21");
        iv_45.setTag("22");
        iv_46.setTag("23");

        iv_51.setTag("24");
        iv_52.setTag("25");
        iv_53.setTag("26");
        iv_54.setTag("27");
        iv_55.setTag("28");
        iv_56.setTag("29");

        iv_61.setTag("30");
        iv_62.setTag("31");
        iv_63.setTag("32");
        iv_64.setTag("33");
        iv_65.setTag("34");
        iv_66.setTag("35");

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArrayNormal));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;

        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                int resourceId = getResources().getIdentifier("iv_" + i + j, "id", getPackageName());
                ImageView imageView = findViewById(resourceId);
                animateImageViewToOriginalPosition(imageView, centerX, centerY, imageView.getX(), imageView.getY());
            }
        }


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to Reset?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                mainGameNormal();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainGameNormal();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to go back to Main Menu?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                backToHome();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check11 == 1){
                    return;
                }
                flipCard(iv_11);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check12 == 1){
                    return;
                }
                flipCard(iv_12);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check13 == 1){
                    return;
                }
                flipCard(iv_13);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check14 == 1){
                    return;
                }
                flipCard(iv_14);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_14, theCard);
            }
        });
        iv_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check15 == 1){
                    return;
                }
                flipCard(iv_15);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_15, theCard);
            }
        });
        iv_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check16 == 1){
                    return;
                }
                flipCard(iv_16);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_16, theCard);
            }
        });


        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check21 == 1){
                    return;
                }
                flipCard(iv_21);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check22 == 1){
                    return;
                }
                flipCard(iv_22);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check23 == 1){
                    return;
                }
                flipCard(iv_23);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check24 == 1){
                    return;
                }
                flipCard(iv_24);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_24, theCard);
            }
        });
        iv_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check25 == 1){
                    return;
                }
                flipCard(iv_25);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_25, theCard);
            }
        });
        iv_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check26 == 1){
                    return;
                }
                flipCard(iv_26);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_26, theCard);
            }
        });


        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check31 == 1){
                    return;
                }
                flipCard(iv_31);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check32 == 1){
                    return;
                }
                flipCard(iv_32);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check33 == 1){
                    return;
                }
                flipCard(iv_33);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check34 == 1){
                    return;
                }
                flipCard(iv_34);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_34, theCard);
            }
        });
        iv_35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check35 == 1){
                    return;
                }
                flipCard(iv_35);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_35, theCard);
            }
        });
        iv_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check36 == 1){
                    return;
                }
                flipCard(iv_36);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_36, theCard);
            }
        });

        iv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check41 == 1){
                    return;
                }
                flipCard(iv_41);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_41, theCard);
            }
        });
        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check42 == 1){
                    return;
                }
                flipCard(iv_42);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_42, theCard);
            }
        });
        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check43 == 1){
                    return;
                }
                flipCard(iv_43);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_43, theCard);
            }
        });
        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check44 == 1){
                    return;
                }
                flipCard(iv_44);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_44, theCard);
            }
        });
        iv_45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check45 == 1){
                    return;
                }
                flipCard(iv_45);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_45, theCard);
            }
        });
        iv_46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check46 == 1){
                    return;
                }
                flipCard(iv_46);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_46, theCard);
            }
        });

        iv_51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check51 == 1){
                    return;
                }
                flipCard(iv_51);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_51, theCard);
            }
        });
        iv_52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check52 == 1){
                    return;
                }
                flipCard(iv_52);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_52, theCard);
            }
        });
        iv_53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check53 == 1){
                    return;
                }
                flipCard(iv_53);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_53, theCard);
            }
        });
        iv_54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check54 == 1){
                    return;
                }
                flipCard(iv_54);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_54, theCard);
            }
        });
        iv_55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check55 == 1){
                    return;
                }
                flipCard(iv_55);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_55, theCard);
            }
        });
        iv_56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check56 == 1){
                    return;
                }
                flipCard(iv_56);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_56, theCard);
            }
        });

        iv_61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check61 == 1){
                    return;
                }
                flipCard(iv_61);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_61, theCard);
            }
        });
        iv_62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check62 == 1){
                    return;
                }
                flipCard(iv_62);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_62, theCard);
            }
        });
        iv_63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check63 == 1){
                    return;
                }
                flipCard(iv_63);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_63, theCard);
            }
        });
        iv_64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check64 == 1){
                    return;
                }
                flipCard(iv_64);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_64, theCard);
            }
        });
        iv_65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check65 == 1){
                    return;
                }
                flipCard(iv_65);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_65, theCard);
            }
        });
        iv_66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check66 == 1){
                    return;
                }
                flipCard(iv_66);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffNormal(iv_66, theCard);
            }
        });
    }

    private void doStuffNormal(ImageView iv, int card){
        if(cardsArrayNormal[card] == 101)
            iv.setImageResource(image101);
        else if(cardsArrayNormal[card] == 102)
            iv.setImageResource(image102);
        else if(cardsArrayNormal[card] == 103)
            iv.setImageResource(image103);
        else if(cardsArrayNormal[card] == 104)
            iv.setImageResource(image104);
        else if(cardsArrayNormal[card] == 105)
            iv.setImageResource(image105);
        else if(cardsArrayNormal[card] == 106)
            iv.setImageResource(image106);
        else if(cardsArrayNormal[card] == 107)
            iv.setImageResource(image107);
        else if(cardsArrayNormal[card] == 108)
            iv.setImageResource(image108);
        else if(cardsArrayNormal[card] == 109)
            iv.setImageResource(image109);
        else if(cardsArrayNormal[card] == 110)
            iv.setImageResource(image110);
        else if (cardsArrayNormal[card] == 111)
            iv.setImageResource(image111);
        else if (cardsArrayNormal[card] == 112)
            iv.setImageResource(image112);
        else if (cardsArrayNormal[card] == 113)
            iv.setImageResource(image113);
        else if (cardsArrayNormal[card] == 114)
            iv.setImageResource(image114);
        else if (cardsArrayNormal[card] == 115)
            iv.setImageResource(image115);
        else if (cardsArrayNormal[card] == 116)
            iv.setImageResource(image116);
        else if (cardsArrayNormal[card] == 117)
            iv.setImageResource(image117);
        else if (cardsArrayNormal[card] == 118)
            iv.setImageResource(image118);

        if (cardsArrayNormal[card] == 201)
            iv.setImageResource(image201);
        else if (cardsArrayNormal[card] == 202)
            iv.setImageResource(image202);
        else if (cardsArrayNormal[card] == 203)
            iv.setImageResource(image203);
        else if (cardsArrayNormal[card] == 204)
            iv.setImageResource(image204);
        else if (cardsArrayNormal[card] == 205)
            iv.setImageResource(image205);
        else if (cardsArrayNormal[card] == 206)
            iv.setImageResource(image206);
        else if (cardsArrayNormal[card] == 207)
            iv.setImageResource(image207);
        else if (cardsArrayNormal[card] == 208)
            iv.setImageResource(image208);
        else if (cardsArrayNormal[card] == 209)
            iv.setImageResource(image209);
        else if (cardsArrayNormal[card] == 210)
            iv.setImageResource(image210);
        else if (cardsArrayNormal[card] == 211)
            iv.setImageResource(image211);
        else if (cardsArrayNormal[card] == 212)
            iv.setImageResource(image212);
        else if (cardsArrayNormal[card] == 213)
            iv.setImageResource(image213);
        else if (cardsArrayNormal[card] == 214)
            iv.setImageResource(image214);
        else if (cardsArrayNormal[card] == 215)
            iv.setImageResource(image215);
        else if (cardsArrayNormal[card] == 216)
            iv.setImageResource(image216);
        else if (cardsArrayNormal[card] == 217)
            iv.setImageResource(image217);
        else if (cardsArrayNormal[card] == 218)
            iv.setImageResource(image218);

        if(cardNumber == 1){
            firstCard = cardsArrayNormal[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArrayNormal[card];
            if(secondCard > 200){
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_15.setEnabled(false);
            iv_16.setEnabled(false);

            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_25.setEnabled(false);
            iv_26.setEnabled(false);

            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);
            iv_35.setEnabled(false);
            iv_36.setEnabled(false);

            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);
            iv_45.setEnabled(false);
            iv_46.setEnabled(false);

            iv_51.setEnabled(false);
            iv_52.setEnabled(false);
            iv_53.setEnabled(false);
            iv_54.setEnabled(false);
            iv_55.setEnabled(false);
            iv_56.setEnabled(false);

            iv_61.setEnabled(false);
            iv_62.setEnabled(false);
            iv_63.setEnabled(false);
            iv_64.setEnabled(false);
            iv_65.setEnabled(false);
            iv_66.setEnabled(false);

            if(turnCount == 0){
                score();
            }else{
                turnCount--;
            }

            String turn = "FLIPS: ";
            String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

            txtv_turn.setText(turnFormatted);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculateNormal();
                }
            }, 500);
        }

    }
    private void calculateNormal(){
        if (firstCard == secondCard){
            if (clickedFirst == 0)
            {
                iv_11.setEnabled(false);
                check11 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 1)
            {
                iv_12.setEnabled(false);
                check12 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 2)
            {
                iv_13.setEnabled(false);
                check13 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 3)
            {
                iv_14.setEnabled(false);
                check14 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 4)
            {
                iv_15.setEnabled(false);
                check15 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 5)
            {
                iv_16.setEnabled(false);
                check16 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 6)
            {
                iv_21.setEnabled(false);
                check21 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 7)
            {
                iv_22.setEnabled(false);
                check22 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 8)
            {
                iv_23.setEnabled(false);
                check23 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 9)
            {
                iv_24.setEnabled(false);
                check24 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 10)
            {
                iv_25.setEnabled(false);
                check25 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 11)
            {
                iv_26.setEnabled(false);
                check26 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 12)
            {
                iv_31.setEnabled(false);
                check31 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 13)
            {
                iv_32.setEnabled(false);
                check32 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 14)
            {
                iv_33.setEnabled(false);
                check33 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 15)
            {
                iv_34.setEnabled(false);
                check34 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 16)
            {
                iv_35.setEnabled(false);
                check35 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 17)
            {
                iv_36.setEnabled(false);
                check36 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 18)
            {
                iv_41.setEnabled(false);
                check41 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 19)
            {
                iv_42.setEnabled(false);
                check42 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 20)
            {
                iv_43.setEnabled(false);
                check43 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 21)
            {
                iv_44.setEnabled(false);
                check44 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 22)
            {
                iv_45.setEnabled(false);
                check45 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 23)
            {
                iv_46.setEnabled(false);
                check46 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 24)
            {
                iv_51.setEnabled(false);
                check51 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 25)
            {
                iv_52.setEnabled(false);
                check52 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 26)
            {
                iv_53.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 27)
            {
                iv_54.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 28)
            {
                iv_55.setEnabled(false);
                check55 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 29)
            {
                iv_56.setEnabled(false);
                check56 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 30)
            {
                iv_61.setEnabled(false);
                check61 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 31)
            {
                iv_62.setEnabled(false);
                check62 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 32)
            {
                iv_63.setEnabled(false);
                check63 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 33)
            {
                iv_64.setEnabled(false);
                check64 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 34)
            {
                iv_65.setEnabled(false);
                check65 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 35)
            {
                iv_66.setEnabled(false);
                check66 = 1;
                scoreCount += 5;
            }

            if (clickedSecond == 0)
            {
                iv_11.setEnabled(false);
                check11 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 1)
            {
                iv_12.setEnabled(false);
                check12 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 2)
            {
                iv_13.setEnabled(false);
                check13 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 3)
            {
                iv_14.setEnabled(false);
                check14 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 4)
            {
                iv_15.setEnabled(false);
                check15 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 5)
            {
                iv_16.setEnabled(false);
                check16 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 6)
            {
                iv_21.setEnabled(false);
                check21 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 7)
            {
                iv_22.setEnabled(false);
                check22 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 8)
            {
                iv_23.setEnabled(false);
                check23 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 9)
            {
                iv_24.setEnabled(false);
                check24 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 10)
            {
                iv_25.setEnabled(false);
                check25 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond ==11)
            {
                iv_26.setEnabled(false);
                check26 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 12)
            {
                iv_31.setEnabled(false);
                check31 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 13)
            {
                iv_32.setEnabled(false);
                check32 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 14)
            {
                iv_33.setEnabled(false);
                check33 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 15)
            {
                iv_34.setEnabled(false);
                check34 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 16)
            {
                iv_35.setEnabled(false);
                check35 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 17)
            {
                iv_36.setEnabled(false);
                check36 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 18)
            {
                iv_41.setEnabled(false);
                check41 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 19)
            {
                iv_42.setEnabled(false);
                check42 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 20)
            {
                iv_43.setEnabled(false);
                check43 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 21)
            {
                iv_44.setEnabled(false);
                check44 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 22)
            {
                iv_45.setEnabled(false);
                check45 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 23)
            {
                iv_46.setEnabled(false);
                check46 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 24)
            {
                iv_51.setEnabled(false);
                check51 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 25)
            {
                iv_52.setEnabled(false);
                check52 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 26)
            {
                iv_53.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 27)
            {
                iv_54.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 28)
            {
                iv_55.setEnabled(false);
                check55 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 29)
            {
                iv_56.setEnabled(false);
                check56 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 30)
            {
                iv_61.setEnabled(false);
                check61 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 31)
            {
                iv_62.setEnabled(false);
                check62 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 32)
            {
                iv_63.setEnabled(false);
                check63 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 33)
            {
                iv_64.setEnabled(false);
                check64 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 34)
            {
                iv_65.setEnabled(false);
                check65 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 35)
            {
                iv_66.setEnabled(false);
                check66 = 1;
                scoreCount += 5;
            }


        }else{
            if(check11 == 0)
                iv_11.setImageResource(R.drawable.backcard);
            if(check12 == 0)
                iv_12.setImageResource(R.drawable.backcard);
            if(check13 == 0)
                iv_13.setImageResource(R.drawable.backcard);
            if(check14 == 0)
                iv_14.setImageResource(R.drawable.backcard);
            if(check15 == 0)
                iv_15.setImageResource(R.drawable.backcard);
            if(check16 == 0)
                iv_16.setImageResource(R.drawable.backcard);

            if(check21 == 0)
                iv_21.setImageResource(R.drawable.backcard);
            if(check22 == 0)
                iv_22.setImageResource(R.drawable.backcard);
            if(check23 == 0)
                iv_23.setImageResource(R.drawable.backcard);
            if(check24 == 0)
                iv_24.setImageResource(R.drawable.backcard);
            if(check25 == 0)
                iv_25.setImageResource(R.drawable.backcard);
            if(check26 == 0)
                iv_26.setImageResource(R.drawable.backcard);

            if(check31 == 0)
                iv_31.setImageResource(R.drawable.backcard);
            if(check32 == 0)
                iv_32.setImageResource(R.drawable.backcard);
            if(check33 == 0)
                iv_33.setImageResource(R.drawable.backcard);
            if(check34 == 0)
                iv_34.setImageResource(R.drawable.backcard);
            if(check35 == 0)
                iv_35.setImageResource(R.drawable.backcard);
            if(check36 == 0)
                iv_36.setImageResource(R.drawable.backcard);

            if(check41 == 0)
                iv_41.setImageResource(R.drawable.backcard);
            if(check42 == 0)
                iv_42.setImageResource(R.drawable.backcard);
            if(check43 == 0)
                iv_43.setImageResource(R.drawable.backcard);
            if(check44 == 0)
                iv_44.setImageResource(R.drawable.backcard);
            if(check45 == 0)
                iv_45.setImageResource(R.drawable.backcard);
            if(check46 == 0)
                iv_46.setImageResource(R.drawable.backcard);

            if(check51 == 0)
                iv_51.setImageResource(R.drawable.backcard);
            if(check52 == 0)
                iv_52.setImageResource(R.drawable.backcard);
            if(check53 == 0)
                iv_53.setImageResource(R.drawable.backcard);
            if(check54 == 0)
                iv_54.setImageResource(R.drawable.backcard);
            if(check55 == 0)
                iv_55.setImageResource(R.drawable.backcard);
            if(check56 == 0)
                iv_56.setImageResource(R.drawable.backcard);

            if(check61 == 0)
                iv_61.setImageResource(R.drawable.backcard);
            if(check62 == 0)
                iv_62.setImageResource(R.drawable.backcard);
            if(check63 == 0)
                iv_63.setImageResource(R.drawable.backcard);
            if(check64 == 0)
                iv_64.setImageResource(R.drawable.backcard);
            if(check65 == 0)
                iv_65.setImageResource(R.drawable.backcard);
            if(check66 == 0)
                iv_66.setImageResource(R.drawable.backcard);

        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_15.setEnabled(true);
        iv_16.setEnabled(true);

        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_25.setEnabled(true);
        iv_26.setEnabled(true);

        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);
        iv_35.setEnabled(true);
        iv_36.setEnabled(true);

        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);
        iv_45.setEnabled(true);
        iv_46.setEnabled(true);

        iv_51.setEnabled(true);
        iv_52.setEnabled(true);
        iv_53.setEnabled(true);
        iv_54.setEnabled(true);
        iv_55.setEnabled(true);
        iv_56.setEnabled(true);

        iv_61.setEnabled(true);
        iv_62.setEnabled(true);
        iv_63.setEnabled(true);
        iv_64.setEnabled(true);
        iv_65.setEnabled(true);
        iv_66.setEnabled(true);

        checkEnd();

    }

    private void mainGameHard(){
        setContentView(R.layout.screen_maingame_hard);
        txtv_time = findViewById(R.id.txtTime);
        txtv_turn  = findViewById(R.id.txtFlips);
        cardNumber = 1;
        btnPlayAgain = findViewById(R.id.btnPlayAgain);
        btnReset = findViewById(R.id.btnReset);
        btnHome = findViewById(R.id.btnHome);

        initialDuration = 10 * 60 * 1000;;
        startTime();

        turnCount = 70;
        String turn = "FLIPS: ";
        String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

        txtv_turn.setText(turnFormatted);
        findViewById(R.id.layout_score).setVisibility(View.INVISIBLE);
        btnPlayAgain.setEnabled(false);

        restartCheck();

        iv_11 = (ImageView) findViewById(R.id.iv_11);
        iv_12 = (ImageView) findViewById(R.id.iv_12);
        iv_13 = (ImageView) findViewById(R.id.iv_13);
        iv_14 = (ImageView) findViewById(R.id.iv_14);
        iv_15 = (ImageView) findViewById(R.id.iv_15);
        iv_16 = (ImageView) findViewById(R.id.iv_16);
        iv_17 = (ImageView) findViewById(R.id.iv_17);
        iv_18 = (ImageView) findViewById(R.id.iv_18);

        iv_21 = (ImageView) findViewById(R.id.iv_21);
        iv_22 = (ImageView) findViewById(R.id.iv_22);
        iv_23 = (ImageView) findViewById(R.id.iv_23);
        iv_24 = (ImageView) findViewById(R.id.iv_24);
        iv_25 = (ImageView) findViewById(R.id.iv_25);
        iv_26 = (ImageView) findViewById(R.id.iv_26);
        iv_27 = (ImageView) findViewById(R.id.iv_27);
        iv_28 = (ImageView) findViewById(R.id.iv_28);

        iv_31 = (ImageView) findViewById(R.id.iv_31);
        iv_32 = (ImageView) findViewById(R.id.iv_32);
        iv_33 = (ImageView) findViewById(R.id.iv_33);
        iv_34 = (ImageView) findViewById(R.id.iv_34);
        iv_35 = (ImageView) findViewById(R.id.iv_35);
        iv_36 = (ImageView) findViewById(R.id.iv_36);
        iv_37 = (ImageView) findViewById(R.id.iv_37);
        iv_38 = (ImageView) findViewById(R.id.iv_38);

        iv_41 = (ImageView) findViewById(R.id.iv_41);
        iv_42 = (ImageView) findViewById(R.id.iv_42);
        iv_43 = (ImageView) findViewById(R.id.iv_43);
        iv_44 = (ImageView) findViewById(R.id.iv_44);
        iv_45 = (ImageView) findViewById(R.id.iv_45);
        iv_46 = (ImageView) findViewById(R.id.iv_46);
        iv_47 = (ImageView) findViewById(R.id.iv_47);
        iv_48 = (ImageView) findViewById(R.id.iv_48);

        iv_51 = (ImageView) findViewById(R.id.iv_51);
        iv_52 = (ImageView) findViewById(R.id.iv_52);
        iv_53 = (ImageView) findViewById(R.id.iv_53);
        iv_54 = (ImageView) findViewById(R.id.iv_54);
        iv_55 = (ImageView) findViewById(R.id.iv_55);
        iv_56 = (ImageView) findViewById(R.id.iv_56);
        iv_57 = (ImageView) findViewById(R.id.iv_57);
        iv_58 = (ImageView) findViewById(R.id.iv_58);

        iv_61 = (ImageView) findViewById(R.id.iv_61);
        iv_62 = (ImageView) findViewById(R.id.iv_62);
        iv_63 = (ImageView) findViewById(R.id.iv_63);
        iv_64 = (ImageView) findViewById(R.id.iv_64);
        iv_65 = (ImageView) findViewById(R.id.iv_65);
        iv_66 = (ImageView) findViewById(R.id.iv_66);
        iv_67 = (ImageView) findViewById(R.id.iv_67);
        iv_68 = (ImageView) findViewById(R.id.iv_68);

        iv_71 = (ImageView) findViewById(R.id.iv_71);
        iv_72 = (ImageView) findViewById(R.id.iv_72);
        iv_73 = (ImageView) findViewById(R.id.iv_73);
        iv_74 = (ImageView) findViewById(R.id.iv_74);
        iv_75 = (ImageView) findViewById(R.id.iv_75);
        iv_76 = (ImageView) findViewById(R.id.iv_76);
        iv_77 = (ImageView) findViewById(R.id.iv_77);
        iv_78 = (ImageView) findViewById(R.id.iv_78);

        iv_81 = (ImageView) findViewById(R.id.iv_81);
        iv_82 = (ImageView) findViewById(R.id.iv_82);
        iv_83 = (ImageView) findViewById(R.id.iv_83);
        iv_84 = (ImageView) findViewById(R.id.iv_84);
        iv_85 = (ImageView) findViewById(R.id.iv_85);
        iv_86 = (ImageView) findViewById(R.id.iv_86);
        iv_87 = (ImageView) findViewById(R.id.iv_87);
        iv_88 = (ImageView) findViewById(R.id.iv_88);


        iv_11.setTag("0");
        iv_12.setTag("1");
        iv_13.setTag("2");
        iv_14.setTag("3");
        iv_15.setTag("4");
        iv_16.setTag("5");
        iv_17.setTag("6");
        iv_18.setTag("7");

        iv_21.setTag("8");
        iv_22.setTag("9");
        iv_23.setTag("10");
        iv_24.setTag("11");
        iv_25.setTag("12");
        iv_26.setTag("13");
        iv_27.setTag("14");
        iv_28.setTag("15");

        iv_31.setTag("16");
        iv_32.setTag("17");
        iv_33.setTag("18");
        iv_34.setTag("19");
        iv_35.setTag("20");
        iv_36.setTag("21");
        iv_37.setTag("22");
        iv_38.setTag("23");

        iv_41.setTag("24");
        iv_42.setTag("25");
        iv_43.setTag("26");
        iv_44.setTag("27");
        iv_45.setTag("28");
        iv_46.setTag("29");
        iv_47.setTag("30");
        iv_48.setTag("31");

        iv_51.setTag("32");
        iv_52.setTag("33");
        iv_53.setTag("34");
        iv_54.setTag("35");
        iv_55.setTag("36");
        iv_56.setTag("37");
        iv_57.setTag("38");
        iv_58.setTag("39");

        iv_61.setTag("40");
        iv_62.setTag("41");
        iv_63.setTag("42");
        iv_64.setTag("43");
        iv_65.setTag("44");
        iv_66.setTag("45");
        iv_67.setTag("46");
        iv_68.setTag("47");

        iv_71.setTag("48");
        iv_72.setTag("49");
        iv_73.setTag("50");
        iv_74.setTag("51");
        iv_75.setTag("52");
        iv_76.setTag("53");
        iv_77.setTag("54");
        iv_78.setTag("55");

        iv_81.setTag("56");
        iv_82.setTag("57");
        iv_83.setTag("58");
        iv_84.setTag("59");
        iv_85.setTag("60");
        iv_86.setTag("61");
        iv_87.setTag("62");
        iv_88.setTag("63");

        frontOfCardsResources();

        Collections.shuffle(Arrays.asList(cardsArrayHard));

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int centerX = displayMetrics.widthPixels / 2;
        int centerY = displayMetrics.heightPixels / 2;

        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                int resourceId = getResources().getIdentifier("iv_" + i + j, "id", getPackageName());
                ImageView imageView = findViewById(resourceId);
                animateImageViewToOriginalPosition(imageView, centerX, centerY, imageView.getX(), imageView.getY());
            }
        }


        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to Reset?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                mainGameHard();;
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        btnPlayAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainGameHard();
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Do you want to go back to Main Menu?")
                        .setCancelable(false) // Disable dismissing by tapping outside
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                // Go to the home screen (assuming mainMenu() does that)
                                backToHome();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {

                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

        iv_11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check11 == 1){
                    return;
                }
                flipCard(iv_11);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_11, theCard);
            }
        });
        iv_12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check12 == 1){
                    return;
                }
                flipCard(iv_12);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_12, theCard);
            }
        });
        iv_13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check13 == 1){
                    return;
                }
                flipCard(iv_13);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_13, theCard);
            }
        });
        iv_14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check14 == 1){
                    return;
                }
                flipCard(iv_14);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_14, theCard);
            }
        });
        iv_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check15 == 1){
                    return;
                }
                flipCard(iv_15);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_15, theCard);
            }
        });
        iv_16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check16 == 1){
                    return;
                }
                flipCard(iv_16);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_16, theCard);
            }
        });
        iv_17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check17 == 1){
                    return;
                }
                flipCard(iv_17);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_17, theCard);
            }
        });
        iv_18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check18 == 1){
                    return;
                }
                flipCard(iv_18);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_18, theCard);
            }
        });


        iv_21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check21 == 1){
                    return;
                }
                flipCard(iv_21);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_21, theCard);
            }
        });
        iv_22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check22 == 1){
                    return;
                }
                flipCard(iv_22);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_22, theCard);
            }
        });
        iv_23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check23 == 1){
                    return;
                }
                flipCard(iv_23);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_23, theCard);
            }
        });
        iv_24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check24 == 1){
                    return;
                }
                flipCard(iv_24);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_24, theCard);
            }
        });
        iv_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check25 == 1){
                    return;
                }
                flipCard(iv_25);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_25, theCard);
            }
        });
        iv_26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check26 == 1){
                    return;
                }
                flipCard(iv_26);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_26, theCard);
            }
        });
        iv_27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check27 == 1){
                    return;
                }
                flipCard(iv_27);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_27, theCard);
            }
        });

        iv_28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check28 == 1){
                    return;
                }
                flipCard(iv_28);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_28, theCard);
            }
        });



        iv_31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check31 == 1){
                    return;
                }
                flipCard(iv_31);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_31, theCard);
            }
        });
        iv_32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check32 == 1){
                    return;
                }
                flipCard(iv_32);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_32, theCard);
            }
        });
        iv_33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check33 == 1){
                    return;
                }
                flipCard(iv_33);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_33, theCard);
            }
        });
        iv_34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check34 == 1){
                    return;
                }
                flipCard(iv_34);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_34, theCard);
            }
        });
        iv_35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check35 == 1){
                    return;
                }
                flipCard(iv_35);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_35, theCard);
            }
        });
        iv_36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check36 == 1){
                    return;
                }
                flipCard(iv_36);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_36, theCard);
            }
        });
        iv_37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check37 == 1){
                    return;
                }
                flipCard(iv_37);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_37, theCard);
            }
        });
        iv_38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check38 == 1){
                    return;
                }
                flipCard(iv_38);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_38, theCard);
            }
        });

        iv_41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check41 == 1){
                    return;
                }
                flipCard(iv_41);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_41, theCard);
            }
        });
        iv_42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check42 == 1){
                    return;
                }
                flipCard(iv_42);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_42, theCard);
            }
        });
        iv_43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check43 == 1){
                    return;
                }
                flipCard(iv_43);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_43, theCard);
            }
        });
        iv_44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check44 == 1){
                    return;
                }
                flipCard(iv_44);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_44, theCard);
            }
        });
        iv_45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check45 == 1){
                    return;
                }
                flipCard(iv_45);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_45, theCard);
            }
        });
        iv_46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check46 == 1){
                    return;
                }
                flipCard(iv_46);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_46, theCard);
            }
        });
        iv_47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check47 == 1){
                    return;
                }
                flipCard(iv_47);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_47, theCard);
            }
        });
        iv_48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check48 == 1){
                    return;
                }
                flipCard(iv_48);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_48, theCard);
            }
        });

        iv_51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check51 == 1){
                    return;
                }
                flipCard(iv_51);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_51, theCard);
            }
        });
        iv_52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check52 == 1){
                    return;
                }
                flipCard(iv_52);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_52, theCard);
            }
        });
        iv_53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check53 == 1){
                    return;
                }
                flipCard(iv_53);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_53, theCard);
            }
        });
        iv_54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check54 == 1){
                    return;
                }
                flipCard(iv_54);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_54, theCard);
            }
        });
        iv_55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check55 == 1){
                    return;
                }
                flipCard(iv_55);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_55, theCard);
            }
        });
        iv_56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check56 == 1){
                    return;
                }
                flipCard(iv_56);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_56, theCard);
            }
        });
        iv_57.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check57 == 1){
                    return;
                }
                flipCard(iv_57);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_57, theCard);
            }
        });
        iv_58.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check58 == 1){
                    return;
                }
                flipCard(iv_58);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_58, theCard);
            }
        });

        iv_61.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check61 == 1){
                    return;
                }
                flipCard(iv_61);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_61, theCard);
            }
        });
        iv_62.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check62 == 1){
                    return;
                }
                flipCard(iv_62);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_62, theCard);
            }
        });
        iv_63.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check63 == 1){
                    return;
                }
                flipCard(iv_63);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_63, theCard);
            }
        });
        iv_64.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check64 == 1){
                    return;
                }
                flipCard(iv_64);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_64, theCard);
            }
        });
        iv_65.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check65 == 1){
                    return;
                }
                flipCard(iv_65);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_65, theCard);
            }
        });
        iv_66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check66 == 1){
                    return;
                }
                flipCard(iv_66);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_66, theCard);
            }
        });
        iv_67.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check67 == 1){
                    return;
                }
                flipCard(iv_67);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_67, theCard);
            }
        });
        iv_68.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check68 == 1){
                    return;
                }
                flipCard(iv_68);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_68, theCard);
            }
        });

        iv_71.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check71 == 1){
                    return;
                }
                flipCard(iv_71);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_71, theCard);
            }
        });
        iv_72.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check72 == 1){
                    return;
                }
                flipCard(iv_72);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_72, theCard);
            }
        });
        iv_73.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check73 == 1){
                    return;
                }
                flipCard(iv_73);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_73, theCard);
            }
        });
        iv_74.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check74 == 1){
                    return;
                }
                flipCard(iv_74);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_74, theCard);
            }
        });
        iv_75.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check75 == 1){
                    return;
                }
                flipCard(iv_75);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_75, theCard);
            }
        });
        iv_76.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check76 == 1){
                    return;
                }
                flipCard(iv_76);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_76, theCard);
            }
        });
        iv_77.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check77 == 1){
                    return;
                }
                flipCard(iv_77);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_77, theCard);
            }
        });
        iv_78.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check78 == 1){
                    return;
                }
                flipCard(iv_78);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_78, theCard);
            }
        });

        iv_81.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check81 == 1){
                    return;
                }
                flipCard(iv_81);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_81, theCard);
            }
        });
        iv_82.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check82 == 1){
                    return;
                }
                flipCard(iv_82);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_82, theCard);
            }
        });
        iv_83.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check83 == 1){
                    return;
                }
                flipCard(iv_83);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_83, theCard);
            }
        });
        iv_84.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check84 == 1){
                    return;
                }
                flipCard(iv_84);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_84, theCard);
            }
        });
        iv_85.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check85 == 1){
                    return;
                }
                flipCard(iv_85);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_85, theCard);
            }
        });
        iv_86.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check86 == 1){
                    return;
                }
                flipCard(iv_86);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_86, theCard);
            }
        });
        iv_87.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check87 == 1){
                    return;
                }
                flipCard(iv_87);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_87, theCard);
            }
        });
        iv_88.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check88 == 1){
                    return;
                }
                flipCard(iv_88);
                int theCard = Integer.parseInt((String) view.getTag());
                doStuffHard(iv_88, theCard);
            }
        });
    }

    private void doStuffHard(ImageView iv, int card){
        if (cardsArrayHard[card] == 101)
            iv.setImageResource(image101);
        else if (cardsArrayHard[card] == 102)
            iv.setImageResource(image102);
        else if (cardsArrayHard[card] == 103)
            iv.setImageResource(image103);
        else if (cardsArrayHard[card] == 104)
            iv.setImageResource(image104);
        else if (cardsArrayHard[card] == 105)
            iv.setImageResource(image105);
        else if (cardsArrayHard[card] == 106)
            iv.setImageResource(image106);
        else if (cardsArrayHard[card] == 107)
            iv.setImageResource(image107);
        else if (cardsArrayHard[card] == 108)
            iv.setImageResource(image108);
        else if (cardsArrayHard[card] == 109)
            iv.setImageResource(image109);
        else if (cardsArrayHard[card] == 110)
            iv.setImageResource(image110);
        else if (cardsArrayHard[card] == 111)
            iv.setImageResource(image111);
        else if (cardsArrayHard[card] == 112)
            iv.setImageResource(image112);
        else if (cardsArrayHard[card] == 113)
            iv.setImageResource(image113);
        else if (cardsArrayHard[card] == 114)
            iv.setImageResource(image114);
        else if (cardsArrayHard[card] == 115)
            iv.setImageResource(image115);
        else if (cardsArrayHard[card] == 116)
            iv.setImageResource(image116);
        else if (cardsArrayHard[card] == 117)
            iv.setImageResource(image117);
        else if (cardsArrayHard[card] == 118)
            iv.setImageResource(image118);
        else if (cardsArrayHard[card] == 119)
            iv.setImageResource(image119);
        else if (cardsArrayHard[card] == 120)
            iv.setImageResource(image120);
        else if (cardsArrayHard[card] == 121)
            iv.setImageResource(image121);
        else if (cardsArrayHard[card] == 122)
            iv.setImageResource(image122);
        else if (cardsArrayHard[card] == 123)
            iv.setImageResource(image123);
        else if (cardsArrayHard[card] == 124)
            iv.setImageResource(image124);
        else if (cardsArrayHard[card] == 125)
            iv.setImageResource(image125);
        else if (cardsArrayHard[card] == 126)
            iv.setImageResource(image126);
        else if (cardsArrayHard[card] == 127)
            iv.setImageResource(image127);
        else if (cardsArrayHard[card] == 128)
            iv.setImageResource(image128);
        else if (cardsArrayHard[card] == 129)
            iv.setImageResource(image129);
        else if (cardsArrayHard[card] == 130)
            iv.setImageResource(image130);
        else if (cardsArrayHard[card] == 131)
            iv.setImageResource(image131);
        else if (cardsArrayHard[card] == 132)
            iv.setImageResource(image132);

        if (cardsArrayHard[card] == 201)
            iv.setImageResource(image201);
        else if (cardsArrayHard[card] == 202)
            iv.setImageResource(image202);
        else if (cardsArrayHard[card] == 203)
            iv.setImageResource(image203);
        else if (cardsArrayHard[card] == 204)
            iv.setImageResource(image204);
        else if (cardsArrayHard[card] == 205)
            iv.setImageResource(image205);
        else if (cardsArrayHard[card] == 206)
            iv.setImageResource(image206);
        else if (cardsArrayHard[card] == 207)
            iv.setImageResource(image207);
        else if (cardsArrayHard[card] == 208)
            iv.setImageResource(image208);
        else if (cardsArrayHard[card] == 209)
            iv.setImageResource(image209);
        else if (cardsArrayHard[card] == 210)
            iv.setImageResource(image210);
        else if (cardsArrayHard[card] == 211)
            iv.setImageResource(image211);
        else if (cardsArrayHard[card] == 212)
            iv.setImageResource(image212);
        else if (cardsArrayHard[card] == 213)
            iv.setImageResource(image213);
        else if (cardsArrayHard[card] == 214)
            iv.setImageResource(image214);
        else if (cardsArrayHard[card] == 215)
            iv.setImageResource(image215);
        else if (cardsArrayHard[card] == 216)
            iv.setImageResource(image216);
        else if (cardsArrayHard[card] == 217)
            iv.setImageResource(image217);
        else if (cardsArrayHard[card] == 218)
            iv.setImageResource(image218);
        else if (cardsArrayHard[card] == 219)
            iv.setImageResource(image219);
        else if (cardsArrayHard[card] == 220)
            iv.setImageResource(image220);
        else if (cardsArrayHard[card] == 221)
            iv.setImageResource(image221);
        else if (cardsArrayHard[card] == 222)
            iv.setImageResource(image222);
        else if (cardsArrayHard[card] == 223)
            iv.setImageResource(image223);
        else if (cardsArrayHard[card] == 224)
            iv.setImageResource(image224);
        else if (cardsArrayHard[card] == 225)
            iv.setImageResource(image225);
        else if (cardsArrayHard[card] == 226)
            iv.setImageResource(image226);
        else if (cardsArrayHard[card] == 227)
            iv.setImageResource(image227);
        else if (cardsArrayHard[card] == 228)
            iv.setImageResource(image228);
        else if (cardsArrayHard[card] == 229)
            iv.setImageResource(image229);
        else if (cardsArrayHard[card] == 230)
            iv.setImageResource(image230);
        else if (cardsArrayHard[card] == 231)
            iv.setImageResource(image231);
        else if (cardsArrayHard[card] == 232)
            iv.setImageResource(image232);


        if(cardNumber == 1){
            firstCard = cardsArrayHard[card];
            if(firstCard > 200){
                firstCard = firstCard - 100;
            }
            cardNumber = 2;
            clickedFirst = card;

            iv.setEnabled(false);
        }else if(cardNumber == 2){
            secondCard = cardsArrayHard[card];
            if(secondCard > 200){
                secondCard = secondCard - 100;
            }
            cardNumber = 1;
            clickedSecond = card;

            iv_11.setEnabled(false);
            iv_12.setEnabled(false);
            iv_13.setEnabled(false);
            iv_14.setEnabled(false);
            iv_15.setEnabled(false);
            iv_16.setEnabled(false);
            iv_17.setEnabled(false);
            iv_18.setEnabled(false);

            iv_21.setEnabled(false);
            iv_22.setEnabled(false);
            iv_23.setEnabled(false);
            iv_24.setEnabled(false);
            iv_25.setEnabled(false);
            iv_26.setEnabled(false);
            iv_27.setEnabled(false);
            iv_28.setEnabled(false);

            iv_31.setEnabled(false);
            iv_32.setEnabled(false);
            iv_33.setEnabled(false);
            iv_34.setEnabled(false);
            iv_35.setEnabled(false);
            iv_36.setEnabled(false);
            iv_37.setEnabled(false);
            iv_38.setEnabled(false);

            iv_41.setEnabled(false);
            iv_42.setEnabled(false);
            iv_43.setEnabled(false);
            iv_44.setEnabled(false);
            iv_45.setEnabled(false);
            iv_46.setEnabled(false);
            iv_47.setEnabled(false);
            iv_48.setEnabled(false);

            iv_51.setEnabled(false);
            iv_52.setEnabled(false);
            iv_53.setEnabled(false);
            iv_54.setEnabled(false);
            iv_55.setEnabled(false);
            iv_56.setEnabled(false);
            iv_57.setEnabled(false);
            iv_58.setEnabled(false);

            iv_61.setEnabled(false);
            iv_62.setEnabled(false);
            iv_63.setEnabled(false);
            iv_64.setEnabled(false);
            iv_65.setEnabled(false);
            iv_66.setEnabled(false);
            iv_67.setEnabled(false);
            iv_68.setEnabled(false);

            iv_71.setEnabled(false);
            iv_72.setEnabled(false);
            iv_73.setEnabled(false);
            iv_74.setEnabled(false);
            iv_75.setEnabled(false);
            iv_76.setEnabled(false);
            iv_77.setEnabled(false);
            iv_78.setEnabled(false);

            iv_81.setEnabled(false);
            iv_82.setEnabled(false);
            iv_83.setEnabled(false);
            iv_84.setEnabled(false);
            iv_85.setEnabled(false);
            iv_86.setEnabled(false);
            iv_87.setEnabled(false);
            iv_88.setEnabled(false);


            if(turnCount == 0){
                score();
            }else{
                turnCount--;
            }

            String turn = "FLIPS: ";
            String turnFormatted = String.format(Locale.getDefault(), "%s %02d", turn, turnCount);

            txtv_turn.setText(turnFormatted);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    calculateHard();
                }
            }, 500);
        }

    }

    private void calculateHard(){
        if (firstCard == secondCard){
            if (clickedFirst == 0)
            {
                iv_11.setEnabled(false);
                check11 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 1)
            {
                iv_12.setEnabled(false);
                check12 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 2)
            {
                iv_13.setEnabled(false);
                check13 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 3)
            {
                iv_14.setEnabled(false);
                check14 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 4)
            {
                iv_15.setEnabled(false);
                check15 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 5)
            {
                iv_16.setEnabled(false);
                check16 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 6)
            {
                iv_17.setEnabled(false);
                check17 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 7)
            {
                iv_18.setEnabled(false);
                check18 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 8)
            {
                iv_21.setEnabled(false);
                check21 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 9)
            {
                iv_22.setEnabled(false);
                check22 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 10)
            {
                iv_23.setEnabled(false);
                check23 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 11)
            {
                iv_24.setEnabled(false);
                check24 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 12)
            {
                iv_25.setEnabled(false);
                check25 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 13)
            {
                iv_26.setEnabled(false);
                check26 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 14)
            {
                iv_27.setEnabled(false);
                check27 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 15)
            {
                iv_28.setEnabled(false);
                check28 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 16)
            {
                iv_31.setEnabled(false);
                check31 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 17)
            {
                iv_32.setEnabled(false);
                check32 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 18)
            {
                iv_33.setEnabled(false);
                check33 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 19)
            {
                iv_34.setEnabled(false);
                check34 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 20)
            {
                iv_35.setEnabled(false);
                check35 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 21)
            {
                iv_36.setEnabled(false);
                check36 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 22)
            {
                iv_37.setEnabled(false);
                check37 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 23)
            {
                iv_38.setEnabled(false);
                check38 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 24)
            {
                iv_41.setEnabled(false);
                check41 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 25)
            {
                iv_42.setEnabled(false);
                check42 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 26)
            {
                iv_43.setEnabled(false);
                check43 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 27)
            {
                iv_44.setEnabled(false);
                check44 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 28)
            {
                iv_45.setEnabled(false);
                check45 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 29)
            {
                iv_46.setEnabled(false);
                check46 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 30)
            {
                iv_47.setEnabled(false);
                check47 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 31)
            {
                iv_48.setEnabled(false);
                check48 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 32)
            {
                iv_51.setEnabled(false);
                check51 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 33)
            {
                iv_52.setEnabled(false);
                check52 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 34)
            {
                iv_53.setEnabled(false);
                check53 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 35)
            {
                iv_54.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 36)
            {
                iv_55.setEnabled(false);
                check55 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 37)
            {
                iv_56.setEnabled(false);
                check56 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 38)
            {
                iv_57.setEnabled(false);
                check57 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 39)
            {
                iv_58.setEnabled(false);
                check58 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 40)
            {
                iv_61.setEnabled(false);
                check61 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 41)
            {
                iv_62.setEnabled(false);
                check62 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 42)
            {
                iv_63.setEnabled(false);
                check63 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 43)
            {
                iv_64.setEnabled(false);
                check64 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 44)
            {
                iv_65.setEnabled(false);
                check65 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 45)
            {
                iv_66.setEnabled(false);
                check66 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 46)
            {
                iv_67.setEnabled(false);
                check67 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 47)
            {
                iv_68.setEnabled(false);
                check68 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 48)
            {
                iv_71.setEnabled(false);
                check71 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 49)
            {
                iv_72.setEnabled(false);
                check72 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 50)
            {
                iv_73.setEnabled(false);
                check73 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 51)
            {
                iv_74.setEnabled(false);
                check74 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 52)
            {
                iv_75.setEnabled(false);
                check75 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 53)
            {
                iv_76.setEnabled(false);
                check76 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 54)
            {
                iv_77.setEnabled(false);
                check77 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 55)
            {
                iv_78.setEnabled(false);
                check78 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 56)
            {
                iv_81.setEnabled(false);
                check81 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 57)
            {
                iv_82.setEnabled(false);
                check82 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 58)
            {
                iv_83.setEnabled(false);
                check83 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 59)
            {
                iv_84.setEnabled(false);
                check84 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 60)
            {
                iv_85.setEnabled(false);
                check85 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 61)
            {
                iv_86.setEnabled(false);
                check86 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 62)
            {
                iv_87.setEnabled(false);
                check87 = 1;
                scoreCount += 5;
            }
            else if (clickedFirst == 63)
            {
                iv_88.setEnabled(false);
                check88 = 1;
                scoreCount += 5;
            }


            if (clickedSecond == 0)
            {
                iv_11.setEnabled(false);
                check11 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 1)
            {
                iv_12.setEnabled(false);
                check12 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 2)
            {
                iv_13.setEnabled(false);
                check13 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 3)
            {
                iv_14.setEnabled(false);
                check14 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 4)
            {
                iv_15.setEnabled(false);
                check15 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 5)
            {
                iv_16.setEnabled(false);
                check16 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 6)
            {
                iv_17.setEnabled(false);
                check17 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 7)
            {
                iv_18.setEnabled(false);
                check18 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 8)
            {
                iv_21.setEnabled(false);
                check21 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 9)
            {
                iv_22.setEnabled(false);
                check22 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 10)
            {
                iv_23.setEnabled(false);
                check23 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 11)
            {
                iv_24.setEnabled(false);
                check24 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 12)
            {
                iv_25.setEnabled(false);
                check25 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 13)
            {
                iv_26.setEnabled(false);
                check26 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 14)
            {
                iv_27.setEnabled(false);
                check27 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 15)
            {
                iv_28.setEnabled(false);
                check28 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 16)
            {
                iv_31.setEnabled(false);
                check31 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 17)
            {
                iv_32.setEnabled(false);
                check32 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 18)
            {
                iv_33.setEnabled(false);
                check33 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 19)
            {
                iv_34.setEnabled(false);
                check34 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 20)
            {
                iv_35.setEnabled(false);
                check35 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 21)
            {
                iv_36.setEnabled(false);
                check36 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 22)
            {
                iv_37.setEnabled(false);
                check37 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 23)
            {
                iv_38.setEnabled(false);
                check38 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 24)
            {
                iv_41.setEnabled(false);
                check41 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 25)
            {
                iv_42.setEnabled(false);
                check42 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 26)
            {
                iv_43.setEnabled(false);
                check43 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 27)
            {
                iv_44.setEnabled(false);
                check44 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 28)
            {
                iv_45.setEnabled(false);
                check45 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 29)
            {
                iv_46.setEnabled(false);
                check46 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 30)
            {
                iv_47.setEnabled(false);
                check47 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 31)
            {
                iv_48.setEnabled(false);
                check48 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 32)
            {
                iv_51.setEnabled(false);
                check51 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 33)
            {
                iv_52.setEnabled(false);
                check52 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 34)
            {
                iv_53.setEnabled(false);
                check53 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 35)
            {
                iv_54.setEnabled(false);
                check54 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 36)
            {
                iv_55.setEnabled(false);
                check55 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 37)
            {
                iv_56.setEnabled(false);
                check56 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 38)
            {
                iv_57.setEnabled(false);
                check57 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 39)
            {
                iv_58.setEnabled(false);
                check58 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 40)
            {
                iv_61.setEnabled(false);
                check61 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 41)
            {
                iv_62.setEnabled(false);
                check62 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 42)
            {
                iv_63.setEnabled(false);
                check63 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 43)
            {
                iv_64.setEnabled(false);
                check64 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 44)
            {
                iv_65.setEnabled(false);
                check65 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 45)
            {
                iv_66.setEnabled(false);
                check66 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 46)
            {
                iv_67.setEnabled(false);
                check67 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 47)
            {
                iv_68.setEnabled(false);
                check68 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 48)
            {
                iv_71.setEnabled(false);
                check71 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 49)
            {
                iv_72.setEnabled(false);
                check72 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 50)
            {
                iv_73.setEnabled(false);
                check73 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 51)
            {
                iv_74.setEnabled(false);
                check74 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 52)
            {
                iv_75.setEnabled(false);
                check75 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 53)
            {
                iv_76.setEnabled(false);
                check76 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 54)
            {
                iv_77.setEnabled(false);
                check77 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 55)
            {
                iv_78.setEnabled(false);
                check78 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 56)
            {
                iv_81.setEnabled(false);
                check81 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 57)
            {
                iv_82.setEnabled(false);
                check82 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 58)
            {
                iv_83.setEnabled(false);
                check83 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 59)
            {
                iv_84.setEnabled(false);
                check84 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 60)
            {
                iv_85.setEnabled(false);
                check85 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 61)
            {
                iv_86.setEnabled(false);
                check86 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 62)
            {
                iv_87.setEnabled(false);
                check87 = 1;
                scoreCount += 5;
            }
            else if (clickedSecond == 63)
            {
                iv_88.setEnabled(false);
                check88 = 1;
                scoreCount += 5;
            }


        }else{
            if(check11 == 0)
                iv_11.setImageResource(R.drawable.backcard);
            if(check12 == 0)
                iv_12.setImageResource(R.drawable.backcard);
            if(check13 == 0)
                iv_13.setImageResource(R.drawable.backcard);
            if(check14 == 0)
                iv_14.setImageResource(R.drawable.backcard);
            if(check15 == 0)
                iv_15.setImageResource(R.drawable.backcard);
            if(check16 == 0)
                iv_16.setImageResource(R.drawable.backcard);
            if(check17 == 0)
                iv_17.setImageResource(R.drawable.backcard);
            if(check18 == 0)
                iv_18.setImageResource(R.drawable.backcard);

            if(check21 == 0)
                iv_21.setImageResource(R.drawable.backcard);
            if(check22 == 0)
                iv_22.setImageResource(R.drawable.backcard);
            if(check23 == 0)
                iv_23.setImageResource(R.drawable.backcard);
            if(check24 == 0)
                iv_24.setImageResource(R.drawable.backcard);
            if (check25 == 0)
                iv_25.setImageResource(R.drawable.backcard);
            if (check26 == 0)
                iv_26.setImageResource(R.drawable.backcard);
            if (check27 == 0)
                iv_27.setImageResource(R.drawable.backcard);
            if (check28 == 0)
                iv_28.setImageResource(R.drawable.backcard);

            if (check31 == 0)
                iv_31.setImageResource(R.drawable.backcard);
            if (check32 == 0)
                iv_32.setImageResource(R.drawable.backcard);
            if (check33 == 0)
                iv_33.setImageResource(R.drawable.backcard);
            if (check34 == 0)
                iv_34.setImageResource(R.drawable.backcard);
            if (check35 == 0)
                iv_35.setImageResource(R.drawable.backcard);
            if (check36 == 0)
                iv_36.setImageResource(R.drawable.backcard);
            if (check37 == 0)
                iv_37.setImageResource(R.drawable.backcard);
            if (check38 == 0)
                iv_38.setImageResource(R.drawable.backcard);

            if (check41 == 0)
                iv_41.setImageResource(R.drawable.backcard);
            if (check42 == 0)
                iv_42.setImageResource(R.drawable.backcard);
            if (check43 == 0)
                iv_43.setImageResource(R.drawable.backcard);
            if (check44 == 0)
                iv_44.setImageResource(R.drawable.backcard);
            if (check45 == 0)
                iv_45.setImageResource(R.drawable.backcard);
            if (check46 == 0)
                iv_46.setImageResource(R.drawable.backcard);
            if (check47 == 0)
                iv_47.setImageResource(R.drawable.backcard);
            if (check48 == 0)
                iv_48.setImageResource(R.drawable.backcard);

            if (check51 == 0)
                iv_51.setImageResource(R.drawable.backcard);
            if (check52 == 0)
                iv_52.setImageResource(R.drawable.backcard);
            if (check53 == 0)
                iv_53.setImageResource(R.drawable.backcard);
            if (check54 == 0)
                iv_54.setImageResource(R.drawable.backcard);
            if (check55 == 0)
                iv_55.setImageResource(R.drawable.backcard);
            if (check56 == 0)
                iv_56.setImageResource(R.drawable.backcard);
            if (check57 == 0)
                iv_57.setImageResource(R.drawable.backcard);
            if (check58 == 0)
                iv_58.setImageResource(R.drawable.backcard);

            if (check61 == 0)
                iv_61.setImageResource(R.drawable.backcard);
            if (check62 == 0)
                iv_62.setImageResource(R.drawable.backcard);
            if (check63 == 0)
                iv_63.setImageResource(R.drawable.backcard);
            if (check64 == 0)
                iv_64.setImageResource(R.drawable.backcard);
            if (check65 == 0)
                iv_65.setImageResource(R.drawable.backcard);
            if (check66 == 0)
                iv_66.setImageResource(R.drawable.backcard);
            if (check67 == 0)
                iv_67.setImageResource(R.drawable.backcard);
            if (check68 == 0)
                iv_68.setImageResource(R.drawable.backcard);

            if (check71 == 0)
                iv_71.setImageResource(R.drawable.backcard);
            if (check72 == 0)
                iv_72.setImageResource(R.drawable.backcard);
            if (check73 == 0)
                iv_73.setImageResource(R.drawable.backcard);
            if (check74 == 0)
                iv_74.setImageResource(R.drawable.backcard);
            if (check75 == 0)
                iv_75.setImageResource(R.drawable.backcard);
            if (check76 == 0)
                iv_76.setImageResource(R.drawable.backcard);
            if (check77 == 0)
                iv_77.setImageResource(R.drawable.backcard);
            if (check78 == 0)
                iv_78.setImageResource(R.drawable.backcard);

            if (check81 == 0)
                iv_81.setImageResource(R.drawable.backcard);
            if (check82 == 0)
                iv_82.setImageResource(R.drawable.backcard);
            if (check83 == 0)
                iv_83.setImageResource(R.drawable.backcard);
            if (check84 == 0)
                iv_84.setImageResource(R.drawable.backcard);
            if (check85 == 0)
                iv_85.setImageResource(R.drawable.backcard);
            if (check86 == 0)
                iv_86.setImageResource(R.drawable.backcard);
            if (check87 == 0)
                iv_87.setImageResource(R.drawable.backcard);
            if (check88 == 0)
                iv_88.setImageResource(R.drawable.backcard);

        }

        iv_11.setEnabled(true);
        iv_12.setEnabled(true);
        iv_13.setEnabled(true);
        iv_14.setEnabled(true);
        iv_15.setEnabled(true);
        iv_16.setEnabled(true);
        iv_17.setEnabled(true);
        iv_18.setEnabled(true);

        iv_21.setEnabled(true);
        iv_22.setEnabled(true);
        iv_23.setEnabled(true);
        iv_24.setEnabled(true);
        iv_25.setEnabled(true);
        iv_26.setEnabled(true);
        iv_27.setEnabled(true);
        iv_28.setEnabled(true);

        iv_31.setEnabled(true);
        iv_32.setEnabled(true);
        iv_33.setEnabled(true);
        iv_34.setEnabled(true);

        iv_35.setEnabled(true);
        iv_36.setEnabled(true);
        iv_37.setEnabled(true);
        iv_38.setEnabled(true);

        iv_41.setEnabled(true);
        iv_42.setEnabled(true);
        iv_43.setEnabled(true);
        iv_44.setEnabled(true);

        iv_45.setEnabled(true);
        iv_46.setEnabled(true);
        iv_47.setEnabled(true);
        iv_48.setEnabled(true);

        iv_51.setEnabled(true);
        iv_52.setEnabled(true);
        iv_53.setEnabled(true);
        iv_54.setEnabled(true);

        iv_55.setEnabled(true);
        iv_56.setEnabled(true);
        iv_57.setEnabled(true);
        iv_58.setEnabled(true);

        iv_61.setEnabled(true);
        iv_62.setEnabled(true);
        iv_63.setEnabled(true);
        iv_64.setEnabled(true);

        iv_65.setEnabled(true);
        iv_66.setEnabled(true);
        iv_67.setEnabled(true);
        iv_68.setEnabled(true);

        iv_71.setEnabled(true);
        iv_72.setEnabled(true);
        iv_73.setEnabled(true);
        iv_74.setEnabled(true);

        iv_75.setEnabled(true);
        iv_76.setEnabled(true);
        iv_77.setEnabled(true);
        iv_78.setEnabled(true);

        iv_81.setEnabled(true);
        iv_82.setEnabled(true);
        iv_83.setEnabled(true);
        iv_84.setEnabled(true);

        iv_85.setEnabled(true);
        iv_86.setEnabled(true);
        iv_87.setEnabled(true);
        iv_88.setEnabled(true);

        checkEnd();

    }

    private void checkEnd(){
        if(difficulty == 1 ){
            if(check11 == 1 && check12 == 1 && check13 == 1 && check14 == 1 &&
                    check21 == 1 && check22 == 1 && check23 == 1 && check24 == 1 &&
                    check31 == 1 && check32 == 1 && check33 == 1 && check34 == 1 &&
                    check41 == 1 && check42 == 1 && check43 == 1 && check44 == 1)
            {
                score();
                iv_11.setEnabled(true);
                iv_12.setEnabled(true);
                iv_13.setEnabled(true);
                iv_14.setEnabled(true);

                iv_21.setEnabled(true);
                iv_22.setEnabled(true);
                iv_23.setEnabled(true);
                iv_24.setEnabled(true);

                iv_31.setEnabled(true);
                iv_32.setEnabled(true);
                iv_33.setEnabled(true);
                iv_34.setEnabled(true);

                iv_41.setEnabled(true);
                iv_42.setEnabled(true);
                iv_43.setEnabled(true);
                iv_44.setEnabled(true);
            }
        }else if(difficulty == 2){
            if(check11 == 1 && check12 == 1 && check13 == 1 && check14 == 1 &&
                    check15 == 1 && check16 == 1 &&
                    check21 == 1 && check22 == 1 && check23 == 1 && check24 == 1 &&
                    check25 == 1 && check26 == 1 &&
                    check31 == 1 && check32 == 1 && check33 == 1 && check34 == 1 &&
                    check35 == 1 && check36 == 1 &&
                    check41 == 1 && check42 == 1 && check43 == 1 && check44 == 1 &&
                    check45 == 1 && check46 == 1 &&
                    check51 == 1 && check52 == 1 && check53 == 1 && check54 == 1 &&
                    check55 == 1 && check56 == 1 &&
                    check61 == 1 && check62 == 1 && check63 == 1 && check64 == 1 &&
                    check65 == 1 && check66 == 1 &&
                    check71 == 1 && check72 == 1 && check73 == 1 && check74 == 1 &&
                    check75 == 1 && check76 == 1 &&
                    check81 == 1 && check82 == 1 && check83 == 1 && check84 == 1 &&
                    check85 == 1 && check86 == 1)
            {
                score();
                iv_11.setEnabled(true);
                iv_12.setEnabled(true);
                iv_13.setEnabled(true);
                iv_14.setEnabled(true);
                iv_15.setEnabled(true);
                iv_16.setEnabled(true);

                iv_21.setEnabled(true);
                iv_22.setEnabled(true);
                iv_23.setEnabled(true);
                iv_24.setEnabled(true);
                iv_25.setEnabled(true);
                iv_26.setEnabled(true);

                iv_31.setEnabled(true);
                iv_32.setEnabled(true);
                iv_33.setEnabled(true);
                iv_34.setEnabled(true);
                iv_35.setEnabled(true);
                iv_36.setEnabled(true);

                iv_41.setEnabled(true);
                iv_42.setEnabled(true);
                iv_43.setEnabled(true);
                iv_44.setEnabled(true);
                iv_45.setEnabled(true);
                iv_46.setEnabled(true);

                iv_51.setEnabled(true);
                iv_52.setEnabled(true);
                iv_53.setEnabled(true);
                iv_54.setEnabled(true);
                iv_55.setEnabled(true);
                iv_56.setEnabled(true);

                iv_61.setEnabled(true);
                iv_62.setEnabled(true);
                iv_63.setEnabled(true);
                iv_64.setEnabled(true);
                iv_65.setEnabled(true);
                iv_66.setEnabled(true);
            }
        }else if(difficulty == 3){
            if(check11 == 1 && check12 == 1 && check13 == 1 && check14 == 1 &&
                    check15 == 1 && check16 == 1 && check17 == 1 && check18 == 1 &&
                    check21 == 1 && check22 == 1 && check23 == 1 && check24 == 1 &&
                    check25 == 1 && check26 == 1 && check27 == 1 && check28 == 1 &&
                    check31 == 1 && check32 == 1 && check33 == 1 && check34 == 1 &&
                    check35 == 1 && check36 == 1 && check37 == 1 && check38 == 1 &&
                    check41 == 1 && check42 == 1 && check43 == 1 && check44 == 1 &&
                    check45 == 1 && check46 == 1 && check47 == 1 && check48 == 1 &&
                    check51 == 1 && check52 == 1 && check53 == 1 && check54 == 1 &&
                    check55 == 1 && check56 == 1 && check57 == 1 && check58 == 1 &&
                    check61 == 1 && check62 == 1 && check63 == 1 && check64 == 1 &&
                    check65 == 1 && check66 == 1 && check67 == 1 && check68 == 1 &&
                    check71 == 1 && check72 == 1 && check73 == 1 && check74 == 1 &&
                    check75 == 1 && check76 == 1 && check77 == 1 && check78 == 1 &&
                    check81 == 1 && check82 == 1 && check83 == 1 && check84 == 1 &&
                    check85 == 1 && check86 == 1 && check87 == 1 && check88 == 1)
            {
                score();
                iv_11.setEnabled(true);
                iv_12.setEnabled(true);
                iv_13.setEnabled(true);
                iv_14.setEnabled(true);
                iv_15.setEnabled(true);
                iv_16.setEnabled(true);
                iv_17.setEnabled(true);
                iv_18.setEnabled(true);

                iv_21.setEnabled(true);
                iv_22.setEnabled(true);
                iv_23.setEnabled(true);
                iv_24.setEnabled(true);
                iv_25.setEnabled(true);
                iv_26.setEnabled(true);
                iv_27.setEnabled(true);
                iv_28.setEnabled(true);

                iv_31.setEnabled(true);
                iv_32.setEnabled(true);
                iv_33.setEnabled(true);
                iv_34.setEnabled(true);
                iv_35.setEnabled(true);
                iv_36.setEnabled(true);
                iv_37.setEnabled(true);
                iv_38.setEnabled(true);

                iv_41.setEnabled(true);
                iv_42.setEnabled(true);
                iv_43.setEnabled(true);
                iv_44.setEnabled(true);
                iv_45.setEnabled(true);
                iv_46.setEnabled(true);
                iv_47.setEnabled(true);
                iv_48.setEnabled(true);

                iv_51.setEnabled(true);
                iv_52.setEnabled(true);
                iv_53.setEnabled(true);
                iv_54.setEnabled(true);
                iv_55.setEnabled(true);
                iv_56.setEnabled(true);
                iv_57.setEnabled(true);
                iv_58.setEnabled(true);

                iv_61.setEnabled(true);
                iv_62.setEnabled(true);
                iv_63.setEnabled(true);
                iv_64.setEnabled(true);
                iv_65.setEnabled(true);
                iv_66.setEnabled(true);
                iv_67.setEnabled(true);
                iv_68.setEnabled(true);

                iv_71.setEnabled(true);
                iv_72.setEnabled(true);
                iv_73.setEnabled(true);
                iv_74.setEnabled(true);
                iv_75.setEnabled(true);
                iv_76.setEnabled(true);
                iv_77.setEnabled(true);
                iv_78.setEnabled(true);

                iv_81.setEnabled(true);
                iv_82.setEnabled(true);
                iv_83.setEnabled(true);
                iv_84.setEnabled(true);
                iv_85.setEnabled(true);
                iv_86.setEnabled(true);
                iv_87.setEnabled(true);
                iv_88.setEnabled(true);
            }
        }

    }
    private void frontOfCardsResources(){
        image101 = R.drawable.ic_images101;
        image102 = R.drawable.ic_images102;
        image103 = R.drawable.ic_images103;
        image104 = R.drawable.ic_images104;
        image105 = R.drawable.ic_images105;
        image106 = R.drawable.ic_images106;
        image107 = R.drawable.ic_images107;
        image108 = R.drawable.ic_images108;
        image109 = R.drawable.ic_images109;
        image110 = R.drawable.ic_images110;
        image111 = R.drawable.ic_images111;
        image112 = R.drawable.ic_images112;
        image113 = R.drawable.ic_images113;
        image114 = R.drawable.ic_images114;
        image115 = R.drawable.ic_images115;
        image116 = R.drawable.ic_images116;
        image117 = R.drawable.ic_images117;
        image118 = R.drawable.ic_images118;
        image119 = R.drawable.ic_images119;
        image120 = R.drawable.ic_images120;
        image121 = R.drawable.ic_images121;
        image122 = R.drawable.ic_images122;
        image123 = R.drawable.ic_images123;
        image124 = R.drawable.ic_images124;
        image125 = R.drawable.ic_images125;
        image126 = R.drawable.ic_images126;
        image127 = R.drawable.ic_images127;
        image128 = R.drawable.ic_images128;
        image129 = R.drawable.ic_images129;
        image130 = R.drawable.ic_images130;
        image131 = R.drawable.ic_images131;
        image132 = R.drawable.ic_images132;

        image201 = R.drawable.ic_images201;
        image202 = R.drawable.ic_images202;
        image203 = R.drawable.ic_images203;
        image204 = R.drawable.ic_images204;
        image205 = R.drawable.ic_images205;
        image206 = R.drawable.ic_images206;
        image207 = R.drawable.ic_images207;
        image208 = R.drawable.ic_images208;
        image209 = R.drawable.ic_images209;
        image210 = R.drawable.ic_images210;
        image211 = R.drawable.ic_images211;
        image212 = R.drawable.ic_images212;
        image213 = R.drawable.ic_images213;
        image214 = R.drawable.ic_images214;
        image215 = R.drawable.ic_images215;
        image216 = R.drawable.ic_images216;
        image217 = R.drawable.ic_images217;
        image218 = R.drawable.ic_images218;
        image219 = R.drawable.ic_images219;
        image220 = R.drawable.ic_images220;
        image221 = R.drawable.ic_images221;
        image222 = R.drawable.ic_images222;
        image223 = R.drawable.ic_images223;
        image224 = R.drawable.ic_images224;
        image225 = R.drawable.ic_images225;
        image226 = R.drawable.ic_images226;
        image227 = R.drawable.ic_images227;
        image228 = R.drawable.ic_images228;
        image229 = R.drawable.ic_images229;
        image230 = R.drawable.ic_images230;
        image231 = R.drawable.ic_images231;
        image232 = R.drawable.ic_images232;
    }

    private void restartCheck(){
        check11 = 0;
        check12 = 0;
        check13 = 0;
        check14 = 0;
        check15 = 0;
        check16 = 0;
        check17 = 0;
        check18 = 0;
        check21 = 0;
        check22 = 0;
        check23 = 0;
        check24 = 0;
        check25 = 0;
        check26 = 0;
        check27 = 0;
        check28 = 0;
        check31 = 0;
        check32 = 0;
        check33 = 0;
        check34 = 0;
        check35 = 0;
        check36 = 0;
        check37 = 0;
        check38 = 0;
        check41 = 0;
        check42 = 0;
        check43 = 0;
        check44 = 0;
        check45 = 0;
        check46 = 0;
        check47 = 0;
        check48 = 0;
        check51 = 0;
        check52 = 0;
        check53 = 0;
        check54 = 0;
        check55 = 0;
        check56 = 0;
        check57 = 0;
        check58 = 0;
        check61 = 0;
        check62 = 0;
        check63 = 0;
        check64 = 0;
        check65 = 0;
        check66 = 0;
        check67 = 0;
        check68 = 0;
        check71 = 0;
        check72 = 0;
        check73 = 0;
        check74 = 0;
        check75 = 0;
        check76 = 0;
        check77 = 0;
        check78 = 0;
        check81 = 0;
        check82 = 0;
        check83 = 0;
        check84 = 0;
        check85 = 0;
        check86 = 0;
        check87 = 0;
        check88 = 0;
    }
    private void score() {
        timer.cancel();
        time = minutes * 60 + seconds;
        finalScore = scoreCount + (int) time + turnCount;

        findViewById(R.id.layout_score).setVisibility(View.VISIBLE);
        findViewById(R.id.btnPlayAgain).setVisibility(View.VISIBLE);
        findViewById(R.id.btnPlayAgain).setEnabled(true);

        TextView txtv_finalturn = findViewById(R.id.tvFinalTurn);
        TextView txtv_finaltime = findViewById(R.id.tvFinalTime);
        TextView txtv_finalscore = findViewById(R.id.tvFinalScore);


        String finalTurnFormatted = String.format(Locale.getDefault(), "%02d", turnCount);
        txtv_finalturn.setText(finalTurnFormatted);

        String stringSecond = "";
        String finalTimeFormatted = String.format(Locale.getDefault(), "%03d %s", time, stringSecond);
        txtv_finaltime.setText(finalTimeFormatted);

        String finalScoreFormatted = String.format(Locale.getDefault(), "%02d", finalScore);
        txtv_finalscore.setText(finalScoreFormatted);

        inputUsername = findViewById(R.id.inputUsername);


        btnIconLeaderboard = (ImageView) findViewById(R.id.btnIconLeaderboard);
        btnNext = (ImageView) findViewById(R.id.btnIconNext);

        btnIconLeaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbUsername = inputUsername.getText().toString();
                if (lbUsername.equals("")) {
                    lbUsername = "Guest";
                }
                updateLeaderboard(lbUsername, finalScore, time,turnCount );
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lbUsername = inputUsername.getText().toString();
                if (lbUsername.equals("")) {
                    lbUsername = "Guest";
                }
                updateLeaderboard(lbUsername, finalScore, time,turnCount );
            }
        });
    }
    private void startTime() {
        // Set the initial duration to 3 minutes (3 * 60 * 1000 milliseconds)


        if (timer != null) {
            timer.cancel();
        }

        timer = new CountDownTimer(initialDuration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Calculate hours, minutes, and seconds from milliseconds
                hours = (millisUntilFinished / (1000 * 60 * 60)) % 24;
                minutes = (millisUntilFinished / (1000 * 60)) % 60;
                seconds = (millisUntilFinished / 1000) % 60;

                // Format the time as HH:MM:SS
                String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

                // Set the formatted time to the TextView
                txtv_time.setText(timeFormatted);
            }

            @Override
            public void onFinish() {
                score();
            }
        }.start();
    }

    public void onBackPressed() {
        // Check if the current layout is the main game layout
        if (findViewById(R.id.txtTime) != null) {
            // If it is, navigate back to the main menu
            mainMenu();
        } else if (findViewById(R.id.backgroundSetting) != null){
            mainMenu();
        }
        else {
            // If not, perform the default back button action
            super.onBackPressed();
        }
    } //not sure

    private void flipCard(ImageView iv){
        MediaPlayer se_flipCard = MediaPlayer.create(MainActivity.this, R.raw.soundeffect_flipcard);
        se_flipCard.start();
        iv.setImageResource(R.drawable.backcard);
        ObjectAnimator flipAnimator = ObjectAnimator.ofFloat(iv, "rotationY", 90f, 180f);
        flipAnimator.setDuration(200); // Adjust duration as needed
        flipAnimator.start();
    }

    //Animation if papalit screen
    private void startscreen() {
        mainMenu();
    }
    private void homeGame() {
        mainGame();
    }
    private void homeGameNormal() {
        mainGameNormal();
    }
    private void homeGameHard() {
        mainGameHard();
    }

    private void backToHome() {
        mainMenu();
    }

    //Resources Animation
    private void animateImageViewToOriginalPosition(ImageView imageView, int startX, int startY, float endX, float endY) {
        ObjectAnimator animY = ObjectAnimator.ofFloat(imageView, View.TRANSLATION_Y, startY - endY, 0f);

        AnimatorSet animSetXY = new AnimatorSet();
        animSetXY.setDuration(1000); // Adjust duration as needed
        animSetXY.start();
    }

    private void buttonAnimationStart(){
        btnStart = findViewById(R.id.btnStart);
        btnSetting = findViewById(R.id.btnSetting);
        btnLeaderboards = findViewById(R.id.btnLeaderboards);

        btnStart.setAlpha(0f);
        btnStart.setTranslationY(50);

        btnSetting.setAlpha(0f);
        btnSetting.setTranslationY(50);

        btnLeaderboards.setAlpha(0f);
        btnLeaderboards.setTranslationY(50);

        btnStart.animate().alpha(1f).translationYBy(-50).setDuration(1500);
        btnSetting.animate().alpha(1f).translationYBy(-50).setDuration(1500);
        btnLeaderboards.animate().alpha(1f).translationYBy(-50).setDuration(1500);
    }
    private void buttonAnimationEnd(){
        btnStart = findViewById(R.id.btnStart);
        btnSetting = findViewById(R.id.btnSetting);
        btnLeaderboards = findViewById(R.id.btnLeaderboards);

        btnStart.setAlpha(1f); // Start with full opacity
        btnStart.setTranslationY(0); // Start with no translation

        btnSetting.setAlpha(1f); // Start with full opacity
        btnSetting.setTranslationY(0); // Start with no translation

        btnLeaderboards.setAlpha(1f); // Start with full opacity
        btnLeaderboards.setTranslationY(0); // Start with no translation

        // Animate the buttons back to their initial state
        btnStart.animate().alpha(0f).translationYBy(50).setDuration(1500);
        btnSetting.animate().alpha(0f).translationYBy(50).setDuration(1500);
        btnLeaderboards.animate().alpha(0f).translationYBy(50).setDuration(1500);

        btnStart.animate().alpha(0f).translationYBy(50).setDuration(1500);
        btnSetting.animate().alpha(0f).translationYBy(50).setDuration(1500);
        btnLeaderboards.animate().alpha(0f).translationYBy(50).setDuration(1500);

    }


    //Setting
    public void setting(){
        setContentView(R.layout.screen_setting);
        btnDone = findViewById(R.id.btnDone);
        RadioButton easyRadioButton = findViewById(R.id.diff_easy);
        RadioButton mediumRadioButton = findViewById(R.id.diff_normal);
        RadioButton hardRadioButton = findViewById(R.id.diff_hard);
        RadioButton Music1 = findViewById(R.id.music1);
        RadioButton Music2 = findViewById(R.id.music2);

        SharedPreferences preferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        savedDifficulty = preferences.getInt("difficulty", 1); // Default to easy if not saved
        savedSetMusic = preferences.getInt("setMusic", 1); // Default to easy if not saved

        if(savedDifficulty == 1 ){
            difficulty = 1;
            easyRadioButton.setChecked(true);
            mediumRadioButton.setChecked(false);
            hardRadioButton.setChecked(false);
        } else if (savedDifficulty == 2) {
            difficulty = 2;
            easyRadioButton.setChecked(false);
            mediumRadioButton.setChecked(true);
            hardRadioButton.setChecked(false);
        } else if(savedDifficulty == 3){
            difficulty = 3;
            easyRadioButton.setChecked(false);
            mediumRadioButton.setChecked(false);
            hardRadioButton.setChecked(true);
        }

        if(savedSetMusic == 1){
            setMusic = 1;
            Music1.setChecked(true);
            Music2.setChecked(false);
        } else if (savedSetMusic == 2) {
            setMusic = 2;
            Music1.setChecked(false);
            Music2.setChecked(true);
        }


        easyRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDifficulty(1);
                difficulty = 1 ;
                mediumRadioButton.setChecked(false);
                hardRadioButton.setChecked(false);
            }
        });

        mediumRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDifficulty(2);
                difficulty = 2 ;
                easyRadioButton.setChecked(false);
                hardRadioButton.setChecked(false);
            }
        });

        hardRadioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDifficulty(3);
                difficulty = 3;
                easyRadioButton.setChecked(false);
                mediumRadioButton.setChecked(false);
            }
        });

        Music1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMusic(1);
                Music2.setChecked(false);
            }
        });
        Music2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMusic(2);
                Music1.setChecked(false);
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.volumeControl);

        // Get the audio manager
        AudioManager audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        // Set the maximum volume of the SeekBar to the maximum volume of the MediaPlayer:
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBar.setMax(maxVolume);

        // Set the current volume of the SeekBar to the current volume of the MediaPlayer:
        int currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBar.setProgress(currVolume);

        // Add a SeekBar.OnSeekBarChangeListener to the SeekBar:
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,i,0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Do Nothing
            }
        });

        Animation animShrink = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.buttonclickedanimation);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDone.startAnimation(animShrink);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mainMenu();
                    }
                }, 300);

            }
        });


    }

    public void setDifficulty(int level) {
        difficulty = level;
        SharedPreferences preferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("difficulty", difficulty);
        editor.apply();
    }

    public void setMusic(int music){
        setMusic = music;
        SharedPreferences preferences = getSharedPreferences("mySettings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("setMusic", setMusic);
        editor.apply();

        if(music == 1){
            Intent serviceIntent = new Intent(this, MusicService.class);
            Intent serviceIntent2 = new Intent(this, MusicService2.class);
            stopService(serviceIntent2);
            stopService(serviceIntent);
            startService(serviceIntent);

        }else if(music == 2){
            Intent serviceIntent = new Intent(this, MusicService.class);
            Intent serviceIntent2 = new Intent(this, MusicService2.class);
            stopService(serviceIntent);
            stopService(serviceIntent2);
            startService(serviceIntent2);
        }


    }

    //Leaderboards
    public void initializeLB(){
        R1Username = findViewById(R.id.tvRank1);
        R1Time = findViewById(R.id.tvR1Time);
        R1Turn = findViewById(R.id.tvR1Turn);
        R1Score = findViewById(R.id.tvR1Score);

        R2Username = findViewById(R.id.tvRank2);
        R2Time = findViewById(R.id.tvR2Time);
        R2Turn = findViewById(R.id.tvR2Turn);
        R2Score = findViewById(R.id.tvR2Score);

        R3Username = findViewById(R.id.tvRank3);
        R3Time = findViewById(R.id.tvR3Time);
        R3Turn = findViewById(R.id.tvR3Turn);
        R3Score = findViewById(R.id.tvR3Score);

        R4Username = findViewById(R.id.tvRank4);
        R4Time = findViewById(R.id.tvR4Time);
        R4Turn = findViewById(R.id.tvR4Turn);
        R4Score = findViewById(R.id.tvR4Score);

        R5Username = findViewById(R.id.tvRank5);
        R5Time = findViewById(R.id.tvR5Time);
        R5Turn = findViewById(R.id.tvR5Turn);
        R5Score = findViewById(R.id.tvR5Score);
    }
    public void viewLeaderboard(){
        //Set content view
        if(difficulty == 1) {
            lbDifficulty = "Easy";
            setContentView(R.layout.screen_leaderboard_easy);
        }
        if(difficulty == 2) {
            lbDifficulty = "Normal";
            setContentView(R.layout.screen_leaderboard_normal);
        }
        if(difficulty == 3) {
            lbDifficulty = "Hard";
            setContentView(R.layout.screen_leaderboard_hard);
        }

        //Buttons
        

        btnSetting = findViewById(R.id.btnSetting);
        btnHome = findViewById(R.id.btnHome);
        btnNext = findViewById(R.id.btnIconNext);
        iconTrophy = findViewById(R.id.iconTrophy);

        iconTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });


        //Logic for updating leaderboard



        FirebaseDatabase.getInstance("https://marvelgalaxy-d8255-default-rtdb.asia-southeast1.firebasedatabase.app");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference leaderboardRef = database.getReference("LeaderboardDatabase/Leaderboard/" + lbDifficulty);
        DatabaseReference rankRef1 = leaderboardRef.child("Rank1");
        DatabaseReference rankRef2 = leaderboardRef.child("Rank2");
        DatabaseReference rankRef3 = leaderboardRef.child("Rank3");
        DatabaseReference rankRef4 = leaderboardRef.child("Rank4");
        DatabaseReference rankRef5 = leaderboardRef.child("Rank5");

        StringBuilder leaderboardText = new StringBuilder();
        TextView tvRank1 = findViewById(R.id.tvRank1);
        TextView tvRank2 = findViewById(R.id.tvRank2);
        TextView tvRank3 = findViewById(R.id.tvRank3);
        TextView tvRank4 = findViewById(R.id.tvRank4);
        TextView tvRank5 = findViewById(R.id.tvRank5);

        TextView tvTimeR1 = findViewById(R.id.tvR1Time);
        TextView tvTimeR2 = findViewById(R.id.tvR2Time);
        TextView tvTimeR3 = findViewById(R.id.tvR3Time);
        TextView tvTimeR4 = findViewById(R.id.tvR4Time);
        TextView tvTimeR5 = findViewById(R.id.tvR5Time);

        TextView tvTurnR1 = findViewById(R.id.tvR1Turn);
        TextView tvTurnR2 = findViewById(R.id.tvR2Turn);
        TextView tvTurnR3 = findViewById(R.id.tvR3Turn);
        TextView tvTurnR4 = findViewById(R.id.tvR4Turn);
        TextView tvTurnR5 = findViewById(R.id.tvR5Turn);

        TextView tvScoreR1 = findViewById(R.id.tvR1Score);
        TextView tvScoreR2 = findViewById(R.id.tvR2Score);
        TextView tvScoreR3 = findViewById(R.id.tvR3Score);
        TextView tvScoreR4 = findViewById(R.id.tvR4Score);
        TextView tvScoreR5 = findViewById(R.id.tvR5Score);
        rankRef1.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    String usernameLB = (String) rankData.get("username");
                    String scoreLB = (String) rankData.get("score");
                    String timeLB = (String) rankData.get("time");
                    String turnLB = (String) rankData.get("turn");


                    if(Objects.equals(usernameLB, "")){
                        tvRank1.setText("Player");
                        tvScoreR1.setText("--");
                        tvTimeR1.setText("--");
                        tvTurnR1.setText("--");
                    }else{
                        tvRank1.setText(usernameLB);
                        tvScoreR1.setText(scoreLB);
                        tvTimeR1.setText(timeLB);
                        tvTurnR1.setText(turnLB);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this,
                        "No Internet Connection Detected", Toast.LENGTH_SHORT).show();
            }
        });

        rankRef2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    String usernameLB = (String) rankData.get("username");
                    String scoreLB = (String) rankData.get("score");
                    String timeLB = (String) rankData.get("time");
                    String turnLB = (String) rankData.get("turn");

                    if(Objects.equals(usernameLB, "")){
                        tvRank2.setText("Player");
                        tvScoreR2.setText("--");
                        tvTimeR2.setText("--");
                        tvTurnR2.setText("--");
                    }else{
                        tvRank2.setText(usernameLB);
                        tvScoreR2.setText(scoreLB);
                        tvTimeR2.setText(timeLB);
                        tvTurnR2.setText(turnLB);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });

        rankRef3.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    String usernameLB = (String) rankData.get("username");
                    String scoreLB = (String) rankData.get("score");
                    String timeLB = (String) rankData.get("time");
                    String turnLB = (String) rankData.get("turn");

                    if(Objects.equals(usernameLB, "")){
                        tvRank3.setText("Player");
                        tvScoreR3.setText("--");
                        tvTimeR3.setText("--");
                        tvTurnR3.setText("--");
                    }else{
                        tvRank3.setText(usernameLB);
                        tvScoreR3.setText(scoreLB);
                        tvTimeR3.setText(timeLB);
                        tvTurnR3.setText(turnLB);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });

        rankRef4.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    String usernameLB = (String) rankData.get("username");
                    String scoreLB = (String) rankData.get("score");
                    String timeLB = (String) rankData.get("time");
                    String turnLB = (String) rankData.get("turn");

                    if(Objects.equals(usernameLB, "")){
                        tvRank4.setText("Player");
                        tvScoreR4.setText("--");
                        tvTimeR4.setText("--");
                        tvTurnR4.setText("--");
                    }else{
                        tvRank4.setText(usernameLB);
                        tvScoreR4.setText(scoreLB);
                        tvTimeR4.setText(timeLB);
                        tvTurnR4.setText(turnLB);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });

        rankRef5.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    String usernameLB = (String) rankData.get("username");
                    String scoreLB = (String) rankData.get("score");
                    String timeLB = (String) rankData.get("time");
                    String turnLB = (String) rankData.get("turn");


                    if(Objects.equals(usernameLB, "")){
                        tvRank5.setText("Player");
                        tvScoreR5.setText("--");
                        tvTimeR5.setText("--");
                        tvTurnR5.setText("--");
                    }else{
                        tvRank5.setText(usernameLB);
                        tvScoreR5.setText(scoreLB);
                        tvTimeR5.setText(timeLB);
                        tvTurnR5.setText(turnLB);
                    }
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });

    }
    public void updateLeaderboard(String lbUsername, int finalScore, long time, int turnCount){
        //Set content view
        if(difficulty == 1) {
            lbDifficulty = "Easy";
            setContentView(R.layout.screen_leaderboard_easy);
        }
        if(difficulty == 2) {
            lbDifficulty = "Normal";
            setContentView(R.layout.screen_leaderboard_normal);
        }
        if(difficulty == 3) {
            lbDifficulty = "Hard";
            setContentView(R.layout.screen_leaderboard_hard);
        }

        //Buttons

        btnSetting = findViewById(R.id.btnSetting);
        btnHome = findViewById(R.id.btnHome);
        btnNext = findViewById(R.id.btnIconNext);
        iconTrophy = findViewById(R.id.iconTrophy);

        iconTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });

        //Logic for updating leaderboard

        FirebaseDatabase.getInstance("https://marvelgalaxy-d8255-default-rtdb.asia-southeast1.firebasedatabase.app");
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference leaderboardRef = database.getReference("LeaderboardDatabase/Leaderboard/" + lbDifficulty);
        DatabaseReference rankRef1 = leaderboardRef.child("Rank1");
        DatabaseReference rankRef2 = leaderboardRef.child("Rank2");
        DatabaseReference rankRef3 = leaderboardRef.child("Rank3");
        DatabaseReference rankRef4 = leaderboardRef.child("Rank4");
        DatabaseReference rankRef5 = leaderboardRef.child("Rank5");

        StringBuilder leaderboardText = new StringBuilder();
        TextView tvRank1 = findViewById(R.id.tvRank1);
        TextView tvRank2 = findViewById(R.id.tvRank2);
        TextView tvRank3 = findViewById(R.id.tvRank3);
        TextView tvRank4 = findViewById(R.id.tvRank4);
        TextView tvRank5 = findViewById(R.id.tvRank5);

        TextView tvTimeR1 = findViewById(R.id.tvR1Time);
        TextView tvTimeR2 = findViewById(R.id.tvR2Time);
        TextView tvTimeR3 = findViewById(R.id.tvR3Time);
        TextView tvTimeR4 = findViewById(R.id.tvR4Time);
        TextView tvTimeR5 = findViewById(R.id.tvR5Time);

        TextView tvTurnR1 = findViewById(R.id.tvR1Turn);
        TextView tvTurnR2 = findViewById(R.id.tvR2Turn);
        TextView tvTurnR3 = findViewById(R.id.tvR3Turn);
        TextView tvTurnR4 = findViewById(R.id.tvR4Turn);
        TextView tvTurnR5 = findViewById(R.id.tvR5Turn);

        TextView tvScoreR1 = findViewById(R.id.tvR1Score);
        TextView tvScoreR2 = findViewById(R.id.tvR2Score);
        TextView tvScoreR3 = findViewById(R.id.tvR3Score);
        TextView tvScoreR4 = findViewById(R.id.tvR4Score);
        TextView tvScoreR5 = findViewById(R.id.tvR5Score);
        rankRef1.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
            @Override
            public void onSuccess(DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                    getUsernameR1 = (String) rankData.get("username");
                    getScoreR1 = (String) rankData.get("score");
                    getTimeR1 = (String) rankData.get("time");
                    getTurnR1 = (String) rankData.get("turn");
                }

                rankRef2.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                    @Override
                    public void onSuccess(DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                            getUsernameR2 = (String) rankData.get("username");
                            getScoreR2 = (String) rankData.get("score");
                            getTimeR2 = (String) rankData.get("time");
                            getTurnR2 = (String) rankData.get("turn");
                        }
                        rankRef3.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                            @Override
                            public void onSuccess(DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                                    getUsernameR3 = (String) rankData.get("username");
                                    getScoreR3 = (String) rankData.get("score");
                                    getTimeR3 = (String) rankData.get("time");
                                    getTurnR3 = (String) rankData.get("turn");
                                }
                                rankRef4.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                    @Override
                                    public void onSuccess(DataSnapshot snapshot) {
                                        if(snapshot.exists()){
                                            Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                                            getUsernameR4 = (String) rankData.get("username");
                                            getScoreR4 = (String) rankData.get("score");
                                            getTimeR4 = (String) rankData.get("time");
                                            getTurnR4 = (String) rankData.get("turn");
                                        }
                                        rankRef5.get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
                                            @Override
                                            public void onSuccess(DataSnapshot snapshot) {
                                                if(snapshot.exists()){
                                                    Map<String, Object> rankData = (Map<String, Object>) snapshot.getValue();

                                                    getUsernameR5 = (String) rankData.get("username");
                                                    getScoreR5 = (String) rankData.get("score");
                                                    getTimeR5 = (String) rankData.get("time");
                                                    getTurnR5 = (String) rankData.get("turn");
                                                    ranking(lbUsername, finalScore, time, turnCount);
                                                }

                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                System.out.println(e);
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        System.out.println(e);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                System.out.println(e);
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println(e);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                System.out.println(e);
            }
        });

    }
    public void ranking(String lbUsername, int finalScore, long time, int turnCount){
        //Set content view
        if(difficulty == 1) {
            lbDifficulty = "Easy";
            setContentView(R.layout.screen_leaderboard_easy);
        }
        if(difficulty == 2) {
            lbDifficulty = "Normal";
            setContentView(R.layout.screen_leaderboard_normal);
        }
        if(difficulty == 3) {
            lbDifficulty = "Hard";
            setContentView(R.layout.screen_leaderboard_hard);
        }

        //Buttons
        btnSetting = findViewById(R.id.btnSetting);
        btnHome = findViewById(R.id.btnHome);
        btnNext = findViewById(R.id.btnIconNext);
        iconTrophy = findViewById(R.id.iconTrophy);

        iconTrophy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(lbDifficulty.equals("Easy")){
                    difficulty = 2;
                }else if(lbDifficulty.equals("Normal")){
                    difficulty = 3;
                }else if(lbDifficulty.equals("Hard")){
                    difficulty = 1;
                }
                viewLeaderboard();
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainMenu();
            }
        });

        //Logic
        FirebaseDatabase.getInstance("https://marvelgalaxy-d8255-default-rtdb.asia-southeast1.firebasedatabase.app");
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        int finalTime = (int) time;
        String lbFinalScore = String.valueOf(finalScore);
        String lbFinalTime = String.valueOf(finalTime);
        String lbFinalTurn = String.valueOf(turnCount);
        if(difficulty == 1 || level == 1) {
            lbDifficulty = "Easy";
        }
        if(difficulty == 2 || level == 2) {
            lbDifficulty = "Normal";
        }
        if(difficulty == 3 || level == 3) {
            lbDifficulty = "Hard";
        }
        DatabaseReference leaderboardRef = database.getReference("LeaderboardDatabase/Leaderboard/" + lbDifficulty);
        DatabaseReference leaderboardRankRef = leaderboardRef.child(lbDifficulty);
        DatabaseReference rankRef1 = leaderboardRef.child("Rank1");
        DatabaseReference rankRef2 = leaderboardRef.child("Rank2");
        DatabaseReference rankRef3 = leaderboardRef.child("Rank3");
        DatabaseReference rankRef4 = leaderboardRef.child("Rank4");
        DatabaseReference rankRef5 = leaderboardRef.child("Rank5");

        int R1FinalScore = 1, R2FinalScore = 1, R3FinalScore = 1, R4FinalScore = 1, R5FinalScore = 1;
        if (!getScoreR1.equals("--")) {
            R1FinalScore = Integer.parseInt(getScoreR1);
        }
        if (!getScoreR2.equals("--")) {
            R2FinalScore = Integer.parseInt(getScoreR2);
        }
        if (!getScoreR3.equals("--")) {
            R3FinalScore = Integer.parseInt(getScoreR3);
        }
        if (!getScoreR4.equals("--")) {
            R4FinalScore = Integer.parseInt(getScoreR4);
        }
        if (!getScoreR5.equals("--")) {
            R5FinalScore = Integer.parseInt(getScoreR5);
        }
        if (finalScore > R1FinalScore){
            //R5:  shift downward R4 => R5
            rankRef5.child("username").setValue(getUsernameR4);
            rankRef5.child("time").setValue(getTimeR4);
            rankRef5.child("turn").setValue(getTurnR4);
            rankRef5.child("score").setValue(getScoreR4);

            //R4:  shift downward R3 => R4
            rankRef4.child("username").setValue(getUsernameR3);
            rankRef4.child("time").setValue(getTimeR3);
            rankRef4.child("turn").setValue(getTurnR3);
            rankRef4.child("score").setValue(getScoreR3);

            //R3:  shift downward R2 => R3
            rankRef3.child("username").setValue(getUsernameR2);
            rankRef3.child("time").setValue(getTimeR2);
            rankRef3.child("turn").setValue(getTurnR2);
            rankRef3.child("score").setValue(getScoreR2);

            //R2:  shift downward R1 => R2
            rankRef2.child("username").setValue(getUsernameR1);
            rankRef2.child("time").setValue(getTimeR1);
            rankRef2.child("turn").setValue(getTurnR1);
            rankRef2.child("score").setValue(getScoreR1);

            //Update to new Rank1
            rankRef1.child("username").setValue(lbUsername);
            rankRef1.child("time").setValue(lbFinalTime);
            rankRef1.child("turn").setValue(lbFinalTurn);
            rankRef1.child("score").setValue(lbFinalScore);


        }else if(finalScore > R2FinalScore){
            //R5:  shift downward R4 => R5
            rankRef5.child("username").setValue(getUsernameR4);
            rankRef5.child("time").setValue(getTimeR4);
            rankRef5.child("turn").setValue(getTurnR4);
            rankRef5.child("score").setValue(getScoreR4);

            //R4:  shift downward R3 => R4
            rankRef4.child("username").setValue(getUsernameR3);
            rankRef4.child("time").setValue(getTimeR3);
            rankRef4.child("turn").setValue(getTurnR3);
            rankRef4.child("score").setValue(getScoreR3);

            //R3:  shift downward R2 => R3
            rankRef3.child("username").setValue(getUsernameR2);
            rankRef3.child("time").setValue(getTimeR2);
            rankRef3.child("turn").setValue(getTurnR2);
            rankRef3.child("score").setValue(getScoreR2);

            //Update to new Rank 2
            rankRef2.child("username").setValue(lbUsername);
            rankRef2.child("time").setValue(lbFinalTime);
            rankRef2.child("turn").setValue(lbFinalTurn);
            rankRef2.child("score").setValue(lbFinalScore);
        }else if(finalScore > R3FinalScore){
            //R5:  shift downward R4 => R5
            rankRef5.child("username").setValue(getUsernameR4);
            rankRef5.child("time").setValue(getTimeR4);
            rankRef5.child("turn").setValue(getTurnR4);
            rankRef5.child("score").setValue(getScoreR4);

            //R4:  shift downward R3 => R4
            rankRef4.child("username").setValue(getUsernameR3);
            rankRef4.child("time").setValue(getTimeR3);
            rankRef4.child("turn").setValue(getTurnR3);
            rankRef4.child("score").setValue(getScoreR3);

            //Update to new Rank 3
            rankRef3.child("username").setValue(lbUsername);
            rankRef3.child("time").setValue(lbFinalTime);
            rankRef3.child("turn").setValue(lbFinalTurn);
            rankRef3.child("score").setValue(lbFinalScore);

        }else if(finalScore > R4FinalScore){
            //R5:  shift downward R4 => R5
            rankRef5.child("username").setValue(getUsernameR4);
            rankRef5.child("time").setValue(getTimeR4);
            rankRef5.child("turn").setValue(getTurnR4);
            rankRef5.child("score").setValue(getScoreR4);

            //Update to new Rank 4
            rankRef4.child("username").setValue(lbUsername);
            rankRef4.child("time").setValue(lbFinalTime);
            rankRef4.child("turn").setValue(lbFinalTurn);
            rankRef4.child("score").setValue(lbFinalScore);

        }else if(finalScore > R5FinalScore){
            //Update to new Rank5
            rankRef5.child("username").setValue(lbUsername);
            rankRef5.child("time").setValue(lbFinalTime);
            rankRef5.child("turn").setValue(lbFinalTurn);
            rankRef5.child("score").setValue(lbFinalScore);
        }
        viewLeaderboard();
    }
}

