package kakao_Project;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class aa extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					aa frame = new aa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public aa() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 414, 736);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		
		JLabel lblNewLabel = new JLabel("kakao talk");
		lblNewLabel.setBounds(74, 165, 265, 68);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(93, 262, 291, 58);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(93, 319, 291, 58);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setBounds(21, 267 ,53, 48);
		contentPane.add(lblNewLabel_1);
		
		JLabel label = new JLabel("New label");
		label.setBounds(2, 324 ,91, 48);
		contentPane.add(label);
		
		JButton btnNewButton = new JButton("New button");
		btnNewButton.setBounds(76, 542, 262, 67);
		contentPane.add(btnNewButton);
		
		JLabel label_1 = new JLabel();
		label_1.setBounds(12,26,40,40);
		contentPane.add(label_1);
		
		JLabel label_2 = new JLabel("");
		label_2.setBounds(243, 26, 40,40);
		contentPane.add(label_2);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(93, 401, 290, 53);
		contentPane.add(textField_2);
		
		JLabel label_3 = new JLabel("New label");
		label_3.setBounds(21, 413,52,30);
		contentPane.add(label_3);
	}

}
