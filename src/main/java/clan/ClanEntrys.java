package clan;

import database.SQL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author baodubai
 */
public class ClanEntrys {
    public static List<Clan> Clans = new ArrayList<>();
    
    public static Clan findClan(int id){
        return Clans.stream().filter(cl -> cl != null && cl.Id == id).findFirst().orElse(null);
    }
    
    public static void updateClan(){
        for(int i = 0 ; i < Clans.size();i++){
            Clan clan = Clans.get(i);
            if(clan != null){
                SQL.gI().executeUpdate(String.format("UPDATE `clan` SET name='%s',icon='%s',level='%s',numMem='%s',maxMem='%s',Exp='%s',maxExp='%s',Rank='%s',Caption='%s',attribute='%s',Info='%s',Member='%s' WHERE id=" + clan.Id,
                    clan.Name, clan.idIcon, clan.Level, clan.numMem, clan.maxMem, clan.exp, clan.maxExp, clan.Rank, clan.nameCaption, clan.Attribute.toString(),clan.Info.toString(),clan.Members.toString()));
                Clans.remove(clan);
            }
        }
    }
}
