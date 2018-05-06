package com.model2.mvc.service.product.test;




import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.model2.mvc.service.product.ProductService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml" })
public class ProductServiceTest {
	
	/*ApplicationContext context = new ClassPathXmlApplicationContext(
			new String[] {"/config/commonservice.xml","/config/productservice.xml"}
			);
	ProductService productService = (ProductService)context.getBean("productServiceImpl");*/
	
	@Autowired
	@Qualifier("productServiceImpl")
	
	private ProductService productService;
	
	
	//@Test
	public void testAddProduct() throws Exception{
		
		Product product = new Product();
		
		product.setProdNo(123);
		product.setProdName("test");
		product.setProdDetail("test123");
		product.setManuDate("180425");
		product.setPrice(10000);
		product.setFileName("testfile");
		/*product.setRegDate();*/
		/*product.setLookup();*/
		/**/
		product.setTotalEA(100);
		
		/*System.out.println(product+":::입력된거 출력해보자");*/
		productService.addProduct(product);
		System.out.println("insert완료=======");
	/*	product = productService.getProduct(product.getProdNo());
		
		System.out.println("받아온거 출력해보자::"+product);
		
		Assert.assertEquals(123, product.getProdNo());
		Assert.assertEquals("test", product.getProdName());
		Assert.assertEquals("test123", product.getProdDetail());
		Assert.assertEquals("180425", product.getManuDate());
		Assert.assertEquals("testfile", product.getFileName());
		Assert.assertEquals(100, product.getnEA());*/
	}
	
	//@Test
	public void testGetProduct() throws Exception {
		
		Product product = productService.getProduct(10035);
		
		System.out.println(product+":::GET입력된거 출력해보자");
		product = productService.getProduct(product.getProdNo());

		//==> console 확인
		//System.out.println(user);
		
		//==> API 확인
		/*Assert.assertEquals(123, product.getProdNo());
		Assert.assertEquals("test", product.getProdName());
		Assert.assertEquals("test123", product.getProdDetail());
		Assert.assertEquals("180425", product.getManuDate());
		Assert.assertEquals("testfile", product.getFileName());
		Assert.assertEquals(100, product.getnEA());

		Assert.assertNotNull(productService.getProduct(10031));*/
		Assert.assertNotNull(productService.getProduct(10035));
	}
	
	// @Test
	 public void testUpdateUser() throws Exception{
		
			Product product = productService.getProduct(123);
			
			Assert.assertNotNull(product);
			
			
			
			
			Assert.assertEquals(123, product.getProdNo());
			Assert.assertEquals("test10", product.getProdName());
			Assert.assertEquals("test123", product.getProdDetail());
			Assert.assertEquals("180425", product.getManuDate());
			Assert.assertEquals("testfile", product.getFileName());
			Assert.assertEquals(100, product.getnEA());
			
			
			
			product.setProdNo(123);
			product.setProdName("test10");
			product.setProdDetail("test123");
			product.setManuDate("180425");
			product.setPrice(4000);
			product.setFileName("testfile");
			
			
			
			productService.updateProduct(product);
			
			System.out.println("====업데이트완료::");
			
			product = productService.getProduct(product.getProdNo());
			System.out.println("===================48::"+product);
			Assert.assertNotNull(product);
			
			//==> console 확인
			//System.out.println(user);
				
			//==> API 확인
			Assert.assertEquals(123, product.getProdNo());
			Assert.assertEquals("test10", product.getProdName());
			Assert.assertEquals("test123", product.getProdDetail());
			Assert.assertEquals("180425", product.getManuDate());
			Assert.assertEquals("testfile", product.getFileName());
			Assert.assertEquals(100, product.getnEA());
		 }
	 
	 @Test
	 public void testGetProductListAll() throws Exception{
		 
		 	Search search = new Search();
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	Map<String,Object> map = productService.getProductList(search);
		 	
		 	List<Object> list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
			//==> console 확인
		 	//System.out.println(list);
		 	
		 	Integer totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 	
		 	System.out.println("=======================================");
		 	
		 	search.setCurrentPage(1);
		 	search.setPageSize(3);
		 	search.setSearchCondition("0");
		 	search.setSearchKeyword("");
		 	map = productService.getProductList(search);
		 	
		 	list = (List<Object>)map.get("list");
		 	Assert.assertEquals(3, list.size());
		 	
		 	//==> console 확인
		 	//System.out.println(list);
		 	
		 	totalCount = (Integer)map.get("totalCount");
		 	System.out.println(totalCount);
		 }
	
	// @Test
	 public void mainlist() throws Exception{
			
			Product product = new Product();
			
			Map<String, Object> map = productService.getMainList();
			System.out.println("===========mainlist==========start");
			System.out.println(map.get("list"));
			System.out.println("===========mainlist==========end");			

			
		}
	 //@Test
	 public void updatelookup() throws Exception{
		 Date day = new Date();
		 SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		 String today = date.format(day); 
		 
		 Product product = new Product();
		 product.setProdNo(10035);
		 product.setToday(today);
		 
		 productService.updateLookup(product);
	 }
	 
	//@Test
	 public void dayCheck() throws Exception{
			Date day = new Date();
		 SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		 String today = date.format(day);
		 
		 System.out.println(today+"::오늘날짜 check");
		 
		Map<String,Object> map = productService.getMainList();
		List<Product> list = (List<Product>)map.get("list");
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).getToday()!=today) {
				Product product = list.get(i);
				productService.daycheck(today,product);
				
			}
		}
		 
		 
	 }
	 
	 //@Test
	 public void lookupList() throws Exception{
		
		String temp ="20180428"; 
		 
		Date day = new Date();
		SimpleDateFormat date = new SimpleDateFormat("yyyyMMdd");
		String today = date.format(day);
		
		if(temp==today) {
		
		Map<String,Object> map = productService.getLookupList(today);
		
		List<Product> lookuplist= (List<Product>)map.get("list");
		
		
		}else {
		 Map<String,Object> map = productService.getLookupList(temp);
		 List<Product> lookuplist= (List<Product>)map.get("list");
		}
				
		 
	 }
	 
	 //@Test
	 public void updateEA() throws Exception{
					
		/*String sql ="update product set nea=? where prod_Name=?";*/
		
		/*productService.updateEA(sEA, prod);*/
		
		Product product = productService.getProduct(10035);
		int sEA = 10;
		product.setnEA(product.getnEA()-sEA);
		productService.updateEA(product.getnEA(), product);
			
			
		}
	 //@Test
	public void cancelEA() throws Exception{
	
		/*String sql ="update product set nea=? where prod_Name=?";*/
		Product product = productService.getProduct(10035);
		int sEA=11;
		product.setnEA(product.getnEA()+sEA);
		productService.updateEA(product.getnEA(), product);
	}
}
