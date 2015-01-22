package Users;

import javax.persistence.Entity;
import javax.persistence.Id;
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

    private String name;

    public String getGameName()
    {
        return GameName;
    }

    public String getName()
    {
        return name;
    }
}
