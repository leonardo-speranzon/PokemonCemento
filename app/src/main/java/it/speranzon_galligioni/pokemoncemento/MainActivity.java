package it.speranzon_galligioni.pokemoncemento;

import android.annotation.SuppressLint;
import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private View up, down, left, right;
    private Game game;
    private ConstraintLayout root;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        root = findViewById(R.id.root);

        up.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        game.startMove(Direction.UP);
                        break;
                    case MotionEvent.ACTION_UP:
                        game.stopMove(Direction.UP);
                }
                return true;
            }
        });
        down.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        game.startMove(Direction.DOWN);
                        break;
                    case MotionEvent.ACTION_UP:
                        game.stopMove(Direction.DOWN);
                }
                return true;
            }
        });
        left.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        game.startMove(Direction.LEFT);
                        break;
                    case MotionEvent.ACTION_UP:
                        game.stopMove(Direction.LEFT);
                }
                return true;
            }
        });
        right.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        game.startMove(Direction.RIGHT);
                        break;
                    case MotionEvent.ACTION_UP:
                        game.stopMove(Direction.RIGHT);
                }
                return true;
            }
        });



        game = readXmlAndCreate(R.id.map_container, R.id.map, R.xml.map_severi);

        hideSystemUI();
    }




    private void hideSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

                    }else if(xpp.getName().equals("trainer")){
                        int x = xpp.getAttributeIntValue(null, "x", 0);
                        int y = xpp.getAttributeIntValue(null, "y", 0);
                        String dir = xpp.getAttributeValue(null, "direction");
                        trainers.add(new Trainer(this, x, y, Direction.valueOf(dir)));
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

        Log.d("PROVA", "Height: " + screenSize.y + "  Width: " + screenSize.x);
        ConstraintLayout.LayoutParams lpMc = (ConstraintLayout.LayoutParams) mapContainerL.getLayoutParams();
        int mcHeight = (int) Math.floor((screenSize.y - 16) / GameCostants.BOX_SIZE);
        if (mcHeight % 2 == 0)
            mcHeight--;
        int mcWidth = (int) Math.floor((screenSize.x - 16) / GameCostants.BOX_SIZE);
        if (mcWidth % 2 == 0)
            mcWidth--;
        lpMc.height = mcHeight * GameCostants.BOX_SIZE;
        lpMc.width = mcWidth * GameCostants.BOX_SIZE;
        lpMc.topMargin=(screenSize.y-mcHeight*GameCostants.BOX_SIZE)/2;
        lpMc.leftMargin=(screenSize.x-mcWidth*GameCostants.BOX_SIZE)/2;
        mapContainerL.setLayoutParams(lpMc);


        Player p = new Player(this, mcWidth / 2, mcHeight / 2);
        mapContainerL.addView(p);
        RelativeLayout.LayoutParams lpP = (RelativeLayout.LayoutParams) p.getLayoutParams();
        lpP.topMargin = mapContainerL.getHeight() / 2;
        lpP.leftMargin = mapContainerL.getHeight() / 2;
        p.setLayoutParams(lpP);

        Game game = new Game(mapL, mapHeight, mapWidth, playerStartX, playerStartY, obstacles,trainers, p,this);
        return game;
    }
}
