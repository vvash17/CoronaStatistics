package com.example.coronastatistics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Iterator;

/**
 * Skeleton of an Android Things activity.
 * <p>
 * Android Things peripheral APIs are accessible through the PeripheralManager
 * For example, the snippet below will open a GPIO pin and set it to HIGH:
 * <p>
 * PeripheralManager manager = PeripheralManager.getInstance();
 * try {
 * Gpio gpio = manager.openGpio("BCM6");
 * gpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);
 * gpio.setValue(true);
 * } catch (IOException e) {
 * Log.e(TAG, "Unable to access GPIO");
 * }
 * <p>
 * You can find additional examples on GitHub: https://github.com/androidthings
 */
public class MainActivity extends Activity {

    private ConstraintLayout dataLayout;
    private LinearLayout countriesLayout;
    private LinearLayout newCasesLayout;
    private LinearLayout newDeathsLayout;
    private LinearLayout totalCasesLayout;
    private LinearLayout totalDeathsLayout;


    private URLStorage urlStorage;
    private Handler handler;
    private DataStorage dataStorage;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        dataLayout = findViewById(R.id.mainDataLayout);
        countriesLayout = findViewById(R.id.countriesLayout);
        newCasesLayout = findViewById(R.id.newCasesLayout);
        newDeathsLayout = findViewById(R.id.newDeathsLayout);
        totalCasesLayout = findViewById(R.id.totalCasesLayout);
        totalDeathsLayout = findViewById(R.id.totalDeathsLayout);

        handler = new Handler();
        urlStorage = new URLStorage(this.getResources());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                dataStorage = new DataStorage();
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initializeData();
            }
        }, 1000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fillVisualSide();
            }
        }, 5000);
    }

    private void fillVisualSide() {
        Iterator<Long> dataIdentifiers = dataStorage.getDataIterator();
        DataValues values;
        while (dataIdentifiers.hasNext()) {
            values = dataStorage.getDataValues(dataIdentifiers.next());
//            LinearLayout rowLayout = new LinearLayout(getBaseContext());
//
//            rowLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
//            rowLayout.setOrientation(LinearLayout.HORIZONTAL);
//            rowLayout.setDividerPadding(10);
            //children of layout2 LinearLayout
            TextView country = new TextView(getBaseContext());
            country.setText(values.getCountry());
            TextView newCases = new TextView(getBaseContext());
            newCases.setText(String.valueOf(values.getNewCases()));
            TextView newDeaths = new TextView(getBaseContext());
            newDeaths.setText(String.valueOf(values.getNewDeaths()));
            TextView totalCases = new TextView(getBaseContext());
            totalCases.setText(String.valueOf(values.getTotalCases()));
            TextView totalDeaths = new TextView(getBaseContext());
            totalDeaths.setText(String.valueOf(values.getTotalDeaths()));

//            ShapeDrawable sd = new ShapeDrawable();
//
//            // Specify the shape of ShapeDrawable
//            sd.setShape(new RectShape());
//
//            // Specify the border color of shape
//            sd.getPaint().setColor(Color.RED);
//
//            // Set the border width
//            sd.getPaint().setStrokeWidth(10f);
//
//            // Specify the style is a Stroke
//            sd.getPaint().setStyle(Paint.Style.STROKE);
//
//            country.setBackground(sd);
//            newCases.setBackground(sd);
//            newDeaths.setBackground(sd);
//            totalCases.setBackground(sd);
//            totalDeaths.setBackground(sd);

            countriesLayout.addView(country);
            newCasesLayout.addView(newCases);
            newDeathsLayout.addView(newDeaths);
            totalCasesLayout.addView(totalCases);
            totalDeathsLayout.addView(totalDeaths);

//            dataLayout.addView(rowLayout);
        }
    }

    //comment out  urlStorage.getTotalDeathsUrlConnection()
    private void initializeData() {
        new DataDownloader(dataStorage, urlStorage).execute(ScrappedDataTypes.NEW_CASES, ScrappedDataTypes.NEW_DEATHS,
                ScrappedDataTypes.TOTAL_CASES, ScrappedDataTypes.TOTAL_DEATHS);
    }

}
