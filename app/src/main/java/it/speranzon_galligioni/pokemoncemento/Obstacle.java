package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;

public class Obstacle extends GameElement {


    public Obstacle(Context context, int x, int y, int height, int width, ObstacleTypes type) {
        super(context, x, y, height, width, type.drawableRes);
    }


}