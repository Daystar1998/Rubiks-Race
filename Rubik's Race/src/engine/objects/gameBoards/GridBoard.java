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

import engine.graphics.Renderer;
import engine.math.Vector2f;
import engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Matthew Day
 */

public class GridBoard extends GameBoard {

	protected ArrayList<GameObject> grid;
	
	private int rows, columns;
	private Color backColor;
	
	public GridBoard(Vector2f position, Vector2f scale, int rows, int columns){
		
		this(position, scale, rows, columns, new Color(0, 0, 0, 0));
	}

	public GridBoard(Vector2f position, Vector2f scale, int rows, int columns, Color backColor) {

		super(position, scale);
		
		this.rows = rows;
		this.columns = columns;
		this.backColor = backColor;

		this.grid = new ArrayList<GameObject>(rows * columns);

		// TODO: Should the grid be filled with GameObjects by default or left empty?
	}
	
	@Override
	public void render(Renderer renderer) {
	
	}
	
	public GameObject getCell(int row, int column){
		
		if(row < 0 || row >= rows || column < 0 || column >= columns){
			
			throw new IndexOutOfBoundsException("Cell: " + row + ", " + column + " is out of bounds");
		}
		
		return grid.get(row * this.columns + column);
	}
	
	public void setCell(int row, int column, GameObject object){
		
		if(row < 0 || row >= rows || column < 0 || column >= columns){
			
			throw new IndexOutOfBoundsException("Cell: " + row + ", " + column + " is out of bounds");
		}
		
		GameObject previousObject = grid.get(row * this.columns + column);
		
		if(previousObject != null){
			
			this.removeChild(previousObject);
		}

		// TODO: Consider placing a GameObject in the grid with the new object as its child. This would avoid requiring changing the original's data
		
		this.addChild(object);
		
		grid.set(row * this.columns + column, object);
	}
}
