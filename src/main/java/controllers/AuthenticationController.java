package controllers;

import Passwords.Passwords;
import Repository.UserRepository;
import Users.Game;
import Users.UserGame;
import Users.User;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import Users.User;


/**
 * Created by dihan on 15/01/16.
 */
@Singleton
public class AuthenticationController  {

    List<String> username = new LinkedList<String>();
    List<String> password = new LinkedList<String>();


    @Inject
    UserRepository userRepository;

    @Inject
    Provider<EntityManager> entityManagerProvider;


    public boolean Login(String usernameP , String passwordP)
    {
        int count = 0;
        for(String string : username)
        {
            if(string.equals(usernameP) && password.get(count).equals(passwordP))
            {
                return true;
            }
            count++;
        }
        return false;
    }
    public boolean Register(String Username, String Password)
    {

        if(username.contains(Username))
            return false;
        else {
            username.add(Username);
            password.add(Password);
            return true;
        }
    }



    public boolean loginHash(String usernameP, String plaintextPassword)
    {
        try {
            try {
                Passwords passwords = new Passwords();
                int count = 0;
                for(String string : username)
                {
                    if(string.equals(usernameP) && passwords.validatePassword(plaintextPassword, password.get(count)))
                    {
                        return true;
                    }
                    count++;
                }
                return false;
            } catch (InvalidKeySpecException e) {
            }
        }
        catch(NoSuchAlgorithmException e1){}
        return false;
    }

    //

    public boolean registerHash(String usernameParam, String plaintextPassword)
    {
        String hash = "";
        try {
            Passwords passGenerator = new Passwords();
           try{  hash = passGenerator.createHash(plaintextPassword);} catch(InvalidKeySpecException e2){}

            if (username.contains(usernameParam))
                return false;
            else {
                username.add(usernameParam);
                password.add(hash);
                return true;
            }
        }
        catch(NoSuchAlgorithmException e)
        {}


return false;

    }


    @UnitOfWork
    public boolean loginDatabase(String USERNAME, String PASSWORD) {

        Passwords p = new Passwords();

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :USERNAME");
        q.setParameter("USERNAME" , USERNAME);
        List<User> list = (List<User>) q.getResultList();

        if(list.isEmpty())
            return false;
        else {
            String password = list.get(0).getPassword();

            try {
                try {
                    if (p.validatePassword(PASSWORD, password))
                    {
                        return true;
                    }
                    else
                        return false;
                } catch (NoSuchAlgorithmException e) {
                }
            }
            catch(InvalidKeySpecException e){}
        }


        return false;
    }

@Transactional
    public boolean registerDatabase(String USERNAME, String PASSWORD) {
    EntityManager entityManager = entityManagerProvider.get();

    Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :USERNAME");
    q.setParameter("USERNAME", USERNAME);
    List<Users.User> list = (List<Users.User>) q.getResultList();


            if (list.isEmpty()) {
                try {
                    try {
                        Passwords p = new Passwords();
                        String hashedPassword = p.createHash(PASSWORD);
                        Users.User users = new Users.User(USERNAME, hashedPassword);

                        EntityManager entity = entityManagerProvider.get();
                        //entity.persist((Game) game);
                        entity.persist((Users.User) users);
                        return true;
                    } catch (NoSuchAlgorithmException e) {
                    }
                } catch (InvalidKeySpecException e) {
                }
                return false;
            }


    return false;
    }


    @Transactional
    public boolean createPlayerGames(UserGame userGame)
    {
        EntityManager entityManager = entityManagerProvider.get();
        entityManager.persist((UserGame) userGame);
        return true;
    }

    @Transactional
    public boolean createGame(Game game)
    {
        EntityManager entityManager = entityManagerProvider.get();
        game.setActive(true);
        try {
            game.setHost(Inet4Address.getLocalHost().getHostAddress().toString());
        }catch(UnknownHostException e){}
        entityManager.persist((Game) game);
        return true;
    }

    @UnitOfWork
    public Users.User getPlayer(String pname)
    {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :NAME");
        q.setParameter("NAME" , pname);
        List<Users.User> list = (List<Users.User>) q.getResultList();
        if(list.isEmpty())
        {
            System.out.println("LEKKER NULLK");
            return null;
        }
        else
        {
            System.out.println("NOT NULL, found player" + pname);
            return list.get(0);
        }

    }


@UnitOfWork
    public boolean checkGame(String name , Timestamp timestamp)
    {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT u FROM Game u WHERE u.gameName = :GAMENAME");
        q.setParameter("GAMENAME" , name);
        List<Game> list = (List<Game>) q.getResultList();

        if(list.isEmpty())
        {
            /*Game game = new Game(name , timestamp,name);
            entityManager.persist(game);*/
            return true;
        }

        else
        {
            return false;
        }
    }

    @UnitOfWork
    public List<UserGame> getHistory()
    {
        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT g FROM UserGame g");
        List results = q.getResultList();


        List<UserGame> list = (List<UserGame>) q.getResultList();

        return list;
    }

    @UnitOfWork
    public List<Game> getCurrentActiveGames()
    {
        EntityManager entityManager = entityManagerProvider.get();
        Query q = entityManager.createQuery("SELECT g FROM Game g WHERE g.active = 'TRUE'");
        List results = q.getResultList();
        List<Game> list = (List<Game>) q.getResultList();

        return list;
    }
}
