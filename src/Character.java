import edu.utc.game.*;

public class Character {
	public int mus;
	public int agi;
	public int mind;
	public int will;
	public int hp;
	public int chp;
	public int mp;
	public int cmp;
	public int level;
	public int ata;
	public int def;
	public int xp;
	public boolean player;
	public boolean alive;
	
	public Character(int str, int dex, int min, int wil, boolean play)
	{
		level = 1;
		mus = str;
		agi = dex;
		mind = min;
		will = wil;
		player = play;
		hp = gethp();
		mp = getmp();
		chp = hp;
		cmp = mp;
		xp = 0;
		alive = true;
		ata = level + mus/2 + (int) (agi*0.25);
		def = (int) (level*0.75) + (int) (mus*0.25) + (int) (agi*0.5);
	}
	public int gethp()
	{
		return 10+(2*level)+((mus/2)*level);
	}
	public int getchp()
	{
		return chp;
	}
	public int getmp()
	{
		return 5+level+((mind/2)*level);
	}
	public int getlvl()
	{
		return level;
	}
	public int getLevel()
	{
		return level;
	}
	public int getxp()
	{
		return xp;
	}
	public void heal(int yub)
	{
		if(chp+yub>hp)
		{
			chp = hp;
		}
		else
		{
			chp = chp + yub;
		}
	}
	public int attack()
	{
		System.out.println("yub attack");
		return ata;
	}
	public int defend(boolean block)
	{
		if(block == true)
		{
			return def*(int)1.5;
		}
		else
		{
			return def;
		}
	}
	public void damage(int dam)
	{
		if(chp > chp - dam)
		{
			chp = chp - dam;
		}
		if(chp <= 0)
		{
			alive = false;
		}
	}
	public void usemp(int used)
	{
		cmp = cmp - used;
	}
	public void levelup()
	{
		level++;
		hp = gethp();
		chp = hp;
		mp = getmp();
		cmp = mp;
		System.out.println("level up");
	}
	public boolean status()
	{
		return alive;
	}
	public void gainxp(int lev)
	{
		xp = xp + 25*lev;
		while(xp >= level*100)
		{
			xp = xp - (level*100);
			levelup();
		}
	}
}
