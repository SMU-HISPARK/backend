<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/headerM.jsp" %>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주문내역</title>
    <style>
    @font-face {
            font-family: 'Pretendard4';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Regular.woff') format('woff');
            font-weight: 400;
            font-display: swap;
        }

        @font-face {
            font-family: 'Pretendard5';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Medium.woff') format('woff');
            font-weight: 500;
            font-display: swap;
        }

        @font-face {
            font-family: 'Pretendard6';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-SemiBold.woff') format('woff');
            font-weight: 600;
            font-display: swap;
        }

        @font-face {
            font-family: 'Pretendard7';
            src: url('https://cdn.jsdelivr.net/gh/Project-Noonnu/noonfonts_2107@1.1/Pretendard-Bold.woff') format('woff');
            font-weight: 700;
            font-display: swap;
        }
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Pretendard4', sans-serif;
            padding: 0;
            margin: 0;
        }

        .container {
            max-width: 720px;
            margin: 0 auto;
        }

        /* 헤더 */
        .header {
            padding: 70px 0;
            text-align: center;
            margin: 0 auto;   
        }

        .header div{
            font-size: 30px;
            font-weight: 700;
            color: #222;
        }


        /* 기본 메뉴 스타일 */
        .menu {
            display: flex;
            padding-left: 0;
            margin-bottom: 0;
            position: relative;
            border-bottom : 1px solid #1a1a1a;
        }

        .menu li {
            list-style: none;
            margin-right: 0;
            cursor: pointer;
            width: 150px;
            text-align: center;
            position: relative;
        }

        .menu li a {
            display: block;
            padding: 15px 10px;
            font-size: 16px;
            color: #1a1a1a;
            text-decoration: none;
            width: 100%;
            height: 100%;
        }

        .menu li.selected a {
            background-color: transparent;
            color : #FFF;
            border-bottom: none;
        }

        /* selected 탭 스타일 */
        .menu li.selected {
            border-left: 1px solid #1a1a1a;
            border-top: 1px solid #1a1a1a;
            border-right: 1px solid #1a1a1a;
            border-bottom: none;
            background-color: #1a1a1a;
            color : #FFF;
            font-weight : 600;
            z-index: 10;
            
        }

        /* 선택된 탭 왼쪽의 테두리 연결선 */
        .menu li.selected::before {
            content: "";
            position: absolute;
            bottom: -1px;
            left: -1px;
            width: 1px;
            height: 1px;
            background-color: #1a1a1a;
        }

        /* 선택된 탭 오른쪽의 테두리 연결선 */
        .menu li.selected::after {
            content: "";
            position: absolute;
            bottom: -1px;
            right: -1px;
            width: 1px;
            height: 1px;
            background-color: #1a1a1a;
        }


        /* content */
        .content {
            flex: 1;
            padding-top : 40px;
            padding-left : 30px;
            padding-right : 30px;           
            height: auto;
            min-height : 0;
            border-top: 20px; 
            background-color: #fff;
        }
        
        .pg-container {
          display: flex;
          justify-content: center;
          margin: 50px 0 20px 0;
          width: 100%
      }
      
      .paginator {
          display: inline-flex;
          align-items: center;
          line-height: 1;
      }
      
      .pg-btns {
          display: inline-flex;
          margin: 0 10px;
      }
      
      .pg-btns button {
          border: none;
          margin: 0;
          padding: 12px 0;
          width: 24px;
          background: transparent;
          color: #ccc;
          cursor: pointer;
          font-size: 30px;
          line-height: 0;
      }
      
      .pg-btns button:disabled {
          cursor: default;
      }
      
      .pg-btns button {
          color: #ccc;
      }
      
      .pg-numbers {
          display: inline-block;
          margin: 0 10px;
          
      }
      
      .pg-numbers a {
          margin: 0 5px;
          color: #ccc;
          font-size: 20px;
          cursor: pointer;
          text-decoration: none;
      }
      
      .pg-numbers a.active {
          color: #1a1a1a;
      }
      
      .pg-btns svg {
          width: 16px;
          height: 16px;
          fill: currentColor;
      }
      

        /* 주문상품 리스트 */
        .orderProduct{
            width:100%;
            background-color: white;
        }

        .orderProduct{
            margin-top:20px;
        }


        .addressBox .label{
            font-size: 14px;
            vertical-align: top;
            padding-top: 18px;
        }

        /* 배송지 필수정보, 주문갯수  */
        .label .required, .orderProduct span{
            color:#035fe0;
        }

        
        .orderProduct_one {
            display: flex;
            align-items: flex-start;
            padding: 20px 0;
            height:130px;
            border-bottom: 1px solid #eee;
        }

        .orderProduct_one:last-child {
            border-bottom: none;
        }

        .orderProduct_one img {
            width: 90px;
            height: 90px;
            object-fit: cover;
            margin-right: 15px;
        }

        .productInfo {
            flex: 1;
        }

        .productName {
            color:#666;
            font-size: 14px;
            font-weight: 500;
            margin-bottom: 5px;
        }

        .productOption {
            font-size: 12px;
            color: #a0a0a0;
            margin-bottom: 3px;
        }

        .productQty {
            font-size: 12px;
            color: #a0a0a0;
            margin-bottom: 6px;
        }

        .productPrice {
            font-size: 14px;
            font-weight: 600;
            color: #1a1a1a;
        }

        /* 버튼 */
        .btn {
            background-color: #1a1a1a;
            border: 1px solid #ccc;
            color: #fff;
            padding: 10px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            margin-right: 10px;
        }
        
        #order-detail.section.active{
            padding : 0 30px;
        }
        
        #delivery-tracking{
            padding : 0 30px;
        }

        .order-detail-actions {
            display: flex;
            justify-content: center;  /* 가운데 정렬 */
            gap: 10px;                /* 버튼 사이 간격 */
            margin-top: 20px;
        }

        /* 버튼 공통 */
        .update, .cancel {
            display: inline-block;  /* block 대신 inline-block */
            margin: 0;              /* margin auto 제거 */
            padding: 10px 20px;
            cursor: pointer;
        }

        .update {
            background: #1a1a1a;
            color: #fff;
            border: 1px solid #ccc;
        }

        .cancel {
            background: #fff;
            color: #333;
            border: 1px solid #ccc;
        }
        .btn-small {
            padding: 6px 12px;
            font-size: 12px;
        }

        /* 주문 카드 */
        .order-list {
            display: grid;
            gap: 15px;
        }

        .order-item {
            border: 1px solid #ddd;
            padding: 20px;
            background-color: #fff;
        }


        .order-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 10px;
        }

        .post-title {
            font-weight: 500;
            font-size: 16px;
            color: #222;
        }

        .order-date {
            font-size: 12px;
            color: #666;
        }

        .order-details {
            display: grid;
            grid-template-columns: repeat(4, 1fr);
            gap: 15px;
            margin-bottom: 15px;
        }

        .order-field {
            text-align: center;
        }

        .order-field label {
            display: block;
            font-size: 12px;
            margin-bottom: 5px;
        }

        .order-field span {
            font-weight: 500;
            font-size: 15px;
        }

        .status-badge {
            padding: 4px 8px;
            font-size: 12px !important;
        }

        .fin{
            background : #666;
            color : white;
        }

        .can{
            background : red;
            color : white;
        }

        .ing{
            background : #035fe0;
            color : white;
        }

        .buy{
            background : #c8def8;
            color : white;
        }
        /* 상세 & 배송 카드 */
        .detail-section {
            border: 1px solid #ddd;
            padding: 20px;
            margin-bottom: 20px;
            background-color: #fff;
            font-size : 15px;
        }


        .detail-section .subtitle{
            font-size : 16px;
            font-weight : 600;
            margin-bottom : 10px;
        }

        .detail-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
        }

        .detail-item {
            display: flex;
            justify-content: space-between;
            padding: 5px 0;
        }

        .detail-item label {
            font-weight: 500;
            color: #555;
        }

        .tracking-step {
            border: 1px solid #ddd;
            padding: 15px;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: white;
        }

        .tracking-status {
            font-weight: bold;
            color: #035fe0;
        }

        .tracking-date {
            font-size: 12px;
            color: #666;
        }
                
        .section {
            display: none;
        }
        
        .section.active {
            display: block;
        }
        
        .back-button {
            margin-bottom: 20px;
        }
        
        
    </style>

