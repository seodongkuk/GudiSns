<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<html>
<head>
    <meta charset="utf-8">  
    <style>
        body{
                width: 700px;
                margin-left:20%;
                padding: 50px;
                border: 1px solid black;
         }
       .title{
            text-align: center;
            margin: 0;
            padding-top: 25px;
            padding-bottom: 25px;
        }

        .button button{
            margin-left: 45%;
            font-size: 9px;
            background-color: white;
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
        }
        div{
            margin-bottom: 100px;
        }
        p{
            text-align: center;
            color: red;
        }
        .img{
            display: block;
            margin-top: 10%;
            margin-bottom: 10%;
            margin-left: 45%;
        }
    </style>
</head>
<body> 
    <h2 class='title'>블랙리스트</h2>
    <img class='img' src="C:\Goodee\UI/lock.png" width='80px'>
    <div>
        <p>해당 아이디 <b>user1</b> (은)는</p>
        <p> 정책에 위반하여 </p>
        <p>사이트를 이용하실 수 없습니다.</p>
    </div>
    <br/>
    <div class="button">
        <button onclick="location.href='index.jsp'">돌아가기</button>
    </div>
</body>
</html>