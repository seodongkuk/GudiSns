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
            left:-60px;
        }
        button{
            position: relative;
            top: 170px;
            left: 70px;
            width:110px;
            height:30px;

        }
        #sbbutton{
            position: relative;
            top: 200px;
            left: -110px;
        }
        
    </style>
</head>

<body onload="hashyo()">
    <div id="newWritebox">
        <h2>글 수정</h2>
        <form action="edit" id="newWrite" class="filebox" method="post" enctype="multipart/form-data" name="fomvl">
        <input type="hidden" name="board_idx" value="${dto.board_idx}"/>
        
            <textarea name="textArea" id="text" cols="40" rows="10" maxlength="1000">${dto.content}</textarea><br>
            <input type="text" name="hashOne" id="hash1" class="hash" value="">
            <input type="text" name="hashTwo" id="hash2" class="hash" value="">
            <!-- 추가 삭제는 자바사크립트로  -->
            <br>
            <label for="file">업로드</label>
            <input type="file" id="file" name="photo">
            <input class="upload_name" value="${dto.newFileName}">
            
   			
            <!-- {기존 파일 명을 보여줘야함 .수정해야하니까 .. value로 처리하는게 편할듯 }-->
            
            <br>
            <select name="release_state" id="selectBox">
                <%-- <option value="001" <c:if test="${dto.release_state == 1}"> selected="selected"</c:if>>전체공개</option> --%>
                <option value="002" <c:if test="${dto.release_state == 2}"> selected="selected"</c:if>>친구공개</option>
                <option value="003" <c:if test="${dto.release_state == 3}"> selected="selected"</c:if>>나만보기</option>
            </select>
        <button type="button" onclick="chkValue()" id="sbbutton">수정완료</button>
        </form>
        <button type="button" onclick="location.href='detail?board_idx=${dto.board_idx}'">취소</button>
        <!-- 자바스크립트로 메인으로 보내기 !-->
    </div>
</body>
<script>

function hashyo(){
	var a = "${dto.hashTag}";

	var hash =a.split('#');

	var sh = "#";
	
	if(hash[1] != undefined){
		document.getElementById("hash1").value = sh+hash[1];
		}
	
	if(hash[2] != undefined){
   		 document.getElementById("hash2").value = sh+hash[2];
		}
	
}



    $(document).ready(function () {
        var fileTarget = $('#file');
        fileTarget.on('change', function () { // 값이 변경되면
            var cur = $(".filebox input[type='file']").val();
            $(".upload_name").val(cur);
        });
    });
    
    function chkValue() {
        var tmp = document.fomvl.textArea.value.replace(/\s|　/gi, '');

        var hashvl = document.fomvl.hashOne.value;

        var hashvl2 = document.fomvl.hashTwo.value;



        if (tmp == '') {
            alert('내용을 입력해 주세요');
            return false;
        } 
        
        if (hashvl.charAt(0) == ('#')) {
            if (hashvl.charAt(1) == '' ) {
                alert("#이후 글을작성해주세요")
                return false;
            }
        } 
        
        if (hashvl.charAt(0) != '' && hashvl.charAt(0) != ('#')) {
            alert("해시태그에 #을 붙여주세요");
            return false;
            // 글을쓰긴햇는데 이자쉭 #을안붙엿네    

        }if(hashvl2.charAt(0) != '' && hashvl2.charAt(0) != ('#')){
            alert("해시태그에 #을 붙여주세요");
             return false;
        } 
        if(hashvl2.charAt(0) == ('#')){
            if( hashvl2.charAt(1) == ''){
                alert("#이후 글을작성해주세요")
                return false;
            }
        }
          return document.getElementById("newWrite").submit();
        

    }
    
    
    
    
    
    $(document).ready(function () {
        $('#text').keyup(function () {
            if ($(this).val().length > $(this).attr('maxlength')) {
                alert('제한길이 초과');
                $(this).val($(this).val().substr(0, $(this).attr('maxlength')));
            }
        });
    });
</script>
</html>