/**
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;


import Services.PokerService;
import Users.Users;
import com.google.inject.Inject;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;

import Cards.Hand;
import ninja.Context;
import ninja.session.Session;

import java.util.LinkedList;
import java.util.List;


@Singleton
public class ApplicationController {

    @Inject
    private PokerService pokerService;
    @Inject
    AuthenticationController auth;
    @Inject
    Context contextGlobal;
    @Inject
    Session session;

    Hand hand;

    List<Hand> listHands = new LinkedList<Hand>();
    List<Users> currentPlayers =  new LinkedList<>();

    int count = 0;
    boolean regError = false;
    boolean loginError = false;
    boolean loggedIn = false;
    boolean logout = false;
    boolean restore = false;


    //THIS IS A TEMPORARY FUNCTION
    public Result setplayers(Context context) {

        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Dihan,Hardu,Chris,Arno,Andre";   //Get List of players from database or from ajax
        System.out.println("Setting players");
        return Results.json().render(simplePojo);

    }


    public Result getGameHistory(Context context)
    {

        SimplePojo jsonEntry = new SimplePojo();
        jsonEntry.content = "Not yet implemented.";
        return Results.json().render(jsonEntry);
    }


    public Result index(Context context) {

            Result result = Results.html();
        session = context.getSession();

        if(restore)
        {
            result = Results.html();
            result.render("name", listHands.get(1).toString() + " #" + pokerService.evaluateDeck(listHands.get(1)));
            result.render("deck1", listHands.get(1).toString() + " #" + pokerService.evaluateDeck(listHands.get(1)));
            result.render("deck2", listHands.get(2).toString() + " #" + pokerService.evaluateDeck(listHands.get(2)));
            result.render("deck3", listHands.get(3).toString() + " #" + pokerService.evaluateDeck(listHands.get(3)));
            result.render("deck4", listHands.get(4).toString() + " #" + pokerService.evaluateDeck(listHands.get(4))); //RESTORE FROM LIST RATHER THAN SINGLE HAND
            restore = false;
            return result.html();
        }

        if(!loginError) {
            session = context.getSession();

            String username = context.getParameter("username");
            String password = context.getParameter("password");

            session.put("username", username);
            session.put("password", password);

            if (auth.loginHash(username,password)) {
                System.out.println("loginHash returned success");
                loginError = false;         loggedIn = true;
                pokerService.createDeck();
              //   hand = pokerService.dealHand(); //SET MULTIPLE HANDS HERE
                for(int k = 0 ; k < 5 ; k++)
                {
                    listHands.add(pokerService.dealHand());
                }


                 result = Results.html();
                result.render("name", listHands.get(0).toString() + " #" + pokerService.evaluateDeck(listHands.get(0)));
                result.render("deck1", listHands.get(1).toString() + " #" + pokerService.evaluateDeck(listHands.get(1)));
                result.render("deck2", listHands.get(2).toString() + " #" + pokerService.evaluateDeck(listHands.get(2)));
                result.render("deck3", listHands.get(3).toString() + " #" + pokerService.evaluateDeck(listHands.get(3)));
                result.render("deck4", listHands.get(4).toString() + " #" + pokerService.evaluateDeck(listHands.get(4))); //RENDER ALL 5 DECKS. ADJUST JQUERY AND JAVASCRIPT
                return result;
            }
        }

        if(loginError) {
            loginError = false;
            loggedIn = false;

        }
                     result = Results.html();
                    result = result.redirect("/");
                    result.render("name", "blank");
                    return result.html();
    }

    public Result logon(Context context)
    {
        Result result = Results.html();

        String username = "";
        String password = "";

        if(!logout) {
            if (context != null) {

                username = context.getParameter("usernameRegister");
                password = context.getParameter("passwordRegister");
                if (username != null && password != null) {
                    session = context.getSession();

                    if (auth.registerHash(username,password) ) { //&& auth.registerDatabase(username,password)

                        regError = false;
                    } else {

                        result = result.redirect("/register");
                        regError = true;
                        return result;
                    }
                }
            }
        }

            logout = false;
            result = Results.html();
            if(!context.getSession().isEmpty())
            result.render("restoreButton" , "Click to restore previous session for " +context.getSession().get("username")+"");
            else
            result.render("restoreButton", "No session to restore");

            return result;

    }

    public Result register(Context context)
    {


        Result result = Results.html();

        if(regError)
            result.render("RegistrationStatus" ,"<div  class ='alert alert-danger container-fluid'>And error occured. Please choose a different username.</div>" );
        else
            result.render("RegistrationStatus" ,"<div class ='alert alert-success container-fluid'>Please fill in the above details</div>");

        regError = true;
        return result;
    }


    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }

    public  Result logout(Context context)
    {
        context.getSession().clear();
        logout = true;
        return Results.redirect("/");
    }

    public Result restore(Context context)
    {
        if(!context.getSession().isEmpty())
        {
            Result result = Results.redirect("/index");
            restore = true;
            return result;
        }
        else {
            return Results.redirect("/");
        }
    }
    
    public static class SimplePojo {

        public String content;
        
    }




}
