package com.example.coronastatistics;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class DataDownloader extends AsyncTask<HttpURLConnection, Integer, Long> {

    private DataStorage dataStorage;

    public DataDownloader(DataStorage dataStorage) {
        this.dataStorage = dataStorage;
    }

    @Override
    protected Long doInBackground(HttpURLConnection... httpURLConnections) {
        try {
            boolean headlinesWritten = false;
            InputStream is;
            BufferedReader br;
            for (int i = 0; i < httpURLConnections.length; i++) {
                is = httpURLConnections[i].getInputStream();
                br = new BufferedReader(new InputStreamReader(is));
                if (!headlinesWritten) {
                    dataStorage.initializeHeadlines(br.readLine());
                    headlinesWritten = true;
                }
                String previousLine = null;
                String newLine = null;
                while (true) {
                    previousLine = newLine;
                    newLine = br.readLine();
                    if (newLine == null) break;
                }
                validateAndInitialize(previousLine, i);
            }
            String s = "sad";
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return null;
    }

    private void validateAndInitialize(String dataLine, int position) {
        switch (position) {
            case 0:
                dataStorage.initializeNewCaseValues(dataLine);
                break;
            case 1:
                dataStorage.initializeNewDeathValues(dataLine);
                break;
            case 2:
                dataStorage.initializeTotalCaseValues(dataLine);
                break;
            default:
                dataStorage.initializeTotalDeathValues(dataLine);
                break;
        }
    }

//    @Override
//    protected void onProgressUpdate(Integer... progress)
//    {
//
//    }
//
//    @Override
//    protected void onPostExecute(Long result) {
//
//    }
}
