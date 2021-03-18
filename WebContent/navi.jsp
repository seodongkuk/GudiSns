<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style>
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
        width: 580px;
        height: 500px;
        min-width: 845px;                
        position: fixed;
        top: 630px;
        left: 15px;
    }
</style>
<div class="bar">
    <ul>
       <a href="main" target="_parent">
            <li>메인</li>
        </a>
        <a href="find.jsp" target="_parent">
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