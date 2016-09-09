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
import javax.swing.JLabel;
import javax.swing.JPanel;

import deps.*;
import game.*;
import game.client.ClientHandler;
import game.client.RhoClient;

public class Frame extends JFrame implements ActionListener{

	private int width;
	private int height;

	
	private Spielbrett panel;
	private ClientHandler handler;

	/**
	 * Create the frame.
	 */
	public Frame(String head, ClientHandler handler) {
		super(head);
		
		panel = new Spielbrett(this);
		//panel = new Spielbrett();
		this.handler = handler;
		this.handler.setCallback(panel.getOnUpdate());
				
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		width = 950;
		System.out.println(width);
		height = 615;
		System.out.println(height);
		setBounds(100, 100, width, height);
		
		
		
		setContentPane(panel);
		panel.add(new JLabel("Hallo"));
		repaint();
		
	}
	
	
	
	
	public static ArrayList<Vertex> convertAbiListToArrayList(List<Vertex> felder)
	{
		ArrayList<Vertex> result = new ArrayList<Vertex>();
		felder.toFirst();
		while(felder.hasAccess())
		{
			result.add(felder.getContent());
			felder.next();
		}
		return result;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof FeldButton){
			FeldButton actButton = (FeldButton) e.getSource();
			handler.handleAction(actButton.getFeld_id());
		}else if (e.getSource() instanceof JButton){
			handler.end();
		}
		
		
	}
	/*
	public void teamButtonsEnable(int team, boolean enabling){
		ArrayList<Feld> team_felder = new ArrayList<Feld>();
		for(Feld actFeld : panel.getFelder()){
			if(actFeld.getZg() == team){
				team_felder.add(actFeld);
			}
		}
		panel.enableButtons(team_felder, enabling);
	}
	
	public void enableButtons(ArrayList<Feld> pFelder, boolean enabling){
		panel.enableButtons(pFelder, enabling);
	}*/
	
}

