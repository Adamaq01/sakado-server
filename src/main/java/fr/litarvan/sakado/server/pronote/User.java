/*
 *  Sakado, an app for school
 *  Copyright (C) 2017 Adrien 'Litarvan' Navratil
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
package fr.litarvan.sakado.server.pronote;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static fr.litarvan.sakado.server.util.FailableConsumer.waitFor;

public class User
{
    private static final Logger log = LogManager.getLogger("Pronote");

    private String username;
    private String token;

    private Pronote pronote;
    private Cours[] edt;

    protected User(Pronote pronote, String username, String token)
    {
        this.pronote = pronote;
        this.username = username;
        this.token = token;
    }

    static User open(Pronote pronote, String username) throws IOException
    {
        return waitFor(future -> pronote.getClient().push("open").handle(response -> {
            future.complete(new User(pronote, username, response.getResult().get("token").getAsString()));
        }));
    }

    public void update()
    {
        try
        {
            tryToUpdate();
        }
        catch (IOException e)
        {
            log.error("Couldn't query user data from Pronote, ignoring", e);
        }
    }

    public void tryToUpdate() throws IOException
    {
        Gson gson = new Gson();

        this.edt = waitFor(future -> pronote.getClient().push("edt", token).handle(r -> {
            JsonArray array = r.getResult().getAsJsonArray("cours");
            Cours[] cours = new Cours[array.size()];

            for (int i = 0; i < array.size(); i++)
            {
                cours[i] = gson.fromJson(array.get(i), Cours.class);
            }

            future.complete(cours);
        }));
    }

    public boolean isLogged()
    {
        return edt != null;
    }

    public String getUsername()
    {
        return username;
    }

    String getToken()
    {
        return token;
    }

    public Cours[] getEDT()
    {
        if (edt == null)
        {
            update();
        }

        return edt;
    }
}
