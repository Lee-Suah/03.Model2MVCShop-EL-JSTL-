// ��ǰ ��� 
package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;

import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.domain.User;
import javax.servlet.http.HttpSession;

// ���Ÿ� ���� ȭ�� ��û 
public class AddPurchaseViewAction extends Action {
	
	@Override
	public String execute( HttpServletRequest request,
												HttpServletResponse response) throws Exception {
	// ���� ���ǰ� �־��ֱ� 				
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	
		// ��ǰ��ȣ�� �ĺ��� 
		System.out.println(request.getParameter("prodNo"));
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductService service=new ProductServiceImpl();
		Product product=service.getProduct(prodNo);
		
		request.setAttribute("product", product);
		
		return "forward:/purchase/addPurchaseView.jsp";
	}
}
