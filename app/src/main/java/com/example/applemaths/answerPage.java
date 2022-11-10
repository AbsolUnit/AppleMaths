package com.example.applemaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class answerPage extends AppCompatActivity {

    Bundle bundle;
    int answer;
    int given;
    int num1;
    int num2;
    boolean correct;
    String operator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ans_page);

        getBundle();
        setTextViews();
        setButtons();

    }

    public void getBundle(){
        bundle = getIntent().getExtras();
        answer = bundle.getInt("answer");
        given = bundle.getInt("given");
        num1 = bundle.getInt("num1");
        num2 = bundle.getInt("num2");
        operator = bundle.getString("operator");
    }

    public void setTextViews(){
        String str;
        correct = answer == given;
        TextView responseView  = findViewById(R.id.response);
        TextView answerView  = findViewById(R.id.answerView);
        if(correct){
            str = num1 + operator + num2 + "=" + answer;
            responseView.setTextColor(0xFF00FF00);
            responseView.setText(R.string.yay);
        }else{
            str = num1 + operator + num2 + "=?";
            responseView.setTextColor(0xFFFF0000);
            responseView.setText(R.string.oops);
        }
        answerView.setText(str);
    }

    public void setButtons(){
        Button newQues = findViewById(R.id.buttonNew);
        Button tryAgain = findViewById(R.id.buttonTry);
        newQues.setOnClickListener(v -> {
            Intent intent = new Intent(answerPage.this, questionPage.class);
            bundle.putInt("num1", num1);
            bundle.putInt("num2", num2);
            bundle.putInt("answer", answer);
            bundle.putInt("given", given);
            bundle.putBoolean("newQ", true);
            bundle.putString("operator", operator);
            intent.replaceExtras(bundle);
            startActivity(intent);
        });

        if(!correct) {
            tryAgain.setOnClickListener(v -> {
                Intent intent = new Intent(answerPage.this, questionPage.class);
                bundle.putInt("num1", num1);
                bundle.putInt("num2", num2);
                bundle.putInt("answer", answer);
                bundle.putInt("given", given);
                bundle.putBoolean("newQ", false);
                bundle.putString("operator", operator);
                intent.replaceExtras(bundle);
                startActivity(intent);
            });
        }else{
            tryAgain.setBackgroundColor(0xFFFF0000);
        }
    }
}