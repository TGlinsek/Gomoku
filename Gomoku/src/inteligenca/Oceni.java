package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Polje;
import logika.Vrsta;

public class Oceni {

	public static int oceni(Igra igra, Igralec jaz) {
		int ocena = 0;
		for (Vrsta v : Igra.VRSTE) {
			ocena = ocena + oceniVrsto(v, igra, jaz);
		}
		return ocena;	
	}
	
	public static int oceniVrsto (Vrsta vrsta, Igra igra, Igralec jaz) {
		int belih = 0;
		int crnih = 0;
		for (int k = 0; k < Igra.dolzinaVrste && (belih == 0 || crnih == 0); k++) {
			switch (igra.matrika.vrniClen(vrsta.tabelaKoordinat[k])) {
			case CRNO: crnih += 1; break;
			case BELO: belih += 1; break;
			case PRAZNO: break;
			}
		}
		if (crnih > 0 && belih > 0) { return 0; }
		else if (jaz == Igralec.CRNI) { return crnih - belih; }
		else { return belih - crnih; }
	}
}
