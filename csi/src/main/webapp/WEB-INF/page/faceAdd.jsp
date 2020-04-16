<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>人脸认证</title>
    <link href="${ctx}/public/css/font.css" rel="stylesheet">
    <link href="${ctx}/public/css/xadmin.css" rel="stylesheet">
    <link href="${ctx}/public/css/faceControl.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/public/js/jquery.min.js"></script>
    <script src="${ctx}/public/lib/layui/layui.js" charset="utf-8"></script>
    <script src="${ctx}/public/js/xadmin.js" type="text/javascript" ></script>
    <style type="text/css">body{text-align:center;}</style>
</head>
<body class="login-bg">

<div class="login layui-anim layui-anim-up">
    <div class="message">CSI-员工之家管理系统-人脸识别注册</div>
    <div>
        <video id="video" width="300px" height="400px" autoplay></video>
        <canvas id="canvas" width="300px" height="400px" ></canvas>

        <input type="button" onclick="add()" value="点击注册" />
        <hr class="hr15">
        <input type="button" onclick="histroy()" value="返回主页" />
    </div>
</div>
<script type="text/javascript">
    var video = document.getElementById("video");
    var context = canvas.getContext("2d");
    var con  ={
        audio:false,
        video:{
            width:1980,
            height:1024,
        }
    };
    navigator.mediaDevices.getUserMedia(con).then(function(stream){
        video.srcObject = stream;
        video.onloadmetadate = function(e){
            video.play();
        }
    });

    //获取人脸照片的base64，用于发送给后台进行识别
    function getBase64() {
        var imgSrc = document.getElementById("canvas").toDataURL("image/png");
        return imgSrc.split("base64,")[1];
    };

    function add() {
        context.drawImage(video,0,0,400,300);//把流媒体数据画到convas画布上
        var base = getBase64();
        $.ajax({
            url:"${ctx}/uploadFace",
            type:"POST",
            dataType:"json",
            data:{base:base},
            success:function(resp){
                if (resp !== 0) {
                    alert("认证成功");
                    window.location.href = "${ctx}/user/jump";
                } else {
                    alert("认证失败！请重新认证！");
                    window.location.reload();
                }
            }
        });
    }
    function  histroy() {
        top.location.href="/";
    }
</script>
</body>
</html>
