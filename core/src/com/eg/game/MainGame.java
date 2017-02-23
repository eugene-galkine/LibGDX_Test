package com.eg.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public class MainGame extends ApplicationAdapter
{
	public static final int PPM = 16;
	
	private World world;
	private Box2DDebugRenderer debugRenderer;
	private OrthographicCamera camera;
	private SpriteBatch batch;
	private Game game;
	
	@Override
	public void create ()
	{
		batch = new SpriteBatch();		
		world = new World(new Vector2(0,-9.8f), true);
		camera = new OrthographicCamera(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		debugRenderer = new Box2DDebugRenderer();
		
		createGround(0,-300,100);
		createGround(0,-400,-300);
		
		game = new Game(this);
		Gdx.input.setInputProcessor(new InputHandler());
		
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
		
		s1.dispose();
		
		world.createBody(def1).createFixture(fdef1);
	}

	@Override
	public void render () 
	{
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		world.setGravity(new Vector2(-2 * (int)Gdx.input.getAccelerometerX(), -9.8f));
		
		world.step(Gdx.graphics.getDeltaTime(), 6, 2);
		
		Array<Body> iter = new Array<Body>();
	    world.getBodies(iter);
		
		batch.begin();
		
		System.out.println("X: " + (int)Gdx.input.getAccelerometerX());
		
		
	    for (Body body : iter) 
	    {
	    	GameObject s = game.get(body.toString());
	    	
	    	if (s == null)
	    		continue;
	    	
	    	if (body.isAwake())
	    	{
	    		s.getImg().setPosition(((body.getPosition().x )* PPM) + (Gdx.graphics.getWidth() / 2) - 19, ((body.getPosition().y) * PPM) + (Gdx.graphics.getHeight() / 2) - 19);
	    		s.getImg().setSize(38, 38);
	    		s.getImg().setOrigin(19, 19);
	    		s.getImg().setRotation((float) Math.toDegrees(body.getAngle()));
	    	}
	    	
	    	s.getImg().draw(batch);
	    }
	    
        batch.end();
        
        debugRenderer.render(world, camera.projection);
	}
	
	@Override
	public void dispose () 
	{
		debugRenderer.dispose();
		world.dispose();
	}

	public Body createBody(BodyDef def1, FixtureDef fdef1)
	{
		Body b = world.createBody(def1);
		b.createFixture(fdef1);
		return b;
	}
}
