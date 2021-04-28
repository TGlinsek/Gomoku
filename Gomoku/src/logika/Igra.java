package logika;

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
	public static int N = 15;
	
	
	public Igra() {
		this(N);  // kli�e konstruktor s parametrom
	}
	
	
	public Igra(int stranica) {
		this.matrika = new Matrika(stranica);
		this.igralecNaPotezi = Igralec.CRNI;  // baje �rni vedno za�ne
		this.trenutnoStanje = Stanje.V_TEKU;
		this.velikost = matrika.vrniDimenzije();
	}
	
	
	public boolean igraJePolna() {
		return this.matrika.matrikaJePolna();
	}
	
	
	public Polje vrniClen(Koordinati k) {
		return this.matrika.vrniClen(k);
	}
	
	
	public void spremeniStanjeIgre(Koordinati k) {
		// if (zmagovalneVrste.size() != 0) {
		if (matrika.imamoResitev(k).size() != 0) {
			switch (matrika.vrniClen(k)) {
				case CRNO: trenutnoStanje = Stanje.ZMAGA_CRNI;
				case BELO: trenutnoStanje = Stanje.ZMAGA_BELI;
				case PRAZNO: assert false; // tole nevem ce je potrebno, ker se if itak ne izpolni, ce je polje prazno. Ce umaknem, pa tezi
			}
		}
		else if (matrika.matrikaJePolna()) {
			trenutnoStanje = Stanje.NEODLOCENO;
		}
		else trenutnoStanje = Stanje.V_TEKU;
	}
	
	
	public boolean igraj(Koordinati k) {  // metoda .igraj() vrne true, �e je poteza ustrezna, druga�e false
		if (!matrika.dodajKamen(k, igralecNaPotezi.barvaPoteze())) return false;  // .dodajKamen() vrne true, �e je poteza bila ustrezna, druga�e false
		zmagovalneVrste = this.resitve(k);
		spremeniStanjeIgre(k);
		igralecNaPotezi = igralecNaPotezi.pridobiNasprotnika();
		return true;
	}
	
	
	public List<Koordinati> vrniVsaPraznaPolja() {
		return this.matrika.vrniVsaPraznaPolja();
	}
	
	
	public Set<Vrsta> resitve(Koordinati k) {
		return this.matrika.imamoResitev(k);
	}
}
