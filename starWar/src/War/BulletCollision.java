package War;

public class BulletCollision {
	
	// 子彈怪物的碰撞
	public static boolean collide(Bullet bp, Airplane ap) {
		
		// 算X軸中心點
		int aveBIX = average(bp.getX(), bp.getX() + bp.getWidth());
		int aveINX = average(ap.getX(), ap.getX() + ap.getWidth());
		
		// 算Y軸中心點
		int aveBIY = average(bp.getY(), bp.getY() + bp.getHeight());
		int aveINY = average(ap.getY(), ap.getY() + ap.getHeight());
		
		// 兩者之間距離
		
		int absBIYINY = abs(aveBIY, aveINY);
		int avsBIXINX = abs(aveBIX, aveINX);
		
		if(absBIYINY <= 20 && avsBIXINX <= 35) {
			return true;
		}
		return false;
	}

	
	// 玩家怪物的碰撞
	public static boolean collide(Airplane play, Airplane ap) {
		
		// 算X軸中心點
		int aveBIX = average(play.getX(), play.getX() + play.getWidth());
		int aveINX = average(ap.getX(), ap.getX() + ap.getWidth());
		
		// 算Y軸中心點
		int aveBIY = average(play.getY(), play.getY() + play.getHeight());
		int aveINY = average(ap.getY(), ap.getY() + ap.getHeight());
		
		// 兩者之間距離
		
		int absBIYINY = abs(aveBIY, aveINY);
		int avsBIXINX = abs(aveBIX, aveINX);
		
		if(absBIYINY <= 20 && avsBIXINX <= 35) {
			return true;
		}
		return false;
	}
	
	
	public static int average( int a1, int a2) {
		return (a1 + a2) / 2;
	}
	
	public static int abs(int a1, int a2) {
		return Math.abs(a1 - a2);
	}

}
