<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="UTF-8">
        <title>신고접수 리스트</title>
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
            button{
                margin-top: 40px;
            }
            input[type='button']{
                position: absolute;
                left: 1207px;
                top: 80px;
            }
        </style>
    </head>
    <body>
        <h1>관리자 모드</h1>
        <h3>신고접수 리스트</h3>
        <input type="button" onclick="location.href='logout'" value="로그아웃"/>
        <table>
            <tr>
                <th>신고접수 번호</th>
                <th>등록일</th>
                <th>신고사유</th>
                <th>신고자</th>
                <th>블랙</th>
                <th>블라<br/>
                    인드
                </th>
                <th>처리</th>
            </tr>
            <tr>
                <td></td>
                <td></td>
                <td><a href="#">보기</a></td>
                <td></td>
                <td><input type="checkbox" id="ck_blind"></td>
                <td><input type="checkbox" id="ck_black"></td>
                <td><a href="#">처리하기</a></td>
            </tr>
        </table>        
        <button>이전</button>
        <button>1</button>
        <button>다음</button>
        <iframe src="manager_bottom.html" width="100%" height="500px" scrolling="no" frameborder="0"></iframe>
    </body>
</html>