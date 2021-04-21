package grafika;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;

import graficni_vmesnik.Vodja;

import logika.Igra;
// import logika.Igralec;
import logika.Polje;
import logika.Vrsta;
// import tekstovni_vmesnik.VrstaIgralca;
import splosno.Koordinati;


/**
 * Pravokotno obmoèje, v katerem je narisano igralno polje.
 */
@SuppressWarnings("serial")
public class Platno extends JPanel implements MouseListener {
	
	double sirinaPolja;
	
	Color barvaRoba;  // rob kamna
	Color barvaMreze;
	Color barvaPolja;
	
	Stroke debelinaRoba;
	
	private static Map<Polje, Color> poljuPrirediBarvo;

	
	public Platno() {
		setBackground(Color.WHITE);
		this.addMouseListener(this);
		
		sirinaPolja = sirinaPolja();
		
		barvaRoba = Color.BLACK;
		barvaMreze = Color.BLACK;
		barvaPolja = new Color(255, 255, 196);
		
		debelinaRoba = new BasicStroke(3);
		
		poljuPrirediBarvo = new EnumMap<Polje, Color>(Polje.class);
		poljuPrirediBarvo.put(Polje.BELO, Color.WHITE);
		poljuPrirediBarvo.put(Polje.CRNO, Color.BLACK);
	}
	
	
	@Override
	public Dimension getPreferredSize() {  // privzete dimenzije?
		return new Dimension(400, 400);
	}
	
	
	// Širina enega polja
	private double sirinaPolja() {
		return Math.min(getWidth(), getHeight()) / Igra.N;
	}
	
	
	// Relativna širina èrte
	private final static double LINE_WIDTH = 0.08;
	
	
	// Relativni prostor okoli kamnov
	private final static double PADDING = 0.18;
	
	
	private double indeksStolpcaVXKoordinato(int i) {
		return (i + 0.5 * LINE_WIDTH + PADDING);
	}
	
	
	private double indeksStolpcaVYKoordinato(int j) {
		return (j + 0.5 * LINE_WIDTH + PADDING);
	}
	
	
	private void narisiKamen(Graphics2D g2, Koordinati k, Color barvaNotranjosti) {
		int x = (int) (indeksStolpcaVXKoordinato(k.getX()) * sirinaPolja);
		int y = (int) (indeksStolpcaVYKoordinato(k.getY()) * sirinaPolja);
		int premer = (int) ((1.0 - LINE_WIDTH - 2.0 * PADDING) * sirinaPolja);
		g2.setColor(barvaNotranjosti);
		g2.fillOval(
				x, 
				y, 
				premer, 
				premer
		);
		
		g2.setColor(barvaRoba);
		g2.setStroke(debelinaRoba);
		g2.drawOval(
				x, 
				y, 
				premer, 
				premer
		);
	}
	
	
	private void pobarvajOzadjePolja(Graphics2D g2, Koordinati k) {  // barvo nastavimo še pred klicem te metode
		g2.fillRect(
				(int) (sirinaPolja * k.getX()), 
				(int) (sirinaPolja * k.getY()), 
				(int) sirinaPolja, 
				(int) sirinaPolja
		);
	}
	
	
	private void pobarvajZmagovalnoVrsto(Graphics2D g2) {
		// pobarvamo ozadje zmagovalne vrste
		Set<Vrsta> vrste = null;
		if (Vodja.igra != null) {
			vrste = Vodja.igra.zmagovalneVrste;
		
			g2.setColor(barvaPolja);
			for (Vrsta vrsta : vrste) {
				for (Koordinati k : vrsta.tabelaKoordinat) {
					pobarvajOzadjePolja(g2, k);
				}
			}
		}
	}
	
	
	private void narisiMrezo(Graphics2D g2) {
		g2.setColor(barvaMreze);
		g2.setStroke(new BasicStroke((float) (sirinaPolja * LINE_WIDTH)));
		for (int i = 1; i < Igra.N; i++) {
			// navpiène èrte
			g2.drawLine(
					(int) (i * sirinaPolja),
				    (int) (0),
				    (int) (i * sirinaPolja),
				    (int) (Igra.N * sirinaPolja)
			);
			
			// vodoravne èrte
			g2.drawLine(
					(int) (0),
				    (int) (i * sirinaPolja),
				    (int) (Igra.N * sirinaPolja),
				    (int) (i * sirinaPolja)
			);
		}
	}
	
	
	private void narisiKamne(Graphics2D g2) {
		if (Vodja.igra != null) {
			Igra igra = Vodja.igra;
			for (int i = 0; i < Vodja.igra.velikost; i++) {
				for (int j = 0; j < Vodja.igra.velikost; j++) {
					Koordinati k = new Koordinati(i, j);
					/*
					switch(igra.vrniClen(k)) {
					case BELO: narisiKamen(g2, k, Color.WHITE); break;
					case CRNO: narisiKamen(g2, k, Color.BLACK); break;
					default: break;
					}
					*/
					narisiKamen(g2, k, poljuPrirediBarvo.get(igra.vrniClen(k)));
				}
			}
		} else {
			assert false;
		}
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {  // se klièe, ko uporabimo repaint(g);
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		pobarvajZmagovalnoVrsto(g2);

		narisiMrezo(g2);
		
		narisiKamne(g2);
	}
	
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if (Vodja.clovekNaVrsti) {
			int x = e.getX();
			int y = e.getY();
			int w = (int) (sirinaPolja);
			int i = x / w;
			double di = (x % w) / sirinaPolja;
			int j = y / w;
			double dj = (y % w) / sirinaPolja;
			if (
					0 <= i && i < Vodja.igra.velikost &&
					0.5 * LINE_WIDTH < di && di < 1.0 - 0.5 * LINE_WIDTH &&
					0 <= j && j < Vodja.igra.velikost &&
					0.5 * LINE_WIDTH < dj && dj < 1.0 - 0.5 * LINE_WIDTH
			) {
				Vodja.igrajClovekovoPotezo (new Koordinati(i, j));
			}
		}
	}
	
	
	@Override
	public void mousePressed(MouseEvent e) {
	}
	
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	
	
	@Override
	public void mouseExited(MouseEvent e) {
	}
}
