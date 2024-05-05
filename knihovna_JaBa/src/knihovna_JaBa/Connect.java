package knihovna_JaBa;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {

	private Connection conn;

	public boolean connect() {
		conn = null;
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:myDB.db");
			if (conn != null) {

			} else {
				System.out.println("Nepodařilo se vytvořit připojení k databázi.");
				return false;
			}
		} catch (SQLException e) {
			System.out.println("Chyba při vytváření připojení k databázi: " + e.getMessage());
			return false;
		}
		return true;
	}

	public void disconnect() {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	public boolean createTable() {
		if (conn == null)
			return false;
		String sql = "CREATE TABLE IF NOT EXISTS knihovna (" + "nazev varchar(255) PRIMARY KEY,"
				+ "jmeno_autor varchar(255) NOT NULL," + "prijmeni_autor varchar(255) NOT NULL, "
				+ "rok smallint NOT NULL , " + "typ varchar(255), " + "rocnik tinyint, " + "zanr varchar(255), "
				+ "dostupnost bit" + " );";
		try {
			Statement stmt = conn.createStatement();
			stmt.execute(sql);
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return false;
	}

	public void insertRecord(Knihy f) {
		String sql = "INSERT INTO knihovna (nazev,jmeno_autor,prijmeni_autor,rok,typ,rocnik,zanr,dostupnost) VALUES(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, f.getNazev());
			pstmt.setString(2, f.getAutor_jmeno());
			pstmt.setString(3, f.getAutor_prijmeni());
			pstmt.setInt(4, f.getRok());
			if (f instanceof Romany) {
				pstmt.setString(5, "Román");
				pstmt.setString(7, ((Romany) f).getZanry());
			}
			if (f instanceof Ucebnice) {
				pstmt.setString(5, "Učebnice");
				pstmt.setInt(6, ((Ucebnice) f).getRocnik());
			}
			switch (f.isDostupnost()) {
			case "dostupné":
				pstmt.setInt(8, 1);
				break;
			case "vypůjčené":
				pstmt.setInt(8, 0);
				break;
			}

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public Database getRecord() {
		Database pomocnaMapa = new Database();

		try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT * FROM knihovna")) {

			while (rs.next()) {

				String nazev = rs.getString("nazev");
				String jmeno = rs.getString("jmeno_autor");
				String prijmeni = rs.getString("prijmeni_autor");
				int rok = rs.getInt("rok");
				String typ = rs.getString("typ");
				if (typ.equals("Román")) {
					String zanr = rs.getString("zanr");
					pomocnaMapa.setRoman(nazev, jmeno, prijmeni, rok, zanr);
				}
				if (typ.equals("Učebnice")) {
					int rocnik = rs.getInt("rocnik");
					pomocnaMapa.setUcebnice(nazev, jmeno, prijmeni, rok, rocnik);
				}
				Knihy k = pomocnaMapa.getKniha(nazev);
				int Dostupnost = rs.getInt("dostupnost");
				switch (Dostupnost) {
				case 0:
					k.setDostupnost(false);
					break;
				case 1:
					k.setDostupnost(true);
					break;

				}

			}
			return pomocnaMapa;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	public void deleteAllRecords() {

		String sql = "DELETE FROM knihovna";
		try {
			Statement stmt = conn.createStatement();
			int affectedRows = stmt.executeUpdate(sql);

			if (affectedRows > 0) {

			}
		} catch (SQLException e) {
			System.out.println("Chyba při přepisování záznamů z tabulky: " + e.getMessage());
		}
	}

}
