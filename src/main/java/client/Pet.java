package client;

import io.Message;
import java.io.IOException;
import template.ItemPet;

public class Pet {
    private final Player p;
    public short maxSize;
    public ItemPet[] mPet;
    
    public Pet(Player p) {
            this.p = p;
	}
    
    private int get_quantPet() {
        int result = 0;
        for(int i = 0; i < mPet.length; i++) {
            if(mPet[i] != null) {
                result++;
            }
        }
        return result;
    } 
    
    public void send_Bag_Pet() throws IOException {
        Message m = new Message(-80);
        m.writer().writeByte(3); // type 3 la show Bag Pet
        int soluongPet = get_quantPet();
        m.writer().writeShort(soluongPet);
        for(int i = 0; i < soluongPet; i++)
        {
            m.writer().writeShort(0); // ID pet
            m.writer().writeUTF("PET 0"); // name
            m.writer().writeUTF("Thong tin"); // info
            m.writer().writeShort(200); // Id Icon
            m.writer().writeByte(4); // typeObj
            m.writer().writeByte(1); // isUse
            int petOplen = mPet[i].option_Pet.size();
            m.writer().writeByte(petOplen); // lenOp
            for(int j = 0; j < petOplen; j++) {
               m.writer().writeByte(mPet[i].option_Pet.get(i).id);
               m.writer().writeShort(mPet[i].option_Pet.get(i).getParamGoc());
            }
        }
        p.conn.addmsg(m);
        m.cleanup();
    }
}
