package com.main.app.currency.exchange.rates.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.main.app.currency.exchange.rates.R;

public class CalculatorFragment extends Fragment {

    private EditText calculation, result;
    private String current, finalResult;
    private boolean dot_inserted, operator_inserted;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_calculator, container, false);
        super.onCreate(savedInstanceState);

        calculation = fragmentView.findViewById(R.id.calculation);
        result = fragmentView.findViewById(R.id.calculation_result);

        current = "";
        finalResult = "";
        dot_inserted = false;
        operator_inserted = false;

        Button btn0 = fragmentView.findViewById(R.id.button_zero);
        Button btn1 = fragmentView.findViewById(R.id.button_one);
        Button btn2 = fragmentView.findViewById(R.id.button_two);
        Button btn3 = fragmentView.findViewById(R.id.button_three);
        Button btn4 = fragmentView.findViewById(R.id.button_four);
        Button btn5 = fragmentView.findViewById(R.id.button_five);
        Button btn6 = fragmentView.findViewById(R.id.button_six);
        Button btn7 = fragmentView.findViewById(R.id.button_seven);
        Button btn8 = fragmentView.findViewById(R.id.button_eight);
        Button btn9 = fragmentView.findViewById(R.id.button_nine);
        Button btnDot = fragmentView.findViewById(R.id.button_point);

        Button btnMultiplication = fragmentView.findViewById(R.id.button_multiplication);
        Button btnDivision = fragmentView.findViewById(R.id.button_division);
        Button btnSubtraction = fragmentView.findViewById(R.id.button_subtraction);
        Button btnAddition = fragmentView.findViewById(R.id.button_addition);

        Button btnEqual = fragmentView.findViewById(R.id.button_sum);
        Button btnAC = fragmentView.findViewById(R.id.btnClear);
        Button btnDel = fragmentView.findViewById(R.id.btnRemove);

        btn0.setOnClickListener(v -> {
            current = current + "0";
            displayOne();
        });

        btn1.setOnClickListener(v -> {
            current = current + "1";
            displayOne();
        });

        btn2.setOnClickListener(v -> {
            current = current + "2";
            displayOne();
        });

        btn3.setOnClickListener(v -> {
            current = current + "3";
            displayOne();
        });

        btn4.setOnClickListener(v -> {
            current = current + "4";
            displayOne();
        });

        btn5.setOnClickListener(v -> {
            current = current + "5";
            displayOne();
        });

        btn6.setOnClickListener(v -> {
            current = current + "6";
            displayOne();
        });

        btn7.setOnClickListener(v -> {
            current = current + "7";
            displayOne();
        });

        btn8.setOnClickListener(v -> {
            current = current + "8";
            displayOne();
        });

        btn9.setOnClickListener(v -> {
            current = current + "9";
            displayOne();
        });

        btnDot.setOnClickListener(v -> {
            if (current.isEmpty()) {
                current = "0.";
                dot_inserted = true;
            }

            if (!dot_inserted) {
                current = current + ".";
                dot_inserted = true;
            }
            displayOne();

        });

        btnAC.setOnClickListener(v -> {
            clear();
            displayOne();
            displayTwo();
        });

        btnDel.setOnClickListener(v -> {
            backspace();
            displayOne();
        });

        btnDivision.setOnClickListener(v -> {
            dot_inserted = false;
            if (!current.isEmpty()) {
                if (current.substring(current.length() - 1, current.length()).equals(".")) {
                    backspace();
                }
                if (!operator_inserted) {
                    current = current + " / ";
                    operator_inserted = true;
                }
            }
            displayOne();
        });

        btnMultiplication.setOnClickListener(v -> {
            dot_inserted = false;
            if (!current.isEmpty()) {
                if (current.substring(current.length() - 1, current.length()).equals(".")) {
                    backspace();
                }
                if (!operator_inserted) {
                    current = current + " * ";
                    operator_inserted = true;
                }
            }
            displayOne();
        });

        btnSubtraction.setOnClickListener(v -> {
            dot_inserted = false;
            if (!current.isEmpty()) {
                if (current.substring(current.length() - 1, current.length()).equals(".")) {
                    backspace();
                }
                if (!operator_inserted) {
                    current = current + " - ";
                    operator_inserted = true;
                }
            }
            displayOne();
        });

        btnAddition.setOnClickListener(v -> {
            dot_inserted = false;
            if (!current.isEmpty()) {
                if (current.substring(current.length() - 1, current.length()).equals(".")) {
                    backspace();
                }
                if (!operator_inserted) {
                    current = current + " + ";
                    operator_inserted = true;
                }
            }
            displayOne();
        });

        btnEqual.setOnClickListener(v -> {
            if (operator_inserted && !current.substring(current.length() - 1, current.length()).equals(" ")) {
                String[] tokens = current.split(" ");
                switch (tokens[1].charAt(0)) {
                    case '+':
                        finalResult = Double.toString(Double.parseDouble(tokens[0]) + Double.parseDouble(tokens[2]));
                        break;
                    case '-':
                        finalResult = Double.toString(Double.parseDouble(tokens[0]) - Double.parseDouble(tokens[2]));
                        break;
                    case '*':
                        finalResult = Double.toString(Double.parseDouble(tokens[0]) * Double.parseDouble(tokens[2]));
                        break;
                    case '/':
                        finalResult = Double.toString(Double.parseDouble(tokens[0]) / Double.parseDouble(tokens[2]));
                        break;
                }
                displayTwo();
            }
        });

        return fragmentView;
    }

    public void displayOne() {
        calculation.setText(current);
    }

    public void displayTwo() {
        result.setText(finalResult);
    }

    public void clear() {
        current = "";
        finalResult = "";
        dot_inserted = false;
        operator_inserted = false;
    }

    public void backspace() {
        if (!current.isEmpty()) {

            if (current.substring(current.length() - 1, current.length()).equals(".")) {
                dot_inserted = false;
            }

            if (current.substring(current.length() - 1, current.length()).equals(" ")) {
                current = current.substring(0, current.length() - 3);
                operator_inserted = false;
            } else {
                current = current.substring(0, current.length() - 1);
            }
        }
    }
}
