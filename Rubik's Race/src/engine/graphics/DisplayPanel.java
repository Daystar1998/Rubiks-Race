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

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import javax.swing.JPanel;

/**
 * @author Matthew Day
 */
public class DisplayPanel extends JPanel implements IDisplay {

	private BufferedImage image;
	private int[] pixels;

	public DisplayPanel(int width, int height) {

		super.setBackground(java.awt.Color.BLACK);

		image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	@Override
	public void clear() {

		Arrays.fill(pixels, 0);
	}

	@Override
	public void drawPixel(Vector2f position, Color color) {

		pixels[(int) position.y * image.getWidth() + (int) position.x] = color.getRGB();
	}

	@Override
	public void drawLine(Vector2f start, Vector2f end, Color color) {

		/*
			Author: Dana Steil
			Source: AddLine function in https://www.harding.edu/dsteil/345/assignments/picture.cpp

			Modified by Matthew Day to convert it to Java, use Vector2f, and use the drawPixel method
	 	*/

		Vector2f temp = new Vector2f();

		//y = mx + b
		//y - y0 = m( x - x0 )
		//m=(y-y0)/(x-x0)
		//x = (y-b)/m;
		if (start.getX() != end.getX()) {

			double m = (start.getY() - end.getY()) / (start.getX() - end.getX());
			double b = start.getY() - (m * start.getX());

			if ((int) Math.abs(start.getY() - end.getY()) < (int) Math.abs(start.getX() - end.getX())) {

				int x1 = ((int) start.getX() < (int) end.getX()) ? (int) start.getX() : (int) end.getX();
				int x2 = ((int) start.getX() < (int) end.getX()) ? (int) end.getX() : (int) start.getX();

				//change in x is greater than change in y
				for (temp.x = x1; temp.x <= x2; temp.x++) {

					temp.y = (int) Math.floor((m * temp.x) + b);
					drawPixel(temp, color);
				}
			} else {

				//change in y is greater than change in x
				int y1 = ((int) start.getY() < (int) end.getY()) ? (int) start.getY() : (int) end.getY();
				int y2 = ((int) start.getY() < (int) end.getY()) ? (int) end.getY() : (int) start.getY();

				//change in x is greater than change in y
				for (temp.y = y1; temp.y <= y2; temp.y++) {

					temp.x = (int) Math.floor((temp.y - b) / m);
					drawPixel(temp, color);
				}
			}
		} else {

			int y1 = ((int) start.getY() < (int) end.getY()) ? (int) start.getY() : (int) end.getY();
			int y2 = ((int) start.getY() < (int) end.getY()) ? (int) end.getY() : (int) start.getY();

			temp.x = start.getX();

			for (temp.y = y1; temp.y <= y2; temp.y++) {

				drawPixel(temp, color);
			}
		}
	}

	@Override
	public void drawTriangle(Vector2f position1, Vector2f position2, Vector2f position3, Color color) {

	}

	@Override
	public void render() {

		Graphics g = super.getGraphics();
		g.drawImage(image, 0, 0, null);
	}
}
