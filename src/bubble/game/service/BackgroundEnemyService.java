package bubble.game.service;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.imageio.ImageIO;

import bubble.game.component.Enemy;
import bubble.game.state.EnemyPattern;
import bubble.game.state.EnemyWay;

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
//			System.out.println("적 좌표: " + enemy.getX() + ", " + enemy.getY());
			Color leftColor = new Color(image.getRGB(enemy.getX() -10 , enemy.getY() + 25));
			Color rightColor = new Color(image.getRGB(enemy.getX() + 50 + 15, enemy.getY() + 25));
			// -2 가 나오면 바닥 흰색
			int bottomColor = image.getRGB(enemy.getX() +10, enemy.getY() +50 +5) + image.getRGB(enemy.getX() -10 +50, enemy.getY() +50 +5);
			
			// 바닥 충돌 확인
			if(bottomColor != -2 ) {
				enemy.setDown(false);
				if(new Color(bottomColor).getRed() == 254) {
					enemy.setBottomWallCrash(true);
				}
			} else { // 바닥이 흰색인 경우
				if(!enemy.isUp() && !enemy.isDown() && !enemy.isBottomWallCrash()) {
					Color temp = new Color(bottomColor);
					enemy.down();
					if(new Color(bottomColor).getRed() != 254) {
						enemy.setBottomWallCrash(false);
					}
				}
			}
			// 왼쪽 오른쪽 벽 충돌시 동작
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0){
//				System.out.println("왼쪽 벽 충돌");
				enemy.setLeft(false);
				enemy.setLeftWallCrash(true);
			
				
				if(!enemy.isRight() && enemy.getPattern() == EnemyPattern.DEFAULT) {
					enemy.right();
				}
				
			}else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0){
//				System.out.println("오른쪽 벽 충돌");
				enemy.setRight(false);
				enemy.setRightWallCrash(true);
				
				if(!enemy.isLeft() && enemy.getPattern() == EnemyPattern.DEFAULT) {
					enemy.left();
				}
			} else {
				enemy.setRightWallCrash(false);
				enemy.setLeftWallCrash(false);
			}
			
			// 바닥 충돌 및 좌우측 끝 도달 시 HOME 패턴 적용
			if (enemy.isBottomWallCrash() && (enemy.isLeftWallCrash() || enemy.isRightWallCrash())) {
				if(enemy.getPattern() != EnemyPattern.HOME) {
					enemy.setPattern(EnemyPattern.HOME);
					setMovePattern(enemy);
					enemy.setLeft(false);
					enemy.setRight(false);
					enemy.setBottomWallCrash(false);
				}
			}
			// HOME 패턴 동작
			if(enemy.getPattern() == EnemyPattern.HOME) {
				List<EnemyWay> moveList = enemy.getMoveList();
				if(moveList.size() == 0) {
					enemy.setPattern(EnemyPattern.DEFAULT);
				}else {
					if(runPattern(moveList.get(0))) {
						moveList.remove(0);
					}
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
	
	private void setMovePattern(Enemy enemy) {
		List<EnemyWay> moveList = enemy.getMoveList();
		if(moveList.size() == 0) {
			moveList.add(EnemyWay.UP);
			moveList.add(EnemyWay.UP);
			moveList.add(EnemyWay.UP);
			if(enemy.isLeftWallCrash()) {
				moveList.add(EnemyWay.RIGHT);
			}else if(enemy.isRightWallCrash()) {
				moveList.add(EnemyWay.LEFT);
			}
		}
	}
	private boolean runPattern(EnemyWay way) {
		if(way == EnemyWay.LEFT) {
			if(!enemy.isLeft()) {
				enemy.left();
				return true;
			}
		} else if(way == EnemyWay.RIGHT) {
			if(!enemy.isRight()) {
				enemy.right();
				return true;
			}
		} else if(way == EnemyWay.UP) {
			if(!enemy.isUp() && !enemy.isDown()) {
				enemy.up();
				return true;
			}
		}
		return false;
	}
	
	
}
