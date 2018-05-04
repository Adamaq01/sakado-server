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
package fr.litarvan.sakado.server.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Establishment
{
    private String name;
    private String method;
    private String link;
    private String cas;

    private List<StudentClass> classes;

    public Establishment(String name, String method, String link)
    {
        this(name, method, link, null);
    }

    public Establishment(String name, String method, String link, String cas)
    {
        this.name = name;
        this.method = method;
        this.link = link;
        this.cas = cas;
        this.classes = new ArrayList<>();
    }

    public void setClasses(StudentClass[] classes)
    {
        this.classes.addAll(Arrays.asList(classes));
    }

    public StudentClass getClass(String name)
    {
        for (StudentClass studentClass : classes)
        {
            if (Objects.equals(studentClass.getName(), name))
            {
                return studentClass;
            }
        }

        return null;
    }

    public void addClass(StudentClass studentClass)
    {
        this.classes.add(studentClass);
    }

    public User getUser(String username)
    {
        for (StudentClass studentClass : classes)
        {
            User user = studentClass.getUser(username);

            if (user != null)
            {
                return user;
            }
        }

        return null;
    }

    public String getName()
    {
        return name;
    }

    public String getMethod()
    {
        return method;
    }

    public String getLink()
    {
        return link;
    }

    public String getCas()
    {
        return cas;
    }
}
