package com.model2.mvc.view.purchase;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;

// 구매목록 요청 (ui - 구매이력조회) 
public class ListPurchaseAction extends Action {

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		Search search=new Search();
		
	    HttpSession session = request.getSession();
	    User userVO = (User)session.getAttribute("user");
	    
		
	    int currentPage=1;
	    if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
	    search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic 수행
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(search, userVO.getUserId());
		
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	    System.out.println("ListPurchaseAction ::"+resultPage);
		
		
	    // Model 과 View 연결
	    request.setAttribute("list", map.get("list"));
		request.setAttribute("searchVO", search);
		request.setAttribute("resultPage", resultPage);
		
		return "forward:/purchase/listPurchaseView.jsp"; // 상품 목록조회 ui 이동 
	}
}