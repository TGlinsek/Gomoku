package logika;

import splosno.Koordinati;

public class Matrika {
	
	private Polje[][] matrika;
	private int stranica;
	
	
	public Matrika(int stranica) {
		this.stranica = stranica;
		this.matrika = new Polje[stranica][stranica];
	}
	
	
	public int vrniDimenzije() {
		return stranica;
	}
	
	
	public void izprazniMatriko() {
		for (int i = 0; i < this.stranica; i++) {
			for (int j = 0; j < this.stranica; j++) {
				this.matrika[i][j] = Polje.PRAZNO;
			}
		}
	}
	
	
	public Polje[][] pridobiMatriko() {
		return this.matrika;
	}
	
	
	public Polje vrniClen(Koordinati k) {
		return this.matrika[k.getY()][k.getX()];
	}
	
	
	public void zamenjajClen(Koordinati k, Polje novClen) {
		this.matrika[k.getY()][k.getX()] = novClen;
	}
	
	
	public boolean matrikaJePolna() {  // vrne true, èe nobeno polje ni prazno
		boolean obstajaPraznoPolje = false;
		for (int i = 0; i < this.stranica; i++) {
			for (int j = 0; j < this.stranica; j++) {
				if (this.matrika[i][j] == Polje.PRAZNO) {
					obstajaPraznoPolje = true;
				}
			}
		}
		return !obstajaPraznoPolje;
	} 
}
