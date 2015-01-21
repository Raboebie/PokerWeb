package Users;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;

/**
 *
 * Created by dihan on 15/01/20.
 */
@Entity
public class Users {

    @Id
    @Size(max =12)
    private String name;

    private String password;
    private String salt;


    //getters and setters

    public Users(String username, String UserPassword)
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
        return salt;
    }

    public void setName(String a){
        name = a;
    }

    public void setPassword(String p)
    {
        password = p;
    }

    public void setSalt(String s)
    {
        salt = s;
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
