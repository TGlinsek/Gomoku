package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class AlfaBeta extends Inteligenca {
	
	private static final int ZMAGA = 100; // vrednost zmage
	private static final int PORAZ = -ZMAGA;  // vrednost izgube
	private static final int NEODLOC = 0;  // vrednost neodločene igre	
	
	private int globina;
	
	public AlfaBeta (int globina) {
		super("alphabeta globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		//na zacetku alpha = PORAZ in beta = ZMAGA
		return alfabetaPoteze(igra, this.globina, PORAZ, ZMAGA, igra.igralecNaPotezi).poteza;
	}
	
	public static OcenjenaPoteza alfabetaPoteze(Igra igra, int globina, int alfa, int beta, Igralec jaz) {
		int ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.igralecNaPotezi == jaz) {ocena = PORAZ;} else {ocena = ZMAGA;}
		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
		Koordinati kandidat = moznePoteze.get(0); //možno je, da se ne spremeni kanditat. Zato ne sme biti null.
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.igraj (p);
			int ocenap;
			switch (kopijaIgre.trenutnoStanje) {
			case ZMAGA_CRNI: ocenap = (jaz == Igralec.CRNI ? ZMAGA : PORAZ); break;
			case ZMAGA_BELI: ocenap = (jaz == Igralec.BELI ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocenap = NEODLOC; break;
			default:
				if (globina == 1) ocenap = Oceni.oceni(kopijaIgre, jaz);
				else ocenap = alfabetaPoteze(kopijaIgre, globina-1, alfa, beta, jaz).ocena;
			}
			if (igra.igralecNaPotezi == jaz) { // Maksimiramo oceno
				if (ocenap > ocena) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alfa = Math.max(alfa,ocena);
				}
			} 
			else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap < ocena) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena);					
				}	
			}
			if (alfa >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza(kandidat, ocena);
		}
		return new OcenjenaPoteza(kandidat, ocena);
	}

}