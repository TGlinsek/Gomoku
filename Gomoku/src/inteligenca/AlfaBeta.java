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
	
	public static OcenjenaPoteza alfabetaPoteze(Igra igra, int globina, double alfa, double beta, Igralec jaz) {
		Vrednost ocena;
		// Če sem računalnik, maksimiramo oceno z začetno oceno ZGUBA
		// Če sem pa človek, minimiziramo oceno z začetno oceno ZMAGA
		if (igra.igralecNaPotezi == jaz) {ocena = new Vrednost(PORAZ);} else {ocena = new Vrednost(ZMAGA);}
		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
		Koordinati kandidat = moznePoteze.get(0); //možno je, da se ne spremeni kanditat. Zato ne sme biti null.
		
		MatrikaVrednosti matrikaVrednosti = Oceni.izIgrePridobiMatrikoVrednosti(igra.matrika, jaz);  // evaluira vsa polja
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.igraj (p);
			Vrednost ocenap = null;  // v tem switchu se bo ocenap itak �e enkrat nastavila na neko vrednost
			switch (kopijaIgre.trenutnoStanje) {
			case ZMAGA_CRNI: ocena = new Vrednost(jaz == Igralec.CRNI ? ZMAGA : PORAZ); break;
			case ZMAGA_BELI: ocena = new Vrednost(jaz == Igralec.BELI ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = new Vrednost(NEODLOC); break;
			default:
				// if (globina == 1) ocenap = Oceni.oceni(kopijaIgre, jaz);
				if (globina == 1) ocenap = matrikaVrednosti.vrniClen(p);
				else ocenap = new Vrednost(alfabetaPoteze(kopijaIgre, globina-1, alfa, beta, jaz).ocena);
			}
			if (ocenap == null) continue;  // presko�i vrednosti null
			if (igra.igralecNaPotezi == jaz) { // Maksimiramo oceno
				if (ocenap.vrednost > ocena.vrednost) { // mora biti > namesto >=
					ocena = ocenap;
					kandidat = p;
					alfa = Math.max(alfa, ocena.vrednost);
				}
			} 
			else { // igra.naPotezi() != jaz, torej minimiziramo oceno
				if (ocenap.vrednost < ocena.vrednost) { // mora biti < namesto <=
					ocena = ocenap;
					kandidat = p;
					beta = Math.min(beta, ocena.vrednost);					
				}	
			}
			if (alfa >= beta) // Izstopimo iz "for loop", saj ostale poteze ne pomagajo
				return new OcenjenaPoteza(kandidat, ocena.vrednost);
		}
		return new OcenjenaPoteza(kandidat, ocena.vrednost);
	}

}