package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.table.DefaultTableModel;

public class DB_ReJTable2DAO {
	Connection con;
	Statement st;
	PreparedStatement ps;
	ResultSet rs;

	public DB_ReJTable2DAO() {
		con = DB_DBConn.getConnection();
	}
	
	public void dbClose() {
		try {
			if (rs != null)
				rs.close();
			if (st != null)
				st.close();
			if (ps != null)
				ps.close();
		} catch (Exception e) {
			System.out.println(e + "=> dbClose fail");
		}
	}


	////////////////////////////// ++++++++++++++++++++++++++++////////////////
////////////////////////////////////////////////////////////////////
//////////////////새로 추가한 메소드 /////////////////////////////////////	

	// 조회(관리자)
	public void showListadmre(DefaultTableModel t_model) {
		try {
			st = con.createStatement();
			
			rs = st.executeQuery("select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_recommender, p.price"
					+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id)"
					+ " ORDER BY TO_NUMBER(r.r_id)");

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };
				t_model.addRow(data); // DefaultTableModel에 레코드 추가
			}
		} catch (SQLException e) {
			System.out.println(e + "=> showListadmre fail");
		} finally {
			dbClose();
		}
	}

	// 조회(멤버)
	public void showListmemre(DefaultTableModel t_model, String user) {
		try {
			st = con.createStatement();
//			rs = st.executeQuery("select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_recommender, p.price"
//					+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id) " + " where rm_id = '"
//					+ user + "' ORDER BY TO_NUMBER(r.r_id)"); 초기화 내용 //
			rs = st.executeQuery("select r.re_id, r.r_id,  p.e_name, p.time, p.e_recommender, p.price"
					+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id) " + " where rm_id = '"
					+ user + "' ORDER BY TO_NUMBER(r.r_id)");

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < t_model.getRowCount();) {
				t_model.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2),  rs.getString(3),
						rs.getString(4), rs.getString(5), rs.getInt(6) };
				t_model.addRow(data); // DefaultTableModel에 레코드 추가
			}
		} catch (SQLException e) {
			System.out.println(e + "=> showListmemre fail");
		} finally {
			dbClose();
		}
	}

	// 검색(관리자)
	public void searchadmre(DefaultTableModel redt2, String fieldName, String word) {
		String sql = "select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_recommender, p.price"
				+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id)" + " WHERE "
				+ fieldName.trim() + " LIKE '%" + word.trim() + "%' ORDER BY TO_NUMBER(r.r_id)";
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < redt2.getRowCount();) {
				redt2.removeRow(0);
			}

			while (rs.next()) {
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
						rs.getString(6), rs.getString(7), rs.getInt(8) };

				redt2.addRow(data);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> Searchadmre fail");
		} finally {
			dbClose();
		}
	}

	// 검색(멤버)
	public void searchmemre(DefaultTableModel redt2, String fieldName, String word, String user) {
		
//		String sql = "select r.r_id, r.rm_id, r.re_id, m.m_name, p.e_name, p.time, p.e_recommender, p.price"
//				+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id)" + " WHERE "
//				+ fieldName.trim() + " LIKE '%" + word.trim() + "%' AND rm_id = '" + user + "' ORDER BY TO_NUMBER(r.r_id)";
		String sql = "r.re_id, r.r_id,  p.e_name, p.time, p.e_recommender, p.price"
				+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id)" + " WHERE "
				+ fieldName.trim() + " LIKE '%" + word.trim() + "%' AND rm_id = '" + user + "' ORDER BY TO_NUMBER(r.r_id)";

		System.out.println(sql);
		
		try {
			st = con.createStatement();
			rs = st.executeQuery(sql);

			// DefaultTableModel에 있는 기존 데이터 지우기
			for (int i = 0; i < redt2.getRowCount();) {
				redt2.removeRow(0);
			}

			while (rs.next()) {
//				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), 
//						rs.getString(6), rs.getString(7), rs.getInt(8) };
				Object data[] = { rs.getString(1), rs.getString(2), rs.getString(3), 
						rs.getString(4), rs.getString(5), rs.getInt(6) };
				redt2.addRow(data);
			}
		} catch (SQLException e) {
			System.out.println(e + "=> Searchmemre fail");
		} finally {
			dbClose();
		}
	}

	// 추가
	public int insertre(Fr_ReJTable2DialogGUI re) {
		int result = 0;
		try {
			ps = con.prepareStatement("insert into re (r_id, rm_id, re_id)" + "values (seq_commid.nextval,?,?)");
			ps.setString(1, re.cbM_id.getSelectedItem().toString());
			ps.setString(2, re.cbE_id.getSelectedItem().toString());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> insertre fail");
		} finally {
			dbClose();
		}
		return result;
	}

	// 수정
	public int updatere(Fr_ReJTable2DialogGUI re) {
		int result = 0;
		String sql = "UPDATE re SET rm_id=?, re_id=? WHERE r_id=?";

		try {
			ps = con.prepareStatement(sql);
			// ?의 순서대로 값 넣기
			ps.setString(1, re.cbM_id.getSelectedItem().toString());
			ps.setString(2, re.cbE_id.getSelectedItem().toString());
			ps.setString(3, re.r_id.getText().trim());

			result = ps.executeUpdate();
		} catch (SQLException e) {
			System.out.println(e + "=> updatere fail");
		} finally {
			dbClose();
		}

		return result;
	}

	// 삭제
	public int deletere(String r_id) {
		int result = 0;
		try {
			ps = con.prepareStatement("delete re where r_id = ? ");
			ps.setString(1, r_id.trim());
			result = ps.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e + "=> deletere fail");
		} finally {
			dbClose();
		}

		return result;
	}
	
	//////////////////////////////++++++++++++++++++++++++++++////////////////
	
		// 회원 삭제시 해당 회원의 예약 정보도 함께 삭제하기 위한 메소드
		public int reDeleteByRm_id(String rm_id) {
			int result = 0;
			try {
				ps = con.prepareStatement("delete re where rm_id = ? ");
				ps.setString(1, rm_id.trim());
				result = ps.executeUpdate();

			} catch (SQLException e) {
				System.out.println(e + "=> reDelete fail");
			} finally {
				dbClose();
			}

			return result;
		}
		
		////////////////모임 테이블에서 예약 테이블에 추가하는 SQL 메소드들////////////////////////
		
		// 임시 예약 테이블 sql 메서드
		// 모임 삭제되면 예약테이블도 삭제 메서드
		public int commreDelete(String commreText) {
			
			int result = 0;
			try {
				String sql = "delete from re where re_id=?";
				
				ps = con.prepareStatement(sql);
				
				System.out.println(commreText);
				
				ps.setString(1, commreText.trim());
				
				System.out.println(sql + " : 삭제 sql문");
				
				result = ps.executeUpdate();
				System.out.println(result + " : 행 실행 완료 흑흑");
				
			} catch (Exception e) {
				System.out.println(e + " : Delete 실패");
				e.printStackTrace();
			} finally {
				try {
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			
			return 0;
		}
		
		
		public int commReinsert(String loginUser, String commText) {
			int result = 0;
			
			try {
				System.out.println("연결 성공");
				
				System.out.println(loginUser);
				System.out.println(commText);
				
				st = con.createStatement();
				String sql = "insert into re (r_id, rm_id, re_id)" 
				+ "values(seq_reid.nextval,'" + loginUser + "'," + "'" + commText + "')";
				
				result = st.executeUpdate(sql);
				System.out.println(sql + " : sql문");
				System.out.println(result + " : 개의 행 실행완료");
				
			} catch (Exception e) {
				System.out.println(e + "ReInset 실패");
				e.printStackTrace();
			} finally {
				try {
					st.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return result;	
		}
		
		// 임시 DB의 중복된 comm아이디 체크해서 중복된 값 체크하는 메서드
		public ArrayList<DB_ReDTO> commInsertCheck(String loginUser, String commText) {
			ArrayList<DB_ReDTO> list = new ArrayList<DB_ReDTO>();
			try {
//				System.out.println("연결 성공");
//				String sql = "select r_id from re " 
//				+ " where rm_id='" + loginUser + "'" + " and re_id='" + commText + "'";
				String sql = "select r.r_id, r.rm_id, p.time"
						+ " from re r join member m on(r.rm_id = m.m_id) join comm p on(re_id = p.e_id)"
						+ " WHERE rm_id='" + loginUser + "'" + " and time=" + "(select time from comm where e_id = '" + commText +"')";
				ps = con.prepareStatement(sql);
				System.out.println(sql);
				rs = ps.executeQuery();
				while(rs.next()) {
					DB_ReDTO dto = new DB_ReDTO();
					dto.r_id = rs.getString(1);
					list.add(dto);
				}
				
			} catch (Exception e) {
				System.out.println(e + " : Insert Check 안됨");
			} finally {
				try {
					rs.close();
					ps.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}	
			return list;
		}
}