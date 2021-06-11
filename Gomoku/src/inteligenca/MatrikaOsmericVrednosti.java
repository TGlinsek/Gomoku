package inteligenca;

import java.util.Arrays;

import splosno.Koordinati;

public class MatrikaOsmericVrednosti {
	private Vrednost[][][] matrika;
	private int stranica;
	
	
	public MatrikaOsmericVrednosti(int stranica) {
		this.stranica = stranica;
		this.matrika = new Vrednost[stranica][stranica][8];
	}
	
	
	public int vrniDimenzije() {
		return stranica;
	}
	
	@Override
	public String toString() {
		// return Arrays.deepToString(this.matrika);  // to izpiše celotno matriko v eni vrstici
		String niz = "";
		for (Vrednost[][] vrsta : this.matrika) {
			// niz += Arrays.deepToString(vrsta);
			for (Vrednost[] cetverica : vrsta) {
				niz += Arrays.toString(cetverica);
				niz += "     ";
			}
			niz += "\n";
		}
		return niz;
	}
	
	public Vrednost[][][] pridobiMatriko() {
		return this.matrika;
	}
	
	
	public Vrednost[] vrniClen(Koordinati k) {
		if (!koordinateSoVMatriki(k)) throw new java.lang.RuntimeException("Prišli smo izven matrike!");
		return this.matrika[k.getY()][k.getX()];
	}
	
	
	public void dodajClen (Koordinati k, Vrednost vrednost, int pozicija) {
		if (pozicija < 0 || pozicija > 7) throw new java.lang.RuntimeException("pozicija mora biti med 0 in 7.");
		if (!koordinateSoVMatriki(k)) {
			System.out.println("Koordinate so zunaj, ampak niè hudega. To polje potem samo pustimo na miru.");
			return;
		}
		this.matrika[k.getY()][k.getX()][pozicija] = vrednost;
	}
	
	
	private boolean koordinateSoVMatriki(Koordinati k) {
		return k.getX() >= 0 &&
				k.getX() < this.stranica &&
				k.getY() >= 0 &&
				k.getY() < this.stranica;
	}

}
