package inteligenca;

import java.util.List;
import java.util.LinkedList;

public class OcenjenePotezeLestvica {
	
	private int velikost;
	
	private LinkedList<OcenjenaPoteza> lestvica;
		
	public OcenjenePotezeLestvica(int velikost) {
		this.velikost = velikost;
		this.lestvica = new LinkedList<OcenjenaPoteza> ();
	}
	
	public void dodaj(OcenjenaPoteza ocenjenaPoteza) {
		int i = 0;
		for (OcenjenaPoteza op:lestvica) {
			if (ocenjenaPoteza.compareTo(op) != 1) i++;
			else break; // izstopimo iz zanke	
		}
		if (i < velikost) lestvica.add(i,ocenjenaPoteza);
		if (lestvica.size() > velikost) lestvica.removeLast();
	}
	
	
	public List<OcenjenaPoteza> pridobiLestvico() {
		return (List<OcenjenaPoteza>) lestvica;
	}

}
