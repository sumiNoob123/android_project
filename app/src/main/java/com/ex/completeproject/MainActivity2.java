package com.ex.completeproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class MainActivity2 extends AppCompatActivity {

    EditText nitrogen, phosphorus, potassium, temperature, rainfall, ph, humidity;
    TextView textview;
    Button button;

    // Pre-loaded Python objects
    Python py;
    PyObject pyObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);

        nitrogen = findViewById(R.id.nitrogeninput);
        phosphorus = findViewById(R.id.phosphorusinput);
        potassium = findViewById(R.id.potassiuminput);
        temperature = findViewById(R.id.tempinput);
        rainfall = findViewById(R.id.raininput);
        ph = findViewById(R.id.pHinput);
        humidity = findViewById(R.id.humidityinput);
        button = findViewById(R.id.getbtn);
        textview = findViewById(R.id.optxt);

        //Background thread used to pre_load the python_environment_script while starting-up the app
        new Thread(() -> {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(this));
            }
            py = Python.getInstance();
            pyObj = py.getModule("predict");
        }).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textview.setText("");

                try {
                    // Read inputs
                    String nStr = nitrogen.getText().toString().trim();
                    String pStr = phosphorus.getText().toString().trim();
                    String kStr = potassium.getText().toString().trim();
                    String tempStr = temperature.getText().toString().trim();
                    String humStr = humidity.getText().toString().trim();
                    String phStr = ph.getText().toString().trim();
                    String rainStr = rainfall.getText().toString().trim();

                    if (nStr.isEmpty() || pStr.isEmpty() || kStr.isEmpty() ||
                            tempStr.isEmpty() || humStr.isEmpty() || phStr.isEmpty() || rainStr.isEmpty()) {
                        textview.setText("Please fill all fields with valid numbers");
                        return;
                    }

                    double n = Double.parseDouble(nStr);
                    double p = Double.parseDouble(pStr);
                    double k = Double.parseDouble(kStr);
                    double temp = Double.parseDouble(tempStr);
                    double hum = Double.parseDouble(humStr);
                    double phVal = Double.parseDouble(phStr);
                    double rain = Double.parseDouble(rainStr);

                    // Python prediction in background
                    new Thread(() -> {
                        try {
                            PyObject result = pyObj.callAttr("recommendation", n, p, k, temp, hum, phVal, rain);
                            runOnUiThread(() -> textview.setText("Recommended Crop: " + result.toString()));
                        } catch (Exception e) {
                            runOnUiThread(() -> textview.setText("Error: " + e.getMessage()));
                            e.printStackTrace();
                        }
                    }).start();

                } catch (NumberFormatException e) {
                    textview.setText("Invalid input. Please enter only numbers (no spaces or letters).");
                }
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}