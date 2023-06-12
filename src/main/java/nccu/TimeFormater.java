package nccu;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class TimeFormater {
    public static ArrayList<String> getTimeTag(String time) {
        String day;
        String[] session;
        ArrayList<String> times = new ArrayList<>();

        day = time.substring(0, 1);
        times.add(day);

        session = time.substring(1).split("-");
        IntStream.range(Integer.parseInt(session[0]), Integer.parseInt(session[1]))
                .forEach(n -> times.add(String.valueOf(n)));

        System.out.println(times.toString());

        return times;
    }

    public static int getDay(String courseTime) {
        String courseDay = courseTime.substring(0, 1);

        switch (courseDay) {
            case "一":
                return 1;
            case "二":
                return 2;
            case "三":
                return 3;
            case "四":
                return 4;
            case "五":
                return 5;

            default:
                return 0;
        }
    }

    public static ArrayList<Integer> getSession(String courseTime) {
        int start, end;
        ArrayList<Integer> session = new ArrayList<>();

        start = Integer.parseInt(courseTime.substring(1, 3));
        end = Integer.parseInt(courseTime.substring(4, 6));

        for (int i = start; i < end; i++) {
            session.add(i);
        }

        return session;
    }
}
