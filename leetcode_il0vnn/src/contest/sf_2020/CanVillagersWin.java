package contest.sf_2020;

import java.util.ArrayList;
import java.util.List;

public class CanVillagersWin {

	public static int DEF_UNKNOWN = 0;
	public static int DEF_WW = 1;
	public static int DEF_HUNTER = 2;
	public static int DEF_IDIOT = 3;
	public static int DEF_BEAR = 4;
	public static int DEF_GOODMAN = 5;

	public static int DEF_LIVE = 0;
	public static int DEF_DEAD = 1;

	public static int DEF_ALL_NUM = 12;

	public static int DEF_CONTINUE = 3;
	public static int DEF_WW_WIN = 1;
	public static int DEF_VIL_WIN = 2;

	public static interface Sprit {
		int killAtNight(int[] credibility, int[] lived, int[] roles, int[] wolfs);// ww
		// int bearAloud(int[] lived,int[] roles); jurge说的 咆哮

		void morning(int[] credibility, int[] lived, int[] roles); // bear

		void deadSkill(int[] credibility, int[] lived, int[] roles); // idiot
																		// hunter

		void speak(int[] credibility, int[] lived, int[] roles); // bear
		// think 也可以交给jurge

		int vote(int[] credibility, int[] lived, int[] roles);

		void imGood(int[] credibility, int[] lived, int[] roles);
	}

	public static abstract class Thing implements Sprit {
		int idx;
		List<Integer[]> cups = new ArrayList<>();

		public Thing(int idx) {
			this.idx = idx;
		}

		@Override
		public int killAtNight(int[] credibility, int[] lived, int[] roles, int[] wolfs) {
			return -1;
		}

		@Override
		public void morning(int[] credibility, int[] lived, int[] roles) {
		}

		@Override
		public void deadSkill(int[] credibility, int[] lived, int[] roles) {
		}

		@Override
		public void speak(int[] credibility, int[] lived, int[] roles) {
		}

		@Override
		public int vote(int[] credibility, int[] lived, int[] roles) {
			// 杀低信用的活人
			int kill = -1;
			int cred = Integer.MAX_VALUE;
			for (int i = 0; i < lived.length; i++) {
				if (DEF_LIVE == lived[i]) {
					if (cred > credibility[i]) {
						cred = credibility[i];
						kill = i;
					}
				}
			}
			System.out.println("Hunter kill " + kill);
			lived[kill] = DEF_DEAD;

			// 整理cups
			List<Integer> rms = new ArrayList<>();
			int i = 0;
			for (Integer[] cup : cups) {
				if (cup[0] == kill || cup[1] == kill) {
					rms.add(i);
				}
				i++;
			}
			for (int j : rms) {
				cups.remove(j);
			}

			return kill;
		}

		@Override
		public void imGood(int[] credibility, int[] lived, int[] roles) {
			credibility[idx] = 100;

			// 猜测铁狼
			List<Integer> rms = new ArrayList<>();
			int i = 0;
			for (Integer[] cup : cups) {
				if (cup[0] == idx || cup[1] == idx) {
					credibility[cup[0] == idx ? cup[1] : cup[0]] = 0;
					roles[cup[0] == idx ? cup[1] : cup[0]] = DEF_WW;
					rms.add(i);
				}
				i++;
			}

			for (int j : rms) {
				cups.remove(j);
			}
		}

	}

	public static class Vil extends Thing {

		public Vil(int idx) {
			super(idx);
		}

	}

	public static class Ww extends Thing {
		public Ww(int idx) {
			super(idx);
		}

		@Override
		public int killAtNight(int[] credibility, int[] lived, int[] roles, int[] wolfs) {
			// 找熊
			for (int i = 0; i < lived.length; i++) {
				if (DEF_LIVE == lived[i] && roles[i] == DEF_BEAR) {
					return i;
				}
			}

			// 杀高信用村名
			int kill = -1;
			int cred = 0;
			for (int i = 0; i < lived.length; i++) {
				if (DEF_LIVE == lived[i] && roles[i] == DEF_UNKNOWN && wolfs[i] != DEF_WW) {
					if (credibility[i] > cred) {
						cred = credibility[i];
						kill = i;
					}
				}
			}

			System.out.println("ww want to kill " + kill);
			lived[kill] = DEF_DEAD;
			return kill;
		}
	}

