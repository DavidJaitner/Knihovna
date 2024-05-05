
package knihovna_JaBa;

public class Knihy {

	private String nazev;
	private String autor_jmeno;
	private String autor_prijmeni;
	private int rok;
	public boolean dostupnost = true;

	public Knihy(String nazev, String autor_jmeno, String autor_prijmeni, int rok ) {
		this.nazev = nazev;
		this.autor_jmeno = autor_jmeno;
		this.autor_prijmeni = autor_prijmeni;
		this.rok = rok;
		
	}

	public String getNazev() {
		return nazev;
	}

	public String getAutor_jmeno() {
		return autor_jmeno;
	}

	public String getAutor_prijmeni() {
		return autor_prijmeni;
	}

	public int getRok() {
		return rok;
	}

	public void setAutor_jmeno(String autor_jmeno) {
		this.autor_jmeno = autor_jmeno;
	}

	public void setAutor_prijmeni(String autor_prijmeni) {
		this.autor_prijmeni = autor_prijmeni;
	}

	public void setRok(int rok) {
		this.rok = rok;
	}

	public String isDostupnost() {
		if (dostupnost) {
			return "dostupné";
		} else
			return "vypůjčené";
	}

	public boolean setDostupnost(boolean dostupnost) {
		this.dostupnost = dostupnost;
		return this.dostupnost;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}


	
}