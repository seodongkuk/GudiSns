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
                width: 830px;
                margin-left:20%;
                padding: 50px;
                border: 1px solid black;
            }
            h2,h3{
                text-align: center;
            }
            .find{
                display: block;
                padding-left: 30%;
            }
            ol{
                background-color: lightgrey;
                width: 400px;
                margin-left: 23%;   
            }
            ol li{
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
        <h3>오늘의 검색 해시태그</h3>
        <ol>
            <li>코로나</li>
            <li>봄</li>
            <li>개강</li>
        </ol>
    <div>
        <!-- <iframe id="footer" src="navi.jsp" style="border: none;bottom: 0; left: 350; position: fixed; z-index: 5; top: 850; width: 100%;"></iframe> -->
        <iframe id="footer" src="navi.jsp" width="100%" height="500px" scrolling="no" frameborder="0"></iframe>
    </div>
	</body>
	<script>
		//Service에서 지정한 msg 값을 alert로 View
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
</html>