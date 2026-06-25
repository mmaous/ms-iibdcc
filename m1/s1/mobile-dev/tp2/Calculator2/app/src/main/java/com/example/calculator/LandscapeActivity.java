//package com.example.calculator;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.os.Bundle;
//
//public class LandscapeActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_landscape);
//    }
//
//    @Override
//    public void onConfigurationChanged(Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//
//        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
//            // Switch to LandscapeActivity
//            startActivity(new Intent(this, LandscapeActivity.class));
//            finish();
//        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
//            // Switch to PortraitActivity
//            startActivity(new Intent(this, PortraitActivity.class));
//            finish();
//        }
//    }
//
//}

package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.utils.Calculator;
import com.example.calculator.utils.ObservableValue;

public class LandscapeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView resultDisplay;
    private TextView expressionDisplay;
    private ObservableValue<StringBuilder> expression = new ObservableValue<>(new StringBuilder(), this::updateExpression);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape);
        initializeViews();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // Switch to LandscapeActivity
            startActivity(new Intent(this, LandscapeActivity.class));
            finish();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            // Switch to PortraitActivity
            startActivity(new Intent(this, PortraitActivity.class));
            finish();
        }
    }
    public void initializeViews(){

        // Initialize TextView for display
        resultDisplay = findViewById(R.id.resultDisplay);
        expressionDisplay = findViewById(R.id.expressionDisplay);

        // Set OnClickListener for number buttons
        findViewById(R.id.btn0).setOnClickListener(this);
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);
        findViewById(R.id.btn8).setOnClickListener(this);
        findViewById(R.id.btn9).setOnClickListener(this);


//         Set OnClickListener for operation buttons
        findViewById(R.id.btnPlus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMinus).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnMultiply).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDevide).setOnClickListener(this::handleOperation);
        findViewById(R.id.btnDot).setOnClickListener(this::handleOperation);

        // Set OnClickListener for trigonometric function buttons
        findViewById(R.id.btnSin).setOnClickListener(this);
        findViewById(R.id.btnCos).setOnClickListener(this);
        findViewById(R.id.btnTan).setOnClickListener(this);
        findViewById(R.id.btnPi).setOnClickListener(this);

        // Set OnClickListener for exponential function buttons
        findViewById(R.id.btnExp).setOnClickListener(this);
        findViewById(R.id.btnLn).setOnClickListener(this);
        findViewById(R.id.btnLog).setOnClickListener(this);
        findViewById(R.id.btnSqrt).setOnClickListener(this);
//        findViewById(R.id.btnSquare).setOnClickListener(this);

        // parathanses button
        findViewById(R.id.btnLeftBracket).setOnClickListener(this::handleInput);
        findViewById(R.id.btnRightBracket).setOnClickListener(this::handleInput);

//        //controle button
        findViewById(R.id.btnDel).setOnClickListener(this::handleSupp);
        findViewById(R.id.btnAC).setOnClickListener(this::handleClear);

        findViewById(R.id.btnAns).setOnClickListener(this::addAns);

        // Set OnClickListener for equal button
        findViewById(R.id.btnResult).setOnClickListener(this::evaluateExpression);

        // special buttons
//        findViewById(R.id.btnLeft).setOnClickListener(this::handleLeftDirection);
//        findViewById(R.id.btnRight).setOnClickListener(this::handleRightDirection);
    }

    @Override
    public void onClick(View v) {
        expression.setValue(expression.getValue().append(((TextView)v).getText()));
    }

//    public void handleNumber(View view){
//         Handle the number button click
//        expression.setValue(expression.getValue().append(((TextView)view).getText()));
//    }

    public void addAns(View view){
        expression.setValue(expression.getValue().append(resultDisplay.getText()));
    }
    public void evaluateExpression(View view){
        // Handle the equal button click
        try {
            double result = Calculator.evaluate(expression.getValue().toString());
            resultDisplay.setText(String.valueOf(result));
        } catch (IllegalArgumentException e){
            resultDisplay.setText("Error");
        }
    }

    public void handleOperation(View view){
        String input = expressionDisplay.getText().toString();
        if(input.length() > 0 && Calculator.isOperator(input.charAt(input.length() - 1))){
            input = input.substring(0, input.length() - 1) ;
        }
        input += ((TextView)view).getText();
        expression.setValue(new StringBuilder(input));
    }


    public void handleInput(View view){
        expression.setValue(expression.getValue().append(((TextView)view).getText()));
    }

    public void handleSupp(View view){
        // Handle the delete button click
        if(expression.getValue().length() > 0){
            expression.setValue(expression.getValue().deleteCharAt(expression.getValue().length() - 1));
        }
    }

    public void handleClear(View view){
        // Handle the clear button click
        expression.setValue(new StringBuilder());
    }

    public void updateExpression(){
        // Update the expression
        expressionDisplay.setText(expression.getValue());
    }



}