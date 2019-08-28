package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
/*1. 이름 변경 메시지 보내기 확인
2. 디자인
3. 화면 visible 자유롭게
4. 주석 달기*/
public class MainFrame extends JFrame implements ActionListener{
	private JLabel MainImage;
	private JButton ServerBtn, ClientBtn;
	public MainFrame() {
		setTitle("3617 홍은빈");
		setSize(414, 736);
		JPanel panel = new JPanel();
        setContentPane(panel);
		setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		panel.setBackground(new Color(255,235,51));
		panel.setBounds(0, 0, 414, 736);
		
		MainImage = new JLabel(new ImageIcon("img\\kakao_logo.png"));
		
		MainImage.setBounds(79, 167, 257, 246);
		panel.add(MainImage);

		ServerBtn = new JButton("Server");
		ClientBtn = new JButton("Client");
		
		ServerBtn.setBounds(60, 438, 135, 135);
		ClientBtn.setBounds(222, 438, 135, 135);
		
		
		ClientBtn.addActionListener(this);
		ServerBtn.addActionListener(this);
		
		panel.add(ServerBtn);
		panel.add(ClientBtn);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(ClientBtn)) {
			ClientInputFrame a = new ClientInputFrame();
		}
		if(e.getSource().equals(ServerBtn)) {
			ServerInputFrame b = new ServerInputFrame();
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}