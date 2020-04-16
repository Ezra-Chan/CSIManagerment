package com.jit.cx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.cx.domain.Dept;
import com.jit.cx.service.RainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class DeptController {
	@Autowired
	@Qualifier("RainService")
	private RainService rainservice;
	
	// 如果在目录下输入为空，则跳转到指定链接
	@RequestMapping(value="/dept/")
	 public ModelAndView index2(ModelAndView mv){
		mv.setViewName("dept/list");
		return mv;
	}
	// 如果在目录下输入任何不存在的参数，则跳转到list
	@RequestMapping(value="/dept/{formName}")
	 public String index2(@PathVariable String formName){
//		return formName;
		String blank = "/dept/list";
		return blank;
	}
	
	@RequestMapping(value="/dept/list",method=RequestMethod.GET)
	 public String index(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model, String content){
		PageHelper.startPage(pn, 5);
		List<Dept> dept_list = rainservice.findAllDept();
		for (Dept dept : dept_list) {
			System.out.println(dept);
		}
		PageInfo page = new PageInfo(dept_list,5);
		model.addAttribute("pageInfo", page);
		model.addAttribute("list",dept_list);
		return "dept/list";
	}
	@RequestMapping(value="/dept/add",method=RequestMethod.GET)
	 public String add(Model model,Integer id){
//		System.out.println(id);
		if(id!=null){
			Dept dept = rainservice.get_Info(id);
			model.addAttribute("dept",dept);
//			System.out.println(dept.getName());
		}
		return "/dept/add";
	}
	@RequestMapping(value="/dept/add",method=RequestMethod.POST)
	 public ModelAndView add(ModelAndView mv,@ModelAttribute Dept dept ,Integer id){
		System.out.println(id);
//		System.out.println(dept.getId());
		if(id!=null){
			rainservice.update_Info(dept);
			System.out.println(dept.getId());
		}else{
			rainservice.addDept(dept);
		}
//		System.out.println(dept.getName());
		mv.setViewName("redirect:/dept/list");
		return mv;
	}
	@RequestMapping(value="/dept/delete",method=RequestMethod.GET)
	 public void delete(Integer id){
		System.out.println(id);
		if(id!=null){
			rainservice.delete_Info(id);
		}
	}
}
