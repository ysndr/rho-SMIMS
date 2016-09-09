package game.client.graphic;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.client.RhoClient;

public class LoginScreen extends JFrame implements ActionListener {

	private int m_width;
	private int m_height;
	private JTextField m_PortTF;
	private JLabel m_PortL;
	private JLabel m_HeadingL;
	private JLabel m_IPL;
	private JTextField m_IPTF;
	private JButton m_EnterB;
	
	public static void main(String[] args){
		RhoClient client = new RhoClient("localhost", 6631);
		if(client.isConnected()){
			new Frame("ϱ-Risiko-online", client.getClientHandler()).setVisible(true);
			
		}
		/*EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen frame = new LoginScreen("ϱ-Risiko-online Loginscreen");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});*/
	}
	
	public LoginScreen(String head) {
		super(head);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds();
		initComponets();
	}
	
	private void setBounds()
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		m_width = (int) (400);
		m_height = (int) (200);
		setBounds(100, 100, m_width, m_height);
	}
	
	private void initComponets()
	{
		m_HeadingL = new JLabel("ϱ Serveranmeldung:");
		m_HeadingL.setBounds(0,0,m_width,50);
		m_HeadingL.setHorizontalAlignment(JLabel.CENTER);
		
		m_PortL = new JLabel("Port:");
		m_PortL.setBounds(50, 50, 40, 20);
		m_PortTF = new JTextField();
		m_PortTF.setBounds( (int)(m_PortL.getBounds().x+m_PortL.getBounds().getWidth()+10),50, 100, 20);

		m_IPL = new JLabel("IP:");
		m_IPL.setBounds(50,m_PortL.getBounds().y + m_PortL.getBounds().height + 10, 30,20);
		m_IPTF = new JTextField();
		m_IPTF.setBounds(m_PortTF.getBounds().x, m_PortL.getBounds().y + m_PortL.getBounds().height + 10, 200, 20);
		m_EnterB = new JButton("Bestätigen");
		m_EnterB.setBounds(m_width/2-50,(int)(m_IPL.getBounds().y+m_IPL.getBounds().getHeight()+10),100,30);
		m_EnterB.addActionListener(this);
		
		JPanel panel = new JPanel();
		panel.add(m_PortL);
		panel.setLayout(null);
		panel.add(m_PortTF);
		panel.add(m_IPL);
		panel.add(m_IPTF);
		panel.add(m_EnterB);
		panel.add(m_HeadingL);
		setContentPane(panel);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource().equals(m_EnterB)){
			String PortInput, IPInput;
			int Port;
			try
			{
				PortInput = m_PortTF.getText();
				Port = Integer.parseInt(PortInput);
				IPInput = m_IPTF.getText();
				RhoClient client = new RhoClient(IPInput, Port);
				if(client.isConnected()){
					new Frame("ϱ-Risiko-online", client.getClientHandler()).setVisible(true);
					
				}
			}
			catch(Exception e)
			{
				System.out.println("Ungültige Eingabe");
			}
		}
	}

}
