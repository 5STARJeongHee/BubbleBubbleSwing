package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import bubble.game.component.Bubble;
import bubble.game.component.Player;

// 메인스레드 바쁨 - 키보드 이벤트 처리하기에 바쁨
// 백그라운드에서 계속 관찰
public class BackgroundPlayerService implements Runnable {
	private Player player;
	private BufferedImage image;
	private List<Bubble> bubbleList;
	
	public BackgroundPlayerService(Player player) {
		this.player = player;
		this.bubbleList = player.getBubbleList();
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		}catch (Exception e) {
		}
		
	}
	@Override
	public void run() {
		// 색상 확인
		while(true) {
			Color leftColor = new Color(image.getRGB(player.getX() -10 , player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			
			// -2 가 나오면 바닥 흰색
			int bottomColor = image.getRGB(player.getX() +10, player.getY() +50 +5) + image.getRGB(player.getX() -10 +50, player.getY() +50 +5);
			
			// 1. 버블 충돌 확인
			for(int i = 0 ; i< bubbleList.size(); i++){
				Bubble bubble = bubbleList.get(i);
				if(bubble.getState() == 1) {
					if((Math.abs(player.getX() - bubble.getX()) < 10) 
							&& (Math.abs(player.getY() - bubble.getY()) > 0 && Math.abs(player.getY() - bubble.getY()) < 50)) {
						System.out.println("적군 폭발");
					
						bubble.clearBubbled();
						
					}

				}
					
			}
			// 바닥 충돌 확인
			if(bottomColor != -2) {
//				System.out.println("바텀 컬러 : " + bottomColor);
//				System.out.println("바닥에 충돌함");
				player.setDown(false);
			} else { // 바닥이 흰색인 경우
				if(!player.isUp() && !player.isDown()) {
					player.down();
				}
			}
//			System.out.println("L COLOR: " + leftColor);
//			System.out.println("R COLOR: " + rightColor);
			
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0){
//				System.out.println("왼쪽 벽 충돌");
				player.setLeft(false);
				player.setLeftWallCrash(true);
			}else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0){
//				System.out.println("오른쪽 벽 충돌");
				player.setRight(false);
				player.setRightWallCrash(true);
			} else {
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
}
