package kakao_Project;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Main extends JFrame implements ActionListener{
	private JLabel MainImage;
	private JButton ServerBtn, ClientBtn;
	Client_Input client_input;
	Server_Input server_input;
	public Main() {
		JPanel panel = new JPanel();
        setContentPane(panel);
        
		setLayout(null);
		setTitle("정보보호 수행평가");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		client_input = new Client_Input(this, "방 입장하기");
		server_input = new Server_Input(this, "방 입장하기");
		
		panel.setBackground(new Color(255,235,51));
		panel.setBounds(0, 0, 414, 736);
		
		MainImage = new JLabel(new ImageIcon("img\\kakao_logo.png"));
		
		MainImage.setBounds(79, 167, 257, 246);
		panel.add(MainImage);

		ServerBtn = new JButton("Server");
		ClientBtn = new JButton("Client");
		
		ServerBtn.setBounds(60, 438, 135, 135);
		ClientBtn.setBounds(222, 438, 135, 135);
		
		ServerBtn.addActionListener(this);
		ClientBtn.addActionListener(this);
		panel.add(ServerBtn);
		panel.add(ClientBtn);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ClientBtn)) {
			client_input.setVisible(true);
		}
		if(e.getSource().equals(ServerBtn)) {
			client_input.setVisible(true);
		}
	}
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setSize(414, 736);
					frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		});	
	}
}
