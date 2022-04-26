package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
//import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Purchase;


public class PurchaseDAO {
   
   public PurchaseDAO(){
   }

   //구매정보 상세조회를 위한 
   public void insertPurchase(Purchase purchase) throws Exception {
      
      Connection con = DBUtil.getConnection();

      String sql = "insert into transaction values (seq_transaction_tran_no.NEXTVAL,?,?,?,?,?,?,?,?,sysdate,?)";
      
      PreparedStatement stmt = con.prepareStatement(sql);
      
//      PurchaseVO purchase = new PurchaseVO();
//      UserVO buyer=purchase.getBuyer();
//      ProductVO purchaseProd = purchase.getPurchaseProd();
     
      stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
      stmt.setString(2, purchase.getBuyer().getUserId());
      stmt.setString(3, purchase.getPaymentOption());
      stmt.setString(4, purchase.getReceiverName());
      stmt.setString(5,  purchase.getReceiverPhone());
      stmt.setString(6, purchase.getDivyAddr());
      stmt.setString(7, purchase.getDivyRequest());
      stmt.setString(8, purchase.getTranCode());
      stmt.setString(9, purchase.getDivyDate().replace("-", "")); 
      
      stmt.executeUpdate();
          
      con.close();
   
   } //end of insertPurchase
   
   
   //구매이력조회 
   public Map<String,Object> getPurchaseList(Search search, String userId) throws Exception {
	   
	   Map<String,Object> map = new HashMap<String,Object>();
      
	   Connection con = DBUtil.getConnection();
      
      String sql = "SELECT * FROM transaction WHERE buyer_id='"+userId+"'";
      
      sql += " order by tran_no";
      
      System.out.println("PurchaseDAO::Original SQL :: " + sql);
      
    //==> TotalCount GET
      int totalCount = this.getTotalCount(sql);
      System.out.println("PurchaseDAO :: totalCount  :: " + totalCount);
      
      //==> CurrentPage 게시물만 받도록 Query 다시구성
      sql = makeCurrentPageSql(sql, search);
      PreparedStatement pStmt = con.prepareStatement(sql);
      ResultSet rs = pStmt.executeQuery();
      
      System.out.println(search);
   
      
      List<Purchase> list = new ArrayList<Purchase>();
      while(rs.next()){
        	 
            Purchase vo = new Purchase();
            User uvo = new User();
            Product pvo = new Product();
            
            
            vo.setTranNo(rs.getInt("tran_no"));
            uvo.setUserId(rs.getString("buyer_id"));
            vo.setBuyer(uvo);
            pvo.setProdNo(rs.getInt("prod_no"));
            vo.setPurchaseProd(pvo);
            
           // vo.setPurchaseProd(new ProductDAO().findProduct(rs.getInt("prod_no")));
            //vo.setBuyer(new UserDAO().findUser(rs.getString("user_id")));
            
            vo.setPaymentOption(rs.getString("payment_option"));
            vo.setReceiverName(rs.getString("receiver_name"));
            vo.setReceiverPhone(rs.getString("receiver_phone"));
            vo.setDivyAddr(rs.getString("demailaddr"));
            vo.setDivyRequest(rs.getString("dlvy_request"));
            vo.setTranCode(rs.getString("tran_status_code"));
            vo.setOrderDate(rs.getDate("order_data"));
            vo.setDivyDate(rs.getString("dlvy_date"));

            list.add(vo);
            
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
   
   // 구매 상세조회 
   public Purchase findPurchase(int tranNo) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "select * from transaction where tran_no=?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
	      stmt.setInt(1, tranNo);

	      ResultSet rs = stmt.executeQuery();

	      Purchase purchase = null;
	      while (rs.next()) {
	    	  purchase = new Purchase();
	          User uvo = new User();
	          Product pvo = new Product();
	            
	            purchase.setTranNo(rs.getInt("tran_no"));
	            uvo.setUserId(rs.getString("buyer_id"));
	            purchase.setBuyer(uvo);
	            pvo.setProdNo(rs.getInt("prod_no"));
	            purchase.setPurchaseProd(pvo);
	            
	            purchase.setPaymentOption(rs.getString("payment_option"));
	            purchase.setReceiverName(rs.getString("receiver_name"));
	            purchase.setReceiverPhone(rs.getString("receiver_phone"));
	            purchase.setDivyAddr(rs.getString("demailaddr"));
	            purchase.setDivyRequest(rs.getString("dlvy_request"));
	            purchase.setTranCode(rs.getString("tran_status_code"));
	            purchase.setOrderDate(rs.getDate("order_data"));
	            purchase.setDivyDate(rs.getString("dlvy_date"));
	         
	      }
	      
	      System.out.println("dao purchase" +purchase);
	      
	      con.close();
	      return purchase;
	   }
   
   // 구매 정보수정 
   public void updatePurchase(Purchase purchase) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "update transaction set payment_option=?,receiver_name=?,receiver_phone=?,demailaddr =?,dlvy_request=?,dlvy_date=? where tran_no = ?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
	      stmt.setString(1, purchase.getPaymentOption());
	      stmt.setString(2, purchase.getReceiverName());
	      stmt.setString(3, purchase.getReceiverPhone());
	      stmt.setString(4, purchase.getDivyAddr());
	      stmt.setString(5, purchase.getDivyRequest());
	      stmt.setString(6, purchase.getDivyDate());
	      stmt.setInt(7, purchase.getTranNo());
	     
	      stmt.executeUpdate();
	      
	      con.close();
	   }
   
   
// 구매 상태코드 수정 
   public void updateTranCode(Purchase purchase) throws Exception {
	      
	      Connection con = DBUtil.getConnection();

	      String sql = "update transaction set tran_status_code= ? where prod_no = ?";
	      
	      PreparedStatement stmt = con.prepareStatement(sql);
//	      stmt.setString(1, purchaseVO.getPaymentOption());
//	      stmt.setString(2, purchaseVO.getReceiverName());
//	      stmt.setString(3, purchaseVO.getReceiverPhone());
//	      stmt.setString(4, purchaseVO.getDivyAddr());
//	      stmt.setString(5, purchaseVO.getDivyRequest());
//	      stmt.setString(6, purchaseVO.getDivyDate());
	      stmt.setString(1, purchase.getTranCode());
	      stmt.setInt(2, purchase.getPurchaseProd().getProdNo());
//	      stmt.setInt(3, purchaseVO.getTranNo());
	//      stmt.setInt(3, purchaseVO.getTranNo());
	     
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
	   
	   
	   
   } // end of class
   