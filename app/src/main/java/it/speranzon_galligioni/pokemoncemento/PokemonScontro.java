package it.speranzon_galligioni.pokemoncemento;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PokemonScontro extends ConstraintLayout {

	private ConstraintLayout detailsBar;

	private ImageView imgPokemon;
	private TextView txtName,txtHp;
	private ProgressBar life;
	private int hp,currentHp;
	private AnimatorSet anim;

	public PokemonScontro(Context context) {
		super(context);
	}
	public PokemonScontro(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

	}
	public PokemonScontro(Context context, AttributeSet attrs) {
		super(context, attrs);
		TypedArray a = context.getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.PokemonScontro,0,0);
		anim=(AnimatorSet) AnimatorInflater.loadAnimator(getContext(),(a.getInt(R.styleable.PokemonScontro_side,0)==0?R.animator.friendly_attack:R.animator.enemy_attack));

	}


	public void init(Pokemon pokemon){
		imgPokemon = this.findViewById(R.id.pokemon);
		detailsBar = this.findViewById(R.id.details);
		life=detailsBar.findViewById(R.id.life);
		txtHp=detailsBar.findViewById(R.id.hp);
		txtName=detailsBar.findViewById(R.id.name);

		setClipToPadding(false);
		setClipChildren(false);


		currentHp=hp=pokemon.getHp();

		imgPokemon.setImageDrawable(getContext().getDrawable(pokemon.getImg()));
		life.setMax(hp);
		life.setProgress(currentHp);
		txtName.setText(pokemon.getName());
		txtHp.setText(currentHp+"/"+hp+" HP");

		anim.setTarget(imgPokemon);
		imgPokemon.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				anim.start();
				return false;
			}
		});

	}


}
