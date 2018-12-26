package War;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import org.magiclen.media.AudioPlayer;

import Tank.GamePanel.KeyGame;
import Tank.GamePanel.KeyHelp;
import Tank.GamePanel.KeyHome;



public class Panel extends JPanel{
	
	// constants
	public static final int FRAME = 800;
	private final ImageIcon startBackGround = new ImageIcon("./image/startbackground.png");
	private final ImageIcon gameoverBackGround = new ImageIcon();
	private final ImageIcon background = new ImageIcon();
	private final ImageIcon winBackGround = new ImageIcon();
	private final ImageIcon playerImage = new ImageIcon("./image/l0_Plane1.png");
	private final ImageIcon monsterImage = new ImageIcon("./image/l0_Plane3.png");
	private final ImageIcon playerBulletImage = new ImageIcon();
	private final ImageIcon monsterBulletImage = new ImageIcon();
	
	// fields
	private BufferedImage myImage;
	private Graphics2D myBuffer;
	
	private Timer brill; // use for word's color
	private Timer start;
	private Timer playing;
	private Timer gameOver;
	private Timer win;
	private Timer monsterY;
	int homeC = 0;
	
	private boolean changeStart = true;
	
	private int monsterCountX = 7;
	private int monsterCountY = 4;
	private int monsterUp = 0;
	private int monsterButton = 0;
	private int hits = 0;
	private int type = 0;
	private int player_controltype = 0;
	private int bulletCountPlayer = 0;
	private int bulletCountMax = 200;
	private int bulletCountMonster = 0;
	
	KeyHome Home = new KeyHome();
	KeyHelp Help = new KeyHelp();
	KeyGame Game = new KeyGame();
	
	private Airplane player;
	private Airplane[][] monster;
	private Bullet[] playerBullet;
	private Bullet[] monsterBullet;
	
	private AudioPlayer backgroundSound;
	
	public Panel() {
		
		myImage = new BufferedImage(FRAME, FRAME, BufferedImage.TYPE_INT_BGR);
		myBuffer = (Graphics2D)myImage.getGraphics();
		
		initialization();
		
		
		setFocusable(true);
		
		playing = new Timer(15, new Listener());
		win = new Timer(100, new ListenerWin());
		gameOver = new Timer(100, new ListenerOver());
		brill = new Timer(900, new ListenerBrill());
		start = new Timer(800, new ListenerStart());
		monsterY = new Timer(3000, new ListenerMonsterY());
		
//		File startSound = new File();
//		backgroundSound = new AudioPlayer(startSound);
//		backgroundSound .play();
		
		addKeyListener(Home);
		brill.start();
		start.start();

		
	}
	
	public void paintComponent(Graphics g) {
		g.drawImage(myImage, 0, 0, getWidth(), getHeight(), null);
	}
	
	/**
	 * 調整字體顏色
	 * @author hongminghong
	 *
	 */
	
	private class ListenerBrill implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			myBuffer.setColor(Color.red);
			myBuffer.setFont(new Font("Avenir", Font.BOLD, 80));
			myBuffer.drawString("Space", 270, 120);
			myBuffer.drawString("Monster", 200, 200);
			myBuffer.setFont(new Font("Arial Black", Font.BOLD, 40));
			
