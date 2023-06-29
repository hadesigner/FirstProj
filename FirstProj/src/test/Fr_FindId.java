package test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;



public class Fr_FindId extends JFrame {

	DB_MemberDAO memberDao;
	DB_CommDAO commDao;
	Fr_AdminMain am;
	Fr_MemberMain mm;
	
	private JPanel contentPane;
	private JTextField textname;
	private JTextField textbirth;

	public Fr_FindId() {
		
		setVisible(true);
		setTitle("ID 찾기");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 482, 120);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblId = new JLabel("이름");
		lblId.setBounds(30, 20, 57, 15);
		contentPane.add(lblId);

		JLabel lblPW = new JLabel("생년월일");
		lblPW.setBounds(30, 43, 57, 15);
		contentPane.add(lblPW);

		textname = new JTextField();
		textname.setBounds(99, 17, 202, 21);
		contentPane.add(textname);
		textname.setColumns(10);

		textbirth = new JTextField();
		textbirth.setColumns(10);
		textbirth.setBounds(99, 40, 202, 21);
		contentPane.add(textbirth);

		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setBounds(31, 83, 420, 15);
		contentPane.add(lblNewLabel_1);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.setBounds(313, 17, 136, 44);
		btnNewButton.setActionCommand("FindID"); // 
		
		
		
		
		
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String sid = "";
				if (textname.getText().equals("") || textbirth.getText().equals("")) //
				{
					Fr_M_Login.ShowMessage("빈공간이 있습니다.");
					return;
				} else
//					sid = sql(textname.getText(), textbirth.getText());
				if (sid.equals("")) {
					Fr_M_Login.ShowMessage("일치하는 정보가 없습니다.");
				} else {
					StringBuffer strbuffer = new StringBuffer();
					strbuffer.append(sid);
					lblNewLabel_1.setText("일치하는 정보가 없습니다." + IDOutput(strbuffer) + "짜잔");
				}
			}
		});
		contentPane.add(btnNewButton);
	}



	private StringBuffer IDOutput(StringBuffer sId) // 
	{
		String str = "";
		for (int i = 0; i < (sId.length() - 3); i++) {
			str += "*";
		}
		if (sId.length() >= 3)
			return sId.replace(3, sId.length(), str);
		return sId;
	}
}
