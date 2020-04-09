package com.example.coronastatistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;

public class DataStorage {

    private Map<Long, DataValues> data;

    public DataStorage() {
        this.data = new HashMap<>();
    }

    public void initializeHeadlines(String dataLine) {
        StringTokenizer tokenizer = new StringTokenizer(dataLine, ",");
        if (tokenizer.hasMoreTokens()) tokenizer.nextToken();

        long counter = 0;
        DataValues dataValues = null;
        while (tokenizer.hasMoreTokens()) {
            dataValues = new DataValues();
            dataValues.setCountry(tokenizer.nextToken());
            data.put(counter++, dataValues);
        }
    }

    public void initializeNewDeathValues(String dataLine) {
        StringTokenizer tokenizer = new StringTokenizer(dataLine, ",");
        long counter = 0;
        String date = null;
        if (tokenizer.hasMoreTokens()) date = tokenizer.nextToken();
        while (tokenizer.hasMoreTokens()) {
            data.get(counter).setDate(date);
            data.get(counter).setNewDeaths(Integer.parseInt(tokenizer.nextToken()));
            counter++;
        }
    }


    public void initializeNewCaseValues(String dataLine) {
        StringTokenizer tokenizer = new StringTokenizer(dataLine, ",");
        long counter = 0;
        if (tokenizer.hasMoreTokens()) tokenizer.nextToken();
        while (tokenizer.hasMoreTokens()) {
            data.get(counter++).setNewCases(Integer.parseInt(tokenizer.nextToken()));
        }
    }

    public void initializeTotalCaseValues(String dataLine) {
        StringTokenizer tokenizer = new StringTokenizer(dataLine, ",");
        long counter = 0;
        if (tokenizer.hasMoreTokens()) tokenizer.nextToken();
        while (tokenizer.hasMoreTokens()) {
            data.get(counter++).setTotalCases(Integer.parseInt(tokenizer.nextToken()));
        }
    }

    public void initializeTotalDeathValues(String dataLine) {
        StringTokenizer tokenizer = new StringTokenizer(dataLine, ",");
        long counter = 0;
        if (tokenizer.hasMoreTokens()) tokenizer.nextToken();
        while (tokenizer.hasMoreTokens()) {
            data.get(counter++).setTotalDeaths(Integer.parseInt(tokenizer.nextToken()));
        }
    }

    public Iterator<Long> getDataIterator() {
        ArrayList<Long> dataIdentifiers = new ArrayList<>(data.keySet());
        Collections.sort(dataIdentifiers);
        return dataIdentifiers.iterator();
    }

    public DataValues getDataValues(long identifier) {
        return data.get(identifier);
    }
}
