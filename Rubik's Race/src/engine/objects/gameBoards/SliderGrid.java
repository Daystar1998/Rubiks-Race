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
}
