package grafika;

import java.util.Random;
import java.util.Map;
import java.util.List;

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
	
	
	public static void igramoNovoIgro() {
		igra = new Igra(19);  // nastavi velikost (brez parametra za default)
		okno.nastaviVelikostPoljVPlatnu();
		igramo();
	}
	
	
	public static void igramo() {
		okno.osveziGUI();
		// igra.spremeniStanjeIgre();
		
		switch (igra.trenutnoStanje) {
		case ZMAGA_CRNI: 
		case ZMAGA_BELI:
		case NEODLOCENO:
			return; // odhajamo iz metode igramo
		case V_TEKU: 
			Igralec igralec = igra.igralecNaPotezi;
			VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
			clovekNaVrsti = false;  // èe tega ne bi bilo, bi lahko uporabnik "prenesel" potezo iz enega naèina igre v drugega, kar pa se ne sme zgoditi
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
	
	
	public static Inteligenca racunalnikovaInteligenca = new Inteligenca("Inteligenca s površinsko analizo plošèe");
	
	
	public static void igrajRacunalnikovoPotezo() {
		Igra zacetkaIgra = igra;
		SwingWorker<Koordinati, Void> worker = new SwingWorker<Koordinati, Void> () {

			@Override
			protected Koordinati doInBackground() {
				Koordinati poteza = racunalnikovaInteligenca.izberiPotezo(igra);
				
				// try {TimeUnit.SECONDS.sleep(1);} catch (Exception e) {};
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