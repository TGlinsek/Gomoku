package logika;

public enum Igralec {
	
	BELI, CRNI;
	
	
	public Igralec pridobiNasprotnika() {
		return (this == BELI ? CRNI : BELI);
	}
	
	
	public Polje barvaPoteze() {
		return (this == BELI ? Polje.BELO : Polje.CRNO);
	}
}
