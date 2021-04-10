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
	
	
	public boolean igraj(Koordinati k) {
		if (matrika.vrniClen(k) == Polje.PRAZNO) {
			matrika.zamenjajClen(k, igralecNaPotezi.barvaPoteze());
			igralecNaPotezi = igralecNaPotezi.pridobiNasprotnika();
			return true;
		}
		else {
			return false;
		}
	}
}
