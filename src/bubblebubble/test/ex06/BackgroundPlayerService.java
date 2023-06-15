package bubblebubble.test.ex06;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// 메인스레드 바쁨 - 키보드 이벤트 처리하기에 바쁨
// 백그라운드에서 계속 관찰
public class BackgroundPlayerService implements Runnable {
	private Player player;
	private BufferedImage image;
	
	public BackgroundPlayerService(Player player) {
		this.player = player;
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
			System.out.println("L COLOR: " + leftColor);
			System.out.println("R COLOR: " + rightColor);
			
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0){
				System.out.println("왼쪽 벽 충돌");
			}
			if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0){
				System.out.println("오른쪽 벽 충돌");
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
