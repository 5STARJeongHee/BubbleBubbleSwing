package bubble.game;

import bubble.game.component.Player;

public interface EnemyMovable extends Movable{
	public abstract void killPlayer(Player plyaer);
}
