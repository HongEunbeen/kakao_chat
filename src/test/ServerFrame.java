package test;
import java.awt.BorderLayout;
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
	
	
	
	private String msg;
	private Map<String, DataOutputStream> clientMap = new HashMap<String, DataOutputStream>();

	public ServerFrame(String name,String ip,String port) {
		this.name = name;
		this.ip = ip;
		this.port = port;	

		setTitle("Server");
		setBounds(0, 0, 414, 736);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		back_btn = new JButton(new ImageIcon("img\\back.png"));
		ip_text = new JLabel("��  IP");
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
		modify_btn = new JButton("����");
		modify_btn.setBounds(308, 36, 74, 34);
		modify_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String be_nick = nickName;
				setNickname(name_input.getText().toString());
				sendMessage("<"+ be_nick + "���� '" + nickName + "' (��)�� �̸��� �����Ͽ����ϴ�.>\n");
				chat_room.append("<"+ be_nick + "���� '" + nickName + "' (��)�� �̸��� �����Ͽ����ϴ�.>\n");
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
		
		send_btn.addActionListener(new ActionListener() {			
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = nickName+" : "+ content_input.getText() + "\n";
		        System.out.print(msg);
		        sendMessage(msg);
		        chat_room.append(msg);
		        content_input.setText("");
			}
		});
		
		//����Ű ������ �� �����ϱ�
		content_input.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {				
				super.keyPressed(e);			
				int keyCode = e.getKeyCode();
				switch(keyCode) {
				case KeyEvent.VK_ENTER:
					String msg = nickName+" : " +content_input.getText() + "\n";
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
		serverThread.setDaemon(true); //���� ������ ���� ����
		serverThread.start();
		
		addWindowListener(new WindowAdapter() {			
			@Override //Ŭ���̾�Ʈ �����ӿ� window(â) ���� ������ �߰�
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
	}//������ �޼ҵ�	
	class ServerThread extends Thread {
		@Override
		public void run() {	
			
			try {  //���� ���� ���� �۾�
							
				Collections.synchronizedMap(clientMap); //���������� ���ش�.
				serverSocket = new ServerSocket(Integer.parseInt(port));////���������� �����Ͽ�  ��Ʈ�� ����(bind)��Ų��
			
				chat_room.append("<ä�ù��� �����Ǿ����ϴ�.>\n");
				chat_room.append("<�����ڸ� ��ٸ��� ���Դϴ�.>\n");				
				
				while(true) {
					socket = serverSocket.accept();//Ŭ���̾�Ʈ�� �����Ҷ����� Ŀ��(������)�� ����Ѵ�.
					chat_room.append("<" + socket.getLocalPort() +" : " + socket.getPort()+ "���� �����ϼ̽��ϴ�.>\n");
					System.out.println(socket.getInetAddress()+"�� "+socket.getPort()

					+ "��Ʈ��" + socket.getLocalAddress()+"�� "+socket.getLocalPort()

					+ "��Ʈ�� ����Ǿ����ϴ�.");

					
					Receiver receiver = new Receiver(socket);//Receiver�� �̿��ؼ� ��Ʈ��ũ ������ �޾Ƽ� ��ӵ�� ������ ���� �Ѵ�.
	                receiver.start();
				}				
			} catch (IOException e) {
				chat_room.append("<�����ڰ� ���� �������ϴ�.>\n");
			}
		}
	}
	class Receiver extends Thread {
        private DataInputStream in; // ������ �Է� ��Ʈ��
        private DataOutputStream out; // ������ �ƿ�ǲ ��Ʈ��
        private String nick;//nickname
 
        public Receiver(Socket socket) {
            try {
                out = new DataOutputStream(socket.getOutputStream());
                in = new DataInputStream(socket.getInputStream());
                nick = name;
                addClient(nick,out);//���ο� Ŭ���̾�Ʈ�� �߰��Ѵ�.
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
 
        @Override
        public void run() {
            try {
                while (in != null) {
                    msg = in.readUTF();// UTF�� �о���δ�.
                    sendMessage(msg);
                    chat_room.append(msg);	               
              }
            } catch (Exception e) {
                //������������ ���⼭ �����߻�. 
                removeClient(nick);
            }
        }
    }
    //���ǳ���(Ŭ���̾�Ʈ) ����� ���� 
    public void addClient(String nick, DataOutputStream out) throws IOException{
        if(!name.equals("")) {
        	String message = "<" + ip + "�Բ���" + nick + "(��)�� �̸��� �����Ͽ����ϴ�. >\n";
            sendMessage(message);
        }
        clientMap.put(nick, out);
        
    }
    
    public void removeClient(String nick){
        String message = "<"+ nick + "���� �����̽��ϴ�.> \n";
        sendMessage(message);
        chat_room.append(message);
        clientMap.remove(nick);
    }
//�޼��� ���� ���� 
    public void sendMessage (String msg){
        Iterator<String> iterator = clientMap.keySet().iterator(); //key������ �ݺ�������
        String key = "";
        
        while(iterator.hasNext()){
            key = iterator.next();// �ݺ��ڿ��� �ϳ��ϳ� Ű�� ���´�.
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

