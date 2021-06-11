package tekstovni_vmesnik;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Random;
import java.util.Map;
import java.util.EnumMap;
import java.util.List;

import logika.Igra;
import logika.Igralec;
import splosno.Koordinati;

public class Vodja {
	
	// private static enum VrstaIgralca { R, C; }  // to je zdaj definirano v posebej razredu
	
	private static BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
	
	private static Map<Igralec, VrstaIgralca> vrstaIgralca;
	
	
	public static void igramo () throws IOException {
		while (true) {
			System.out.println("Nova igra. Prosim, da izberete:");
			System.out.println(" 1 - èrni je èlovek, beli je raèunalnik");
			System.out.println(" 2 - èrni je raèunalnik, beli je èlovek");
			System.out.println(" 3 - èrni je èlovek, beli je èlovek");
			System.out.println(" 4 - izhod");
			String s = r.readLine();
			if (s.equals("1")) {
				vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
				vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			} else if (s.equals("2")) {
				vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
				vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			} else if (s.equals("3")) {
				vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
				vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
				vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			} else if (s.equals("4")) {
				System.out.println("Nasvidenje!");
				break;
			} else {
				System.out.println("Vnos ni veljaven");
				continue;
			}
			// Èe je s == "1", "2" ali "3"
			Igra igra = new Igra();
			igranje : while (true) {
				switch (igra.trenutnoStanje) {
				case ZMAGA_BELI:
					System.out.println("Zmagal je beli igralec!");
					// System.out.println("Zmagovalne vrste: " + igra.zmagovalneVrste.toString());
					break igranje;
				case ZMAGA_CRNI:
					System.out.println("Zmagal je èrni igralec!");
					// System.out.println("Zmagovalne vrste: " + igra.zmagovalneVrste.toString());
					break igranje;
				case NEODLOCENO:
					System.out.println("Igra je neodloèena");
					break igranje;
				case V_TEKU:
					Igralec igralec = igra.igralecNaPotezi;
					VrstaIgralca vrstaNaPotezi = vrstaIgralca.get(igralec);
					Koordinati poteza = null;
					switch (vrstaNaPotezi) {
					case C:
						poteza = clovekovaPoteza(igra);
						break;
					case R:
						poteza = racunalnikovaPoteza(igra);
						break;
					}
					System.out.println(igra);
					System.out.println("Igralec " + igralec + " je igral " + poteza);
				}
			}
		}
	}
	
	
	private static Random random = new Random ();
	
	
	public static Koordinati racunalnikovaPoteza(Igra igra) {
		List<Koordinati> moznePoteze = igra.vrniVsaPraznaPolja();
		// System.out.println(moznePoteze.size());
		int randomIndex = random.nextInt(moznePoteze.size());
		Koordinati poteza = moznePoteze.get(randomIndex);
		if (igra.odigraj(poteza)) return poteza;
		assert false;  // to se nikoli ne bi smelo zgoditi
		return null;  // vrnemo nekaj samo zato, ker paè moramo
	}
	
	
	public static Koordinati clovekovaPoteza(Igra igra) throws IOException {
		while (true) {
			System.out.println("Igralec " + igra.igralecNaPotezi.toString() +
					" vnesite potezo \"x y\"");
			String s = r.readLine();
			int i = s.indexOf(' ');  // pridobimo indeks presledka v nizu s
			if (i == -1 || i  == s.length()) {
				System.out.println("Napaèen format"); continue;
			}
			String xString = s.substring(0, i);
			String yString = s.substring(i + 1);
			int x, y;
			try {
				x = Integer.parseInt(xString);
				y = Integer.parseInt(yString);
			} catch (NumberFormatException e) {
				System.out.println("Napaèen format"); continue;
			}
			Koordinati poteza = new Koordinati(x, y);
			if (igra.odigraj(poteza)) return poteza;
			System.out.println(poteza.toString() + " ni možna");
		}
	}
}
