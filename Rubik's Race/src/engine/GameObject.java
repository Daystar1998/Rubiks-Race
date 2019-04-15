package engine;

import engine.graphics.Display;
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

	public class Position {

		int x, y;

		Position(int x, int y) {

			this.x = x;
			this.y = y;
		}
	}

	protected enum Status {

		RUNNING,
		FAILURE,
		FOCUSED,
		SUCCESS
	};

	private Position position;
	private int width, height;

	private Status currentStatus = Status.RUNNING;

	private GameObject parent  = null;
	private ArrayList<GameObject> children;
	private ArrayList<GameObject> removedChildren;

	private ArrayList<Integer> data;

	public GameObject(Position position, int width, int height) {

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

	public Status getStatus() {

		return currentStatus;
	}

	public void setStatus(Status status) {

		currentStatus = status;
	}

	void setPosition(Position position) {

		// Sets the child's position relative to the parent's position
		Position parentPosition = parent.getPosition();
		this.position.x = parentPosition.x + position.x;
		this.position.y = parentPosition.y + position.y;

		for (GameObject child : children) {

			child.setPosition(child.getPosition());
		}
	}

	public Position getPosition() {

		return position;
	}

	public int getWidth() {

		return width;
	}

	public void setWidth(int width) {

		this.width = width;

		data.clear();
		data.ensureCapacity(width * height);
	}

	public int getHeight() {

		return height;
	}

	public void setHeight(int height) {

		this.height = height;

		data.clear();
		data.ensureCapacity(width * height);
	}

	public int getData(int x, int y) throws Exception {

		if (!inBounds(x, y)) {
			
			throw new Exception("Error: Position " + x + ", " + y + " is out of bounds");
		}

		return data.get(y * width + x);
	}

	public void setData(int x, int y, int newData) throws Exception {

		if (!inBounds(x, y)) {

			throw new Exception("Error: Position " + x + ", " + y + " is out of bounds");
		}

		data.set(y * width + x, newData);
	}

	public GameObject getParent() {

		return parent;
	}

	public void setParent(GameObject newParent) {

		parent = newParent;
	}

}
