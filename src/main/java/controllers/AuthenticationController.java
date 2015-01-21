package controllers;

import Passwords.Passwords;
import Repository.UserRepository;
import Users.Game;
import Users.Players;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;


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

        Query q = entityManager.createQuery("SELECT u FROM Players u WHERE u.name = :USERNAME");
        q.setParameter("USERNAME" , USERNAME);
        List<Players> list = (List<Players>) q.getResultList();

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

    Query q = entityManager.createQuery("SELECT u FROM Players u WHERE u.name = :USERNAME");
    q.setParameter("USERNAME", USERNAME);
    List<Players> list = (List<Players>) q.getResultList();


            if (list.isEmpty()) {
                try {
                    try {
                        Passwords p = new Passwords();
                        String hashedPassword = p.createHash(PASSWORD);
                        Players users = new Players(USERNAME, hashedPassword);
                        Game game = new Game("test" , new Timestamp(5));
                        users.setSalt("salt");
                        EntityManager entity = entityManagerProvider.get();
                        //entity.persist((Game) game);
                        entity.persist((Players) users);
                        return true;
                    } catch (NoSuchAlgorithmException e) {
                    }
                } catch (InvalidKeySpecException e) {
                }
                return false;
            }


    return false;
    }
}
