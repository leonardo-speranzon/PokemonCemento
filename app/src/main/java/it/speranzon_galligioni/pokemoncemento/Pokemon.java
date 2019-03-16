package it.speranzon_galligioni.pokemoncemento;

public enum Pokemon{
	GRIGINOMON("Grigimon",120,R.drawable.griginomon),
	CEMENTOKARP("Cementokarp",50,R.drawable.cementokarp);

	private int hp,img;
	private String name;
	Pokemon(String name,int hp, int img) {
		this.hp = hp;
		this.img = img;
		this.name = name;
	}

	public int getHp() {
		return hp;
	}
	public int getImg() {
		return img;
	}
	public String getName() {
		return name;
	}
}