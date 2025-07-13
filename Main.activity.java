package com.makeeasy.emicalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText principalAmount, interestRate, loanTenure;
    private TextView result;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize UI components
        principalAmount = findViewById(R.id.principalAmount);
        interestRate = findViewById(R.id.interestRate);
        loanTenure = findViewById(R.id.loanTenure);
        result = findViewById(R.id.result);
        calculateButton = findViewById(R.id.calculateButton);

        // Button click listener
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateEMI();
            }
        });
    }

    private void calculateEMI() {
        String principalStr = principalAmount.getText().toString().trim();
        String interestStr = interestRate.getText().toString().trim();
        String tenureStr = loanTenure.getText().toString().trim();

        if (principalStr.isEmpty() || interestStr.isEmpty() || tenureStr.isEmpty()) {
            result.setText("Please fill in all fields.");
            return;
        }

        try {
            double principal = Double.parseDouble(principalStr);
            double annualInterestRate = Double.parseDouble(interestStr);
            int tenureMonths = Integer.parseInt(tenureStr); // already in months

            if (principal <= 0 || annualInterestRate <= 0 || tenureMonths <= 0) {
                result.setText("Enter values greater than zero.");
                return;
            }

            double monthlyInterestRate = (annualInterestRate / 100) / 12;

            // EMI calculation
            double emi = (principal * monthlyInterestRate * Math.pow(1 + monthlyInterestRate, tenureMonths)) /
                    (Math.pow(1 + monthlyInterestRate, tenureMonths) - 1);

            DecimalFormat df = new DecimalFormat("0.00");
            result.setText("Your Monthly EMI: ₹ " + df.format(emi));

        } catch (NumberFormatException e) {
            result.setText("Invalid input! Please enter numeric values.");
        }
    }
}
