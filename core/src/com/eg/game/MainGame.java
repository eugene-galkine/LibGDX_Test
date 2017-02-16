package com.eg.game;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MainGame extends ApplicationAdapter implements InputProcessor
{
	private static final int PPM = 16;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private Texture img;
	private SpriteBatch batch;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();
		img = new Texture ("badlogic.jpg");
		
		world = new World(new Vector2(0,-9.8f), true);
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		debugRenderer = new Box2DDebugRenderer();
		
		createGround(0,0,100);
		createBody(0,-500,400,50,true);
		
		Gdx.input.setInputProcessor(this);
		
	}
	
	private void createBody(int x, int y, int w, int h, boolean isStatic)
	{
		BodyDef def1 = new BodyDef();
		def1.type = isStatic ? BodyType.StaticBody : BodyType.DynamicBody;
		def1.position.set(x / PPM,y / PPM);
		
		PolygonShape s1 = new PolygonShape();
		s1.setAsBox(w / PPM, h / PPM);
		
		FixtureDef fdef1 = new FixtureDef();
		fdef1.shape = s1;
		fdef1.density = 1f;
		
		s1.dispose();
		
		world.createBody(def1).createFixture(fdef1);
	}
	
	private void createGround(int x, int y, int w)
	{ 
		BodyDef def1 = new BodyDef();
		def1.type = BodyType.StaticBody;
		def1.position.set(x / PPM,y / PPM);
		
		PolygonShape s1 = new PolygonShape();
		s1.set(new Vector2[]{new Vector2((x - w) / PPM, y / PPM), new Vector2((x + w) / PPM, y / PPM), new Vector2(x / PPM, (y + w) / PPM)});
		
		FixtureDef fdef1 = new FixtureDef();
		fdef1.shape = s1;
		fdef1.density = 1f;
		
		s1.dispose();
		
		world.createBody(def1).createFixture(fdef1);
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		Array<Body> iter = new Array<Body>();
	    world.getBodies(iter);
		
		batch.begin();
	    for (Body body : iter) 
	        batch.draw(img, (body.getPosition().x * PPM) + (Gdx.graphics.getWidth() / 2), (body.getPosition().y * PPM) + (Gdx.graphics.getHeight() / 2));
	    
        batch.end();
        
        debugRenderer.render(world, camera.projection);
	}
	
	@Override
	public void dispose () 
	{
		debugRenderer.dispose();
		world.dispose();
	}

	
	@Override
	public boolean touchDown (int screenX, int screenY, int pointer, int button) 
	{
		System.out.println("touch: " + screenX + "," + screenY);
        createBody(screenX - (Gdx.graphics.getWidth() / 2),-(screenY - (Gdx.graphics.getHeight() / 2)),20,20,false);
        return true;
    }
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer)
	{
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
	@Override
	public boolean keyDown(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount)
	{
		// TODO Auto-generated method stub
		return false;
	}
}
