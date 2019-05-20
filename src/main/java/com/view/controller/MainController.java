package com.view.controller;

import com.view.service.RedisHelper;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
//@ComponentScan({"com.example.demo"})
//@MapperScan("com.example.demo.mapper")
public class MainController {
	@Resource
	private RedisHelper redishelper;
	
	@RequestMapping("index")
//	@ResponseBody
	public void test() throws Exception {
		System.out.println("---进来咯--------");
		redishelper.getRedisson();
		System.out.println("第一个Spring boot");
		/*ModelAndView mav = new ModelAndView("/index");
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> json = new HashMap<String, String>();
		json.put("appName", "应用名");
		json.put("appID", "应用ID");
		list.add(json);
		mav.addObject("list",list);
		return mav;*/
	}

}
