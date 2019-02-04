package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;

public class Obstacle extends GameElement {


    public Obstacle(Context context, int x, int y, int height, int width, ObstacleTypes type) {
        super(context, x, y, height, width, type.drawableRes);
    }

    public boolean checkCollision(Player player, int moveX, int moveY) {
        //Log.d("OSTACOLO","playerX: "+player.getX()+", playerY: "+player.getY()+", moveX: "+moveX+", moveY: "+moveY+", obsX: "+getX()+", obsY: "+getY()+", obsWidth: "+getCWidth()+", obsHeight: "+getCHeight());
        return (moveX + getX() <= player.getX() && moveX + getX() + getCWidth() >= player.getX() + 1
                && moveY + getY() <= player.getY() && moveY + getY() + getCHeight() >= player.getY() + 1);
    }
}