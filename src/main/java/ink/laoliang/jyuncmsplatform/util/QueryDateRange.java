package ink.laoliang.jyuncmsplatform.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class QueryDateRange {

    /**
     * 根据用户选择的时间范围字段（格式：YYYY-MM），返回对应的起止时间点.
     * 如果传入的字符串为空，则返回一个超大的范围（2000-01-01 ~ 2099-12-31）
     *
     * @param selectedDateRange
     * @return {
     * "startDate" : startDate,
     * "endDate" : endDate
     * }
     */
    public static Map<String, Date> handle(String selectedDateRange) {
        DateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2000, Calendar.JANUARY, 1); // 2000-01-01
        Date startDate = calendar.getTime();
        calendar.set(2099, Calendar.DECEMBER, 31); // 2099-12-31
        Date endDate = calendar.getTime();

        if (selectedDateRange != null && !selectedDateRange.equals("") && !selectedDateRange.equals("null")) {
            try {
                startDate = format.parse(selectedDateRange);
                calendar.setTime(startDate);
                calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
                calendar.add(Calendar.DATE, 1);
                endDate = calendar.getTime();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Map<String, Date> result = new HashMap<>();
        result.put("startDate", startDate);
        result.put("endDate", endDate);

        return result;
    }
}
