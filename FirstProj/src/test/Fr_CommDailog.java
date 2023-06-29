package test;
import java.awt.Component;
import java.awt.Font;
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

public class Fr_CommDailog extends JDialog implements ActionListener{
	
	private static final long serialVersionUID = -2937385415231613170L;
	
	// 편집 다이얼로그 생성
	JPanel commwe;
	JPanel commcen;
	JPanel commsout;
	
	String timeCombo[];
	JComboBox<String> combo;
	
	JLabel comm_id;
	JLabel commEname;
	JLabel commTime;
	JLabel commErecommender;
	JLabel commEprice;
	
	JTextField commTxtId;
	JTextField commTxtName;
	JTextField commTxtrecommender;
	JTextField commTxtPrice;
	
	JButton commInUpDe;
	JButton commmemin;
	JButton commEixt;
	
	DB_CommDAO dao;
	DB_ReJTable2DAO redao;
	Fr_AdminMain commAd;
	Fr_MemberMain commMem;
	String userID;
	// Memeber의 모임 신청을 위한 생성자
	public Fr_CommDailog(Fr_MemberMain memberMain, String index) {
		super(memberMain, "모임 신청");
		
		// 편집 다이얼로그 생성
		commwe = new JPanel(new GridLayout(5, 1));
		commcen = new JPanel(new GridLayout(5, 1));
		commsout = new JPanel();
		
		timeCombo = new String[] {"월요일 오전", "월요일 오후", "화요일 오전", "화요일 오후", "수요일 오전", "수요일 오후",
				 "목요일 오전", "목요일 오후", "금요일 오전", "금요일 오후", "토요일 오전", "토요일 오후", "일요일 오전", "일요일 오후"};
		combo = new JComboBox<String>(timeCombo);
		
		comm_id = new JLabel("No. :  ");
		commEname = new JLabel("모임명 :  ");
		commTime = new JLabel("시간대 :  ");
		commErecommender = new JLabel("지역 : ");
		commEprice = new JLabel("회비 :  ");
		
		commTxtId = new JTextField(10);
		commTxtName = new JTextField(10);
		commTxtrecommender = new JTextField(10);
		commTxtPrice = new JTextField(10);
		
		commEixt = new JButton("취 소");
		
		dao = new DB_CommDAO();
		redao = new DB_ReJTable2DAO();
		
		this.userID = memberMain.userID;
		this.commMem = memberMain;
		
		commmemin = new JButton();
		
		// 텍스트필드의 값을 가져와서 다이얼로그로 뿌려주기
		
		if (index.equals("모임 신청")) {
			commmemin = new JButton(index);
			int row = memberMain.mTable.getSelectedRow();
			commTxtId.setText(memberMain.mTable.getValueAt(row, 0).toString());
			commTxtName.setText(memberMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(memberMain.mTable.getValueAt(row, 2).toString());
			commTxtrecommender.setText(memberMain.mTable.getValueAt(row, 3).toString());
			commTxtPrice.setText(memberMain.mTable.getValueAt(row, 4).toString());
			
			commTxtId.setEditable(false);
			commTxtName.setEditable(false);
			combo.setEditable(false);
			combo.setEnabled(false);
			commTxtrecommender.setEditable(false);
			commTxtPrice.setEditable(false);
		}
		
		commwe.add(comm_id);
		comm_id.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commEname);
		commEname.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commTime);
		commTime.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commErecommender);
		commErecommender.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commEprice);
		commEprice.setFont(new Font("고딕체", Font.BOLD, 13));
		
		commcen.add(commTxtId);
		commcen.add(commTxtName);
		commcen.add(combo);
		commcen.add(commTxtrecommender);
		commcen.add(commTxtPrice);
		
		commsout.add(commmemin);
		commsout.add(commEixt);
		
		add(commwe, "West");
		add(commcen, "Center");
		add(commsout, "South");
		
		setSize(300, 250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		commmemin.addActionListener(this);
		commEixt.addActionListener(this);	
		
		System.out.println("멤버 프레임 생성자 끝남");
	}
	
	// Admin의 모든 버튼 작동을 위한 생성자
	public Fr_CommDailog(Fr_AdminMain adminMain, String index) {
		super(adminMain, "COMMUNITY");
		
		// 편집 다이얼로그 생성
		commwe = new JPanel(new GridLayout(5, 1));
		commcen = new JPanel(new GridLayout(5, 1));
		commsout = new JPanel();
		
		timeCombo = new String[] {"월요일 오전", "월요일 오후", "화요일 오전", "화요일 오후", "수요일 오전", "수요일 오후",
				 "목요일 오전", "목요일 오후", "금요일 오전", "금요일 오후", "토요일 오전", "토요일 오후", "일요일 오전", "일요일 오후"};
		combo = new JComboBox<String>(timeCombo);
		
		comm_id = new JLabel("닉네임 :  ");
		commEname = new JLabel("모임명 :  ");
		commTime = new JLabel("시간대 :  ");
		commErecommender = new JLabel("지역 : ");
		commEprice = new JLabel("회비 :  ");
		
		commTxtId = new JTextField(10);
		commTxtName = new JTextField(10);
		commTxtrecommender = new JTextField(10);
		commTxtPrice = new JTextField(10);
		
		commEixt = new JButton("취 소");
		
		dao = new DB_CommDAO();
		redao = new DB_ReJTable2DAO();
		
		this.commAd = adminMain;
		
		commmemin = new JButton("되돌리기");
		commInUpDe = new JButton("수정하기");
		
		// 값에 따라 바뀌는 다이얼로그 버튼
		// 텍스트 필드의 값을 가져온다
		if (index.equals("모임 등록")) {
			commInUpDe = new JButton(index);
			commTxtId.setEditable(false);
			
		} else if(index.equals("일정 수정")) {
			commInUpDe = new JButton(index);
			int row = adminMain.mTable.getSelectedRow();
			commTxtId.setText(adminMain.mTable.getValueAt(row, 0).toString());
			commTxtName.setText(adminMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(adminMain.mTable.getValueAt(row, 2).toString());
			commTxtrecommender.setText(adminMain.mTable.getValueAt(row, 3).toString());
			commTxtPrice.setText(adminMain.mTable.getValueAt(row, 4).toString());
		
			commTxtId.setEditable(false);
		
		} else if(index.equals("일정 삭제")){
			commInUpDe = new JButton(index);
			int row = adminMain.mTable.getSelectedRow();
			commTxtId.setText(adminMain.mTable.getValueAt(row, 0).toString());
			commTxtName.setText(adminMain.mTable.getValueAt(row, 1).toString());
			combo.setSelectedItem(adminMain.mTable.getValueAt(row, 2).toString());
			commTxtrecommender.setText(adminMain.mTable.getValueAt(row, 3).toString());
			commTxtPrice.setText(adminMain.mTable.getValueAt(row, 4).toString());
			
			commTxtId.setEditable(false);
			commTxtName.setEditable(false);
			combo.setEditable(false);
			combo.setEnabled(false);;
			commTxtrecommender.setEditable(false);
			commTxtPrice.setEditable(false);
		}
			
		commwe.add(comm_id);
		comm_id.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commEname);
		commEname.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commTime);
		commTime.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commErecommender);
		commErecommender.setFont(new Font("고딕체", Font.BOLD, 13));
		commwe.add(commEprice);
		commEprice.setFont(new Font("고딕체", Font.BOLD, 13));
		
		commcen.add(commTxtId);
		commcen.add(commTxtName);
		commcen.add(combo);
		commcen.add(commTxtrecommender);
		commcen.add(commTxtPrice);
		
		commsout.add(commInUpDe);
		commsout.add(commEixt);
		
		add(commwe, "West");
		add(commcen, "Center");
		add(commsout, "South");
		
		setSize(300, 250);
		setVisible(true);

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		commInUpDe.addActionListener(this);
		commmemin.addActionListener(this);
		commEixt.addActionListener(this);	
}

	@Override
	public void actionPerformed(ActionEvent e) {
		String btnComm = e.getActionCommand();
		
		// 텍스트필드로 등록 및 수정 삭제하는 액션폼
		if (btnComm.equals("모임 등록")) {
			DB_CommDTO dto = new DB_CommDTO();
			dto.setE_name(commTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_recommender(commTxtrecommender.getText());
			dto.setPrice(Integer.parseInt(commTxtPrice.getText()));
			
			//중복된 일정 데이터 확인후 넣기
			ArrayList<DB_CommDTO> list = dao.InsertCheck(commTxtName.getText(), combo.getSelectedItem().toString(), commTxtrecommender.getText());
			if (list.isEmpty()) {
				dao.commInsert(dto);
				messageBox(this, "신청이 완료 되었습니다.");
				dispose();
				for (int i = 0; i < commAd.commDt.getRowCount();) {
					commAd.commDt.removeRow(0);
				}
				ArrayList<DB_CommDTO> commDto = dao.commShowList();
				for(int i=0; i < commDto.size(); i++) {
					String[] temp = new String[5];
					temp[0] = commDto.get(i).getE_id();
					temp[1] = commDto.get(i).getE_name();
					temp[2] = commDto.get(i).getTime();
					temp[3] = commDto.get(i).getE_recommender();
					temp[4] = Integer.toString(commDto.get(i).getPrice());	
					commAd.commDt.addRow(temp);
				}
			} else {
				messageBox(this, "중복된 사항이 있습니다. \n 확인 하시길 바랍니다.");
			}
			// 다이얼로그로 수정
		} else if (btnComm.equals("모임 수정")) {
			DB_CommDTO dto = new DB_CommDTO();
			dto.setE_id(commTxtId.getText());
			dto.setE_name(commTxtName.getText());
			dto.setTime(combo.getSelectedItem().toString());
			dto.setE_recommender(commTxtrecommender.getText());
			dto.setPrice(Integer.parseInt(commTxtPrice.getText()));
			
			ArrayList<DB_CommDTO> list = dao.InsertCheck(commTxtName.getText(), combo.getSelectedItem().toString(), commTxtrecommender.getText());
			if (list.isEmpty()) {
				if (dao.commUpdate(dto) > 0) { //오류나면 commUpdate로 변경
					messageBox(this, "수정이 완료되었습니다.");
					redao.showListadmre(commAd.redt2);
					dispose();
					for (int i = 0; i < commAd.commDt.getRowCount();) {
						commAd.commDt.removeRow(0);
					}
					ArrayList<DB_CommDTO> commDto = dao.commShowList();
					for(int i=0; i < commDto.size(); i++) {
						String[] temp = new String[5];
						temp[0] = commDto.get(i).getE_id();
						temp[1] = commDto.get(i).getE_name();
						temp[2] = commDto.get(i).getTime();
						temp[3] = commDto.get(i).getE_recommender();
						temp[4] = Integer.toString(commDto.get(i).getPrice());	
						commAd.commDt.addRow(temp);
					}
				} else {
					messageBox(this, "수정이 올바르게 되지 않았습니다.");
				}
			} else {
				messageBox(this, "중복된 사항이 있습니다. \n 확인 하시길 바랍니다.");
			}
			
		// 다이얼로그로 삭제
		} else if (btnComm.equals("일정 삭제")){
			int t = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?\n삭제시 해당 모임과 관련된 예약 정보도 함께 삭제됩니다.", "회원 삭제 경고", JOptionPane.YES_NO_OPTION);
			if (t == 1)
				return;
				
			DB_CommDTO dto = new DB_CommDTO();
			dto.setE_id(commTxtId.getText());				
			// 일정삭제 할 때 삭제할 일정을 예약한 회원 예약상품도 같이 삭제하게 만듬
			redao.commreDelete(commTxtId.getText());
			redao.showListadmre(commAd.redt2);
			if (dao.commDelete(dto) > 0) {
				messageBox(this, "삭제 되었습니다.");
				dispose();
				for (int i = 0; i < commAd.commDt.getRowCount();) {
					commAd.commDt.removeRow(0);
				}
				ArrayList<DB_CommDTO> commDto = dao.commShowList();
				for(int i=0; i < commDto.size(); i++) {
					String[] temp = new String[5];
					temp[0] = commDto.get(i).getE_id();
					temp[1] = commDto.get(i).getE_name();
					temp[2] = commDto.get(i).getTime();
					temp[3] = commDto.get(i).getE_recommender();
					temp[4] = Integer.toString(commDto.get(i).getPrice());	
					commAd.commDt.addRow(temp);
				}
				System.out.println("모임 삭제 완료");
			} else {
				messageBox(this, "삭제 되지 않았습니다.");
			}
			
			// 일정 신청 할때 예약이 중복된 일정id값 체크 후 insert
			} else if (btnComm.equals("모임 신청")) {			
				ArrayList<DB_ReDTO> list = redao.commInsertCheck(userID, commTxtId.getText());
				System.out.println(userID);
				System.out.println(commTxtId.getText());
				System.out.println(list.size() + " : 중복 체크 값");
				if (list.isEmpty()) {
					messageBox(this, "일정 신청이 되었습니다.");
					dispose();
					redao.commReinsert(userID, commTxtId.getText());
					redao.showListmemre(commMem.redt2, userID);	// 일정 신청 후에 회원의 예약 목록 갱신
					commMem.getUserInfo();
				} else {
					messageBox(this, "해당 시간대에 예약된 일정이 있습니다.");
				}
				System.out.println("모임 신청 작동");
			} else if (btnComm.equals("취 소")) {
				dispose();
			}
		}
	
	
	public static void messageBox(Object obj, String msg) {
		JOptionPane.showMessageDialog((Component) obj, msg);
	}

}