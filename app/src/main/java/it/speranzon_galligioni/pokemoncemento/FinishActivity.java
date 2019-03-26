package it.speranzon_galligioni.pokemoncemento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;

public class FinishActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_finish);

		((TextView) findViewById(R.id.msg)).setText(getIntent().getStringExtra("frase"));
		((ImageView) findViewById(R.id.img_pokemon)).setImageDrawable(getDrawable(((Pokemon) getIntent().getSerializableExtra("pokemon")).getImg()));
		findViewById(R.id.cmd_rigioca).setOnClickListener((v) -> startActivity(new Intent(this, StartActivity.class)));

		hideSystemUI();
	}

	private void hideSystemUI() {
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		getWindow().getDecorView().setSystemUiVisibility(
				View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
						| View.SYSTEM_UI_FLAG_IMMERSIVE
						| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		onWindowFocusChanged(true);
	}

	@Override
	public void onBackPressed() {
		//NON FA NIENTE
	}
}
