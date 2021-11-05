package com.sist.goods03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;

public class GoodsDao {
	
	//데이터베이스 접근하여 모든 상품목록을 읽어와서 반환하는 메소드를 정의
	//select * from goods
	//상품레코드를 하나하나를 GoodsVo로 포장하고 
	//이것들을 모두 ArrayList에 담아서 반환하려고 합니다.
	public ArrayList<GoodsVo> listGoods(){
		ArrayList<GoodsVo> list = new ArrayList<GoodsVo>();
		String sql = "select * from goods order by no";
				
				//데이터베이스 연결과 필요한 변수들을 선언한다.
				Connection conn = null;		//DB서버에 연결을 위한 변수
				//Statement stmt = null;		//데이터베이스 명령을 실행하기 위한 변수
				PreparedStatement pstmt = null;
				ResultSet rs = null;		//읽어온 데이터를 담기위한 변수
				
				try {				
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","c##sist","sist");
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while(rs.next()) {		
						int no = rs.getInt(1);
						String item = rs.getString(2);
						int qty = rs.getInt(3);
						int price= rs.getInt(4);
						
						//레코드의 내용을 GoodsVo객체로 만들어 list에 담아봅니다.
						list.add(new GoodsVo(no, item, qty, price));
						
						//프레임에서 목록 단추를 눌렀을 때 이 메소드를 호출해서 적용하기
					}
					
				}catch (Exception e) {
					System.out.println("예외발생:"+e.getMessage());
				}finally {
					try {				
						//사용했던 자원들을 닫아줍니다.
						if(rs != null) {
							rs.close();
						}
						if(pstmt !=null) {
							pstmt.close();
						}
						if(conn != null) {
							conn.close();
						}
						
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
		
		return list;
	}
	
	//수정할 상품번호, 상품이름, 수량, 가격을 매개변수로 전달받아 데이터베이스에 수정하는 메소드를 정의
		public int updateGoods(GoodsVo g) {
			
			int re= -1;
			//String sql = "update goods set item='"+item+"', qty="+qty +", price="+price+" where no="+no;
			String sql = "update goods set item=?,qty=?,price=? where no=?";
			Connection conn = null;
			//Statement stmt = null;
			//sql명령어안에 ?가 있는 경우 Statement의 후손클래스인 PreparedStatement를 이용합니다.
			PreparedStatement pstmt = null;
			try {
				//1. jdbc드라이버를 메모리로 로드한다.
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				//2. DB 서버에 연결한다.
				String url = "jdbc:oracle:thin:@localhost:1521:XE";
				String user = "c##sist";
				String pwd="sist";
				conn = DriverManager.getConnection(url, user, pwd);
				
				//3. Statement 객체를 생성한다.
				//stmt = conn.createStatement();
				//PreparesStatement 생성시에 ?가 있는 미리 만들어놓은 명령어를 매개변수로 전달해야 합니다.
				pstmt = conn.prepareStatement(sql);
				
				//PerparedStatement 객체에 결정되지 않는 ?에 차례대로 값을 설정해야 합니다.
				pstmt.setString(1,g.getItem());
				pstmt.setInt(2, g.getQty());
				pstmt.setInt(3, g.getPrice());
				pstmt.setInt(4, g.getNo());
				
				//4. 데이터베이스 명령을 실행한다
				//PreparedStatement 객체 생성시에 이미 sql이 전달되었고
				//그리고 위에서 각각의 ?값이 연결된 상태이기때문에 
				//executeUpdate할때는 sql를 전달하지 않아야 합니다.
				//만약 여기서 매개변수로 sql를 전달하게 되면 ?의 값이 정해지지 않는 상태로 명령을 실행하게 됩니다.
				re =pstmt.executeUpdate();
				
				//나머지 일처리를 한다.
				if(re == 1) {
					System.out.println("상품수정에 성공하였습니다.");
					
				}else {
					System.out.println("상품수정에 실패하였습니다.");
				}
				
			}catch (Exception e) {
				System.out.println("예외발생:"+e.getMessage());
			}finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
					
					if(conn != null) {
						conn.close();
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
				
			}
			return re;
			
			
		}
		
		
		
		//삭제할 상품번호를 매개변수로 전달받아 해당상품을 데이터베이스에 삭제하는 메소드를 정의
		public int deleteGoods(int no){
			int re= -1;
			//String sql = "delete goods where no="+no;
			String sql = "delete goods where no=?";
			Connection conn = null;
			//Statement stmt = null;		
			PreparedStatement pstmt = null;
			try {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "c##sist", "sist");
				//stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, no);
				
				 re = pstmt.executeUpdate();
				
			}catch (Exception e) {
				System.out.println("예외발생:"+e.getMessage());
			}finally {
				try {
					if(pstmt != null) {
						pstmt.close();
					}
					if(conn != null) {
						conn.close();
					}
				}catch (Exception e) {
					// TODO: handle exception
				}
			}
			return re;
			
			
		}
		
		//추가할 상품번호,상품명, 수량,단가를 매개변수로 전달받아 데이터베이스 추가하는 메소드를 정의
		public int insertGoods( GoodsVo g  ){
			int re = -1; //초기값을 아ㅜ상관없는 값으로
			//String sql = "insert into goods values("+no+",'"+item+"',"+qty+","+price+")";
			String sql = "insert into goods values(?,?,?,?)";
			
			//finally 에서도 접근할 수 있도록 Connection과 Statement
			//변수를 try문 바깥에 선언해요
			Connection conn = null;
			//Statement stmt = null;
			PreparedStatement pstmt = null;
			
			try {
				//1. jdbc드라이버를 메모리로 로드한다.
				Class.forName("oracle.jdbc.driver.OracleDriver");
				
				//2. DB서버에 연결한다.
				conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "c##sist", "sist");
				
				//3. 데이터베이스 명령을 실행할 수 있는 Statement 객체를 생성합니다.
				//stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, g.getNo());
				pstmt.setString(2, g.getItem());
				pstmt.setInt(3, g.getQty());
				pstmt.setInt(4, g.getPrice());
				
				//4. 데이터베이스 명령을 실행합니다.
				re = pstmt.executeUpdate();			
				
						
			}catch (Exception ex) {
				System.out.println("예외발생:"+ex.getMessage());
			}finally {
				
				try {
					//5. 사용했던 자원들을 닫아줍니다.						
					if(pstmt != null) {
						pstmt.close();
					}
					
					if(conn != null) {
						conn.close();
					}
				}catch (Exception ex) {
					// TODO: handle exception
				}
			}
			return re;
			
		}				
}
