package Users;
import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.validation.constraints.Size;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

/**
 * Created by dihan on 15/01/21.
 */
@Entity
public class Game {

    @Id
    @Size (max = 12)
    private String GameName;

    private Timestamp GameDate;

    private String name;

    /*Timestamp stamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(stamp.getTime());
    System.out.println(date); */

    public Game()
    {

    }

    public void setName(String pname)
    {
            name = pname;
    }

    public String getName(){
        return name;
    }

    public Game(String gameName, Timestamp gameDate , String pname)
    {
        GameName = gameName;
        GameDate = gameDate;
        name = pname;
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
