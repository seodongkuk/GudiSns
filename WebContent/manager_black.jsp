<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>블랙리스트</title>
        <!-- favicon:사이트를 대표하는 탭창에 보여지는 이미지 -->
        <link rel="icon" href="icon.jpg">
        <style>
            body{
                width: 700px;
                margin: 20px 550px;
                padding: 50px;
                text-align: center;
                border: 1px solid black;
            }
            table{
                width: 650px;
                height: 100px;
                margin: auto;
                text-align: center;
            }
            table,td,th{                
                border: 1px solid black;
                border-collapse: collapse;
            } 
            th{
                background-color: rgba(238, 178, 66, 0.596);
            }
            th, td{
                padding: 10px;
            }
            .paging{
                margin-top: 40px;
            }
            p{
                font-weight: 600;
            }
        </style>
    </head>
    <body>
        <h1>관리자 모드</h1>
        <h3>블랙 리스트</h3>
        <p>유저 <input type="text"> <button>검색</button></p>
        <table>
            <tr>
                <th>프로필</th>
                <th>블랙리스트</th>
                <th>처리한 관리자</th>
                <th>상태</th>
            </tr>
            <tr>
                <td><img src="#"/></td>
                <td>NO: 아이디: 등록날짜: </td>
                <td></td>
                <td><a href="#">해제</a></td>
            </tr>
        </table>    
        <div class="paging">
            <button>이전</button>
            <button>1</button>
            <button>다음</button>
        </div>    
        <iframe src="manager_bottom.jsp" width="100%" height="500px" scrolling="no" frameborder="0"></iframe>
    </body>
    	<script>
		//Service에서 지정한 msg 값을 alert로 View
		var msg = "${msg}";
		if(msg != ""){
			alert(msg);
		}
	</script>
    
</html>
