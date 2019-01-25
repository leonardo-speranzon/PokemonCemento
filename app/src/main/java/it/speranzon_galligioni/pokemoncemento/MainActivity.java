package it.speranzon_galligioni.pokemoncemento;

import android.content.res.XmlResourceParser;
import android.graphics.Point;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button up, down, left, right;
    private Game game;
    private ConstraintLayout root;

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

        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(Direction.UP);
            }
        });
        down.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(Direction.DOWN);
            }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(Direction.LEFT);
            }
        });
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                move(Direction.RIGHT);
            }
        });

        game = readXmlAndCreate(R.id.map_container, R.id.map, R.xml.map_severi);

        hideSystemUI();
    }

    public void move(Direction d) {
        Log.d("PROVA", "mW: " + game.getWidth() / GameCostants.BOX_SIZE + "  mH: " + game.getHeight() / GameCostants.BOX_SIZE);
        //Log.d("PROVA","Bordi: "+!game.checkMapBounds(d) +"  Collisioni: "+game.checkCollisions(d));
        if (!game.checkMapBounds(d))
            return;
        if (game.checkCollisions(d))
            return;

        game.movePlayer(d);

    }


    private void hideSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        onWindowFocusChanged(true);
    }

    public Game readXmlAndCreate(int mapContainerId, int mapId, int xmlMapFile) {

        List<Obstacle> obstacles = new ArrayList<>();
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
        mapContainerL.setLayoutParams(lpMc);

        Player p = new Player(this, mcWidth / 2, mcHeight / 2);
        mapContainerL.addView(p);
        RelativeLayout.LayoutParams lpP = (RelativeLayout.LayoutParams) p.getLayoutParams();
        lpP.topMargin = mapContainerL.getHeight() / 2;
        lpP.leftMargin = mapContainerL.getHeight() / 2;
        p.setLayoutParams(lpP);

        Game game = new Game(mapL, mapHeight, mapWidth, playerStartX, playerStartY, obstacles, p);
        return game;
    }
}
