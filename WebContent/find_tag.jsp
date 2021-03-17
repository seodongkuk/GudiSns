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
            /* body{
                width: 700px;
                margin: 20px 550px;
                padding: 50px;
                text-align: center;
                border: 1px solid black;
            } */
            body{
                width: 830px;
                margin-left:20%;
                padding: 50px;
                border: 1px solid black;
            }
            table{
                width: 650px;
                height: 100px;
                margin: auto;
                text-align: center;
            }
            div.find{
            	text-align: center;
            }
           table,td,th{
                border: 1px solid white;
                border-collapse: collapse;
            } 
        </style>
	</head>	       
	    <body>	        
	        <div class="find">
	            <form action="find" method="get">
	                <select name="find">
	                    <option value="none">===선택===</option>
	                    <option value="HashTag" selected>해시태그</option>
	                    <option value="User">유저</option>
	                </select>
	                <input type="text" name="search" placeholder="검색어를 입력해주세요.">
	                <button>검색</button>
	            </form>
	        </div>
	        <c:forEach items="${list}" var="member2">
	            <table style="float: left;margin-top:40px; width: 250px">
	                <tr>
	                    <td rowspan="2"><img src="user.jpg" width="70" height="70"></td>
	                    <td>${member2.user_id}</td>            
	                </tr> 
	            </table>	
	            <table>
	                <tr>
	                    <td><button style="color: red; margin-left: 90%;">신고</button></td>
	                </tr>	
			        <tr>
			            <td>
			                <p>${member2.hashTag}</p>
			                <p>${member2.content}</p>
			            </td>
			        </tr>
			        <tr>
					   <td>
					        <button class="btn-like" style="font-size: 40px;background-color: white;border: none;float: left;">♥</button>
					        <input style="float: left;margin-top: 10px;border: none;"  value="추천수">
					    </td>
					</tr>
					<tr>
	    				<td style="margin:0; padding: 0;";>
	        			<hr>
	    				</td>
					</tr>
					<tr>
					    <td>
					        <p style="font-size: 15px; color: grey;" >댓글 모두 3개입니다.</p>
					        <p>1번댓글</p>
					        <p>2번댓글</p>
					        <p>3번댓글</p>
					    </td>
					</tr>
				</table>
			</c:forEach>
				<iframe id="footer" src="navi.jsp" width="100%" height="500px" scrolling="no" frameborder="0"></iframe>
		</body>
		<script>
		//Service에서 지정한 msg 값을 alert로 View
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
</html>