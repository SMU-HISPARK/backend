<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
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

        .pagination {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 10px;
            margin-top: 40px;
        }

        .pagination button {
            padding: 8px 12px;
            border: 1px solid #ddd;
            background: white;
            cursor: pointer;
            border-radius: 4px;
        }

        .pagination button.active {
            background: #1a1a1a;
            color: white;
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
                <a href="/adpage/graphCommu">커뮤니티 그래프</a>
            </li>
            <li>
                <a href="/adpage/graphShop">주문 그래프</a>
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
                    <div class="product-card">
                        <div class="product-image">
                            <img src="../images/hoodie1.jpg" alt="한정판 후드티" onerror="this.src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjgwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI4MCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyODAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0xMTAgODBIMTcwVjEyMEgxMTBWODBaIiBmaWxsPSIjREREREREIi8+Cjx0ZXh0IHg9IjE0MCIgeT0iMTA1IiBmaWxsPSIjOTk5OTk5IiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmb250LXNpemU9IjEyIj5JbWFnZTwvdGV4dD4KPC9zdmc+'">
                            <div class="product-status status-active">판매중</div>
                        </div>
                        <div class="product-info">
                            <div class="product-category">MD</div>
                            <div class="product-name">한정판 후드티 (블랙)</div>
                            <div class="product-price">₩89,000</div>
                            <div class="product-stock stock-low">재고: 12개</div>
                            <div class="product-actions">
                            	<button class="btn-small btn-edit" onclick="editProduct(1)">수정</button>
                                <button class="btn-small btn-delete" onclick="deleteProduct(1)">삭제</button>
                            </div>
                        </div>
                    </div>

                    <div class="product-card">
                        <div class="product-image">
                            <img src="../images/keyring1.jpg" alt="아크릴 키링" onerror="this.src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjgwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI4MCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyODAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0xMTAgODBIMTcwVjEyMEgxMTBWODBaIiBmaWxsPSIjREREREREIi8+Cjx0ZXh0IHg9IjE0MCIgeT0iMTA1IiBmaWxsPSIjOTk5OTk5IiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmb250LXNpemU9IjEyIj5JbWFnZTwvdGV4dD4KPC9zdmc+'">
                            <div class="product-status status-active">판매중</div>
                        </div>
                        <div class="product-info">
                            <div class="product-category">MD</div>
                            <div class="product-name">아크릴 키링 세트</div>
                            <div class="product-price">₩15,000</div>
                            <div class="product-stock stock-normal">재고: 156개</div>
                            <div class="product-actions">
                            	<button class="btn-small btn-edit" onclick="editProduct(1)">수정</button>
                                <button class="btn-small btn-delete" onclick="deleteProduct(2)">삭제</button>
                            </div>
                        </div>
                    </div>

                    <div class="product-card">
                        <div class="product-image">
                            <img src="../images/sticker1.jpg" alt="스티커 팩" onerror="this.src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjgwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI4MCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyODAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0xMTAgODBIMTcwVjEyMEgxMTBWODBaIiBmaWxsPSIjREREREREIi8+Cjx0ZXh0IHg9IjE0MCIgeT0iMTA1IiBmaWxsPSIjOTk5OTk5IiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmb250LXNpemU9IjEyIj5JbWFnZTwvdGV4dD4KPC9zdmc+'">
                            <div class="product-status status-soldout">품절</div>
                        </div>
                        <div class="product-info">
                            <div class="product-category">MD</div>
                            <div class="product-name">스티커 팩 (50매)</div>
                            <div class="product-price">₩12,000</div>
                            <div class="product-stock stock-low">재고: 0개</div>
                            <div class="product-actions">
                                <button class="btn-small btn-edit" onclick="editProduct(1)">수정</button>
                                <button class="btn-small btn-delete" onclick="deleteProduct(3)">삭제</button>
                            </div>
                        </div>
                    </div>

                    <div class="product-card">
                        <div class="product-image">
                            <img src="../images/bag1.jpg" alt="에코백" onerror="this.src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjgwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI4MCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyODAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0xMTAgODBIMTcwVjEyMEgxMTBWODBaIiBmaWxsPSIjREREREREIi8+Cjx0ZXh0IHg9IjE0MCIgeT0iMTA1IiBmaWxsPSIjOTk5OTk5IiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmb250LXNpemU9IjEyIj5JbWFnZTwvdGV4dD4KPC9zdmc+'">
                            <div class="product-status status-active">판매중</div>
                        </div>
                        <div class="product-info">
                            <div class="product-category">MD</div>
                            <div class="product-name">에코백 (캔버스)</div>
                            <div class="product-price">₩25,000</div>
                            <div class="product-stock stock-normal">재고: 67개</div>
                            <div class="product-actions">
                            	<button class="btn-small btn-edit" onclick="editProduct(1)">수정</button>
                                <button class="btn-small btn-delete" onclick="deleteProduct(4)">삭제</button>
                            </div>
                        </div>
                    </div>


                    <div class="product-card">
                        <div class="product-image">
                            <img src="../images/notebook1.jpg" alt="노트북" onerror="this.src='data:image/svg+xml;base64,PHN2ZyB3aWR0aD0iMjgwIiBoZWlnaHQ9IjIwMCIgdmlld0JveD0iMCAwIDI4MCAyMDAiIGZpbGw9Im5vbmUiIHhtbG5zPSJodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyI+CjxyZWN0IHdpZHRoPSIyODAiIGhlaWdodD0iMjAwIiBmaWxsPSIjRjBGMEYwIi8+CjxwYXRoIGQ9Ik0xMTAgODBIMTcwVjEyMEgxMTBWODBaIiBmaWxsPSIjREREREREIi8+Cjx0ZXh0IHg9IjE0MCIgeT0iMTA1IiBmaWxsPSIjOTk5OTk5IiB0ZXh0LWFuY2hvcj0ibWlkZGxlIiBmb250LXNpemU9IjEyIj5JbWFnZTwvdGV4dD4KPC9zdmc+'">
                            <div class="product-status status-active">판매중</div>
                        </div>
                        <div class="product-info">
                            <div class="product-category">MD</div>
                            <div class="product-name">브랜드 노트 (A5)</div>
                            <div class="product-price">₩18,000</div>
                            <div class="product-stock stock-normal">재고: 92개</div>
                            <div class="product-actions">
                            	<button class="btn-small btn-edit" onclick="editProduct(1)">수정</button>
                                <button class="btn-small btn-delete" onclick="deleteProduct(6)">삭제</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- pagination -->
            <div class="pg-container">
		      <div class="paginator">
		          <div class="pg-btns">
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M21.9323 22.5362C22.6042 21.8162 22.6042 20.7122 21.8842 20.0162L13.5562 12.0002L23.1562 2.76019L21.4762 1.00819L10.0282 12.0002L21.4763 22.9922L21.9323 22.5122L21.9323 22.5362Z" fill="currentColor"/>
		                      <path d="M12.4786 22.5362C13.1506 21.8162 13.1506 20.7122 12.4306 20.0162L4.10256 12.0002L13.7266 2.76019L12.0466 1.00819L0.598563 12.0002L12.0226 22.9922L12.4786 22.5122L12.4786 22.5362Z" fill="currentColor"/>
		                  </svg>
		              </button>
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M16.3325 1.47C17.0125 2.18 16.9925 3.3 16.2825 3.98L7.9425 12L17.5625 21.25L15.8825 23L4.4425 12L15.8825 1L16.3325 1.47Z" fill="currentColor"/>
		                  </svg>
		              </button>
		          </div>
		
		          <div class="pg-numbers">
		              <a class="active">1</a>
		              <a href="#">2</a>
		              <a href="#">3</a>
		              <a href="#">4</a>
		              <a href="#">5</a>
		          </div>
		
		          <div class="pg-btns">
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M7.6675 1.47C6.9875 2.18 7.0075 3.3 7.7175 3.98L16.0575 12L6.4375 21.25L8.1175 23L19.5575 12L8.1175 1L7.6675 1.47Z" fill="currentColor"/>
		                  </svg>
		              </button>
		              <button>
		                  <svg width="24" height="24" viewBox="0 0 24 24" fill="none">
		                      <path d="M2.06775 1.46381C1.39575 2.18381 1.39575 3.28781 2.11575 3.98381L10.4437 11.9998L0.84375 21.2398L2.52375 22.9918L13.9718 11.9998L2.52375 1.00781L2.06775 1.48781V1.46381Z" fill="currentColor"/>
		                      <path d="M11.5214 1.46381C10.8494 2.18381 10.8494 3.28781 11.5694 3.98381L19.8974 11.9998L10.2734 21.2398L11.9534 22.9918L23.4014 11.9998L11.9774 1.00781L11.5214 1.48781V1.46381Z" fill="currentColor"/>
		                  </svg>
		              </button>
		          </div>
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
                <form id="productForm">
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


        // 페이지 변경
        function changePage(page) {
            if (page === 'prev' && currentPage > 1) {
                currentPage--;
            } else if (page === 'next') {
                currentPage++;
            } else if (typeof page === 'number') {
                currentPage = page;
            }
            
            // 페이지네이션 버튼 업데이트
            document.querySelectorAll('.pagination button').forEach(btn => {
                btn.classList.remove('active');
            });
            
            console.log('현재 페이지:', currentPage);
        }

        // 새 상품 등록 모달 열기
        function openAddModal() {
            editingProductId = null;
            document.getElementById('modalTitle').textContent = '새 상품 등록';
            document.getElementById('productForm').reset();
            document.getElementById('previewContainer').innerHTML = '';
            document.getElementById('productModal').style.display = 'block';
        }

        // 상품 수정 모달 열기
        function editProduct(productId) {
            editingProductId = productId;
            document.getElementById('modalTitle').textContent = '상품 정보 수정';
            
            // 기존 데이터 로드 (실제로는 서버에서 데이터를 가져와야 함)
            const sampleData = {
                1: { name: '한정판 후드티 (블랙)',price: 89000, stock: 12, description: '프리미엄 소재의 한정판 후드티입니다.', status: 'active' },
                2: { name: '아크릴 키링 세트', price: 15000, stock: 156, description: '고품질 아크릴로 제작된 키링 세트입니다.', status: 'active' },
                3: { name: '스티커 팩 (50매)', price: 12000, stock: 0, description: '다양한 디자인의 스티커 50매 세트입니다.', status: 'soldout' },
                4: { name: '에코백 (캔버스)', price: 25000, stock: 67, description: '친환경 캔버스 소재의 에코백입니다.', status: 'active' },
                5: { name: '마우스패드 (대형)', price: 35000, stock: 23, description: '게이밍용 대형 마우스패드입니다.', status: 'active' },
                6: { name: '브랜드 노트 (A5)', price: 18000, stock: 92, description: 'A5 사이즈의 프리미엄 노트입니다.', status: 'active' }
            };
            
            const data = sampleData[productId];
            if (data) {
                document.getElementById('productName').value = data.name;
                document.getElementById('productPrice').value = data.price;
                document.getElementById('productStock').value = data.stock;
                document.getElementById('productDescription').value = data.description;
            }
            
            document.getElementById('productModal').style.display = 'block';
        }

     // 상품 수정 모달 열기
        function editProduct(productId) {
            editingProductId = productId;
            document.getElementById('modalTitle').textContent = '상품 정보 수정';
            
            // 기존 데이터 로드 (실제로는 서버에서 데이터를 가져와야 함)
            const sampleData = {
                1: { name: '한정판 후드티 (블랙)', price: 89000, stock: 12, description: '프리미엄 소재의 한정판 후드티입니다.', status: 'active' },
                2: { name: '아크릴 키링 세트', price: 15000, stock: 156, description: '고품질 아크릴로 제작된 키링 세트입니다.', status: 'active' },
                3: { name: '스티커 팩 (50매)', price: 12000, stock: 0, description: '다양한 디자인의 스티커 50매 세트입니다.', status: 'soldout' },
                4: { name: '에코백 (캔버스)', price: 25000, stock: 67, description: '친환경 캔버스 소재의 에코백입니다.', status: 'active' },
                5: { name: '마우스패드 (대형)', price: 35000, stock: 23, description: '게이밍용 대형 마우스패드입니다.', status: 'active' },
                6: { name: '브랜드 노트 (A5)', price: 18000, stock: 92, description: 'A5 사이즈의 프리미엄 노트입니다.', status: 'active' }
            };
            
            const data = sampleData[productId];
            if (data) {
                document.getElementById('productName').value = data.name;
                document.getElementById('productPrice').value = data.price;
                document.getElementById('productStock').value = data.stock;
                document.getElementById('productDescription').value = data.description;
            }
            
            document.getElementById('productModal').style.display = 'block';
        }

        // 상품 삭제
        function deleteProduct(productId) {
            if (confirm('정말 이 상품을 삭제하시겠습니까? 이 작업은 되돌릴 수 없습니다.')) {
                alert(`상품 ${productId}이 삭제되었습니다.`);
                location.reload();
            }
        }

        // 모달 닫기
        function closeModal() {
            document.getElementById('productModal').style.display = 'none';
        }

        // 상품 저장
        function saveProduct() {
            const form = document.getElementById('productForm');
            const formData = new FormData(form);
            
            // 필수 항목 검증
            if (!formData.get('productName') || !formData.get('productPrice') || !formData.get('productStock')) {
                alert('필수 항목을 모두 입력해주세요.');
                return;
            }
            
            if (editingProductId) {
                alert('상품 정보가 수정되었습니다.');
            } else {
                alert('새 상품이 등록되었습니다.');
            }
            
            closeModal();
            location.reload();
        }

        // 이미지 업로드 처리
        document.getElementById('imageInput').addEventListener('change', function(e) {
            const files = e.target.files;
            const previewContainer = document.getElementById('previewContainer');
            
            Array.from(files).forEach((file, index) => {
            	// PNG,JPEG,GIF,SVG 가능
                if (file.type.startsWith('image/')) {
                    const reader = new FileReader();
                    reader.onload = function(e) {
                        const previewItem = document.createElement('div');
                        previewItem.className = 'preview-item';
                        previewItem.innerHTML = `
                            <img src="${e.target.result}" alt="Preview">
                            <button class="remove-preview" onclick="removePreview(this)">&times;</button>
                        `;
                        previewContainer.appendChild(previewItem);
                    };
                    reader.readAsDataURL(file);
                }
            });
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

        // 이미지 미리보기 제거
        function removePreview(button) {
            button.parentElement.remove();
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
