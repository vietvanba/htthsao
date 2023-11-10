package core;

import client.Player;
import database.SQL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Death God
 */
public class UpdateTopWanted implements Runnable {

    private static UpdateTopWanted instance;

    public static UpdateTopWanted gI() {
        if (instance == null) {
            instance = new UpdateTopWanted();
        }
        return instance;
    }

    public class topWanted {

        public String name;
        public int point;
        
        public topWanted(String a , int b){
            name = a;
            point = b;
        }
    }

    public List<topWanted> player = new ArrayList<>();

    public String findPlayer(int pointWanted){
        List<String> list = new ArrayList<>();
        int indexUpperBound = Collections.binarySearch(player, pointWanted - 20, Collections.reverseOrder());
        if (indexUpperBound < 0) {
            indexUpperBound = -(indexUpperBound + 1);
        }
        int indexLowerBound = Collections.binarySearch(player, pointWanted + 20, Collections.reverseOrder());
        if (indexLowerBound < 0) {
            indexLowerBound = -(indexLowerBound + 1);
        }
        for (int i = 0; i < indexUpperBound; i++) {
            list.add(player.get(i).name);
        }
        for (int i = indexLowerBound; i < player.size(); i++) {
            list.add(player.get(i).name);
        }
        return list.get(Util.random(0, list.size() - 1));
    }
    
    @Override
    public void run() {
        try {
            while (true) {
                try ( Connection conn = SQL.gI().getCon();  Statement ps = conn.createStatement();  ResultSet rs = ps.executeQuery("SELECT JSON_EXTRACT(`site`, '$[4]') AS point,name FROM `players` ORDER BY `point` DESC")) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int point = rs.getInt("point");
                        if(point != 0){
                            topWanted top = player.stream().filter(t -> t != null && t.name.equals(name)).findFirst().orElse(null);
                            if(top == null){
                                player.add(new topWanted(name,point));
                            }else{
                                top.point = point;
                            }
                        }
                    }
                }
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        } catch (InterruptedException | SQLException e) {
        }
    }
}
