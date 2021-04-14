package logika;

import java.util.LinkedList;
import java.util.List;


import splosno.Koordinati;

public class Matrika {
	
	private Polje[][] matrika;
	private int stranica;
	private List<Koordinati> praznaPolja;  // zato, da se bo raèunalnik lahko odloèal med možnimi potezami
	
	public Matrika(int stranica) {
		this.stranica = stranica;
		this.matrika = new Polje[stranica][stranica];
		this.praznaPolja = new LinkedList<Koordinati>();
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
	
	
	public boolean matrikaJePolna() {  // vrne true, èe nobeno polje ni prazno
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
	
	
	private boolean preveriVrsto(Koordinati k) {
		int stevec = 1;
		Polje prejsnjePolje = Polje.PRAZNO;
		int vrstica = k.getY();
		
		for (int j = 0; j < this.stranica; j++) {
			if (this.matrika[vrstica][j] == prejsnjePolje && this.matrika[vrstica][j] != Polje.PRAZNO) {
				stevec++;
				prejsnjePolje = vrniClen(new Koordinati(j, vrstica));
			}
			else {
				stevec = 1;
				prejsnjePolje = vrniClen(new Koordinati(j, vrstica));
			}
			if (stevec == 5) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean preveriStolpec(Koordinati k) {
		int stevec = 1;
		Polje prejsnjePolje = Polje.PRAZNO;
		int stolpec = k.getX();
		
		for (int i = 0; i < this.stranica; i++) {
			if (this.matrika[i][stolpec] == prejsnjePolje && this.matrika[i][stolpec] != Polje.PRAZNO) {
				stevec++;
				prejsnjePolje = vrniClen(new Koordinati(stolpec, i));
			}
			else {
				stevec = 1;
				prejsnjePolje = vrniClen(new Koordinati(stolpec, i));
			}
			if (stevec == 5) {
				return true;
			}
		}
		return false;
	}
	
	
	private boolean preveriDiag1(Koordinati k) {
		int stevec = 1;
		Polje prejsnjePolje = Polje.PRAZNO;
		int stolpec = k.getX();
		int vrstica = k.getY();
		int razlika = vrstica - stolpec;
		
		if (Math.abs(razlika) >= 0) {
			for (int i = 0; i < this.stranica-Math.abs(razlika); i++) {
				if (this.matrika[i+Math.abs(razlika)][i] == prejsnjePolje && this.matrika[i+Math.abs(razlika)][i] != Polje.PRAZNO) {
					stevec++;
					prejsnjePolje = vrniClen(new Koordinati(i, i+Math.abs(razlika)));
				}
				else {
					stevec = 1;
					prejsnjePolje = vrniClen(new Koordinati(i, i+Math.abs(razlika)));
				}
				if (stevec == 5) {
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < this.stranica-Math.abs(razlika); i++) {
				if (this.matrika[i][i+Math.abs(razlika)] == prejsnjePolje && this.matrika[i][i+Math.abs(razlika)] != Polje.PRAZNO) {
					stevec++;
					prejsnjePolje = vrniClen(new Koordinati(i+Math.abs(razlika), i));
				}
				else {
					stevec = 1;
					prejsnjePolje = vrniClen(new Koordinati(i+Math.abs(razlika), i));
				}
				if (stevec == 5) {
					return true;
				}
			}			
		}
		return false;
	}
	
	
	private boolean preveriDiag2(Koordinati k) {
		int stevec = 1;
		Polje prejsnjePolje = Polje.PRAZNO;
		int stolpec = k.getX();
		int vrstica = k.getY();
		int vsota = vrstica + stolpec;
		
		if (vsota >= this.stranica-1) {
			for (int i = 0; i < this.stranica-(vsota-this.stranica-1); i++) {
				if (this.matrika[this.stranica-1-i][i+(vsota-this.stranica+1)] == prejsnjePolje && this.matrika[this.stranica-1-i][i+(vsota-this.stranica+1)] != Polje.PRAZNO) {
					stevec++;
					prejsnjePolje = vrniClen(new Koordinati(i+(vsota-this.stranica+1), this.stranica-1-i));
				}
				else {
					stevec = 1;
					prejsnjePolje = vrniClen(new Koordinati(i+(vsota-this.stranica+1), this.stranica-1-i));
				}
				if (stevec == 5) {
					return true;
				}
			}
		}
		else {
			for (int i = 0; i < this.stranica-1-(vsota-this.stranica+1)-i; i++) {
				if (this.matrika[this.stranica-1-(vsota-this.stranica+1)-i][i] == prejsnjePolje && this.matrika[this.stranica-1-(vsota-this.stranica+1)-i][i] != Polje.PRAZNO) {
					stevec++;
					prejsnjePolje = vrniClen(new Koordinati(i, this.stranica-1-(vsota-this.stranica+1)-i));
				}
				else {
					stevec = 1;
					prejsnjePolje = vrniClen(new Koordinati(i, this.stranica-1-(vsota-this.stranica+1)-i));
				}
				if (stevec == 5) {
					return true;
				}
			}			
		}
		return false;
	}
	
	
	public boolean imamoResitev(Koordinati k) {
		return preveriVrsto(k) || preveriStolpec(k) || preveriDiag1(k) || preveriDiag2(k);
	}
	
	
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
