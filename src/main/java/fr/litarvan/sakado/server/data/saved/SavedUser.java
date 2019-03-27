package fr.litarvan.sakado.server.data.saved;

import fr.litarvan.sakado.server.data.Reminder;

public class SavedUser
{
    private String token;
    private String establishment;
    private String method;
    private String username;
    private String password;
    private String deviceToken;
    private Reminder[] reminders;
    private long lastLogin;

    public SavedUser(String token, String establishment, String method, String username, String password, String deviceToken, Reminder[] reminders, long lastLogin)
    {
        this.token = token;
        this.establishment = establishment;
        this.method = method;
        this.username = username;
        this.password = password;
        this.deviceToken = deviceToken;
        this.reminders = reminders;
        this.lastLogin = lastLogin;
    }

    public String getToken()
    {
        return token;
    }

    public String getEstablishment()
    {
        return establishment;
    }

	public String getMethod()
	{
		return method;
	}

	public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDeviceToken()
    {
        return deviceToken;
    }

    public Reminder[] getReminders()
    {
        return reminders;
    }

    public long getLastLogin()
    {
        return lastLogin;
    }
}
