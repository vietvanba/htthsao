package clone;

import client.Player;

/**
 *
 * @author Death God
 */
public abstract class ClonePlayer extends Player{
    
    public ClonePlayer(String name) {
        super(name);
    }
    
    public void start() {
        this.init();
    }
    
    protected abstract void init();
    
    @Override
    public abstract int get_head();

    @Override
    public abstract int get_hair();

    @Override
    public abstract short[] get_fashion();
    
    public abstract void startDie();

    public abstract void startLeaveMap();
    
    public abstract void update();
}
