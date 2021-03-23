<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><!-- JSTL 태그 사용 -->
<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>Insert title here</title>
		<!-- 제이쿼리 사용을 위해 선언 -->
		<script src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
		<style>
            /* body{
                width: 700px;
                margin: 20px 550px;
                padding: 50px;
                text-align: center;
                border: 1px solid black;
            } */
            body{
                width: 850px;
                height: 1000px;
                margin-left:25%;
                padding: 5px;
                border: 1px solid black;
                overflow: hidden;
            }
            table{
                width: 650px;
                height: 100px;
                margin: auto;
                text-align: center;
            }
            div.find{
            	text-align: center;
            	margin-top: 5%;
            }
           table,td,th{
                border: 1px solid white;
                border-collapse: collapse;
            } 
            div.Tlist{
            	height: 850px;
				width: 100%;
				overflow: scroll;
            }
            a:link{
            	color:black;
            	text-decoration: none;
            }
            a:visited{
            	color:black;
            	text-decoration: none;
            }
            a:hover{
            	color:blue;
            	text-decoration: underline;
            }            
        </style>
	</head>	       
	    <body>	        
	        <div class="find">
	            <form action="find" method="get">
	                <select name="find">
	                    <option value="none">===선택===</option>
	                    <option value="HashTag" selected>해시태그</option>
	                    <option value="User">유저</option>
	                </select>
	                <input type="text" name="search" id="sch" placeholder="검색어를 입력해주세요.">
	                <button>검색</button>
	            </form>
	        </div>
	        <div class="Tlist">
		        <c:forEach items="${list}" var="member2">
		            <table style="float: left;margin-top:40px; width: 250px">
		                <tr>
		                    <%-- <td rowspan="2"><img src="기본프사.png" width="70" height="70"></td>
		                    <td>${member2.user_id}</td>   --%>       
		                    <td rowspan="2"><img src="/GudiSns/photo/" width="70" height="70"></td>
				            <td><a style="font-weight:bold;"
				                    href="otherProfile?id=${member2.user_id}">
				                    ${member2.user_id}</a>   
			                </td>
		                </tr> 
		            </table>	
		            <table>
		                <tr>
		                    <!-- <td><button style="color: red; margin-left: 90%;">신고</button></td> -->
		                    <form action="singo" method="post">
			                	<input type="hidden" name="board_idx" value="${member2.board_idx}" />
			                	<input type="hidden" name="user_id" value="${member2.user_id}" />
			                	<input type="submit" value="신고하기" 
			                	style="color: red; margin-left: 85%;">
		            		</form>
		                </tr>	
		                <tr>
	                        <c:if test="${member2.newFileName ne null }">
	                        <td>
	                            <img src="/GudiSns/photo/${member2.newFileName}" width="600" height="450" style="margin-top:100px; margin-left: -73px;" />
	                        </td>
	                        </c:if>
	                        <c:if test="${member2.newFileName eq null }">
	                         <td>
	                            <img src="/GudiSns/photo/${member2.newFileName}" width="600" height="0" style="margin-top:100px; visibility: hidden;" />
	                        </td>
	                        </c:if>
	                    </tr>
				        <tr>
				            <td>
				                <p>${member2.hashTag}</p>
				                <p>${member2.content}</p>
				                <button id="moreShow" name="board_idx" value="${member2.board_idx}"
                                    onclick="location.href='detail?board_idx=${member2.board_idx}'">더보기</button>
				            </td>
				            <td>
								<p style="margin-top: -17px; margin-left: -225px;">${member2.writedate}</p>
	                        </td>
				        </tr>
				        <!-- <tr>
						   <td>
						        <button class="btn-like" style="font-size: 40px;background-color: white;border: none;float: left;">♥</button>
						        <input style="float: left;margin-top: 10px;border: none;"  value="추천수">
						    </td>
						</tr>
						<tr>
		    				<td style="margin:0; padding: 0;";>
		        			<hr>
		    				</td>
						</tr>
						<tr>
						    <td>
						        <p style="font-size: 15px; color: grey;" >댓글 모두 3개입니다.</p>
						        <p>1번댓글</p>
						        <p>2번댓글</p>
						        <p>3번댓글</p>
						    </td>
						</tr> -->
					</table>
				</c:forEach>
	        </div>
				<iframe 
					src="navi.jsp" width="850px" height="55px" scrolling="no" frameborder="0"></iframe>
		</body>
		<script>
			
			/* var hash = $('#sch').val();
			if(hash == "" || hash == " "){
				alert("검색 할 해시태그를 입력해주세요!");
				location.href = "todayTag";
			} */
			
			/* if(hash != "" || hash != null){
				location.href = "find_tag.jsp";
			} */
		
			//Service에서 지정한 msg 값을 alert로 View
			var msg = "${msg}";
			if(msg != ""){
				alert(msg);
			}
		</script>
</html>