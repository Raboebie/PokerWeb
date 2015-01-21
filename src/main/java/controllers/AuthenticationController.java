package controllers;

import Passwords.Passwords;
import Repository.UserRepository;
import Users.Users;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.persist.Transactional;
import ninja.jpa.UnitOfWork;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    public boolean loginDatabase(String USERNAME) {

        EntityManager entityManager = entityManagerProvider.get();

        Query q = entityManager.createQuery("SELECT x FROM GuestbookEntry x.name WHERE x.name = :USERNAME");
        q.setParameter("USERNAME" , USERNAME);
        List<Users> list = (List<Users>) q.getResultList();




        return false;
    }

@Transactional
    public boolean registerDatabase(String param1, String param2)
    {
        Users users = new Users(param1,param2);
        users.setSalt("salt");
        EntityManager entity = entityManagerProvider.get();
        entity.persist((Users)users);
        return true;
    }




}
