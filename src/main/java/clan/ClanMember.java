package clan;

import client.Player;
import org.json.simple.JSONArray;

/**
 *
 * @author Administrator
 */
public class ClanMember {

    public short Lv;
    public int Id;
    public String Name;
    public short Head;
    public short Hair;
    public short Hat;
    public int gopRuby;
    public int conghien;
    public int numTangQua;
    public int cooldownTangQua;
    public int numQuest;
    public byte Role;

    public ClanMember(Player player, byte role) {
        Lv = 0;
        Id = player.id;
        Name = player.name;
        Head = player.head;
        Hair = player.hair;
        Hat = 12;
        gopRuby = 0;
        conghien = 0;
        numTangQua = 0;
        cooldownTangQua = 0;
        numQuest = 0;
        Role = role;
    }
    
    public ClanMember() {
    }

    @Override
    public String toString() {
        JSONArray arr = new JSONArray();
        arr.add(Lv);
        arr.add(Id);
        arr.add(Name);
        arr.add(Head);
        arr.add(Hair);
        arr.add(Hat);
        arr.add(gopRuby);
        arr.add(conghien);
        arr.add(numTangQua);
        arr.add(cooldownTangQua);
        arr.add(numQuest);
        arr.add(Role);
        return arr.toString();
    }
}
