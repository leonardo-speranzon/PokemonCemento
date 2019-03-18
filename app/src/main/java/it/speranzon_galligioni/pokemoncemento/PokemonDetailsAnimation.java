package it.speranzon_galligioni.pokemoncemento;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PokemonDetailsAnimation extends Animation {
	private ProgressBar life;
	private TextView hp;
	private float maxHp;
	private float from;
	private float to;

	public PokemonDetailsAnimation(ConstraintLayout detailsBar, float from, float to) {
		super();
		life = detailsBar.findViewById(R.id.life);
		hp = detailsBar.findViewById(R.id.hp);
		maxHp = life.getMax();
		this.from = from;
		this.to = to;

	}

	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		super.applyTransformation(interpolatedTime, t);
		float value = from + (to - from) * interpolatedTime;

		if ((value / maxHp) <= 0.25)
			life.setProgressTintList(ColorStateList.valueOf(Color.RED));
		else if ((value / maxHp) <= 0.5)
			life.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));
		hp.setText(((int) (value / 100)) + "/" + ((int) (maxHp / 100)) + " HP");
		life.setProgress((int) value);
	}

}