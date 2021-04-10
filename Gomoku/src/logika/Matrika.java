package logika;

import splosno.Koordinati;

public class Matrika {
	
	private Polje[][] matrika;
	private int stranica;
	
	
	public Matrika (int stranica) {
		this.stranica = stranica;
		this.matrika = new Polje[stranica][stranica];
	}
	
	
	public Polje vrniClen (Koordinati k) {
		return this.matrika[k.getY()][k.getX()];
	}
	
	
	public void zamenjajClen (Koordinati k, Polje novClen) {
		this.matrika[k.getY()][k.getX()] = novClen;
	}
}
