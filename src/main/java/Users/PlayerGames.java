package Users;

import Cards.Hand;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

/**
 * Created by dihan on 15/01/22.
 */
@Entity
public class PlayerGames {
    @Id
    @Size(max = 12)
    private String GameName;

    @ManyToOne
    @JoinColumn(name = "name", referencedColumnName = "name", insertable = false, updatable = false)
    private Players player;

    @ManyToOne
    @JoinColumn(name = "GameName" , referencedColumnName =  "GameName" , insertable = false, updatable = false)
    private Game game;

    private String name;
    private String hand;

    public String getGameName()
    {
        return GameName;
    }

    public String getName()
    {
        return name;
    }

    public void setGameName(String gn)
    {
        GameName = gn;
    }

    public void setUsername(String un)
    {
            name = un;
    }

    public void setPlayer(Players player)
    {
        this.player = player;
    }

    public void setGame(Game game)
    {
        this.game = game;
    }

    public void setHand(String hand)
    {
        this.hand = hand;
    }

    public String getHand()
    {
        return hand;
    }


}
