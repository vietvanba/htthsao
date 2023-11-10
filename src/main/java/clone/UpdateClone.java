package clone;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Death God
 */
public class UpdateClone implements Runnable {

    private static UpdateClone instance;

    public static UpdateClone gI() {
        if (instance == null) {
            instance = new UpdateClone();
        }
        return instance;
    }

    public List<ClonePlayer> players = new ArrayList<>();

    @Override
    public void run() {
        try {
            while (true) {
                for(int i = 0 ; i< players.size();i++){
                    ClonePlayer pl = players.get(i);
                    if(pl != null){
                        pl.update();
                    }
                }
                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException e) {
        }
    }
}
