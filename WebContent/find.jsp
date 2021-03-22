<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<!-- 제이쿼리 사용을 위해 선언 -->
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            body{
                width: 850px;
                height: 1032px;
                margin-left:25%;
                padding: 5px;
                border: 1px solid black;
                overflow: hidden;
            }
            h2,h3{
                text-align: center;
            }
            .find{
                display: block;
                padding-left: 30%;
            }
            ul{
                background-color: lightgray;
                width: 400px;
                margin-left: 23%;   
                list-style: none;
            }
            ul li{
                text-align: center;
                padding-top: 20px;
                padding-bottom: 20px;
            }
        </style>
	</head>
	<body>
		<h2>검색</h2>
	    <div class="find">
	        <form action="find" method="get">
	            <select name="find">
	                <option value="none">===선택===</option>
	                <option value="HashTag">해시태그</option>
	                <option value="User">유저</option>
	            </select>
	            <input name="search" type="text" placeholder="검색어를 입력해주세요.">
	            <button>검색</button>
	        </form>
	    </div>
	    <br/>
       <h3>오늘의 해시태그 Top3</h3>
       <div style="height: 70%">
	        <c:forEach items="${tag}" var="HashTag2">
		        <ul>
		            <li>${HashTag2.rnum}.  ${HashTag2.hashTag}</li>
		        </ul>
	    	</c:forEach>
       </div>
	        <iframe src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>
	</body>
	<script>
		//Service에서 지정한 msg 값을 alert로 View
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
</html>