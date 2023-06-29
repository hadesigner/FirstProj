package test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class Fr_AdminMain extends JFrame implements ActionListener {
	private static final long serialVersionUID = 4652601346626156680L;
	
	//=====================================화면 공통 부분 변수들========================================
	// 상단 메뉴
	MenuBar mb;
	Menu menu;
	MenuItem miLogout;
	
	// 탭 기능
	JTabbedPane t;	// 탭 패널 생성
    JPanel commPanel, memberPanel, rePanel;	// 각 탭에서 나타낼 화면 3개 생성
    
    // 로그인 유저 기록 기능
    String userID;	// 로그인한 유저명을 표시하기 위한 String 준비
    
    //=====================================상품 관련 기능에 필요한 변수들========================================
    // commAdmin 멤버변수들 
    String commName[];
	DefaultTableModel commDt;
	JTable mTable;
	JScrollPane mtScroll;
	
	JPanel mPanel;
	
	String serchCombo[];
	JComboBox<String> combo;
	JButton insertComm;
	JButton updateComm;
	JButton deleteComm;
	JTextField serchTf;
	JButton serch;
	JButton commall;
	
	DB_CommDAO commDao;
 	//=====================================회원 관련 기능에 필요한 변수들========================================
 	String memberData[][];
	String memberTitle[];
	DefaultTableModel memberModel;
	JTable memberTable;
	JScrollPane memberScrlPane;
 	
    // 회원 관리 조작부
    JPanel memberControlPanel;
    // 회원 관리 기능 + 검색 기능
    JPanel memberMetaInfoPanel, memberBtnsPanel;
	JButton BtnMemberDelete, BtnMemberUpdate, BtnMemberSelect;
	JComboBox<String> cbSearchFilter, cbOrderFilter;
	JTextField tfSearchText;
	JLabel lTotalMember;
	
	String memberOrderby;	// 회원 테이블 정렬을 위한 변수
	
	DB_MemberDAO memberDao;	// 회원 테이블에 접근하기 위한 DAO 준비
	Fr_SignForm signUpdate;	// 회원 정보 수정에 사용할 다이얼로그 준비
	
	//======================================예약 관련 기능에 필요한 변수들========================================
	String[] rid2 ;
	DefaultTableModel redt2;
	JTable rejt2;
	JScrollPane rejsp2;
	
	JPanel rep2;
	String[] recomboN2;

	JButton rejt2insert, rejt2update, rejt2delete;
	JLabel empty1;
	JComboBox<String> recombo2;
	JTextField rejtf2;
	JButton reserach2;
	
	DB_ReJTable2DAO reDao;

	// 관리자 화면 생성자
	public Fr_AdminMain(String userID) {
		this.userID = userID;
		memberDao = new DB_MemberDAO();
		reDao = new DB_ReJTable2DAO();
		
		memberOrderby = "joindate";
		
		layoutInit();
		showTables();
		reDao.showListadmre(redt2);
		eventInit();
		
		setTitle("모임 관리자");
		setVisible(true);
		
		JOptionPane.showMessageDialog(this, this.userID + "님 환영합니다.", "로그인 성공", JOptionPane.INFORMATION_MESSAGE);
	
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("[종료]");
				System.out.println("=================================================================================");
				DB_DBConn.close();
				System.exit(0);
			}
		});
	}
	
	// 레이아웃 정의
	public void layoutInit() {
		setLayout(new BorderLayout());
		setSize(1200, 800);
		setResizable(false);
		
		commLayoutInit(); 	// 상품 관리 탭에 대한 레이아웃 정의
		memberLayoutInit();	// 회원 관리 탭에 대한 레이아웃 정의
		reLayoutInit();		// 예약 관리 탭에 대한 레이아웃 정의
		
		t = new JTabbedPane();
		t.add("모임 관리", commPanel);
		t.add("회원 관리", memberPanel);
		t.add("예약 관리", rePanel);
		add(t);
		
		mb = new MenuBar();
		menu = new Menu("menu");
		miLogout = new MenuItem("logout");
		
		menu.add(miLogout);
		mb.add(menu);
		setMenuBar(mb);
	}
	
	// 상품 관리 레이아웃
	public void commLayoutInit() {
		// commProduct 만들기
		commDao = new DB_CommDAO();
		
		commName = new String[] {"No.", "모임 이름", "시간대", "지역", "회비"};
		commDt = new DefaultTableModel(commName, 0) {
			private static final long serialVersionUID = -1181334915458554664L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		mTable = new JTable(commDt);
		mtScroll = new JScrollPane(mTable);
		
		serchCombo = new String[] {"모임 이름", "시간대", "지역"};
		combo = new JComboBox<String>(serchCombo);
		insertComm = new JButton("모임 등록");
		updateComm = new JButton("모임 수정");
		deleteComm = new JButton("모임 삭제");
		serchTf = new JTextField(20);
		serch = new JButton("검색");
		commall = new JButton("전체 보기");
		
		mPanel = new JPanel();
		mPanel.add(insertComm);
		insertComm.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		mPanel.add(updateComm);
		updateComm.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		mPanel.add(deleteComm);
		deleteComm.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		mPanel.add(combo);
		combo.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		mPanel.add(serchTf);
		mPanel.add(serch);
		serch.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		mPanel.setBackground(Color.darkGray);
		mPanel.add(commall);
		commall.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		
		
		commPanel = new JPanel();
		commPanel.setLayout(new BorderLayout());
		commPanel.add(mtScroll, "Center");
		commPanel.add(mPanel, "South");
		
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ArrayList<DB_CommDTO> commDto = commDao.commShowList();
		for(int i=0; i < commDto.size(); i++) {
			String[] temp = new String[5];
			temp[0] = commDto.get(i).getE_id();
			temp[1] = commDto.get(i).getE_name();
			temp[2] = commDto.get(i).getTime();
			temp[3] = commDto.get(i).getE_recommender();
			temp[4] = Integer.toString(commDto.get(i).getPrice());
			
			commDt.addRow(temp);
		}
		if (commDt.getRowCount() > 0)
			mTable.setRowSelectionInterval(0, 0);			
	}
	
	// 회원 관리 레이아웃
	public void memberLayoutInit() {
		memberData = new String[0][9];
		memberTitle = new String[] { "회원 ID", "비밀번호", "회원 이름", "생년월일", "성별", "전화번호", "주소", "가입일자", "지역" };
		
		memberModel = new DefaultTableModel(memberData, memberTitle) {
			private static final long serialVersionUID = 6300785512235916424L;

			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		memberTable = new JTable(memberModel);
		memberTable.getTableHeader().setReorderingAllowed(false); // 칼럼 이동 방지
		memberScrlPane = new JScrollPane(memberTable);
		
		BtnMemberDelete = new JButton("삭제");
		BtnMemberUpdate = new JButton("수정");
		cbSearchFilter = new JComboBox<String>(new String[] {"회원 ID", "회원 이름", "주소", "지역"});
		cbOrderFilter = new JComboBox<String>(new String[] {"회원 ID", "회원 이름", "생년월일", "성별", "주소", "가입일자", "지역"});
		cbOrderFilter.setSelectedIndex(5);
		tfSearchText = new JTextField(null, 20);
		BtnMemberSelect = new JButton("검색");
		
		lTotalMember = new JLabel("전체 회원수  :  " + memberDao.selectTotalMemberCnt());
		
		memberMetaInfoPanel = new JPanel();
		memberMetaInfoPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		memberMetaInfoPanel.setBackground(Color.gray);
		memberMetaInfoPanel.add(lTotalMember);
		
		memberBtnsPanel = new JPanel();
		memberBtnsPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
		memberBtnsPanel.setBackground(Color.gray);
		memberBtnsPanel.add(new JLabel("정렬 기준", JLabel.RIGHT));
		memberBtnsPanel.add(cbOrderFilter);
		memberBtnsPanel.add(BtnMemberDelete);
		memberBtnsPanel.add(BtnMemberUpdate);
		memberBtnsPanel.add(cbSearchFilter);
		memberBtnsPanel.add(tfSearchText);
		memberBtnsPanel.add(BtnMemberSelect);
		
		
		memberControlPanel = new JPanel();
		memberControlPanel.setLayout(new BorderLayout());
		memberControlPanel.setBackground(Color.gray);
		memberControlPanel.add("West", memberMetaInfoPanel);
		memberControlPanel.add("Center", memberBtnsPanel);
		
		memberPanel = new JPanel();
		memberPanel.setLayout(new BorderLayout());
		memberPanel.add("Center", memberScrlPane);
		memberPanel.add("South", memberControlPanel);
	}
	
	// 예약 관리 레이아웃
	public void reLayoutInit() {
		//예약 관리
		rid2 = new String[] { "예약 번호", "회원 ID", "No.","회원명", "모임명", "시간", "지역", "금액"};
		redt2 = new DefaultTableModel(rid2, 0);
		rejt2 = new JTable(redt2);
		rejsp2 = new JScrollPane(rejt2);
		
		rep2 = new JPanel();
		recomboN2 = new String[] { "  ALL  ", "  예약 번호  ", "  회원 ID  ", "  No.  " };

		rejt2insert = new JButton("추가");
		rejt2update = new JButton("수정");
		rejt2delete = new JButton("취소");
		empty1 = new JLabel("                         ");
		recombo2 = new JComboBox<String>(recomboN2);
		rejtf2 = new JTextField(5);
		reserach2 = new JButton("검색");
		
		rep2.setBackground(Color.gray);
		rep2.add(rejt2insert);
		rep2.add(rejt2update);
		rep2.add(rejt2delete);
		rep2.add(empty1);
		rep2.setLayout( new FlowLayout( FlowLayout.RIGHT ) );
		rep2.add(recombo2);
		rep2.add(rejtf2);
		rep2.add(reserach2);
		
		rePanel = new JPanel();
		rePanel.setLayout(new BorderLayout());
		rePanel.add(rejsp2);
		rePanel.add("South", rep2);
	}
	
	// 이벤트 리스너 등록
	public void eventInit() {
		// 회원 관리 이벤트 리스너 등록
		BtnMemberDelete.addActionListener(this);
		BtnMemberUpdate.addActionListener(this);
		BtnMemberSelect.addActionListener(this);
		miLogout.addActionListener(this);
		cbOrderFilter.addActionListener(this);
		
		// 상품 관리 이벤트 리스너 등록
		insertComm.addActionListener(this);
		updateComm.addActionListener(this);
		deleteComm.addActionListener(this);
		serch.addActionListener(this);
		commall.addActionListener(this);
		combo.addActionListener(this);
		
		// 예약 관리 이벤트 리스너 등록
		rejt2insert.addActionListener(this);
		rejt2update.addActionListener(this);
		rejt2delete.addActionListener(this);
		reserach2.addActionListener(this);
	}
	
	// 이벤트 정의
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		
		//=====================================회원 관련 이벤트 처리========================================
		if (ob == BtnMemberDelete) {
			deleteMember();	// 회원 삭제 메소드 실행
		} else if (ob == BtnMemberUpdate){
			updateMember();	// 회원 수정 메소드 실행
		} else if (ob == BtnMemberSelect) {
			searchMember(); // 회원 검색 메소드 실행
		} else if (ob == miLogout) {
			 int result = JOptionPane.showConfirmDialog(null, "로그아웃 하시겠습니까?", "로그아웃", JOptionPane.OK_CANCEL_OPTION);
			 if(result == 0) {
				 System.out.println("[로그아웃 성공]");
				 System.out.println("=================================================================================");
				 dispose();
				 new Fr_M_Login();
				 JOptionPane.showMessageDialog(this, "로그아웃 되었습니다.");
			 }
		} else if (ob == cbOrderFilter) {
			switch (cbOrderFilter.getSelectedItem().toString()) {
			case "회원 ID":
				memberOrderby = "m_id";
				break;
			case "회원 이름":
				memberOrderby = "m_name";
				break;
			case "생년월일":
				memberOrderby = "birth";
				break;
			case "성별":
				memberOrderby = "gender";
				break;
			case "주소":
				memberOrderby = "addr";
				break;
			case "가입일자":
				memberOrderby = "joindate";
				break;
			case "지역":
				memberOrderby = "m_recommender";
				break;
			}
			showTables();
		}
		
		//=====================================모임 관련 이벤트 처리========================================
		// 모임 관리
		if (e.getSource() == insertComm) {
			new Fr_CommDailog(this, "모임 등록");
		} else if (e.getSource() == updateComm) {
			if (mTable.getSelectedRow() > -1) {
				new Fr_CommDailog(this, "모임 수정");
			}
		} else if (e.getSource() == deleteComm) {
			if (mTable.getSelectedRow() > -1) {
				new Fr_CommDailog(this, "모임 삭제");
			}
		} else if (e.getSource() == commall) {
			for (int i = 0; i < commDt.getRowCount();) {
				commDt.removeRow(0);
			}
			ArrayList<DB_CommDTO> commDto = commDao.commShowList();
			for(int i=0; i < commDto.size(); i++) {
				String[] temp = new String[5];
				temp[0] = commDto.get(i).getE_id();
				temp[1] = commDto.get(i).getE_name();
				temp[2] = commDto.get(i).getTime();
				temp[3] = commDto.get(i).getE_recommender();
				temp[4] = Integer.toString(commDto.get(i).getPrice());
				
				commDt.addRow(temp);
			}
		} else if (e.getSource() == serch) {
			String comboName = null;
			switch (combo.getSelectedItem().toString()) {
			case "모임 이름":
				comboName = "e_name";
				break;
			case "시간대":
				comboName = "time";
				break;
			case "지역":
				comboName = "e_recommender";
				break;
			}
			String comboTxt = serchTf.getText();
			System.out.println("검색명 출력 : " + comboName);
			
			if (serchTf.getText().trim().equals("")) {
				Fr_CommDailog.messageBox(this, "검색어를 입력해 주세요~!");
				serchTf.requestFocus();
			} else {
				for (int i = 0; i < commDt.getRowCount();) {
					commDt.removeRow(0);
				}
				
				ArrayList<DB_CommDTO> list = commDao.commSearch(comboName, comboTxt);
				
				if (list.size() > 0) {
					ArrayList<DB_CommDTO> commDto = commDao.commSearch(comboName, comboTxt);
					
					for(int i=0; i < commDto.size(); i++) {
						String[] temp = new String[5];
						temp[0] = commDto.get(i).getE_id();
						temp[1] = commDto.get(i).getE_name();
						temp[2] = commDto.get(i).getTime();
						temp[3] = commDto.get(i).getE_recommender();
						temp[4] = Integer.toString(commDto.get(i).getPrice());
						
						commDt.addRow(temp);
					}
				} else {
					Fr_CommDailog.messageBox(this, "검색된 결과가 없습니다.  다시 검색해주세요.");
				}				
			} 
		}
		// 콤보 셀렉트 액션
		if (e.getSource() == combo) {
			String comboSelete = null;
			switch (combo.getSelectedItem().toString()) {
			case "모임 이름":
				comboSelete = "e_name";
				break;
			case "시간대":
				comboSelete = "time";
				break;
			case "지역":
				comboSelete = "e_recommender";
				break;
			}
			
			for (int t = 0; t < commDt.getRowCount();) {
				commDt.removeRow(0);
			}
			
			ArrayList<DB_CommDTO> list = commDao.comboSeletedShow(comboSelete);
			
			for(int i=0; i < list.size(); i++) {
				String[] temp = new String[5];
				temp[0] = list.get(i).getE_id();
				temp[1] = list.get(i).getE_name();
				temp[2] = list.get(i).getTime();
				temp[3] = list.get(i).getE_recommender();
				temp[4] = Integer.toString(list.get(i).getPrice());
				
				commDt.addRow(temp);
			}
		}
		
		//=====================================예약 관련 이벤트 처리========================================
		// 예약 관리
		if (e.getSource() == rejt2insert) {// 추가 메뉴아이템 클릭
			new Fr_ReJTable2DialogGUI(this, "추가");
		} else if (e.getSource() == rejt2update) {// 수정 메뉴아이템 클릭
			if(rejt2.getSelectedRow() > -1) {
				new Fr_ReJTable2DialogGUI(this, "수정");
			}
		} else if (e.getSource() == rejt2delete) {// 취소 메뉴아이템 클릭
			if (rejt2.getSelectedRow() > -1) {
				int row = rejt2.getSelectedRow();
				System.out.println("선택행 : " + row);
				Object obj = rejt2.getValueAt(row, 0);// 행 열에 해당하는 value
				System.out.println("값 : " + obj);
				int result = JOptionPane.showConfirmDialog(null, "정말로 취소 하시겠습니까?", "취소하기",
						JOptionPane.OK_CANCEL_OPTION);
				if (result == 0) {
					if (reDao.deletere(obj.toString()) > 0) {
						JOptionPane.showMessageDialog(this, "취소 되었습니다.");

						reDao.showListadmre(redt2);
						if (redt2.getRowCount() > 0)
							rejt2.setRowSelectionInterval(0, 0);
					} else {
						JOptionPane.showMessageDialog(this, "취소 실패되었습니다.");
					}
				}
			}
		} else if (e.getSource() == reserach2) {// 검색 버튼 클릭
			// JComboBox에 선택된 value 가져오기
			String fieldName = recombo2.getSelectedItem().toString();
			System.out.println("필드명 " + fieldName);
			switch (recombo2.getSelectedItem().toString()) {
			case "All":
				fieldName = "All";
			case "예약 번호":
				fieldName = "r_id";
				break;
			case "회원 ID":
				fieldName = "rm_id";
				break;
			case "닉네임":
				fieldName = "re_id";
				break;
			}

			if (fieldName.trim().equals("ALL")) {// 전체검색
				
				try {
					reDao.showListadmre(redt2);
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
				if (redt2.getRowCount() > 0)
					rejt2.setRowSelectionInterval(0, 0);
					
			} else {
				if (rejtf2.getText().trim().equals("")) {
					Fr_ReJTable2DialogGUI.messageBox(this, "검색단어를 입력해주세요!");
					rejtf2.requestFocus();
				} else {// 검색어를 입력했을경우
					reDao.searchadmre(redt2, fieldName, rejtf2.getText());
					if (redt2.getRowCount() > 0)
						rejt2.setRowSelectionInterval(0, 0);
				}
				rejtf2.setText("");
				rejtf2.requestFocus();
			}
		}
	}
	
	// 회원 테이블 갱신 메소드
	public void showTables() {
		memberModel.setRowCount(0); // 새로고친 후에 보여주기
		
		ArrayList<DB_MemberDTO> dtoMembers = new ArrayList<DB_MemberDTO>();
		dtoMembers = memberDao.selectAdminShowMember(memberOrderby);
		
		String[] temp = new String[9];
		
		for(int i = 0; i<dtoMembers.size(); i++) {
			temp[0] = dtoMembers.get(i).getM_id();
			temp[1] = dtoMembers.get(i).getPwd();
			temp[2] = dtoMembers.get(i).getM_name();
			temp[3] = dtoMembers.get(i).getBirth();
			temp[4] = dtoMembers.get(i).getGender();
			temp[5] = dtoMembers.get(i).getPhone();
			temp[6] = dtoMembers.get(i).getAddr();
			temp[7] = dtoMembers.get(i).getJoindate();
			temp[8] = dtoMembers.get(i).getM_recommender();
			
			memberModel.addRow(temp);
		}
	}

	// 회원 삭제 메소드
	public void deleteMember() {
		String memberDelete = JOptionPane.showInputDialog(this, "삭제할 회원 ID를 입력하세요.", "회원 삭제", JOptionPane.INFORMATION_MESSAGE);

		if (memberDelete == null)
			return;
		if (userID.equals(memberDelete)) { // 자기 자신은 삭제할 수 없게 한다
			JOptionPane.showMessageDialog(this, "관리자는 삭제하실 수 없습니다.", "회원 삭제 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		ArrayList<DB_MemberDTO> selectUser = memberDao.selectIdCheck(memberDelete);

		if (selectUser.isEmpty()) {
			JOptionPane.showMessageDialog(this, "입력하신 ID는 존재하지 않습니다.", "회원 삭제 오류", JOptionPane.ERROR_MESSAGE);
			return;
		} else {
			int t = JOptionPane.showConfirmDialog(this, "정말로 삭제하시겠습니까?\n삭제시 해당 회원의 예약 정보도 함께 삭제됩니다.", "회원 삭제 경고", JOptionPane.YES_NO_OPTION);
			if (t == 1 || memberDelete.equals(""))
				return;
			try {
				reDao.reDeleteByRm_id(memberDelete);	// 회원을 삭제하기 위해서 먼저 예약 정보를 삭제한다.
				memberDao.deleteMember(memberDelete);
				lTotalMember.setText("전체 회원수 : " + memberDao.selectTotalMemberCnt());	// 회원 삭제 후 회원수를 갱신한다.
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "정상적으로 삭제되지 않았습니다. (오류 발생)", "회원 삭제 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
			showTables();
			reDao.showListadmre(redt2);
			JOptionPane.showMessageDialog(this, "삭제되었습니다.", "회원 삭제 성공", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	// 회원 수정 메소드
	public void updateMember() {
		String selectId = "";
		int row = memberTable.getSelectedRow();
		if (row < 0) {
			JOptionPane.showMessageDialog(this, "수정할 데이터를 선택하세요.", "회원 수정", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		selectId = (String) (memberModel.getValueAt(row, 0));
		
		if (userID.equals(selectId)) { // 자기 자신은 삭제할 수 없게 한다
			JOptionPane.showMessageDialog(this, "관리자는 정보를 수정할 수 없습니다.", "회원 수정 경고", JOptionPane.WARNING_MESSAGE);
			return;
		}
		
		ArrayList<DB_MemberDTO> selectUser = memberDao.selectIdCheck(selectId);
		
		if(selectUser.isEmpty()) {
			JOptionPane.showMessageDialog(this, "데이터가 존재하지 않습니다.", "회원 수정 오류", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		signUpdate = new Fr_SignForm(selectUser.get(0), this);
	}
	
	// 회원 검색 메소드
	public void searchMember() {
		String tag = null;
		switch(cbSearchFilter.getSelectedItem().toString()) {
		case "회원 ID":
			tag = "m_id";
			break;
		case "회원 이름":
			tag = "m_name";
			break;
		case "지역":
			tag = "m_recommender";
			break;
		case "주소":
			tag = "addr";
			break;
		}
		String inputText = tfSearchText.getText().trim();
		
		System.out.println("검색 : " + tag + " - " + inputText);
		
		memberModel.setRowCount(0); // 새로고친 후에 보여주기
		
		ArrayList<DB_MemberDTO> dtoMembers = new ArrayList<DB_MemberDTO>();
		
		dtoMembers = memberDao.selectAdminShowMemberSearch(tag, inputText, memberOrderby);
		String[] temp = new String[9];
		
		for(int i = 0; i<dtoMembers.size(); i++) {
			temp[0] = dtoMembers.get(i).getM_id();
			temp[1] = dtoMembers.get(i).getPwd();
			temp[2] = dtoMembers.get(i).getM_name();
			temp[3] = dtoMembers.get(i).getBirth();
			temp[4] = dtoMembers.get(i).getGender();
			temp[5] = dtoMembers.get(i).getPhone();
			temp[6] = dtoMembers.get(i).getAddr();
			temp[7] = dtoMembers.get(i).getJoindate();
			temp[8] = dtoMembers.get(i).getM_recommender();
			
			memberModel.addRow(temp);
		}
	}
}