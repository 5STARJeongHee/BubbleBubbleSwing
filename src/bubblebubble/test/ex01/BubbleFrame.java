package bubblebubble.test.ex01;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.BorderLayout;

// 1. 윈도우 창
// 2. 윈도우 창 내부 패널 하나 가지고 있음
public class BubbleFrame extends JFrame{
	
	public BubbleFrame() {
		setSize(1000, 640);
		getContentPane().setLayout(null);
		setVisible(true);
	}
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
