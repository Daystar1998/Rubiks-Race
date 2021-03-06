/*
 * Copyright 2019 Matthew Day.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package engine.objects;

import engine.math.Vector2f;
import java.util.ArrayList;
import engine.graphics.Renderer;

/**
 * @author Matthew Day
 */

public class GameObject {
	
	private static GameObject root = null;

	private Vector2f position;
	private Vector2f scale;

	private GameObject parent = null;
	private ArrayList<GameObject> children;
	private ArrayList<GameObject> removedChildren;

	public GameObject() {

		this(new Vector2f(), new Vector2f(1, 1));
	}

	public GameObject(Vector2f position, Vector2f scale) {

		this.children = new ArrayList<GameObject>();
		this.removedChildren = new ArrayList<GameObject>();
		
		this.position = position;
		this.scale = scale;
	}
	
	public synchronized void updateAll(double frameTime){
		
		update(frameTime);
		
		for (GameObject child : children) {

			child.updateAll(frameTime);
		}

		for (GameObject child : removedChildren) {

			children.remove(child);
		}

		removedChildren.clear();
	}

	public synchronized void update(double frameTime) {}

	public synchronized void renderAll(Renderer renderer) {
		
		render(renderer);

		for (GameObject child : children) {

			child.renderAll(renderer);
		}
	}

	public synchronized void render(Renderer renderer) {}

	public synchronized void addChild(GameObject child) {
        
        if(child.getParent() != null){
            
            child.getParent().removeChild(child);
        }
        
		child.setParent(this);

		children.add(child);
	}

	public synchronized void removeChild(GameObject child) {

		removedChildren.add(child);
	}
	
	public void move(Vector2f distance){
		
		position = position.add(distance);
	}

	public void setPosition(Vector2f position) {

		this.position = position;
	}

	public Vector2f getPosition() {
		
		Vector2f result = null;
		
		if(parent != null){
			
			// Get the child's position relative to the parent's position
			result = position.add(parent.getPosition());
		}else{
			
			result = new Vector2f(position);
		}
		
		return result;
	}

	public Vector2f getScale() {

		Vector2f result = null;
		
		if(parent != null){
			
			// Get the child's scale relative to the parent's scale
			result = scale.mul(parent.getScale());
		}else{
			
			result = new Vector2f(scale);
		}
		
		return result;
	}

	public void setScale(Vector2f scale) {

		this.scale = scale;
	}

	public GameObject getParent() {

		return parent;
	}

	public void setParent(GameObject newParent) {
  
		parent = newParent;
	}
	
	public static GameObject getRootObject(){
		
		if(root == null){
			
			root = new GameObject();
		}
		
		return root;
	}
}
