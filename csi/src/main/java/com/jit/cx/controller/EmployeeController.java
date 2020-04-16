package com.jit.cx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.cx.domain.Dept;
import com.jit.cx.domain.Employee;
import com.jit.cx.domain.Job;
import com.jit.cx.service.RainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {
	@Autowired
	@Qualifier("RainService")
	private RainService rainservice;
	// 如果在目录下输入为空，则跳转到指定链接
		@RequestMapping(value="/employee/")
		 public ModelAndView index2(ModelAndView mv){
			mv.setViewName("employee/list");
			return mv;
		}
		// 如果在目录下输入任何不存在的参数，则跳转到list
		@RequestMapping(value="/employee/{formName}")
		 public String index2(@PathVariable String formName){
			String blank = "/employee/list";
			return blank;
		}
		@RequestMapping(value="/employee/list",method=RequestMethod.GET)
		 public String index(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model, String content){
			PageHelper.startPage(pn, 5);
			List<Employee> employee_list = rainservice.get_EmployeeList();
			if (content!=null){
				employee_list = rainservice.get_EmployeeLikeList(content);
			}
			PageInfo page = new PageInfo(employee_list,5);
			model.addAttribute("pageInfo", page);
			model.addAttribute("list",employee_list);
			return "employee/list";
		}
		@RequestMapping(value="/employee/add",method=RequestMethod.GET)
		 public String add(Model model,Integer id){
			if(id!=null){
				Employee employee = rainservice.get_EmployeeInfo(id);
				model.addAttribute("job",employee);
			}
			List<Dept> dept_list = rainservice.findAllDept();
			List<Job> job_list = rainservice.findAllJob();
			model.addAttribute("job_list", job_list);
			model.addAttribute("dept_list",dept_list);
			return "/employee/add";
		}
		@RequestMapping(value="/employee/add",method=RequestMethod.POST)
		 public ModelAndView add(ModelAndView mv,@ModelAttribute Employee job ,Integer id){
//			System.out.println(id);
			if(id!=null){
				rainservice.update_EmployeeInfo(job);
			}else{
				rainservice.insert_EmployeeInfo(job);
			}
			mv.setViewName("redirect:/employee/list");
			return mv;
		}
		@RequestMapping(value="/employee/delete",method=RequestMethod.GET)
		 public void delete(Integer id){
//			System.out.println(id);
			if(id!=null){
				rainservice.delete_EmployeeInfo(id);
			}
		}
}
