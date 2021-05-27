package inteligenca;

import logika.Igra;
import logika.Igralec;
import logika.Matrika;
import logika.Polje;
import logika.Vrsta;
import splosno.Koordinati;

public class Oceni {
	
	private static Polje cePoljeNullGaSpremeni(Polje polje, boolean defenseNamestoAttack, Igralec jaz) {  // jaz je ra�unalnik, proti kateremu igramo
		// �e polje null, to pomeni, da je bilo polje izven matrike
		if (polje == null) {
			if (defenseNamestoAttack) {
				// return Polje.PRAZNO;
				return null;  // to bomo posebej obravnavali:
				/*
				 * seveda no�emo nastaviti na nasprotnikovo figuro, druga�e se bo ra�unalnik hotel postaviti ob stene
				 * vseeno pa ho�emo, da ra�unalnik ve, da nasprotnik ne more postavljati figur v steno, torej lahko to tudi upo�teva
				 */
			}
			else {
				if (jaz == Igralec.CRNI) {
					return Polje.BELO;
				}
				else {  // �e pa jaz == Igralec.BELI
					return Polje.CRNO;
				}
			}
		}
		return polje;  // �e polje ni bilo null, ga ne spremeni
	}
	
	
	private static MatrikaOsmericVrednosti izIgrePridobiMatrikoOsmeric(Matrika matrika, Igralec jaz) {
		// recimo, da sem bel in da sem na vrsti in da maksimiziram attack
		
		Polje mojePolje;
		Polje nasprotnikovoPolje;
		if (jaz == Igralec.CRNI) {
			mojePolje = Polje.CRNO;
			nasprotnikovoPolje = Polje.BELO;
		} else if (jaz == Igralec.BELI) {
			mojePolje = Polje.BELO;
			nasprotnikovoPolje = Polje.CRNO;
		} else {throw new java.lang.RuntimeException("error!!!");}
		
		Polje zacasnoPolje;
		
		
		MatrikaOsmericVrednosti novaMatrika = new MatrikaOsmericVrednosti(matrika.vrniDimenzije());
		for (int i = 0; i < matrika.vrniDimenzije(); i++) {
			for (int j = 0; j < matrika.vrniDimenzije(); j++) {

				// tukaj loopamo po vseh smereh
				for (int l = 0; l < 4; l++) {
					if (matrika.vrniClen(new Koordinati(j, i)) == Polje.BELO || matrika.vrniClen(new Koordinati(j, i)) == Polje.CRNO) {
						novaMatrika.dodajClen(new Koordinati(j, i), null, l);  // �e zasedeno, nekako poskrbimo, da tistega ne gledamo
						novaMatrika.dodajClen(new Koordinati(j, i), null, l + 4);  // �e zasedeno, nekako poskrbimo, da tistega ne gledamo
						continue;
					}

					Koordinati smer = Vrsta.smeri.get(l);

					Vrsta desnaVrsta = Vrsta.vrniVrsto(new Koordinati(j, i), smer, 1, 5);

					Vrsta levaVrsta = Vrsta.vrniVrsto(new Koordinati(j, i), Vrsta.obrniSmer(smer), 1, 5);
					
					{
						int povezanaDolzina = 1;  // kamen, ki bi bil na mestu (j, i)
								
						int prviNasprotnikovKamenNaDesni;
						int razdaljaMedZadnjimMojimInPrvimNasprotnimDesno;
						int desniStevec = 1;
						int nazadnjiMojKamenDesno = 0;
						boolean desnaSredinaPovezana = true;
						int povezanaDesnaDolzina = 0;
						int povezanaDesnaOdmik = 0;
						boolean desnaKomponentaPovezana = true;

						desnaStran : for (Koordinati k : desnaVrsta.tabelaKoordinat) {
							zacasnoPolje = cePoljeNullGaSpremeni(matrika.vrniClen(k), false, jaz);
							if (zacasnoPolje == Polje.PRAZNO) {
								desniStevec++;
								desnaSredinaPovezana = false;  // od tu naprej ni ve� povezana
								if (povezanaDesnaDolzina != 0) desnaKomponentaPovezana = false;  // konec druge komponente
								if (!desnaSredinaPovezana && desnaKomponentaPovezana) povezanaDesnaOdmik++;  // med povezano in med drugo komponento
							} else if (zacasnoPolje == mojePolje) {
								nazadnjiMojKamenDesno = desniStevec;
								desniStevec++;
								if (desnaSredinaPovezana) povezanaDolzina++;
								if (!desnaSredinaPovezana && desnaKomponentaPovezana) povezanaDesnaDolzina++;
							} else if (zacasnoPolje == nasprotnikovoPolje) {
								break desnaStran;
							} else {
								throw new java.lang.RuntimeException("to se ne bi smelo zgoditi");
							}
						}
						prviNasprotnikovKamenNaDesni = desniStevec;
						razdaljaMedZadnjimMojimInPrvimNasprotnimDesno = desniStevec - nazadnjiMojKamenDesno;  // �e sta �ist skupaj, je razdalja 1
						
						
						int prviNasprotnikovKamenNaLevi;
						int razdaljaMedZadnjimMojimInPrvimNasprotnimLevo;
						int leviStevec = -1;
						int nazadnjiMojKamenLevo = 0;
						boolean levaSredinaPovezana = true;
						int povezanaLevaDolzina = 0;
						int povezanaLevaOdmik = 0;
						boolean levaKomponentaPovezana = true;
						
						levaStran : for (Koordinati k : levaVrsta.tabelaKoordinat) {
							zacasnoPolje = cePoljeNullGaSpremeni(matrika.vrniClen(k), false, jaz);
							if (zacasnoPolje == Polje.PRAZNO) {
								leviStevec--;
								levaSredinaPovezana = false;
								if (povezanaLevaDolzina != 0) levaKomponentaPovezana = false;  // konec druge komponente
								if (!levaSredinaPovezana && levaKomponentaPovezana) povezanaLevaOdmik++;  // med povezano in med drugo komponento
							} else if (zacasnoPolje == mojePolje) {
								nazadnjiMojKamenLevo = leviStevec;
								leviStevec--;
								if (levaSredinaPovezana) povezanaDolzina++;
								if (!levaSredinaPovezana && levaKomponentaPovezana) povezanaLevaDolzina++;
							} else if (zacasnoPolje == nasprotnikovoPolje) {
								break levaStran;
							} else {
								throw new java.lang.RuntimeException("to se ne bi smelo zgoditi");
							}
						}
						prviNasprotnikovKamenNaLevi = leviStevec;
						razdaljaMedZadnjimMojimInPrvimNasprotnimLevo = nazadnjiMojKamenLevo - leviStevec;  // �e sta �ist skupaj, je razdalja 1
						
						
						/*
						Skica:
						
						�b__bbx___bb_�
						stMojihKamnovNaDesni = 2
						stMojihKamnovNaLevi = 3
						prviNasprotnikovKamenNaDesni = 7
						prviNasprotnikovKamenNaLevi = -6
						razdaljaMedZadnjimMojimInPrvimNasprotnimDesno = 2
						razdaljaMedZadnjimMojimInPrvimNasprotnimLevo = 1
					 	nazadnjiMojKamenDesno = 5
						nazadnjiMojKamenLevo = -5
						povezanaDesnaDolzina = 2
						povezanaLevaDolzina = 2  (gleda se najbli�ja komponenta)
						povezanaDesnaOdmik = 3  (torej, razdalja se meri v �t praznih polj, ampak med nasprotnikom se pa meri �t praznih polj - 1)
						povezanaLevaOdmik = 2
						*/
						
						
						int razdaljaOdNasprotnikovih = Math.min(razdaljaMedZadnjimMojimInPrvimNasprotnimDesno, razdaljaMedZadnjimMojimInPrvimNasprotnimLevo);
						// razdaljo do nasprotnika izra�unamo kot minimum leve in desne razdalje do nasprotnika
						
						
						int stProstorckov = prviNasprotnikovKamenNaDesni - prviNasprotnikovKamenNaLevi - 1;
						
						double ocenaPoljaZaVodoravnoSmerAttack;
						if (stProstorckov < 5) {
							ocenaPoljaZaVodoravnoSmerAttack = 0;  // slaba ocena, saj ni mo�no dobiti peterice
						} else {
							double prviKandidat;  // edini kandidat, ki upo�teva �e nasprotnikove kamne (torej, koliko smo omejeni)

							if (razdaljaOdNasprotnikovih == 1) prviKandidat = povezanaDolzina - 1;  // �e smo tik ob nasprotniku, nam zaseda veliko prostora, zato bo ocena nizka
							else {
								if (razdaljaOdNasprotnikovih >= povezanaDolzina - 1) prviKandidat = povezanaDolzina - 0.05;  // �e nasprotnika ni v bli�ini
								else {  // �e je nasprotnik nekoliko blizu, ampak ne preve� (torej, nekje vmes)
									prviKandidat = povezanaDolzina - 0.05*(povezanaDolzina - razdaljaOdNasprotnikovih);  // za razdaljaOdNasprotnikovih == povezanaDolzina - 1 se oba primera ujemata
								}
							}
							// prviKandidat = boljsiMax((povezanaDolzina >= 4 ? 4 : 0), prviKandidat);
							prviKandidat = boljsiMax(((povezanaLevaDolzina + povezanaDolzina >= 4 && povezanaDesnaDolzina + povezanaDolzina >= 4) ? 4 : 0), prviKandidat);  // tole ga naj bi naredilo malo bolj napadalnega
							
							
							double cetrtiKandidat = 0;  // �e imamo situacijo npr. b_bxb_b ali pa bb_bx_bb ali bbb_x_bbb, kjer je x poteza, ki bi jo v tej rundi naredili, potem smo si zagotovili zmago, zato damo oceno 4
							if (povezanaDesnaOdmik == 1 && 
									povezanaLevaOdmik == 1 && 
									(povezanaLevaDolzina + povezanaDolzina) >= 4 &&
									(povezanaDesnaDolzina + povezanaDolzina) >= 4
								) cetrtiKandidat = 3.91;  // malo zmanj�al (iz 4), samo zato, ker obstaja ve�ja mo�nost, da nasprotnik tega ne opazi
							
							// ocenaPoljaZaVodoravnoSmerAttack = Math.max(Math.max(Math.max(prviKandidat, drugiKandidat), tretjiKandidat), cetrtiKandidat);
							// ocenaPoljaZaVodoravnoSmerAttack = prviKandidat;
							ocenaPoljaZaVodoravnoSmerAttack = Math.max(prviKandidat, cetrtiKandidat);
						}

						novaMatrika.dodajClen(new Koordinati(j, i), new Vrednost(ocenaPoljaZaVodoravnoSmerAttack + 0.5), l);  // l (tretji argument): 0 je za vodoravno smer, attack mode, 1 za navpi�no, 5 za vodoravno defense, ...
						// attack ima 0.5 ve� kot defense, za iste poteze, zato tu pri�tejemo
						
						
						/*
						4.5 naslednjo rundo zmagam
						5.0 �e ne naredim zmaga
						5.5 �e naredim, zmagam
						4.0 �e ne naredim, zmaga �ez eno rundo
						 */
					}
					{
						// vse naprej je isto, razen zadnje vrstice. Fora je, da si predstavljamo, kok to�k bi nasprotnik dobu, �e bi dal tja, zato ho�emo mi ravno tista polja zasesti
						// povsod, kjer v imenih spremenljivk pi�e "moj" ali kaj podobnega, bi moralo pisati nasprotnikov. In obratno.
						// aja pa barve v switchih so seveda spremenjene
						
						int povezanaDolzina = 1;  // kamen, ki bi bil na mestu (j, i)
						
						int prviNasprotnikovKamenNaDesni;
						int razdaljaMedZadnjimMojimInPrvimNasprotnimDesno;
						int desniStevec = 1;
						int nazadnjiMojKamenDesno = 0;
						boolean desnaSredinaPovezana = true;
						int povezanaDesnaDolzina = 0;
						int povezanaDesnaOdmik = 0;
						boolean desnaKomponentaPovezana = true;
						
						
						desnaStran : for (Koordinati k : desnaVrsta.tabelaKoordinat) {
							zacasnoPolje = cePoljeNullGaSpremeni(matrika.vrniClen(k), true, jaz);
							if (zacasnoPolje == Polje.PRAZNO) {
								
								desniStevec++;
								desnaSredinaPovezana = false;  // od tu naprej ni ve� povezana
								if (povezanaDesnaDolzina != 0) desnaKomponentaPovezana = false;  // konec druge komponente
								if (!desnaSredinaPovezana && desnaKomponentaPovezana) povezanaDesnaOdmik++;  // med povezano in med drugo komponento
							} else if (zacasnoPolje == nasprotnikovoPolje) {
								
								
								nazadnjiMojKamenDesno = desniStevec;
								desniStevec++;
								if (desnaSredinaPovezana) povezanaDolzina++;
								if (!desnaSredinaPovezana && desnaKomponentaPovezana) povezanaDesnaDolzina++;
							} else if (zacasnoPolje == mojePolje || zacasnoPolje == null) {  // �e smo zadeli mojePolje ali pa �li iz okvirja
								break desnaStran;
							} else {
								throw new java.lang.RuntimeException("to se ne bi smelo zgoditi");
							}
						}
						
						prviNasprotnikovKamenNaDesni = desniStevec;
						razdaljaMedZadnjimMojimInPrvimNasprotnimDesno = desniStevec - nazadnjiMojKamenDesno;  // �e sta �ist skupaj, je razdalja 1
						

						int prviNasprotnikovKamenNaLevi;
						int razdaljaMedZadnjimMojimInPrvimNasprotnimLevo;
						int leviStevec = -1;
						int nazadnjiMojKamenLevo = 0;
						boolean levaSredinaPovezana = true;
						int povezanaLevaDolzina = 0;
						int povezanaLevaOdmik = 0;
						boolean levaKomponentaPovezana = true;
						
						levaStran : for (Koordinati k : levaVrsta.tabelaKoordinat) {
							zacasnoPolje = cePoljeNullGaSpremeni(matrika.vrniClen(k), true, jaz);
							if (zacasnoPolje == Polje.PRAZNO) {
								leviStevec--;
								levaSredinaPovezana = false;
								if (povezanaLevaDolzina != 0) levaKomponentaPovezana = false;  // konec druge komponente
								if (!levaSredinaPovezana && levaKomponentaPovezana) povezanaLevaOdmik++;  // med povezano in med drugo komponento
							} else if (zacasnoPolje == nasprotnikovoPolje) {
								nazadnjiMojKamenLevo = leviStevec;
								leviStevec--;
								if (levaSredinaPovezana) povezanaDolzina++;
								if (!levaSredinaPovezana && levaKomponentaPovezana) povezanaLevaDolzina++;
							} else if (zacasnoPolje == mojePolje || zacasnoPolje == null) {
								break levaStran;
							} else {
								throw new java.lang.RuntimeException("to se ne bi smelo zgoditi");
							}
						}
						
						prviNasprotnikovKamenNaLevi = leviStevec;
						razdaljaMedZadnjimMojimInPrvimNasprotnimLevo = nazadnjiMojKamenLevo - leviStevec;  // �e sta �ist skupaj, je razdalja 1
						
						
						int razdaljaOdNasprotnikovih = Math.min(razdaljaMedZadnjimMojimInPrvimNasprotnimDesno, razdaljaMedZadnjimMojimInPrvimNasprotnimLevo);
						// razdaljo do nasprotnika izra�unamo kot minimum leve in desne razdalje do nasprotnika
						
						int stProstorckov = prviNasprotnikovKamenNaDesni - prviNasprotnikovKamenNaLevi - 1;
						
						
						double ocenaPoljaZaVodoravnoSmerDefense;
						if (stProstorckov < 5) {
							ocenaPoljaZaVodoravnoSmerDefense = 0;  // slaba ocena, saj ni mo�no dobiti peterice
						} else {
							double prviKandidat;  // edini kandidat, ki upo�teva �e nasprotnikove kamne (torej, koliko smo omejeni)
							
							
							int dodatek = 0;  // ne vem a je to treba uvest, ampak mislim da se lahko zgodi, da v�asih ne bo zagradil �tiri v vrsto, tudi �e ne zmaga
							if (povezanaDolzina >= 5) dodatek = 1;  // �e je kriza naj bolj defenzivno igra
							
							
							if (razdaljaOdNasprotnikovih == 1) prviKandidat = povezanaDolzina + dodatek - 1;  // �e smo tik ob nasprotniku, nam zaseda veliko prostora, zato bo ocena nizka
							else {
								if (razdaljaOdNasprotnikovih >= povezanaDolzina - 1) prviKandidat = povezanaDolzina - 0.05 + dodatek;  // �e nasprotnika ni v bli�ini
								else {  // �e je nasprotnik nekoliko blizu, ampak ne preve� (torej, nekje vmes)
									prviKandidat = povezanaDolzina - 0.05*(povezanaDolzina - razdaljaOdNasprotnikovih) + dodatek;  // za razdaljaOdNasprotnikovih == povezanaDolzina - 1 se oba primera ujemata
								}
							}
							
							
							double cetrtiKandidat = 0;  // �e imamo situacijo npr. b_bxb_b ali pa bb_bx_bb ali bbb_x_bbb, kjer je x poteza, ki bi jo v tej rundi naredili, potem smo si zagotovili zmago, zato damo oceno 4
							if (povezanaDesnaOdmik == 1 && 
									povezanaLevaOdmik == 1 && 
									(povezanaLevaDolzina + povezanaDolzina) >= 4 &&
									(povezanaDesnaDolzina + povezanaDolzina) >= 4
								) cetrtiKandidat = 4;
							
							// ocenaPoljaZaVodoravnoSmerDefense = Math.max(Math.max(Math.max(prviKandidat, drugiKandidat), tretjiKandidat), cetrtiKandidat);
							// ocenaPoljaZaVodoravnoSmerDefense = prviKandidat;
							ocenaPoljaZaVodoravnoSmerDefense = Math.max(prviKandidat, cetrtiKandidat);
						}

						// do sem je koda bila enaka, le da sm attack spremenil v defense
						novaMatrika.dodajClen(new Koordinati(j, i), new Vrednost(ocenaPoljaZaVodoravnoSmerDefense), l + 4);  // +4 ker smo v defense
						// attack ima 0.5 ve� kot defense, za iste poteze, zato tukaj ni� ne pri�tejemo
					}
				}
			}
		}
		System.out.println(novaMatrika);
		System.out.println(jaz);
		return novaMatrika;  // vsak �len te matrike je seznam z 8 elementi: 4 smeri, 2 na�ina (attack, defense)	
	}
	
	
	private static double boljsiMax(double a, double b) {
		// vzame max in pri�teje relativno zelo majhno koli�ino, ki je ve�ja, �e je manj�i �len bli�je ve�jemu �lenu
		double odmik;
		if (a == b) {
			odmik = 0.011;  // va�no je, da je malo ve�je od 0.01 (glej tri vrstice ni�je)
		} else {
			double dif = Math.abs(a - b);
			odmik = Math.min(0.01, (0.0001/dif));
		}
		return Math.max(a, b) + odmik;
	}
	

