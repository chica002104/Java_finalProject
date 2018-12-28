package War;

import javax.swing.JFrame;

import Tank.GamePanel;

public class Driver {

	public static void main(String[] args) {
		JFrame frame = new JFrame("Unit2, Lab20");
		//frame.setSize(800, 800);
		frame.setSize(1440, 900);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel p = new Panel();
        frame.setContentPane(p);
        p.requestFocus();
		frame.setVisible(true);

	}

}

