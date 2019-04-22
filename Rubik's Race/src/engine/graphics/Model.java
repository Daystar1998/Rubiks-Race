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

package engine.graphics;

import engine.math.Vector2f;

import java.util.ArrayList;

/**
 * @author Matthew Day
 */
public class Model {
	
	private ArrayList<Vector2f> vertices;
	private ArrayList<Integer> indices;
	
	public Model(ArrayList<Vector2f> vertices, ArrayList<Integer> indices) {
		
		this.vertices = vertices;
		this.indices = indices;
	}
	
	public ArrayList<Vector2f> getVertices() {
		
		return vertices;
	}
	
	public void setVertices(ArrayList<Vector2f> vertices) {
		
		this.vertices = vertices;
	}
	
	public ArrayList<Integer> getIndices() {
		
		return indices;
	}
	
	public void setIndices(ArrayList<Integer> indices) {
		
		this.indices = indices;
	}
}
