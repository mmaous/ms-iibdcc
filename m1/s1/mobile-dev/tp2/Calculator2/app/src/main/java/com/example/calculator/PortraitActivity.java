package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class PortraitActivity extends AppCompatActivity {
    private TextView display;
    private String currentInput = "";
    private double operand1 = 0;
    private String operator = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portrait);

        display = findViewById(R.id.display);

    }

    public void onNumberClick(View view) {
        // Handle number button click
        String value = ((TextView) view).getText().toString();
        currentInput += value;
        updateDisplay();
    }

    public void onOperatorClick(View view) {
        // Handle operator button click
        operator = ((TextView) view).getText().toString();
        operand1 = Double.parseDouble(currentInput);
        currentInput = "";
    }

    public void onChangeSign(View view) {
        // Handle change sign button click
        if (!currentInput.isEmpty()) {
            double currentValue = Double.parseDouble(currentInput);
            currentValue = -currentValue;
            currentInput = String.valueOf(currentValue);
            updateDisplay();
        }
    }

    public void onEqualsClick(View view) {
        // Handle equals button click
        if (!currentInput.isEmpty()) {
            double operand2 = Double.parseDouble(currentInput);
            double result = performOperation(operand1, operand2, operator);
            currentInput = String.valueOf(result);
            updateDisplay();
        }
    }

    public void onPercentageClick(View view) {
        // Handle percentage button click
        if (!currentInput.isEmpty()) {
            double currentValue = Double.parseDouble(currentInput);
            currentValue = currentValue / 100.0;
            currentInput = String.valueOf(currentValue);
            updateDisplay();
        }
    }

    public void onClearClick(View view) {
        // Handle clear button click
        currentInput = "";
        operand1 = 0;
        operator = "";
        updateDisplay();
    }

    private double performOperation(double operand1, double operand2, String operator) {
        // Perform the calculation based on the operator
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "x":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    // Handle division by zero
                    return Double.NaN;
                }
            default:
                return Double.NaN;
        }
    }

    private void updateDisplay() {
        // Update the display with the current input
        display.setText(currentInput);
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

}