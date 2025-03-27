package com.example.myapplication;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner source_unit_spinner;
    Spinner destination_unit_spinner;
    EditText inputValue;
    TextView resultText;
    Button submit_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        source_unit_spinner= findViewById(R.id.SU_spinner);
        destination_unit_spinner= findViewById(R.id.DU_spinner);
        inputValue= findViewById(R.id.enter_value);
        resultText= findViewById(R.id.result_text);
        submit_button= findViewById(R.id.submitButton);
        String[] units= {"Inch","Foot","Yard","Mile","Pound","Ounce","Ton","Celsius","Fahrenheit","Kelvin"};
        ArrayAdapter<String> spinner_menu= new ArrayAdapter<>(this,android.R.layout.simple_dropdown_item_1line, units);
        source_unit_spinner.setAdapter(spinner_menu);
        destination_unit_spinner.setAdapter(spinner_menu);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public double conversionProcess(double val, String su, String du){
        double result;
        Map<String, Double> length_conversions = new HashMap<>();
        length_conversions.put("Inch", 2.54);
        length_conversions.put("Foot", 30.48);
        length_conversions.put("Yard", 91.44);
        length_conversions.put("Mile", 1609.34);

        Map<String, Double> weight_conversions = new HashMap<>();
        weight_conversions.put("Pound", 0.453592);
        weight_conversions.put("Ounce", 28.3495);
        weight_conversions.put("Ton", 907.185);

        if(length_conversions.containsKey(su) && length_conversions.containsKey(du))
        {
            result= val*(length_conversions.get(du)/length_conversions.get(su));
            return result;
        }
        else if(weight_conversions.containsKey(su) && weight_conversions.containsKey(du))
        {
            result= val*(weight_conversions.get(du)/weight_conversions.get(su));
            return result;
        }
        else if(su.equals("Celsius") && du.equals("Fahrenheit"))
        {
            result=  (val * 1.8) + 32;
            return result;
        }
        else if(su.equals("Fahrenheit") && du.equals("Celsius"))
        {
            result=  (val - 32) / 18;
            return result;
        }
        else if(su.equals("Celsius") && du.equals("Kelvin"))
        {
            result=  val + 273.15;
            return result;
        }
        else if(su.equals("Kelvin") && du.equals("Celsius"))
        {
            result=  val - 273.15;
            return result;
        }
        else {
            Toast.makeText(this,"Invalid conversion",Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    public void unitConversion(){
        String source_unit= source_unit_spinner.getSelectedItem().toString();
        String destination_unit= destination_unit_spinner.getSelectedItem().toString();
        String input_value= inputValue.getText().toString();
        double value;
        double value_converted;

        if (input_value.isEmpty()){
            Toast.makeText(this,"Please enter a numeric value",Toast.LENGTH_SHORT).show();
            return;
        }
         if (source_unit.equals(destination_unit)){
            Toast.makeText(this,"source and destination unit are same",Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            value= Double.parseDouble(input_value);
        } catch (NumberFormatException e) {
            Toast.makeText(this,"Invalid value",Toast.LENGTH_SHORT).show();
            return;
        }

        value_converted= conversionProcess(value,source_unit,destination_unit);
        resultText.setText(String.format(" %.2f %s",value_converted,destination_unit));
    }

    public void submitValues(View view) {
        unitConversion();
    }

}
