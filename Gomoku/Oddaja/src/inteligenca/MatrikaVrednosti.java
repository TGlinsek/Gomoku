package inteligenca;

import java.util.Arrays;

import splosno.Koordinati;

public class MatrikaVrednosti {
	private Vrednost[][] matrika;
	private int stranica;
	
	
	public MatrikaVrednosti(int stranica) {
		this.stranica = stranica;
		this.matrika = new Vrednost[stranica][stranica];
	}
	
	
	@Override
	public String toString() {
		// return Arrays.deepToString(this.matrika);  // to izpiše celotno matriko v eni vrstici
		String niz = "";
		for (Vrednost[] vrsta : this.matrika) {
			// niz += vrsta.toString();
			niz += Arrays.toString(vrsta);
			niz += "\n";
		}
		return niz;
	}
	
	
	public int vrniDimenzije() {
		return stranica;
	}
	
	
	public Vrednost[][] pridobiMatriko() {
		return this.matrika;
	}
	
	
	public Vrednost vrniClen(Koordinati k) {
		if (!koordinateSoVMatriki(k)) throw new java.lang.RuntimeException("Prišli smo izven matrike!");
		return this.matrika[k.getY()][k.getX()];
	}
	
	
	public void zamenjajClen (Koordinati k, Vrednost novClen) {
		this.matrika[k.getY()][k.getX()] = novClen;
	}
	
	
	private boolean koordinateSoVMatriki(Koordinati k) {
		return k.getX() >= 0 &&
				k.getX() < this.stranica &&
				k.getY() >= 0 &&
				k.getY() < this.stranica;
	}
}
