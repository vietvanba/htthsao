
package activities;

import client.Player;
import io.Message;
import java.io.IOException;

public class PET {
    
    public static void process_Pet(Player p, Message m) throws IOException {
        byte action = m.reader().readByte();
        switch (action) {
                    case 3:
                        p.pet.send_Bag_Pet();
                        break;
                }
    }
}