			repaint();
		}
	}
	
	/**
	 * 開始畫面
	 * @author hongminghong
	 *
	 */
	
	private class ListenerStart implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setFocusable(true);
			myBuffer.drawImage(background.getImage(), 0, 0, 1440, 900, null);
			myBuffer.setColor(Color.GRAY);
			myBuffer.fillRect(338, 170, 814, 100);
			myBuffer.fillRect(338, 440, 814, 100);
			myBuffer.setFont(new Font("Serif", Font.BOLD, 70));
			myBuffer.setColor(Color.BLACK);
			myBuffer.drawString("遊戲開始", 588, 230);
			myBuffer.drawString("遊戲說明", 588, 500);
			if (homeC == 0) {
				myBuffer.setColor(Color.YELLOW);
				myBuffer.setStroke(new BasicStroke(10.0f));
				myBuffer.drawRect(330, 160, 830, 120);
			}
			if (homeC == 1) {
				myBuffer.setColor(Color.YELLOW);
				myBuffer.setStroke(new BasicStroke(10.0f));
				myBuffer.drawRect(330, 430, 830, 120);

			}

			repaint();

		}
	}
	
	/**
	 * 主遊戲畫面
	 * @author hongminghong
	 *
	 */
	
	private class Listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			
			myBuffer.setColor(Color.black);
			myBuffer.fillRect(0, 0, FRAME, FRAME);
			
			//myBuffer.drawImage(background.getImage(), 0, 0, FRAME, FRAME, null);
			playerMove(); // 玩家移動
			monsterMove(); // 怪物移動
			playerBulletMove(); // 玩家子彈移動

			
			for(int i = 0; i < monsterCountX; i++) {
				for(int j = 0; j < monsterCountY; j++) {
					isPlayerBulletHitMonster(monster[i][j]); // 玩家子彈與怪物碰撞
					// 怪物是否發射子彈，並設定發射子彈位置
					if(isMonsterFire()) {
						monsterBullet[bulletCountMonster].setSeeable(false); // false會畫出來
						monsterBullet[bulletCountMonster].setX(monster[i][j].getX() + 10);
						monsterBullet[bulletCountMonster].setY(monster[i][j].getY() + 5);
						monsterBullet[bulletCountMonster].setBulletFly(type);
						 
						if(bulletCountMonster >= 20) {
							bulletCountMonster = 0;
						}
						bulletCountMonster++;
					}
					// 當玩家撞到怪物，停止音樂，改為死亡以及GameOver音樂
					if(BulletCollision.collide(player, monster[i][j])) {
//						backgroundSound.pause();
//						File soundFile = new File();
//						backgroundSound = new AudioPlayer(soundFile);
//						backgroundSound.setVolumn(3);
//						backgroundSound.play();
						playing.stop();
						monsterY.stop();
						gameOver.start();
					}
					monster[i][j].draw(myBuffer);
				}
				repaint();
			}
			for(int i = 0; i < bulletCountMax; i++) {
				playerBullet[i].draw(myBuffer);
				monsterBullet[i].draw(myBuffer);
				// 撞到子彈的話
				if(BulletCollision.collide(monsterBullet[i], player)) {
//					backgroundSound.pause();
//					File soundFile = new File();
//					backgroundSound = new AudioPlayer(soundFile);
//					backgroundSound.setVolumn(3);
//					backgroundSound.play();
					playing.stop();
					monsterY.stop();
					gameOver.start();
					
				}
				repaint();
			}
			// 達到一定分數就勝利
			if(hits >= (monsterCountX * monsterCountY)) {
//				background.pause();
//				File winSound = new File();
//				backgroundSound = new AudioPlayer(winSound);
//				backgroundSound.setVolumn(3);
//				backgroundSound.play();
				playing.stop();
				monsterY.stop();
				win.start();
				repaint();
			}
			// 往下到一定程度也輸
			if(monsterButton >= 250) {
//				background.pause();
//				File killSound = new File();
//				AudioPlayer killAudio = new AudioPlayer(killSound);
//				killAudio.setVolumn(9);
//				killAudio.play();
//				File soundFile = new File();
//				backgroundSound = new AudioPlayer(soundFile);
//				backgroundSound.setVolumn(3);
//				backgroundSound.play();
				playing.stop();
				monsterY.stop();
				gameOver.start();
				repaint();
				
			}
			player.draw(myBuffer);
			
			myBuffer.setColor(Color.white);
			myBuffer.setFont(new Font("Arial", Font.BOLD, 30));
			myBuffer.drawString("Source: " + hits, FRAME - 300, 25);
//			if(!backgroundSound.getStatus().equals(AudioPlayer.Status.START)) {
//				backgroundSound.play();
//			}
			repaint();
		}
	}
	
	/**
	 * 遊戲獲勝畫面
	 * @author hongminghong
	 *
	 */
	private class ListenerWin implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			initialization();
