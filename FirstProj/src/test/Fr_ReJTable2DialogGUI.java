package test;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Fr_ReJTable2DialogGUI extends JDialog implements ActionListener {
	private static final long serialVersionUID = -8926950592764879152L;
	
	JPanel pw;
	JPanel pc;
	JPanel ps;

	JLabel lable_R_id;
	JLabel lable_Rm_id;
	JLabel lable_Re_id;

	JTextField r_id;
	JTextField rm_id;
	JTextField re_id;
	
	JComboBox<String> cbE_id;
	JComboBox<String> cbM_id;

	JButton confirm;
	JButton reset;

	Fr_MemberMain rejt2ob;
	Fr_AdminMain rejt2ob2;

	JPanel ridCkP;
	JButton ridCkBtn;

	DB_ReJTable2DAO redao2;

	public Fr_ReJTable2DialogGUI(Fr_MemberMain rejt2ob, String index) {
		super(rejt2ob, "예약추가/수정");
		
		pw = new JPanel(new GridLayout(4, 1));
		pc = new JPanel(new GridLayout(4, 1));
		ps = new JPanel();

		lable_R_id = new JLabel("예약 번호");
		lable_Rm_id = new JLabel("회원 ID");
		lable_Re_id = new JLabel("닉네임");

		r_id = new JTextField();
		rm_id = new JTextField();
		re_id = new JTextField();
		
		cbE_id = new JComboBox<String>();
		cbM_id = new JComboBox<String>();

		reset = new JButton("취소");

		ridCkP = new JPanel(new BorderLayout());
		ridCkBtn = new JButton("R_IDCheck");

		redao2 = new DB_ReJTable2DAO();
		
		this.rejt2ob = rejt2ob;
		if (index.equals("추가")) {
			confirm = new JButton(index);
			r_id.setEnabled(false);
		} else {
			confirm = new JButton("수정");

			// text박스에 선택된 레코드의 정보 넣기
			int row = rejt2ob.rejt2.getSelectedRow();// 선택된 행
			r_id.setText(rejt2ob.rejt2.getValueAt(row, 0).toString());
			rm_id.setText(rejt2ob.rejt2.getValueAt(row, 1).toString());
			re_id.setText(rejt2ob.rejt2.getValueAt(row, 2).toString());

			// r_id text박스 비활성
			r_id.setEditable(false);

			// R_IDCheck버튼 비활성화
			ridCkBtn.setEnabled(false);
		}

		pw.add(lable_R_id);
		pw.add(lable_Rm_id);
		pw.add(lable_Re_id);

		ridCkP.add(r_id, "Center");
		ridCkP.add(ridCkBtn, "East");

		pc.add(ridCkP);
		pc.add(rm_id);
		pc.add(re_id);

		ps.add(confirm);
		ps.add(reset);

		add(pw, "West");
		add(pc, "Center");
		add(ps, "South");

		setSize(300, 250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// 이벤트등록
		confirm.addActionListener(this); // 추가,수정버튼 다이어로그 이벤트등록
		reset.addActionListener(this); // 취소 이벤트등록
		ridCkBtn.addActionListener(this);// r_id중복체크 이벤트 등록

	}
	
	public Fr_ReJTable2DialogGUI(Fr_AdminMain rejt2ob, String index) {
		super(rejt2ob, "예약추가/수정");
		
		pw = new JPanel(new GridLayout(4, 1));
		pc = new JPanel(new GridLayout(4, 1));
		ps = new JPanel();

		lable_R_id = new JLabel("예약 번호");
		lable_Rm_id = new JLabel("회원 ID");
		lable_Re_id = new JLabel("닉네임");

		r_id = new JTextField();
		rm_id = new JTextField();
		re_id = new JTextField();
		
		cbE_id = new JComboBox<String>();
		cbM_id = new JComboBox<String>();

		reset = new JButton("취소");

		ridCkP = new JPanel(new BorderLayout());
		ridCkBtn = new JButton("R_IDCheck");

		redao2 = new DB_ReJTable2DAO();
		
		this.rejt2ob2 = rejt2ob;
		
		DB_MemberDAO dao = new DB_MemberDAO();
		ArrayList<DB_MemberDTO> Dto = dao.memberSelectM_id();
		for(int i = 0; i < Dto.size(); i ++) {
			if(!Dto.get(i).getM_id().equals("admin")) {
				cbM_id.addItem(Dto.get(i).getM_id());
			}			
		}
		DB_CommDAO commdao = new DB_CommDAO();
		ArrayList<DB_CommDTO> commDto = commdao.commSelectE_id();
		for(int i = 0; i < commDto.size(); i ++) {
			cbE_id.addItem(commDto.get(i).getE_recommender());
		}
		
		if (index.equals("추가")) {
			confirm = new JButton(index);
			r_id.setEditable(false);
		} else {
			confirm = new JButton("수정");

			// text박스에 선택된 레코드의 정보 넣기
			int row = rejt2ob.rejt2.getSelectedRow();// 선택된 행
			r_id.setText(rejt2ob.rejt2.getValueAt(row, 0).toString());
//			rm_id.setText(rejt2ob.rejt2.getValueAt(row, 1).toString());
//			re_id.setText(rejt2ob.rejt2.getValueAt(row, 2).toString());

			// r_id text박스 비활성
			r_id.setEditable(false);
//			rm_id.setEditable(false);
			
			cbM_id.setSelectedItem(rejt2ob.rejt2.getValueAt(row, 1).toString());
			cbE_id.setSelectedItem(rejt2ob.rejt2.getValueAt(row, 2).toString());

		}

		pw.add(lable_R_id);
		pw.add(lable_Rm_id);
		pw.add(lable_Re_id);

		pc.add(r_id);
		pc.add(cbM_id);
//		pc.add(rm_id);
		pc.add(cbE_id);
//		pc.add(re_id);

		ps.add(confirm);
		ps.add(reset);

		add(pw, "West");
		add(pc, "Center");
		add(ps, "South");

		setSize(300, 250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

		// 이벤트등록
		confirm.addActionListener(this); // 추가,수정버튼 다이어로그 이벤트등록
		reset.addActionListener(this); // 취소 이벤트등록
		ridCkBtn.addActionListener(this);// r_id중복체크 이벤트 등록

	}

	// 추가/수정/삭제 기능에 대한 부분
	@Override
	public void actionPerformed(ActionEvent e) {
		String btnLabel = e.getActionCommand();// 이벤트주체 대한 Label 가져오기

		if(rejt2ob != null) {
			if (btnLabel.equals("추가")) {
//				if (redao2.reListInsert(this) > 0) {// 예약성공
				ArrayList<DB_ReDTO> list = redao2.commInsertCheck(cbM_id.getSelectedItem().toString(), cbE_id.getSelectedItem().toString());
				System.out.println(list.size());
				if(list.isEmpty()) {
					if (redao2.insertre(this) > 0) {
						messageBox(this, "정상 예약 되었습니다");
						dispose();// JDialog 창닫기

//						redao2.reSelectAll(rejt2ob.redt2);// 모든 레코드 가져와서 DefaultTableModel에 올리기
						redao2.showListadmre(rejt2ob.redt2);// 모든 레코드 가져와서 DefaultTableModel에 올리기

						if (rejt2ob.redt2.getRowCount() > 0)
							rejt2ob.rejt2.setRowSelectionInterval(0, 0);// 첫번째 행 선택

					} else {
						messageBox(this, "모두 입력 부탁드립니다");
					}
				} else {
					messageBox(this, "중복된 예약입니다.");
				}
			} else if (btnLabel.equals("수정")) {
				ArrayList<DB_ReDTO> list = redao2.commInsertCheck(cbM_id.getSelectedItem().toString(), cbE_id.getSelectedItem().toString());
				if(list.isEmpty()) {
					if (redao2.updatere(this) > 0) {
//						if (redao2.reUpdate(this) > 0) {
							messageBox(this, "수정되었습니다.");
							dispose();
//							redao2.reSelectAll(rejt2ob.redt2);
							redao2.showListadmre(rejt2ob.redt2);
							if (rejt2ob.redt2.getRowCount() > 0)
								rejt2ob.rejt2.setRowSelectionInterval(0, 0);
						} else {
							messageBox(this, "수정되지 않았습니다.");
						}
				} else {
					messageBox(this, "중복된 예약입니다.");
				}
				
			} else if (btnLabel.equals("취소")) {
				dispose();
			} 
//			else if (btnLabel.equals("R_IDCheck")) {
//				// r_id텍스트 박스에 값 없으면 메세지 출력 있으면 DB연동한다.
//				if (r_id.getText().trim().equals("")) {
//					messageBox(this, "예약번호를 입력해주세요.");
//					r_id.requestFocus();// 포커스이동
//				} else {
//					if (redao2.rIDCheck(r_id.getText())) { // 중복아니다.(사용가능)
//						messageBox(this, r_id.getText() + "번은 추가 가능합니다.");
//					} else {
//						messageBox(this, r_id.getText() + "번은 중복 예약번호 입니다.");
//						r_id.setText("");// text박스지우기
//						r_id.requestFocus();// 커서놓기
//					}
//				}
//			}
		} else {
			if (btnLabel.equals("추가")) {
				if(cbM_id.getSelectedItem() == null || cbE_id.getSelectedItem() == null) {
					messageBox(this, "기재사항을 확인해주세요.");
				} else {
					ArrayList<DB_ReDTO> list = redao2.commInsertCheck(cbM_id.getSelectedItem().toString(), cbE_id.getSelectedItem().toString());
					System.out.println(list.size());
					if(list.isEmpty()) {
						if (redao2.insertre(this) > 0) {
//							if (redao2.reListInsert(this) > 0) {// 예약성공
								messageBox(this, "정상 예약 되었습니다");
								dispose();// JDialog 창닫기

//								redao2.reSelectAll(rejt2ob2.redt2);// 모든 레코드 가져와서 DefaultTableModel에 올리기
								redao2.showListadmre(rejt2ob2.redt2);// 모든 레코드 가져와서 DefaultTableModel에 올리기

								if (rejt2ob2.redt2.getRowCount() > 0)
									rejt2ob2.rejt2.setRowSelectionInterval(0, 0);// 첫번째 행 선택

							} else {
								messageBox(this, "모두 입력 부탁드립니다");
							}
					} else {
						messageBox(this, "중복된 예약입니다.");
					}
				}
				
			} else if (btnLabel.equals("수정")) {
				if(cbM_id.getSelectedItem() == null || cbE_id.getSelectedItem() == null) {
					messageBox(this, "기재사항을 확인해주세요.");
				} else {
					ArrayList<DB_ReDTO> list = redao2.commInsertCheck(cbM_id.getSelectedItem().toString(), cbE_id.getSelectedItem().toString());
					System.out.println(list.size());
					if(list.isEmpty()) {
						if (redao2.updatere(this) > 0) {
//							if (redao2.reUpdate(this) > 0) {
								messageBox(this, "수정되었습니다.");
								dispose();
								redao2.showListadmre(rejt2ob2.redt2);
//								redao2.reSelectAll(rejt2ob.redt2);

							} else {
								messageBox(this, "수정되지 않았습니다.");
							}
					} else {
						messageBox(this, "중복된 예약입니다.");
					}
				}
			} else if (btnLabel.equals("취소")) {
				dispose();
			}
		}
		
		
	}

	// 메시지박스 띄워주는 메소드 작성
	public static void messageBox(Object obj, String message) {
		JOptionPane.showMessageDialog((Component) obj, message);
	}
}