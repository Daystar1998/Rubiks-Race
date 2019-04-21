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

/**
 * @author Matthew Day
 */

public class Color {

	private char red, green, blue;

	public Color(char red, char green, char blue) {

		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public char getRed() {

		return red;
	}

	public void setRed(char red) {

		this.red = red;
	}

	public char getGreen() {

		return green;
	}

	public void setGreen(char green) {

		this.green = green;
	}

	public char getBlue() {

		return blue;
	}

	public void setBlue(char blue) {

		this.blue = blue;
	}
}
