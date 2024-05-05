package knihovna_JaBa;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Formatter;

public class Database {
	private Map<String, Knihy> mapaKnih;

	Database() {
		mapaKnih = new HashMap<String, Knihy>();
	}

	public boolean setRoman(String nazev, String autor_jmeno, String autor_prijmeni, int rok, String zanry) {
		if (mapaKnih.put(nazev, new Romany(nazev, autor_jmeno, autor_prijmeni, rok, zanry)) == null)

			return true;
		else
			return false;
	}

	public boolean setUcebnice(String nazev, String autor_jmeno, String autor_prijmeni, int rok, int rocnik) {
		if (mapaKnih.put(nazev, new Ucebnice(nazev, autor_jmeno, autor_prijmeni, rok, rocnik)) == null)
			return true;
		else
			return false;
	}

	public Knihy getKniha(String nazev) {
		return mapaKnih.get(nazev);
	}

	public Knihy removeKniha(String nazev) {
		return mapaKnih.remove(nazev);
	}

	public void vypisKnihy(String nazev) {
		if (mapaKnih.get(nazev) == null)
			System.out.println("Kniha neexistuje");
		Knihy e = mapaKnih.get(nazev);
		Formatter fmt = new Formatter();

		if (e instanceof Romany)
			fmt.format("%-30s %10s %15s %10s %15s %15s %10s", e.getNazev(), e.getAutor_jmeno(), e.getAutor_prijmeni(),
					e.getRok(), "Román", ((Romany) e).getZanry(), e.isDostupnost());
		if (e instanceof Ucebnice)
			fmt.format("%-30s %10s %15s %10s %15s %15s %10s", e.getNazev(), e.getAutor_jmeno(), e.getAutor_prijmeni(),
					e.getRok(), "Učebnice", ((Ucebnice) e).getRocnik() + ".", e.isDostupnost());
		System.out.println(fmt);
	}

	public void vypisKnihAutor(String prijmeni, String jmeno) {
		List<Knihy> knihy = new ArrayList<Knihy>(mapaKnih.size());
		knihy.addAll(mapaKnih.values());

		Collections.sort(knihy, new Comparator<Knihy>() {

			@Override
			public int compare(Knihy o1, Knihy o2) {
				return o1.getRok() - (o2.getRok());

			}
		});
		int i = 0;
		for (Knihy e : knihy) {
			if (prijmeni.equals(e.getAutor_prijmeni()) || jmeno.equals(e.getAutor_jmeno())) {
				System.out
						.println("Název: " + e.getNazev() + " Rok: " + e.getRok() + " Dostupnost: " + e.isDostupnost());

				i++;
			}
		}
		if (i == 0)
			System.out.println("Autor nenalezen");
	}

	public void vypisKnihAbecedne() {
		List<Knihy> knihyAbecedne = new ArrayList<Knihy>(mapaKnih.size());
		knihyAbecedne.addAll(mapaKnih.values());

		Collections.sort(knihyAbecedne, new Comparator<Knihy>() {

			@Override
			public int compare(Knihy o1, Knihy o2) {
				return o1.getNazev().compareTo(o2.getNazev());

			}
		});
		Formatter fmm = new Formatter();
		fmm.format("%-30s %10s  %15s %10s %15s %15s %10s", "Název", "Autor", " ", "Rok vydání", "Román/Učebnice",
				"Žánr/Ročník", "Dostupnost");
		System.out.println(fmm);
		for (Knihy e : knihyAbecedne) {
			vypisKnihy(e.getNazev());

		}
	}

	public void vypisPodleZanru(int volba) {

		List<Knihy> knihy = new ArrayList<Knihy>(mapaKnih.size());
		knihy.addAll(mapaKnih.values());
		for (Knihy e : knihy) {

			if (e instanceof Romany) {
				String zanr = ((Romany) e).getZanry();
				int zanry = 0;
				switch (zanr) {
				case "Fantasy":
					zanry = 1;
					break;
				case "Historický":
					zanry = 2;
					break;
				case "Horrrorový":
					zanry = 3;
					break;
				case "Sci-Fi":
					zanry = 4;
					break;
				case "Detektivní":
					zanry = 5;
					break;
				}
				if (volba == zanry)
					System.out.println("Nazev: " + e.getNazev());
			}
		}
	}

