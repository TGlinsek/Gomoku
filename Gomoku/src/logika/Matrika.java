package logika;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import grafika.Vodja;
import splosno.Koordinati;

public class Matrika {
	
	private Polje[][] matrika;
	private int stranica;
	private List<Koordinati> praznaPolja;  // zato, da se bo ra�unalnik lahko odlo�al med mo�nimi potezami
	//public List<Vrsta> okoliskeVrste; // a je to prav? a mora bit public ali private? a mora bit tukaj ali v igra pa potem Vodja.igra.okoliskeVrste
	
	public Matrika(int stranica) {
		this.stranica = stranica;
		this.matrika = new Polje[stranica][stranica];
		this.praznaPolja = new LinkedList<Koordinati>();
		//this.okoliskeVrste = new LinkedList<Vrsta>();
		izprazniMatriko();
		preberiPraznaPolja();
	}
	
	
	public int vrniDimenzije() {
		return stranica;
	}
	
	
	public void izprazniMatriko() {
		for (int i = 0; i < this.stranica; i++) {
			for (int j = 0; j < this.stranica; j++) {
				this.matrika[i][j] = Polje.PRAZNO;
			}
		}
	}
	
	
	public Polje[][] pridobiMatriko() {
		return this.matrika;
	}
	
	
	public Polje vrniClen(Koordinati k) {
		return this.matrika[k.getY()][k.getX()];
	}
	
	
	private void zamenjajClen (Koordinati k, Polje novClen) {
		this.matrika[k.getY()][k.getX()] = novClen;
	}
	
	
	public boolean dodajKamen (Koordinati k, Polje kamen) {
		if (kamen == Polje.PRAZNO) return false;  // postavljamo "prazen kamen"
		if (vrniClen(k) != Polje.PRAZNO) return false;  // ne postavljamo kamna na prazno mesto
		zamenjajClen(k, kamen);
		preberiPraznaPolja();  // posodobi praznaPolja
		return true;
	}
	
	
	public boolean matrikaJePolna() {  // vrne true, �e nobeno polje ni prazno
		boolean obstajaPraznoPolje = false;
		for (int i = 0; i < this.stranica; i++) {
			for (int j = 0; j < this.stranica; j++) {
				if (this.matrika[i][j] == Polje.PRAZNO) {
					obstajaPraznoPolje = true;
				}
			}
		}
		return !obstajaPraznoPolje;
	}
	
