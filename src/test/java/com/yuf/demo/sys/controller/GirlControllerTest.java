package com.yuf.demo.sys.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GirlControllerTest {
	
	@Autowired
    private WebApplicationContext wac;
	
	private MockMvc mvc;
	
    @Before
    public void  setup(){
    	mvc= MockMvcBuilders.webAppContextSetup(wac).build();
    }
	@Test
	public void girlList() throws Exception{
		mvc.perform(MockMvcRequestBuilders.get("/girls"))
		.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
}	
