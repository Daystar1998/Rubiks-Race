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
import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Matthew Day
 */
public class Renderer {

	private IDisplay display;

	public Renderer(IDisplay display) {

		this.display = display;
	}
	
	public void clear(){
		
		display.clear();
	}

	public void render(Model model, Vector2f position, Vector2f scale, Color color) {
		
		ArrayList<Vector2f> vertices = model.getVertices();
		ArrayList<Integer> indices = model.getIndices();
		
		for(int i = 0; i < model.getIndices().size(); i += 3){
			
			Vector2f position1 = vertices.get(indices.get(i));
			Vector2f position2 = vertices.get(indices.get(i + 1));
			Vector2f position3 = vertices.get(indices.get(i + 2));
			
			// TODO: Use a transformation matrix for this
			position1 = position1.mul(scale).add(position);
			position2 = position2.mul(scale).add(position);
			position3 = position3.mul(scale).add(position);
			
			display.drawTriangle(position1, position2, position3, color);
		}
	}
	
	public void render(){
		
		display.render();
	}

	// TODO: Quit exposing this after proper font rendering support is added
	public IDisplay getDisplay() {

		return display;
	}
}
