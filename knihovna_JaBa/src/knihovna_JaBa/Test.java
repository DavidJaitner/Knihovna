package knihovna_JaBa;

import java.io.IOException;
//import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class Test {

	public static int pouzeCelaCisla(Scanner sc) {
		int cislo = 0;

		try {

			cislo = sc.nextInt();

		} catch (Exception e) {

			System.out.println("Zadejte prosím celé číslo ");
			sc.nextLine();
			cislo = pouzeCelaCisla(sc);
		}

		return cislo;
	}

	public static int pouzeCelaCislaVyber(Scanner sc, int min, int max) {
		int cislo = 0;
		boolean check = false;

		do {
			cislo = pouzeCelaCisla(sc);

			if (min <= cislo && cislo <= max)
				check = true;
			else
				System.out.println("Zadejete jedno z čísel z výběru");
			sc.nextLine();
		} while (check == false);

		return cislo;
	}

	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);

		int volba;
		boolean run = true;

		Connect concon = new Connect();

		concon.connect();
		concon.createTable();
		Database Knihovna = new Database();
		if (concon.getRecord() != null) {
			Knihovna = concon.getRecord();
		}
		/*
		 * Knihovna.setRoman("Tři mušketýři", "Alexandre", "Dumas", 1844, "Historický");
		 * Knihovna.setRoman("Vražda v Orient expresu", "Agatha", "Christie", 1934,
		 * "Detektivní"); Knihovna.setRoman("Deset malých černoušků", "Agatha",
		 * "Christie", 1939, "Detektivní"); Knihovna.setRoman("To", "Stephen", "King",
		 * 1986, "Horrrorový"); Knihovna.setRoman("Zelená míle", "Stephen", "King",
		 * 1996, "Fantasy"); Knihovna.setUcebnice("ČESKÝ JAZYK ", "Ludmila",
		 * "Konopková", 2022, 2); Knihovna.setUcebnice("MATEMATICKÉ MINUTOVKY 2 (1)",
		 * "Josef", "Molnár", 2007, 2);
		 * Knihovna.setUcebnice("MATEMATICKÉ MINUTOVKY 3 (1)", "Josef", "Molnár", 2016,
		 * 3); Knihovna.setUcebnice("MATEMATICKÉ MINUTOVKY 3 (2)", "Josef", "Molnár",
		 * 2019, 3);
		 */
		while (run) {

			System.out.println("Vyberte požadovanou činnost:");
			System.out.println("1 ... Vytvoření nové knihy");
			System.out.println("2 ... Úprava knihy");
			System.out.println("3 ... Smazání knihy");
			System.out.println("4 ... Vypůjčení/vrácení knihy");
			System.out.println("5 ... Výpis všech knih");
			System.out.println("6 ... Vyhledání knihy");
			System.out.println("7 ... Výpis všech knih autora");
			System.out.println("8 ... Výpis románů podle žánru");
			System.out.println("9 ... Výpis vypůjčených knih");
			System.out.println("10 ... Uložení knihy do souboru");
			System.out.println("11 ... Načíst informace o knize ze souboru");
			System.out.println("0 ... Ukončení aplikace");
			System.out.println("");

			volba = pouzeCelaCislaVyber(sc, 0, 11);

			switch (volba) {
			case 1:
				System.out.println("Zadejte číslo typu knihy:");
				System.out.println("1) Román");
				System.out.println("2) Učebnice");

				volba = pouzeCelaCislaVyber(sc, 1, 2);

				System.out.println("Zadejte název díla: ");
				String nazev = sc.nextLine();

				System.out.println("Zadejte jméno autora: ");
				String jmeno = sc.nextLine();

				System.out.println("Zadejte příjmení autora: ");
				String prijmeni = sc.nextLine();

				System.out.println("Zadejte rok vydání: ");
				int rok;
				rok = pouzeCelaCisla(sc);

				switch (volba) {
				case 1:
					System.out.println("Vyberte číslo žánru díla: ");
					System.out.println("1) Fantasy román");
					System.out.println("2) Historický román");
					System.out.println("3) Horrrorový román");
					System.out.println("4) Sci-Fi román");
					System.out.println("5) Detektivní román");

					String zanry = null;

					volba = pouzeCelaCislaVyber(sc, 1, 5);

					switch (volba) {
					case 1:
						zanry = "Fantasy";
						break;
					case 2:
						zanry = "Historický";
						break;
					case 3:
						zanry = "Horrrorový";
						break;
					case 4:
						zanry = "Sci-Fi";
						break;
					case 5:
						zanry = "Detektivní";
						break;

					}
					if (!Knihovna.setRoman(nazev, jmeno, prijmeni, rok, zanry))
						System.out.println("Kniha s tímto názvem již existuje");
					else
						System.out.println("Kniha vytvořena");
					break;

				case 2:
					System.out.println("Zadejte číslo ročníku, pro který je učebnice určena: ");
					int rocnik;
					rocnik = pouzeCelaCisla(sc);
					if (!Knihovna.setUcebnice(nazev, jmeno, prijmeni, rok, rocnik))
						System.out.println("Kniha s tímto názvem již existuje");
					else
						System.out.println("Kniha vytvořena");
					break;
				}
				System.out.println("");
				break;

			case 2:
				System.out.println("Zadejte název knihy, kterou chcete upravit");
				nazev = sc.nextLine();
				if (Knihovna.getKniha(nazev) != null) {
					Knihovna.vypisKnihy(nazev);
					System.out.println("Chcete změnit:");
					System.out.println("1) Jméno autora");
					System.out.println("2) Rok vydani");
					System.out.println("3) Stav dostupnosti");
					volba = pouzeCelaCislaVyber(sc, 1, 3);
					Knihy k = Knihovna.getKniha(nazev);
					switch (volba) {
					case 1:
						System.out.println("Zadejte nové křestní jméno autora");
						k.setAutor_jmeno(sc.nextLine());
						System.out.println("Zadejte nové příjmení autora");
						k.setAutor_prijmeni(sc.nextLine());
						System.out.println("Jméno změněno");

						break;
					case 2:
						System.out.println("Zadejte nový rok vydání");
						k.setRok(pouzeCelaCisla(sc));
						System.out.println("Rok vydání změněn");

						break;
					case 3:
						System.out.println("Nastavte stav dostupnosti knihy");
						System.out.println("1) dostupné");
						System.out.println("2) vypůjčené");
						volba = pouzeCelaCislaVyber(sc, 1, 2);
						switch (volba) {
						case 1:
							if (volba == 1) {
								k.setDostupnost(true);

							}
							break;

						case 2:
							if (volba == 2) {
								k.setDostupnost(false);

							}
							break;
						}

					}
				}

				else
					System.out.println("Kniha s tímto názvem neexistuje");
				System.out.println("");
				break;

			case 3:
				System.out.println("Zadejte název knihy, kterou chcete vymazat");
				nazev = sc.nextLine();
				if (Knihovna.getKniha(nazev) != null) {
					Knihovna.removeKniha(nazev);
					System.out.println("Kniha " + nazev + " odstraněna");
				} else
					System.out.println("Kniha s tímto názvem neexistuje");
				System.out.println("");
				break;

			case 4:

				System.out.println("Zadejte název knihy, které chcete nastavit stav dostupnosti");
				nazev = sc.nextLine();

				if (Knihovna.getKniha(nazev) != null) {
					System.out.println("Nastavte stav dostupnosti knihy");
					System.out.println("1) dostupné");
					System.out.println("2) vypůjčené");
					volba = pouzeCelaCislaVyber(sc, 1, 2);
					Knihy k = Knihovna.getKniha(nazev);
					switch (volba) {
					case 1:
						if (volba == 1) {
							k.setDostupnost(true);
							System.out.println("Kniha " + k.getNazev() + " je nyní dostupná");
						}
						break;

					case 2:
						if (volba == 2) {
							k.setDostupnost(false);
							System.out.println("Kniha " + k.getNazev() + " je nyní vypůjčená");
						}
						break;
					}
				} else
					System.out.println("Kniha s tímto názvem neexistuje");
				System.out.println("");
				break;

			case 5:

				Knihovna.vypisKnihAbecedne();
				System.out.println("");
				break;

			case 6:
				System.out.println("Zadejte název knihy, kterou chcete vyhledat");
				nazev = sc.nextLine();
				if (Knihovna.getKniha(nazev) != null) {
					Knihovna.vypisKnihy(nazev);
				} else
					System.out.println("Kniha s tímto názvem neexistuje");
				System.out.println("");
				break;

			case 7:
				System.out.println("Zadejte příjmení autora");
				prijmeni = sc.nextLine();
				System.out.println("Zadejte křestní jméno autora");
				jmeno = sc.nextLine();
				Knihovna.vypisKnihAutor(prijmeni, jmeno);
				System.out.println("");
				break;

			case 8:
				System.out.println("Vyberte číslo žánru");
				System.out.println("1) Fantasy román");
				System.out.println("2) Historický román");
				System.out.println("3) Horrrorový román");
				System.out.println("4) Sci-Fi román");
				System.out.println("5) Detektivní román");
				volba = pouzeCelaCislaVyber(sc, 1, 5);
				Knihovna.vypisPodleZanru(volba);
				System.out.println("");
				break;

			case 9:

				boolean e = Knihovna.vypisPodleDostupnosti();
				if (e == false)
					System.out.println("Není žádná vypůjčená kniha");
				System.out.println("");
				break;
			case 10:
				System.out.println("Zadejte název knihy, kterou chcete uložit do souboru");
				nazev = sc.nextLine();
				Knihovna.ulozKnihu(nazev);

				System.out.println("");
				break;
			case 11:
				System.out.println("Zadejte název knihy, kterou chcete načíst ze souboru");
				nazev = sc.nextLine();
				Knihovna.nactiKnihu(nazev);

				System.out.println("");
				break;
			case 0:
				concon.deleteAllRecords();
				List<Knihy> knihy = Knihovna.list();
				for (Knihy f : knihy) {
					concon.insertRecord(f);
				}
				run = false;
				break;
			}
		}
	}
}
