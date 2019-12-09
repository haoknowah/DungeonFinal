public class enemy {
	public int hp;
	public int chp;
	public int ata;
	public int level;
	public boolean alive;
	public int cr;
	private static java.util.Random rand=new java.util.Random();
	public enemy(int level)
	{
		alive = true;
		this.level = level;
		cr = rand.nextInt(((level+2)-(level-2))+1)+level-2;
		gen(cr);
	}
	public void gen(int lev)
	{
		if(lev < 1)
		{
			lev = 1;
			cr = 1;
		}
		hp = 10 + (int) 2.5 * lev;
		chp = hp;
		ata = (int) 1.5 * lev;
	}
	public int getlvl()
	{
		return cr;
	}
	public int gethp()
	{
		return hp;
	}
	public int getchp()
	{
		return chp;
	}
	public void damage(int dam)
	{
		chp = chp - dam;
		if(chp <= 0)
		{
			alive = false;
		}
	}
	public boolean status()
	{
		return alive;
	}
	public int attack()
	{
		return ata;
	}
}
