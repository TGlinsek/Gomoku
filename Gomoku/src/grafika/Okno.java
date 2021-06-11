package grafika;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EnumMap;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.border.LineBorder;

import tekstovni_vmesnik.VrstaIgralca;
import logika.Igralec;


/**
 * Glavno okno aplikacije hrani trenutno stanje igre in nadzoruje potek
 * igre.
 *
 */
@SuppressWarnings("serial")
public class Okno extends JFrame implements ActionListener {
	/**
	 * JPanel, v katerega igramo
	 */
	private Platno platno;
	private int velikost = 15;
	
	// Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	//vrstica z izbirnimi menuji
	private JMenuBar menuBar;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenuItem belaPlosca;
	private JMenuItem rumenaPlosca;
	private JMenuItem modraPlosca;
	private JMenuItem zelenaPlosca;
	private JMenuItem rdecaPlosca;
	
	private JMenuItem sedem;
	private JMenuItem deset;
	private JMenuItem petnajst;
	private JMenuItem devetnajst;
	
	
	/**
	 * KONSTRUKTOR - ustvari novo glavno okno in prični igrati igro.
	 */
	public Okno() {
		
		this.setTitle("GOMOKU");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu IGRA
		menuBar = new JMenuBar();
		menuBar.setPreferredSize(new Dimension(400,50));
		this.setJMenuBar(menuBar);
		
		JMenu igra_menu = dodajMenu("NOVA IGRA", new Color(255,230,0));
		
		igraClovekRacunalnik = dodajMenuItem(igra_menu, "1 IGRALEC (ČRNI)", Color.LIGHT_GRAY);
		igraRacunalnikClovek = dodajMenuItem(igra_menu, "1 IGRALEC (BELI)", Color.WHITE);
		igraClovekClovek = dodajMenuItem(igra_menu, "2 IGRALCA", Color.LIGHT_GRAY);
		igraRacunalnikRacunalnik = dodajMenuItem(igra_menu, "NAVIJAČ", Color.WHITE);
		
		// menu VELIKOST
		JMenu velikost_menu = dodajMenu("VELIKOST", new Color(255,230,0));
		
		sedem = dodajMenuItem(velikost_menu, "7x7", Color.LIGHT_GRAY);
		deset = dodajMenuItem(velikost_menu, "10x10", Color.WHITE);
		petnajst = dodajMenuItem(velikost_menu, "15x15", Color.LIGHT_GRAY);
		devetnajst = dodajMenuItem(velikost_menu, "19x19", Color.WHITE);

		// menu BARVA
		JMenu barva_menu = dodajMenu("BARVA PLOŠČE", new Color(255,230,0));
		
		rdecaPlosca = dodajMenuItem(barva_menu, "RDEČA", new Color(255,142,142));
		modraPlosca = dodajMenuItem(barva_menu, "MODRA", new Color(137, 137, 255));
		zelenaPlosca = dodajMenuItem(barva_menu, "ZELENA", new Color(132,204,132));
		rumenaPlosca = dodajMenuItem(barva_menu, "RUMENA", new Color(255,234,81));		
		belaPlosca = dodajMenuItem(barva_menu, "BELA", Color.WHITE);
				
		// PLATNO
		platno = new Platno();
		
		GridBagConstraints polje_layout = new GridBagConstraints();
		polje_layout.gridx = 0;
		polje_layout.gridy = 0;
		polje_layout.fill = GridBagConstraints.BOTH;
		polje_layout.weightx = 1.0;
		polje_layout.weighty = 1.0;
		getContentPane().add(platno, polje_layout);
		
		// statusna vrstica za sporočila
		status = new JLabel();
		status.setFont(new Font("Monospaced", Font.BOLD, 30));
		status.setOpaque(true);
		status.setBackground(new Color(255,230,0));
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("IZBERI IGRO!");
				
	}
	
	public JMenu dodajMenu(String naslov, Color barva) {
		JMenu menu = new JMenu(naslov);
		menu.setOpaque(true);
		menu.setBackground(barva);
		menu.setBorder(new LineBorder(Color.BLACK));
		menu.setFont(new Font("Monospaced", Font.BOLD, 30));
		this.menuBar.add(menu);
		return menu;
	}
	
	public JMenuItem dodajMenuItem(JMenu menu, String naslov, Color barva) {
		JMenuItem menuitem = new JMenuItem(naslov);
		menuitem.setPreferredSize(new Dimension(220,60));
		menuitem.setOpaque(true);
		menuitem.setBackground(barva);
		menuitem.setFont(new Font("Monospaced", Font.BOLD, 22));
		menu.add(menuitem);
		menuitem.addActionListener(this);
		return menuitem;
	}
	
	public void nastaviVelikostPoljVPlatnu() {
		this.platno.nastaviVelikostPolj();
	}
		
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == igraClovekRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.igramoNovoIgro(this.velikost);
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.igramoNovoIgro(this.velikost);
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.igramoNovoIgro(this.velikost);
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.igramoNovoIgro(this.velikost);
		} 
		else if (e.getSource() == sedem) {
			this.velikost = 7;
		} else if (e.getSource() == deset) {
			this.velikost = 10;
		} else if (e.getSource() == petnajst) {
			this.velikost = 15;
		} else if (e.getSource() == devetnajst) {
			this.velikost = 19;
		} else if (e.getSource() == belaPlosca) {
			platno.spremeniOzadje(Color.WHITE);
		} else if (e.getSource() == rumenaPlosca) {
			platno.spremeniOzadje(new Color(255,234,81));
		} else if (e.getSource() == modraPlosca) {
			platno.spremeniOzadje(new Color(137, 137, 255));
		}else if (e.getSource() == rdecaPlosca) {
			platno.spremeniOzadje(new Color(255,142,142));
		}else if (e.getSource() == zelenaPlosca) {
			platno.spremeniOzadje(new Color(132,204,132));
		}
	}
	
	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ne poteka");
		}
		else {
			switch(Vodja.igra.trenutnoStanje) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU:
				status.setText("Na potezi je " + Vodja.igra.igralecNaPotezi + 
						" (" + Vodja.vrstaIgralca.get(Vodja.igra.igralecNaPotezi) + 
						")"); 
				break;
			case ZMAGA_CRNI:
				status.setText("ZMAGAL ČRNI - " + 
						Vodja.vrstaIgralca.get(Igralec.CRNI));
				break;
			case ZMAGA_BELI:
				status.setText("ZMAGAL BELI - " + 
						Vodja.vrstaIgralca.get(Igralec.BELI));
				break;
			}
		}
		platno.repaint();
	}
}