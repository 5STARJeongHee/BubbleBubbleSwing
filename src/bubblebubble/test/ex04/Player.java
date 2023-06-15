package bubblebubble.test.ex04;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
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

	public boolean isLeft() {
		return left;
	}
	
	public boolean isRight() {
		return right;
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
		System.out.println("left 스레드 생성");
		left = true;
		new Thread(() -> {
			while(left) {
				setIcon(playerL);
				x -= 1;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
		System.out.println("left 스레드 종료");
	}

	@Override
	public void right() {
		System.out.println("right 스레드 생성");
		right = true;
		new Thread(() -> {
			while(right) {
				setIcon(playerR);
				x += 1;
				setLocation(x, y);	
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
		System.out.println("right 스레드 종료");
	}

	@Override
	public void up() {
		System.out.println("점프");
	}

	@Override
	public void down() {
		
	}

}
