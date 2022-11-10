package com.example.applemaths;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class questionPage extends AppCompatActivity {
    int appleNum = 11;
    ImageView Apple;
    int id;
    int buttonNum = 10;
    Button button;
    Bundle bundle = new Bundle();
    boolean newQ = true;
    int answer;
    int num1;
    int num2;
    int HEIGHT;
    int WIDTH;
    String operator = "+";
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    Switch operatorSwitch;
    boolean bundleState = false;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_page);

        operatorSwitch = findViewById(R.id.operatorS);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        HEIGHT = displayMetrics.heightPixels;
        WIDTH = displayMetrics.widthPixels;

        setApples();
        setBundle();
        setQuestion();
        setOperatorSwitch();
        setButtons();

    }

    private final View.OnTouchListener handleTouch = new View.OnTouchListener() {
        float dX, dY;
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - event.getRawX();
                    dY = view.getY() - event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float newX = event.getRawX() + dX;
                    float newY = event.getRawY() + dY;

                    if(newX <= 0){
                        newX = 0;
                    }
                    if(newY <= findViewById(R.id.button9).getY() + findViewById(R.id.button9).getHeight()){
                        newY = findViewById(R.id.button9).getY() + findViewById(R.id.button9).getHeight();
                    }
                    if(newY >= findViewById(R.id.bowl).getY() + findViewById(R.id.bowl).getHeight()){
                        newY = findViewById(R.id.bowl).getY() + findViewById(R.id.bowl).getHeight();
                    }
                    if(newX >= WIDTH - view.getWidth()){
                        newX = WIDTH - view.getWidth();
                    }
                    if(newY >= HEIGHT - view.getHeight()){
                        newY = HEIGHT - view.getHeight();
                    }

                        view.animate()
                                .x(newX)
                                .y(newY)
                                .setDuration(0)
                                .start();
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    public void setOperator() {
        boolean state = operatorSwitch.isChecked();
        if(state){
            operator = "-";
        }else{
            operator = "+";
        }
    }

    public void setOperatorSwitch() {
        if(bundleState){
            operator = bundle.getString("operator");
            operatorSwitch.setChecked(!operator.equals("+"));
            changeOperator(operator);
            changeAnswer(operator);
        }
        operatorSwitch.setOnClickListener(v -> {
            setOperator();
            changeOperator(operator);
            changeAnswer(operator);
        });
    }

    public void changeOperator(String operator_){
        TextView question  = findViewById(R.id.question);
        if(num1 < num2 && operator_.equals("-")){
            int temp = num1;
            num1 = num2;
            num2 = temp;
        }
        String questionText = num1 + operator_ + num2 + "=?";
        question.setText(questionText);
    }

    public void changeAnswer(String operator_){
        if(operator_.equals("+")){
            answer = num1 + num2;
        }else{
            answer = num1 - num2;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setApples(){
        for(int i=1; i<appleNum; i++) {
            id = getResources().getIdentifier("Apple" + i, "id", getPackageName());
            Apple = findViewById(id);
            Apple.setOnTouchListener(handleTouch);
        }
    }

    public void setBundle(){
        Intent intent = getIntent();
        if(intent.hasExtra("newQ")){
            bundle = intent.getExtras();
            bundleState = true;
            newQ = bundle.getBoolean("newQ");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setQuestion() {
        TextView question  = findViewById(R.id.question);
        if (newQ) {
            answer = new Random().nextInt(8) + 1;
            num1 = new Random().nextInt(answer);
            num2 = answer - num1;
        } else {
            answer = bundle.getInt("answer");
            num1 = bundle.getInt("num1");
            num2 = bundle.getInt("num2");
        }
        String questionText = num1 + operator + num2 + "=?";
        question.setText(questionText);
    }

    public void setButtons(){
        for(int i=0; i<buttonNum; i++) {
            id = getResources().getIdentifier("button" + i, "id", getPackageName());
            button = findViewById(id);
            int given = Integer.parseInt(button.getText().toString());
            Bundle finalBundle = bundle;
            button.setOnClickListener(v -> {
                finalBundle.putInt("num1", num1);
                finalBundle.putInt("num2", num2);
                finalBundle.putInt("answer", answer);
                finalBundle.putInt("given", given);
                finalBundle.putString("operator", operator);
                Intent intent2 = new Intent(questionPage.this, answerPage.class);
                intent2.replaceExtras(finalBundle);
                startActivity(intent2);
            });
        }
    }
}