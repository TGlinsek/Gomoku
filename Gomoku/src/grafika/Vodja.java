package grafika;

import java.util.Random;
import java.util.Map;
import java.util.List;

import javax.swing.SwingWorker;

import inteligenca.AlfaBeta;
import inteligenca.Inteligenca;
import inteligenca.Minimax;

import java.util.concurrent.TimeUnit;

import logika.Igra;
import logika.Igralec;
import splosno.KdoIgra;
import splosno.Koordinati;
import tekstovni_vmesnik.VrstaIgralca;

public class Vodja {	
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
	public static Map<Igralec,KdoIgra> kdoIgra;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
	
	public static void igramoNovoIgro() {
		igra = new Igra(19);  // nastavi velikost (brez parametra za default)
		okno.nastaviVelikostPoljVPlatnu();
		igramo();
	}
	
	
	public static void igramo() {
		okno.osveziGUI();
		// igra.spremeniStanjeIgre(igra.zadnjaIgranaPoteza);  // ne dela, ker lahko da je na tem mestu igra.zadnjaIgranaPoteza �e null
		// igra.spremeniStanjeIgre();
		
		switch (igra.trenutnoStanje) {
		case ZMAGA_CRNI: 
		case ZMAGA_BELI:
		case NEODLOCENO:
			return; // odhajamo iz metode igramo
		case V_TEKU: 
			Igralec igralec = igra.igralecNaPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			switch (vrstaNaPotezi) {
			case C: 
				clovekNaVrsti = true;
				break;
			case R:
				igrajRacunalnikovoPotezo ();
				break;
			}
		}
	}
	
	
//	private static Random random = new Random();
	
//	public static void igrajRacunalnikovoPotezo() {
//		List<Koordinati> moznePoteze = igra.poteze();
//		try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {};
//		int randomIndex = random.nextInt(moznePoteze.size());
//		Koordinati poteza = moznePoteze.get(randomIndex);
//		igra.odigraj(poteza);
//		igramo ();
//	}
	
	
	// public static Inteligenca racunalnikovaInteligenca = new AlfaBeta(1);
	public static Inteligenca racunalnikovaInteligenca = new Minimax(1);
	
//	private static Random random = new Random ();  // le zaradi metode racunalnikovaPoteza
//	
//	public static Koordinati racunalnikovaPoteza(Igra igra) {  // za�asna metoda za testiranje grafike
//		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
//		int randomIndex = random.nextInt(moznePoteze.size());
//		Koordinati poteza = moznePoteze.get(randomIndex);
//		if (igra.igraj(poteza)) return poteza;
//		assert false;
//		return null;
//	}
	
	
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {

			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				//Koordinati poteza = racunalnikovaPoteza(igra);  // za�asna metoda
				
				// try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
				try {TimeUnit.MILLISECONDS.sleep(500);} catch (Exception e) {};
				
				// System.out.println(poteza);
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {return;};
				// System.out.println(poteza);
				if (igra == zacetkaIgra) {
					igra.igraj(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}
	
	
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.igraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
}