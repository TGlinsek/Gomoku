package grafika;

import java.awt.Color;
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

import grafika.Vodja;
import tekstovni_vmesnik.VrstaIgralca;
import logika.Igra;
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
	
	
	// Statusna vrstica v spodnjem delu okna
	private JLabel status;
	
	// Izbire v menujih
	private JMenuItem igraClovekRacunalnik;
	private JMenuItem igraRacunalnikClovek;
	private JMenuItem igraClovekClovek;
	private JMenuItem igraRacunalnikRacunalnik;
	
	private JMenuItem belaPlosca;
	private JMenuItem crnaPlosca;
	private JMenuItem modraPlosca;

	
	/**
	 * KONSTRUKTOR - ustvari novo glavno okno in prični igrati igro.
	 */
	public Okno() {
		
		this.setTitle("GOMOKU");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new GridBagLayout());
		
		// menu
		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);
		JMenu igra_menu = new JMenu("NOVA IGRA");
		menu_bar.add(igra_menu);
		
		igraClovekRacunalnik = new JMenuItem("TI proti MENI");
		igra_menu.add(igraClovekRacunalnik);
		igraClovekRacunalnik.addActionListener(this);
		
		igraRacunalnikClovek = new JMenuItem("JAZ proti TEBI");
		igra_menu.add(igraRacunalnikClovek);
		igraRacunalnikClovek.addActionListener(this);
		
		igraClovekClovek = new JMenuItem("TI proti FRENDU");
		igra_menu.add(igraClovekClovek);
		igraClovekClovek.addActionListener(this);
		
		igraRacunalnikRacunalnik = new JMenuItem("JAZ proti SEBI");
		igra_menu.add(igraRacunalnikRacunalnik);
		igraRacunalnikRacunalnik.addActionListener(this);
		

		// menu2
		JMenu igra_menu2 = new JMenu("BARVA PLO��E");
		menu_bar.add(igra_menu2);
		
		belaPlosca = new JMenuItem("BELA");
		igra_menu2.add(belaPlosca);
		belaPlosca.addActionListener(this);
		
		crnaPlosca = new JMenuItem("�RNA");
		igra_menu2.add(crnaPlosca);
		crnaPlosca.addActionListener(this);
		
		modraPlosca = new JMenuItem("MODRA");
		igra_menu2.add(modraPlosca);
		modraPlosca.addActionListener(this);
		
		
		// igralno polje oz. platno
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
		status.setFont(
				new Font(
						status.getFont().getName(),
						status.getFont().getStyle(),
						20
				)
		);
		GridBagConstraints status_layout = new GridBagConstraints();
		status_layout.gridx = 0;
		status_layout.gridy = 1;
		status_layout.anchor = GridBagConstraints.CENTER;
		getContentPane().add(status, status_layout);
		
		status.setText("IZBERI IGRO!");
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
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraClovekClovek) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.C);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.C);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == igraRacunalnikRacunalnik) {
			Vodja.vrstaIgralca = new EnumMap<Igralec, VrstaIgralca>(Igralec.class);
			Vodja.vrstaIgralca.put(Igralec.CRNI, VrstaIgralca.R);
			Vodja.vrstaIgralca.put(Igralec.BELI, VrstaIgralca.R);
			Vodja.igramoNovoIgro();
		} else if (e.getSource() == belaPlosca) {
			platno.spremeniOzadje(Color.WHITE);
		} else if (e.getSource() == crnaPlosca) {
			platno.spremeniOzadje(Color.BLACK);
		} else if (e.getSource() == modraPlosca) {
			platno.spremeniOzadje(new Color(127, 127, 255));  // modro-vijoli�na barva
		}
	}
	
	
	public void osveziGUI() {
		if (Vodja.igra == null) {
			status.setText("Igra ne poteka");
		}
		else {
			// Vodja.igra.spremeniStanjeIgre(Vodja.igra.zadnjaIgranaPoteza);
			switch(Vodja.igra.trenutnoStanje) {
			case NEODLOCENO: status.setText("Neodločeno!"); break;
			case V_TEKU:
				status.setText("Na potezi je " + Vodja.igra.igralecNaPotezi + 
						" - " + Vodja.vrstaIgralca.get(Vodja.igra.igralecNaPotezi)); 
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