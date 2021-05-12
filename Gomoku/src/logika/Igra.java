package logika;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Koordinati;

public class Igra {
	
	public Matrika matrika; // prej je bil tale private...a mora bit?
	public Igralec igralecNaPotezi;
	public Stanje trenutnoStanje;
	
	//public List<Vrsta> okoliskeVrste;
	public Set<Vrsta> zmagovalneVrste;
	public int velikost;
	private static int privzetaVelikost = 15;
	public static int dolzinaVrste = 5;
	public static final List<Vrsta> VRSTE = new LinkedList<Vrsta>();
	
	
	public Igra(int stranica) {
		this.matrika = new Matrika(stranica);
		this.igralecNaPotezi = Igralec.CRNI;  // baje crni vedno zacne
		this.trenutnoStanje = Stanje.V_TEKU;
		// this.velikost = matrika.vrniDimenzije();  // matrika mora prevzeti velikost matrike in ne obratno
		this.velikost = stranica;
		nastaviVrste();
	}
	
	
	@Override
	public String toString() {
		return this.matrika.toString();
	}
	
	
	public Igra() {
		this(privzetaVelikost);  // klice konstruktor s parametrom
	}
	
	public Igra(Igra igra) {
		Polje[][] mat = igra.matrika.pridobiMatriko();
		// this.velikost = matrika.vrniDimenzije();
		this.velikost = igra.velikost;
		this.matrika = new Matrika(velikost);
		Polje[][] kopijaMat = this.matrika.pridobiMatriko();
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				kopijaMat[i][j] = mat[i][j];
			}
		}
		this.igralecNaPotezi = igra.igralecNaPotezi;
		this.trenutnoStanje = igra.trenutnoStanje;
	}
		
	public void nastaviVrste() {  // vse vrste v igri
		int[][] smer = {{1,0}, {0,1}, {1,1}, {1,-1}};
		for (int x = 0; x < velikost; x++) {
			for (int y = 0; y < velikost; y++) {
				for (int[] s : smer) {
					int dx = s[0];
					int dy = s[1];
					// če je skrajno polje terice še na plošči, jo dodamo med terice
					if ((0 <= x + (dolzinaVrste-1) * dx) && (x + (dolzinaVrste-1) * dx < velikost) && 
						(0 <= y + (dolzinaVrste-1) * dy) && (y + (dolzinaVrste-1) * dy < velikost)) {
						Koordinati[] vrsta = new Koordinati[dolzinaVrste];
						for (int k = 0; k < dolzinaVrste; k++) {
							vrsta[k] = new Koordinati(x + k*dx, y + k*dy);
						}
						VRSTE.add(new Vrsta(vrsta));
					}
				}
			}
		}
	}
	
	public boolean igraJePolna() {
		return this.matrika.matrikaJePolna();
	}
	
	
	public Polje vrniClen(Koordinati k) {
		return this.matrika.vrniClen(k);
	}
	
	private Igralec cigavaVrsta(Vrsta t) {
		int belih = 0;
		int crnih = 0;
		for (int k = 0; k < dolzinaVrste && (belih == 0 || crnih == 0); k++) {
			switch (matrika.vrniClen(t.tabelaKoordinat[k])) {
			case CRNO: crnih += 1; break;
			case BELO: belih += 1; break;
			case PRAZNO: break;
			}
		}
		if (crnih == dolzinaVrste) { return Igralec.CRNI; }
		else if (belih == dolzinaVrste) { return Igralec.BELI; }
		else { return null; }
	}
	
	public Vrsta zmagovalnaVrsta() {
		for (Vrsta t : VRSTE) {
			Igralec lastnik = cigavaVrsta(t);
			if (lastnik != null) return t;
		}
		return null;
	}
	
	public void spremeniStanje() {
		// Ali imamo zmagovalca?
		Vrsta t = zmagovalnaVrsta();
		if (t != null) {
			// System.out.println(matrika.vrniClen(t.tabelaKoordinat[0]));
			switch (matrika.vrniClen(t.tabelaKoordinat[0])) {
			case CRNO: trenutnoStanje = Stanje.ZMAGA_CRNI;
			case BELO: trenutnoStanje = Stanje.ZMAGA_BELI;
			case PRAZNO: assert false;
			}
		}
		// Ali imamo kakšno prazno polje?
		// Če ga imamo, igre ni konec in je nekdo na potezi
		for (int i = 0; i < velikost; i++) {
			for (int j = 0; j < velikost; j++) {
				if (matrika.vrniClen(new Koordinati(i,j)) == Polje.PRAZNO) trenutnoStanje = Stanje.V_TEKU;
				return;
			}
		}
		// Polje je polno, rezultat je neodločen
		trenutnoStanje = Stanje.NEODLOCENO;
	}
	
//	public void spremeniStanjeIgre(Koordinati k) {
//		// if (zmagovalneVrste.size() != 0) {
//		if (matrika.imamoResitev(k).size() != 0) {
//			switch (matrika.vrniClen(k)) {
//				case CRNO: trenutnoStanje = Stanje.ZMAGA_CRNI;
//				case BELO: trenutnoStanje = Stanje.ZMAGA_BELI;
//				case PRAZNO: assert false; // tole nevem ce je potrebno, ker se if itak ne izpolni, ce je polje prazno. Ce umaknem, pa tezi
//			}
//		}
//		else if (matrika.matrikaJePolna()) {
//			trenutnoStanje = Stanje.NEODLOCENO;
//		}
//		else trenutnoStanje = Stanje.V_TEKU;
//	}
	
	
	public boolean igraj(Koordinati k) {  // metoda .igraj() vrne true, �e je poteza ustrezna, druga�e false
		if (!matrika.dodajKamen(k, igralecNaPotezi.barvaPoteze())) return false;  // .dodajKamen() vrne true, �e je poteza bila ustrezna, druga�e false
		//zmagovalneVrste = this.resitve(k);
		//spremeniStanje();
		igralecNaPotezi = igralecNaPotezi.pridobiNasprotnika();
		return true;
	}
	
	
	public List<Koordinati> vrniVsaPraznaPolja() {
		return this.matrika.vrniVsaPraznaPolja();
	}
	
	
//	public Set<Vrsta> resitve(Koordinati k) {
//		return this.matrika.imamoResitev(k);
//	}
}
