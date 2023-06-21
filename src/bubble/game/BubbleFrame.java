package bubble.game;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import bubble.game.component.Enemy;
import bubble.game.component.Player;
import bubble.game.music.BGM;
import bubble.game.state.Location;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BubbleFrame extends JFrame {

	private BubbleFrame mContext = this;
	private JLabel backgroundMap;
	private Player player;
	
	private List<Enemy> enemyList;
	
	public BubbleFrame() {
		initSetting();
		initObject();
		initListener();
		setVisible(true);
		
	}
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			// keyboard 눌림 이벤트
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						if(!player.isLeft() && !player.isLeftWallCrash()) {
							player.left();
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(!player.isRight() && !player.isRightWallCrash()) {
							player.right();
						}
						break;
					case KeyEvent.VK_UP:
						if(!player.isUp() && player.isDown()) {
							player.up();
						}
						break;
					case KeyEvent.VK_SPACE:
						player.attack();
						break;
//					case KeyEvent.VK_DOWN:
//						player.down();
//						break;
					
				}
			}
			// keyboard 놓음 이벤트

			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT:
						player.setLeft(false);
						break;
					case KeyEvent.VK_RIGHT:
						player.setRight(false);
						break;
					case KeyEvent.VK_UP:
						player.up();
						break;
				}
			}
		}
		);
		
	}
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMapService.png"));
//		backgroundMap.setSize(974,581);
//		backgroundMap.setLocation(0,10);
//		getContentPane().add(backgroundMap); // JFrame에 추가
		
		setContentPane(backgroundMap);
		
		player = new Player(mContext);
		add(player);
		
		enemyList = new ArrayList<>();
		enemyList.add(new Enemy(mContext, new Location(480, 178)));
		enemyList.add(new Enemy(mContext, new Location(160, 178)));
		enemyList.get(0).right();
		enemyList.get(1).left();
		
		add(enemyList.get(0));
		add(enemyList.get(1));
		new BGM();
		
		
	}
	
	private void initSetting() {
		setSize(1000,640);
		getContentPane().setLayout(null); // absolute 레이아이웃 버튼 등을 자유롭게 배치가능
		setLocationRelativeTo(null); // Jframe 가운데 배치
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼 창으로 끌때 JVM도 같이 종료 시키기
	}
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
