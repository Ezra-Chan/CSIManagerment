package com.jit.cx.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jit.cx.domain.Document;
import com.jit.cx.domain.User;
import com.jit.cx.service.RainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

@Controller
public class DocumentController {
	@Autowired
	@Qualifier("RainService")
	private RainService rainservice;


	// 如果在目录下输入为空，则跳转到指定链接
		@RequestMapping(value="/document/")
		 public ModelAndView index2(ModelAndView mv){
			mv.setViewName("document/list");
			return mv;
		}
		// 如果在目录下输入任何不存在的参数，则跳转到list
		@RequestMapping(value="/document/{formName}")
		 public String index2(@PathVariable String formName){
			String blank = "/document/list";
			return blank;
		}
		@RequestMapping(value="/document/list",method=RequestMethod.GET)
		 public String index(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model,String content){
			PageHelper.startPage(pn, 5);
			List<Document> job_list = rainservice.get_DocumentList();
			if (content!=null){
				job_list = rainservice.get_DocumentLikeList(content);
			}
			PageInfo page = new PageInfo(job_list,5);
			model.addAttribute("pageInfo", page);
			model.addAttribute("list",job_list);
			return "document/list";
		}

	@RequestMapping(value="/document/add",method=RequestMethod.GET)
	public String add(Model model,Integer id){
		if(id!=null){
			Document job = rainservice.get_DocumentInfo(id);
			model.addAttribute("job",job);
		}
		return "/document/add";
	}
	@RequestMapping(value="/document/add",method=RequestMethod.POST)
	public ModelAndView add(ModelAndView mv,@ModelAttribute Document document ,Integer id,HttpSession session,HttpServletRequest request) throws Exception {
		User user = (User) session.getAttribute("user_session");
		document.setUser_id(user.getId());
		System.out.println(id);
		// 上传文件路径

		String path = request.getSession().getServletContext().getRealPath("/uploads/");
		System.out.println(path);
		// 上传文件名
		String fileName = document.getFile().getOriginalFilename();
		// 将上传文件保存到一个目标文件当中
		document.getFile().transferTo(new File(path + File.separator + fileName));
		// 插入数据库
		// 设置fileName
		document.setFilename(fileName);
		// 设置关联的User对象
//				User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
//				document.setUser(user);
		// 插入数据库
		rainservice.insert_DocumentInfo(document);
		mv.setViewName("redirect:/document/list");
		return mv;
	}

	@RequestMapping(value="/document/download",method=RequestMethod.GET)
	public void download(@RequestParam(value="filename")String filename,
						 HttpServletRequest request,
						 HttpServletResponse response) throws IOException {
		//模拟文件，myfile.txt为需要下载的文件
		//  String path = request.getSession().getServletContext().getRealPath("statics\\upload")+"\\"+filename;
		String path = request.getSession().getServletContext().getRealPath("/uploads/")+"\\"+filename;
		//获取输入流
		InputStream bis = new BufferedInputStream(new FileInputStream(new File(path)));
		//转码，免得文件名中文乱码
		filename = URLEncoder.encode(filename,"UTF-8");
		//设置文件下载头
		response.addHeader("Content-Disposition", "attachment;filename=" + filename);
		//1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
		response.setContentType("multipart/form-data");
		BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
		int len = 0;
		while((len = bis.read()) != -1){
			out.write(len);
			out.flush();
		}
		out.close();
	}
/*
	@RequestMapping(value="/document/update",method=RequestMethod.POST)
	@ResponseBody
	public Document update(Document document){
		System.out.println("修改进来");
		System.out.println(document);
		rainservice.update_DocumentInfo(document);
		System.out.println(document);
		//session.setAttribute(Constants.USER_SESSION, user);
		//return "forward:/user/list";
		return document;
	}*/

		/*@RequestMapping(value="/document/add",method=RequestMethod.GET)
		 public String add(Model model,Integer id){
			if(id!=null){
				Document job = rainservice.get_DocumentInfo(id);
				model.addAttribute("job",job);
			}
			return "/document/add";
		}
		@RequestMapping(value="/document/add",method=RequestMethod.POST)
		 public ModelAndView add(ModelAndView mv, @ModelAttribute Document document , Integer id, HttpSession session, HttpServletRequest request) throws Exception{
			System.out.println(id);
			if(id!=null){
				rainservice.update_DocumentInfo(document);
			}else{
				*//**
				 * 上传文件
				 *//*
				User user = (User) session.getAttribute("user_session");
				document.setUser_id(user.getId());
				String path = request.getSession().getServletContext().getRealPath("/uploads/");
				String filename = document.getFile().getOriginalFilename();
				File tempFile = new File(path+File.separator+filename);
				tempFile.createNewFile();
				document.getFile().transferTo(tempFile);
				document.setFilename(filename);
				rainservice.insert_DocumentInfo(document);
			}
			mv.setViewName("redirect:/document/list");
			return mv;
		}*/
		@RequestMapping(value="/document/delete",method=RequestMethod.GET)
		 public void delete(Integer id){
			System.out.println(id);
			if(id!=null){
				rainservice.delete_DocumentInfo(id);
			}
		}
}