	public static class Hunter extends Thing {
		public Hunter(int idx) {
			super(idx);
		}

		@Override
		public void deadSkill(int[] credibility, int[] lived, int[] roles) {
			// 亮牌
			roles[idx] = DEF_HUNTER;
			imGood(credibility, lived, roles);
			;
			vote(credibility, lived, roles);
		}
	}

	public static class Idiot extends Thing {
		public Idiot(int idx) {
			super(idx);
		}

		@Override
		public void deadSkill(int[] credibility, int[] lived, int[] roles) {
			// 救活自己
			lived[idx] = DEF_LIVE;
			// 亮牌
			roles[idx] = DEF_IDIOT;
			imGood(credibility, lived, roles);
		}
	}

	public static class Bear extends Thing {
		public Bear(int idx) {
			super(idx);
		}

		int[][] cached = new int[2][3];

		@Override
		public void speak(int[] credibility, int[] lived, int[] roles) {
			// 发言，亮身份
			roles[idx] = DEF_BEAR;
			imGood(credibility, lived, roles);
			updateCache(credibility, lived, roles);
		}

		private void updateCache(int[] credibility, int[] lived, int[] roles) {
			if (cached[0][0] >= 0) {
				// 之前的咆哮可以发出去了
				credibility[cached[0][0]] = cached[0][1];
				credibility[cached[1][0]] = cached[1][1];
				if (cached[0][2] >= 0)
					roles[cached[0][0]] = cached[0][2];
				if (cached[1][2] >= 0)
					roles[cached[1][0]] = cached[1][2];

				cached[0][0] = -1;
			}
		}

		@Override
		public void morning(int[] credibility, int[] lived, int[] roles) {

			int l = idx - 1;
			int r = idx + 1;

			while (lived[(l + DEF_ALL_NUM) % DEF_ALL_NUM] == DEF_DEAD) {
				l--;
			}

			while (lived[(r + DEF_ALL_NUM) % DEF_ALL_NUM] == DEF_DEAD) {
				r++;
			}

			boolean lGood = isGood(roles[l]);
			boolean rGood = isGood(roles[r]);

			cached[0][0] = l;
			cached[1][0] = r;

			// 不会有2个都是铁狼，现有好人，再有铁狼，那么铁狼会被杀
			if (lGood && rGood) {

			} else if (lGood || rGood) {
				// 铁狼
				cached[0][0] = lGood ? r : l;
				cached[0][1] = 0;
				cached[0][2] = DEF_WW;
			} else {
				// 各少一半
				cached[0][0] = l;
				cached[0][1] = credibility[l] == 1 ? 1 : credibility[l] / 2;
				cached[0][2] = -1;
				cached[1][0] = r;
				cached[1][1] = credibility[r] == 1 ? 1 : credibility[r] / 2;
				cached[1][2] = -1;

				cups.add(new Integer[] { l, r });

			}

			// 量过身份了
			if (roles[idx] == DEF_BEAR) {
				updateCache(credibility, lived, roles);
			} else {
				// 存着下次咆哮让大家知道
			}
		}
	}

	public static boolean isGood(int role) {
		return role == DEF_BEAR || role == DEF_GOODMAN || role == DEF_HUNTER || role == DEF_IDIOT;
	}

	public static boolean isBad(int role) {
		return role == DEF_WW;
	}

