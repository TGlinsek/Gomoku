package grafika;

import java.util.Map;
import javax.swing.SwingWorker;

import inteligenca.Inteligenca;

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
	
	// ustvari novo igro z izbrano velikostjo polja (default 15)
	public static void igramoNovoIgro(int velikost) {
		igra = new Igra(velikost);
		okno.nastaviVelikostPoljVPlatnu();
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
			clovekNaVrsti = false;  // ce tega ne bi bilo, bi lahko uporabnik "prenesel" potezo iz enega nacina igre v drugega, kar pa se ne sme zgoditi
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
	
	
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca();
	
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {

			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				//zakasnitev racunalnikove poteze za npr. 1 sekundo
				try {TimeUnit.MILLISECONDS.sleep(1000);} catch (Exception e) {};
				
				return poteza;
			}
			@Override
			protected void done () {
				Koordinati poteza = null;
				try {poteza = get();} catch (Exception e) {return;};
				
				if (igra == zacetkaIgra) {
					igra.odigraj(poteza);
					igramo();
				}
			}
		};
		worker.execute();
	}
	
	
	public static void igrajClovekovoPotezo(Koordinati poteza) {
		if (igra.odigraj(poteza)) clovekNaVrsti = false;
		igramo();
	}
}