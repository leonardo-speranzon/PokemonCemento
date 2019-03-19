package it.speranzon_galligioni.pokemoncemento;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.Random;

import it.speranzon_galligioni.pokemoncemento.enums.Attack;
import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;
import it.speranzon_galligioni.pokemoncemento.gameObject.PokemonScontro;

public class Scontro {
	private PokemonScontro friendly, enemy;
	private Attack[] friendlyAttacks, enemyAttacks;
	private LinearLayout atcks;
	private TextView txtAttackName;
	private AnimatorSet txtAttackNameAnimator;
	private boolean turno;//TRUE:friendly / FALSE:enemy
	private Runnable onFinish;
	private Handler handler;
	private Random r;

	/**
	 * @param friendlyPok
	 * @param enemyPok
	 * @param onFinish
	 * @param context
	 */
	public Scontro(final Pokemon friendlyPok, final Pokemon enemyPok, final Runnable onFinish, Context context) {
		this.onFinish = onFinish;
		friendly = ((AppCompatActivity) context).findViewById(R.id.friendly_side);
		enemy = ((AppCompatActivity) context).findViewById(R.id.enemy_side);
		atcks = ((AppCompatActivity) context).findViewById(R.id.atcks);
		txtAttackName = ((AppCompatActivity) context).findViewById(R.id.txt_attack_name);
		txtAttackNameAnimator = (AnimatorSet) AnimatorInflater.loadAnimator(context, R.animator.text_attack);
		txtAttackNameAnimator.setTarget(txtAttackName);
		turno = true;
		handler = new Handler();

		r = new Random();
		friendlyAttacks = new Attack[4];
		enemyAttacks = new Attack[4];

		for (int i = 0; i < 4; i++) {
			Attack at;
			do
				at = Attack.values()[r.nextInt(Attack.values().length)];
			while (Arrays.binarySearch(Arrays.copyOf(friendlyAttacks, i), at) >= 0);
			friendlyAttacks[i] = at;
		}
		for (int i = 0; i < 4; i++) {
			Attack at;
			do
				at = Attack.values()[r.nextInt(Attack.values().length)];
			while (Arrays.binarySearch(Arrays.copyOf(enemyAttacks, i), at) >= 0);
			enemyAttacks[i] = at;
		}


		friendly.init(friendlyPok);
		enemy.init(enemyPok);
		for (int i = 0; i < atcks.getChildCount(); i++) {
			((Button) atcks.getChildAt(i)).setText(friendlyAttacks[i].toString().replace("_", " \n"));
			atcks.getChildAt(i).setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_UP) {
						turno = false;
						Attack atck = Attack.valueOf(((Button) v).getText().toString().replace(" \n", "_"));
						txtAttackName.setText(((Button) v).getText().toString() + ' ');
						txtAttackNameAnimator.start();
						friendly.attackAnim(() -> enemy.damage(atck.getDamage(), () -> enemyAttack(), () -> onFinish.run()));

						atcks.setVisibility(View.INVISIBLE);
					}
					return false;
				}
			});
		}

		friendly.setOnPokemonTouchListener(new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_UP) {
					if (turno)
						atcks.setVisibility(atcks.getVisibility() == View.INVISIBLE ? View.VISIBLE : View.INVISIBLE);
				}
				return true;
			}
		});

	}

	/**
	 * attacco del nemico
	 */
	void enemyAttack() {

		Attack atck = enemyAttacks[r.nextInt(4)];
		txtAttackName.setText(atck.toString().replace("_", " \n") + ' ');
		txtAttackNameAnimator.start();
		enemy.attackAnim(() -> friendly.damage(atck.getDamage(), () -> turno = true, () -> onFinish.run()));
	}

	private boolean notEquals(int a, int... bs) {
		for (int b : bs)
			if (b == a)
				return false;
		return true;
	}
}
