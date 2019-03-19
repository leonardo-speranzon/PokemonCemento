package it.speranzon_galligioni.pokemoncemento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.speranzon_galligioni.pokemoncemento.gameObject.Trainer;

public class TextController {

	private ConstraintLayout textLayout;
	private TextView dialog;
	private ImageView triangle;

	private ConstraintLayout controllers;

	private Context context;

	private Handler handler;

	/**
	 * @param textLayout
	 * @param controllers
	 * @param context
	 */
	public TextController(ConstraintLayout textLayout, ConstraintLayout controllers, Context context) {
		this.textLayout = textLayout;
		this.controllers = controllers;

		this.dialog = (TextView) textLayout.getChildAt(0);
		this.triangle = (ImageView) textLayout.getChildAt(1);

		this.context = context;

		handler = new Handler();

		handler.postDelayed(new Runnable() {
			boolean visible = true;

			@Override
			public void run() {
				visible = !visible && canTouch;
				toggleTriangle(visible);
				handler.postDelayed(this, 500);
			}
		}, 0);
	}

	/**
	 *
	 * @param show
	 */
	public void toggleDialog(boolean show) {
		controllers.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
		textLayout.setVisibility(!show ? View.INVISIBLE : View.VISIBLE);
	}


	private int c = 0;
	private boolean canTouch = false;

	/**
	 * @param t
	 * @param post
	 */
	@SuppressLint("ClickableViewAccessibility")
	public void writeText(final Trainer t, final Runnable post) {

		dialog.setText(t.getName() + "\n");

		canTouch = false;
		toggleTriangle(false);

		final char[] chars = context.getString(context.getResources().getIdentifier(t.getName(), "string", context.getPackageName())).toCharArray();

		textLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_UP:
						if (canTouch)
							writeText(t, post);
						return true;
				}
				return false;
			}
		});

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (c < chars.length) {
					String s = dialog.getText().toString() + chars[c++];
					dialog.setText(s);
					if (c == 0 || (chars[c - 1] != '.' && chars[c - 1] != '!' && chars[c - 1] != '?'))
						handler.postDelayed(this, GameCostants.DIALOG_SPEED);
					else
						canTouch = c < chars.length;
				}
				if (c >= chars.length) {
					canTouch = true;
					textLayout.setOnTouchListener(new View.OnTouchListener() {
						@Override
						public boolean onTouch(View v, MotionEvent event) {
							switch (event.getAction()) {
								case MotionEvent.ACTION_DOWN:
									return true;
								case MotionEvent.ACTION_UP:
									c = 0;
									toggleDialog(false);
									post.run();
									return true;
							}
							return false;
						}
					});
				}
			}
		}, 0);
	}

	/**
	 *
	 * @return
	 */
	public ConstraintLayout getTextLayout() {
		return textLayout;
	}

	/**
	 *
	 * @param show
	 */
	public void toggleTriangle(boolean show) {
		triangle.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
}
