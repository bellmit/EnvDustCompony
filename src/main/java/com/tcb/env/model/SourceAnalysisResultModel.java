package com.tcb.env.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SourceAnalysisResultModel {

    private List<PollutionSourceModel> pollutionSources = new ArrayList<>();

    private double windSpeed;

    private double windDegree;

    private int windSc;

    public List<PollutionSourceModel> getPollutionSources() {
        return pollutionSources;
    }

    private List<List<Integer>> windRoseData = new ArrayList<>();

    public void setPollutionSources(List<PollutionSourceModel> pollutionSources) {
        this.pollutionSources = pollutionSources;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(double windDegree) {
        this.windDegree = windDegree;
    }

    @Override
    public String toString() {
        return "SourceAnalysisResultModel{" +
                "pollutionSources=" + pollutionSources +
                ", windSpeed=" + windSpeed +
                ", windDegree=" + windDegree +
                '}';
    }

    public List<List<Integer>> getWindRoseData() {
        return windRoseData;
    }

    public void setWindRoseData(List<List<Integer>> windRoseData) {
        this.windRoseData = windRoseData;
    }

    public int getWindSc() {
        return windSc;
    }

    public void setWindSc(int windSc) {
        this.windSc = windSc;
    }
}
