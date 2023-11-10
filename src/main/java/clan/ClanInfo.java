package clan;

import org.json.simple.JSONArray;

/**
 *
 * @author Administrator
 */
public class ClanInfo {

    public int Ruby;
    public int Bery;

    public ClanInfo() {
        Ruby = 0;
        Bery = 0;
    }

    @Override
    public String toString() {
        JSONArray arr = new JSONArray();
        arr.add(Ruby); // để gì đó vào đây
        arr.add(Bery);
        return arr.toString();
    }
}
