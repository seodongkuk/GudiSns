<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
    <head>
        <meta charset="utf-8">
        <title>회원가입</title>
         <script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
        <style>
           h2{
               padding: 30;
               font-size: 70;
           }
           table{
               height: 40%;
               width: 50%
           }
            table,td,th{
                /* 
                border: 1px solid black;
                border-collapse: collapse;
                */
            } 
            th, td{
                padding: 10px;
            }
            input[type='text'],input[type='password'],input[type='email']{
                width: 100%;
            }
            #photo{
                width: 60%;
            }
            #userId{
                width: 60%;
            }
            button,input[type='button'], input[type='submit']{
              
               background-color: rgb(240, 207, 207);
               border: 0px;
            }
        </style>
       
    </head>
    <body>
       <center>
        <h2>구디SNS 회원가입</h2>
    
            <table>
                <tr>
                    <th>아이디</th>
                    <td>
                        <input id="userId" type="text"/>
                        <input type="button" id="idChk" value="아이디중복체크">
                    </td>
                </tr>
                <tr>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td>
                        <input type="password" id="userPw" class="pass"/>
                    </td>
                </tr>
                <tr>
                    <th>비밀번호 확인</th>
                    <td>
                        <input type="password" id="userPwChk" class="pass"/>
                    </td>
                </tr>
                <tr>
                    <th>
                        <td id="passFail" style="color: red; display: none;">비밀번호 일치 하지 않습니다.</td>
                    </th>
                </tr>
                <tr>
                    <th>이름</th>
                    <td>
                        <input type="text" id="userName"/>
                    </td>
                </tr>
                <tr>
                    <th>핸드폰번호</th>
                    <td>
                        <input type="text" id="userPhone"/>
                    </td>
                </tr>
                <tr>
                    <th>이메일</th>
                    <td>
                        <input type="email" id="userEmail"/>
                    </td>
                </tr>
            </table>
            <button style="padding: 10;margin: 10; font-size: 15;">회원가입</button>
      </center>
    </body>
  <script>
		var overChk = false;//중복 체크 여부
	
		$("#idChk").click(function(){			
			$.ajax({
				type:'get'
				,url:'idChk'
				,data:{"id":$("#userId").val()}
				,dataType:'JSON'
				,success:function(obj){
					console.log(obj);
					if(obj.use){
						alert('사용할 수 있는 아이디 입니다.');
						//비동기 방식을 사용하면 javascript 를 이용하여 좀더 다양한 효과를 줄 수 있다.
						$("#userId").css({backgroundColor:'green'});
						overChk= true;
					}else{
						alert('사용불가 아이디 입니다.');
						$("#userId").val('');
					}
				}
				,error:function(e){
					console.log(e);
				}
			});				
		});	
		$('button').click(function(){

			 var $id = $("#userId");//selector 값을 변수에 담는다.(나중에 간편하게 사용하기 위해)
			 var $pw = $("#userPw");
			 var $name = $("#userName");
			 var $phone = $("#userPhone");
			 var $email = $("#userEmail");
			
			if(overChk){				
				if($id.val()==''){
					alert('아이디를 입력해 주세요!');
					$id.focus();
				}else if($pw.val()==''){
					alert('비밀번호 를 입력해 주세요!');
					$pw.focus();
				}else if($name.val()==''){
					alert('이름을 입력해 주세요!');
					$name.focus();
				}else if($phone.val()==''){
					alert('번호 를 입력해 주세요!');
					$age.focus();
				}else if($email.val()==''){
					alert('이메일을 입력해 주세요!');
					$email.focus();
				}else{
					var params = {};
					params.id = $id.val();
					params.pw = $pw.val();
					params.name = $name.val();
					params.phone = $phone.val();
					params.email = $email.val();
					$.ajax({
						type:'POST'
						,url:'join'
						,data:params
						,dataType:'text'
						,success:function(data){
							console.log(data);
							if(data.success == true){
								alert('가입실패입니다.');
							
							}else{
								alert('회원가입을 축하 드립니다.');
								location.href="index.jsp";
							}
						},error:function(e){
							console.log(e);
						}
					});
				}			
			}else{
				alert('중복 체크를 해 주세요!');
				$id.focus();
			}
		});
		
	    //2. 비밀번호 일치 여부...
	    $('.pass').keyup(function(){
	        
	        var first_pwd = $('#userPw').val();
	        var second_pwd = $('#userPwChk').val();
	        var chkpw = "";

	        if(first_pwd != '' && second_pwd == '') {
	            null
	        }else if(first_pwd != '' || second_pwd != ''){
	            if(first_pwd == second_pwd) {
	            	chkpw = true;
	                $('#passFail').css('display','none');
	            }else{
	            	chkpw = false;
	                $('#passFail').css('display','inline-block');
	            }
	        }
	    });
	
	</script>
</html>