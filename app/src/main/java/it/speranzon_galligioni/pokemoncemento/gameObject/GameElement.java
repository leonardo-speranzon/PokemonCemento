package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.RelativeLayout;

import it.speranzon_galligioni.pokemoncemento.GameCostants;

public class GameElement extends AppCompatImageView {
	public GameElement(Context context, int x, int y, int height, int width, int image) {
		this(context, x, y, height, width, context.getDrawable(image));
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

	/**
	 * ritorna la X dell'elemento già divisa per la grandezza del quadrato
	 * @return X
	 */
	@Override
	public float getX() {
		return super.getX() / GameCostants.BOX_SIZE;
	}
	/**
	 * ritorna la Y dell'elemento già divisa per la grandezza del quadrato
	 * @return Y
	 */
	@Override
	public float getY() {
		return super.getY() / GameCostants.BOX_SIZE;
	}

	/**
	 * ritorna la larghezza dell'elemento già divisa per la grandezza del quadrato
	 * @return larghezza
	 */
	public int getCWidth() {
		return getWidth() / GameCostants.BOX_SIZE;
	}
	/**
	 * ritorna l'altezza dell'elemento già divisa per la grandezza del quadrato
	 * @return altezza
	 */
	public int getCHeight() {
		return getHeight() / GameCostants.BOX_SIZE;
	}
}
