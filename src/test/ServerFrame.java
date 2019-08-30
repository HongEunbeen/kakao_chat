package test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class ServerFrame extends JFrame {
	static String name = "", ip = "", port = "";
	private String nickName;
	
	String hostAddress;
	ServerSocket serverSocket;
	Socket socket;
	DataInputStream dis;
	DataOutputStream dos;
	
	private JPanel contentPane;
	JLabel ip_text, my_ip_text;
	JTextField name_input, content_input;
	JButton modify_btn,back_btn, send_btn;
	JTextArea chat_room;
	private Font font1, font2, font3;
	
	
	private String msg;
	private Map<String, DataOutputStream> clientMap = new HashMap<String, DataOutputStream>();

	public ServerFrame(String name,String ip,String port) {
		this.name = name;
		this.ip = ip;
		this.port = port;	


		font1 = new Font("돋움", Font.PLAIN, 20);
		font2 = new Font("돋움", Font.PLAIN, 22);
		font3 = new Font("돋움", Font.PLAIN, 50);
		
		setTitle("Server");
		setBounds(850, 0, 414, 736);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		contentPane.setBackground(new Color(180, 201, 220));
		
		Image backImg = new ImageIcon("img\\back.png").getImage();
		Image cutBackImg = backImg.getScaledInstance(52, 52, java.awt.Image.SCALE_SMOOTH);
		ImageIcon cutBackIcon = new ImageIcon(cutBackImg);
		
		Image sendImg = new ImageIcon("img\\send.png").getImage();
		Image cutSendImg = sendImg.getScaledInstance(52, 52, java.awt.Image.SCALE_SMOOTH);
		ImageIcon cutSendIcon = new ImageIcon(cutSendImg);
		
		
		back_btn = new JButton(cutBackIcon);
		send_btn = new JButton(cutSendIcon);
		
		ip_text = new JLabel("내  IP");
		my_ip_text = new JLabel(ip + " : " + port);
		
		ip_text.setFont(new Font("돋움",Font.PLAIN,15));
		my_ip_text.setFont(font1);
		
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
		
		name_input.setFont(font2);
		content_input.setFont(font2);
		
		contentPane.add(name_input);
		contentPane.add(content_input);
		
		//btn
		modify_btn = new JButton("수정");
		modify_btn.setBackground(new Color(246, 246, 246));
		modify_btn.setBounds(308, 36, 74, 34);
		modify_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String be_nick = nickName;
				setNickname(name_input.getText().toString());
				sendMessage("<"+ be_nick + "님이 '" + nickName + "' (으)로 이름을 변경하였습니다.>\n");
				chat_room.append("<"+ be_nick + "님이 '" + nickName + "' (으)로 이름을 변경하였습니다.>\n");
				name_input.setText("");
			}
		});
		contentPane.add(modify_btn);
		
		//area
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(14, 84, 368, 539);
		contentPane.add(scrollPane);	
		
		chat_room = new JTextArea();
		chat_room.setFont(new Font("굴림", Font.PLAIN, 18));
		chat_room.setLineWrap(true);
		chat_room.setBackground(new Color(180, 201, 220));
		scrollPane.setViewportView(chat_room);
		
		//nickname
		if(this.name.equals("") || name_input.getText().equals(null)) {
			setNickname(this.ip);
		}else {
			setNickname(this.name);
		}
		
		send_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = nickName+" > "+ content_input.getText() + "\n";
		        System.out.print(msg);
		        sendMessage(msg);
		        chat_room.append(msg);
		        content_input.setText("");
			}
		});
		
		//엔터키 눌렀을 때 반응하기
		content_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);			
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_ENTER:
					String msg = nickName+" > " +content_input.getText() + "\n";
			        System.out.print(msg);
			        sendMessage(msg);
			        chat_room.append(msg);
			        content_input.setText("");
					break;
				}
			}
		});		
		
		setVisible(true);
		content_input.requestFocus();
	
		ServerThread serverThread = new ServerThread();
		serverThread.setDaemon(true); //메인 끝나면 같이 종료
		serverThread.start();
		
		addWindowListener(new WindowAdapter() {			
			@Override 
			public void windowClosing(WindowEvent e) {				
				super.windowClosing(e);
				try {
					if(dos != null) dos.close();
					if(dis != null) dis.close();
					if(socket != null) socket.close();
					if(serverSocket != null) serverSocket.close();
				} catch (IOException e1) {					
					e1.printStackTrace();
				}
			}			
		});
	}//생성자 메소드	
	class ServerThread extends Thread {
		@Override
		public void run() {	
			
			try {  //서버 소켓 생성 작업
							
				Collections.synchronizedMap(clientMap); //교통정리를 해준다.
				serverSocket = new ServerSocket(Integer.parseInt(port));////서버소켓을 생성하여  포트와 결합(bind)시킨다
			
				chat_room.append("<채팅방이 개설되었습니다.>\n");
				chat_room.append("<참여자를 기다리는 중입니다.>\n");				
				
				while(true) {
					socket = serverSocket.accept();//클라이언트가 접속할때까지 커서(스레드)가 대기한다.
					chat_room.append("<" + socket.getInetAddress() +" : " + socket.getPort()+ "님이 접속하셨습니다.>\n");
					
					Receiver receiver = new Receiver(socket);//Receiver를 이용해서 네트워크 소켓을 받아서 계속듣고 보내는 일을 한다.
	                receiver.start();
				}				
			} catch (IOException e) {
				e.printStackTrace();
				chat_room.append("<참여자가 방을 나갔습니다.>\n");
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}
	class Receiver extends Thread {
        private DataInputStream in; // 데이터 입력 스트림
        private DataOutputStream out; // 데이터 아웃풋 스트림
        private String nick;//nickname
 
        public Receiver(Socket socket) {
            try {
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                nick = name;
                addClient(nick,out);//새로운 클라이언트를 추가한다.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        public void run() {
            try {
                while (in != null) {
                    msg = in.readUTF();// UTF로 읽어들인다.
                    sendMessage(msg);
                    chat_room.append(msg);	               
              }
            } catch (Exception e) {
                //사용접속종료시 여기서 에러발생. 
                removeClient(nick);
            }
        }
    }
    //맵의내용(클라이언트) 저장과 삭제 
    public void addClient(String nick, DataOutputStream out) throws IOException{
        if(!name.equals("")) {
        	String message = "<" + ip + "님께서" + nick + "(으)로 이름은 변경하였습니다. >\n";
            sendMessage(message);
        }
        clientMap.put(nick, out);
        
    }
    
    public void removeClient(String nick){
        String message = "<사용자가 나갔습니다.> \n";
        sendMessage(message);
        chat_room.append(message);
        clientMap.remove(nick);
    }
//메세지 내용 전파 
    public void sendMessage (String msg){
        Iterator<String> iterator = clientMap.keySet().iterator(); //key셋으로 반복자지정
        String key = "";
        
        while(iterator.hasNext()){
            key = iterator.next();// 반복자에서 하나하나 키를 빼온다.
            try{
                clientMap.get(key).writeUTF(msg);
            } catch(IOException e){
                e.printStackTrace();
            }
        }
    }
    public void setNickname(String nickName){
        this.nickName = nickName;
    }


}//class

