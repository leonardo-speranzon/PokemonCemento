package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.RelativeLayout;

public class GameElement extends AppCompatImageView {
    public GameElement(Context context, int x, int y, int height, int width,int image) {
        this(context, x, y, height, width,context.getDrawable(image));
    }

    public GameElement(Context context, int x, int y, int height, int width, Drawable image) {
        super(context);
        setLayoutParams(new RelativeLayout.LayoutParams(width * GameCostants.BOX_SIZE, height * GameCostants.BOX_SIZE));
        setImageDrawable(image);
        setX(x * GameCostants.BOX_SIZE);
        setY(y * GameCostants.BOX_SIZE);
        setScaleType(ScaleType.FIT_XY);
    }
    public boolean checkCollision(Player player, int moveX, int moveY) {
        //Log.d("OSTACOLO","playerX: "+player.getX()+", playerY: "+player.getY()+", moveX: "+moveX+", moveY: "+moveY+", obsX: "+getX()+", obsY: "+getY()+", obsWidth: "+getCWidth()+", obsHeight: "+getCHeight());
        return (moveX + getX() <= player.getX() && moveX + getX() + getCWidth() >= player.getX() + 1
                && moveY + getY() <= player.getY() && moveY + getY() + getCHeight() >= player.getY() + 1);
    }
    @Override
    public float getX() {
        return super.getX() / GameCostants.BOX_SIZE;
    }

    @Override
    public float getY() {
        return super.getY() / GameCostants.BOX_SIZE;
    }

    public int getCWidth() {
        return getWidth() / GameCostants.BOX_SIZE;
    }

    public int getCHeight() {
        return getHeight() / GameCostants.BOX_SIZE;
    }
}
