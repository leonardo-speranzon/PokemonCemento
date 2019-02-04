package it.speranzon_galligioni.pokemoncemento;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.widget.RelativeLayout;

public class GameElement extends AppCompatImageView {
    public GameElement(Context context, int x, int y, int height, int width, int image) {
        super(context);
        setLayoutParams(new RelativeLayout.LayoutParams(width * GameCostants.BOX_SIZE, height * GameCostants.BOX_SIZE));
        setImageDrawable(context.getDrawable(image));
        setX(x * GameCostants.BOX_SIZE);
        setY(y * GameCostants.BOX_SIZE);
        setScaleType(ScaleType.FIT_XY);
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
