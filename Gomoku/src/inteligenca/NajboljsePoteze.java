package inteligenca;

import java.util.List;
import java.util.LinkedList;

public class NajboljsePoteze {
	
	private LinkedList<OcenjenaPoteza> najboljsePoteze;
		
	public NajboljsePoteze() {
		this.najboljsePoteze = new LinkedList<OcenjenaPoteza> ();
	}
	
	public void dodaj(OcenjenaPoteza ocenjenaPoteza) {
		if (najboljsePoteze.isEmpty()) najboljsePoteze.add(ocenjenaPoteza);
		else {
			OcenjenaPoteza op = najboljsePoteze.getFirst();
			switch (ocenjenaPoteza.compareTo(op)) {
			case 1: 
				najboljsePoteze.clear();  // ocenjenaPoteza > op
			case 0: // ali 1
				najboljsePoteze.add(ocenjenaPoteza); // ocenjenaPoteza >= op
			}			
		}
	}
	
	
	public List<OcenjenaPoteza> pridobiNajboljsePoteze() {
		return (List<OcenjenaPoteza>) najboljsePoteze;
	}

}
