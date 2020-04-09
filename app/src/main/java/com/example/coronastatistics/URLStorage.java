package com.example.coronastatistics;

import android.content.res.Resources;
import android.os.Handler;

import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class URLStorage {

    private URL totalDeathsUrl;
    private URL newCasesUrl;
    private URL newDeathsUrl;
    private URL totalCasesUrl;

    private HttpsURLConnection totalDeathsUrlConnection;
    private HttpsURLConnection newCasesUrlConnection;
    private HttpsURLConnection newDeathsUrlConnection;
    private HttpsURLConnection totalCasesUrlConnection;

    public URLStorage(Resources resources) {
        init(resources);
    }

    private void init(Resources resources) {
        initializeUrls(resources);
        initializeConnections();
        connectToConnections();
    }

    private void initializeUrls(Resources resources) {
        try {
            totalCasesUrl = new URL(resources.getString(R.string.total_cases));
            newDeathsUrl = new URL(resources.getString(R.string.new_deaths));
            newCasesUrl = new URL(resources.getString(R.string.new_cases));
            totalDeathsUrl = new URL(resources.getString(R.string.total_deaths));
        } catch (Exception ignored) {
        }
    }

    private void initializeConnections() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                try {
                    totalDeathsUrlConnection = (HttpsURLConnection) totalDeathsUrl.openConnection();
                    newCasesUrlConnection = (HttpsURLConnection) newCasesUrl.openConnection();
                    newDeathsUrlConnection = (HttpsURLConnection) newDeathsUrl.openConnection();
                    totalCasesUrlConnection = (HttpsURLConnection) totalCasesUrl.openConnection();
                } catch (Exception ignored) {

                }
//            }
//        }, 1000);
    }

    private void connectToConnections() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
                try {
                    totalDeathsUrlConnection.connect();
                    newCasesUrlConnection.connect();
                    newDeathsUrlConnection.connect();
                    totalCasesUrlConnection.connect();
                } catch (Exception ignored) {
                }
//            }
//        }, 1000);
    }

    public URL getTotalDeathsUrl() {
        return totalDeathsUrl;
    }

    public URL getTotalCasesUrl() {
        return totalCasesUrl;
    }

    public URL getNewCasesUrl() {
        return newCasesUrl;
    }

    public URL getNewDeathsUrl() {
        return newDeathsUrl;
    }

    public HttpsURLConnection getTotalDeathsUrlConnection() {
        return totalDeathsUrlConnection;
    }

    public HttpsURLConnection getNewCasesUrlConnection() {
        return newCasesUrlConnection;
    }

    public HttpsURLConnection getNewDeathsUrlConnection() {
        return newDeathsUrlConnection;
    }

    public HttpsURLConnection getTotalCasesUrlConnection() {
        return totalCasesUrlConnection;
    }
}
