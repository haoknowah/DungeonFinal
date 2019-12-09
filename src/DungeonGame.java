import edu.utc.game.*;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import java.util.LinkedList;
import java.awt.Rectangle;
public class DungeonGame extends Game implements Scene{
	
	public static void main(String[] args)
	{
		DungeonGame game = new DungeonGame();
		game.registerGlobalCallbacks();

		SimpleMenu menu = new SimpleMenu();
		menu.addItem(new SimpleMenu.SelectableText(20, 20, 20, 20, "Launch Game", 1, 0, 0, 1, 1, 1), game);
		menu.addItem(new SimpleMenu.SelectableText(20, 60, 20, 20, "Exit", 1, 0, 0, 1, 1, 1), null);
		menu.select(0);

		game.setScene(menu);
		game.gameLoop();
	}
	private LinkedList<Item> items;
	private int selected;
	private boolean go=false;
	public MenuArea zone;
	public Character player;
	public Actions act;
	public enemy npc;
	public Text health;
	public HealthBar hpbar;
	public Text level;
	public Text xp;
	public Text killed;
	public Text cr;
	public ExpBar xpbar;
	public BarBack hback;
	public BarBack xback;
	public Texture mob;
	public mobImg img;
	public DungeonGame()
	{
		initUI(640*2, 480*2, "Dungeon Game");
		glClearColor(.0f, .0f, .0f, .0f);
		player = new Character(5, 5, 5, 5, true);
		npc = new enemy(1);
		act = new Actions(player, npc);
		items=new LinkedList<>();
		selected=0;
		go=false;
		zone = new MenuArea();
		hpbar = new HealthBar();
		xpbar = new ExpBar();
		hback = new BarBack(hpbar);
		hback.setColors((float) 0, (float) 0.5, (float) 0);
		xback = new BarBack(xpbar);
		xback.setColors((float) 0, (float) 0, (float) 0.75);
		mob = new Texture("res/goblin.png");
		img = new mobImg();
		this.addItem(new SelectableText(20, 20, 20, 20, "Attack", 1, 0, 0, 1, 1, 1), 0);
		this.addItem(new SelectableText(20, 60, 20, 20, "Block", 1, 0, 0, 1, 1, 1), 1);
		this.addItem(new SelectableText(20, 100, 20, 20, "Quit", 1, 0, 0, 1, 1, 1), 2);
		this.select(0);
	}
	public class MenuArea extends GameObject
	{
		public MenuArea()
		{
			this.hitbox.setSize(100, 200);
			this.hitbox.setLocation(0, 0);
			this.setColor(0,0,0);
		}
	}
	public class HealthBar extends GameObject
	{
		public Rectangle healthbox;
		public HealthBar()
		{
			healthbox = new Rectangle();
			healthbox.setSize(150, 40);
			healthbox.setLocation(0, Game.ui.getHeight()-60);
		}
		public Rectangle getBox()
		{
			return healthbox;
		}
		public void update(int delta)
		{
			int left = (int) ((double) player.getchp() / (double) player.gethp() * 150);
			this.hitbox.setSize(left, 40);
			this.hitbox.setLocation(0, Game.ui.getHeight()-60);
			this.setColor(0, 1, 0);
		}
	}
	public class ExpBar extends GameObject
	{
		public Rectangle xpbox;
		public ExpBar()
		{
			xpbox = new Rectangle();
			xpbox.setSize(300, 40);
			xpbox.setLocation(Game.ui.getWidth()-300, Game.ui.getHeight()-60);
		}
		public Rectangle getBox()
		{
			return xpbox;
		}
		public void update(int delta)
		{
			int gain = (int) ((double) player.getxp() / (double) (player.getlvl()*100) * 300);
			this.hitbox.setSize(gain, 40);
			this.hitbox.setLocation(Game.ui.getWidth()-300, Game.ui.getHeight()-60);
			this.setColor((float) 0, (float) 0, (float) 1); 
		}
	}
	public class BarBack extends GameObject
	{
		public GameObject bar;
		public Rectangle hit;
		protected float r;
		protected float g;
		protected float b;
		public BarBack(HealthBar bar)
		{
			this.bar = bar;
			hit = bar.getBox();
			this.hitbox = hit;
		}
		public BarBack(ExpBar bar)
		{
			this.bar = bar;
			hit = bar.getBox();
			this.hitbox = hit;
		}
		protected void setColors(float r, float g, float b) 
		{
			this.setColor(r, g, b);
		}
		public void update(int delta)
		{

		}
	}
	public void nme(int respawn)
	{
		npc = new enemy(respawn);
	}
	public class mobImg extends GameObject
	{
		public mobImg()
		{
			this.hitbox.setSize((int) (600.0 * 736.0/811.0), (int) (600.0 * 811.0/736.0));
			float width = (float) this.hitbox.getWidth();
			float height = (float) this.hitbox.getHeight();
			this.hitbox.setLocation((int) (Game.ui.getWidth()/2 - width/2), (int) (Game.ui.getHeight()/2 - 
					height/2));
			this.setColor(1, 1, 1);
		}
		public void update(int delta)
		{
			
		}
	}
	public static interface SelectableObject
	{
		void select();
		void deselect();
		void update(int delta);
		void draw();
	}

