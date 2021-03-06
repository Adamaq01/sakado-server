package fr.litarvan.sakado.server.refresh;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import fr.litarvan.sakado.server.data.Lesson;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.Week;
import fr.litarvan.sakado.server.push.PushService;
import fr.litarvan.sakado.server.push.PushType;
import fr.litarvan.sakado.server.util.CalendarUtils;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AwayTeacherTask extends BaseRefreshTask
{
    private static final Logger log = LogManager.getLogger("AwayTeacherTask");

    @Inject
    private PushService push;

    @Override
    public void refresh(User user)
    {
        Calendar today = CalendarUtils.create();

        List<Lesson> away = new ArrayList<>();
        for (Week week : user.getTimetable())
        {
            for (Lesson lesson : week.getContent())
            {
                if (lesson.isAway() && lesson.getFrom() > today.getTimeInMillis())
                {
                    away.add(lesson);
                }
            }
        }

        removeSeen(user, away);

        if (away.size() == 0)
        {
            return;
        }

        String title;
        String message;

        if (away.size() > 1)
        {
            title = "Plusieurs profs. absents";
            message = "Cliquer pour voir";
        }
        else
        {
            title = "Prof. absent";

            Lesson lesson = away.get(0);
            Calendar day = lesson.getFromAsCalendar();

            message = lesson.getTeacher();
            message += " : " + CalendarUtils.parse(day, Calendar.DAY_OF_WEEK, Calendar.DAY_OF_MONTH, Calendar.MONTH);
            message += " - " + CalendarUtils.parse(day, Calendar.HOUR_OF_DAY) + "h-" + CalendarUtils.parse(lesson.getToAsCalendar(), Calendar.HOUR_OF_DAY) + "h";
        }

        try
        {
            push.send(user, PushType.AWAY, title, message);
        }
        catch (Exception e)
        {
            log.error("Couldn't send away push notification", e);
        }
    }
}