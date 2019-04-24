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

package engine.objects.entities;

import engine.graphics.Model;
import engine.graphics.Renderer;
import engine.math.Vector2f;
import engine.objects.GameObject;

import java.awt.*;

/**
 * @author Matthew Day
 */
public class Entity extends GameObject {
	
	private Model model;
	private Color color;
	
	public Entity(Model model, Vector2f position, Vector2f scale, Color color) {
		
		super(position, scale);
		
		this.model = model;
		this.color = color;
	}
	
	@Override
	public void render(Renderer renderer){
	
		renderer.render(model, this.getPosition(), this.getScale(), color);
	}
	
	public Model getModel() {
		
		return model;
	}
	
	public void setModel(Model model) {
		
		this.model = model;
	}
	
	public Color getColor() {
		
		return color;
	}
	
	public void setColor(Color color) {
		
		this.color = color;
	}
}
