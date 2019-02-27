package it.speranzon_galligioni.pokemoncemento;

import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

public class TextController {

	private ConstraintLayout textController;
	private TextView dialog;

	private ConstraintLayout controllers;

	private Handler handler;

	public TextController(ConstraintLayout textController, ConstraintLayout controllers) {
		this.textController = textController;
		this.controllers = controllers;

		this.dialog = (TextView) textController.getChildAt(0);

		handler = new Handler();
	}

	public void toggleDialog(boolean show) {
		controllers.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
		textController.setVisibility(!show ? View.INVISIBLE : View.VISIBLE);
	}


	private int c = 0;

	public void writeText(String textdialog, final Runnable post) {
		dialog.setText("");
		c = 0;
		final char[] chars = textdialog.toCharArray();

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				if (c < chars.length) {
					String s = dialog.getText().toString() + chars[c++];
					dialog.setText(s);
					handler.postDelayed(this, GameCostants.DIALOG_SPEED);
				} else
					post.run();
			}
		}, 0);
	}

	public ConstraintLayout getTextController() {
		return textController;
	}
}
