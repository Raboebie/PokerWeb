package Users;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dihan on 15/01/21.
 */
@Entity
public class Game {

    @Id
    @Size (max = 20)
    private String GameName;

    @OneToMany(mappedBy = "game")
    private List<PlayerGames> players = new ArrayList<>();

    private Timestamp GameDate;


    /*Timestamp stamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(stamp.getTime());
    System.out.println(date); */

    public void addPlayer(Players player)
    {
        PlayerGames playerGames = new PlayerGames();
        playerGames.setGameName(this.getGameName());
        playerGames.setUsername(player.getName());
        playerGames.setHand(player.getHand());
        playerGames.setGame(this);
        playerGames.setPlayer(player);
        this.players.add(playerGames);
    }

    public Game()
    {

    }




    public Game(String gameName, Timestamp gameDate)
    {
        GameName = gameName;
        GameDate = gameDate;
    }

    public String  getGameName()
    {
            return GameName;
    }

    public Timestamp getDate()
    {
        return GameDate;
    }

    public void setGameName(String name)
    {
        GameName = name;
    }

    public void setGameDate(Timestamp stamp)
    {
        GameDate = stamp;
    }




}
