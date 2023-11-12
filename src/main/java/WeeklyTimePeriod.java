import data.TimePeriod;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class WeeklyTimePeriod extends TimePeriod {

    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private DayOfWeek dayOfWeek;
    private List<LocalDate> excludedDays;

    public WeeklyTimePeriod(
            LocalTime start,
            long minute,
            LocalDate startPeriod,
            LocalDate endPeriod,
            DayOfWeek dayOfWeek,
            List<LocalDate> excludedDays
    ) {
        super(start, minute);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = excludedDays;
    }

    public WeeklyTimePeriod(
            LocalTime start,
            LocalTime end,
            LocalDate startPeriod,
            LocalDate endPeriod,
            DayOfWeek dayOfWeek,
            List<LocalDate> excludedDays
    ) {
        super(start, end);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = excludedDays;
    }

    public WeeklyTimePeriod(
            LocalTime start,
            long minute,
            LocalDate startPeriod,
            LocalDate endPeriod,
            DayOfWeek dayOfWeek
    ) {
        super(start, minute);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = new ArrayList<>();
    }

    public WeeklyTimePeriod(
            LocalTime start,
            LocalTime end,
            LocalDate startPeriod,
            LocalDate endPeriod,
            DayOfWeek dayOfWeek
    ) {
        super(start, end);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = new ArrayList<>();
    }

    public WeeklyTimePeriod(
            LocalTime start,
            LocalTime end,
            LocalDate date,
            DayOfWeek dayOfWeek
    ) {
        super(start, end);
        this.startPeriod = date;
        this.endPeriod = date;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = new ArrayList<>();
    }

    public WeeklyTimePeriod(
            LocalTime start,
            LocalTime end,
            LocalDate date,
            DayOfWeek dayOfWeek,
            List<LocalDate> excludedDays
    ) {
        super(start, end);
        this.startPeriod = date;
        this.endPeriod = date;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = excludedDays;
    }

    public WeeklyTimePeriod(
            LocalTime start,
            long minute,
            LocalDate date,
            DayOfWeek dayOfWeek
    ) {
        super(start, minute);
        this.startPeriod = date;
        this.endPeriod = date;
        this.dayOfWeek = dayOfWeek;
        this.excludedDays = new ArrayList<>();
    }

    @Override
    public boolean isOverlap(TimePeriod timePeriod) {
        if(!(timePeriod instanceof WeeklyTimePeriod weeklyTimePeriod)
                || (weeklyTimePeriod.dayOfWeek!=null && !weeklyTimePeriod.dayOfWeek.equals(dayOfWeek))
                || !(startPeriod.isBefore(weeklyTimePeriod.endPeriod) && weeklyTimePeriod.startPeriod.isBefore(endPeriod))
        )
            return false;
        for(LocalDate localDate = startPeriod.with(TemporalAdjusters.nextOrSame(dayOfWeek));
            localDate.isBefore(endPeriod);
            localDate = localDate.with(TemporalAdjusters.next(dayOfWeek))
        ){
            if(!excludedDays.contains(localDate)){
                LocalDateTime start1 = LocalDateTime.of(localDate,getStart());
                LocalDateTime end1 = LocalDateTime.of(localDate,getEnd());
                for(LocalDate weeklyDate = weeklyTimePeriod.startPeriod.with(TemporalAdjusters.nextOrSame(dayOfWeek));
                    weeklyDate.isBefore(weeklyTimePeriod.endPeriod);
                    weeklyDate = weeklyDate.with(TemporalAdjusters.next(dayOfWeek))
                ){
                    if(!weeklyTimePeriod.excludedDays.contains(weeklyDate)){
                        LocalDateTime start2 =LocalDateTime.of(weeklyDate,weeklyTimePeriod.getStart());
                        LocalDateTime end2 = LocalDateTime.of(weeklyDate,weeklyTimePeriod.getEnd());
                        if(start1.isBefore(end2) && start2.isBefore(end1))
                            return true;
                    }
                }
            }
        }
        return false;
    }
}
