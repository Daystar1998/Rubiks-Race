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
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * @author Matthew Day
 */
public class DisplayPanel extends JPanel implements IDisplay {

	private BufferedImage image;

	public DisplayPanel(int width, int height) {

		image = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
	}

	@Override
	public void clear() {

		Graphics g = image.getGraphics();
		g.clearRect(0, 0, image.getWidth(), image.getHeight());
	}

	@Override
	public void drawPixel(Vector2f position, Color color) {

	}

	@Override
	public void drawLine(Vector2f start, Vector2f end, Color color) {

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