	private static double vsota(Vrednost A, Vrednost B, Vrednost C, Vrednost D) {
		double a = A.vrednost;
		double b = B.vrednost;
		double c = C.vrednost;
		double d = D.vrednost;
		double maks = Math.max(a, Math.max(b, Math.max(c, d)));
		double drugiMaks;
		if (maks == a) {
			drugiMaks = Math.max(b, Math.max(c, d));
		} else if (maks == b) {
			drugiMaks = Math.max(a, Math.max(c, d));
		} else if (maks == c) {
			drugiMaks = Math.max(a, Math.max(b, d));
		} else if (maks == d) {
			drugiMaks = Math.max(a, Math.max(b, c));
		} else {
			throw new java.lang.RuntimeException("To se ne bi smelo zgoditi.");
		}
		if (maks >= 2.95 && drugiMaks >= 2.95) return boljsiMax(boljsiMax(drugiMaks, maks), 3.55);  // �e imamo:
		/*
		   _
		   _
		   �
		   �
		 ___��__
		   _
		   _
		  
		 Potem bi lahko �rn dal v to kri�i��e in si tako �e zagotovil zmago
		 
		 Zato nastavimo vrednost na malo ve� kot 3.5 (ne more biti bolje kot 4.0, saj 4.0 pomeni, da bo nasprotnik zmagal �ez eno rundo - to pa je premalo rund, da bi lahko ta�as mi zmagali)
		 */
		return boljsiMax(drugiMaks, maks);
	}
	
	
	private static MatrikaVrednosti izMatrikeOsmericPridobiMatrikoVrednosti(MatrikaOsmericVrednosti matrikaOsmeric) {
		Vrednost[][][] matrika = matrikaOsmeric.pridobiMatriko();
		MatrikaVrednosti novaMatrika = new MatrikaVrednosti(matrikaOsmeric.vrniDimenzije());
		for (int i = 0; i < matrikaOsmeric.vrniDimenzije(); i++) {
			for (int j = 0; j < matrikaOsmeric.vrniDimenzije(); j++) {
				Vrednost WEattack = matrika[i][j][0];
				Vrednost NSattack = matrika[i][j][1];
				Vrednost SEattack = matrika[i][j][2];
				Vrednost SWattack = matrika[i][j][3];
				Vrednost WEdefense = matrika[i][j][4];
				Vrednost NSdefense = matrika[i][j][5];
				Vrednost SEdefense = matrika[i][j][6];
				Vrednost SWdefense = matrika[i][j][7];
				
				Vrednost ocena;
				if (WEattack == null) {  // lahko kergakol izberemo, namre� �e je en izmed teh osmih enak null, bodo vsi
					ocena = null;
				}
				else {
					double prvaVsota = vsota(WEattack, NSattack, SEattack, SWattack);
					double drugaVsota = vsota(WEdefense, NSdefense, SEdefense, SWdefense);

					
					double vrednost = 
							boljsiMax(prvaVsota, drugaVsota)
							;
					ocena = new Vrednost(vrednost);
				}
				
				novaMatrika.zamenjajClen(
						new Koordinati(j, i), 
						ocena
				);
			}
		}
		System.out.println(novaMatrika);
		return novaMatrika;
	}
	
	
	public static MatrikaVrednosti izIgrePridobiMatrikoVrednosti(Matrika matrika, Igralec jaz) {
		return izMatrikeOsmericPridobiMatrikoVrednosti(izIgrePridobiMatrikoOsmeric(matrika, jaz));
	}
}
