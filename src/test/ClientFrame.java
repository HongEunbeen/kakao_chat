package test;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientFrame extends JFrame{
	static String name = "", ip = "", port = "";
	private String nickName;
	
	String hostAddress;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	private JPanel contentPane;
	JLabel ip_text, my_ip_text;
	JTextField name_input, content_input;
	JButton modify_btn,back_btn, send_btn;
	JTextArea chat_room;
	
	public ClientFrame(String name,String ip,String port) {
		this.name = name;
		this.ip = ip;
		this.port = port;
		
		setTitle("Client");
		setBounds(0, 0, 414, 736);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		back_btn = new JButton(new ImageIcon("img\\back.png"));
		ip_text = new JLabel("내  IP");
		send_btn = new JButton(new ImageIcon("img\\send.png"));
		my_ip_text = new JLabel(ip + " : " + port);
		
		my_ip_text.setBounds(135, 12, 232, 20);		
		back_btn.setBounds(13, 12, 52, 55);
		ip_text.setBounds(79, 12, 42, 20);
		send_btn.setBounds(332, 635, 42, 42);
		
		contentPane.add(back_btn);
		contentPane.add(ip_text);
		contentPane.add(send_btn);
		contentPane.add(my_ip_text);
		
		//input
		name_input = new JTextField();
		content_input = new JTextField();
		
		name_input.setBounds(79, 36, 223, 34);
		content_input.setBounds(13, 635, 313, 42);
		
		content_input.setColumns(30);
		name_input.setColumns(30);
		
		contentPane.add(name_input);
		contentPane.add(content_input);
	
		//btn
		modify_btn = new JButton("수정");
		modify_btn.setBounds(308, 36, 74, 34);
		modify_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String be_nick = nickName;
				setNickname(name_input.getText().toString());
				sendMessage("<"+ be_nick + "님이 '" + nickName + "' (으)로 이름을 변경하였습니다.>\n");
				name_input.setText("");				
			}
		});
		contentPane.add(modify_btn);
		
		//area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 84, 368, 539);
		contentPane.add(scrollPane);		
		chat_room = new JTextArea();
		scrollPane.setViewportView(chat_room);
		
		
		//nickname
		if(this.name.equals("")) {
			setNickname(this.ip);
		}else {
			setNickname(this.name);
		}
		
		//send 버튼 클릭에 반응하는 리스너 추가
		send_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
			  String msg =nickName+" : "+ content_input.getText() + "\n";
		      sendMessage(msg);
		      content_input.setText("");
			}
		});
		//엔터키 눌렀을 때 반응하기
		content_input.addKeyListener(new KeyAdapter() {
			//키보드에서 키 하나를 눌렀을때 자동으로 실행되는 메소드..: 콜백 메소드
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);
				
			//입력받은 키가 엔터인지 알아내기, KeyEvent 객체가 키에대한 정보 갖고있음
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_ENTER:
					String msg =nickName+" : "+ content_input.getText() + "\n";
					sendMessage(msg);
					content_input.setText("");
					break;
				}
			}
		});
		
		setVisible(true);
		content_input.requestFocus();
		
		//서버와 연결하는 네트워크 작업 : 스레드 객체 생성 및 실행
		ClientThread clientThread = new ClientThread();
		clientThread.setDaemon(true);
		clientThread.start();
		
		addWindowListener(new WindowAdapter() {			
			@Override //클라이언트 프레임에 window(창) 관련 리스너 추가
			public void windowClosing(WindowEvent e) {				
				super.windowClosing(e);
				try {
					if(dos != null) dos.close();
					if(dis != null) dis.close();
					if(socket != null) socket.close();
				} catch (IOException e1) {					
					e1.printStackTrace();
				}
			}			
		});
		
	}//생성자
	
	//이너클래스 : 서버와 연결하는 네트워크 작업 스레드
	class ClientThread extends Thread {
		
		@Override
		public void run() {
			try {
				socket = new Socket(ip, Integer.parseInt(port));
				chat_room.append("< 채팅방에 참여하였습니다. >\n");
				
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
			
	            //접속하자마자 닉네임 전송하면, 서버가 닉네임으로 인식 
				dos.writeUTF(name);
	            System.out.println("클라이언트 : 닉네임 전송완료 ");	
	            
	            while(dis!=null){
	            	String msg = dis.readUTF();
	            	chat_room.append(msg);
	           }			            
			} catch (IOException e) {
	            e.printStackTrace();
			}
		}			    
	}
	 public void sendMessage(String msg){
	        try {
	        	dos.writeUTF(msg);
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }    
    public void setNickname(String nickName){
        this.nickName = nickName;
    }
}//class

