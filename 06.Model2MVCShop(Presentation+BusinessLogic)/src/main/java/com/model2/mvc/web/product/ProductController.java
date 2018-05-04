package com.model2.mvc.web.product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;

import com.model2.mvc.service.product.impl.ProductServiceImpl;



//==> 회원관리 Controller
@Controller
public class ProductController {
	
	///Field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	//setter Method 구현 않음
		
	public ProductController(){
		System.out.println(this.getClass());
		System.out.println("agsadg");
	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml 참조 할것
	//==> 아래의 두개를 주석을 풀어 의미를 확인 할것
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}")
	int pageUnit;
	
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	
	@RequestMapping("/addProductView.do")
	public String addProductView() throws Exception {

		System.out.println("/addProductView.do");
		
		return "redirect:/product/addProductView.jsp";
	}
	
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product ) throws Exception {

		System.out.println("/addProduct.do");
		//Business Logic
		productService.addProduct(product);
		
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo , Model model ) throws Exception {
		
		System.out.println("/getProduct.do");
		//Business Logic
		Product Product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("product", Product);
		
		return "forward:/product/getProduct.jsp";
	}
	
	/*@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo , Model model ) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		Product Product = productService.getProduct(prodNo);
		// Model 과 View 연결
		model.addAttribute("Product", Product);
		
		return "forward:/product/updateProduct.jsp";
	}*/
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView(@RequestParam("prodNo") int prodNo , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProductView.do");
		//Business Logic
		System.out.println("/updateProduct.do");
		//Business Logic
		Product product = productService.getProduct(prodNo);
				
		model.addAttribute("vo",product);
		System.out.println("들어오기는함?");
		
		return "forward:/product/updateProduct.jsp";
		
	}
	
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("Product") Product product , Model model , HttpSession session) throws Exception{

		System.out.println("/updateProduct.do");
		//Business Logic
		productService.updateProduct(product);
		
		String prodNo = product.getProdNo()+"";
		System.out.println(prodNo+"---");
		model.addAttribute("product",productService.getProduct(product.getProdNo()));
		
		return "forward:/product/getProduct.jsp?prodNo="+prodNo.trim()+"&menu=manage";
	}
			
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search , Model model , HttpServletRequest request) throws Exception{
		
		System.out.println("/listProduct.do");
		
		if(search.getCurrentPage() ==0 ){
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
	
		
		//추가기능 날짜,가격 Sorting
		String daysorting = "";
		String sorting = "low";
		System.out.println("Daysorting를 위한 parameter!!!!!!!!!!!!!!!!!+++++++:::::"+daysorting);
		
		if(request.getParameter("daysorting")!=null && request.getParameter("daysorting").equals("highDay")) {
			search.setDaySorting(request.getParameter("daysorting"));
			search.setSorting("a");
			daysorting = request.getParameter("daysorting");
		}else if(request.getParameter("daysorting")!=null && request.getParameter("daysorting").equals("lowDay")) {
			
			daysorting = request.getParameter("daysorting");
			search.setDaySorting(daysorting);
			search.setSorting("a");
		}else if(search.getSorting()!="a") {
			
			if(request.getParameter("sorting")!=null && request.getParameter("sorting").equals("high")) {
				search.setSorting(request.getParameter("sorting"));
				sorting = request.getParameter("sorting");
			}else {
				search.setSorting(sorting);
			}
			
		}
		
		request.setAttribute("sorting", sorting);
		System.out.println("sorting를 위한 parameter!!!!!!!!!!!!!!!!!+++++++:::::"+sorting);
		request.setAttribute("daysorting", daysorting);
		
		// Business logic 수행
		
		//검색시 값입력
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		/*search.setStartRowNum();
		search.setEndRowNum();*/
		
		System.out.println(request.getParameter("searchCondition")+":::::::::"+request.getParameter("searchKeyword"));
		
		
				
		Map<String , Object> map=productService.getProductList(search);
		
		Page resultPage = new Page( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println(resultPage);
		
		// Model 과 View 연결
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		
	
		
		return "forward:/product/listProduct.jsp";
	}
	
	@RequestMapping("/mainView.do")
	 public String mainlist(HttpServletRequest request ,HttpServletResponse response) throws Exception{
		System.out.println("===========mainlist==========start");
		
			
			Map<String, Object> map = productService.getMainList();
			request.setAttribute("list", map.get("list"));
			
			System.out.println();
			
			if(request.getParameter("manuDate")!=null) {
				//조회수리스트 제목용 날짜
				request.setAttribute("day", request.getParameter("manuDate"));
				
				String day = request.getParameter("manuDate").replaceAll("-", "");
				//달력값 날짜
				
					
				request.setAttribute("pday", day);
				
				
				map = productService.getLookupList(day);
				
				
				request.setAttribute("lookuplist", map.get("lookuplist"));
				
				System.out.println(day+"=====::parsing한 날짜");
				
				}else {
					Date today = new Date();
					SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
					
					String day = date.format(today);
					//달력값 날짜
					
						
					request.setAttribute("pday", day);
					
					
					map = productService.getLookupList(day);
					
					
					request.setAttribute("lookuplist", map.get("lookuplist"));
				}
				
				System.out.println("조회수 리스트 체크:::"+request.getAttribute("lookuplist"));
				
				
				System.out.println("mainAction 출력 : "+request.getAttribute("list"));
				
				//날짜 체크해서 현재 날짜와 다르면 그전에 저장된 조회수를 lookup table 에 옮긴후 다시 1부터 시작
				
				Date day = new Date();
				 SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
				 SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
				 String today = date.format(day);
				 
				 System.out.println(today+"::오늘날짜 check");
				 
				 map = productService.getMainList();
				List<Product> list = (List<Product>)map.get("list");
				for (int i = 0; i < list.size(); i++) {
					if(list.get(i).getToday()!=today) {
						Product product = list.get(i);
						productService.daycheck(today,product);
						
					}
				}
			    
				
				
			    HttpSession session = request.getSession(false);
			    User user = (User)session.getAttribute("user");
			    if(user== null) {
			    	user = new User();
			    	user.setRole("user");
			    }
			    
			    System.out.println(user.getRole()+"::::adminCheck");
			    //차트랑 조회수 구분
			    String start = request.getParameter("start");
			    
			    System.out.println(start+":::start체크");
			    
			    if(start!=null && start.equals("yes")) {
			    request.setAttribute("start", start);
			    }else {
			    request.setAttribute("start", start);
			    }
			    
			    System.out.println(request.getAttribute("start")+":::start체크2");
			    
			    
			    ///차트랑 리스트 날짜 별 조회
			    System.out.println(request.getParameter("manuDate")+"::::날짜 체크!!!");
			    request.setAttribute("manuDate", request.getParameter(date.format(today)));
			  
			    
			    //접속한 client ip systemlog io로 txt에 입력하기
		  		  System.out.println(request.getRemoteAddr()+"::접속한 클라이언트 IP 정보");
		  	      String ip = request.getRemoteAddr();
		  		
		  	      BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\workspace\\06.Model2MVCShop(Presentation+BusinessLogic)\\IPlog\\IpLog.txt",true));
		  	      //true 쓰면 기존 파일내용에 뒤에 추가되는 내용이 append된다. default가 false이므로 써준것
		  	      
		  	      String logday = date.format(today)+"-"+time.format(today);
		  	      
		  	      String ipLog = ip+"날짜-시간:"+logday+"//접속ID:"+user.getUserId();
		  	      
		  	      /*bw.write(ipLog);*/
		  	      bw.newLine();
		  	      bw.flush();
		  	      
		  	      bw.close();
			
			System.out.println("===========mainlist==========end");			

			return "forward:/main/mainView.jsp";
		}
}