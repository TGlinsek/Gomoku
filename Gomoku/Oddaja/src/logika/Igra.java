package logika;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import splosno.Koordinati;

public class Igra {
	
	public Matrika matrika; 
	public Igralec igralecNaPotezi;
	public Stanje trenutnoStanje;
	
	public Set<Vrsta> zmagovalneVrste;
	public int velikost;
	private static int privzetaVelikost = 15;
	public static int dolzinaVrste = 5;
	
	public Koordinati zadnjaIgranaPoteza;  // zadnja poteza igralca, ki je bil nazadnje na vrsti
	// to definiramo samo zato, da se vsaka nova poteza obarva
	
	
	// 1. KONSTRUKTOR  s parametrom velikosti igre
	public Igra(int stranica) {
		this.matrika = new Matrika(stranica);
		this.igralecNaPotezi = Igralec.CRNI;  // crni zacne
		this.trenutnoStanje = Stanje.V_TEKU;
		this.velikost = stranica;
		this.zmagovalneVrste = Collections.<Vrsta>emptySet();  // to je pomembno, saj drugače zmagovalneVrste == null, kar pa ni dobro, če hočemo recimo pobarvati ozadja vrst
	}
	
	// 2. KONSTRUKTOR  brez paraetra - vzame default velikost
	public Igra() {
		this(privzetaVelikost);  // klice konstruktor s parametrom
	}
	
	// 3. KONSTRUKTOR  s parametrom igra in naredi njeno kopijo (rabimo za inteligenco)
	public Igra(Igra igra) {
		Polje[][] mat = igra.matrika.pridobiMatriko();
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
	
	
	public boolean igraJePolna() {
		return this.matrika.matrikaJePolna();
	}
	
	
	public Polje vrniClen(Koordinati k) {
		return this.matrika.vrniClen(k);
	}
	
	
	public void spremeniStanjeIgre() {
		if (zmagovalneVrste.size() != 0) {
			switch (this.igralecNaPotezi) {
			case CRNI: trenutnoStanje = Stanje.ZMAGA_CRNI; break;
			case BELI: trenutnoStanje = Stanje.ZMAGA_BELI; break;
			}
		}
		else if (igraJePolna()) {
			trenutnoStanje = Stanje.NEODLOCENO;
		}
		else trenutnoStanje = Stanje.V_TEKU;
	}
	
	
	public boolean odigraj(Koordinati k) {  // metoda .odigraj() vrne true, �e je poteza ustrezna, druga�e false
		if (k == null) throw new java.lang.RuntimeException("Koordinate ne morejo biti null!");
		if (!matrika.dodajKamen(k, igralecNaPotezi.barvaPoteze())) return false;  // .dodajKamen() vrne true, �e je poteza bila ustrezna, druga�e false
		zmagovalneVrste = this.resitve(igralecNaPotezi, k);
		spremeniStanjeIgre();
		zadnjaIgranaPoteza = k;
		igralecNaPotezi = igralecNaPotezi.pridobiNasprotnika();
		return true;
	}
	
	
	public List<Koordinati> vrniVsaPraznaPolja() {
		return this.matrika.vrniVsaPraznaPolja();
	}
	
	
	public Set<Vrsta> resitve(Igralec p, Koordinati k) {  // koordinate, kamor smo nazadnje postavili figuro
		return this.matrika.vrniResitev(p, k);
	}  // vrne prazno, če ni rešitev (če ni konec igre), drugače vrne vse rešitve


	@Override
	public String toString() {
		return this.matrika.toString();
	}
}