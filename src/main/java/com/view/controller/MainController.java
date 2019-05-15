package com.view.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@RestController
@Controller
//@ComponentScan({"com.example.demo"})
//@MapperScan("com.example.demo.mapper")
public class MainController {
	
	@RequestMapping("index")
//	@ResponseBody
	public ModelAndView test() {
		System.out.println("第一个Spring boot");
		ModelAndView mav = new ModelAndView("/index");
		List<Map<String, String>> list = new ArrayList<>();
		Map<String, String> json = new HashMap<String, String>();
		json.put("appName", "应用名");
		json.put("appID", "应用ID");
		list.add(json);
		mav.addObject("list",list);
		return mav;
	}

}
