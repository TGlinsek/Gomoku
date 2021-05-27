package inteligenca;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;
import splosno.KdoIgra;

public class Inteligenca extends KdoIgra {
	
	public Inteligenca(String ime) {
		super(ime);
	}
	
	
	private Koordinati nakljucniElementIzSeznama(List<Koordinati> seznam) {
		Random rand = new Random();
		Koordinati nakljucniElement = seznam.get(rand.nextInt(seznam.size()));
		return nakljucniElement;
	}
	
	
	private static final int ZMAGA = 100;
	private static final int PORAZ = -ZMAGA;
	private static final int NEODLOC = 0;	
	
	
	public Koordinati izberiPotezo(Igra igra) {
		Igralec jaz = igra.igralecNaPotezi;
		
		Vrednost najboljsaPoteza = null;
		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
		
		MatrikaVrednosti matrikaVrednosti = Oceni.izIgrePridobiMatrikoVrednosti(igra.matrika, jaz);  // evaluira vsa polja
		
		List<Koordinati> enakovrednePoteze = new LinkedList<Koordinati>();
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.odigraj(p);
			Vrednost ocena;
			
			switch (kopijaIgre.trenutnoStanje) {
			case ZMAGA_CRNI: ocena = new Vrednost(jaz == Igralec.CRNI ? ZMAGA : PORAZ); break;
			case ZMAGA_BELI: ocena = new Vrednost(jaz == Igralec.BELI ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = new Vrednost(NEODLOC); break;
			default:
				ocena = matrikaVrednosti.vrniClen(p);
			}
			
			if (ocena == null) continue;  // preskoèi vrednosti null
			
			if (najboljsaPoteza == null 
					|| ocena.vrednost > najboljsaPoteza.vrednost) {
				enakovrednePoteze.clear();
				najboljsaPoteza = new Vrednost(ocena.vrednost);
			}
			if (ocena.vrednost >= najboljsaPoteza.vrednost)
				enakovrednePoteze.add(p);
		}
		if (najboljsaPoteza == null) {
			throw new java.lang.RuntimeException("najboljsaPoteza ne more biti null!");
		}
		Koordinati izbranaPoteza = nakljucniElementIzSeznama(enakovrednePoteze);
		// System.out.println(izbranaPoteza);
		return izbranaPoteza;
	}

}