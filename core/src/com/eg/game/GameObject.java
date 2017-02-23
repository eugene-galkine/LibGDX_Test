package com.eg.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;

public class GameObject
{
	private Sprite sprite;
	private Body body;
	
	public GameObject (Body body, Texture texture)
	{
		this.body = body;
		sprite = new Sprite(texture);
	}
	
	public Sprite getImg()
	{
		return sprite;
	}
}
