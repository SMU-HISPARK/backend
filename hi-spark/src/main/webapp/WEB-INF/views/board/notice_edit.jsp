<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>HISPARK NOTICE EDIT</title>
    <link rel="stylesheet" href="/css/c_write.css">
    <link rel="stylesheet" href="/css/common.css">
</head>
<body>
    <div style="width:100%; height:150px; margin-bottom:20px; position: relative; background-color:#035fe0;">
        <a style="position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); font-size:30px; color:white; font-weight:700;">header</a>
    </div>

    <div class="breadcrumb-container">
        <a href="#">홈</a> / <a href="#">게시판</a> / <span class="current"><a href="/board/notice_list">NOTICE</a></span>
    </div>

    <div class="board-container">
        <h2 class="board-title">NOTICE</h2>

        <form class="write-form" action="/board/notice_edit_proc" method="post" enctype="multipart/form-data">
            
            <input type="hidden" name="bno" value="${notice.bno}">
            <input type="hidden" id="existingBfile" name="existingBfile" value="${notice.bfile}">

            <div class="form-group">
                <label for="btitle">제목</label>
                <input type="text" id="btitle" name="btitle" value="${notice.btitle}" required>
            </div>
            
            <div class="form-group">
                <label for="bcontent">내용</label>
                <textarea id="bcontent" name="bcontent" required>${notice.bcontent}</textarea>
            </div>
            
            <div class="form-group">
			    <label for="currentFileName">현재 파일</label>
			    <div style="display: flex; align-items: center;">
			        <input type="text" id="currentFileName" name="currentFileName" value="${not empty notice.bfile ? notice.bfile : '첨부된 파일 없음'}" readonly 
			        style="flex-grow: 1; width:1000px;">
			        <c:if test="${not empty notice.bfile}">
			            <button type="button" class="del-button" onclick="deleteFile()"
			            style="width:100px; height: 46px; margin-left: 25px;" >X</button>
			        </c:if>
			    </div>
			</div>

            <div class="form-group">
                <label for="uploadFile">새 파일 첨부</label>
                <input type="file" id="uploadFile" name="uploadFile">
            </div>

            <div class="button-group">
                <button type="submit" class="submit-button">수정</button>
                <button type="button" class="cancel-button" onclick="location.href='/board/notice_view?bno=${notice.bno}'">취소</button>
            </div>
        </form>
    </div>
</body>
<script>
function deleteFile() {
    if (confirm("기존 파일을 삭제하시겠습니까?")) {
        // 파일을 삭제하기 위해 숨겨진 입력 필드 값을 변경
        document.getElementById('uploadFile').value = '';
        // 이 필드가 서버에 파일 삭제 요청을 알림
        document.getElementById('existingBfile').value = 'delete'; 
        document.getElementById('currentFileName').value = '첨부된 파일 없음';
        // 버튼의 클래스명을 'del-button'으로 수정
        document.querySelector('.del-button').style.display = 'none';
    }
}
</script>
</html>