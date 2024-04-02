import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    private static final String validPattern = "(?:2[0-3]|[01][0-9]):[0-5][0-9]:[0-5][0-9]";
    public String[] patternMatcher(String line) {
        Pattern pattern = Pattern.compile(validPattern);
        Matcher matcher = pattern.matcher(line);
        String[] data = null;

        if(matcher.find()) {
            data = line.split("\\s");
        }
        return data;
    }

    public long calculateSeconds(Login start, Login end) throws ParseException {
        SimpleDateFormat simpleDateFormat
                = new SimpleDateFormat("HH:mm:ss");

        // Parsing the Time Period
        Date date1 = simpleDateFormat.parse(start.getLogTime());
        Date date2 = simpleDateFormat.parse(end.getLogTime());
        long differenceInMilliSeconds
                = Math.abs(date2.getTime() - date1.getTime());

        long differenceInHours
                = (differenceInMilliSeconds / (60 * 60 * 1000))
                % 24;

        // Calculating the difference in Minutes
        long differenceInMinutes
                = (differenceInMilliSeconds / (60 * 1000)) % 60;
        long differenceInSeconds
                = (differenceInMilliSeconds / 1000) % 60;
        return (differenceInSeconds + (differenceInMinutes * 60) + (differenceInHours * 60 * 60));
    }
}
