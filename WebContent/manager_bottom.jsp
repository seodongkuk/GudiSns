<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head></head>
        <meta charset="UTF-8">
        <style>
            div.footer{
                text-align: center;
                width: 580px;
                height: 500px;
                min-width: 721px;                
                position: fixed;
                top: 450px;
            }
            a>li{
                float: left;
                padding: 0px 20px;
                color: black;
                font-size: 120%;
                font-weight: 600;
                width: 185px;
                border: 1px solid black;
                border-collapse: collapse;
            }
            ul{
                list-style-type: none;
                min-width: 721px;
                display: inline;
            }
            a:link{
                color: black;
                text-decoration: none;
            }        
            li:hover{
                background-color: yellow;
            }
    </style>
    </head>
    <body>
        <div class="footer">
            <ul>
                <td>
                <input type="button" value="블라인드 리스트" onclick="location.href='manager_blind.jsp'">
                </td>
   				<td>
                <input type="button" value="신고 리스트" onclick="location.href='manager_bottom.jsp'">
                </td>
                <td>
                <input type="button" value="블랙 리스트" onclick="location.href='manager_black.jsp'">
              	</td>
             </ul>
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