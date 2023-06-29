package test;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Fr_M_Login extends JFrame implements ActionListener {
	private static final long serialVersionUID = -1445214176327987639L;
	
//	public static FindId findid = new FindId(); // 
//	public static FindPw findpw = new FindPw(); // 
	
	BufferedImage img = null;
	JPanel loginInfoPanel, inputPanel, btnsPanel, contentPane;
	JTextField tfId;
	JPasswordField pfPwd;
	JButton BtnSignUp, BtnLogin, BtnFindId,BtnFindPw;
	
	DB_MemberDAO memberDao;
	Fr_SignForm signUp;

	Fr_FindId findId;
	Fr_FindPw findPw;
	
	// 실행 메소드(로그인 화면)
	
	public static void main(String[] args) {
		new Fr_M_Login();
	}
	
	public static void ShowMessage(String str) {
		JOptionPane.showMessageDialog(null, str);
	}
	
	
	// 로그인 화면 생성자
	public Fr_M_Login() {
		memberDao = new DB_MemberDAO();
		
		layoutInit();
		eventInit();
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				DB_DBConn.close();
				System.exit(0);
			}
		});
	}
	
	// 레이아웃 정의
	public void layoutInit() {
		
		setTitle("TO:게더 로그인");
		setSize(1600, 900);
		setResizable(false);
		
		
		//레이아웃 설정
        getContentPane().setLayout(null);
        JLayeredPane layeredPane = new JLayeredPane();
        MyPanel panel = new MyPanel();
        layeredPane.setBounds(0, 0, 1600, 900);
        layeredPane.setLayout(null);
        layeredPane.add(panel);
		
        // 패널1
        // 이미지 받아오기
        try {
        	img = ImageIO.read(new File("C:\\Users\\gk019\\eclipse-workspace\\FirstProj\\src\\imgs\\login.png"));
        } catch (IOException e) {
            System.out.println("이미지 불러오기 실패");
            System.exit(0);
        }
		
        panel.setBounds(0, 0, 1600, 900);     
        

		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//버튼 제어//
		
		//로그인//
		BtnLogin = new JButton("로그인");
		BtnLogin = new JButton(new ImageIcon("C:\\Users\\gk019\\eclipse-workspace\\FirstProj\\src\\imgs\\login_btn.png"));
		BtnLogin.setBounds(970, 380, 150, 196);
		BtnLogin.setBorderPainted(false);
		BtnLogin.setFocusPainted(false);
		BtnLogin.setContentAreaFilled(true);
		contentPane.add(BtnLogin);
	
		//회원가입//
		BtnSignUp = new JButton("회원 가입");
		BtnSignUp = new JButton(new ImageIcon("C:\\Users\\gk019\\eclipse-workspace\\FirstProj\\src\\imgs\\join_btn.png"));
		BtnSignUp.setBounds(430, 689, 226, 76);
		BtnSignUp.setBorderPainted(false);
		BtnSignUp.setFocusPainted(false);
		BtnSignUp.setContentAreaFilled(false);
		contentPane.add(BtnSignUp);
		
		//아이디찾기//
		BtnFindId = new JButton("아이디찾기");
		BtnFindId = new JButton(new ImageIcon("C:\\Users\\gk019\\eclipse-workspace\\FirstProj\\src\\imgs\\fid_btn.png"));
		BtnFindId.setBounds(680, 689, 226, 76);
		BtnFindId.setBorderPainted(false);
		BtnFindId.setFocusPainted(false);
		BtnFindId.setContentAreaFilled(false);
		contentPane.add(BtnFindId);
		
		//비밀번호찾기//
		BtnFindPw = new JButton("비밀번호찾기");
		BtnFindPw = new JButton(new ImageIcon("C:\\Users\\gk019\\eclipse-workspace\\FirstProj\\src\\imgs\\fpw_btn.png"));
		BtnFindPw.setBounds(930, 689, 226, 76);
		BtnFindPw.setBorderPainted(false);
		BtnFindPw.setFocusPainted(false);
		BtnFindPw.setContentAreaFilled(false);
		contentPane.add(BtnFindPw);
		
		
		//아이디 필드
		tfId = new JTextField();
		tfId.setBounds(630, 396, 280, 30);
		tfId.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		tfId.setForeground(new Color(255, 122, 0));
		
		contentPane.add(tfId);
		tfId.setColumns(10);
		tfId.setOpaque(false);
		
		tfId.setBorder(javax.swing.BorderFactory.createEmptyBorder());
				
		//비밀번호 필드
		pfPwd = new JPasswordField();
//		pfPwd.setToolTipText("Password");
		pfPwd.setBounds(630, 526, 280, 30);
		pfPwd.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		pfPwd.setForeground(new Color(255, 122, 0));
		pfPwd.setEchoChar('*');
		contentPane.add(pfPwd);
		pfPwd.setColumns(10);
		pfPwd.setOpaque(false);
		
		pfPwd.setBorder(javax.swing.BorderFactory.createEmptyBorder());
		getContentPane().add(layeredPane);
		setVisible(true);
				
	}

	// 이벤트 리스너 등록
	public void eventInit() {
		BtnSignUp.addActionListener(this);
		BtnLogin.addActionListener(this);
		BtnFindId.addActionListener(this);
		BtnFindPw.addActionListener(this);
	}

	// 이벤트 정의
	@Override
	public void actionPerformed(ActionEvent e) {
		Object ob = e.getSource();
		if (ob == BtnSignUp) {
			System.out.println("[회원 가입 버튼 클릭]");
			signUp = new Fr_SignForm();
		} else if (ob == BtnLogin) {
			System.out.println("[로그인 버튼 클릭]");
			loginCheck();
		} else if(ob == BtnFindId) {
			System.out.println("[아이디찾기 버튼 클릭]");
			findId = new Fr_FindId();
		} else if(ob == BtnFindPw) {
			System.out.println("[비밀번호찾기 버튼 클릭]");
			findPw = new Fr_FindPw();
		}
	}
	
	
	
	// 로그인 검사/처리
	public void loginCheck() {
		String id = tfId.getText().trim();
		String pwd = new String(pfPwd.getPassword()).trim();
		ArrayList<DB_MemberDTO> loginUser = new ArrayList<DB_MemberDTO>();
		
		// ID 입력 확인
		if (id.length() == 0) {
			// ID 미입력시 경고
			JOptionPane.showMessageDialog(this, "ID를 입력하세요.", "로그인 알림", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.setText(null);
			tfId.requestFocus(); // Focus 맞춰주기.
			return;
		} else if (pwd.length() == 0) {
			// ID 입력시 비밀번호 검사. 비밀번호 미입력시 경고
			JOptionPane.showMessageDialog(this, "비밀번호를 입력하세요.", "로그인 알림", JOptionPane.INFORMATION_MESSAGE);
			pfPwd.requestFocus();
			return;
		}
		System.out.println("입력된 사용자 정보 => {ID=" + id + ", Pwd=" + pwd + "}");
		
		// ID와 비밀번호를 모두 입력했다면, member 테이블에 정보가 존재/일치하는지 확인한다.
		
		try {	// ID가 member 테이블에 존재하는지 확인
			loginUser = memberDao.selectIdCheck(id);
			
			if(!loginUser.isEmpty()) {	// 리스트가 비어있지 않은 경우, ID가 존재한다.
				loginUser = memberDao.selectPwdCheck(id, pwd);
				
				if(!loginUser.isEmpty()) {	// 리스트가 비어있지 않은 경우, ID가 존재한다.
					System.out.print("[로그인 성공 ");
					
					if(id.equals("master")||id.equals("admin")) {	// 로그인 계정이 관리자라면 관리자 화면 표시
						System.out.println("로그인 계정 : 관리자]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_AdminMain(id);
					} else {	// 로그인 계정이 일반 회원이면 회원 화면 표시
						System.out.println("로그인 계정 : 일반 회원]");
						System.out.println("=================================================================================");
						dispose();
						new Fr_MemberMain(id);
					}
					
					dispose();	// 로그인 창은 닫는다
				} else {
					System.err.println("[Login 경고 : 일치하지 않는 비밀번호]");
					JOptionPane.showMessageDialog(this, "비밀번호가 일치하지 않습니다.", "로그인 경고", JOptionPane.WARNING_MESSAGE);
					pfPwd.requestFocus();
				}
			} else {
				System.err.println("[Login 경고 : 존재하지 않는 아이디]");
				JOptionPane.showMessageDialog(this, "존재하지 않는 아이디입니다.", "로그인 경고", JOptionPane.WARNING_MESSAGE);
				tfId.requestFocus();
			}
		} catch (Exception e) {
			System.err.println("[Login 예외 : ID, pass 검색 실패]");
			e.printStackTrace();
		}
	}
	
    class MyPanel extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(img, 0, 0, null);
        }
    }

}