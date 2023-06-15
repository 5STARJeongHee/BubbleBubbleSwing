package bubblebubble.test.ex12;

/**
 * 	default 를 사용하면 인터페이스도 몸체가 있는 메서드를 만들 수 있음
 *  원래라면 Adapter 패턴으로 일부를 구현하여 항상 구현하지 않도록 하여 인터페이스의 강제성을 일부 회피 가능
 *  하지만 자바는 다중 상속이 되지 않으므로 다중 상속이 필요한 경우 힘듬
 *  대신 default라는 문법이 있어 구현하지 않고도 사용할 수 있으며 구현에 강제성을 주지 않는 방법이 생김
 *   
 * 
 *
 */
public interface Movable {
	public abstract void left();
	public abstract void right();
	public abstract void up();
	default public void down() {}; 


}
