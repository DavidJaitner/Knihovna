package knihovna_JaBa;

public class Ucebnice extends Knihy {

	int rocnik;

	public Ucebnice(String nazev, String autor_jmeno, String autor_prijmeni, int rok, int rocnik) {
		super(nazev, autor_jmeno, autor_prijmeni, rok);
		this.rocnik = rocnik;
	}

	public int getRocnik() {
		return rocnik;
	}

}
