package knihovna_JaBa;

public class Romany extends Knihy {

	private String zanry;

	public Romany(String nazev, String autor_jmeno, String autor_prijmeni, int rok, String zanry) {
		super(nazev, autor_jmeno, autor_prijmeni, rok);
		this.zanry = zanry;
	}

	public String getZanry() {
		return zanry;
	}

}
