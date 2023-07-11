package bubble.game.component;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import bubble.game.BubbleFrame;
import bubble.game.Movable;
import bubble.game.service.BackgroundPlayerService;
import bubble.game.state.PlayerWay;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Player extends JLabel implements Movable {
	private BubbleFrame mContext;
	private List<Bubble> bubbleList;
	
	// 위치 상태
	private int x;
	private int y;

	// 움직임에 대한 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 적과 충돌 상태
	private int state; // 0(dead) , 1(alive)
	
	// 플레이어의 방향
	private PlayerWay playerWay;
	
	// 벽 충돌 상태
	private boolean leftWallCrash;
	private boolean rightWallCrash;
	// 플레이어 이동 속도
	private final int SPEED = 4;
	private final int JUMP_SPEED = 3; // up, down
	private ImageIcon playerR, playerL;

	public Player(BubbleFrame mContext) {
		this.mContext = mContext;
		initObject();
		initSetting();
		initBackgroundPlayerService();
		
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	private void initSetting() {
		bubbleList = new ArrayList<>();
		
		state = 1;
		
		x = 80;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;
		leftWallCrash = false;
		rightWallCrash = false;
		
		playerWay = PlayerWay.RIGHT;
		
		
		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
		
		
	}

	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start();
	}

	// 1. 그림 변경 시점: 이벤트 루프의 모든 task가 완료되어야 repaint가 된다.
	// 2. main thread 밖에 없다면 key 여러개 동시 전달-> 이벤트 루프에 동시에 전달 x 하나만 처리
	// 이하를 이벤트 핸들러 역할로 쓴다.
	@Override
	public void left() {
//		System.out.println("left 스레드 생성");
		if(state == 0) {
			return;
		}
		playerWay = PlayerWay.LEFT;
		left = true;
		new Thread(() -> {
			while (left) {
				setIcon(playerL);
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
		if(state == 0) {
			return;
		}
		playerWay = PlayerWay.RIGHT;
		right = true;
		new Thread(() -> {
			while (right) {
				setIcon(playerR);
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
		if(state == 0) {
			return;
		}
//		System.out.println("up");
		up = true;

		new Thread(() -> {
			for (int i = 0; i < 120 / JUMP_SPEED; i++) {
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
		if(state == 0) {
			return;
		}
		down = true;
		new Thread(() -> {
			while(down){
				y += JUMP_SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(4);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}

	@Override
	public void attack() {
		if(state == 0) {
			return;
		}
		new Thread(()-> {
			Bubble bubble = new Bubble(mContext);
			mContext.add(bubble);
			bubbleList.add(bubble);
			if(playerWay == PlayerWay.LEFT) {
				bubble.left();
			}else {
				bubble.right();
			}
		}).start();
	}

	
}
