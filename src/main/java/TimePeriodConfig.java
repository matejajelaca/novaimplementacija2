import data.importexport.ConfigurationItem;
import data.importexport.ConfigurationTimePeriod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TimePeriodConfig extends ConfigurationTimePeriod {
    private ConfigurationItem start;
    private ConfigurationItem end;
    private ConfigurationItem startPeriod;
    private ConfigurationItem endPeriod;
    private ConfigurationItem dayOfWeek;
    private ConfigurationItem excludedDays;
}
