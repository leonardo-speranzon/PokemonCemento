package it.speranzon_galligioni.pokemoncemento;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TextController {

	private ConstraintLayout textLayout;
	private TextView dialog;
	private ImageView triangle;

	private ConstraintLayout controllers;

	private Handler handler;

	public TextController(ConstraintLayout textLayout, ConstraintLayout controllers) {
		this.textLayout = textLayout;
		this.controllers = controllers;

		this.dialog = (TextView) textLayout.getChildAt(0);
		this.triangle = (ImageView) textLayout.getChildAt(1);

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

	public void toggleDialog(boolean show) {
		controllers.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
		textLayout.setVisibility(!show ? View.INVISIBLE : View.VISIBLE);
	}


	private int c = 0;
	private boolean canTouch = false;

	public void writeText(final String name, String textdialog, final Runnable post) {
		dialog.setText(name + "\n");

		canTouch = false;
		toggleTriangle(false);
		c = 0;

		final char[] chars = textdialog.toCharArray();

		textLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_UP:
						if (canTouch)
							writeText(name, String.valueOf(chars).substring(c), post);
						return true;
				}
				return false;
			}
		});

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (c < chars.length) {
					if (c != 0 && chars[c - 1] == '.') {
						canTouch = true;
					} else {
						String s = dialog.getText().toString() + chars[c++];
						dialog.setText(s);
						handler.postDelayed(this, GameCostants.DIALOG_SPEED);
					}
				} else {
					canTouch = true;
					post.run();
				}
			}
		}, 0);
	}

	public ConstraintLayout getTextLayout() {
		return textLayout;
	}

	public void toggleTriangle(boolean show) {
		triangle.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
}