	public static class SelectableText  extends Text implements SelectableObject
	{
		private float activeR, activeG, activeB;
		private float inactiveR, inactiveG, inactiveB;

		public SelectableText(int x, int y, int w, int h, String text, 
				float aR, float aG, float aB, float iR, float iG, float iB)
		{
			super(x,y,w,h,text);
			activeR=aR;
			activeG=aG;
			activeB=aB;
			inactiveR=iR;
			inactiveG=iG;
			inactiveB=iB;
		}

		public void select()
		{
			this.setColor(activeR, activeG, activeB);
		}

		public void deselect()
		{
			this.setColor(inactiveR, inactiveG, inactiveB);
		}


	}

	private class Item
	{
		public SelectableObject label;
		public int action;

		public Item(SelectableObject label, int action)
		{
			this.label=label;
			this.action=action;
		}

	}

	public void reset()
	{
		go=false;
		select(0);
	}

	public void addItem(SelectableObject label, int action)
	{
		items.add(new Item(label, action));
	}

	public void select(int p)
	{
		items.get(selected).label.deselect();
		items.get(p).label.select();
		selected=p;
	}

	public void go()
	{
		go=true;
	}
	
	public void onKeyEvent(int key, int scancode, int action, int mods)  
	{
		if (action==org.lwjgl.glfw.GLFW.GLFW_PRESS)
		{
			if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_UP)
			{
				select((selected+items.size()-1)%items.size());
			}
			else if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_DOWN)
			{
				select((selected+1)%items.size());
			}
			else if (key == org.lwjgl.glfw.GLFW.GLFW_KEY_ENTER)
			{
				go();
			}
		}
		
	};

	public Scene drawFrame(int delta) {
		glClearColor(1f, 1f, 1f, 1f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
		zone.draw();
		if (go) 
		{  
			if(items.get(selected).action == 2 || player.getchp() <= 0
					)
			{
				this.setScene(null);
				Game.ui.destroy();
				glfwWindowShouldClose(ui.getWindow());
			}
			else
			{
				act.act(items.get(selected).action);
				reset();
			}
		}

		for (Item item : items)
		{	
			item.label.update(delta);
			item.label.draw();
		}
		health = new Text(10, Game.ui.getHeight()-40, 20, 20, player.getchp() + "/" + player.gethp());
		if(player.getchp() <= (int) (0.25 * player.gethp())) 
		{
			health.setColor(1, 0, 0);
		}
		else
		{
			health.setColor(0, 0, 0);
		}
		xp = new Text(Game.ui.getWidth()-160, Game.ui.getHeight()-40, 20, 20, player.getxp() + "/" + 
		(100 * player.getlvl()));
		xp.setColor(0, 0, 0);
		level = new Text(Game.ui.getWidth()/2 -75, Game.ui.getHeight() - 40, 20, 20, "LVL " + player.getlvl());
		killed = new Text(Game.ui.getWidth()-100, 20, 20, 20, "Wins: " + act.getKillCount());
		killed.setColor(0, 0, 0);
		cr = new Text(Game.ui.getWidth()-100, 60, 20, 20, "CR " + act.getcr());
		cr.setColor(0, 0, 0);
		level.setColor(0,  0,  0);
		hpbar.update(delta);
		hback.draw();
		hpbar.draw();
		xpbar.update(delta);
		xback.draw();
		xpbar.draw();
		health.draw();
		xp.draw();
		level.draw();
		killed.draw();
		cr.draw();
		img.draw();
		img.update(delta);
		mob.draw(img);


		return this;
	}

}
