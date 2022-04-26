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

// ���� ��û 
public class AddPurchaseAction extends Action {
   
   @Override
   public String execute( HttpServletRequest request,
                                    HttpServletResponse response) throws Exception {
   
	  // ���� ���ǰ� �־��ֱ� 				
	     HttpSession session = request.getSession();
	     User user = (User)session.getAttribute("user");
	   
      // ��ǰ��ȣ�� �ĺ��� 
      int prodNo=(Integer.parseInt(request.getParameter("prodNo")));
      
      // ���δ�Ʈ vo�� �޾ƿ��� 
         Product pvo=new Product();
         pvo.setProdNo(Integer.parseInt(request.getParameter("prodNo")));
      
         System.out.println("ProductVO"+pvo);
     
        // ���� vo �� �޾ƿ��� 
         User uvo=new User();
         uvo.setUserId(request.getParameter("userId"));
         
         System.out.println("UserVO"+uvo);
         
         
         // ���� vo �� �޾ƿ��� 
         Purchase purchaseVO=new Purchase();
         purchaseVO.setPurchaseProd(pvo);
         purchaseVO.setBuyer(uvo);
         purchaseVO.setPaymentOption(request.getParameter("paymentOption")); // ���Ź��
         purchaseVO.setReceiverName(request.getParameter("receiverName")); // �������̸�
         purchaseVO.setReceiverPhone(request.getParameter("receiverPhone")); // �����ڿ���ó
         purchaseVO.setDivyAddr(request.getParameter("receiverAddr")); // ������ �ּ� 
         purchaseVO.setDivyRequest(request.getParameter("receiverRequest")); // ���ſ�û����
         purchaseVO.setDivyDate(request.getParameter("receiverDate")); // ��������¥ 
         purchaseVO.setTranCode("1"); // �����ϸ� Ʈ���ڵ� 1�� ���� 
         
         
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