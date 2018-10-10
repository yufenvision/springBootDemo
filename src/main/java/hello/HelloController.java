package hello;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	
	@RequestMapping("/")
	public String hello(){
		return "开始SpringBoot项目";
	}
	@RequestMapping("/list")
	@ResponseBody
	public List<String> list(){
		List<String> strs = new ArrayList<>();
		int num=5;
		while(num>0){
			strs.add(String.valueOf((Math.ceil(Math.random()*5))));
			num--;
		}
		
		return strs;
	}
}
