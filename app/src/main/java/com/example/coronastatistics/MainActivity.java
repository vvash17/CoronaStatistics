package com.example.coronastatistics;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

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

    private URLStorage urlStorage;

    private DataStorage dataStorage;

    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        urlStorage = new URLStorage(this.getResources());
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dataStorage = new DataStorage();
            }
        }, 1000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initialize();
            }
        }, 2000);
    }

    //comment out  urlStorage.getTotalDeathsUrlConnection()
    private void initialize() {
        new DataDownloader(dataStorage).execute(urlStorage.getNewCasesUrlConnection(), urlStorage.getNewDeathsUrlConnection()
                , urlStorage.getTotalCasesUrlConnection());
    }

}
