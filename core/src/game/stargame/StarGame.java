package game.stargame;

import com.badlogic.gdx.Game;


import game.stargame.screen.MenuScreen;

public class StarGame extends Game {

	
	@Override
	public void create () {
		setScreen(new MenuScreen());
	}

}