</head>
<body>
    <div class="container">
       <div class="header">
            <div class = "title">마이페이지</div>
        </div>
        <ul class="menu">
            <li>
            <a href = "/mypage/member">회원정보</a>
            </li>
            <li>
                <a href = "/mypage/club">가입 동아리</a>
            </li>
            <li>
                <a href = "/mypage/community">게시글 관리</a>
            </li>
            <li  class = "selected">
                <a href = "/mypage/shop">주문내역</a>                
            </li>
            <li>
                <a href = "/mypage/point">적립금</a>                
            </li>
        </ul>
        
        <div class="content">
            <div id="orders-list" class="section active">
                <!-- 주문내역 목록 -->
                <div class="order-list">
                    <c:forEach var="order" items="${ordersList}">
                   <div class="order-item">
                       <div class="order-header">
                           <div class="post-title">주문번호: ${order.orderCode}</div>
                           <div class="order-date">${order.createdAt}</div>
                       </div>
                       <div class="order-details">
                           <!-- 상품명 요약 -->
                           <div class="order-field">
                               <label>상품명</label>
                               <span>
                                   ${order.orderItems[0].product.productName} 
                                   <c:if test="${fn:length(order.orderItems) > 1}">
                                       외 ${fn:length(order.orderItems) - 1}건
                                   </c:if>
                               </span> 
                           </div>
               
                           <!-- 수량 합계 -->
                           <div class="order-field">
                               <label>수량</label>
                               <c:set var="totalQty" value="0" />
                               <c:forEach var="item" items="${order.orderItems}">
                                   <c:set var="totalQty" value="${totalQty + item.quantity}" />
                               </c:forEach>
                               <span>${totalQty}개</span>
                           </div>
               
                           <!-- 결제금액 -->
                           <div class="order-field">
                               <label>결제금액</label>
                               <span><fmt:formatNumber value="${order.totalAmount}" pattern="#,###"/>원</span>
                           </div>
               
                           <!-- 주문상태 -->
                           <div class="order-field">
                               <label>주문상태</label>
                               <c:choose>
                                   <c:when test="${order.orderState == 2}">
                                       <span class="status-badge fin">배송완료</span>
                                   </c:when>
                                   <c:when test="${order.orderState == 1}">
                                       <span class="status-badge ing">배송중</span>
                                   </c:when>
                                   <c:when test="${order.orderState == 0}">
                                       <span class="status-badge buy">상품준비중</span>
                                   </c:when>
                                   <c:when test="${order.orderState == -1}">
                                       <span class="status-badge can">주문취소</span>
                                   </c:when>
                               </c:choose>
                           </div>
                       </div>
               
                       <!-- 버튼 -->
                       <div class="order-actions">
                           <button class="btn btn-small" data-order-code="${order.orderCode}" data-action="detail">주문상세</button>
                           <button class="btn btn-small" data-order-code="${order.orderCode}" data-action="tracking">배송조회</button>
                       </div>
                   </div>
               </c:forEach>
                </div>
            </div>
            </div>
            
          <div class="pg-container">
             <div class="paginator">
                 <div class="pg-btns">
                     <c:if test="${page < 2}">
                         <button class="disabled">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
                                 <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
                             </svg>
                         </button>
                         <button class="disabled">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
                             </svg>
                         </button>
                     </c:if>
                     <c:if test="${page >= 2}">
                         <button onclick="location.href='/mypage/shop'">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
                                 <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
                             </svg>
                         </button>
                         <button onclick="location.href='/mypage/shop?page=${page-1}'">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
                             </svg>
                         </button>
                     </c:if>
                 </div>
         
                 <div class="pg-numbers">
                     <c:forEach var="i" begin="${startpage}" end="${endpage}">
                         <c:if test="${page == i}">
                             <a class="active">${i}</a>
                         </c:if>
                         <c:if test="${page != i}">
                             <a href="/mypage/shop?page=${i}">${i}</a>
                         </c:if>
                     </c:forEach>
                 </div>
         
                 <div class="pg-btns">
                     <c:if test="${page < maxpage}">
                         <button onclick="location.href='/mypage/shop?page=${page+1}'">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
                             </svg>
                         </button>
                         <button onclick="location.href='/mypage/shop?page=${maxpage}'">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
                                 <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
                             </svg>
                         </button>
                     </c:if>
                     <c:if test="${page >= maxpage}">
                         <button class="disabled">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
                             </svg>
                         </button>
                         <button class="disabled">
                             <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
                                 <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
                                 <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
                             </svg>
                         </button>
                     </c:if>
                 </div>
             </div>
         </div>
            
            
            <!-- 주문 상세 페이지 -->
            <div id="order-detail" class="section">
                <div class="back-button">
                    <button class="btn" onclick="showOrdersList()">← 주문내역으로 돌아가기</button>
                </div>
                
                <div id="order-detail-content">
                    <!-- 주문 상세 내용 -->
                </div>
                <!-- 버튼 영역 -->
                <div class="order-detail-actions" id="order-detail-buttons">
                <button onclick="openPopup(${order.orderCode})" class = "btn update">배송지 변경</button>
                <button onclick = "cancelOrder('')" class = "cancel" id="cancel-btn">주문취소</button>
            </div>
            </div>
            
            <!-- 배송 조회 페이지 -->
            <div id="delivery-tracking" class="section">
                <div class="back-button">
                    <button class="btn" onclick="showOrdersList()">← 주문내역으로 돌아가기</button>
                </div>
                <div id="delivery-tracking-content">
                    <!-- 배송 조회 -->
                </div>
            </div>
        </div>
    </div>
