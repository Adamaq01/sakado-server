package fr.litarvan.sakado.server.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Establishment
{
    private String name;
    private FetchMethod[] methods;
    private int zone;

    private transient List<StudentClass> classes;

    public Establishment()
    {
        this.classes = new ArrayList<>();
    }

    public Establishment(String name, FetchMethod[] methods, int zone)
    {
        this();

        this.name = name;
        this.methods = methods;
        this.zone = zone;
    }

    public StudentClass classOf(User user)
    {
        for (StudentClass cl : classes)
        {
            if (cl.getMembers().contains(user.getName()) && cl.getEstablishment() == user.getEstablishment())
            {
                return cl;
            }
        }

        return null;
    }

    public StudentClass getClassByName(String name)
    {
        for (StudentClass cl : this.classes)
        {
            if (cl.getName().equalsIgnoreCase(name))
            {
                return cl;
            }
        }

        return null;
    }

    public String getName()
    {
        return name;
    }

    public FetchMethod[] getMethods()
    {
        return methods;
    }

    public int getZone()
    {
        return zone;
    }

    public List<StudentClass> getClasses()
    {
        return classes;
    }

    public static class FetchMethod
    {
    	private String name;
        private String server;
        private String url;
        private String cas;

        public FetchMethod()
        {
        }

        public FetchMethod(String name, String server, String url, String cas)
        {
            this.server = server;
            this.url = url;
            this.cas = cas;
        }

		public String getName()
		{
			return name;
		}

		public String getServer()
        {
            return server;
        }

        public String getUrl()
        {
            return url;
        }

        public String getCas()
        {
            return cas;
        }
    }
}
