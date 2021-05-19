package inteligenca;

import splosno.Koordinati;

public class OcenjenaPoteza {
	
	Koordinati poteza;
	double ocena;
	
	public OcenjenaPoteza (Koordinati poteza, double ocena) {
		this.poteza = poteza;
		this.ocena = ocena;
	}
	
	
	private static String vrniPrvihNZnakov(String niz, int n) {  // vrne presledke, �e n ve�ji od dol�ine niza
		String novNiz = "";
		int dolzina = niz.length();
		for (int i = 0; i < n; i++) {
			if (dolzina <= i) novNiz += " ";
			else novNiz += niz.charAt(i);
		}
		return novNiz;
	}
	
	
	@Override
	public String toString() {
		return vrniPrvihNZnakov(String.valueOf(ocena), 4);
	}
	
	
	public int compareTo (OcenjenaPoteza op) {
		if (this.ocena < op.ocena) return -1;
		else if (this.ocena > op.ocena) return 1;
		else return 0;
	}

}