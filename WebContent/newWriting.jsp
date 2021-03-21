<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" href="imgfile\icon.jpg">
    <title>new_Writing</title>
    <script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
    <style>
        body{
            margin-left: 31%;
        }
        #newWritebox{
            border: 1px solid black;
            text-align: center;
            width: 700px;
            height:800px;
        }
        .hash{
            position: relative;
            margin-top: 30px;
            left: -42px;
            width:100px;
            height:15px;
            
        }
        h2{
            font-size: 3.125em;
            margin-bottom: 100px;
        }
        textarea {
            width: 300px;
            height: 170px;
            resize: none;
        }
        .filebox input[type="file"] {
            position: absolute;
            width: 0;
            height: 0;
            padding: 0;
            overflow: hidden;
            border: 0;
        }

        .filebox label {
            position: relative;
            top: 30px;
            left: 120px;
            display: inline-block;
            padding: 10px 20px;
            color: #999;
            vertical-align: middle;
            background-color: #fdfdfd;
            cursor: pointer;
            border: 1px solid #ebebeb;
            border-radius: 5px;
        }

        /* 파일 얿로드 버튼 */
        .filebox .upload_name {
            display: inline-block;
            position: relative;
            top: 30px;
            left: -128px;
            height: 35px;
            font-size: 10px;
            padding: 0 10px;
            vertical-align: middle;
            background-color: #f5f5f5;
            border: 1px solid #ebebeb;
            border-radius: 5px;

        }
        #selectBox{
            position: relative;
            top: 60px;
            left:-115px;
        }
        button{
            position: relative;
            top: 190px;
            width:110px;
            height:30px

        }
    </style>
</head>

<body>
    <div id="newWritebox">
        <h2>새 글쓰기</h2>
        <form action="newWrite" id="newWrite" class="filebox" method="post" enctype="multipart/form-data" name="fomvl">
        <!-- 이미지가 서버에들어갈것 enc mult -->
        	<input type="hidden" name="user_id" value="${sessionScope.loginId}"/>
            <textarea name="textArea" id="text" cols="40" rows="10" maxlength="1000" placeholder="글을작성해주세요"></textarea><br>
            <input type="text" name="hashOne" class="hash" placeholder="#을꼭입력후 글작성해주세요" value="#">
            <input type="text" name="hashTwo" class="hash" placeholder="#을꼭입력후 글작성해주세요" value="#">
            <!-- 추가 삭제는 자바사크립트로  -->
            <br>
            <label for="file">업로드</label>
            <input type="file" id="file" name="photo">
            <input class="upload_name" value="파일을 넣어주세요">
            <br>
            <select name="release_state" id="selectBox">
                <option value="001">전체공개</option>
                <option value="002">친구공개</option>
                <option value="003">나만보기</option>
            </select>
        </form>
        <button type="button" onclick="chkValue()">작성완료</button>
        <button type="button" onclick="location.href='flist'">취소</button>
        <!-- 자바스크립트로 메인으로 보내기 !-->
    </div>
</body>
<script>
    $(document).ready(function () {
        var fileTarget = $('#file');
        fileTarget.on('change', function () { // 값이 변경되면
            var cur = $(".filebox input[type='file']").val();
            $(".upload_name").val(cur);
        });
    });
    
    var msg = "${msg}";
	if(msg!=""){
		alert(msg);
		location.href='flist'; 
	} 
	
	
	function chkValue(){
        var tmp = document.fomvl.textArea.value.replace(/\s|　/gi, '');
       
        if(tmp == ''){
            alert('내용을 입력해 주세요');
            return false;
        }else{
        	document.getElementById("newWrite").submit();
        }
        
    }
</script>
</html>