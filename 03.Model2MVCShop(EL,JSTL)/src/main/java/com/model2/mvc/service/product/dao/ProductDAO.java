package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;

public class ProductDAO {
   
   public ProductDAO(){
   }

   //상품등록
   public void insertProduct(Product product) throws Exception {
      
      Connection con = DBUtil.getConnection();

      String sql = "insert into PRODUCT values (seq_product_prod_no.NEXTVAL,?,?,?,?,?,sysdate)";
      
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1, product.getProdName());
      stmt.setString(2, product.getProdDetail());
      stmt.setString(3, product.getManuDate().replace("-", ""));
      stmt.setInt(4, product.getPrice());
      stmt.setString(5, product.getFileName());
      
      stmt.executeUpdate();
          
      con.close();
   }

   //상품조회
   public Product findProduct(int prodNo) throws Exception {
      
      Connection con = DBUtil.getConnection();

      String sql = "select * from product where prod_No=?";
      
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setInt(1, prodNo);

      ResultSet rs = stmt.executeQuery();

      Product product = null;
      while (rs.next()) {
         product = new Product();
         
         product.setFileName(rs.getString("image_file"));
         product.setManuDate(rs.getString("manufacture_day"));
         product.setPrice(rs.getInt("price"));
         product.setProdDetail(rs.getString("prod_detail"));
         product.setProdName(rs.getString("prod_name"));
         product.setProdNo(rs.getInt("prod_no"));
         product.setRegDate(rs.getDate("reg_date"));
         //productVO.setProTranCode(rs.getString("PROTRANCODE"));
         
      }
      
      System.out.println(product);
      
      con.close();
      return product;
   }

   //상품 목록조회
   public Map<String,Object> getProductList(Search search) throws Exception {
	      
	   	Map<String,Object> map = new HashMap<String,Object>();
	   
	      Connection con = DBUtil.getConnection();
	      
	      
	      String sql = "select p.prod_no, p.prod_name, p.price, p.reg_date, NVL(t.tran_status_code,0) from product p, transaction t where p.prod_no = t.prod_no(+)";
	      if (search.getSearchCondition() != null) {
	    	  if ( search.getSearchCondition().equals("0") &&  !search.getSearchKeyword().equals("") ) {
	            sql += " and prod_no=' " + search.getSearchKeyword()
	                  + " ' ";
	         }if (search.getSearchCondition().equals("1") &&  !search.getSearchKeyword().equals("")) {
	            sql += " and prod_name like '%"+search.getSearchKeyword()+"%' ";
	                  
	         }
	         if (search.getSearchCondition().equals("2") &&  !search.getSearchKeyword().equals("")) {
	             sql += " and price='" + search.getSearchKeyword()
	                   + "'"; 
	          }
	      }
	      
	      sql += "order by p.prod_no";
	      
	      System.out.println("ProductDAO::Original SQL :: " + sql);
	      
	      
	      //==> TotalCount GET
	      int totalCount = this.getTotalCount(sql);
	      System.out.println("ProductDAO :: totalCount  :: " + totalCount);
	      
	      //==> CurrentPage 게시물만 받도록 Query 다시구성
	      sql = makeCurrentPageSql(sql, search);
	      PreparedStatement pStmt = con.prepareStatement(sql);
	      ResultSet rs = pStmt.executeQuery();
	   
	      System.out.println(search);

	      List<Product> list = new ArrayList<Product>();
	      
	      while(rs.next()){
	      
	            Product vo = new Product();
	            //PurchaseVO pv = new PurchaseVO();
	            //vo.setFileName(rs.getString("image_file"));
	            //vo.setManuDate(rs.getString("manufacture_day"));
	            vo.setPrice(rs.getInt("price"));
	            //vo.setProdDetail(rs.getString("prod_detail"));
	            vo.setProdName(rs.getString("prod_name"));
	            vo.setProdNo(rs.getInt("prod_no"));
	            vo.setRegDate(rs.getDate("reg_date"));
	            vo.setProTranCode(rs.getString("NVL(t.tran_status_code,0)"));
	            
	            list.add(vo);
	            System.out.println("네임"+vo.getProdName());

      }
	      
	      //==> totalCount 정보 저장
	      map.put("totalCount", new Integer(totalCount));
	      //==> currentPage 의 게시물 정보 갖는 List 저장
	      map.put("list", list);
	      

	      rs.close();
	      pStmt.close();
	      con.close();

	      return map;
   }
   
   
   //상품 정보수정
   public void updateProduct(Product product) throws Exception {
      
      Connection con = DBUtil.getConnection();

      String sql = "update product set prod_name=?, prod_detail=?, manufacture_day=?, price=?, image_file=? where prod_no=?";
      
      PreparedStatement stmt = con.prepareStatement(sql);
      stmt.setString(1,product.getProdName());
      stmt.setString(2,product.getProdDetail());
      stmt.setString(3, product.getManuDate());
      stmt.setInt(4, product.getPrice());
      stmt.setString(5, product.getFileName());
      stmt.setInt(6, product.getProdNo());
   
      stmt.executeUpdate();
      
      con.close();
   }
   
// 게시판 Page 처리를 위한 전체 Row(totalCount)  return
	private int getTotalCount(String sql) throws Exception {
		
		sql = "SELECT COUNT(*) "+
		          "FROM ( " +sql+ ") countTable";
		
		Connection con = DBUtil.getConnection();
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();
		
		int totalCount = 0;
		if( rs.next() ){
			totalCount = rs.getInt(1);
		}
		
		pStmt.close();
		con.close();
		rs.close();
		
		return totalCount;
	}
	
	// 게시판 currentPage Row 만  return 
	private String makeCurrentPageSql(String sql , Search search){
		sql = 	"SELECT * "+ 
					"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
									" 	FROM (	"+sql+" ) inner_table "+
									"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
					"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
		
		System.out.println("ProductDAO :: make SQL :: "+ sql);	
		
		return sql;
	}
   
   
   
   
   
   
}