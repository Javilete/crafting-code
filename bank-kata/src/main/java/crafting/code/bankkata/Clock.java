package crafting.code.bankkata;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Clock {

    private static final String DATE_PATTERN = "dd\\MM\\yyyy";

    public String now() {
       return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_PATTERN));
    }
}
