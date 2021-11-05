package com.sist.goods03;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import java.awt.event.MouseListener;

public class GoodsTest extends JFrame {
	
	JTextField jtf_no;		//상품번호를 입력받기 위한 입력상자
	JTextField jtf_item;	//상품명을 입력받기 위한 입력상자
	JTextField jtf_qty;		//수량을 입력받기 위한 입력상자
	JTextField jtf_price;	//가격을 입력받기 위한 입력상자
	
	JTable table;					//모든 상품목록을 엑셀과같은 모양으로 보여주기 위한 테이블을 선언
	Vector colNames;		//테이블의 칼럼이름을 위한 벡터를 선언
	Vector<Vector> rowData;	// 테이블의 데이터를 위한 벡터를 선언
	
	//데이터베이스에 접근하여 추가,목록,수정,삭제 기능을 갖고 있는 
	//dao를 맴버로 선언한다
	GoodsDao dao;
	
	public void printGoods() {
		
		rowData.clear();
		ArrayList<GoodsVo>list=dao.listGoods();
		//list에 담긴 goodsvo를 하나씩 꺼내와서 
		//테이블의 데이터를 표현하기 위한 rowData에 담기
						
		for(GoodsVo g:list) {
			Vector v = new Vector();
			v.add(g.getNo());
			v.add(g.getItem());
			v.add(g.getQty());
			v.add(g.getPrice());
			rowData.add(v);
		}
		//테이블을 다시 그려주세요
		table.updateUI();
	}
	
	public GoodsTest() {
		
		//dao를 생성한다.
		dao = new GoodsDao();
		
		//테이블에 들어갈 칼럼이름을 위한 벡터를 생성하고 자료를 추가합니다.
		colNames = new Vector<String>();
		colNames.add("상품번호");
		colNames.add("상품명");
		colNames.add("수량");
		colNames.add("단가");
		
		//데이블에 들어가 실제데이터를 담기 위한 벡터를 생성하고 자료를 추가합니다.
		rowData = new Vector<Vector>();
		
		
		//컬럼이름이 있는 colName벡터와 실제데이터 있는 rowData벡터를 갖고 엑셀과같은 화면의 테이블을 생성
		table = new JTable(rowData,colNames);
		
		//테이블의 자료가 너무 많아서 한 화면에 보이지 않을때에 자동으로 스크롤를 만들어주는 스크롤팬을 생성
		JScrollPane jsp = new JScrollPane(table);
		
		jtf_no = new JTextField();
		jtf_item = new JTextField();
		jtf_qty = new JTextField();
		jtf_price = new JTextField();
		
		//입력상자들과 무엇을 입력해야하는지 설명하는 라벨들을 담기위한 패널을 생성
		JPanel p = new JPanel();
		p.setLayout(new GridLayout(4,2));
		
		//라벨과 입력상자(텍스트필드)들을 패널에 차례로 담아요
		p.add(new JLabel("상품번호:"));
		p.add(jtf_no);
		p.add(new JLabel("상품이름:"));
		p.add(jtf_item);
		p.add(new JLabel("상품수량:"));
		p.add(jtf_qty);
		p.add(new JLabel("상품단가:"));
		p.add(jtf_price);
		
		//"추가"글씨가 쓰여진 버튼을 만들어요
		JButton btn_add = new JButton("추가");
		
		//"목록"글씨가 쓰여진 버튼을 만들어
		JButton btn_list = new JButton("목록");
		
		//"수정"글씨가 쓰여진 버튼을 만들어요
		JButton btn_update = new JButton("수정");
		
		//"삭제"글씨가 쓰여진 버튼을 만들어요
		JButton btn_delete = new JButton("삭제");
		
		//버튼들을 담을 패널를 생성해요
		JPanel p2 = new JPanel();
		p2.add(btn_add);
		p2.add(btn_list);
		p2.add(btn_update);
		p2.add(btn_delete);
		
		//입력상자들이 있는 패널과 버튼이 있는 패널을 담기 위한 패널의 만들어요
		JPanel p_center = new JPanel();
		
		p_center.setLayout(new BorderLayout());
		p_center.add(p,BorderLayout.CENTER);
		p_center.add(p2, BorderLayout.SOUTH);
		
		//프레임의 가운데에 입력상자와 버튼을 담고 있는 p_center패널을 담아요
		add(p_center, BorderLayout.CENTER);
		
		//테이블을 담고 있는 스크롤팬을 프레임의 아래쪽에 담아요.
		add(jsp,BorderLayout.SOUTH);
		
		//프레임의 가로길이,세로길이를 설정하고 화면에 보여주도록 설정합니다.
		setSize(800,600);
		setVisible(true);
		
		//창을 닫으면 프로그램도 종료하도록 설정
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		btn_update.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int no = Integer.parseInt(jtf_no.getText());
				int qty = Integer.parseInt(jtf_qty.getText());
				int price = Integer.parseInt(jtf_price.getText());
				String item = jtf_item.getText();
				
				
				GoodsVo g = new GoodsVo(no, item, qty, price);
				int re = dao.insertGoods(g);
				
				if(re ==1) {
					System.out.println("상품수정에 성공하였습니다.");	
					printGoods();
				}else {
					System.out.println("상품수정에 실패하였습니다.");
				}
				
			}});
		
		
		
		btn_delete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int no = Integer.parseInt(jtf_no.getText());
				int re = dao.deleteGoods(no);
				if(re == 1) {
					System.out.println("상품을 삭제하였습니다.");	
					printGoods();
				}else {
					System.out.println("삭제에 실패하였습니다.");
				}
			}});
		
		
		btn_list.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printGoods();
			}});
		
		
		
		
		//"추가" 버튼을 누르면
		//사용자가 입력한 상품번호,상품이름,상품수량,상품가격
		//으로 데이터베이스 테이블에 자료를 추가하도록 합시다.
		btn_add.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				// 데이터베이스 insert를 수행하는 메소드를 만들고 호출하도록 수정 해 봅니다.`
				int no = Integer.parseInt(jtf_no.getText());
				String item = jtf_item.getText();
				int qty = Integer.parseInt(jtf_qty.getText());
				int price = Integer.parseInt(jtf_price.getText());
				
				//dao.insertGoods(no, item, qty, price);
				//위의 코드를 수정해 봅니다. 완성하면 "3"
				
				//사용자가 입력한 상품번호, 상품명, 수량, 가격을 갖고 Vo를 생성한다.
				GoodsVo g = new GoodsVo(no, item, qty, price);
				
				//그 VO를 dao의 insertGoods에게 전달한다.
				int re = dao.insertGoods(g);
				
				if(re ==1) {
					System.out.println("상품등록에 성공하였습니다.");	
					printGoods();
				}else {
					System.out.println("상품등록에 실패하였습니다.");
				}
				
			}});
		
		
		//테이블에 마우스 이벤트를 등록하여 선택한 행의 상품의정보를 각각의 입력상자에 출력
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				int index = table.getSelectedRow();
				
				//rowData의 index번째의 벡터를 꺼집어내어 온다.
				Vector vr = rowData.get(index);
				
				//그 벡터의 요소를 차례로 입력상자에 출력한다.
				jtf_no.setText(vr.get(0)+"");
				jtf_item.setText(vr.get(1)+"");
				jtf_qty.setText(vr.get(2)+"");
				jtf_price.setText(vr.get(3)+"");
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}});
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//InsertGoods 객체를 생성합니다.
		new GoodsTest();
	}

}












