/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clan;

import org.json.simple.JSONArray;

/**
 *
 * @author Administrator
 */
public class ClanAttribute {
    public int strength;
    public int defend;
    public int stamina;
    public int mind;
    public int agility;
    public int litmit;
    public int point;
    public ClanAttribute(){
        strength = 0;
        defend = 0;
        stamina = 0;
        mind = 0;
        agility = 0;
        litmit = 20;
        point = 2;
    }
    
    @Override
    public String toString(){
        JSONArray arr = new JSONArray();
        arr.add(strength);
        arr.add(defend);
        arr.add(stamina);
        arr.add(mind);
        arr.add(agility);
        arr.add(litmit);
        arr.add(point);
        return arr.toString();
    }
}
