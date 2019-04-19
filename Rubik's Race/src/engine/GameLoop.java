/*
 * Copyright 2019 Matthew Day.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package engine;

import engine.graphics.IDisplay;
import game.AGame;

/**
 * @author Matthew Day
 */

public class GameLoop implements Runnable {
		
	boolean running;

	private AGame game;
	private IDisplay display;

	public GameLoop(AGame game, IDisplay display) {
		
		this.game = game;
		this.display = display;
	}
	
	public void start(){
		
		if(!running){
			
			running = true;
			run();
		}
	}
	
	@Override
	public void run(){
		
		game.initialize();
		
		while(running){
			
			game.update();
			
			// TODO: Implement support for frame rate limiting
			display.clear();
			game.render(display);
			display.render();
		}
	}
	
	public void stop(){
		
		if(running){
			
			running = false;
		}
	}
}
