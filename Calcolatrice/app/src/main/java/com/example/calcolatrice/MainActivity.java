package com.example.calcolatrice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    final static int MAX_OPERAND_LENGTH = 20;
    TextView displayMain;
    boolean newOperation = true;
    boolean dotFlag = false;
    boolean resFlag = false;
    boolean error = false;
    double op1 = 0;
    double op2 = 0;
    int opLength = 0;
    String operator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        displayMain = findViewById(R.id.displayMain);
    }

    public void numClick(View v){
        Button b = findViewById(v.getId());
        if(opLength < MAX_OPERAND_LENGTH && !error){
            if(newOperation){
                displayMain.setText("");
                newOperation = false;
            }
            displayMain.append(b.getText());
            opLength++;
        }

    }

    public void operatorClick(View v){
        Button b = findViewById(v.getId());
        String text = null;

        if (operator == null && !error){
            operator = "" + b.getText();

            if(!resFlag){
                text = "" + displayMain.getText();
            }
            else{
                text = "" + displayMain.getText().subSequence(1, displayMain.getText().length());
            }
            op1 = Double.parseDouble(text);

            displayMain.setText(operator);
            dotFlag = false;
            newOperation = false;
            resFlag = false;
            opLength = 0;
        }
    }

    public void dotClick(View v){
        Button b = findViewById(v.getId());
        if(!dotFlag && !resFlag && !error){
            displayMain.append(".");
            dotFlag = true;
        }

    }

    public void clearClick(View v){
        displayMain.setText("0");
        clearMem();
    }

    public void equalClick(View v){
        Button b = findViewById(v.getId());
        String text = "" + displayMain.getText();
        double res = 0;

        if (operator != null && (text.length()>1) && !error){
            op2 = Double.parseDouble(text.substring(1,text.length()));
            res = evaluate(op1, operator, op2);

            if(!error){
                displayMain.setText("=" + String.format("%.4f",res));
            }
            else{
                displayMain.setText("ERROR");
            }

            clearMem();
            resFlag = true;
        }
    }

    private void clearMem(){
        op1 = 0;
        op2 = 0;
        opLength = 0;
        operator = null;
        newOperation = true;
        dotFlag = false;
        resFlag = false;
        error = false;
    }

    private double evaluate(double x, String op, double y){
        double res = 0;
        switch (op){
            case "+":
                res = x + y;
                break;
            case "-":
                res = x - y;
                break;
            case "X":
                res = x * y;
                break;
            case "/":
                res = x / y;
                break;
            case "%":
                res = (x/100) * y;
                break;
            default:
                error=true;
        }

        if(("" + String.format("%.4f",res)).length()>MAX_OPERAND_LENGTH || res > Double.MAX_VALUE){
            res = 0;
            error = true;
        }
        return res;
    }
}