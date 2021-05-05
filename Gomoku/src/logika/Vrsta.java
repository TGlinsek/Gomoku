package logika;

import splosno.Koordinati;

public class Vrsta {

	public Koordinati[] tabelaKoordinat;
	
	public Vrsta(Koordinati[] koordinate) {
		this.tabelaKoordinat = koordinate;
	}
	
	
	@Override
	public String toString() {
		String niz = "[";
		for (Koordinati k : this.tabelaKoordinat) {
			niz += "(" + k.getX() + ", " + k.getY() + ")";
			niz += ", ";
		}
		niz = niz.substring(0, niz.length() - 2);
		niz += "]";
		return niz;
	}
}
