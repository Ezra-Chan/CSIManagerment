package com.jit.cx.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jit.cx.domain.Employee;
import com.jit.cx.domain.User;
import com.jit.cx.service.RainService;
import com.jit.cx.util.GetToken;
import com.jit.cx.util.GsonUtils;
import com.jit.cx.util.HttpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class FaceController {
	@Autowired
	private RainService rainService;
	/**
	 * 
	 * @return人脸识别界面
	 */
	@RequestMapping("/faceSearch.html")
	 public ModelAndView accountManageJsp() {
		 ModelAndView mv = new ModelAndView();
		 mv.addObject("faceSearch");
		 return mv;
	  }
	
	/**
	 * 人脸识别
	 * @param request
	 * @return 可自行将String改为JSONObject
	 * @throws Exception 
	 */
	@RequestMapping("/face.ajax")
	@ResponseBody
	public int face(HttpServletRequest request) throws Exception {
		System.out.println("faceAjax进来了");
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/search";//请求的url,可以查看官方文档查看不同请求的url

        Map<String, Object> map = new HashMap<>();
        map.put("image", request.getParameter("base"));//获取前台的人脸识别后发送的base64
        map.put("group_id_list", "EasyBuy,face");//之前创建的人脸库，可以在百度云的管理控制台查看用户组
        map.put("image_type", "BASE64");//照片类型为base64
		
        String param = GsonUtils.toJson(map);

        // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
        String accessToken = GetToken.getAuth();

        String result = HttpUtil.post(url, accessToken, "application/json", param);
        System.out.println("识别结果为："+result);

        
        JSONObject jsonObject = (JSONObject) JSON.parse(result);
        JSONObject object = (JSONObject) jsonObject.get("result");
        JSONArray string = (JSONArray) object.get("user_list");
        JSONObject ob = (JSONObject) string.get(0);
        BigDecimal valueOf = (BigDecimal) ob.get("score");
        String group = (String) ob.get("group_id");
        String str = (String) ob.get("user_id");
		int u_id = Integer.parseInt(str);
		HttpSession session = request.getSession();
		session.setAttribute("user_id", u_id);
		session.setAttribute("group_id",group);
		System.out.println(session.getAttribute("user_id"));
		System.out.println(session.getAttribute("group_id"));
        if(valueOf.doubleValue() > 75) {
	    	System.out.println("识别相似度大于75分");
	    	return u_id;
	    }else{
	    	System.out.println("识别相似度小于75分");
			return 0;
		}
	}

	@RequestMapping("/facelogin")
	public String facelogin(HttpServletRequest request){
		//Integer id = Integer.parseInt(resp);
		String tip;
		System.out.println("进来了");
		HttpSession session = request.getSession();
		String group = (String)session.getAttribute("group_id");
		System.out.println(group);
		Integer id = (Integer) session.getAttribute("user_id");
		System.out.println(id);
		if(group.equals("EasyBuy")){
			tip = "1";
			User user = rainService.get_UserInfo(id);
			session.setAttribute("user_session", user);
			session.setAttribute("tip",tip);
			session.setMaxInactiveInterval(24 * 60 * 60);
			System.out.println(user);
			return "index";
		}else if (group.equals("face")){
			tip = "2";
			Employee employee = rainService.get_EmployeeInfo(id);
			session.setAttribute("user_session", employee);
			session.setAttribute("tip",tip);
			session.setMaxInactiveInterval(24 * 60 * 60);
			System.out.println(employee);
			return "indexcustomer";
		}else{
			System.out.println("获取组ID错误！");
			return "/";
		}
	}

	@RequestMapping("/faceadd")
	public String faceAdd(){
		return "faceAdd";
	}


	@RequestMapping("/uploadFace")
	@ResponseBody
	public int upLoadFace(HttpServletRequest request) throws Exception{
		int id;
		// 请求url
		String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
		HttpSession session = request.getSession();
		String tip= (String) session.getAttribute("tip");//通过tip获取角色为管理员还是用户
		if(tip=="1") {
			User user = (User) session.getAttribute("user_session");
			id= user.getId();
			System.out.println(user);
			try {
				Map<String, Object> map = new HashMap<>();
				map.put("image", request.getParameter("base"));
				map.put("group_id", "EasyBuy");
				map.put("user_id", id);
				map.put("user_info", "");
				map.put("liveness_control", "NORMAL");
				map.put("image_type", "BASE64");
				map.put("quality_control", "LOW");

				String param = GsonUtils.toJson(map);

				// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
				String accessToken = GetToken.getAuth();

				String result = HttpUtil.post(url, accessToken, "application/json", param);
				System.out.println(result);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else {
			Employee employee= (Employee) session.getAttribute("user_session");
			id=employee.getId();
			System.out.println(employee);
			try {
				Map<String, Object> map = new HashMap<>();
				map.put("image", request.getParameter("base"));
				map.put("group_id", "face");
				map.put("user_id", id);
				map.put("user_info", "");
				map.put("liveness_control", "NORMAL");
				map.put("image_type", "BASE64");
				map.put("quality_control", "LOW");

				String param = GsonUtils.toJson(map);

				// 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
				String accessToken = GetToken.getAuth();

				String result = HttpUtil.post(url, accessToken, "application/json", param);
				System.out.println(result);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return 1;
	}
}
