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

	private BufferedImage buffer1;
	private BufferedImage buffer2;
	private int[] pixels;
	
	private Color clearColor;

	public DisplayPanel(int width, int height, Color clearColor) {
		
		this.clearColor = clearColor;

		Dimension d = new Dimension(width, height);

		super.setPreferredSize(d);
		super.setMinimumSize(d);
		super.setMaximumSize(d);

		super.setBackground(clearColor);

		buffer1 = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		buffer2 = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) buffer1.getRaster().getDataBuffer()).getData();
	}

	@Override
	public void clear() {

		Arrays.fill(pixels, clearColor.getRGB());
	}

	@Override
	public void drawPixel(Vector2f position, Color color) {

		if (position.y >= 0 && position.y < buffer1.getHeight() && position.x >= 0 && position.x < buffer1.getWidth()) {

			// TODO: Support color blending
			if(color.getAlpha() != 0) {
				
				pixels[(int) position.y * buffer1.getWidth() + (int) position.x] = color.getRGB();
			}
		}
	}

	@Override
	public void drawLine(Vector2f start, Vector2f end, Color color) {

		/*
			Author: Dana Steil
			Source: AddLine function in https://www.harding.edu/dsteil/345/assignments/picture.cpp

			Modified by Matthew Day to convert it to Java, use Vector2f, and use the drawPixel method
	 	*/

		// TODO: Consider putting in class scope
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
		
		int minY = (int) Math.min(Math.min(position1.getY(), position2.getY()), position3.getY());
		int maxY = (int) Math.max(Math.max(position1.getY(), position2.getY()), position3.getY());
		
		// Ensure part of the triangle is on screen
		if(minY < getHeight() && maxY >= 0) {
			
			if(minY < 0){
				
				minY = 0;
			}
			
			if(maxY >= getHeight()){
				
				maxY = getHeight();
			}
			
			Vector2f temp = new Vector2f();
			
			int minX, maxX;
			
			for (int y = minY; y <= maxY; y++){
				
				boolean intersectsLine1 = false;
			
				if (lineIntersectsOnY(position1, position2, y, temp)) {
					
					intersectsLine1 = true;
				} else {
					
					lineIntersectsOnY(position2, position3, y, temp);
				}
				
				minX = (int) temp.getX();
				
				if (temp.getY() >= 0){
					
					maxX = (int) temp.getY();
				} else {
					
					maxX = (int) temp.getX();
				}
				
				if (intersectsLine1){
					
					lineIntersectsOnY(position1, position3, y, temp);
					
					// Temp will not have changed if it didn't intersect
					minX = (int) Math.min(temp.getX(), minX);
					
					if (temp.getY() >= 0){
						
						maxX = (int) Math.max(temp.getY(), maxX);
					} else {
						
						maxX = (int) Math.max(temp.getX(), maxX);
					}
					
					lineIntersectsOnY(position2, position3, y, temp);
				} else{
					
					lineIntersectsOnY(position1, position3, y, temp);
				}
				
				minX = (int) Math.min(temp.getX(), minX);
				
				if (temp.getY() >= 0){
					
					maxX = (int) Math.max(temp.getY(), maxX);
				} else {
					
					maxX = (int) Math.max(temp.getX(), maxX);
				}
				
				drawLine(new Vector2f(minX, y), new Vector2f(maxX, y), color);
			}
		}
	}
	
	private boolean lineIntersectsOnY(Vector2f lineStart, Vector2f lineEnd, int y, Vector2f oIntersection) {
		
		boolean result = false;
		
		if((y < (int) lineStart.y && y < (int) lineEnd.y) || (y > (int) lineEnd.y && y > (int) lineStart.y)) {
			
			// Do nothing
			// Line is horizontal
		}else if ((int) lineStart.y == (int) lineEnd.y && (int) lineStart.y == y) {
			
			oIntersection.x = Math.min(lineStart.x, lineEnd.x);
			oIntersection.y = Math.max(lineStart.x, lineEnd.x);
			
			result = true;
		} else {
			
			// Flag showing that this value should not be used
			oIntersection.y = -1;
			
			double m;
			double b;
			double x;
			
			// Line is vertical
			if (lineStart.x == lineEnd.x) {
				
				x = lineStart.x;
			} else {
				
				m = (double) (lineEnd.y - lineStart.y) / (double) (lineEnd.x - lineStart.x);
				
				b = lineStart.y - (lineStart.x * m);
				
				x = Math.round((y - b) / m);
			}
			
			// Check that the x value is in range regardless of the order the points of the line are in
			if ((x >= lineStart.x && x <= lineEnd.x) || (x >= lineEnd.x && x <= lineStart.x)) {
				
				oIntersection.x = (int) Math.floor(x);
				
				result = true;
			}
		}
		
		return result;
	}

	@Override
	public void render() {

		Graphics g = buffer2.getGraphics();
		g.drawImage(buffer1, 0, 0, null);
	}
	
	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		g.drawImage(buffer2, 0, 0, null);
		
		repaint();
	}

	public Color getClearColor() {
		
		return clearColor;
	}

	public void setClearColor(Color clearColor) {
		
		this.clearColor = clearColor;
	}
}
