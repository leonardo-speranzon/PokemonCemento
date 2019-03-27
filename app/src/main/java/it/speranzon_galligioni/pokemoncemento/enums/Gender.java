package it.speranzon_galligioni.pokemoncemento.enums;

import it.speranzon_galligioni.pokemoncemento.R;

public enum Gender {
	M(R.drawable.trainerm_0, R.drawable.trainerm_1, R.drawable.trainerm_2),
	F(R.drawable.trainerf_0, R.drawable.trainerf_1, R.drawable.trainerf_2);

	private int img0, img1, img2;

	/**
	 * Costruttore di Gender
	 *
	 * @param img0 immagine in posizione 0
	 * @param img1 immagine in posizione 1
	 * @param img2 immagine in posizione 2
	 */
	Gender(int img0, int img1, int img2) {
		this.img0 = img0;
		this.img1 = img1;
		this.img2 = img2;
	}

	/**
	 * Ritorna l'immagine in posizione 0
	 *
	 * @return immagine in posizione 0
	 */
	public int getImg0() {
		return img0;
	}

	/**
	 * Ritorna l'immagine in posizione 1
	 *
	 * @return immagine in posizione 1
	 */
	public int getImg1() {
		return img1;
	}

	/**
	 * Ritorna l'immagine in posizione 2
	 *
	 * @return immagine in posizione 2
	 */
	public int getImg2() {
		return img2;
	}
}
