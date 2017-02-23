package com.eg.game;

import java.util.Hashtable;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class Game
{	
	public static Game getInstance()
	{
		return instance;
	}
	
	private static Game instance;
	
	private MainGame main;
	private Hashtable<String, GameObject> objects;
	private Texture img;
	
	public Game(MainGame main)
	{
		instance = this;
		this.main = main;
		objects = new Hashtable<String, GameObject>();
		img = new Texture ("badlogic.jpg");
	}

	public GameObject get(String string)
	{
		return objects.get(string);
	}
	
	public void createCube(int x, int y)
	{
		BodyDef def1 = new BodyDef();
		def1.type = BodyType.DynamicBody;
		def1.position.set(x / MainGame.PPM, y / MainGame.PPM);
		
		PolygonShape s1 = new PolygonShape();
		s1.setAsBox(20 / MainGame.PPM, 20 / MainGame.PPM);
		
		FixtureDef fdef1 = new FixtureDef();
		fdef1.shape = s1;
		fdef1.density = 1f;
		fdef1.friction = 2f;
		
		s1.dispose();
		
		Body b = main.createBody(def1, fdef1);
		objects.put(b.toString(), new GameObject(b, img));
	}
}
