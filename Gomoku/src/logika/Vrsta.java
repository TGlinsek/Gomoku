package logika;

import java.util.HashMap;
import java.util.Map;

import splosno.Koordinati;

public class Vrsta {

	public Koordinati[] tabelaKoordinat;
	
	public static Map<Integer, Koordinati> smeri = new HashMap<Integer, Koordinati>();
	
	
	static {
	    smeri.put(0, new Koordinati(1, 0));  // WE
	    smeri.put(1, new Koordinati(0, 1));  // NS
	    smeri.put(2, new Koordinati(1, 1));  // SE
	    smeri.put(3, new Koordinati(1, -1));  // SW
	}
	
	
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
	
	
	public static Koordinati obrniSmer(Koordinati smer) {
		return new Koordinati(-smer.getX(), -smer.getY());
	}
	
	
	public static Vrsta vrniVrsto(Koordinati zacetne, Koordinati vektor, int zacetek, int konec) {
		/* Primer:
		vrniVrsto((5, 6), (1, 2), -3, 2) ti da vrsto [(2, 0), (3, 2), (4, 4), (5, 6), (6, 8), (7, 10)]
		*/
		if (zacetek > konec) throw new java.lang.RuntimeException("Zaèetek ne more biti veèji kot konec!");
		Koordinati[] vrsta = new Koordinati[konec - zacetek + 1];
		for (int i = zacetek; i <= konec; i++) {
			// vrsta[i - zacetek] = zacetne.pristejVektor(vektor.pomnozi(i));
			vrsta[i - zacetek] = new Koordinati(zacetne.getX() + i * vektor.getX(), zacetne.getY() + i * vektor.getY());
		}
		return new Vrsta(vrsta);
	}
}
