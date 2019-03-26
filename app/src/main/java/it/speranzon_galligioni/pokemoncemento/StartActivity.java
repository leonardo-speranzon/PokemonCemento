package it.speranzon_galligioni.pokemoncemento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import it.speranzon_galligioni.pokemoncemento.adapter.PokemonAdapter;
import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;

public class StartActivity extends AppCompatActivity {
	ListView pkList, atkList;
	Pokemon selectedPokemon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		pkList = findViewById(R.id.pokemon_list);
		final ArrayAdapter pkAdapter = new PokemonAdapter(this, Pokemon.values());
		pkList.setAdapter(pkAdapter);

		pkList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

		pkList.setOnItemClickListener((parent, view, position, id) -> {
			view.setSelected(true);
			selectedPokemon = Pokemon.values()[position];
		});

		findViewById(R.id.cmd_gioca).setOnClickListener(v -> {
			if (selectedPokemon == null)
				return;
			Intent i = new Intent(this, MainActivity.class);
			i.putExtra("pokemon", selectedPokemon);
			startActivity(i);
		});

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
