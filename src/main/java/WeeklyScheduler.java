import data.Property;
import data.Scheduler;
import data.SchedulerItem;
import data.TimePeriod;
import requests.WeeklyTimePeriodRequest;
import requests.WeeklyTimePeriodRequestWithoutWeek;
import services.impl.SchedulerManager;
import services.requests.OccupiedTimeRequest;
import services.requests.TimeRequest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class WeeklyScheduler extends SchedulerManager {
    @Override
    public List<TimePeriod> getFreeAppointments(Scheduler scheduler, String propertyName, Property property, TimeRequest timeRequest){
        List<TimePeriod> freeApp = new ArrayList<>();
        List<SchedulerItem> ocuppiedApp = getOccupiedAppointments(scheduler,propertyName,property);
        if(timeRequest instanceof WeeklyTimePeriodRequest periodTimeRequest){
            for(
                    LocalTime date = LocalTime.MIDNIGHT;
                    date.isBefore(LocalTime.of(23,59));
                    date = date.plusMinutes(15)
            ){
                WeeklyTimePeriod dateTimePeriod = new WeeklyTimePeriod(
                        date,
                        periodTimeRequest.getMinutes(),
                        periodTimeRequest.getStartPeriod(),
                        periodTimeRequest.getEndPeriod(),
                        periodTimeRequest.getDayOfWeek(),
                        periodTimeRequest.getExcludedDays()
                );
                for(SchedulerItem schedulerItem : ocuppiedApp){
                    if(!schedulerItem.getTimePeriod().isOverlap(dateTimePeriod)){
                        freeApp.add(dateTimePeriod);
                    }
                }
            }
        }

        if(timeRequest instanceof WeeklyTimePeriodRequestWithoutWeek periodTimeRequest){
            for(
                    LocalTime date = LocalTime.MIDNIGHT;
                    date.isBefore(LocalTime.of(23,59));
                    date = date.plusMinutes(15)
            ){
                for(DayOfWeek dayOfWeek:DayOfWeek.values()) {
                    WeeklyTimePeriod dateTimePeriod = new WeeklyTimePeriod(
                            date,
                            periodTimeRequest.getMinutes(),
                            periodTimeRequest.getStartPeriod(),
                            periodTimeRequest.getEndPeriod(),
                            dayOfWeek,
                            periodTimeRequest.getExcludedDays()
                    );
                    for (SchedulerItem schedulerItem : ocuppiedApp) {
                        if (!schedulerItem.getTimePeriod().isOverlap(dateTimePeriod)) {
                            freeApp.add(dateTimePeriod);
                        }
                    }
                }
            }
        }
        return freeApp;
    }

    @Override
    public List<TimePeriod> getFreeAppointments(Scheduler scheduler, Map<String, Property> map, TimeRequest timeRequest) {
        List<TimePeriod> freeApp = new ArrayList<>();
        List<SchedulerItem> ocuppiedApp = getOccupiedAppointments(scheduler,map);
        if(timeRequest instanceof WeeklyTimePeriodRequest periodTimeRequest){
            for(
                    LocalTime date = LocalTime.MIDNIGHT;
                    date.isBefore(LocalTime.of(23,59));
                    date = date.plusMinutes(15)
            ){
                WeeklyTimePeriod dateTimePeriod = new WeeklyTimePeriod(
                        date,
                        periodTimeRequest.getMinutes(),
                        periodTimeRequest.getStartPeriod(),
                        periodTimeRequest.getEndPeriod(),
                        periodTimeRequest.getDayOfWeek(),
                        periodTimeRequest.getExcludedDays()
                );
                for(SchedulerItem schedulerItem : ocuppiedApp){
                    if(!schedulerItem.getTimePeriod().isOverlap(dateTimePeriod)){
                        freeApp.add(dateTimePeriod);
                    }
                }
            }
        }

        if(timeRequest instanceof WeeklyTimePeriodRequestWithoutWeek periodTimeRequest){
            for(
                    LocalTime date = LocalTime.MIDNIGHT;
                    date.isBefore(LocalTime.of(23,59));
                    date = date.plusMinutes(15)
            ){
                for(DayOfWeek dayOfWeek:DayOfWeek.values()) {
                    WeeklyTimePeriod dateTimePeriod = new WeeklyTimePeriod(
                            date,
                            periodTimeRequest.getMinutes(),
                            periodTimeRequest.getStartPeriod(),
                            periodTimeRequest.getEndPeriod(),
                            dayOfWeek,
                            periodTimeRequest.getExcludedDays()
                    );
                    for (SchedulerItem schedulerItem : ocuppiedApp) {
                        if (!schedulerItem.getTimePeriod().isOverlap(dateTimePeriod)) {
                            freeApp.add(dateTimePeriod);
                        }
                    }
                }
            }
        }
        return freeApp;
    }

    @Override
    public List<SchedulerItem> getOccupiedAppointments(Scheduler scheduler, Map<String, Property> map, OccupiedTimeRequest timeRequest) {
        return getOccupiedAppointments(scheduler,map).stream().filter(schedulerItem -> {
            WeeklyTimePeriod weekly = new WeeklyTimePeriod(timeRequest.getStart().toLocalTime(),
                    timeRequest.getEnd().toLocalTime(),
                    timeRequest.getStart().toLocalDate(),
                    timeRequest.getEnd().toLocalDate(),
                    null);
            return schedulerItem.getTimePeriod().isOverlap(weekly);
        }).collect(Collectors.toList());
    }
}
