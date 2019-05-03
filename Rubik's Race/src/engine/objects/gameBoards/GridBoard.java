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

import engine.graphics.Model;
import engine.graphics.Renderer;
import engine.math.Vector2f;
import engine.objects.GameObject;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author Matthew Day
 */

public class GridBoard extends GameBoard {
	
	private static Model backModel;
	
	protected ArrayList<GameObject> grid;
	
	private int rows, columns;
	private float lineWidth;
	
	private Color backColor;
	
	public GridBoard(Vector2f position, Vector2f scale, int rows, int columns){
		
		this(position, scale, rows, columns, 1);
	}
	
	public GridBoard(Vector2f position, Vector2f scale, int rows, int columns, float lineWidth){
		
		this(position, scale, rows, columns, lineWidth, new Color(0, 0, 0, 0));
	}

	public GridBoard(Vector2f position, Vector2f scale, int rows, int columns, float lineWidth, Color backColor) {

		super(position, scale);

		if(backModel == null){
			
			backModel = new Model("rectangle");
		}
		
		this.rows = rows;
		this.columns = columns;
		this.lineWidth = lineWidth;
		
		this.backColor = backColor;

		this.grid = new ArrayList<GameObject>(rows * columns);
		
		for(int i = 0; i < rows * columns; i++){
			
			grid.add(new GameObject());
		}
	}
	
	@Override
	public void render(Renderer renderer) {
	
		if(backColor.getAlpha() != 0){
			
			renderer.render(backModel, this.getPosition(), this.getScale(), backColor);
		}
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
		
		if(previousObject != null && previousObject != object){
			
			this.removeChild(previousObject);
			
			if(object != null){
				
				this.addChild(object);
			}
		}
		
		if (object != null) {

			Vector2f scale = this.getScale();

			float width = scale.x / columns;
			float height = scale.y / rows;

			object.setPosition(new Vector2f(width * column + (lineWidth / 2f), height * row + (lineWidth / 2f)));
			object.setScale(new Vector2f((width - lineWidth) / scale.x, (height - lineWidth) / scale.y));
		}

		grid.set(row * this.columns + column, object);
	}
	
	public void rotate() {
		
		GameObject[] rotatedGrid = new GameObject[this.getRows() * this.getColumns()];
	
		for(int row = 0; row < this.getRows(); row++){
			
			for(int column = 0; column < this.getColumns(); column++){
			
				GameObject object = this.getCell(row, column);
				
				object.setPosition(getCellPositionAsAbsoluteCoordinate(column, this.getColumns() - row - 1).sub(this.getPosition()));
				
				rotatedGrid[column * this.getColumns() + (this.getColumns() - row - 1)] = object;
			}
		}
		
		for(int row = 0; row < this.getRows(); row++){
			
			for(int column = 0; column < this.getColumns(); column++){
				
				grid.set(row * this.getColumns() + column, rotatedGrid[row * this.getColumns() + column]);
			}
		}
	}
	
	public Vector2f getCellPositionAsAbsoluteCoordinate(int row, int column){
		
		Vector2f positionWithinGrid = this.getScale().div(new Vector2f(columns, rows)).mul(new Vector2f(column, row));
		
		return this.getPosition().add(positionWithinGrid);
	}
	
	public Vector2f getAbsoluteCoordinateAsCellPosition(Vector2f position){
		
		position = position.sub(this.getPosition());
		
		Vector2f scale = this.getScale();

		float width = scale.x / columns;
		float height = scale.y / rows;
		
		int rowCount;
		int columnCount;
			
		for (rowCount = 0; (height * (rowCount + 1)) < position.getY(); rowCount++);
		for (columnCount = 0; (width * (columnCount + 1)) < position.getX(); columnCount++);
		
		return new Vector2f(rowCount, columnCount);
	}
	
	public int getRows(){
		
		return rows;
	}
	
	public int getColumns(){
		
		return columns;
	}
}
