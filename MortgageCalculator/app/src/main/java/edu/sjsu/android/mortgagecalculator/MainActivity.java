package edu.sjsu.android.mortgagecalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.MathContext;

public class MainActivity extends AppCompatActivity {
    private EditText editText ;
    private TextView textView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.borrowedEditText) ;
        textView = (TextView) findViewById(R.id.payable) ;

        //Code for seekbar
        SeekBar bar = (SeekBar) findViewById(R.id.interestBar) ;
        bar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener()
        {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar)
            {
                Toast.makeText(seekBar.getContext(), "Interest Rate: " + seekBar.getProgress() / 10.0 + "%", Toast.LENGTH_SHORT).show() ;
            }
        });

    }
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.calc:
                //Validating the input
                if (editText.getText().length() == 0)
                {
                    Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_LONG).show() ;
                    return;
                }

                //fetching the values from the fields
                double borrowed = Double.parseDouble(editText.getText().toString()) ;
                double rate = ((SeekBar) findViewById(R.id.interestBar)).getProgress() / 1200.0 ;
                boolean tax = ((CheckBox) findViewById(R.id.taxCheckBox)).isChecked() ;
                double payable = 0 ;

                //if statements to check which radiobutton of loan term is clicked
                if (((RadioButton) findViewById(R.id.radioButton1)).isChecked())
                {
                    payable = CalculatorUtil.calculatePayable(borrowed,rate,15);
                }
                else if (((RadioButton) findViewById(R.id.radioButton2)).isChecked())
                {
                    payable = CalculatorUtil.calculatePayable(borrowed,rate,20);
                }
                else
                {
                    payable = CalculatorUtil.calculatePayable(borrowed,rate,30);
                }

                if (tax) {
                    payable += borrowed * 0.001 ;
                }

                textView.setText(String.valueOf("Monthly Payment: $" + payable));

                break;
        }
    }
}