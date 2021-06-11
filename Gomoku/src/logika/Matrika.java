package logika;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import splosno.Koordinati;

public class Matrika {
	
	private final int kolikoVVrsto; // vrsta igre (npr. 5 v vrsto, 4 v vrsto...)
	
	private Polje[][] matrika;
	private int stranica;
	private List<Koordinati> praznaPolja;  // zato, da se bo ra�unalnik lahko odlo�al med mo�nimi potezami

	
	public Matrika(int stranica) {
		this.stranica = stranica;
		this.matrika = new Polje[stranica][stranica];
		this.praznaPolja = new LinkedList<Koordinati>();
		izprazniMatriko();
		preberiPraznaPolja();
		kolikoVVrsto = 5;
	}
	
	// za igro v tekstovnem vmesniku
	@Override
	public String toString() {
		String niz = "";
		for (Polje[] vrsta : this.matrika) {
			niz += Arrays.toString(vrsta);
			niz += "\n";
		}
		return niz;
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
		if (!koordinateSoVMatriki(k)) {
			return null;
		}
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
	
	
	private boolean koordinateSoVMatriki(Koordinati k) {
		return k.getX() >= 0 &&
				k.getX() < this.stranica &&
				k.getY() >= 0 &&
				k.getY() < this.stranica;
	}
	
	
	// vrne mno�ico vseh vrst, ki so 5 v vrsto, so od igralca p in vsebujejo koordinato k
	public Set<Vrsta> vrniResitev (Igralec p, Koordinati k) {  // koordinate, kamor smo nazadnje postavili figuro
		Set<Vrsta> vseResitve = new HashSet<Vrsta>();
		
		for (Koordinati l : Vrsta.smeri.values()) {
			Polje[] tabela = new Polje[2 * kolikoVVrsto - 1];  // tabela dol�ine 11
			
			for (int odmik = -kolikoVVrsto + 1; odmik <= kolikoVVrsto - 1; odmik++) {
				// Koordinati prestavljeneKoordinate = k.pristejVektor(l.pomnozi(odmik));
				Koordinati prestavljeneKoordinate = new Koordinati(k.getX() + odmik * l.getX(), k.getY() + odmik * l.getY());
				
				if (!this.koordinateSoVMatriki(prestavljeneKoordinate)) {
					tabela[odmik + kolikoVVrsto - 1] = Polje.PRAZNO;
					continue;
				}
				tabela[odmik + kolikoVVrsto - 1] = this.vrniClen(prestavljeneKoordinate);
			}
			
			// zdaj poi��i morebitno (povezano) podzaporedje dol�ine 5
			int stevec = 0;
			for (int i = -kolikoVVrsto + 1; i <= kolikoVVrsto - 1; i++) {
				Polje polje = tabela[i + kolikoVVrsto - 1];
				
				if (polje == p.barvaPoteze()) {  // �e je tam kamen, ki pripada igral�evim kamnom
					stevec += 1;
				}
				else {
					stevec = 0;  // resetiraj nazaj na 0
				}
				
				if (stevec == kolikoVVrsto) {
					vseResitve.add(
						Vrsta.vrniVrsto(k, l, i - kolikoVVrsto + 1, i)  // -4 zato, ker tako dobimo vrsto dol�ine 5
					);
					break;
				}
				// TODO: nared �e za vrste dol�ine 6, 7, ...
			}
		}
		return vseResitve;
	}

	
	private void preberiPraznaPolja () {
		// pobri�emo seznam preden za�nemo polja dodajati
		this.praznaPolja.clear();  // brez tega se na novo zapolnjena polja ne odstranjujejo in bi hipoteti�no lahko ra�unalnik izbral �e zapolnjeno polje
		
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
