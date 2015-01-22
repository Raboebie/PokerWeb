package Users;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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
    @OneToMany( mappedBy = "player",fetch= FetchType.EAGER)
    private List<PlayerGames> games = new ArrayList<>();

    //getters and setters

    public Players()
    {

    }

    public void addGame(Game game)
    {
        PlayerGames playerGames = new PlayerGames();
        playerGames.setUsername(getName());
        playerGames.setGameName(game.getGameName());
        playerGames.setPlayer(this);
        playerGames.setGame(game);
        this.games.add(playerGames);
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

    public String getHand()
    {
        return this.hand;
    }

    public String toString()
    {
        return name + " " + password;
    }

    public void addHand(String string)
    {
        hand = string;
    }



    //public class BaseRepo (T)

    //@Inject private Provider<EntityManager> entityManagerProvider


    //@Transactional

    //public void persist(T entity)
        //getEntityManager.persist(entity)
}
