import edu.utc.game.*;
public class Actions {
	public Character player;
	public int option;
	public boolean bl;
	public enemy npc;
	public int ata;
	private static java.util.Random rand=new java.util.Random();
	public int killed;
	public Actions(Character player, enemy npc)
	{
		this.player = player;
		this.npc = npc;
	}
	public void nme(int respawn)
	{
		npc = new enemy(respawn);
		System.out.println("Respawn");
	}
	public int getcr()
	{
		return npc.getlvl();
	}
	public void act(int option)
	{
		if(option == 0)
		{
			ata = player.attack();
		}
		if(option == 1)
		{
			bl = true;
			player.heal((int) (0.05*player.gethp())+1);
		}
		if(option == 2)
		{
			Game.ui.destroy();
		}
		int crit = rand.nextInt((20-1)+1)+1;
		if(crit == 20)
		{
			npc.damage(ata*2);
			System.out.println("Critical hit");
		}
		if(crit == 1)
		{
			System.out.println("Miss");
		}
		else
		{
			npc.damage(ata);
		}
		if(npc.status() == true)
		{
			crit = rand.nextInt((20-1)+1)+1;
			int dam = npc.attack() - (int) (0.5*player.defend(bl));
			if(crit == 20)
			{
				System.out.println("Crit happens");
				player.damage(dam*2);
			}
			if(crit == 1)
			{
				System.out.println("They missed.");
			}
			else
			{
				player.damage(dam);
			}
		}
		if(player.status() == true && npc.status() == false)
		{
			int xp = npc.getlvl();
			player.gainxp(xp);
			nme(player.getlvl());
			killed++;
		}
		bl = false;
		ata = 0;
	}
	public int getKillCount()
	{
		return killed;
	}
}
