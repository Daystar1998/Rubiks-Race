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

import java.awt.Color;
import javax.swing.*;

/**
 * @author Matthew Day
 */
public class Window extends JFrame {

	public Window(String title, int width, int height, JPanel panel) {

		super(title);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		super.add(panel);
		super.pack();
		super.setLocationRelativeTo(null);
		super.setResizable(false);

		super.setBackground(new Color(0, 0, 0));
		super.setVisible(true);
	}
}
