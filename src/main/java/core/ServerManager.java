package core;

import clone.UpdateClone;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import io.Session;
import io.SessionManager;
import java.util.concurrent.TimeUnit;
import map.Boss;
import map.Map;

public class ServerManager implements Runnable {

    private static ServerManager instance;
    private final Thread mythread;
    private Thread server_live;
    private boolean running;
    private ServerSocket server;
    private final long time;

    public ServerManager() {
        this.time = System.currentTimeMillis();
        this.mythread = new Thread(this);
    }

    public static ServerManager gI() {
        if (instance == null) {
            instance = new ServerManager();
        }
        return instance;
    }

    public void init() {
        Manager.gI().init();
        Data.gI().InitData();
        server_update_right_time();
        this.running = true;
        this.mythread.start();
        this.server_live.start();
    }

    @Override
    public void run() {
        try {
            this.server = new ServerSocket(Manager.gI().server_port);
            System.out.println("Started in " + (System.currentTimeMillis() - this.time) + "ms");
            System.out.println();
            System.out.println("LISTEN PORT " + Manager.gI().server_port + "...");
            new Thread(UpdateTopWanted.gI(), "Top wanted").start();
            new Thread(UpdateClone.gI(), "Update Clone").start();
            while (this.running) {
                Socket client = this.server.accept();
                Session ss = new Session(client);
                SessionManager.client_connect(ss);
                TimeUnit.MILLISECONDS.sleep(5);
            }
        } catch (Exception e) {
        }
    }

    private void server_update_right_time() {
        this.server_live = new Thread(() -> {
            Calendar now;
            int hour, min, sec, millis;
            boolean a = true;
            while (this.running) {
                try {
                    now = Util.get_calendar();
                    hour = now.get(Calendar.HOUR_OF_DAY);
                    min = now.get(Calendar.MINUTE);
                    sec = now.get(Calendar.SECOND);
                    millis = now.get(Calendar.MILLISECOND);
                    //
                    if (hour == 0 && min == 0 && sec == 0) {
                        for (Map[] map_all : Map.ENTRYS) {
                            for (Map map : map_all) {
                                for (int i = 0; i < map.players.size(); i++) {
                                    map.players.get(i).change_new_date();
                                }
                            }
                        }
                    }
                    if (sec % 30 == 0 || a) { // update BXH + luu data
                        a = false;
                        SessionManager.saveAllData();
                    }
                    if (min % 10 == 0) {
                        Boss.create_boss();
                    }
                    if (hour % 3 == 0 && min == 0 && sec == 0) {
                        Manager.gI().save_list_icon_fail();
                    }
                    //
                    long time_sleep = 1000 - millis;
                    if (time_sleep > 0) {
                        if (time_sleep < 100) {
                            System.err.println("server time update process is overloading...");
                        }
                        Thread.sleep(time_sleep);
                    }
                } catch (InterruptedException e) {
                } catch (Exception e) {
                    System.out.println("exception at server update rigth time " + e.getMessage());
                }
            }
        });
    }

    public void close() throws IOException {
        running = false;
        server_live.interrupt();
        server.close();
        instance = null;
    }
}
