<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>后台登录-CSI-员工之家管理系统</title>
	<link rel="stylesheet" href="${ctx}/public/css/font.css">
	<link rel="stylesheet" href="${ctx}/public/css/xadmin.css">
	<link rel="stylesheet" type="text/css" href="${ctx}/public/css/faceControl.css" />
	<script type="text/javascript" src="${ctx}/public/js/jquery.min.js"></script>
	<script src="${ctx}/public/lib/layui/layui.js" charset="utf-8"></script>
	<script type="text/javascript" src="${ctx}/public/js/xadmin.js"></script>
	<style type="text/css">body{text-align:center;}</style>
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
	<div class="message">CSI-员工之家管理系统-人脸识别登录</div>
	<div>
		<video id="video" width="300px" height="400px" autoplay></video>
		<canvas id="canvas" width="300px" height="400px" ></canvas>

		<input type="button" onclick="query()" value="点击识别" />
		<hr class="hr15">
		<input type="button" onclick="relogin()" value="返回密码登录" />
	</div>
</div>
<script type="text/javascript">

	//获取摄像头对象
	canvas = document.getElementById("canvas");
	context = canvas.getContext("2d");
	//获取视频流
	video = document.getElementById("video");
	var videoObj = {
		"video" : true
	}, errBack = function(error) {
		console.log("Video capture error: ", error.code);
	};
	//初始化摄像头参数
	if (navigator.getUserMedia || navigator.webkitGetUserMedia
			|| navigator.mozGetUserMedia) {
		navigator.getUserMedia = navigator.getUserMedia
				|| navigator.webkitGetUserMedia
				|| navigator.mozGetUserMedia;
		navigator.getUserMedia(videoObj, function(stream) {
			video.srcObject = stream;
			video.play();
		}, errBack);
	}


	//获取人脸照片的base64，用于发送给后台进行识别
	function getBase64() {
		var imgSrc = document.getElementById("canvas").toDataURL("image/png");
		return imgSrc.split("base64,")[1];
	};


	//发送人脸识别的请求
	function query(){
		context.drawImage(video,0,0,400,300);//把流媒体数据画到convas画布上
		var base = getBase64();
		$.ajax({
			url:"${ctx}/face.ajax",
			type:"POST",
			dataType:"json",
			data:{base:base},
			success:function(resp){
				if(resp===0)
				{
					alert("识别失败！");
				}
				else{
					alert("欢迎登录！");
					window.location.href = "${ctx}/facelogin";
					x_admin_close();
				}
			},
			error:function(resp){
				alert("未识别到人脸，请正对摄像头！");
			}
		});
	}
	function relogin(){
		top.location.href="/";
	}
</script>
</body>
</html>