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

import engine.graphics.Renderer;
import game.AGame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Matthew Day
 */
public class GameLoop implements Runnable {
	
	private int maxFPS;
	private double frameTime;
	
	private boolean running;
	
	private AGame game;
	private Renderer renderer;
	
	private Thread thread;
	
	public GameLoop(AGame game, Renderer renderer, int maxFPS) {
		
		this.game = game;
		this.renderer = renderer;
		
		this.maxFPS = maxFPS;
		this.frameTime = 1.0 / maxFPS;
		
		game.setLoop(this);
	}
	
	public void start() {
		
		if (!running) {
			
			running = true;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	@Override
	public void run() {
		
		game.initialize();
		
		boolean renderFrame = false;
		
		long lastTime = System.nanoTime();
		double unprocessedTime = 0;
		
		while (running) {
			
			long startTime = System.nanoTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / 1000000000D;
			
			while (unprocessedTime > frameTime) {
				
				renderFrame = true;
				
				unprocessedTime -= frameTime;
				
				game.update(frameTime);
			}
			
			if (renderFrame) {
				
				renderer.clear();
				game.render(renderer);
				renderer.render();
				
				renderFrame = false;
			} else {
				
				try {
					
					Thread.sleep(1);
				} catch (InterruptedException ex) {
					Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
				}
			}
		}
	}
	
	public void stop() {
		
		if (running) {
			
			try {
				
				thread.join();
			} catch (InterruptedException ex) {
				Logger.getLogger(GameLoop.class.getName()).log(Level.SEVERE, null, ex);
			}
			
			running = false;
		}
	}
}
