package com.model2.mvc.service.purchase.test;


	import java.util.List;
import java.util.Map;

	import org.junit.Assert;
	import org.junit.Test;
	import org.junit.runner.RunWith;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.beans.factory.annotation.Qualifier;
	import org.springframework.test.context.ContextConfiguration;
	import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

	import com.model2.mvc.common.Search;
	import com.model2.mvc.service.domain.Product;
	import com.model2.mvc.service.domain.Purchase;
	import com.model2.mvc.service.domain.User;
	import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;

	/*
	 *	FileName :  UserServiceTest.java
	 * ㅇ JUnit4 (Test Framework) 과 Spring Framework 통합 Test( Unit Test)
	 * ㅇ Spring 은 JUnit 4를 위한 지원 클래스를 통해 스프링 기반 통합 테스트 코드를 작성 할 수 있다.
	 * ㅇ @RunWith : Meta-data 를 통한 wiring(생성,DI) 할 객체 구현체 지정
	 * ㅇ @ContextConfiguration : Meta-data location 지정
	 * ㅇ @Test : 테스트 실행 소스 지정
	 */
	@RunWith(SpringJUnit4ClassRunner.class)
	@ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
	public class PurchaseServiceTest {

		//==>@RunWith,@ContextConfiguration 이용 Wiring, Test 할 instance DI
		@Autowired
		@Qualifier("purchaseServiceImpl")
		private PurchaseService purchaseService;
		
		/*@Autowired
		@Qualifier("userServiceImpl")		
		private UserService userService;*/

		//@Test
		public void testAddPurchase() throws Exception {
			Product product = new Product();
			product.setProdNo(10105);
			
			User user = new User();
			user.setUserId("user03");
			
			Purchase purchase = new Purchase();
			purchase.setPurchaseProd(product);
			purchase.setBuyer(user);
			
			/*purchase.setBuyer(userService.getUser(user.getUserId()));*/ //이방식 쓰려면 auto wired 해줘야함
			
			/*Assert.assertEquals(1, purchaseService.addPurchase(purchase));*/
		}
		
		@Test
		 public void testGetPurchaseListAll() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = purchaseService.getPurchaseList(search,"user09");
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	for(int i=0;i<list.size();i++){
		 		System.out.println(list.get(i));
		 	}
		 }
		
		//@Test
		public void testUpdatePurchase() throws Exception{
			Purchase purchase = new Purchase();
			purchase.setTranNo(10032);
			
			purchase.setDivyRequest("change");
			System.out.println(purchaseService.updatePurcahse(purchase));
			Assert.assertEquals(1, purchaseService.updatePurcahse(purchase));
		}
		
		//@Test	
		public void testUpdateTranCode() throws Exception{
			Purchase purchase = new Purchase();
			purchase.setTranNo(10036);
			
			purchase.setTranCode("2");
			Assert.assertEquals(1, purchaseService.updateTranCode(purchase));
		}
		
		
		
		/* //@Test
		 public void testGetUserListByUserId() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("10035");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(1, list.size());
		 	
			//==> console 확인
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
		 @Test
		 public void testGetUserListByUserName() throws Exception{
			 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword("보");
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(2, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setSearchCondition("1");
		 	search.setSearchKeyword(""+System.currentTimeMillis());
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(0, list.size());
		 	
			//==> console 확인
		 	System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		
		 }*/
	}