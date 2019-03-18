package it.speranzon_galligioni.pokemoncemento;

import android.annotation.SuppressLint;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.speranzon_galligioni.pokemoncemento.enums.Direction;
import it.speranzon_galligioni.pokemoncemento.enums.ObstacleTypes;
import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;
import it.speranzon_galligioni.pokemoncemento.gameObject.Obstacle;
import it.speranzon_galligioni.pokemoncemento.gameObject.Player;
import it.speranzon_galligioni.pokemoncemento.gameObject.Trainer;

public class MainActivity extends AppCompatActivity {


	private View up, down, left, right;
	private Button cmdA, cmdB;
	private Game game;
	private ConstraintLayout root, joystick;
	private TextController txtController;
	private Pokemon myPokemon;

	@SuppressLint("ClickableViewAccessibility")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		myPokemon = (Pokemon) getIntent().getSerializableExtra("pokemon");

		setContentView(R.layout.activity_main);

		GameCostants.BOX_SIZE = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP,
				50,
				getResources().getDisplayMetrics()
		);
		Log.d("PROVA", "50dp = " + GameCostants.BOX_SIZE);

		up = findViewById(R.id.cmd_up);
		down = findViewById(R.id.cmd_down);
		left = findViewById(R.id.cmd_left);
		right = findViewById(R.id.cmd_right);
		cmdA = findViewById(R.id.cmd_A);
		cmdB = findViewById(R.id.cmd_B);
		joystick = findViewById(R.id.joystick);
		root = findViewById(R.id.root);

		txtController = new TextController((ConstraintLayout) findViewById(R.id.textController), (ConstraintLayout) findViewById(R.id.controllers), this);

		joystick.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Direction dir = getDirectionJoystick(event);
				if (dir == Direction.NONE) {
					game.stopMove();
					return true;
				}
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						game.startMove(dir);
						return true;
					case MotionEvent.ACTION_MOVE:
						if (game.getCurrentDirection() != dir) {
							game.changeDirection(dir);
						}
						return true;
					case MotionEvent.ACTION_UP:
						game.stopMove();
						return true;
				}
				return false;
			}
		});
		cmdB.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						game.setRunning(true);
						return true;
					case MotionEvent.ACTION_UP:
						game.setRunning(false);
						return true;
				}
				return false;
			}
		});
		cmdA.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_UP:
						return true;
				}
				return false;
			}
		});


		hideSystemUI();
		game = readXmlAndCreate(R.id.map_container, R.id.map, R.xml.map_severi);
	}

	private Direction getDirectionJoystick(MotionEvent me) {
		float x = me.getX();
		float y = me.getY();
		if (x >= up.getX() && x <= up.getX() + up.getHeight() && y >= up.getY() && y <= up.getY() + up.getWidth())
			return Direction.UP;
		else if (x >= down.getX() && x <= down.getX() + down.getHeight() && y >= down.getY() && y <= down.getY() + down.getWidth())
			return Direction.DOWN;
		else if (x >= left.getX() && x <= left.getX() + left.getHeight() && y >= left.getY() && y <= left.getY() + left.getWidth())
			return Direction.LEFT;
		else if (x >= right.getX() && x <= right.getX() + right.getHeight() && y >= right.getY() && y <= right.getY() + right.getWidth())
			return Direction.RIGHT;
		return Direction.NONE;
	}


	private void hideSystemUI() {
       /* final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;*/
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		onWindowFocusChanged(true);
	}


	public Game readXmlAndCreate(int mapContainerId, int mapId, int xmlMapFile) {

		List<Obstacle> obstacles = new ArrayList<>();
		List<Trainer> trainers = new ArrayList<>();
		RelativeLayout mapContainerL = findViewById(mapContainerId);
		RelativeLayout mapL = findViewById(mapId);

		int mapHeight = -1, mapWidth = -1, playerStartX = 0, playerStartY = 0;
		try {
			XmlResourceParser xpp = getResources().getXml(xmlMapFile);
			xpp.next();

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_TAG) {
					Log.d("PROVA", "Tag : " + xpp.getName());
					if (xpp.getName().equals("map")) {
						mapHeight = xpp.getAttributeIntValue(null, "h", -1);
						mapWidth = xpp.getAttributeIntValue(null, "w", -1);
						playerStartX = xpp.getAttributeIntValue(null, "px", 0);
						playerStartY = xpp.getAttributeIntValue(null, "py", 0);
					} else if (xpp.getName().equals("obstacle")) {
						int x = xpp.getAttributeIntValue(null, "x", 0);
						int y = xpp.getAttributeIntValue(null, "y", 0);
						int h = xpp.getAttributeIntValue(null, "h", 1);
						int w = xpp.getAttributeIntValue(null, "w", 1);
						String type = xpp.getAttributeValue(null, "type");
						if (type == null)
							obstacles.add(new Obstacle(this, x, y, h, w, ObstacleTypes.getDefault()));
						else
							obstacles.add(new Obstacle(this, x, y, h, w, ObstacleTypes.valueOf(type)));

					} else if (xpp.getName().equals("trainer")) {
						int x = xpp.getAttributeIntValue(null, "x", 0);
						int y = xpp.getAttributeIntValue(null, "y", 0);
						String dir = xpp.getAttributeValue(null, "direction");
						String name = xpp.getAttributeValue(null, "name");
						Log.d("PROVA", name + "");
						trainers.add(new Trainer(this, x, y, Direction.valueOf(dir), name));
					}
				}
				eventType = xpp.next();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		}

		Point screenSize = new Point();
		getWindowManager().getDefaultDisplay().getSize(screenSize);
		ConstraintLayout.LayoutParams lpMc = (ConstraintLayout.LayoutParams) mapContainerL.getLayoutParams();

		int mcHeight = (int) Math.ceil((screenSize.y) / (double) GameCostants.BOX_SIZE);
		if (mcHeight % 2 == 0)
			mcHeight++;
		int mcWidth = (int) Math.ceil((screenSize.x) / (double) GameCostants.BOX_SIZE);
		if (mcWidth % 2 == 0)
			mcWidth++;
		Log.d("PROVA", "Height: " + screenSize.y + "  Width: " + screenSize.x + "  mcHeight: " + mcHeight + "  mcWidth: " + mcWidth);

		lpMc.height = mcHeight * GameCostants.BOX_SIZE;
		lpMc.width = mcWidth * GameCostants.BOX_SIZE;
		lpMc.topMargin = -(mcHeight * GameCostants.BOX_SIZE - screenSize.y) / 2;
		lpMc.bottomMargin = -(mcHeight * GameCostants.BOX_SIZE - screenSize.y) / 2;
		lpMc.leftMargin = -(mcWidth * GameCostants.BOX_SIZE - screenSize.x) / 2;
		lpMc.rightMargin = -(mcWidth * GameCostants.BOX_SIZE - screenSize.x) / 2;
		mapContainerL.setLayoutParams(lpMc);


		Player p = new Player(this, mcWidth / 2, mcHeight / 2);
		mapContainerL.addView(p);
		RelativeLayout.LayoutParams lpP = (RelativeLayout.LayoutParams) p.getLayoutParams();
		lpP.topMargin = mapContainerL.getWidth() / 2;
		lpP.leftMargin = mapContainerL.getHeight() / 2;
		p.setLayoutParams(lpP);
		Log.d("PROVA", "sSY: " + screenSize.y + ", plY: " + p.getY());
		Game game = new Game(mapL, mapHeight, mapWidth, playerStartX, playerStartY, obstacles, trainers, p, txtController, new Runnable() {
			@Override
			public void run() {
				final View main = findViewById(R.id.root);

				LayoutInflater inflator = getLayoutInflater();
				View view = inflator.inflate(R.layout.activity_scontro, null, false);
				view.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_in_left));
				setContentView(view);


				//setContentView(R.layout.activity_scontro);
				Scontro s = new Scontro(myPokemon, Pokemon.CEMENTOKARP, new Runnable() {
					@Override
					public void run() {
						main.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(), android.R.anim.slide_out_right));
						setContentView(main);
					}
				}, MainActivity.this);
			}
		}, this);
		return game;
	}
}
