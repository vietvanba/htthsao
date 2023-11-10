
import client.Player;
import io.Message;
import java.io.IOException;


public class Upgrade_Item {
    
    public short[] Beri_REQ = new short[] {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000};
    public short[] Rubi_REQ = new short[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
    public short[] BCH_REQ = new short[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    public short[] BV_REQ = new short[] {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1};
    
    
    public static void processSuperUpgrade(Player p, Message m2) throws IOException 
    {
        byte typeUpgrade = m2.reader().readByte();
        int idItemUpgrade = m2.reader().readShort();
        int beri_or_rubi = m2.reader().readByte();
        short numItem2 = m2.reader().readByte();
        switch(typeUpgrade)
        {
            case 0:
                
                break;
        }
    }
    
    public static void show_table_upgrade(Player p) throws IOException 
    {
        Message m = new Message(-48);
        m.writer().writeByte(7);
        p.conn.addmsg(m);
        m.cleanup();
    }

    public static void show_table_super_upgrade(Player p) throws IOException 
    {
        Message m = new Message(66);
        m.writer().writeByte(7);
        p.conn.addmsg(m);
        m.cleanup();
    }
    
}
