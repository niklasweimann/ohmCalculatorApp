package v1.hapra.rescalc3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ResCalc03Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Refs
        final Spinner spinner1 = findViewById(R.id.spinner1);
        final Spinner spinner2 = findViewById(R.id.spinner2);
        final Spinner spinner3 = findViewById(R.id.spinner3);
        final Spinner spinner4 = findViewById(R.id.spinner4);
        final Spinner spinner5 = findViewById(R.id.spinner5);
        final Spinner spinner6 = findViewById(R.id.spinner6);
        final Button button = findViewById(R.id.button);
        final TextView resultViewResisted = findViewById(R.id.resultViewResisted);
        final TextView resultViewTolerance = findViewById(R.id.resultViewTolerance);
        final TextView resultViewTemperatureCoefficient = findViewById(R.id.resultViewTemperatureCoefficient);

        // Fill Spinner with colors
        ArrayAdapter<CharSequence> adapterOhm = ArrayAdapter.createFromResource(this, R.array.resistor_colour_code_ohm, android.R.layout.simple_spinner_item);
        adapterOhm.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapterOhm);
        spinner2.setAdapter(adapterOhm);
        spinner3.setAdapter(adapterOhm);
        ArrayAdapter<CharSequence> adapterMultiplier = ArrayAdapter.createFromResource(this, R.array.resistor_colour_code_multiplier, android.R.layout.simple_spinner_item);
        adapterMultiplier.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner4.setAdapter(adapterMultiplier);
        ArrayAdapter<CharSequence> adapterTolerance = ArrayAdapter.createFromResource(this, R.array.resistor_colour_code_tolerance, android.R.layout.simple_spinner_item);
        adapterTolerance.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner5.setAdapter(adapterTolerance);
        ArrayAdapter<CharSequence> adapterTemperatureCoefficient = ArrayAdapter.createFromResource(this, R.array.resistor_colour_code_TemperatureCoefficient, android.R.layout.simple_spinner_item);
        adapterTemperatureCoefficient.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner6.setAdapter(adapterTemperatureCoefficient);

        // calc ohm
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double[] multiArray = {100,1000,10000,100000,1000000,10000000,100000000,1000000000,10000000000.0,100000000000.0,10,1};
                int firstband = spinner1.getSelectedItemPosition();
                int secondband = spinner2.getSelectedItemPosition();
                int thirdband = spinner3.getSelectedItemPosition();
                double multiplier = multiArray[spinner4.getSelectedItemPosition()];
                String mult;
                double ohm = ((1000 * firstband) + (100 * secondband) + (10 * thirdband)) * multiplier / 1000;
                if (ohm >= 1e9) {
                    ohm /= 1e9;
                    mult = "G";
                } else if (ohm >= 1e6) {
                    ohm /= 1e6;
                    mult = "M";
                } else if (ohm >= 1e3) {
                    ohm /= 1e3;
                    mult = "k";
                } else {
                    mult = "";
                }
                String resDisplay = getString(R.string.resistance) + " " + ohm + mult + " Ohm";
                resultViewResisted.setText(resDisplay);
            }
        });

        // get tolerance
        spinner5.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                double[] tolArray = {1,2,0.5,0.25,0.1,0.05,5,10};
                int tolIndex = spinner5.getSelectedItemPosition();
                String resDisplay = getString(R.string.tolerance) + " ±" + tolArray[tolIndex] + " %";
                resultViewTolerance.setText(resDisplay);
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        // get ppm
        spinner6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                int[] tolArray = {100,50,15,25,10,5};
                int tolIndex = spinner6.getSelectedItemPosition();
                String resDisplay = getString(R.string.ppm) + " " + tolArray[tolIndex] + " ppm";
                resultViewTemperatureCoefficient.setText(resDisplay);
            }

            public void onNothingSelected(
                    AdapterView<?> adapterView) {

            }
        });

        resultViewResisted.setText(getString(R.string.resistance) + " " + 0.0 + " Ohm");
        resultViewTemperatureCoefficient.setText(getString(R.string.ppm));
        resultViewTolerance.setText(getString(R.string.tolerance));
    }
}
