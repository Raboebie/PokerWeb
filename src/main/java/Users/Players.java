package Users;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 *
 * Created by dihan on 15/01/20.
 */
@Entity
public class Players {

    @Id
    @Size(max =12)
    private String name;

    private String password;
    private String hand;


    //getters and setters

    public Players()
    {

    }

    public Players(String username, String UserPassword)
    {
        name = username;
        password = UserPassword;
    }


    public String getName(){
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public String getSalt()
    {
        return hand;
    }

    public void setName(String a){
        name = a;
    }

    public void setPassword(String p)
    {
        password = p;
    }

    public void setHand(String s)
    {
        hand = s;
    }

    public String toString()
    {
        return name + " " + password;
    }





    //public class BaseRepo (T)

    //@Inject private Provider<EntityManager> entityManagerProvider


    //@Transactional

    //public void persist(T entity)
        //getEntityManager.persist(entity)
}
