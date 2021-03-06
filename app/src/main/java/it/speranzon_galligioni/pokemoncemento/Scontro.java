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

import java.util.ArrayList;
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
	private Runnable onPlayerWin, onPlayerLose;
	private Handler handler;
	private Random r;

	/**
	 * Costruttore di Scontro
	 *
	 * @param friendlyPok  il proprio Pokemon
	 * @param enemyPok     il Pokemon dell'aversario
	 * @param onPlayerWin  azione in caso di vittoria
	 * @param onPlayerLose azione in caso di sconfitta
	 * @param context      context
	 */
	public Scontro(Pokemon friendlyPok, Pokemon enemyPok, Runnable onPlayerWin, Runnable onPlayerLose, Context context) {
		this.onPlayerWin = onPlayerWin;
		this.onPlayerLose = onPlayerLose;
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

		//GENERAZIONE CASUALE ATTACCHI
		ArrayList<Attack> allAttak = new ArrayList<>(Arrays.asList(Attack.values()));
		for (int i = 0; i < 4; i++)
			friendlyAttacks[i] = allAttak.remove(r.nextInt(allAttak.size()));

		allAttak = new ArrayList<>(Arrays.asList(Attack.values()));
		for (int i = 0; i < 4; i++)
			enemyAttacks[i] = allAttak.remove(r.nextInt(allAttak.size()));

		allAttak.clear();

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
						friendly.attackAnim(() -> enemy.damage(atck.getDamage(), () -> enemyAttack(), () -> onPlayerWin.run()));

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
	 * Attacco nemico
	 */
	private void enemyAttack() {
		Attack atck = enemyAttacks[r.nextInt(4)];
		txtAttackName.setText(atck.toString().replace("_", " \n") + ' ');
		txtAttackNameAnimator.start();
		enemy.attackAnim(() -> friendly.damage(atck.getDamage(), () -> turno = true, () -> onPlayerLose.run()));
	}
}
