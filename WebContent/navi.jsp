<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<style>
    a>li{
        float: left;
        padding: 0px 20px;
        color: black;
        font-size: 120%;
        font-weight: 600;
        width: 110px;
        height: 30px;
        text-align: center;
        border: 1px solid black;
                border-collapse: collapse;
    }
    ul{
        list-style-type: none;
        min-width: 650px;
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
        width: 100%;
        height: 30px;
       

        min-width: 700px;
    }
</style>
<div class="bar">
    <ul>
       <a href="#">
            <li>메인</li>
        </a>
        <a href="find.jsp" target="_blank">
            <li>검색</li>
        </a>
        <a href="chatRoom?id=${id}">
            <li>DM</li>
        </a>
        <a href="#">
            <li>알림</li>
        </a>
        <a href="#">
            <li>내프로필</li>
        </a>
    </ul>
</div>