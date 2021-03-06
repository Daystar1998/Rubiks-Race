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

package engine.math;

/**
 * @author Matthew Day
 */

public class Vector2f {

	public float x, y;

	public Vector2f() {

		this(0, 0);
	}

	public Vector2f(float x, float y) {

		this.x = x;
		this.y = y;
	}

	public Vector2f(Vector2f vector) {

		this(vector.x, vector.y);
	}
	
	public Vector2f add(Vector2f other){
		
		return new Vector2f(this.x + other.x, this.y + other.y);
	}
	
	public Vector2f sub(Vector2f other){
		
		return new Vector2f(this.x - other.x, this.y - other.y);
	}
	
	public Vector2f mul(Vector2f other){
		
		return new Vector2f(this.x * other.x, this.y * other.y);
	}
	
	public Vector2f div(Vector2f other){
		
		return new Vector2f(this.x / other.x, this.y / other.y);
	}

	public float getX() {
		
		return x;
	}

	public void setX(float x) {
		
		this.x = x;
	}

	public float getY() {
		
		return y;
	}

	public void setY(float y) {
		
		this.y = y;
	}
}
