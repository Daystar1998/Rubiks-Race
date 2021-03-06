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
import java.util.HashMap;

/**
 * @author Matthew Day
 */
public class Model {
	
	private static HashMap<String, Model> models;
	
	static {
		
		models = new HashMap<String, Model>();
		initializeDefaultModels();
	}
	
	private ArrayList<Vector2f> vertices;
	private ArrayList<Integer> indices;
	
	public Model(String label) {
		
		Model model = models.get(label);
		
		/* TODO: Decide response for trying to use a nonexistent model
			a. Throw an exception
			b. Exit the program with error (current)
			c. Implement a default model
		 */
		
		if (model == null) {
			
			// TODO: Implement a standard logging system
			System.err.println("No model named '" + label + "' was found");
			System.exit(1);
		}
		
		this.vertices = model.vertices;
		this.indices = model.indices;
	}
	
	public Model(String label, ArrayList<Vector2f> vertices, ArrayList<Integer> indices) {
		
		this.vertices = vertices;
		this.indices = indices;
		
		models.put(label, this);
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
	
	private static void initializeDefaultModels() {
		
		ArrayList<Vector2f> vertices = new ArrayList<Vector2f>();
		
		vertices.add(new Vector2f(0, 0));
		vertices.add(new Vector2f(0, 1));
		vertices.add(new Vector2f(1, 1));
		vertices.add(new Vector2f(1, 0));
		
		ArrayList<Integer> indices = new ArrayList<Integer>();
		
		indices.add(0);
		indices.add(1);
		indices.add(2);
		indices.add(0);
		indices.add(2);
		indices.add(3);
		
		new Model("rectangle", vertices, indices);
	}
}
