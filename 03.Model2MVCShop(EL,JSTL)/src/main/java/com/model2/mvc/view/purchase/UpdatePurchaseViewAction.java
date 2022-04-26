package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

// 구매정보수정을 가기위한 액션 
public class UpdatePurchaseViewAction extends Action{

	@Override
	public String execute(HttpServletRequest request,
												HttpServletResponse response) throws Exception {

		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
	
		PurchaseService service=new PurchaseServiceImpl();
		Purchase purchaseVO=service.getPurchase(tranNo);
		
		request.setAttribute("purchaseVO", purchaseVO);
		
		System.out.println("구매vo"+purchaseVO);
		
		return "forward:/purchase/updatePurchaseView.jsp";
	}
}