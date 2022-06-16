package com.bookstore;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.bookstore.controller.HomeController;
import com.bookstore.service.ProductService;

@WebMvcTest(value = HomeController.class)
@WithMockUser
@SpringBootTest
class BookStoreApplicationTests {
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper mapper;
//	@Autowired
//	private MockMvc mockMvc;
//    @MockBean
//    private ProductService productService;
//
//
//
//
//
//    @Test
//	public void retrieveDetailsForCourse() throws Exception {
//
//		Mockito.when(
//				productService.showProductById(Mockito.anyInt())).thenReturn(null);
//
//		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
//				"/home/product/89").accept(
//				MediaType.APPLICATION_JSON);
//
//		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
//		System.out.println(result.getResponse());
//		String expected = "{id:Course1,name:Spring,description:10Steps}";
//
//		// {"id":"Course1","name":"Spring","description":"10 Steps, 25 Examples and 10K Students","steps":["Learn Maven","Import Project","First Example","Second Example"]}
//
//		JSONAssert.assertEquals(expected, result.getResponse()
//				.getContentAsString(), false);
//	}
}
