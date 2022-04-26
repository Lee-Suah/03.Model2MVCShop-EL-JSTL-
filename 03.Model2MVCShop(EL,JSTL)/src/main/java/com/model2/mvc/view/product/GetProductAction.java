package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.domain.Product;

// 상품상세보기(ui - 판매상품관리) 
public class GetProductAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		System.out.println(request.getParameter("prodNo"));
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		String menu = request.getParameter("menu");
		ProductService service=new ProductServiceImpl();
		Product vo=service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		request.setAttribute("menu", menu);
		
        Cookie cookie = new Cookie("history", Integer.toString(prodNo));
		response.addCookie(cookie);
		
//		if(menu!=null) {
//	         if(menu.equals("search")) {
//	            return "forward:/product/getProductView.jsp"; // 구매버튼 있는 jsp  
//	         }else{
//	             return "forward:/product/updateProductView.do"; // 업데이트 프로덕트 뷰액션
//	         }
//	      }else {
//	         return "forward:/product/readProductView.jsp"; // 확인버튼있는 jsp 
//	      }
	
		return "forward:/product/getProductView.jsp";  // 상품 상세조회 jsp로 이동 
		
		
	}
}