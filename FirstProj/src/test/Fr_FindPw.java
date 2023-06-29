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

public class Fr_FindPw extends JFrame {

	DB_MemberDAO memberDao;
	DB_CommDAO commDao;
	Fr_AdminMain am;
	Fr_MemberMain mm;
	
	private JPanel contentPane;
	private JTextField textFieldId;
	private JTextField textFieldName;
	private JTextField textFieldBirth;

	public Fr_FindPw() {
		setVisible(true);
		setTitle("비밀번호 찾기");
		setBounds(100, 100, 489, 150);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblI = new JLabel("아이디");
		lblI.setHorizontalAlignment(SwingConstants.RIGHT);
		lblI.setBounds(26, 20, 57, 15);
		contentPane.add(lblI);

		JLabel label = new JLabel("이름");
		label.setHorizontalAlignment(SwingConstants.RIGHT);
		label.setBounds(26, 45, 57, 15);
		contentPane.add(label);

		JLabel label_1 = new JLabel("생년월일");
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(26, 70, 57, 15);
		contentPane.add(label_1);

		textFieldId = new JTextField();
		textFieldId.setBounds(95, 17, 230, 21);
		contentPane.add(textFieldId);
		textFieldId.setColumns(10);

		textFieldName = new JTextField();
		textFieldName.setColumns(10);
		textFieldName.setBounds(95, 42, 230, 21);
		contentPane.add(textFieldName);

		textFieldBirth = new JTextField();
		textFieldBirth.setColumns(10);
		textFieldBirth.setBounds(95, 67, 230, 21);
		contentPane.add(textFieldBirth);

		JLabel lblNewLabel = new JLabel(""); 
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(26, 113, 435, 15);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton("검색");
		btnNewButton.setBounds(337, 16, 124, 72);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String spw = "";
				if (textFieldId.getText().equals("") || textFieldName.getText().equals("")
						|| textFieldBirth.getText().equals("")) //
				{
					Fr_M_Login.ShowMessage("빈공간이 있습니다.");
					return;
				} else
//					spw = FindPwValue(textFieldId.getText(), textFieldName.getText(), textFieldBirth.getText());
				if (spw.equals("")) {
					Fr_M_Login.ShowMessage("일치하는 정보가 없습니다.");
				} else {
					StringBuffer strbuffer = new StringBuffer();
					strbuffer.append(spw);
					lblNewLabel.setText("일치하는 정보가 없습니다." + PwOutput(strbuffer) + "짜잔");
				}
			}
		});

		contentPane.add(btnNewButton);

	}

//	private String FindPwValue(String m_id, String m_name, String birth) {
//		String spw = "";
//		for (int i = 0; i < Member.MemeverVec.size(); i++) {
//			if (Member.MemeverVec.get(i).getName().equals(null))
//				continue;
//			if (Member.MemeverVec.get(i).getBirth().equals(null))
//				continue;
//			if (Member.MemeverVec.get(i).getSid().equals(null))
//				continue;
//			if (Member.MemeverVec.get(i).getSid().equals(sId) && Member.MemeverVec.get(i).getName().equals(sname)
//					&& Member.MemeverVec.get(i).getBirth().equals(sbirth)) {
//				spw = Member.MemeverVec.get(i).getSpw();
//			}
//		}
//		return spw;
//	}

	private StringBuffer PwOutput(StringBuffer sPw) // ID 
	{
		String str = "";
		for (int i = 0; i < (sPw.length() - 3); i++) {
			str += "*";
		}
		if (sPw.length() >= 3)
			return sPw.replace(3, sPw.length(), str);
		return sPw;
	}
}
