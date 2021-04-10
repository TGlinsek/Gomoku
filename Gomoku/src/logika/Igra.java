package logika;

public class Igra {
	
	private Matrika matrika;
	public Igralec igralecNaPotezi;
	public Stanje trenutnoStanje;
	
	
	public Igra () {
		this(15);  // kli�e konstruktor s parametrom
	}
	
	
	public Igra (int stranica) {
		this.matrika = new Matrika(stranica);
		this.igralecNaPotezi = Igralec.BELI;
		this.trenutnoStanje = Stanje.V_TEKU;
	}
}
