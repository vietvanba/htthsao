package clone;

import map.Map;

/**
 *
 * @author Death God
 */
public class PlayerClone extends ClonePlayer {
    
    public PlayerClone(String name, Map map) {
        super(name);
        this.map = map;
    }

    @Override
    protected void init() {        
        UpdateClone.gI().players.add(this);
    }

    @Override
    public int get_head() {
        return this.head;
    }

    @Override
    public int get_hair() {
        return this.hair;
    }

    @Override
    public short[] get_fashion() {
        short[] result = null;
        for (int i = 0; i < this.fashion.size(); i++) {
            if (this.fashion.get(i).is_use) {
                result = new short[this.fashion.get(i).data.length];
                System.arraycopy(this.fashion.get(i).data, 0, result, 0, result.length);
                break;
            }
        }
        return result;
    }

    @Override
    public void update() {

    }

    @Override
    public void startDie() {
        
    }

    @Override
    public void startLeaveMap() {
        
    }
}
