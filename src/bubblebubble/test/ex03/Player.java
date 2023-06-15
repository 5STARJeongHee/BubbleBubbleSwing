package bubblebubble.test.ex03;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Player extends JLabel implements Movable {

	// 위치 상태
	private int x;
	private int y;

	// 움직임에 대한 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;
	
	private ImageIcon playerR, playerL;

	public Player() {
		initObject();
		initSetting();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	private void initSetting() {
		x = 55;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;
		
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}

	// 1. 그림 변경 시점: 이벤트 루프의 모든 task가 완료되어야 repaint가 된다.
	// 2. main thread 밖에 없다면 key 여러개 동시 전달-> 이벤트 루프에 동시에 전달 x 하나만 처리
	// 이하를 이벤트 핸들러 역할로 쓴다.
	@Override
	public void left() {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		setIcon(playerL);
		x -= 10;
		setLocation(x, y);
	}

	@Override
	public void right() {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			// TODO: handle exception
		}
		setIcon(playerR);
		x += 10;
		setLocation(x, y);
	}

	@Override
	public void up() {
		System.out.println("점프");
	}

	@Override
	public void down() {
		
	}

}