//			if(!backgroundSound.getStatus().equals(AudioPlayer.Status.START)) {
//				backgroundSound.play();
//			}
			myBuffer.drawImage(winBackGround.getImage(), 0, -50, FRAME, FRAME + 50, null);
			changeStart = true;
			myBuffer.setColor(Color.BLACK);
			myBuffer.setFont(new Font("Arial", Font.BOLD, 70));
			myBuffer.drawString("恭喜獲勝！", 100, 150);
			myBuffer.drawString("按Enter重新遊戲", 100, 500);
			
			repaint();
		}
	}
	
	/**
	 * 遊戲失敗畫面
	 * @author hongminghong
	 *
	 */

	private class ListenerOver implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			initialization();
//			if(!backgroundSound.getStatus().equals(AudioPlayer.Status.START)) {
//				backgroundSound.play();
//			}
			myBuffer.drawImage(gameoverBackGround.getImage(), 0, -50, FRAME, FRAME + 50, null);
			changeStart = true;
			myBuffer.setColor(Color.yellow);
			myBuffer.setFont(new Font("Arial", Font.BOLD, 40));
			myBuffer.drawString("按Enter重新遊戲", 100, 500);
			
			repaint();
		}
	}
	
	/**
	 * 怪物往下跑
	 * @author hongminghong
	 *
	 */
	
	private class ListenerMonsterY implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			monsterUp += 10;
			monsterButton += 10;
			repaint();
		}
	}
	
	// 控制飛機移動
	private void playerMove() {
		if(player_controltype == 1) {
			player.setY(player.getY() - 5);
			if(player.getY() <= 0) {
				player.setY(0);
			}
		}else if(player_controltype == 2) {
			player.setY(player.getY() + 5);
			if(player.getY() + player.getHeight() >= Panel.FRAME) {
				player.setY(Panel.FRAME - player.getHeight());
			}
		}else if(player_controltype == 3) {
			player.setX(player.getX() - 5);
			if(player.getX() <= 0) {
				player.setX(0);
			}
		}else if(player_controltype == 4) {
			player.setX(player.getX() + 5);
			if(player.getX() + player.getWidth() >= Panel.FRAME) {
				player.setX(Panel.FRAME - player.getWidth());
			}
		}
		
	}
	
	private void monsterMove() {
		for(int i = 0; i < monsterCountX; i++) {
			for(int j = 0; j < monsterCountY; j++) {
				if(!monster[i][j].getSeeable()) {
					if(type == 0) {
						monster[i][j].setX(monster[i][j].getX() + 1);
						if(monster[i][j].getX() == 650) {
							type++;
						}
					}else if(type == 1) {
						monster[i][j].setY(monster[i][j].getY() + 1);
						if(monster[i][j].getY() == 400 + monsterButton) {
							type++;
						}
					}else if(type == 2) {
						monster[i][j].setX(monster[i][j].getX() - 1);
						if(monster[i][j].getX() == 70) {
							type++;
						}
					}else if(type == 3) {
						monster[i][j].setY(monster[i][j].getY() - 1);
						if(monster[i][j].getY() == 70 + monsterUp) {
							type++;
						}
					}else {
						type = 0;
					}
				}
			}
		}
	}

	private void playerBulletMove() {
		for(int i = 0; i < bulletCountMax; i++) {
			playerBullet[i].move();
			playerBullet[i].move();
			monsterBullet[i].move();
			monsterBullet[i].move();
			// 控制子彈斜著飛
			if(playerBullet[i].getBulletFly() == 4) {
				playerBullet[i].setX(playerBullet[i].getX() + 1);
			}else if(playerBullet[i].getBulletFly() == 3) {
				playerBullet[i].setX(playerBullet[i].getX() - 1);
			}
			
			if(monsterBullet[i].getBulletFly() == 0) {
				monsterBullet[i].setX(monsterBullet[i].getX() + 1);
			}else if(monsterBullet[i].getBulletFly() == 2) {
				monsterBullet[i].setX(monsterBullet[i].getX() - 1);
			}
		}
	}
	
	private void initialization() {
		monsterUp = 0;
		monsterButton = 0;
		hits = 0;
		player = new Airplane(400, 700, 90, 70, playerImage);
		
		monster = new Airplane[monsterCountX][monsterCountY];
		playerBullet = new Bullet[bulletCountMax];
		monsterBullet = new Bullet[bulletCountMax];
		
		for(int i = 0; i < monsterCountX; i++) {
			for(int j = 0; j < monsterCountY; j++) {
				monster[i][j] = new Airplane(150 + 70 * i, 150 + 70 * j, 70, 70, monsterImage);
			}
		}
		
		for(int i = 0; i < bulletCountMax; i++) {
			playerBullet[i] = new Bullet(5000, 5000, 70, 90, playerBulletImage);
			playerBullet[i].setSeeable(true);
			
			monsterBullet[i] = new Bullet(5000, -5000, 70, 70, monsterBulletImage);
			monsterBullet[i].setDY(-1);
			monsterBullet[i].setSeeable(true);
			
		}
	}
	
	private void isPlayerBulletHitMonster(Airplane in) {
		for(int i = 0; i < bulletCountMax; i++) {
			if(BulletCollision.collide(playerBullet[i], in)) {
//				File killSound = new File();
//				AudioPlayer killAudio = new AudioPlayer(killSound);
//				killAudio.setVolumn(9);
//				killAudio.play();
				playerBullet[i].setSeeable(true);
				in.setSeeable(true);
				in.setX(9000);
				in.setY(9000);
				hits++;
			}
		}
	}
	
	private boolean isMonsterFire() {
		double random = Math.random();
		if(random < 0.001)
			return true;
		return false;
	}
	
	private class KeyHome extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				removeKeyListener(Home);
				addKeyListener(Game);
				start.stop();
				playing.start();
			}
			
			
		}
	}
	
	private class KeyGame extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player_controltype = 4;
			}else if(e.getKeyCode() == KeyEvent.VK_UP) {
				player_controltype = 1;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				player_controltype = 2;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				player_controltype = 3;
			}else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(!changeStart) {
					// File f = new File();
					// AudioPlayer fireSound = new AudioPlayer(f);
					// fireSound.setVolume(3);
					// fireSound.play();
					
					playerBullet[bulletCountPlayer].setSeeable(false);
					playerBullet[bulletCountPlayer].setX(player.getX() + player.getWidth() / 8);
					playerBullet[bulletCountPlayer].setY(player.getY());
					playerBullet[bulletCountPlayer].setBulletFly(player_controltype);
					bulletCountPlayer++;
					if(bulletCountPlayer >= bulletCountMax) {
						bulletCountPlayer = 0;
					}
				}
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) { // 切換音樂、遊戲畫面
				if(changeStart) {
					//backgroundSound.pause();
				}
			}
		}
	}
	
	private class KeyHelp extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
				player_controltype = 4;
			}else if(e.getKeyCode() == KeyEvent.VK_UP) {
				player_controltype = 1;
			}else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				player_controltype = 2;
			}else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
				player_controltype = 3;
			}else if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(!changeStart) {
					// File f = new File();
					// AudioPlayer fireSound = new AudioPlayer(f);
					// fireSound.setVolume(3);
					// fireSound.play();
					
					playerBullet[bulletCountPlayer].setSeeable(false);
					playerBullet[bulletCountPlayer].setX(player.getX() + player.getWidth() / 8);
					playerBullet[bulletCountPlayer].setY(player.getY());
					playerBullet[bulletCountPlayer].setBulletFly(player_controltype);
					bulletCountPlayer++;
					if(bulletCountPlayer >= bulletCountMax) {
						bulletCountPlayer = 0;
					}
				}
			}else if(e.getKeyCode() == KeyEvent.VK_ENTER) { // 切換音樂、遊戲畫面
				if(changeStart) {
					//backgroundSound.pause();
				}
			}
		}
	}
	
}
