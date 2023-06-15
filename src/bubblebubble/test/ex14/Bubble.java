package bubblebubble.test.ex14;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Bubble extends JLabel implements Movable {
		
		// 의존성 콤포지션
		private BubbleFrame mContext;
		private Player player;
		private BackgroundBubbleService bubbleService;
		
		// 위치 상태
		private int x;
		private int y;

		
		// 움직임에 대한 상태
		private boolean left;
		private boolean right;
		private boolean up;
	

		// 적과 충돌 상태
		private int state; // 0(물방울) , 1(적을 가둔 물방울)
		
		private ImageIcon bubble; // 물방울
		private ImageIcon bubbled; // 적을 가둔 물방울
		private ImageIcon bomb; // 물방울 터짐 상태
		
		public Bubble(BubbleFrame mContext) {
			this.mContext = mContext;
			this.player = mContext.getPlayer();
			initObject();
			initSetting();
		}
		
		private void initObject() {
			bubble = new ImageIcon("image/bubble.png");
			bubbled = new ImageIcon("image/bubbled.png");
			bomb = new ImageIcon("image/bomb.png");
			
			bubbleService = new BackgroundBubbleService(this);
		}
		
		private void initSetting() {
			left = false;
			right = false;
			up = false;
			
			x = player.getX();
			y = player.getY();
			
			setIcon(bubble);
			setSize(50, 50);
			
			state = 0;
			
		}
		
		@Override
		public void left() {
			left = true;
			
			for(int i = 0; i<400; i++) {
				x--;
				setLocation(x,y);
				if(bubbleService.leftWall()) {
					left = false;
					break;
				}
				System.out.println("x 좌표: " +x + " y 좌표: " + y);
				try {
					Thread.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			up();
		}

		@Override
		public void right() {
			right = true;
			for(int i = 0; i<400; i++) {
				x++;
				setLocation(x,y);
				if(bubbleService.rightWall()) {
					right = false;
					break;
				}
				System.out.println("x 좌표: " +x + " y 좌표: " + y);
				try {
					Thread.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			up();
		}

		@Override
		public void up() {
			up = true;
			while(up) {
				y--;
				setLocation(x,y);
				if(bubbleService.topWall()) {
					up = false;
					break;
				}
				System.out.println("x 좌표: " +x + " y 좌표: " + y);
				try {
					Thread.sleep(1);
				}catch(InterruptedException e) {
					e.printStackTrace();
				}
			}
			clearBubble();
			
		}
		
		// 천장에 버블이 도착하고 3초후에 메모리에서 소멸 
		public void clearBubble() {
			try {
				
				Thread.sleep(3000);
				setIcon(bomb);
				Thread.sleep(500);
				
				mContext.remove(this); // BubbleFrame의 bubble이 메모리에서 사라짐
				mContext.repaint(); // 메모리에 있는 것만 새로 그림
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
}
