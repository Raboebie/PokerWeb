package Users;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Created by dihan on 15/01/21.
 */
@Entity
public class Game {

    @Id
    @Size (max = 12)
    private String GameName;

    private Timestamp GameDate;


    /*Timestamp stamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(stamp.getTime());
    System.out.println(date); */

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
