package it.speranzon_galligioni.pokemoncemento.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import it.speranzon_galligioni.pokemoncemento.R;
import it.speranzon_galligioni.pokemoncemento.enums.Pokemon;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
	private Context context;
	private Pokemon[] pokemonList;

	public PokemonAdapter(@NonNull Context context, @NonNull Pokemon[] list) {
		super(context, 0, list);
		this.context = context;
		this.pokemonList = list;
	}

	public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
		View listItem = convertView;
		if (listItem == null)
			listItem = LayoutInflater.from(context).inflate(R.layout.item_pokemon, parent, false);


		Pokemon pk = pokemonList[position];

		TextView txtName = listItem.findViewById(R.id.name);
		TextView txtHp = listItem.findViewById(R.id.hp);
		ImageView img = listItem.findViewById(R.id.img);

		txtName.setText(pk.getName());
		txtHp.setText(pk.getHp() + "");
		img.setImageDrawable(context.getDrawable(pk.getImg()));

		return listItem;
	}
}
