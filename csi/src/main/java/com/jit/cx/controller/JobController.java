package com.jit.cx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
public class JobController {
	@Autowired
	@Qualifier("RainService")
	private RainService rainservice;
	// 如果在目录下输入为空，则跳转到指定链接
		@RequestMapping(value="/job/")
		 public ModelAndView index2(ModelAndView mv){
			mv.setViewName("job/list");
			return mv;
		}
		// 如果在目录下输入任何不存在的参数，则跳转到list
		@RequestMapping(value="/job/{formName}")
		 public String index2(@PathVariable String formName){
			String blank = "/job/list";
			return blank;
		}
		@RequestMapping(value="/job/list",method=RequestMethod.GET)
		 public String index(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model, String content){
			PageHelper.startPage(pn, 5);
			List<Job> job_list = rainservice.findAllJob();
			if (content!=null){
				job_list = rainservice.findAllJob(content);
			}
			PageInfo page = new PageInfo(job_list,5);
			model.addAttribute("pageInfo", page);
			model.addAttribute("list",job_list);
			return "job/list";
		}
		@RequestMapping(value="/job/add",method=RequestMethod.GET)
		 public String add(Model model,Integer id){
			if(id!=null){
				Job job = rainservice.get_JobInfo(id);
				model.addAttribute("job",job);
			}
			return "/job/add";
		}
		@RequestMapping(value="/job/add",method=RequestMethod.POST)
		 public ModelAndView add(ModelAndView mv,@ModelAttribute Job job ,Integer id){
			System.out.println(id);
			if(id!=null){
				rainservice.update_JobInfo(job);
			}else{
				rainservice.insert_JobInfo(job);
			}
			mv.setViewName("redirect:/job/list");
			return mv;
		}
		@RequestMapping(value="/job/delete",method=RequestMethod.GET)
		 public void delete(Integer id){
			System.out.println(id);
			if(id!=null){
				rainservice.delete_JobInfo(id);
			}
		}
}
