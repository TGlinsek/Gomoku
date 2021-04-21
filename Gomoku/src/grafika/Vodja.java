package grafika;

import java.util.Random;
import java.util.Map;
import java.util.List;

import javax.swing.SwingWorker;
import java.util.concurrent.TimeUnit;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;
import tekstovni_vmesnik.VrstaIgralca;

public class Vodja {	
	
	public static Map<Igralec, VrstaIgralca> vrstaIgralca;
	
	public static Okno okno;
	
	public static Igra igra = null;
	
	public static boolean clovekNaVrsti = false;
	
	
	public static void igramoNovoIgro() {
		igra = new Igra();
		igramo();
	}
	
	
	public static void igramo() {
		okno.osveziGUI();
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
	
	
	private static Random random = new Random();
	
//	public static void igrajRacunalnikovoPotezo() {
//		List<Koordinati> moznePoteze = igra.poteze();
//		try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {};
//		int randomIndex = random.nextInt(moznePoteze.size());
//		Koordinati poteza = moznePoteze.get(randomIndex);
//		igra.odigraj(poteza);
//		igramo ();
//	}
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {
			@Override
			protected Koordinati doInBackground() {
				try {TimeUnit.SECONDS.sleep(2);} catch (Exception e) {};
				List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
				int randomIndex = random.nextInt(moznePoteze.size());
				return moznePoteze.get(randomIndex);
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {};
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