	public Set<Vrsta> dodajVrste(Koordinati koordinati) {
		Set<Vrsta> okoliskeVrste = new HashSet<Vrsta>();
		int[][] smeri = {{1,0}, {0,1}, {1,1}, {1,-1}};
		int x = koordinati.getX();
		int y = koordinati.getY();
		for (int[] s : smeri) {
			int dx = s[0];
			int dy = s[1];
			Koordinati[] vrsta = new Koordinati[9];
			//LinkedList<Koordinati> vrsta = new LinkedList<Koordinati>();
			for (int k = -4; k <= 4; k++) {
				//if ((0 <= x + k * dx) && (x + k * dx < this.stranica) && (0 <= y + k * dy) && (y + k * dy < this.stranica)) {
					vrsta[k+4] = new Koordinati(x + k*dx, y + k*dy);
					//vrsta.add(new Koordinati(x + k*dx, y + k*dy));
				//}
				//else {
					//vrsta[k+4] = null;
					//vrsta.add(null);
				//}
			}
			//this.okoliskeVrste.add(new Vrsta(vrsta));
			okoliskeVrste.add(new Vrsta(vrsta));
		}
		return okoliskeVrste;
	}
	
	
	public Set<Vrsta> imamoResitev(Koordinati k) {
		int stevec = 1;
		Polje prejsnjePolje = Polje.PRAZNO;
		//for (Vrsta vrsta : this.okoliskeVrste) {
		Set<Vrsta> okoliskeVrste = dodajVrste(k);
		Set<Vrsta> zmagovalneVrste = new HashSet<Vrsta>();
		
		for (Vrsta vrsta : okoliskeVrste) {
			Koordinati[] zmagovalnaVrsta = new Koordinati[5];
			
			for (Koordinati koordinati : vrsta.tabelaKoordinat) {
				Polje naslednjePolje = vrniClen(koordinati);
				
				if (naslednjePolje == prejsnjePolje && naslednjePolje != Polje.PRAZNO) {
					zmagovalnaVrsta[stevec] = koordinati;
					stevec++;
				}
				else {
					zmagovalnaVrsta[0] = koordinati;
					stevec = 1; 
				}
				if (stevec == 5) { 
					zmagovalneVrste.add(new Vrsta(zmagovalnaVrsta)); 
				}
				prejsnjePolje = naslednjePolje;
			}
		}
		return zmagovalneVrste;
		
		/*
		Set<Vrsta> vseResitve = new HashSet<Vrsta>();  // ustvarimo mno�ico vseh vrst, ki so trenutno 'pet v vrsto'
		vseResitve.add(new Vrsta(  // TODO
				new Koordinati[]{  // samo primer
					new Koordinati(0, 0),
					new Koordinati(0, 0),
					new Koordinati(0, 0),
					new Koordinati(0, 0),
					new Koordinati(0, 0)
				}
			)
		);
		return vseResitve  // mno�ica re�itev (vrst) bo prazna, �e �e igre ni konec oz. je neodlo�eno
		*/
	}
	
	
//	private boolean preveriVrsto(Koordinati k) {
//		int stevec = 1;
//		Polje prejsnjePolje = Polje.PRAZNO;
//		int vrstica = k.getY();
//		
//		for (int j = 0; j < this.stranica; j++) {
//			if (this.matrika[vrstica][j] == prejsnjePolje && this.matrika[vrstica][j] != Polje.PRAZNO) {
//				stevec++;
//				prejsnjePolje = vrniClen(new Koordinati(j, vrstica));
//			}
//			else {
//				stevec = 1;
//				prejsnjePolje = vrniClen(new Koordinati(j, vrstica));
//			}
//			if (stevec == 5) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	
//	private boolean preveriStolpec(Koordinati k) {
//		int stevec = 1;
//		Polje prejsnjePolje = Polje.PRAZNO;
//		int stolpec = k.getX();
//		
//		for (int i = 0; i < this.stranica; i++) {
//			if (this.matrika[i][stolpec] == prejsnjePolje && this.matrika[i][stolpec] != Polje.PRAZNO) {
//				stevec++;
//				prejsnjePolje = vrniClen(new Koordinati(stolpec, i));
//			}
//			else {
//				stevec = 1;
//				prejsnjePolje = vrniClen(new Koordinati(stolpec, i));
//			}
//			if (stevec == 5) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	
//	private boolean preveriDiag1(Koordinati k) {
//		int stevec = 1;
//		Polje prejsnjePolje = Polje.PRAZNO;
//		int stolpec = k.getX();
//		int vrstica = k.getY();
//		int razlika = vrstica - stolpec;
//		
//		if (Math.abs(razlika) >= 0) {
//			for (int i = 0; i < this.stranica-Math.abs(razlika); i++) {
//				if (this.matrika[i+Math.abs(razlika)][i] == prejsnjePolje && this.matrika[i+Math.abs(razlika)][i] != Polje.PRAZNO) {
//					stevec++;
//					prejsnjePolje = vrniClen(new Koordinati(i, i+Math.abs(razlika)));
//				}
//				else {
//					stevec = 1;
//					prejsnjePolje = vrniClen(new Koordinati(i, i+Math.abs(razlika)));
//				}
//				if (stevec == 5) {
//					return true;
//				}
//			}
//		}
//		else {
//			for (int i = 0; i < this.stranica-Math.abs(razlika); i++) {
//				if (this.matrika[i][i+Math.abs(razlika)] == prejsnjePolje && this.matrika[i][i+Math.abs(razlika)] != Polje.PRAZNO) {
//					stevec++;
//					prejsnjePolje = vrniClen(new Koordinati(i+Math.abs(razlika), i));
//				}
//				else {
//					stevec = 1;
//					prejsnjePolje = vrniClen(new Koordinati(i+Math.abs(razlika), i));
//				}
//				if (stevec == 5) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	
//	
//	private boolean preveriDiag2(Koordinati k) {
//		int stevec = 1;
//		Polje prejsnjePolje = Polje.PRAZNO;
//		int stolpec = k.getX();
//		int vrstica = k.getY();
//		int vsota = vrstica + stolpec;
//		
//		if (vsota >= this.stranica-1) {
//			for (int i = 0; i < this.stranica-(vsota-this.stranica-1); i++) {
//				if (this.matrika[this.stranica-1-i][i+(vsota-this.stranica+1)] == prejsnjePolje && this.matrika[this.stranica-1-i][i+(vsota-this.stranica+1)] != Polje.PRAZNO) {
//					stevec++;
//					prejsnjePolje = vrniClen( Koordinati(i+(vsota-this.stranica+1), this.stranica-1-i));
//				}
//				else {
//					stevec = 1;
//					prejsnjePolje = vrniClen(new Koordinati(i+(vsota-this.stranica+1), this.stranica-1-i));
//				}
//				if (stevec == 5) {
//					return true;
//				}
//			}
//		}
//		else {
//			for (int i = 0; i < this.stranica-1-(vsota-this.stranica+1)-i; i++) {
//				if (this.matrika[this.stranica-1-(vsota-this.stranica+1)-i][i] == prejsnjePolje && this.matrika[this.stranica-1-(vsota-this.stranica+1)-i][i] != Polje.PRAZNO) {
//					stevec++;
//					prejsnjePolje = vrniClen(new Koordinati(i, this.stranica-1-(vsota-this.stranica+1)-i));
//				}
//				else {
//					stevec = 1;
//					prejsnjePolje = vrniClen(new Koordinati(i, this.stranica-1-(vsota-this.stranica+1)-i));
//				}
//				if (stevec == 5) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
	
	

	
	private void preberiPraznaPolja () {
		for (int i = 0; i < this.stranica; i++) {
			for (int j = 0; j < this.stranica; j++) {
				if (matrika[i][j] == Polje.PRAZNO) { 
					this.praznaPolja.add(new Koordinati(j, i));
				}
			}
		}
	}
	
	
	public List<Koordinati> vrniVsaPraznaPolja() {
		return praznaPolja;
	}
}
