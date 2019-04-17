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

package engine.graphics;

import engine.math.Vector2f;

/**
 * @author Matthew Day
 */

public abstract class ADisplay {

	private int width, height;

	public ADisplay(int width, int height) {
		
		this.width = width;
		this.height = height;
	}

	public abstract void clear();

	public abstract void drawPixel(Vector2f position, Color color);

	public abstract void drawLine(Vector2f start, Vector2f end, Color color);

	public abstract void drawTriangle(Vector2f position1, Vector2f position2, Vector2f position3, Color color);

	public abstract void render();

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
}
