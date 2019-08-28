package kakao_Project;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Client_Input extends JDialog{
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	
	public Client_Input(JFrame frame, String title) {
		super(frame, title);
		setBounds(0, 0, 414, 736);
		setLayout(null);
		
		JLabel lblNewLabel = new JLabel("kakao talk");
		lblNewLabel.setBounds(74, 165, 265, 68);
		add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(93, 262, 291, 58);
		add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 319, 291, 58);
		add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(21, 267 ,53, 48);
		add(lblNewLabel_1);
		
		JLabel label = new JLabel("New label");
		label.setBounds(2, 324 ,91, 48);
		add(label);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(76, 542, 262, 67);
		add(btnNewButton);
		
		JLabel label_1 = new JLabel();
		label_1.setBounds(12,26,40,40);
		add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(243, 26, 40,40);
		add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 401, 290, 53);
		add(textField_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(21, 413,52,30);
		add(label_3);
	}
}

