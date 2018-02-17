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
package fr.litarvan.sakado.server.http;

import fr.litarvan.sakado.server.http.controller.*;

import javax.inject.Inject;

import static spark.Spark.*;

public final class Routes
{
    @Inject
    private AuthController auth;

    @Inject
    private MainController main;

    @Inject
    private GraphQLController graphql;

    public void load()
    {
        get("/graphql", graphql::graphql);

        path("/auth", () -> {
            get("/login", auth::login);
            get("/validate", auth::validate);
            get("/logout", auth::logout);
        });

        get("/away", main::away);
        get("/links", main::links);
        get("/next", main::nextCours);
        get("/notes", main::notes);
        get("/version", main::version);
    }
}
