<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
   <head>
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
    	<div class="bar">
    	 <ul>
                <a href="./blind_list" target="_parent">
                     <li>블라인드 리스트</li>
                 </a>
                 <a href="./report_list" target="_parent">
                     <li>신고접수 리스트</li>
                 </a>
                 <a href="./black_list" target="_parent">
                     <li>블랙 리스트</li>
                 </a>
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