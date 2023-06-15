package bubblebubble.test.ex15;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Movable {
	private BubbleFrame mContext;
	// 위치 상태
	private int x;
	private int y;

	// 움직임에 대한 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	
	// 플레이어의 방향
	private EnemyWay enemyWay;
	
	// 플레이어 이동 속도
	private final int SPEED = 3;
	private final int JUMP_SPEED = 1; // up, down
	private ImageIcon enemyR, enemyL;

	public Enemy(BubbleFrame mContext) {
		initObject();
		initSetting();
		initBackgroundEnemyService();
		this.mContext = mContext;
	}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	private void initSetting() {
		x = 480;
		y = 178;

		left = false;
		right = false;
		up = false;
		down = false;
		
		enemyWay = EnemyWay.RIGHT;
		
		setIcon(enemyR);
		setSize(50, 50);
		setLocation(x, y);
	}

	private void initBackgroundEnemyService() {
//		new Thread(new BackgroundEnemyService(this)).start();
	}

	// 1. 그림 변경 시점: 이벤트 루프의 모든 task가 완료되어야 repaint가 된다.
	// 2. main thread 밖에 없다면 key 여러개 동시 전달-> 이벤트 루프에 동시에 전달 x 하나만 처리
	// 이하를 이벤트 핸들러 역할로 쓴다.
	@Override
	public void left() {
//		System.out.println("left 스레드 생성");
		enemyWay = EnemyWay.LEFT;
		left = true;
		new Thread(() -> {
			while (left) {
				setIcon(enemyL);
				x -= SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
//		System.out.println("left 스레드 종료");
	}

	@Override
	public void right() {
//		System.out.println("right 스레드 생성");
		enemyWay = EnemyWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right) {
				setIcon(enemyR);
				x += SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}).start();
//		System.out.println("right 스레드 종료");
	}

	// left + up, right + up, up
	@Override
	public void up() {
//		System.out.println("up");
		up = true;

		new Thread(() -> {
			for (int i = 0; i < 130 / JUMP_SPEED; i++) {
				y -= JUMP_SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			up = false;
			down();
		}).start();

	}

	@Override
	public void down() {
		System.out.println("down");
		down = true;
		new Thread(() -> {
			while(down){
				y += JUMP_SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}


	
}
