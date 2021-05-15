package inteligenca;

import java.util.List;

import logika.Igra;
import logika.Igralec;

import splosno.Koordinati;

public class Minimax extends Inteligenca {
	
	private static final int ZMAGA = 100; // nekaj velikega
	private static final int PORAZ = -ZMAGA;
	private static final int NEODLOC = 0;	
	
	private int globina;
	
	public Minimax (int globina) {
		super("Minimax globina " + globina);
		this.globina = globina;
	}
	
	@Override
	public Koordinati izberiPotezo (Igra igra) {
		OcenjenaPoteza najboljsaPoteza = minimax(igra, this.globina, igra.igralecNaPotezi);
		return najboljsaPoteza.poteza;	
	}
	
	// tezko preverimo vse mozne poteze...poskusimo samo vse, ki imajo zasedene sosede
	// vrne najboljso ocenjeno potezo z vidika igralca jaz
	public OcenjenaPoteza minimax(Igra igra, int globina, Igralec jaz) {
		OcenjenaPoteza najboljsaPoteza = null;
		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
		for (Koordinati p: moznePoteze) {
			Igra kopijaIgre = new Igra(igra);
			kopijaIgre.igraj (p);
			int ocena;
			kopijaIgre.spremeniStanje();
			switch (kopijaIgre.trenutnoStanje) {
			case ZMAGA_CRNI: ocena = (jaz == Igralec.CRNI ? ZMAGA : PORAZ); break;
			case ZMAGA_BELI: ocena = (jaz == Igralec.BELI ? ZMAGA : PORAZ); break;
			case NEODLOCENO: ocena = NEODLOC; break;
			default:
				if (globina == 1) ocena = Oceni.oceni(kopijaIgre, jaz); // dno rekurzije - oceni pozicijo
				else ocena = minimax(kopijaIgre, globina-1, jaz).ocena;	// nadaljuj rekurzijo z globina-1
			}
			if (najboljsaPoteza == null 
					|| jaz == igra.igralecNaPotezi && ocena > najboljsaPoteza.ocena // ce sem na potezi jaz in je ta ocena najboljsa do zdaj jo naredi za najboljso
					|| jaz != igra.igralecNaPotezi && ocena < najboljsaPoteza.ocena) // ali pa ce nisem na potezi jaz in je najmanjsa, jo vzemi 
				najboljsaPoteza = new OcenjenaPoteza (p, ocena);		
		}
		return najboljsaPoteza;
	}
}