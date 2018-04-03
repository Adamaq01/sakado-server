/*
 *  Sakado, an app for school
 *  Copyright (c) 2017-2018 Adrien 'Litarvan' Navratil
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package fr.litarvan.sakado.server.data;

import fr.litarvan.sakado.server.util.CalendarUtils;

import java.util.Calendar;

public class Mark implements Identifiable
{
    private String subject;
    private String title;
    private int value;
    private int max;
    private long time;
    private int period;

    public Mark()
    {
    }

    public Mark(String subject, String title, int value, int max, long time, int period)
    {
        this.subject = subject;
        this.title = title;
        this.value = value;
        this.max = max;
        this.time = time;
        this.period = period;
    }

    public String getSubject()
    {
        return subject;
    }

    public String getTitle()
    {
        return title;
    }

    public int getValue()
    {
        return value;
    }

    public int getMax()
    {
        return max;
    }

    public int getPeriod()
    {
        return period;
    }

    public long getTime()
    {
        return time;
    }

    public Calendar getTimeAsCalendar()
    {
        return CalendarUtils.fromTimestamp(time);
    }

    @Override
    public String getId()
    {
        return "M" + getTime() + getSubject().substring(0, 2) + getValue();
    }
}
