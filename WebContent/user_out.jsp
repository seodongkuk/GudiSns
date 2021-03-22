<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
    <head>
     <script src="//code.jquery.com/jquery.min.js"></script>
        <meta charset="UTF-8">
        <title>회원탈퇴</title>
        <!-- favicon:사이트를 대표하는 탭창에 보여지는 이미지 -->
        <link rel="icon" href="icon.jpg">
        <style>
           body{
                width: 650px;   
                margin: 20px 400px;
                padding: 50px;
                text-align: center;
                border: 1px solid black;
            }
            h1{
                font-size: 50px;
            }
            h3{
                margin-top: 80px;
                color: red;
            }
            h4{
                margin-top: 80px;
            }
            p{
                font-weight: 600;
            }
            button{
                font-size: large;
                margin-top: 50px;
                padding: 20px;
            }
        </style>
    </head>
    <form action="memberdel" method="post">
	<input type="hidden" name="id" value="${sessionScope.id}"/>
	
    <body>
        <h1>회원 탈퇴</h1>
        <h3>
            
        </h3>
        <h4>탈퇴를 위해 고객님의 비밀번호를 입력해주세요.</h4>
        <p>PW <input type="password" id="userPW" placeholder="비밀번호를 입력하세요."/><br/></p>
          <button type="submit">회원탈퇴</button>
        </form>
    </body>
   
   <script>
   $(function(){
	   $('memberdel').click (function(){
		   if(result==true){
			   return true;
		   }else{
			   return false;
		   }
	   });
   });
   
   </script>
</html>