package requests;


import lombok.Getter;
import lombok.Setter;
import services.requests.TimeRequest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// kad dodajemo u schedule ovakav timeperiod onda treba ako imamo izuzete dane da nam dodaje podeljeno na 2 dela

@Getter
@Setter
public class WeeklyTimePeriodRequestWithoutWeek extends TimeRequest {
    private LocalDate startPeriod;
    private LocalDate endPeriod;
    private List<LocalDate> excludedDays;

    public WeeklyTimePeriodRequestWithoutWeek(int minutes, LocalDate startPeriod, LocalDate endPeriod, DayOfWeek dayOfWeek, List<LocalDate> excludedDays) {
        super(minutes);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.excludedDays = excludedDays;
    }

    public WeeklyTimePeriodRequestWithoutWeek(int minutes, LocalDate startPeriod, LocalDate endPeriod, DayOfWeek dayOfWeek) {
        super(minutes);
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        excludedDays = new ArrayList<>();
    }
}
