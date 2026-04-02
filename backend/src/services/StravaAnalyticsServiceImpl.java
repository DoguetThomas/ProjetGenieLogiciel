package services;

import dto.*;

import java.util.ArrayList;
import java.util.List;

import model.ActivityImpl;
import model.ActivityModel;

public class StravaAnalyticsServiceImpl implements AnalyticsService{
    private List<ActivityModel> activities;

    public StravaAnalyticsServiceImpl(){
        this.activities = new ArrayList<>();
        ActivityModel activity1 = new ActivityImpl("1",2000,15,"BIKE",6000,80,147,200,45,1500,1500,2000,500,500);
        ActivityModel activity2 = new ActivityImpl("2",2000,15,"RUN",6000,80,147,200,45,1500,1500,2000,500,500);
        this.activities.add(activity1);
        this.activities.add(activity2);
    }
    @Override
    public AllActivitiesDto getAllActivities(){
        List<ActivityDto> activitiesDto = new ArrayList<>();
        for (ActivityModel activity : this.activities){
            if (activity !=null ){
                if (activity.getSport() == "BIKE"){
                    ActivityTypeDto.RUN
                }
                else if (activity.getSport() == "RUN"){

                }
                ActivityDto activityDto = new ActivityDto(activity.getId(), activity.getDistance(), , activity.get);
            }
        }
    }

    @Override
    public SummaryDto getSummary(String id) {
        return null;
    }

    @Override
    public RouteDto getRoute(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsAltitude(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsSpeed(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsHeartRate(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsPower(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsCadence(String id) {
        return null;
    }

    @Override
    public MetricDto getMetricsGroundTime(String id) {
        return null;
    }

    @Override
    public PaceDto getMetricsPace(String id) {
        return null;
    }

    @Override
    public ZoneDto getMetricsZone(String id) {
        return null;
    }
}