	public boolean vypisPodleDostupnosti() {

		List<Knihy> knihy = new ArrayList<Knihy>(mapaKnih.size());
		knihy.addAll(mapaKnih.values());
		boolean nedostupne_not_null = false;
		for (Knihy e : knihy) {

			String dostupnost = e.isDostupnost();

			if (dostupnost == "vypůjčené") {
				System.out.print("Nazev: " + e.getNazev());
				if (e instanceof Romany)
					System.out.println("\t Román");
				if (e instanceof Ucebnice)
					System.out.println("\t Učebnice");

				nedostupne_not_null = true;
			}

		}
		return nedostupne_not_null;
	}

	public void ulozKnihu(String nazev) {

		FileWriter fww;
		try {

			if (mapaKnih.get(nazev) != null) {
				fww = new FileWriter(nazev + ".txt");

				Knihy e = mapaKnih.get(nazev);
				if (e instanceof Romany)
					fww.write(e.getNazev() + "\t" + e.getAutor_jmeno() + "\t" + e.getAutor_prijmeni() + "\t"
							+ e.getRok() + "\tRomán\t" + ((Romany) e).getZanry() + "\t" + e.isDostupnost());
				if (e instanceof Ucebnice)
					fww.write(e.getNazev() + "\t" + e.getAutor_jmeno() + "\t" + e.getAutor_prijmeni() + "\t"
							+ e.getRok() + "\tUčebnice \t" + ((Ucebnice) e).getRocnik() + "\t" + e.isDostupnost());

				System.out.print("Soubor vytvořen");
				fww.close();

			} else {
				System.out.print("Kniha neexistuje");

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block

			System.out.print("Uložení se nezdařilo");

		}

	}

	public void nactiKnihu(String nazev) {
		FileReader fr = null;
		BufferedReader in = null;

		try {
			fr = new FileReader(nazev + ".txt");

			in = new BufferedReader(fr);
			Formatter fmt = new Formatter();
			String radek = in.readLine();
			String oddelovac = "\t";
			String[] castiTextu = radek.split(oddelovac);
			int rok = Integer.valueOf(castiTextu[3]);
			if (castiTextu[4].equals("Román")) {
				Romany e = new Romany(castiTextu[0], castiTextu[1], castiTextu[2], rok, castiTextu[5]);
				if (castiTextu[6].equals("dostupné"))
					e.setDostupnost(true);
				else
					e.setDostupnost(false);
				fmt.format("%-30s %10s %15s %10s  %15s %10s", e.getNazev(), e.getAutor_jmeno(), e.getAutor_prijmeni(),
						e.getRok(), e.getZanry(), e.isDostupnost());

			}
			if (castiTextu[4].equals("Učebnice")) {
				int rocnik = Integer.valueOf(castiTextu[5]);
				Ucebnice e = new Ucebnice(castiTextu[0], castiTextu[1], castiTextu[2], rok, rocnik);
				if (castiTextu[6].equals("dostupné"))
					e.setDostupnost(true);
				else
					e.setDostupnost(false);
				fmt.format("%-30s %10s %15s %10s %15s %10s", e.getNazev(), e.getAutor_jmeno(), e.getAutor_prijmeni(),
						e.getRok(), e.getRocnik(), e.isDostupnost());

			}

			System.out.println(fmt);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			System.out.println("Soubor neexistuje");

		} catch (IOException e1) {
			// TODO Auto-generated catch block

		} finally {
			try {
				if (in != null) {
					in.close();
					fr.close();
				}
			} catch (IOException e) {
				System.out.println("Soubor  nelze zavřít");

			}
		}

	}

	public List<Knihy> list() {
		List<Knihy> knihyList = new ArrayList<Knihy>(mapaKnih.size());
		knihyList.addAll(mapaKnih.values());
		return knihyList;
	}

}