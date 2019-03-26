package it.speranzon_galligioni.pokemoncemento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import it.speranzon_galligioni.pokemoncemento.gameObject.GameElement;
import it.speranzon_galligioni.pokemoncemento.gameObject.Trainer;
import it.speranzon_galligioni.pokemoncemento.gameObject.Treasure;

public class TextController {

	private ConstraintLayout textLayout;
	private TextView dialog;
	private ImageView triangle;

	private ConstraintLayout controllers;

	private Context context;

	private Handler handler;

	/**
	 * Costruttore di TextController
	 *
	 * @param textLayout  ConstraintLayout dell'area dei dialoghi
	 * @param controllers ConstraintLayout dell'area dei controlli del player
	 * @param context     Context
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
	 * Visializza/Nasconde i ConstraintLayout invertendoli
	 *
	 * @param show Se true rende visibile il dialogo e nasconde i controlli, se false fa il contrario
	 */
	public void toggleDialog(boolean show) {
		controllers.setVisibility(show ? View.INVISIBLE : View.VISIBLE);
		textLayout.setVisibility(!show ? View.INVISIBLE : View.VISIBLE);
	}


	private int c = 0;
	private boolean canTouch = false;

	/**
	 * Gestisce la scrittura del dialogo spezzandolo ad ogni punto
	 *
	 * @param ge   GameElement con cui si è interagito
	 * @param post Runnable che viene richiamato alla fine del dialogo, dopo l'ultimo tocco
	 */
	@SuppressLint("ClickableViewAccessibility")
	public void writeText(GameElement ge, Runnable post) {
		String name;
		if (ge instanceof Trainer)
			name = ((Trainer) ge).getName();
		else if (ge instanceof Treasure)
			name = "Baule";
		else
			return;

		textLayout.setOnTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
						return true;
					case MotionEvent.ACTION_UP:
						if (canTouch)
							dialoghe(name, post);
						return true;
				}
				return false;
			}
		});

		dialoghe(name, post);
	}

	@SuppressLint("ClickableViewAccessibility")
	private void dialoghe(String name, final Runnable post) {

		dialog.setText(name + "\n");

		canTouch = false;
		toggleTriangle(false);

		String s;
		try {
			s = context.getString(context.getResources().getIdentifier(name, "string", context.getPackageName()));
		} catch (Exception exc) {
			s = "Lotta contro di me!";
		}
		final char[] chars = s.toCharArray();

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
									if (post != null)
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
	 * Ritorna il ConstraintLayout del dialogo
	 *
	 * @return ConstraintLayout del dialogo
	 */
	public ConstraintLayout getTextLayout() {
		return textLayout;
	}

	/**
	 * Visualizza/Nasconde il triangolino del dialogo
	 *
	 * @param show true per mostrarlo, false per nasconderlo
	 */
	public void toggleTriangle(boolean show) {
		triangle.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
	}
}
