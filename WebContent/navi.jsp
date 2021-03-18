<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<style>
			body{
				height: 30px;
				/* min-height: 100px; */
				/* z-index: -100; */
			}
		    a>li{
		        float: left;
		        padding: 0px 15px;
		        color: black;
		        font-size: 120%;
		        font-weight: 600;
		        width: 125px;
		        height: 30px;
		        text-align: center;
		        border: 1px solid black;
		        border-collapse: collapse;
		    }
		    ul{
		        list-style-type: none;
		        min-width: 800px;
		        display: inline;
		    }
		    a:link{
		        color: black;
		        text-decoration: none;
		    }
		   
		    li:hover{
		        background-color: yellow;
		    }
		    div.bar{
		        text-align: center;
		        width: 850px;
		        min-width: 845px;                
		        position: absolute;
		        top: 18px;
		        left: 25px;
		    }
		</style>
	</head>
	<body>
		<div class="bar">
		    <ul>
		       <a href="main" target="_parent">
		            <li>메인</li>
		        </a>
		        <a href="todayTag" target="_parent">
		            <li>검색</li>
		        </a>
		        <a href="chatRoom?id=${sessionScope.loginId}" target="_parent">
		            <li>DM</li>
		        </a>
		        <a href="#">
		            <li>알림</li>
		        </a>
		        <a href="MyProfile" target="_parent">
		            <li>내프로필</li>
		        </a>
		    </ul>
		</div>
	</body>
</html>