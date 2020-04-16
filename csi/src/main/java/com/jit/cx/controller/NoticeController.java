package com.jit.cx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.cx.domain.Notice;
import com.jit.cx.domain.User;
import com.jit.cx.service.RainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class NoticeController {
	@Autowired
	@Qualifier("RainService")
	private RainService rainservice;
	// 如果在目录下输入为空，则跳转到指定链接
		@RequestMapping(value="/notice/")
		 public ModelAndView index2(ModelAndView mv){
			mv.setViewName("notice/list");
			return mv;
		}
		// 如果在目录下输入任何不存在的参数，则跳转到list
		@RequestMapping(value="/notice/{formName}")
		 public String index2(@PathVariable String formName){
			String blank = "/notice/list";
			return blank;
		}
		@RequestMapping(value="/notice/list",method=RequestMethod.GET)
		 public String index(@RequestParam(value="pn",defaultValue="1")Integer pn, Model model, String content){
			PageHelper.startPage(pn, 5);
			List<Notice> job_list = rainservice.get_NoticeList();
			if (content!=null){
				job_list = rainservice.get_NoticeLikeList(content);
			}
			PageInfo page = new PageInfo(job_list,5);
			model.addAttribute("pageInfo", page);
			model.addAttribute("list",job_list);
			return "notice/list";
		}
		@RequestMapping(value="/notice/add",method=RequestMethod.GET)
		 public String add(Model model,Integer id){
			if(id!=null){
				Notice job = rainservice.get_NoticeInfo(id);
				model.addAttribute("job",job);
			}
			return "/notice/add";
		}
		@RequestMapping(value="/notice/add",method=RequestMethod.POST)
		 public ModelAndView add(ModelAndView mv, @ModelAttribute Notice notice , Integer id, HttpSession session){

			System.out.println(id);
			if(id!=null){
				rainservice.update_NoticeInfo(notice);
			}else{
				User user = (User) session.getAttribute("user_session");
				notice.setUser_id(user.getId());
				rainservice.insert_NoticeInfo(notice);
			}
			mv.setViewName("redirect:/notice/list");
			return mv;
		}
		@RequestMapping(value="/notice/delete",method=RequestMethod.GET)
		 public void delete(Integer id){
			System.out.println(id);
			if(id!=null){
				rainservice.delete_NoticeInfo(id);
			}
		}
}
