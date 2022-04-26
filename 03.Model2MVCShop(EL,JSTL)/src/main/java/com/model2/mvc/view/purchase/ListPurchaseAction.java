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

// ���Ÿ�� ��û (ui - �����̷���ȸ) 
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
		
		// web.xml  meta-data �� ���� ��� ���� 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		// Business logic ����
		PurchaseService service=new PurchaseServiceImpl();
		Map<String,Object> map=service.getPurchaseList(search, userVO.getUserId());
		
		
		Page resultPage	= 
				new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
	    System.out.println("ListPurchaseAction ::"+resultPage);
		
		
	    // Model �� View ����
	    request.setAttribute("list", map.get("list"));
		request.setAttribute("searchVO", search);
		request.setAttribute("resultPage", resultPage);
		
		return "forward:/purchase/listPurchaseView.jsp"; // ��ǰ �����ȸ ui �̵� 
	}
}