</div>
    <script>
       $(document).ready(function() {
           // 이벤트 위임을 사용한 버튼 클릭 처리
           $('.order-list').on('click', '.btn[data-action]', function() {
               const orderCode = $(this).data('order-code');
               const action = $(this).data('action');
               
               if (action === 'detail') {
                   showOrderDetail(orderCode);
               } else if (action === 'tracking') {
                   showDeliveryTracking(orderCode);
               }
           });
       });
    
       function openPopup(orderCode){
           console.log("openPopup 함수 호출됨, orderCode:", orderCode);
           console.log("orderCode 타입:", typeof orderCode);
           
           if (!orderCode || orderCode === 'undefined' || orderCode === '') {
               alert('주문번호가 없습니다.');
               return;
           }
           
           // 템플릿 리터럴 대신 문자열 연결 사용
           const url = "/mypage/shopupdate?orderCode=" + orderCode;
           console.log("생성된 URL:", url);
           window.open(url, "배송지 변경", "width=800,height=600,top=100,left=200,resizable=no,scrollbars=yes");
       }
        
        
       function showSection(sectionName) {
           // 모든 섹션 숨기기
           document.querySelectorAll('.section').forEach(s => s.classList.remove('active'));
           // 선택된 섹션 보이기
           document.getElementById(sectionName).classList.add('active');
           
           // 페이지네이션 표시/숨김 처리
           const paginationContainer = document.querySelector('.pg-container');
           
           if (sectionName === 'orders-list') {
               // 주문내역 목록일 때만 페이지네이션 표시
               paginationContainer.style.display = 'flex';
           } else {
               // 주문상세, 배송조회일 때는 페이지네이션 숨김
               paginationContainer.style.display = 'none';
           }
       }
        
        
        function showOrderDetail(orderCode) {
            console.log("주문상세 요청:", orderCode);
            
            $.ajax({
                url: "/mypage/shop/detail",
                method: "GET",
                data: { orderCode: orderCode },
                success: function(response) {
                    console.log("받은 order 객체:", response);
                    
                    if (!response) {
                        alert('주문 정보를 불러올 수 없습니다.');
                        return;
                    }

                    const order = response.order;
                    const orderItems = response.orderItems || [];
                    
                    // createdAt 날짜 변환
                    const createdDate = new Date(order.createdAt);
                    const formattedDate = createdDate.toLocaleString('ko-KR', {
                        year: 'numeric',
                        month: '2-digit',
                        day: '2-digit',
                        hour: '2-digit',
                        minute: '2-digit'
                    });

                    // 주문 상태 텍스트 변환
                    let statusText = '';
                    let statusClass = '';
                    switch(order.orderState) {
                        case 2: statusText = '배송완료'; statusClass = 'fin'; break;
                        case 1: statusText = '배송중'; statusClass = 'ing'; break;
                        case 0: statusText = '상품준비중'; statusClass = 'buy'; break;
                        case -1: statusText = '주문취소'; statusClass = 'can'; break;
                        default: statusText = '알수없음'; statusClass = 'buy'; break;
                    }

                    // 상세상품 
                    let productsHtml = '';
                    let totalQuantity = 0;
                    
                    if (orderItems.length > 0) {
                        orderItems.forEach(function(orderItemDTO) {
                            const product = orderItemDTO.product;
                            const imageSrc = product.productImage ? 
                                product.productImage : 
                                '../images/layout/hispark.png';
                            
                            totalQuantity += orderItemDTO.quantity;
                            
                            productsHtml += 
                                '<div class="orderProduct_one">' +
                                    '<img src="' + imageSrc + '" alt="상품이미지" />' +
                                    '<div class="productInfo">' +
                                        '<p class="productName">' + (product.productName || '상품명 없음') + '</p>' +
                                        '<p class="productQty">수량: ' + orderItemDTO.quantity + '개</p>' +
                                        '<p class="productPrice">₩' + orderItemDTO.price.toLocaleString() + '</p>' +
                                    '</div>' +
                                '</div>';
                        });
                    } else {
                        productsHtml = '<div class="orderProduct_one">상품 정보를 불러올 수 없습니다.</div>';
                    }
                    
                    const detailContent = 
                        '<div class="detail-section">' +
                            '<div class="subtitle">주문 정보</div>' +
                            '<div class="detail-grid">' +
                                '<div class="detail-item">' +
                                    '<label>주문번호:</label>' +
                                    '<span>' + order.orderCode + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>주문일자:</label>' +
                                    '<span>' + formattedDate + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>주문상태:</label>' +
                                    '<span class="status-badge ' + statusClass + '">' + statusText + '</span>' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        
                        '<div class="detail-section">' +
                            '<div class="subtitle">상품 정보</div>' +
                            '<div class="orderProduct">' +
                                '<details open>' +
                                    '<summary>상품상세 <span>(' + totalQuantity + ')</span></summary>' +
                                    productsHtml +
                                '</details>' +
                            '</div>' +
                        '</div>' +
                        
                        '<div class="detail-section">' +
                            '<div class="subtitle">결제 정보</div>' +
                            '<div class="detail-grid">' +
                                '<div class="detail-item">' +
                                    '<label>상품금액:</label>' +
                                    '<span>₩' + order.totalAmount.toLocaleString() + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>배송비:</label>' +
                                    '<span>₩' + order.deliverCost.toLocaleString() + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>결제방법:</label>' +
                                    '<span>' + order.paymentMethod + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>총 결제금액:</label>' +
                                    '<span>₩' + (order.totalAmount + order.deliverCost).toLocaleString() + '</span>' +
                                '</div>' +
                            '</div>' +
                        '</div>' +
                        
                        '<div class="detail-section">' +
                            '<div class="subtitle">배송 정보</div>' +
                            '<div class="detail-grid">' +
                                '<div class="detail-item">' +
                                    '<label>수령인:</label>' +
                                    '<span>' + order.receiver + '</span>' +
                                '</div>' +
                                '<div class="detail-item">' +
                                    '<label>연락처:</label>' +
                                    '<span>' + order.phone + '</span>' +
                                '</div>' +
                                '<div class="detail-item" style="grid-column: 1 / -1;">' +
                                    '<label>배송주소:</label>' +
                                    '<span>' + order.addressMain + ' ' + order.addressDetail + '</span>' +
                                '</div>' +
                                '<div class="detail-item" style="grid-column: 1 / -1;">' +
                                    '<label>배송메시지:</label>' +
                                    '<span>' + (order.deliveryMessage || '-') + '</span>' +
                                '</div>' +
                            '</div>' +
                        '</div>';

                    document.getElementById('order-detail-content').innerHTML = detailContent;

                    // 상품준비중일 때만 버튼 추가
                    const btnArea = document.getElementById('order-detail-buttons');
                    btnArea.innerHTML = '';

                    if (order.orderState == 0) {  
                        const updateBtn = document.createElement('button');
                        updateBtn.className = 'btn update';
                        updateBtn.textContent = '배송지 변경';
                        updateBtn.onclick = function() { openPopup(order.orderCode); };

                        const cancelBtn = document.createElement('button');
                        cancelBtn.className = 'cancel';
                        cancelBtn.id = 'cancel-btn';
                        cancelBtn.textContent = '주문취소';
                        cancelBtn.onclick = function() { cancelOrder(order.orderCode); };

                        btnArea.appendChild(updateBtn);
                        btnArea.appendChild(cancelBtn);
                    }

                    showSection('order-detail');
                },
                error: function(xhr, status, error) {
                    console.log("Ajax 에러:", xhr, status, error);
                    alert('주문 상세 정보를 불러올 수 없습니다.');
                }
            });
        }

        function showDeliveryTracking(orderCode) {
            console.log("배송조회 요청 - 주문번호:", orderCode);
            
            $.ajax({
                url: "/mypage/shop/tracking",  
                method: "GET",
                data: {orderCode: orderCode},
                dataType: "text", 
                success: function(response){
                    console.log("API 응답:", response);
                    
                    try {
                        let data = JSON.parse(response);
                        
                        if(data.error) {
                            console.log("API 에러:", data.error);
                            displayDefaultTrackingInfo(data.orderCode || orderCode);
                            return;
                        }
                        
                        if(data && data.complete !== false && data.trackingDetails && data.trackingDetails.length > 0) {
                            displayTrackingInfo(data, data.orderCode || orderCode);
                        } else {
                            console.log("배송정보 없음, 기본 정보 표시");
                            displayDefaultTrackingInfo(data.orderCode || orderCode);
                        }
                    } catch(e) {
                        console.error("JSON 파싱 오류:", e);
                        displayDefaultTrackingInfo(orderCode);
                    }
                },
                error: function(err) {
                    console.error("Ajax 오류:", err);
                    displayDefaultTrackingInfo(orderCode);
                }
            });
        }
        // 배송 조회 정보 없을 때 기본 메시지 표시
      function displayDefaultTrackingInfo(orderCode) {
          const html = `
              <div class="detail-section">
                  <div class="subtitle">배송 정보</div>
                  <div class="detail-grid">
                      <div class="detail-item"><span>배송 정보가 없습니다.</span></div>
                  </div>
              </div>
          `;
          document.getElementById('delivery-tracking-content').innerHTML = html;
          showSection('delivery-tracking');
      }
        
      //API에서 받은 실제 배송조회 정보를 화면에 표시하는 함수
      function displayTrackingInfo(apiResponse, orderCode){
          console.log("실제 API 데이터로 화면 구성:", apiResponse);
          
          // level에 따른 상태 텍스트 변환
          var statusText = '';
          switch(apiResponse.level) {
              case 1: statusText = '접수'; break;
              case 2: statusText = '집화'; break; 
              case 3: statusText = '터미널입고'; break;
              case 4: statusText = '배송입고'; break;
              case 5: statusText = '배송출고'; break;
              case 6: statusText = '배송완료'; break;
              default: statusText = '알 수 없음'; break;
          }
          
          var html = 
              '<div class="detail-section">' +
                  '<div class="subtitle">배송 정보</div>' +
                  '<div class="detail-grid">' +
                      '<div class="detail-item"><label>주문번호:</label><span>' + orderCode + '</span></div>' +
                      '<div class="detail-item"><label>송장번호:</label><span>' + (apiResponse.invoiceNo || '-') + '</span></div>' +
                      '<div class="detail-item"><label>배송완료여부:</label><span>' + (apiResponse.complete ? '완료' : '진행중') + '</span></div>' +
                      '<div class="detail-item"><label>현재상태:</label><span class="status-badge">' + statusText + '</span></div>' +
                  '</div>' +
              '</div>' +
              '<div class="detail-section">' +
                  '<div class="subtitle">배송 추적</div>';
          
          if(apiResponse.trackingDetails && apiResponse.trackingDetails.length > 0){
              var sortedTracking = apiResponse.trackingDetails.sort(function(a, b) { return b.time - a.time; });
              
              for(var i = 0; i < sortedTracking.length; i++){
                  var step = sortedTracking[i];
                  html += '<div class="tracking-step">' +
                      '<div>' +
                          '<div class="tracking-status">' + step.kind + '</div>' +
                          '<div style="font-size:12px;color:#666;">' + step.where + '</div>' +
                      '</div>' +
                      '<div class="tracking-date">' + step.timeString + '</div>' +
                  '</div>';
              }
          } else {
              html += '<div class="tracking-step">배송조회 정보가 없습니다.</div>';
          }
          
          html += '</div>';
          
          document.getElementById('delivery-tracking-content').innerHTML = html;
          showSection('delivery-tracking');
      }

      function cancelOrder(orderCode){
          if(confirm("주문을 취소하시겠습니까?")){
              $.ajax({
                  url: "/mypage/shop/cancel",
                  method: "POST", 
                  data: { orderCode: orderCode },
                  dataType: "json",
                  success: function(result) {
                      if(result.success) {
                          alert(orderCode + ' 주문이 취소되었습니다.');
                          
                          // 주문 목록에서 해당 주문 항목 제거
                          // removeOrderFromList(orderCode);
                          
                          // 주문내역 페이지로 이동
                          showOrdersList();
                      } else {
                          alert('주문 취소에 실패했습니다: ' + (result.message || '알 수 없는 오류'));
                      }
                  },
                  error: function(xhr, status, error) {
                      console.error('주문취소 Ajax 에러:', xhr, status, error);
                      alert('주문 취소 중 오류가 발생했습니다.');
                  }
              });
          }
      }
      
      // 목록에서 특정 주문을 제거
      function removeOrderFromList(orderCode) {
          // 주문 목록에서 해당 주문번호를 가진 항목을 찾아서 제거
          const orderItems = document.querySelectorAll('.order-item');
          
          orderItems.forEach(function(item) {
              const orderTitle = item.querySelector('.post-title');
              if(orderTitle && orderTitle.textContent.includes(orderCode)) {
                   item.remove();
                   checkEmptyOrderList();
                      
              }
          });
      }

      // 주문 목록이 비어있는지 확인하고 메시지 표시
      function checkEmptyOrderList() {
          const orderList = document.querySelector('.order-list');
          const remainingOrders = orderList.querySelectorAll('.order-item');
          
          if(remainingOrders.length === 0) {
              const emptyMessage = document.createElement('div');
              emptyMessage.className = 'empty-message';
              emptyMessage.style.textAlign = 'center';
              emptyMessage.style.padding = '50px 20px';
              emptyMessage.style.color = '#666';
              emptyMessage.style.fontSize = '16px';
              emptyMessage.innerHTML = '주문내역이 없습니다.';
              
              orderList.appendChild(emptyMessage);
          }
      }
      
      function showOrdersList(){
          showSection('orders-list');
      }
    </script>
</body>
</html>
<%@ include file="../layout/footer.jsp" %>