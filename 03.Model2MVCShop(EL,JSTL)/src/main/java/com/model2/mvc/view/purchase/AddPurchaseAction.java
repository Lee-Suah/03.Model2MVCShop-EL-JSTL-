package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;

import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.domain.Product;
import javax.servlet.http.HttpSession;

// 구매 요청 
public class AddPurchaseAction extends Action {
   
   @Override
   public String execute( HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
   
	  // 유저 세션값 넣어주기 				
	     HttpSession session = request.getSession();
	     User user = (User)session.getAttribute("user");
	   
      // 상품번호로 식별값 
      int prodNo=(Integer.parseInt(request.getParameter("prodNo")));
      
      // 프로덕트 vo값 받아오기 
         Product pvo=new Product();
         pvo.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
      
         System.out.println("ProductVO"+pvo);
     
        // 유저 vo 값 받아오기 
         User uvo=new User();
         uvo.setUserId(request.getParameter("userId"));
         
         System.out.println("UserVO"+uvo);
         
         
         // 구매 vo 값 받아오기 
         Purchase purchaseVO=new Purchase();
         purchaseVO.setPurchaseProd(pvo);
         purchaseVO.setBuyer(uvo);
         purchaseVO.setPaymentOption(request.getParameter("paymentOption")); // 구매방법
         purchaseVO.setReceiverName(request.getParameter("receiverName")); // 구매자이름
         purchaseVO.setReceiverPhone(request.getParameter("receiverPhone")); // 구매자연락처
         purchaseVO.setDivyAddr(request.getParameter("receiverAddr")); // 구매자 주소 
         purchaseVO.setDivyRequest(request.getParameter("receiverRequest")); // 구매요청사항
         purchaseVO.setDivyDate(request.getParameter("receiverDate")); // 배송희망날짜 
         purchaseVO.setTranCode("1"); // 구매하면 트랜코드 1로 변경 
         
         
      System.out.println("purchaseVO"+purchaseVO);
      
      PurchaseService service = new PurchaseServiceImpl(); 
      service.addPurchase(purchaseVO);
       
       request.setAttribute("purchase", purchaseVO);
       
       
      
//      HttpSession session=request.getSession();
//      int sessionId=((ProductVO)session.getAttribute("product")).getProdNo();
//   
//      if(sessionId.equals(prodNo)){
//         session.setAttribute("product", productVO);
//      }

      return "forward:/purchase/addPurchase.jsp";
   }
}