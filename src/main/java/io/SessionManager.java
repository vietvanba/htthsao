package io;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import activities.Trade;
import client.Player;
import core.Log;
import core.Util;
import ReCode_Sever.myAdmin;

public class SessionManager {

    public static final List<Session> CLIENT_ENTRYS = new LinkedList<>();
    public static final List<Player> PLAYER_ENTRYS = new LinkedList<>();
    public static HashMap<String, Long> time_login = new HashMap<>();

    public synchronized static void client_connect(Session ss) {
        int count = 0;
        for (Session ssCheck : CLIENT_ENTRYS) {
            if (ssCheck.ip.equals(ss.getIp())) {
                count++;
            }
        }
        if(count > 1){
            System.out.println("ban ip " + ss.getIp() + " - online : " + SessionManager.CLIENT_ENTRYS.size() +  " count: " + count);
            return;
        }
        ss.init();
        SessionManager.CLIENT_ENTRYS.add(ss);
        System.out.println("accecpt ip " + ss.ip + " - online : " + SessionManager.CLIENT_ENTRYS.size() +  " count: " + count);
    }
    
    public synchronized  static void addPlayer(Player pl){
        if(!PLAYER_ENTRYS.contains(pl)){
            PLAYER_ENTRYS.add(pl);
        }
    }
    
    public static void saveAllData(){
        for(int i = 0 ; i < PLAYER_ENTRYS.size();i++){
            Player pl = PLAYER_ENTRYS.get(i);
            if(pl != null && pl.conn != null){
                pl.flush();
            }
        }
    }
    
    public synchronized static void removePlayer(Player pl){
        if(PLAYER_ENTRYS.contains(pl)){
            PLAYER_ENTRYS.remove(pl);
        }
    }

    public synchronized static void client_disconnect(Session ss) {
        if (SessionManager.CLIENT_ENTRYS.contains(ss)) {
            ss.connected = false;
            SessionManager.time_login.put(ss.user, System.currentTimeMillis() + 1_000_000_000L);
            if (ss.p != null && ss.p.map != null) {
                ss.p.map.leave_map(ss.p);
                try {
                    if (ss.p.trade_target != null) {
                        Trade.end_trade_by_disconnect(ss.p.trade_target, ss.p, false);
                    }
                    if (ss.p.party != null) {
                        ss.p.party.remove_mem(ss.p);
                    }
                    ss.p.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SessionManager.removePlayer(ss.p);
                Log.gI().add_log(ss.p, "Logout : [Beri] " + Util.number_format(ss.p.get_vang()) + " [Ruby] "
                        + Util.number_format(ss.p.get_ngoc()));
            }
            ss.p = null;
            ss.clear_network(ss);
            SessionManager.time_login.put(ss.user, System.currentTimeMillis() + 5_000L);
            ss.update_onl(0);
            //
            SessionManager.CLIENT_ENTRYS.remove(ss);
            System.out.println("disconnect session " + ss.user + " - online : " + SessionManager.CLIENT_ENTRYS.size());
        }
    }
}
