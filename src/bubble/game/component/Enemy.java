package bubble.game.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Movable;
import bubble.game.service.BackgroundEnemyService;
import bubble.game.state.EnemyPattern;
import bubble.game.state.EnemyWay;
import bubble.game.state.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Enemy extends JLabel implements Movable {
	private BubbleFrame mContext;
	// 위치 상태
	private Location loc;

	// 움직임에 대한 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 벽 충돌 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	private boolean bottomWallCrash;
	
	private int state; // 0(live) , 1(bubbled)

	// 플레이어의 방향
	private EnemyWay enemyWay;

	// 플레이어 이동 속도
	private final int SPEED = 3;
	private final int JUMP_SPEED = 1; // up, down
	private ImageIcon enemyR, enemyL;
	
	// 적 움직임에 대한 패턴
	private EnemyPattern pattern;
	List<EnemyWay> moveList;

	public Enemy(BubbleFrame mContext, Location loc) {
		this.loc = loc;
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundEnemyService();
		moveList = new ArrayList<>();
	}

	private void initObject() {
		enemyR = new ImageIcon("image/enemyR.png");
		enemyL = new ImageIcon("image/enemyL.png");
	}

	private void initSetting() {
		loc = new Location(loc.getX(), loc.getY());

		left = false;
		right = false;
		up = false;
		down = false;

		enemyWay = EnemyWay.RIGHT;
		pattern = EnemyPattern.DEFAULT;
		
		setLeftWallCrash(false);
		setRightWallCrash(false);
		setBottomWallCrash(false);
		
		setIcon(enemyR);
		setSize(50, 50);
		setLocation(loc.getX(), loc.getY());
	}

	private void initBackgroundEnemyService() {
		new Thread(new BackgroundEnemyService(this)).start();
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
				loc.setX(loc.getX() - SPEED);
				setLocation(loc.getX(), loc.getY());
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
				loc.setX(loc.getX() + SPEED);
				setLocation(loc.getX(), loc.getY());
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
				loc.setY(loc.getY() - JUMP_SPEED);
				setLocation(loc.getX(), loc.getY());
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
		down = true;
		new Thread(() -> {
			while (down) {
				loc.setY(loc.getY() + JUMP_SPEED);
				setLocation(loc.getX(), loc.getY());
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
