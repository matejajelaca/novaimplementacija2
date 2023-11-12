
import data.TimePeriod;
import data.importexport.Configuration;
import org.json.JSONObject;
import services.importexport.JsonParser;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JasonParser extends JsonParser {
    @Override
    public TimePeriod parseTimePeriod(Configuration configuration, Object o) {
        TimePeriodConfig timePeriodConfig = (TimePeriodConfig) configuration.getTimePeriodConfig();
        JSONObject jsonObject = (JSONObject) o;
        List<LocalDate> excludedDays = new ArrayList<>();
        String[] dates = jsonObject.getString(timePeriodConfig.getExcludedDays().getColName()).split(",");
        Arrays.stream(dates).forEach(date->{
            excludedDays.add(LocalDate.parse(date));
        });

        return new WeeklyTimePeriod(
                LocalTime.parse(jsonObject.getString(timePeriodConfig.getStart().getColName())),
                LocalTime.parse(jsonObject.getString(timePeriodConfig.getEnd().getColName())),
                LocalDate.parse(jsonObject.getString(timePeriodConfig.getStartPeriod().getColName())),
                LocalDate.parse(jsonObject.getString(timePeriodConfig.getEndPeriod().getColName())),
                DayOfWeek.valueOf(jsonObject.getString(timePeriodConfig.getStartPeriod().getColName())),
                excludedDays
                );
    }


}
