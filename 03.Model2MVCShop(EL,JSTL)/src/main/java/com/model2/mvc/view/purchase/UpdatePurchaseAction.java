package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.domain.Purchase;

//  구매정보 수정 
public class UpdatePurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		int tranNo=Integer.parseInt(request.getParameter("tranNo"));
		
		Purchase purchaseVO=new Purchase();
		purchaseVO.setTranNo(tranNo);
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setReceiverPhone(request.getParameter("receiverPhone"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyRequest(request.getParameter("divyRequest"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		
	    PurchaseService service=new PurchaseServiceImpl();
		service.updatePurchase(purchaseVO);
		
		return "redirect:/getPurchase.do?tranNo="+tranNo;
	}
}