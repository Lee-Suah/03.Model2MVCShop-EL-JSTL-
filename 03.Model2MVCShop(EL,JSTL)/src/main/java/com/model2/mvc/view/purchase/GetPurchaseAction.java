package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

// ���Ż���ȸ�� �̵� 
public class GetPurchaseAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("tranNo"));
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		//String menu = request.getParameter("menu");
		PurchaseService service=new PurchaseServiceImpl();
		Purchase vo=service.getPurchase(tranNo);
		
		request.setAttribute("vo", vo);
		//request.setAttribute("menu", menu);
		
//		if(menu!=null) {
//	         if(menu.equals("search")) {
//	            return "forward:/product/getProductView.jsp"; // ���Ź�ư �ִ� jsp  
//	         }else{
//	             return "forward:/updateProductView.do"; // ������Ʈ ���δ�Ʈ ��׼�
//	         }
//	      }else {
//	         return "forward:/product/readProductView.jsp"; // Ȯ�ι�ư�ִ� jsp 
//	      }
	
		return "forward:/purchase/getPurchaseView.jsp";  // ���Ż���ȸ jsp�� �̵� 
	}
}