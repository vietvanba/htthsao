package template;

import core.Util;

import java.util.ArrayList;
import java.util.List;

public class ItemVongQuayTemplate {

    public static List<ItemVongQuayTemplate> ENTRYS = new ArrayList<>();
    public byte type;
    public short id;

    static {
        byte[] type = new byte[]{4};
        short[] id_ = new short[]{127, 128, 177, 178, 179, 7, 113, 114, 100, 79, 9, 158, 58, 29, 372, 223, 224, 225, 226, 427};
        for (int i = 0; i < id_.length; i++) {
            ItemVongQuayTemplate temp = new ItemVongQuayTemplate();
            temp.id = id_[i];
            temp.type = 4;
            ENTRYS.add(temp);
        }
    }

    public static ItemVongQuayTemplate get_random() {
        if (25 > Util.random(100)) {
            return null;
        }
        int index = Util.random(20);
        if (index < 19)
            return ItemVongQuayTemplate.ENTRYS.get(index);
        else {
            index = Util.random(20);
            if (index < 19)
                return ItemVongQuayTemplate.ENTRYS.get(index);
            else {
                index = Util.random(20);
                return ItemVongQuayTemplate.ENTRYS.get(index);
            }
        }
    }
}
