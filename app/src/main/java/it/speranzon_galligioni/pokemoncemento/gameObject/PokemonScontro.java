package it.speranzon_galligioni.pokemoncemento.gameObject;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import it.speranzon_galligioni.pokemoncemento.PokemonDetailsAnimation;
import it.speranzon_galligioni.pokemoncemento.R;
import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;

public class PokemonScontro extends ConstraintLayout {

	private ConstraintLayout detailsBar;

	private ImageView imgPokemon;
	private TextView txtName, txtHp;
	private ProgressBar life;
	private int hp, currentHp;
	private AnimatorSet anim;

	/**
	 * @param context
	 */
	public PokemonScontro(Context context) {
		super(context);
	}

	/**
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public PokemonScontro(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}

	/**
	 * @param context
	 * @param attrs
	 */
	public PokemonScontro(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.PokemonScontro, 0, 0);
		anim = (AnimatorSet) AnimatorInflater.loadAnimator(getContext(), (a.getInt(R.styleable.PokemonScontro_side, 0) == 0 ? R.animator.friendly_attack : R.animator.enemy_attack));

	}

	/**
	 * Initializza PokemonScontro
	 *
	 * @param pokemon pokemon da usare
	 */
	public void init(Pokemon pokemon) {
		imgPokemon = this.findViewById(R.id.pokemon);
		detailsBar = this.findViewById(R.id.details);
		life = detailsBar.findViewById(R.id.life);
		txtHp = detailsBar.findViewById(R.id.hp);
		txtName = detailsBar.findViewById(R.id.name);

		setClipToPadding(false);
		setClipChildren(false);


		currentHp = hp = pokemon.getHp();

		imgPokemon.setImageDrawable(getContext().getDrawable(pokemon.getImg()));
		life.setMax(hp * 100);
		life.setProgress(currentHp * 100);
		txtName.setText(pokemon.getName());
		txtHp.setText(currentHp + "/" + hp + " HP");

		anim.setTarget(imgPokemon);

	}

	/**
	 * setta cosa fare in caso di tocco sul pokemon
	 *
	 * @param tl
	 */
	public void setOnPokemonTouchListener(OnTouchListener tl) {
		imgPokemon.setOnTouchListener(tl);
	}

	/**
	 * fa partire l'animazione
	 *
	 * @param onFinish Runnable da eseguire a fine animazione
	 */
	public void attackAnim(final Runnable onFinish) {
		anim.removeAllListeners();
		anim.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				onFinish.run();
			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
		anim.start();

	}

	/**
	 * Infligge danno al pokemon
	 *
	 * @param hpDamage danno da infliggere
	 * @return true se il pokemon è morto
	 */
	public void damage(int hpDamage, final Runnable onNotDied, final Runnable onDied) {
		int previousHp = currentHp;
		currentHp = Math.max(currentHp - hpDamage, 0);

		PokemonDetailsAnimation lifeAnim = new PokemonDetailsAnimation(detailsBar, previousHp * 100, currentHp * 100);
		lifeAnim.setDuration(1000);
		lifeAnim.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				if (currentHp > 0)
					onNotDied.run();
				else
					onDied.run();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		life.startAnimation(lifeAnim);
	}


}
