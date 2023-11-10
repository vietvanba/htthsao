/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clan;

import client.Player;
import core.Manager;
import io.Message;
import io.SessionManager;
import java.util.stream.Stream;

/**
 *
 * @author Administrator
 */
public class ClanService {

    public static void UpdateClan(Player p, Clan clan) {
        clan.numMem = clan.Members.size();
        CreateDataClan(p, clan);
        UpdateRank(p, clan);
        ReadDataChat(p, clan);
        ReadMem(p, clan);
        SendAttribute(p, clan);
        SendNumTangQua(p, clan);
        SendClanXp(p, clan);
        SendClanMoney(p, clan);
        SendClanInfo(p, clan);
        SetDataClan(p, clan);
        UpdateClanInventory(p);
    }

    public static void CreateDataClan(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(0);
            m.writer().writeShort(clan.Id);
            m.writer().writeUTF(clan.Name);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void UpdateRank(Player p, Clan clan) {
        try {
            ClanMember member = clan.Members.stream().filter(id -> id.Id == p.id).findFirst().orElse(null);
            Message m = new Message(-52);
            m.writer().writeByte(1);
            m.writer().writeUTF(member.Name);
            m.writer().writeByte(member.Role);
            //p.conn.addmsg(m);

            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void ReadMem(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(3);
            m.writer().writeByte(clan.numMem);
            for (int i = 0; i < clan.numMem; i++) {
                ClanMember member = clan.Members.get(i);
                m.writer().writeShort(member.Id);
                m.writer().writeUTF(member.Name);
                m.writer().writeShort(member.Lv);
                m.writer().writeByte(member.Role);
                m.writer().writeShort(member.numTangQua);
                m.writer().writeShort(member.gopRuby);
                m.writer().writeShort(member.numQuest);
                m.writer().writeInt(member.conghien);
                m.writer().writeShort(member.Head);
                m.writer().writeShort(member.Hair);
                m.writer().writeShort(member.Hat);
                m.writer().writeByte(2);
            }
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void ReadDataChat(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(9);
            m.writer().writeShort(clan.Chats.size());
            Manager.gI().SendMessageClan(clan, m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void ReadDataChat2(Player plChat, Clan clan, byte type, String text) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(8);
            m.writer().writeByte(type);
            m.writer().writeShort(clan.Chats.size());
            m.writer().writeShort(plChat.id);
            m.writer().writeUTF(plChat.name);
            m.writer().writeUTF(text);
            m.writer().writeLong(System.currentTimeMillis());
            Manager.gI().SendMessageClan(clan, m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendMoiVao(Player plMoiVao, String noitice) {
        try {

            Message m = new Message(-31);
            m.writer().writeByte(0);
            m.writer().writeUTF(noitice);
            m.writer().writeByte(5);
            plMoiVao.list_msg_cache.add(m);
        } catch (Exception e) {

        }
    }
    
    public static void sendMoiVaoBang(Player plSend, Player plReceive){
        try {
            Message m = new Message(-52);
            m.writer().writeByte(7);
            m.writer().writeInt(plSend.id);
            m.writer().writeUTF(plSend.name);
            plReceive.conn.addmsg(m);
            m.cleanup();
            plReceive.idClanSend = plSend.clan.Id;
        } catch (Exception e) {

        }
    }

    public static void SendXinVao(Player plXinVao, Clan clan, String text) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(8);
            m.writer().writeByte(-1);
            m.writer().writeShort(clan.Chats.size());
            m.writer().writeShort(plXinVao.id);
            m.writer().writeUTF(plXinVao.name);
            m.writer().writeUTF(text);
            m.writer().writeLong(System.currentTimeMillis());
            Manager.gI().SendMessageClan(clan, m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendAttribute(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(4);
            m.writer().writeInt(clan.Attribute.litmit);
            m.writer().writeInt(clan.Attribute.point + 2);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendNumTangQua(Player p, Clan clan) {
        try {
            ClanMember member = clan.Members.stream().filter(id -> id.Id == p.id).findFirst().orElse(null);
            if (member == null) {
                return;
            }
            Message m = new Message(-52);
            m.writer().writeInt(13);
            m.writer().writeInt(member.cooldownTangQua);
            m.writer().writeInt(member.numTangQua);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendClanXp(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeInt(16);
            m.writer().writeByte(clan.isLevelup);
            m.writer().writeInt(clan.exp);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendClanMoney(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeInt(17);
            m.writer().writeInt(clan.Info.Ruby);
            m.writer().writeInt(clan.Info.Bery);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void UpdateClanInventory(Player p) {
        try {
            Message m = new Message(-52);
            m.writer().writeInt(19);

            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SendClanInfo(Player p, Clan clan) {
        try {
            ClanMember member = clan.Members.stream().filter(id -> id.Id == p.id).findFirst().orElse(null);
            Message m = new Message(-52);
            m.writer().writeByte(5);
            m.writer().writeShort(p.id);
            m.writer().writeShort(clan.Id);
            m.writer().writeShort(clan.idIcon);
            m.writer().writeByte(member.Role);

            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }

    public static void SetDataClan(Player p, Clan clan) {
        try {
            Message m = new Message(-52);
            m.writer().writeByte(2);
            m.writer().writeShort(clan.idIcon);
            m.writer().writeUTF(clan.nameCaption);
            m.writer().writeShort(clan.Level);
            m.writer().writeInt(clan.exp);
            m.writer().writeInt(clan.maxExp);
            m.writer().writeByte(clan.numMem);
            m.writer().writeByte(clan.maxMem);
            m.writer().writeInt(clan.Rank);
            m.writer().writeUTF(clan.strVoice);
            m.writer().writeByte(clan.trungSinh);
            m.writer().writeInt(clan.cAction);
            p.conn.addmsg(m);
            m.cleanup();
        } catch (Exception e) {

        }
    }
}
