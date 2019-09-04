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
package game;

import engine.GameLoop;
import engine.graphics.DisplayPanel;
import engine.graphics.Renderer;
import engine.graphics.Window;

import java.awt.Color;

/**
 * @author Matthew Day
 */
public class Main {
	
	public static void main(String[] args) {
		
		int width = 800;
		int height = 600;
		
		DisplayPanel panel = new DisplayPanel(width, height, Color.gray);
		Window window = new Window("Rubik's Race", width, height, panel);
		
		Renderer renderer = new Renderer(panel);
		
		GameLoop loop = new GameLoop(new RubiksRaceGame(window, panel, width, height), renderer, 60);
		
		loop.start();
	}
}
