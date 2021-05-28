package inteligenca;

public class Vrednost {
	public double vrednost;
	
	public Vrednost(double vrednost) {
		this.vrednost = vrednost;
	}
	
	
	private static String vrniPrvihNZnakov(String niz, int n) {  // vrne presledke, èe n veèji od dolžine niza
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
		return vrniPrvihNZnakov(String.valueOf(vrednost), 4);
	}
	
	
	public int compareTo (Vrednost vr) {
		if (this.vrednost < vr.vrednost) return -1;
		else if (this.vrednost > vr.vrednost) return 1;
		else return 0;
	}
}
