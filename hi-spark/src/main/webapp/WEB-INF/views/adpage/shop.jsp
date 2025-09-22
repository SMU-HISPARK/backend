<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/headerM.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/mypage/mypage.css">
    <title>관리자페이지 - 샵 관리</title>
    <style>
    	.container{margin-top : 50px;}
    
        .shop-container {
        	margin : 0 auto;
    	    padding: 20px;
            max-width: 1230px;
        }

        .shop-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            flex-wrap: wrap;
            gap: 15px;
        }

        .shop-header h2 {
            font-size: 24px;
            color: #1a1a1a;
        }

        .header-controls {
            display: flex;
            gap: 10px;
            align-items: center;
            flex-wrap: wrap;
        }

        .search-box {
            display: flex;
            gap: 10px;
            align-items: center;
        }

        .search-box input {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
            width: 200px;
        }

        .search-box select {
            padding: 8px 12px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }

        .btn {
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            border: none;
        	background: #1a1a1a;
            color: white;
        }

        .btn-primary {
        	border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            border: none;
            background: #1a1a1a;
            color: white;
            margin : 8px 0;
            padding : 0 8px;
        }

        .btn-secondary {
        	border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            font-weight: 500;
            border: none;
            background: white;
            color: #1a1a1a;
            border: 1px solid #ddd;
            margin : 8px 0;
            padding : 0 8px;
        }


        .products-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(280px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .product-card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
        }

		.product-description {
		    white-space: pre-wrap; /* 공백과 줄바꿈을 그대로 유지 */
		    word-wrap: break-word; /* 긴 단어 줄바꿈 */
		}

        .product-image {
            position: relative;
            height: 200px;
            overflow: hidden;
            background: #f8f9fa;
        }

        .product-image img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .product-status {
            position: absolute;
            top: 10px;
            right: 10px;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: 600;
        }

        .status-active {
            background: #28a745;
            color: white;
        }

        .status-inactive {
            background: #dc3545;
            color: white;
        }

        .status-soldout {
            background: #6c757d;
            color: white;
        }

        .product-info {
            padding: 15px;
        }

        .product-category {
            font-size: 12px;
            color: #666;
            margin-bottom: 5px;
        }

        .product-name {
            font-size: 16px;
            font-weight: 600;
            margin-bottom: 10px;
            color: #1a1a1a;
            line-height: 1.3;
        }

        .product-price {
            font-size: 18px;
            font-weight: bold;
            color: #1a1a1a;
            margin-bottom: 10px;
        }

        .product-stock {
            font-size: 14px;
            margin-bottom: 15px;
        }

        .stock-low {
            color: #dc3545;
            font-weight: 600;
        }

        .stock-normal {
            color: #28a745;
            font-weight: 600;
        }

        .product-actions {
            display: flex;
            gap: 8px;
        }

        .btn-small {
            padding: 6px 12px;
            font-size: 12px;
            border-radius: 4px;
            cursor: pointer;
            border: none;
            flex: 1;
        }

        .btn-edit {
            background: #17a2b8;
            color: white;
        }

        .btn-delete {
            background: #dc3545;
            color: white;
        }

        .btn-toggle {
            background: #ffc107;
            color: #1a1a1a;
        }

        .modal {
            display: none;
            position: fixed;
            z-index: 1000;
            left: 0;
            top: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0,0,0,0.5);
        }

        .modal-content {
            background-color: white;
            margin: 2% auto;
            padding: 0;
            border-radius: 8px;
            width: 90%;
            max-width: 600px;
            max-height: 90vh;
            overflow-y: auto;
        }

        .modal-header {
            padding: 20px;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .modal-header h3 {
            margin: 0;
            font-size: 20px;
        }

        .close {
            font-size: 24px;
            font-weight: bold;
            cursor: pointer;
            color: #666;
        }

        .modal-body {
            padding: 20px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
        }

        .form-group input,
        .form-group select,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }

        .form-group textarea {
            height: 100px;
            resize: vertical;
        }

        .image-upload {
            border: 2px dashed #ddd;
            padding: 40px;
            text-align: center;
            cursor: pointer;
            border-radius: 4px;
            transition: border-color 0.3s;
        }

        .image-upload:hover {
            border-color: #1a1a1a;
        }

        .image-upload.dragover {
            border-color: #1a1a1a;
            background-color: #f8f9fa;
        }

        .preview-images {
            display: flex;
            flex-wrap: wrap;
            gap: 10px;
            margin-top: 10px;
        }

        .preview-item {
            position: relative;
            width: 100px;
            height: 100px;
        }

        .preview-item img {
            width: 100%;
            height: 100%;
            object-fit: cover;
            border-radius: 4px;
        }

        .remove-preview {
            position: absolute;
            top: -5px;
            right: -5px;
            background: #dc3545;
            color: white;
            border: none;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            font-size: 12px;
            cursor: pointer;
        }

        .modal-footer {
            padding: 10px;
            border-top: 1px solid #e0e0e0;
            display: flex;
            justify-content: flex-end;
            gap: 10px;
        }

        @media (max-width: 768px) {
            .shop-header {
                flex-direction: column;
                align-items: stretch;
            }
            
            .header-controls {
                justify-content: space-between;
            }
            
            .search-box {
                flex-direction: column;
            }
            
            .search-box input {
                width: 100%;
            }
            
            .products-grid {
                grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <ul class="menu">
            <li>
                <a href="/adpage/graphCommu">커뮤니티 지수</a>
            </li>
            <li>
                <a href="/adpage/graphShop">주문 통계</a>
            </li>
            <li class="selected">
                <a href="/adpage/shop">샵 관리</a>
            </li>
        </ul>
        
        <div class="content">
            <div class="shop-container">
                <div class="shop-header">
                        <button class="btn" onclick="openAddModal()">+ 새 상품 등록</button>
                    <div class="header-controls">
                        <div class="search-box">
                            <select id="statusFilter">
                                <option value="all">전체 상태</option>
                                <option value="active">판매중</option>
                                <option value="soldout">품절</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="products-grid">
			    <c:forEach var="product" items="${products}">
			        <div class="product-card">
			            <div class="product-image">
			                <img src="${product.productImg}" 
						     alt="${product.productName}" 
						     onerror="this.src='/images/hispark.png'">
			                <div class="product-status ${product.productQuantity > 0 ? 'status-active' : 'status-soldout'}">
			                    ${product.productQuantity > 0 ? '판매중' : '품절'}
			                </div>
			            </div>
			            <div class="product-info">
			                <div class="product-category">MD</div>
			                <div class="product-name">${product.productName}</div>
			                <div class="product-price"><fmt:formatNumber value="${product.productPrice}" pattern="#,##0" />원</div>
			                <div class="product-stock ${product.productQuantity > 0 ? 'stock-normal' : 'stock-low'}">
			                    재고: ${product.productQuantity}개
			                </div>
			                <div class="product-actions">
			                    <button class="btn-small btn-edit" onclick="editProduct(${product.productId})">수정</button>
			                    <button class="btn-small btn-delete" onclick="deleteProduct(${product.productId})">삭제</button>
			                </div>
			            </div>
			        </div>
			    </c:forEach>
			</div>

                <!-- pagination -->
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
			                <button onclick="location.href='/adpage/shop'">
			                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
			                        <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
			                        <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
			                    </svg>
			                </button>
			                <button onclick="location.href='/adpage/shop?page=${page-1}'">
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
			                    <a href="/adpage/shop?page=${i}">${i}</a>
			                </c:if>
			            </c:forEach>
			        </div>
			
			        <div class="pg-btns">
			            <c:if test="${page < maxpage}">
			                <button onclick="location.href='/adpage/shop?page=${page+1}'">
			                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
			                        <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
			                    </svg>
			                </button>
			                <button onclick="location.href='/adpage/shop?page=${maxpage}'">
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
        </div>
    </div>

    <!-- 상품 등록/수정 모달 -->
    <div id="productModal" class="modal">
        <div class="modal-content">
            <div class="modal-header">
                <h3 id="modalTitle">새 상품 등록</h3>
                <span class="close" onclick="closeModal()">&times;</span>
            </div>
            <div class="modal-body">
                <form id="productForm" method = "post" action = "/shop"> 
                    <div class="form-group">
                        <label for="productName">상품명 *</label>
                        <input type="text" id="productName" name="productName" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="productPrice">판매가격 *</label>
                        <input type="text" id="productPrice" name="productPrice" min="0" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="productStock">재고수량 *</label>
                        <input type="number" id="productStock" name="productStock" min="0" required>
                    </div>
                    
                    
                    <div class="form-group">
                        <label for="productDelfee">배송비 *</label>
                        <input type="text" id="productDelfee" name="productDelfee" min="0" required>
                    </div>
                    
                    <div class="form-group">
                        <label for="productDescription">상품설명</label>
                        <textarea id="productDescription" name="productDescription" placeholder="상품에 대한 자세한 설명을 입력하세요"></textarea>
                    </div>
                    
                    <div class="form-group">
                        <label>상품 이미지</label>
                        <div class="image-upload" onclick="document.getElementById('imageInput').click()">
                            <p>클릭하거나 파일을 드래그하여 이미지를 업로드하세요</p>
                            <input type="file" id="imageInput" multiple accept="image/*" style="display: none;">
                        </div>
                        <div id="previewContainer" class="preview-images"></div>
                    </div>
                    
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn-primary" onclick="saveProduct()">저장</button>
                <button class="btn-secondary" onclick="closeModal()">취소</button>
                <div class = "blank" style = "height : 50px;"></div>
            </div>
        </div>
    
    

    <script>
        let currentPage = 1;
        let editingProductId = null;


        // 새 상품 등록 모달 열기
        function openAddModal() {
		    editingProductId = null;
		    document.getElementById('modalTitle').textContent = '새 상품 등록';
		    document.getElementById('productForm').reset();
		    document.getElementById('previewContainer').innerHTML = '';
		    document.getElementById('imageInput').value = ''; // 파일 입력 초기화
		    document.getElementById('productModal').style.display = 'block';
		}


	    // 상품 수정 모달 열기 (서버 데이터 사용)
		function editProduct(productId) {
		    editingProductId = productId;
		    document.getElementById('modalTitle').textContent = '상품 정보 수정';
		    
		    $.ajax({
		        url: '/adpage/shop/detail?productId=' + productId,
		        type: 'GET',
		        dataType: 'json',
		        success: function(data) {
		            document.getElementById('productName').value = data.productName || '';
		            document.getElementById('productPrice').value = data.productPrice || '';
		            document.getElementById('productStock').value = data.productQuantity || '';
		            document.getElementById('productDelfee').value = data.delfee || '';
		            
		            // <br>을 엔터로 변환하여 textarea에 표시
		            const originalContent = data.productContent || '';
		            const convertedContent = originalContent.replace(/<br\s*\/?>/gi, '\n');
		            document.getElementById('productDescription').value = convertedContent;
		
		            const previewContainer = document.getElementById('previewContainer');
		            previewContainer.innerHTML = '';
		            
		            // 기존 이미지가 있으면 파일명 형태로 표시
		            if (data.productImg && data.productImg.trim() !== '' && data.productImg !== 'null' && !data.productImg.includes('hispark.png')) {
		                const fileName = data.productImg.split('/').pop(); // 경로에서 파일명만 추출
		                const fileNameDiv = document.createElement('div');
		                fileNameDiv.className = 'file-name-display';
		                fileNameDiv.innerHTML = '<div class="file-info existing-file" style = "border : 0.5px solid #d3d3d3; border-radius : 4px;">' +
		                '<span class="file-icon">📁</span>' +
		                '<span class="file-name">' + fileName + '</span>' +
		                '<button class="remove-file" onclick="removeFilePreview(this)" type="button" style = "margin : 0 5px 2px 5px;  padding : 0 2px; ">&times;</button>' +
		                '</div>';
		                previewContainer.appendChild(fileNameDiv);
		            }
		
		            document.getElementById('imageInput').value = '';
		            document.getElementById('productModal').style.display = 'block';
		        },
		        error: function(xhr, status, error) {
		            console.error('Error details:', xhr.responseText);
		            alert('상품 정보를 불러오는데 실패했습니다: ' + error);
		        }
		    });
		}
		 
		 
        // 상품 삭제
        function deleteProduct(productId) {
		    if (confirm('정말 이 상품을 삭제하시겠습니까?')) {
		    	$.ajax({
		            url: '/adpage/shop/delete?productId=' + productId,
		            type: 'POST',
		            success: function(response) {
		                alert('삭제 완료');
		                location.reload();
		            },
		            error: function(err) {
		                console.error(err);
		                alert('삭제 실패');
		            }
		        });
		    }
		}

        // 모달 닫기
        function closeModal() {
            document.getElementById('productModal').style.display = 'none';
        }

        // 상품 저장
		function saveProduct() {
		    console.log('saveProduct 함수 호출됨');
		    
		    // 필수값 체크
		    const productName = document.getElementById('productName').value.trim();
		    const productPrice = document.getElementById('productPrice').value.trim();
		    const productStock = document.getElementById('productStock').value.trim();
		    const productDelfee = document.getElementById('productDelfee').value.trim();
		    
		    if (!productName || !productPrice || !productStock || !productDelfee) {
		        alert('필수 항목을 모두 입력해주세요.');
		        return;
		    }
		    
		    const formData = new FormData();
		    
		    // 상품 설명에서 엔터를 <br>로 변환
		    const productDescription = document.getElementById('productDescription').value;
		    const convertedDescription = productDescription.replace(/\n/g, '<br>');
		    
		    // FormData에 데이터 추가
		    formData.append('productName', productName);
		    formData.append('productPrice', productPrice);
		    formData.append('productQuantity', productStock);
		    formData.append('delfee', productDelfee);
		    formData.append('productContent', convertedDescription); // 변환된 텍스트 사용
		    
		    // 이미지 파일 처리
		    const imageInput = document.getElementById('imageInput');
		    if (imageInput.files && imageInput.files.length > 0) {
		        formData.append('image', imageInput.files[0]);
		        console.log('이미지 파일 추가됨:', imageInput.files[0].name);
		    } else {
		        console.log('이미지 파일 없음');
		    }
		    
		    let ajaxUrl = '/adpage/shop';
		    
		    if (editingProductId) {
		        formData.append('productId', editingProductId);
		        ajaxUrl = '/adpage/shop/update';
		        console.log('수정 모드, productId:', editingProductId);
		    } else {
		        console.log('등록 모드');
		    }
		
		    $.ajax({
		        url: ajaxUrl,
		        type: 'POST',
		        data: formData,
		        processData: false,
		        contentType: false,
		        success: function(response) {
		            console.log('성공:', response);
		            if (editingProductId) {
		                alert('상품 정보가 수정되었습니다.');
		            } else {
		                alert('새 상품이 등록되었습니다.');
		            }
		            closeModal();
		            location.reload();
		        },
		        error: function(xhr, status, error) {
		            console.error('AJAX 오류:', xhr.responseText);
		            alert('저장 실패: ' + (xhr.responseJSON ? xhr.responseJSON.message : error));
		        }
		    });
		}


        // 이미지 업로드 처리
        document.getElementById('imageInput').addEventListener('change', function(e) {
		    const files = e.target.files;
		    const previewContainer = document.getElementById('previewContainer');
		    previewContainer.innerHTML = ''; // 기존 미리보기 초기화
		    
		    console.log('파일 선택됨:', files.length);
		    
		    if (files.length > 0) {
		        const file = files[0]; // 첫 번째 파일만 사용
		        console.log('파일 정보:', file.name, file.type, file.size);
		        
		        if (file.type.startsWith('image/')) {
		            // 파일명을 표시하는 div 생성
		            const fileNameDiv = document.createElement('div');
		            fileNameDiv.className = 'file-name-display';
		            fileNameDiv.innerHTML = '<div class="file-info existing-file" style = "border : 0.5px solid #d3d3d3; border-radius : 4px;">' +
	                '<span class="file-icon">📁</span>' +
	                '<span class="file-name">' + file.name + '</span>' +
	                '<button class="remove-file" onclick="removeFilePreview(this)" type="button" style = "margin : 0 5px 2px 5px;  padding : 0 2px; ">&times;</button>' +
	                '</div>';
	                
		            previewContainer.appendChild(fileNameDiv);
		        } else {
		            alert('이미지 파일만 선택 가능합니다.');
		        }
		    }
		});

        // 드래그 앤 드롭 처리
        const uploadArea = document.querySelector('.image-upload');
        
        uploadArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            uploadArea.classList.add('dragover');
        });
        
        uploadArea.addEventListener('dragleave', () => {
            uploadArea.classList.remove('dragover');
        });
        
        uploadArea.addEventListener('drop', (e) => {
            e.preventDefault();
            uploadArea.classList.remove('dragover');
            const files = e.dataTransfer.files;
            document.getElementById('imageInput').files = files;
            document.getElementById('imageInput').dispatchEvent(new Event('change'));
        });

        function removeFilePreview(button) {
            console.log('파일명 표시 제거');
            button.parentElement.parentElement.remove();
            document.getElementById('imageInput').value = '';
        }

        document.getElementById('statusFilter').addEventListener('change', function() {
            filterProducts();
        });

        function filterProducts() {
            const filterValue = document.getElementById('statusFilter').value;
            const productCards = document.querySelectorAll('.product-card');
            
            productCards.forEach(card => {
                const statusElement = card.querySelector('.product-status');
                const stockElement = card.querySelector('.product-stock');
                
                const stockText = stockElement.textContent;
                const stockNumber = parseInt(stockText.match(/\d+/)[0]);
                
                let showCard = true;
                
                switch(filterValue) {
                    case 'all':
                        showCard = true;
                        break;
                    case 'active':
                        showCard = stockNumber > 0;
                        break;
                    case 'soldout':
                        showCard = stockNumber === 0;
                        break;
                }
                
                if (showCard) {
                    card.style.display = 'block';
                } else {
                    card.style.display = 'none';
                }
            });
            
            console.log('필터 적용:', filterValue);
        }
        
        
        
        // 모달 외부 클릭 시 닫기
        window.addEventListener('click', function(e) {
            const modal = document.getElementById('productModal');
            if (e.target === modal) {
                closeModal();
            }
        });

    </script>
</body>
</html>
