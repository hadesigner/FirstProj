package test;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class DB_CommDAO {
	
	Connection con;
	Statement st;
	PreparedStatement pst;
	ResultSet rs;
	
	public DB_CommDAO() {
		con = DB_DBConn.getConnection();
	}
	
	// DB의 전체 리스트 가져온다
	public ArrayList<DB_CommDTO> commShowList() {
		ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
		
		try {
			
			String sql = "select e_id, e_name, time, e_recommender," + 
					" price from comm order by to_number(e_id)";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			
			while(rs.next()) {
				DB_CommDTO dto = new DB_CommDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_recommender = rs.getString("e_recommender");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 검색 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 검색할때 콤보박스의 리스트를 클릭하면 오름차순으로 정렬
	public ArrayList<DB_CommDTO> comboSeletedShow(String combo) {
		ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
		
		try {
			
			
			String sql = "select e_id, e_name, time, e_recommender, "
					+ "price from Comm order by " + combo + "";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				DB_CommDTO dto = new DB_CommDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_recommender = rs.getString("e_recommender");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 콤보박스 리스트 출력 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return list;	
	}
	
	// 회원 가입 폼의 코치 선택할때 중복된 코치의 값 제거
	public ArrayList<DB_CommDTO> commSelectrecommender() {
		ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
		
		try {
			String sql = "select distinct e_recommender from Comm";
			
			System.out.println(sql);
			
			pst = con.prepareStatement(sql);
			rs = pst.executeQuery();

			
			while(rs.next()) {
				DB_CommDTO dto = new DB_CommDTO();
				
				dto.e_recommender = rs.getString("e_recommender");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 지역 출력 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// Insert
	public int commInsert(DB_CommDTO commInsert) {
		
		int result = 0;
		try {
			System.out.println("연결 성공");
			
			String sql = "insert into Comm (e_id, e_name, time, e_recommender, price)" + 
					"values (seq_commid.nextval,?,?,?,?)";
			
			pst = con.prepareStatement(sql);
			
			pst.setString(1, commInsert.getE_name());
			
			pst.setString(2, commInsert.getTime());
			
			pst.setString(3, commInsert.getE_recommender());
			
			pst.setInt(4, commInsert.getPrice());
			
			System.out.println(commInsert.toString());
			
			result = pst.executeUpdate();
			System.out.println(result + " : 행 입력");
			
			DB_CommDTO dto = new DB_CommDTO();
			System.out.println("insert to : " + dto.toString());
		} catch (Exception e) {
			System.out.println(e + " : insert 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return result;	
	}
	
	// Update
	public int commUpdate(DB_CommDTO commupdate) {
		int result = 0;
		
		try {
			String sql = "update comm set e_name=?, time=?, e_recommender=?, price=?"
					+ " where e_id=?";
			pst = con.prepareStatement(sql);
			
			pst.setString(1, commupdate.getE_name());
			pst.setString(2, commupdate.getTime());
			pst.setString(3, commupdate.getE_recommender());
			pst.setInt(4, commupdate.getPrice());
			// to_cahr 문자 그대로 가져오기때문에 서브스트링으로 id 번호 잘라줘야됨
			pst.setString(5, commupdate.getE_id());
			
			System.out.println(commupdate.toString());
			System.out.println(pst);

			result = pst.executeUpdate();
			System.out.println(result + " : 행 입력");
		} catch (Exception e) {
			System.out.println(e + " : update 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	// Delete
	public int commDelete(DB_CommDTO commDelete) {
		int result = 0;
		try {
			String sql = "delete from Comm where e_id=?";
			
			pst = con.prepareStatement(sql);
			
			pst.setString(1, commDelete.getE_id());
			
			System.out.println(sql + " : 삭제 sql문");
			
			result = pst.executeUpdate();
			System.out.println(result + " : 행 실행 완료");
		} catch (Exception e) {
			System.out.println(e + " : Delete 실패");
			e.printStackTrace();
		} finally {
			try {
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return result;
		
	}
	
	// 검색
	public ArrayList<DB_CommDTO> commSearch(String comboName, String word) {
		ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
		
		try {
			
			String sql = "select * from Comm where " + comboName.trim()
			+ " like '%" + word.trim() + "%'";
			
			System.out.println(sql);
			
			st = con.createStatement();
			rs = st.executeQuery(sql);

			
			while(rs.next()) {
				DB_CommDTO dto = new DB_CommDTO();
				
				dto.e_id = rs.getString("e_id");
				dto.e_name = rs.getString("e_name");
				dto.time = rs.getString("time");
				dto.e_recommender = rs.getString("e_recommender");
				dto.price = rs.getInt("price");
				
				System.out.println("select : " + dto.toString());
					
				list.add(dto);
			}
		} catch (Exception e) {
			System.out.println(e + " : 검색 실패");
			e.printStackTrace();
		} finally {
			try {
				rs.close(); st.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	// 운동 등록할때 같은 값으로 선행 등록 되어 있는지 체크
	public ArrayList<DB_CommDTO> InsertCheck(String ename, String time, String recommender) {
		
		ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
		try {
			System.out.println("연결 성공");
			String sql = "select e_id from comm " 
			+ " where e_name='" + ename + "'" + " and time='" + time + "' and e_recommender= '" + recommender + "'";
			pst = con.prepareStatement(sql);
			System.out.println(sql);
			rs = pst.executeQuery();
			while(rs.next()) {
				DB_CommDTO dto = new DB_CommDTO();
				dto.e_id = rs.getString(1);
				list.add(dto);
			}
			
		} catch (Exception e) {
			System.out.println(e + " : Insert Check 안됨");
		} finally {
			try {
				rs.close();
				pst.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}	
		return list;
	}
	
	// 운동 ID 조회
		public ArrayList<DB_CommDTO> commSelectE_id() {
			ArrayList<DB_CommDTO> list = new ArrayList<DB_CommDTO>();
			
			try {
				
				String sql = "select distinct e_id from comm order by to_number(e_id)";
				
				System.out.println(sql);
				
				pst = con.prepareStatement(sql);
				rs = pst.executeQuery();

				
				while(rs.next()) {
					DB_CommDTO dto = new DB_CommDTO();
					
					dto.e_recommender = rs.getString("e_id");
					
					System.out.println("select : " + dto.toString());
						
					list.add(dto);
				}
			} catch (Exception e) {
				System.out.println(e + " : e_id 출력 실패");
				e.printStackTrace();
			} finally {
				try {
					rs.close(); pst.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
			return list;
		}
	}