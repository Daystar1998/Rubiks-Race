/*
 * Copyright 2019 Matthew Day.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package game;

import engine.graphics.Model;
import engine.graphics.Renderer;
import engine.math.Vector2f;
import engine.objects.GameObject;
import engine.objects.entities.Entity;
import engine.objects.gameBoards.GridBoard;
import engine.objects.gameBoards.SliderGrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Matthew Day
 */
public class RubiksRaceGame extends AGame {
	
	private int gameBoardDimensions = 5;
	private int solutionDimensions = 3;
	
	private Entity emptyTile;
	private ArrayList<Entity> tiles = new ArrayList<Entity>();
	private ArrayList<Entity> solutionTiles = new ArrayList<Entity>();
	
	private JFrame window;
	private JPanel panel;
	private JMenuBar menuBar = new JMenuBar();
	private JMenu gameMenu;
	
	private ArrayList<Long> highscores = new ArrayList<Long>();
	
	private SliderGrid gameBoard;
	private GridBoard solution;
	
	static long startTime = 0;
	private long endTime = 0;
	
	public RubiksRaceGame(JFrame window, JPanel panel, int width, int height) {
		
		super(width, height);
		
		this.window = window;
		this.panel = panel;
	}
	
	@Override
	public void initialize() {
		
		window.setVisible(false);
		initializeMenu();
		window.remove(panel);
		window.setJMenuBar(menuBar);
		window.add(panel);
		window.setVisible(true);
		
		gameBoard = new SliderGrid(new Vector2f(0, 0), new Vector2f(500, 500), gameBoardDimensions, gameBoardDimensions, 2, Color.BLACK);
		GameObject.getRootObject().addChild(gameBoard);
		
		solution = new GridBoard(new Vector2f(520, 20), new Vector2f(260, 260), solutionDimensions, solutionDimensions, 2, Color.BLACK);
		GameObject.getRootObject().addChild(solution);
		
		Model tileModel = new Model("rectangle");
		
		// Initialize tiles
		for (int i = 0; i < 4; i++) {
			
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.red));
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.blue));
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.orange));
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.green));
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.cyan));
			tiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.magenta));
		}
		
		// Initialize solution pool
		// Must be separate to avoid taking the tiles from the gameboard
		for (int i = 0; i < 4; i++) {
			
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.red));
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.blue));
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.orange));
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.green));
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.cyan));
			solutionTiles.add(new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), Color.magenta));
		}
		
		// Add empty tile
		emptyTile = new Entity(tileModel, new Vector2f(0, 0), new Vector2f(1, 1), new Color(0, 0, 0, 0));
		tiles.add(emptyTile);
		
		Collections.shuffle(tiles);
		Collections.shuffle(solutionTiles);
		
		fillGrid(gameBoard, gameBoardDimensions, tiles);
		fillGrid(solution, solutionDimensions, solutionTiles);
		
		while (compareSolution(gameBoard, solution)) {
			
			Collections.shuffle(tiles);
			Collections.shuffle(solutionTiles);
			
			fillGrid(gameBoard, gameBoardDimensions, tiles);
			fillGrid(solution, solutionDimensions, solutionTiles);
		}
		
		MouseAdapter listener = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if (!gameBoard.isProcessingMoves()) {
					
					Vector2f mousePosition = new Vector2f(e.getX(), e.getY());
					
					mousePosition = mousePosition.sub(gameBoard.getPosition());
					mousePosition = mousePosition.div(gameBoard.getScale().div(new Vector2f(gameBoardDimensions, gameBoardDimensions)));
					
					int clickRow = (int) Math.floor(mousePosition.getY());
					int clickColumn = (int) Math.floor(mousePosition.getX());
					
					int moveTimeInMilliSeconds = 1000;
					
					for (int r = 0; r < gameBoard.getRows(); r++) {
						
						// Same column
						if (((Entity) gameBoard.getCell(r, clickColumn)).getColor().equals(emptyTile.getColor())) {
							
							if (r != clickRow) {
								
								gameBoard.move(r, clickColumn, clickRow, clickColumn, moveTimeInMilliSeconds);
							}
							
							if (r < clickRow) {
								
								for (int currentRow = clickRow; currentRow > r; currentRow--) {
									
									gameBoard.move(currentRow, clickColumn, currentRow - 1, clickColumn, moveTimeInMilliSeconds);
								}
							} else if (r > clickRow) {
								
								for (int currentRow = clickRow; currentRow < r; currentRow++) {
									
									gameBoard.move(currentRow, clickColumn, currentRow + 1, clickColumn, moveTimeInMilliSeconds);
								}
							}
						}
					}
					
					for (int c = 0; c < gameBoard.getColumns(); c++) {
						
						// Same row
						if (((Entity) gameBoard.getCell(clickRow, c)).getColor().equals(emptyTile.getColor())) {
							
							if (c != clickColumn) {
								
								gameBoard.move(clickRow, c, clickRow, clickColumn, moveTimeInMilliSeconds);
							}
							
							if (c < clickColumn) {
								
								for (int currentColumn = clickColumn; currentColumn > c; currentColumn--) {
									
									gameBoard.move(clickRow, currentColumn, clickRow, currentColumn - 1, moveTimeInMilliSeconds);
								}
							} else if (c > clickColumn) {
								
								for (int currentColumn = clickColumn; currentColumn < c; currentColumn++) {
									
									gameBoard.move(clickRow, currentColumn, clickRow, currentColumn + 1, moveTimeInMilliSeconds);
								}
							}
						}
					}
				}
			}
		};
		
		panel.addMouseListener(listener);
		window.addMouseListener(listener);
		
		startTime = System.currentTimeMillis() / 1000;
	}
	
	@Override
	public void update(double frameTime) {
		
		GameObject.getRootObject().updateAll(frameTime);
		
		if (gameBoard.isProcessingMoves() == false) {
			
			if (compareSolution(gameBoard, solution)) {
				
				// Teacher supplied code
				endTime = System.currentTimeMillis() / 1000;
				long timePlayed = endTime - startTime;
				highscores.add(timePlayed);
				Collections.sort(highscores);
				
				JOptionPane.showMessageDialog(null,
						"Memory Game Completed in " + timePlayed + " seconds",
						"Highscores", JOptionPane.PLAIN_MESSAGE);
				
				ObjectOutputStream oos = null;
				try {
					
					FileOutputStream fout = new FileOutputStream("highscores.ser");
					oos = new ObjectOutputStream(fout);
					oos.writeObject(highscores);
					oos.close();
				} catch (IOException ex) {
					
					ex.printStackTrace();
				}
				
				Collections.shuffle(tiles);
				fillGrid(gameBoard, gameBoardDimensions, tiles);
				
				Collections.shuffle(solutionTiles);
				fillGrid(solution, solutionDimensions, solutionTiles);
				
				while (compareSolution(gameBoard, solution)) {
					
					Collections.shuffle(tiles);
					Collections.shuffle(solutionTiles);
					
					fillGrid(gameBoard, gameBoardDimensions, tiles);
					fillGrid(solution, solutionDimensions, solutionTiles);
				}
				
				startTime = System.currentTimeMillis() / 1000;
			}
		}
	}
	
	@Override
	public void render(Renderer renderer) {
		
		GameObject.getRootObject().renderAll(renderer);
		renderer.render();
	}
	
	private void fillGrid(GridBoard grid, int dimensions, ArrayList<Entity> tiles) {
		
		for (int row = 0; row < dimensions; row++) {
			
			for (int column = 0; column < dimensions; column++) {
				
				grid.setCell(row, column, tiles.get(row * dimensions + column));
			}
		}
	}
	
	private boolean compareSolution(GridBoard gameBoard, GridBoard solution) {
		
		boolean result = true;
		
		for (int i = 0; i < 4; i++) {
			
			result = true;
			solution.rotate();
			
			solutionFailed:
			for (int row = 0; row < solutionDimensions; row++) {
				
				for (int column = 0; column < solutionDimensions; column++) {
					
					Entity gameBoardCell = (Entity) gameBoard.getCell(row + 1, column + 1);
					Entity solutionCell = (Entity) solution.getCell(row, column);
					
					if (!gameBoardCell.getColor().equals(solutionCell.getColor())) {
						
						result = false;
						
						break solutionFailed;
					}
				}
			}
			
			if (result == true) {
				
				break;
			}
		}
		
		return result;
	}
	
	private void initializeMenu() {
		
		// Teacher supplied code
		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startOver = new JMenuItem("Start Over");
		gameMenu.add(startOver);
		
		JMenuItem viewHighScores = new JMenuItem("View High Scores");
		gameMenu.add(viewHighScores);
		
		startOver.addActionListener(ae -> {
			
			Collections.shuffle(tiles);
			fillGrid(gameBoard, gameBoardDimensions, tiles);
			
			Collections.shuffle(solutionTiles);
			fillGrid(solution, solutionDimensions, solutionTiles);
			
			startTime = System.currentTimeMillis() / 1000;
		});
		
		viewHighScores.addActionListener(ae -> showHighScores());
	}
	
	private void showHighScores() {
		
		// Teacher supplied code
		File f = new File("highscores.ser");
		
		if (f.exists()) {
			
			//https://www.tutorialspoint.com/java/java_serialization.htm
			try {
				
				FileInputStream fileIn = new FileInputStream("highscores.ser");
				ObjectInputStream in = new ObjectInputStream(fileIn);
				highscores = (ArrayList<Long>) in.readObject();
				in.close();
				fileIn.close();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			} catch (ClassNotFoundException e) {
				
				e.getStackTrace();
			}
		}
		
		String scoreOutput = "";
		
		for (int c = 0; c < highscores.size(); c++) {
			
			scoreOutput = scoreOutput.concat((c + 1) + ". " + highscores.get(c) + "\n");
		}
		
		JOptionPane.showMessageDialog(null,
				scoreOutput, "Highscores", JOptionPane.PLAIN_MESSAGE);
	}
}
