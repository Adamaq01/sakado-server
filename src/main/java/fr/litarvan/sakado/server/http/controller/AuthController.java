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
package fr.litarvan.sakado.server.http.controller;

import com.google.gson.JsonObject;
import fr.litarvan.sakado.server.data.UserManager;
import fr.litarvan.sakado.server.http.Controller;
import fr.litarvan.sakado.server.http.error.APIError;
import fr.litarvan.sakado.server.data.LoginException;
import fr.litarvan.sakado.server.data.User;
import fr.litarvan.sakado.server.data.network.RequestException;
import spark.Request;
import spark.Response;

import javax.inject.Inject;
import java.io.IOException;

public class AuthController extends Controller
{
    @Inject
    private UserManager userManager;

    public Object login(Request request, Response response) throws APIError, IOException, RequestException
    {
        String username = require(request, "username");
        String password = require(request, "password");
        String deviceToken = require(request, "deviceToken");

        String establishment = require(request, "establishment");
        String method = require(request, "method");

        User user = userManager.login(establishment, method, username, password, deviceToken);

        return json(apply(user), response);
    }

    public Object validate(Request request, Response response) throws APIError
    {
        User user = this.requireLogged(request);
        user.setLastLogin(System.currentTimeMillis());

        return json(apply(user), response);
    }

    public Object logout(Request request, Response response) throws APIError
    {
        this.userManager.remove(requireLogged(request));
        return success(response);
    }

    public Object fetch(Request request, Response response) throws APIError, IOException
    {
        User user = this.requireLogged(request);

        try
        {
            user = userManager.update(user);
        }
        catch (RequestException e)
        {
            throw new APIError(APIError.INVALID_CREDENTIALS, e.getMessage());
        }

        return json(apply(user), response);
    }

    protected JsonObject apply(User user)
    {
        JsonObject rep = new JsonObject();
        rep.addProperty("success", "true");
        rep.addProperty("token", user.getToken());

        return rep;
    }
}
