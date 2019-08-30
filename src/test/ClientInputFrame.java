package test;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientInputFrame extends JFrame implements ActionListener{
	private JTextField ip_input;
	private JTextField port_input;
	private JTextField name_input;
	private JLabel title_text,ip_text,port_text,name_text,back_img,home_img;
	private JButton start_btn;
	private Font font1, font2, font3;
	
	public ClientInputFrame() {
		setBounds(430, 0, 414, 736);
		
		setTitle("CLIENT 정보를 입력하세요");
		
		JPanel panel = new JPanel();
        setContentPane(panel);
		panel.setBackground(new Color(255,235,51));
		panel.setLayout(null);
		
		font1 = new Font("돋움", Font.PLAIN, 25);
		font2 = new Font("돋움", Font.PLAIN, 40);
		font3 = new Font("돋움", Font.PLAIN, 50);
		
		//input
		ip_input = new JTextField();
		port_input = new JTextField();
		name_input = new JTextField();
		
		ip_input.setBounds(93, 262, 291, 58);
		port_input.setBounds(93, 319, 291, 58);
		name_input.setBounds(93, 401, 290, 53);
		
		ip_input.setColumns(20);
		port_input.setColumns(10);
		name_input.setColumns(20);
		
		ip_input.setFont(font1);
		port_input.setFont(font1);
		name_input.setFont(font1);
		
		panel.add(ip_input);		
		panel.add(port_input);
		panel.add(name_input);
		
		//text
		title_text = new JLabel("kakao talk");
		ip_text = new JLabel("IP");
		port_text = new JLabel("PORT");
		name_text = new JLabel("이름");
		
		title_text.setBounds(74, 165, 265, 68);
		ip_text.setBounds(21, 267 ,53, 48);
		port_text.setBounds(2, 324 ,91, 48);
		name_text.setBounds(21, 413,52,30);
		
		title_text.setFont(font3);
		ip_text.setFont(font1);
		port_text.setFont(font1);
		name_text.setFont(font1);
		
		panel.add(title_text);
		panel.add(ip_text);
		panel.add(port_text);	
		panel.add(name_text);
		
		//btn
		start_btn = new JButton("채팅 참여하기");
		start_btn.setFont(font2);
		start_btn.setBounds(76, 542, 262, 67);
		start_btn.addActionListener(this);
		start_btn.setBackground(new Color(246, 246, 246));
		panel.add(start_btn);
		
		//img
		back_img = new JLabel();
		home_img = new JLabel("");
		
		back_img.setBounds(12,26,40,40);
		home_img.setBounds(243, 26, 40,40);
	
		panel.add(back_img);
		panel.add(home_img);
		
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start_btn)) {
			 if(ip_input.equals("")  || port_input.equals("") ) {
				 name_input.setText("");
				 ip_input.setText("");
				 port_input.setText("");
				 return;
		      }else {
		    	  String name = name_input.getText().toString();
		    	  String ip = ip_input.getText().toString();
		    	  String port = port_input.getText().toString();
		    	  ClientFrame a = new ClientFrame(name,ip,port);		
		    	  
		      }
		}	
	}
}

