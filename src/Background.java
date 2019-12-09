import edu.utc.game.*;

public class Background extends GameObject
	{	
		private Texture texture = null;
		private Sound sound = null;
		public boolean scroll = false;
		
		public Background(String skin)
		{  
			texture = new Texture(skin);
			
			this.hitbox.setSize(1920, 1080);
			this.hitbox.setLocation(0, 0);
			this.setColor(1,1,1);
		}
		
		public Background()
		{  			
			this.hitbox.setSize(1920, 1080);
			this.hitbox.setLocation(0, 0);
			this.setColor(1,1,1);
		}
		
		public void ChangeBG(String skin) {
			texture = new Texture(skin);
		}
		
		public void ChangeSound(String file){
			sound = new Sound(file);
			sound.play();
		}
		
		public void StartSound(){
			sound.play();
		}
		
		public void StopSound(){
			sound.stop();
		}
		
		// this allows you to steer the player object
		public void update(int delta)
		{

		}
		
		public void draw() { 
	    	texture.draw(this);
	    }
	}