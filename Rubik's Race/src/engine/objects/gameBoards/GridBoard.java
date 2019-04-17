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

import engine.objects.GameObject;
import java.util.ArrayList;

/**
 * @author Matthew Day
 */

public class GridBoard extends GameBoard {

	private ArrayList<GameObject> grid;
	
	private int rows, columns;

	public GridBoard(int rows, int columns) {
		
		this(rows, columns, new ArrayList<GameObject>(rows * columns));
		
		// TODO: Should the grid be filled with GameObjects by default or left empty?
	}

	public GridBoard(int rows, int columns, ArrayList<GameObject> grid) {
		
		this.rows = rows;
		this.columns = columns;
		
		// TODO: Decide whether to throw an exception or to resize the grid if it is the wrong size. With resizing, would need to decide how to handle shrinking it
		this.grid = grid;
	}
	
	public GameObject getCell(int row, int column){
		
		if(row < 0 || row >= rows || column < 0 || column >= columns){
			
			throw new IndexOutOfBoundsException("Cell: " + row + ", " + column + " is out of bounds");
		}
		
		return grid.get(row * column);
	}
	
	public void setCell(int row, int column, GameObject object){
		
		if(row < 0 || row >= rows || column < 0 || column >= columns){
			
			throw new IndexOutOfBoundsException("Cell: " + row + ", " + column + " is out of bounds");
		}
		
		GameObject previousObject = grid.get(row * column);
		
		if(previousObject != null){
			
			this.removeChild(previousObject);
		}
		
		this.addChild(object);
		
		// TODO: Modify the child's position and scale to place it in its slot on the grid
		
		grid.set(row * column, object);
	}
}
