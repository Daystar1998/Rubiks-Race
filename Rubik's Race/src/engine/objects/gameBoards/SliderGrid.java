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
package engine.objects.gameBoards;

import engine.math.Vector2f;
import engine.objects.GameObject;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Matthew Day
 */
public class SliderGrid extends GridBoard {
	
	protected class MovingObject {
		
		public long startTime;
		public long totalTime;
		public Vector2f start;
		public Vector2f destination;
		public GameObject object;
		
		public MovingObject(GameObject object, Vector2f start, Vector2f destination, long startTime, long totalTimeMillis) {
			
			this.object = object;
			this.start = start;
			this.destination = destination;
			
			this.startTime = startTime;
			this.totalTime = totalTimeMillis;
		}
	}
	
	boolean processingMoves = false;
	
	protected ArrayList<MovingObject> movingCells;
	
	public SliderGrid(Vector2f position, Vector2f scale, int rows, int columns) {
		
		this(position, scale, rows, columns, 1);
	}
	
	public SliderGrid(Vector2f position, Vector2f scale, int rows, int columns, float lineWidth) {
		
		this(position, scale, rows, columns, lineWidth, new Color(0, 0, 0, 0));
	}
	
	public SliderGrid(Vector2f position, Vector2f scale, int rows, int columns, float lineWidth, Color backColor) {
		
		super(position, scale, rows, columns, lineWidth, backColor);
		
		this.movingCells = new ArrayList<MovingObject>();
	}
	
	@Override
	public void update(double frameTime) {
		
		ArrayList<MovingObject> completedMove = new ArrayList<MovingObject>();
		
		for (MovingObject move : movingCells) {
			
			grid.set((int) move.destination.getX() * super.getColumns() + (int) move.destination.getY(), move.object);
			
			long timeTaken = System.currentTimeMillis() - move.startTime;
			
			double percentageMove;
			
			if (move.totalTime > 0) {
				
				percentageMove = timeTaken / move.totalTime;
			} else {
				
				percentageMove = 1;
			}
			
			if (percentageMove > 1.0) {
				
				move.object.setPosition(super.getCellPositionAsAbsoluteCoordinate((int) move.destination.getX(), (int) move.destination.getY()).sub(this.getPosition()));
				completedMove.add(move);
			} else {
				
				Vector2f totalDistance = super.getCellPositionAsAbsoluteCoordinate((int) move.destination.getX(), (int) move.destination.getY()).sub(super.getCellPositionAsAbsoluteCoordinate((int) move.start.getX(), (int) move.start.getY()));
				
				Vector2f moveAmount = totalDistance.mul(new Vector2f((float) percentageMove, (float) percentageMove)).mul(new Vector2f((float) frameTime, (float) frameTime));
				
				move.object.move(moveAmount);
			}
		}
		
		for (MovingObject move : completedMove) {
			
			movingCells.remove(move);
		}
		
		if (movingCells.size() > 0) {
			
			processingMoves = true;
		} else {
			
			processingMoves = false;
		}
	}
	
	public void move(int row1, int column1, int row2, int column2, long totalTimeMillis) {
		
		long currentTime = System.currentTimeMillis();
		
		movingCells.add(new MovingObject(grid.get(row1 * super.getColumns() + column1), new Vector2f(row1, column1), new Vector2f(row2, column2), currentTime, totalTimeMillis));
	}
	
	public boolean isProcessingMoves() {
		
		return processingMoves;
	}
}
