package bubblebubble.test.ex18;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

// 메인스레드 바쁨 - 키보드 이벤트 처리하기에 바쁨
// 백그라운드에서 계속 관찰
public class BackgroundEnemyService implements Runnable {
	private Enemy enemy;
	private BufferedImage image;
	
	public BackgroundEnemyService(Enemy enemy) {
		this.enemy = enemy;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		}catch (Exception e) {
		}
		
	}
	@Override
	public void run() {
		// 색상 확인
		while(enemy.getState() == 0) {
			Color leftColor = new Color(image.getRGB(enemy.getX() -10 , enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			
			// -2 가 나오면 바닥 흰색
			int bottomColor = image.getRGB(enemy.getX() +10, enemy.getY() +50 +5) + image.getRGB(enemy.getX() -10 +50, enemy.getY() +50 +5);
				
			// 바닥 충돌 확인
			if(bottomColor != -2) {
//				System.out.println("바텀 컬러 : " + bottomColor);
//				System.out.println("바닥에 충돌함");
				enemy.setDown(false);
			} else { // 바닥이 흰색인 경우
				if(!enemy.isUp() && !enemy.isDown()) {
					enemy.down();
				}
			}
//			System.out.println("L COLOR: " + leftColor);
//			System.out.println("R COLOR: " + rightColor);
			
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0){
//				System.out.println("왼쪽 벽 충돌");
				enemy.setLeft(false);
				if(!enemy.isRight()) {
					enemy.right();
				}
			}else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0){
//				System.out.println("오른쪽 벽 충돌");
				enemy.setRight(false);
				if(!enemy.isLeft()) {
					enemy.left();
				}
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
