package it.speranzon_galligioni.pokemoncemento;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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

		findViewById(R.id.cmd_gioca).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectedPokemon == null)
					return;
				Intent i = new Intent(getApplicationContext(), MainActivity.class);
				i.putExtra("pokemon", selectedPokemon);
				Log.d("PROVA", selectedPokemon.toString());
				startActivity(i);
			}
		});


	}
}
