

package controllers;


import Services.PokerService;
import Users.Game;
import Users.UserGame;
import Users.User;
import com.google.inject.Inject;
import ninja.Result;
import ninja.Results;

import com.google.inject.Singleton;

import Cards.Hand;
import ninja.Context;
import ninja.session.Session;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.sql.Timestamp;
import java.util.Calendar;
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

    protected String currentUser;


    Hand hand;

    List<Hand> listHands = new LinkedList<>();
    List<String> lobbies = new LinkedList<>();
    //List<User.Users> currentPlayers =  new LinkedList<>();

    int count = 0;
    boolean regError = false;
    boolean loginError = false;
    boolean loggedIn = false;
    boolean logout = false;
    boolean restore = false;

    static int PLAYERS;





   public Result hostGame(Context context)
   {

        return Results.html();
   }

    public Result setplayers(Context context) {

        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Dihan,Hardu,Chris,Arno,Andre";   //Get List of players from database or from ajax
        System.out.println("Setting players");
        return Results.json().render(simplePojo);

    }

    public Result checkPlayer(Context context)
    {
        SimplePojo json = new SimplePojo();

        Users.User player = auth.getPlayer(context.getParameter("player2"));

            if(player != null)
            {

                json.content += context.getParameter("player2") + ",";
            }

        player = auth.getPlayer(context.getParameter("player3"));

        if(player != null)
        {

            json.content += context.getParameter("player3") + ",";
        }

        player = auth.getPlayer(context.getParameter("player4"));

        if(player != null)
        {


            json.content += context.getParameter("player4") + ",";
        }
        player = auth.getPlayer(context.getParameter("player5"));

        if(player != null)
        {

            json.content += context.getParameter("player5");

        }

        return Results.json().render(json);

    }

    public Result createGame(Context context)
    {
        SimplePojo simplePojo = new SimplePojo();
         //Get List of players from database or from ajax

        String gameName = context.getParameter("gameName");
        String players = context.getParameter("Players");
        String hands = context.getParameter("hands");
        JSONObject jsonObject = null;
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        try {
             jsonObject = (JSONObject) jsonParser.parse(players);
        }catch(ParseException e){}

        String[] playerNames = new String[5];

        for(Integer k= 0 ;k < jsonObject.size() ; k++)
        {
            playerNames[k] =  (String) jsonObject.get(k.toString());
        }

        try {
            jsonObject = (JSONObject) jsonParser.parse(hands);
        }catch(ParseException e){}

        String[] playerHands = new String[5];

        for(Integer k= 0 ; k < playerNames.length; k++) {
            playerHands[k] =  (String) jsonObject.get(k.toString());
        }

        Game game = new Game();
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());



        game.setGameDate(currentTimestamp);
        game.setGameName(gameName);
        game.setHost(context.getParameter("username"));

        if(auth.createGame(game))
        {
                //For each user in the game add the gameName, playerName and hand to PlayerGames

                for(int k = 0 ; k < playerNames.length ; k++)
                {
                    UserGame userGame = new UserGame();
                    userGame.setUsername(playerNames[k]);
                    userGame.setGameName(gameName);
                    userGame.setHand(playerHands[k]);
                    Users.User player = auth.getPlayer(playerNames[k]);

                    if(player == null)
                    {
                        SimplePojo json = new SimplePojo();
                        json.content = "FALSE";
                        return Results.json().render(json);
                    }

                   /* player.addHand(playerHands[k]);
//                    player.addGame(game);
                    userGame.setUsername(player.getName());
                    userGame.setHand(player.getHand());
                    game.addPlayer(player);
                   // playerGames.setGame(game);*/


                    if(auth.createPlayerGames(userGame))
                    {
                        System.out.println("Successfully created your game.");
                    }
                    else
                        System.out.println("An error occured while trying to create the game.");

                }
        }
        else
            System.out.println("Failed to create game.");

        SimplePojo json = new SimplePojo();
        json.content = "TRUE";
        return Results.json().render(json);
    }

    public Result setGameName(Context context)
    {
        String gameName = context.getParameter("page");
        //System.out.println(new Timestamp(8));
        Game game = new Game(gameName,new Timestamp(8));
        boolean test = auth.checkGame(gameName, new Timestamp(8));
        SimplePojo jsonEntry = new SimplePojo();
        if(test)
        jsonEntry.content = "TRUE";
        else
        jsonEntry.content = "FALSE";
        return Results.json().render(jsonEntry);
    }


    public Result createLobby(Context context)
    {
        return  Results.html();
    }

    public Result getLobbyGames(Context context)
    {
        SimplePojo json = new SimplePojo();
        json.content = "";
        List<Game> games = auth.getCurrentActiveGames();

        for(int k = 0 ; k < games.size() ; k++)
        {
            json.content += "<li>"+games.get(k).getGameName()+"</li> <button class = 'btn btn-default' onclick = 'joinGame("+k+")'>Join Game</button>";
        }

        return Results.json().render(json);
    }

    public Result getGameHistory(Context context)
    {

        String html = "<html><title>Game history</title><head></head><body>";               //Remember to close body
        List<UserGame> playerGames = auth.getHistory();
        String currentUsername = "";
        String tempName = "";
        if(playerGames.isEmpty()) {
            // tempName = playerGames.get(0).getName();
            Result result = Results.html();
            result.render("tableInfo" , "No games to display.");
            return result;
        }
        //return Results.html();
        html += "<h1 class = ''>" + playerGames.get(0).getUsername() + "</h1><table class = 'table table-striped table-bordered table-hover'>";
        html  += "<th>GameName</th>" + "<th>Hand</th>" + "<th>Player Name</th>";

        for(int k = 0 ; k < playerGames.size() ; k++)
        {
            System.out.println(playerGames.get(k).getHand());
            currentUsername = playerGames.get(k).getUsername();
            if(k % 3 == 0 && k != playerGames.size()-1)
                html += "<th>GameName</th>" + "<th>Hand</th>" + "<th>Player Name</th>";
            html+=
                    "<tr>" +
                        "<td>" +playerGames.get(k).getGameName()
                        +"</td>" +
                        "<td id = 'historyDeck"+k+"'>" + playerGames.get(k).getHand()
                        +"</td> " +
                        "<td>" + playerGames.get(k).getUsername()
                        +"</td>" +
                    "</tr>" ;

            tempName = currentUsername;
        }
        html = html + "</table></body></html>";

        Result result =  Results.html();
        result.render("tableInfo",html);
        return result;
    }


    public Result index(Context context) {

            Result result = Results.html();
        session = context.getSession();

        if(restore)
        {
            result = Results.html();
            result.render("name", listHands.get(0).toString() + " #" + pokerService.evaluateDeck(listHands.get(0)));
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

               if(auth.loginDatabase(username,password))       {
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
                result.render("deck4", listHands.get(4).toString() + " #" + pokerService.evaluateDeck(listHands.get(4)));
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

    public Result getLoggedInPlayerName(Context context)
    {
        SimplePojo jsonEntry = new SimplePojo();
        jsonEntry.content = context.getSession().get("username");
        System.out.println("Getting logged in player name...");
        return Results.json().render(jsonEntry);
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
                currentUser = username;
                if (username != null && password != null) {
                    session = context.getSession();

                    if (auth.registerDatabase(username, password)) { //&& auth.registerDatabase(username,password)

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
