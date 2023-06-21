package bubble.game;

import bubble.game.component.Enemy;

public interface BubbleMovable extends Movable{
	public abstract void bubbleHit(Enemy enemy);
}
