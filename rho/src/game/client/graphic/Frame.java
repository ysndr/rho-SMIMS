package game.client.graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import deps.*;
import game.*;

public class Frame extends JFrame implements ActionListener {

	private int width;
	private int height;
	private String koordinaten = "data/Weltkarte/Koordinaten.txt";
	private String graphen = "data/Weltkarte/Graphen.txt";

	private Spielbrett panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Frame frame = new Frame("Rho");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Frame(String head) {
		super(head);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = (int) (dim.getWidth() / 1.5);
		System.out.println(width);
		height = (int) (width * 2 / 3);
		System.out.println(height);
		setBounds(100, 100, width, height);

		// ----------------------------
		FeldBuilder fb = new FeldBuilder();
		fb.readFelder(koordinaten);
		fb.readEdges(graphen);
		ArrayList<Gebiet> g = Gebiet.readGebiete();
		ArrayList<Player> players = new ArrayList<Player>();

		ArrayList<Feld> fList = fb.getFelder();
		for (int i = 0; i < fList.size(); i++) {
			int x = i % 8;
			Player p = new Player(x);
			p.gameStart(x);
			players.add(p);
			fList.get(i).setZg(x);
		}
		panel = new Spielbrett(fb.getGraph().getEdges(), fb.getFelder(), g, players, this);
		setContentPane(panel);
		// ---------------------------------
		/*
		 * ManageClass mc = new ManageClass(); mc.addPlayer(new Player(0));
		 * mc.addPlayer(new Player(1)); mc.addPlayer(new Player(2));
		 * mc.addPlayer(new Player(3)); mc.beginn();
		 * 
		 * GameInfo gi = mc.getGameInfo();
		 * 
		 * setContentPane(new Spielbrett(gi.getM_SpielPlan().getEdges(),
		 * gi.getM_Felder(), gi.getM_Gebiete(), gi.getM_Spieler(), this));
		 */
		// ---------------------------------

		// pielbrett(List<Edge> pKanten, ArrayList<Feld> pFelder,
		// ArrayList<Gebiet> pGebiete,
		// ArrayList<Player> pPlayers, Frame pFrame) {

		repaint();
	}

	public static ArrayList<Vertex> convertAbiListToArrayList(List<Vertex> felder) {
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		felder.toFirst();
		while (felder.hasAccess()) {
			result.add(felder.getContent());
			felder.next();
		}
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof FeldButton) {
			FeldButton actButton = (FeldButton) e.getSource();
			actButton.setBackground(Color.RED);
		}
	}

	public void teamButtonsEnable(int team, boolean enabling) {
		ArrayList<Feld> team_felder = new ArrayList<Feld>();
		for (Feld actFeld : panel.getFelder()) {
			if (actFeld.getZg() == team) {
				team_felder.add(actFeld);
			}
		}
		panel.enableButtons(team_felder, enabling);
	}

	public void enableButtons(ArrayList<Feld> pFelder, boolean enabling) {
		panel.enableButtons(pFelder, enabling);
	}

}
