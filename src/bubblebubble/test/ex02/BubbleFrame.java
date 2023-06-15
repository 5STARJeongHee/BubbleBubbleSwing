package bubblebubble.test.ex02;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BubbleFrame extends JFrame {

	private JLabel backgroundMap;
	private Player player;
	
	public BubbleFrame() {
		initSetting();
		initObject();
		
		setVisible(true);
		
	}
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
//		backgroundMap.setSize(974,581);
//		backgroundMap.setLocation(0,10);
//		getContentPane().add(backgroundMap); // JFrame에 추가
		
		setContentPane(backgroundMap);
		
		player = new Player();
		add(player);
		
		
		
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
