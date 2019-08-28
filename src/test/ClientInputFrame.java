package test;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ClientInputFrame extends JFrame implements ActionListener{
	private JTextField ip_input;
	private JTextField port_input;
	private JTextField name_input;
	private JLabel title_text,ip_text,port_text,name_text,back_img,home_img;
	private JButton start_btn;
	private Font font1, font2, font3;
	
	public ClientInputFrame() {
		setBounds(0, 0, 414, 736);
		setLayout(null);
		
		font1 = new Font("µ¸¿ò", Font.PLAIN, 25);
		font2 = new Font("µ¸¿ò", Font.PLAIN, 40);
		font3 = new Font("µ¸¿ò", Font.PLAIN, 50);
		
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
		
		add(ip_input);		
		add(port_input);
		add(name_input);
		
		//text
		title_text = new JLabel("kakao talk");
		ip_text = new JLabel("IP");
		port_text = new JLabel("PORT");
		name_text = new JLabel("ÀÌ¸§");
		
		title_text.setBounds(74, 165, 265, 68);
		ip_text.setBounds(21, 267 ,53, 48);
		port_text.setBounds(2, 324 ,91, 48);
		name_text.setBounds(21, 413,52,30);
		
		title_text.setFont(font3);
		ip_text.setFont(font1);
		port_text.setFont(font1);
		name_text.setFont(font1);
		
		add(title_text);
		add(ip_text);
		add(port_text);	
		add(name_text);
		
		//btn
		start_btn = new JButton("Ã¤ÆÃ Âü¿©ÇÏ±â");
		start_btn.setBounds(76, 542, 262, 67);
		start_btn.addActionListener(this);
		add(start_btn);
		
		//img
		back_img = new JLabel();
		home_img = new JLabel("");
		
		back_img.setBounds(12,26,40,40);
		home_img.setBounds(243, 26, 40,40);
	
		add(back_img);
		add(home_img);
		
		setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource().equals(start_btn)) {
			 if(name_input.equals("") || ip_input.equals("")  || port_input.equals("") ) {
				 name_input.setText("");
				 ip_input.setText("");
				 port_input.setText("");
		      }else {
		    	  String name = name_input.getText().toString();
		    	  String ip = ip_input.getText().toString();
		    	  String port = port_input.getText().toString();
		    	  ClientFrame a = new ClientFrame(name,ip,port);		
		    	  
		      }
		}	
	}
}

