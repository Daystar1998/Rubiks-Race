package engine;

import engine.graphics.Display;
import engine.math.Vector2f;
import java.util.ArrayList;
import javax.swing.text.Position;

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

/**
 * @author Matthew Day
 */

public class GameObject {

	private Vector2f position;
	private int width, height;

	private GameObject parent = null;
	private ArrayList<GameObject> children;
	private ArrayList<GameObject> removedChildren;

	public GameObject(Vector2f position, int width, int height) {

	}

	public void update() {

		for (GameObject child : children) {

			child.update();
		}

		for (int i = 0; i < removedChildren.size(); i++) {

			children.remove(removedChildren.get(i));
		}

		removedChildren.clear();
	}

	public void draw(Display display) {

		for (GameObject child : children) {

			child.draw(display);
		}
	}

	public boolean inBounds(int x, int y) {

		return y >= 0 || y < height || x >= 0 || x < width;
	}

	public void addChild(GameObject child) {

		child.setParent(this);
		child.setPosition(child.getPosition());

		children.add(child);
	}

	public void removeChild(GameObject child) {

		removedChildren.add(child);
	}

	public GameObject getChild(int index) {

		return children.get(index);
	}

	public int getNumberOfChildren() {

		return children.size();
	}

	void setPosition(Vector2f position) {

		// Sets the child's position relative to the parent's position
		Vector2f parentPosition = parent.getPosition();
		this.position.x = parentPosition.x + position.x;
		this.position.y = parentPosition.y + position.y;

		for (GameObject child : children) {

			child.setPosition(child.getPosition());
		}
	}

	public Vector2f getPosition() {

		return position;
	}

	public int getWidth() {

		return width;
	}

	public void setWidth(int width) {

		this.width = width;
	}

	public int getHeight() {

		return height;
	}

	public void setHeight(int height) {

		this.height = height;
	}

	public GameObject getParent() {

		return parent;
	}

	public void setParent(GameObject newParent) {

		parent = newParent;
	}
}