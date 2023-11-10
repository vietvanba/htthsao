/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clan;

import client.Player;

/**
 *
 * @author Administrator
 */
public class ClanChat {
    public byte Role;
    public int Type;
    public int Id;
    public String PlayerName;
    public int PlayerId;
    public long time;
    
    public ClanChat(Player p,byte role){
        Role = role;
        Type = -1;
        PlayerName = p.name;
        PlayerId = p.id;
        time = System.currentTimeMillis();
    }
}