	public static int checkGame(Sprit[] sprits, int[] lived) {
		int wwnum = 0;
		int vvnum = 0;
		for (int i = 0; i < lived.length; i++) {
			if (lived[i] == DEF_LIVE) {
				if (sprits[i] instanceof Ww) {
					wwnum++;
				} else if (sprits[i] instanceof Vil) {
					vvnum++;
				}
			}
		}
		if (wwnum > vvnum) {
			return DEF_WW_WIN;
		}
		if (wwnum == 0)
			return DEF_VIL_WIN;
		return DEF_CONTINUE;
	}

	public static Sprit factory(int idx, String name) {
		if ("vil".equals(name)) {
			return new Vil(idx);
		} else if ("ww".equals(name)) {
			return new Ww(idx);
		} else if ("bear".equals(name)) {
			return new Bear(idx);
		} else if ("hunter".equals(name)) {
			return new Hunter(idx);
		} else if ("idiot".equals(name)) {
			return new Idiot(idx);
		}

		throw new RuntimeException("not known" + name);

	}

	public boolean canVillagersWin(String[] players, int[] credibility) {
		// 初始化
		int[] roles = new int[credibility.length];
		int[] lived = new int[credibility.length];
		Sprit[] sprits = new Sprit[credibility.length];
		for (int i = 0; i < credibility.length; i++) {
			sprits[i] = factory(i, players[i]);
		}
		// 4个狼找出来
		int bear = 0;
		int[] wolfs = new int[credibility.length];
		for (int i = 0; i < credibility.length; i++) {
			if (sprits[i] instanceof Ww) {
				wolfs[i] = DEF_WW;
			}
			if (sprits[i] instanceof Bear) {
				bear = i;
			}
		}

		// 游戏开始
		int rst = -1;
		while (true) {
			rst = run(credibility, lived, roles, sprits, wolfs, bear);
			if (rst != DEF_VIL_WIN) {
				break;
			}
		}
		return rst == DEF_VIL_WIN;

	}

	private int run(int[] credibility, int[] lived, int[] roles, Sprit[] sprits, int[] wolfs, int bear) {
		// 1第一个狼人杀人
		int kill = 0;
		for (int i = 0; i < lived.length; i++) {
			if (lived[i] == DEF_LIVE && sprits[i] instanceof Ww) {
				kill = sprits[i].killAtNight(credibility, lived, roles, wolfs);
				break;
			}
		}

		// 咆哮
		if (kill != bear) {
			sprits[bear].morning(credibility, lived, roles);
		}

		// 技能
		sprits[kill].deadSkill(credibility, lived, roles);

		// 检查
		int rst = checkGame(sprits, lived);
		if (rst != DEF_CONTINUE)
			return rst;

		// 投票
		int kill2 = -1;
		for (int i = 0; i < lived.length; i++) {
			if (lived[i] == DEF_LIVE) {
				kill2 = sprits[i].vote(credibility, lived, roles);
				break;
			}
		}

		// 技能
		sprits[kill2].deadSkill(credibility, lived, roles);

		// 检查
		int rst2 = checkGame(sprits, lived);
		if (rst2 != DEF_CONTINUE)
			return rst2;

		return DEF_CONTINUE;
	}

	public static void main(String[] args) {
		CanVillagersWin m = new CanVillagersWin();

		System.out.println(m.canVillagersWin(
				new String[] { "bear", "vil", "vil", "ww", "vil", "vil", "idiot", "ww", "hunter", "ww", "ww", "vil" },
				new int[] { 9, 55, 62, 74, 43, 70, 13, 23, 15, 78, 61, 66 }));
		
		System.out.println(m.canVillagersWin(
				new String[] { "vil", "vil", "vil", "ww", "vil", "ww", "ww", "vil", "ww", "bear", "hunter", "idiot" },
				new int[] { 81, 71, 88, 31, 34, 40, 70, 94, 73, 79, 98, 48 }));
		
		System.out.println(m.canVillagersWin(
				new String[] { "vil","ww","bear","hunter","ww","idiot","vil","vil","ww","vil","ww","vil"},
				new int[] {45,67,32,25,1,27,99,85,3,54,3,25 }));
		
		
	}

}
