package com.example.coronastatistics;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;

import javax.net.ssl.HttpsURLConnection;

public class DataDownloader extends AsyncTask<ScrappedDataTypes, Integer, Long> {

    private DataStorage dataStorage;
    private URLStorage urlStorage;

    public DataDownloader(DataStorage dataStorage, URLStorage urlStorage) {
        this.dataStorage = dataStorage;
        this.urlStorage = urlStorage;
    }

    @Override
    protected Long doInBackground(ScrappedDataTypes... scrappedDataTypes) {
        PriorityQueue<ScrappedDataTypes> dataToDownload = new PriorityQueue<>(Arrays.asList(scrappedDataTypes));
        int tries = 100;

        boolean headlinesWritten = false;
        InputStream inputStream;
        BufferedReader bufferedReader;

        while (true) {
            if (dataToDownload.isEmpty() || tries == 0) break;
            ScrappedDataTypes scrappedDataType = dataToDownload.poll();
            HttpsURLConnection httpsURLConnection = getAssociatedConnection(scrappedDataType);

            try {
                inputStream = httpsURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                if (!headlinesWritten) {
                    dataStorage.initializeHeadlines(bufferedReader.readLine());
                    headlinesWritten = true;
                }
                String previousLine = null;
                String newLine = null;
                while (true) {
                    previousLine = newLine;
                    newLine = bufferedReader.readLine();
                    if (newLine == null) break;
                }
                validateAndInitialize(previousLine, scrappedDataType);
            } catch (Exception ex) {
                urlStorage.reinitializeCertainConnection(scrappedDataType);
                dataToDownload.add(scrappedDataType);
                tries--;
            }
        }
        return null;
    }

    private void validateAndInitialize(String dataLine, ScrappedDataTypes scrappedDataType) {
        switch (scrappedDataType) {
            case NEW_CASES:
                dataStorage.initializeNewCaseValues(dataLine);
                break;
            case NEW_DEATHS:
                dataStorage.initializeNewDeathValues(dataLine);
                break;
            case TOTAL_CASES:
                dataStorage.initializeTotalCaseValues(dataLine);
                break;
            default:
                dataStorage.initializeTotalDeathValues(dataLine);
                break;
        }
    }

    private HttpsURLConnection getAssociatedConnection(ScrappedDataTypes scrappedDataType) {
        switch (scrappedDataType) {
            case TOTAL_CASES:
                return urlStorage.getTotalCasesUrlConnection();
            case TOTAL_DEATHS:
                return urlStorage.getTotalDeathsUrlConnection();
            case NEW_CASES:
                return urlStorage.getNewCasesUrlConnection();
            default:
                return urlStorage.getNewDeathsUrlConnection();
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
