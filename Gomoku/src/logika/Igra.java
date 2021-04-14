package logika;

import splosno.Koordinati;

public class Igra {
	
	private Matrika matrika;
	public Igralec igralecNaPotezi;
	public Stanje trenutnoStanje;
	
	
	public Igra() {
		this(15);  // klièe konstruktor s parametrom
	}
	
	
	public Igra(int stranica) {
		this.matrika = new Matrika(stranica);
		this.igralecNaPotezi = Igralec.BELI;
		this.trenutnoStanje = Stanje.V_TEKU;
	}
	
	
	public int dimenzijaMatrike() {
		return matrika.vrniDimenzije();
	}
	
	
	public boolean igraJePolna() {
		return this.matrika.matrikaJePolna();
	}
	
	
	public void spremeniStanjeIgre(Koordinati k) {
		if (matrika.imamoResitev(k)) {
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
	
	
	public boolean igraj(Koordinati k) {  // metoda .igraj() vrne true, èe je poteza ustrezna, drugaèe false
		if (!matrika.dodajKamen(k, igralecNaPotezi.barvaPoteze())) return false;  // .dodajKamen() vrne true, èe je poteza bila ustrezna, drugaèe false
		spremeniStanjeIgre(k);
		igralecNaPotezi = igralecNaPotezi.pridobiNasprotnika();
		return true;
	}
}
