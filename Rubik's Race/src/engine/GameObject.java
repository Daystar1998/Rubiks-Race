package engine;

import engine.graphics.Display;
import engine.math.Vector2f;
import java.util.ArrayList;

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
	private Vector2f scale;

	private GameObject parent = null;
	private ArrayList<GameObject> children;
	private ArrayList<GameObject> removedChildren;

	public GameObject(Vector2f position, Vector2f scale) {

		this.position = position;
		this.scale = scale;
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

	public void addChild(GameObject child) {

		child.setParent(this);

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

		this.position = position;
	}

	public Vector2f getPosition() {

		return position;
	}

	public Vector2f getScale() {

		return scale;
	}

	public void setWidth(Vector2f scale) {

		this.scale = scale;
	}

	public GameObject getParent() {

		return parent;
	}

	public void setParent(GameObject newParent) {

		parent = newParent;
	}
}
