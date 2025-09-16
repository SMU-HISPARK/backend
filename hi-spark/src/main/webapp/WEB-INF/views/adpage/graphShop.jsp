<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.9.1/chart.min.js"></script>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/mypage/mypage.css">
    <title>관리자페이지 - 주문상품 판매량</title>
    <style>
    	.container{ margin-top : 50px;}
        .sales-container {
            padding: 20px;
            max-width: 1230px;
            margin: 0 auto;
        }

        .sales-header {
            margin-bottom: 30px;
            text-align: center;
        }

        .sales-header h2 {
            font-size: 24px;
            color: #1a1a1a;
            margin-bottom: 10px;
        }

        .filter-section {
            display: flex;
            justify-content: center;
            gap: 15px;
            margin-bottom: 20px;
            align-items: center;
            flex-wrap: wrap;
        }

        .filter-section select, .filter-section input {
            padding: 10px 12px;
            border: 1px solid #ddd;
            font-size: 14px;
            border-radius: 4px;
        }

        .sales-summary {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .summary-card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
        }

        .summary-card h3 {
            font-size: 16px;
            color: #666;
            margin-bottom: 10px;
        }

        .summary-value {
            font-size: 28px;
            font-weight: bold;
            color: #1a1a1a;
            margin-bottom: 5px;
        }

        .summary-change {
            font-size: 14px;
            font-weight: 500;
        }

        .summary-change.positive {
            color: #28a745;
        }

        .summary-change.negative {
            color: #dc3545;
        }

        .charts-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
            margin-bottom: 40px;
        }

        .chart-container {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            width : 620px;
        }

        .chart-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
        }

        .chart-wrapper {
            position: relative;
            height: 300px;
        }

        .products-table {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
            margin-bottom: 30px;
        }

        .table-header {
            background: #f8f9fa;
            padding: 15px 20px;
            font-weight: bold;
            border-bottom: 1px solid #e0e0e0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .table-content {
            max-height: 500px;
            overflow-y: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px 15px;
            text-align: left;
            border-bottom: 1px solid #f0f0f0;
        }

        th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }

        .product-img {
            width: 50px;
            height: 50px;
            object-fit: cover;
            border-radius: 4px;
        }

        .product-name {
            max-width: 200px;
            overflow: hidden;
            text-overflow: ellipsis;
            white-space: nowrap;
        }

        .price {
            font-weight: 600;
            color: #1a1a1a;
        }

        .stock-low {
            color: #dc3545;
            font-weight: 600;
        }

        .stock-normal {
            color: #28a745;
            font-weight: 600;
        }


        .action-buttons {
            display: flex;
            gap: 10px;
        }

        .btn-small {
            padding: 5px 10px;
            font-size: 12px;
            border-radius: 4px;
            cursor: pointer;
            border: none;
        }

        .btn-edit {
            background: #17a2b8;
            color: white;
        }

        .btn-delete {
            background: #dc3545;
            color: white;
        }

        .refresh-btn, .export-btn {
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }

        .refresh-btn {
            background: #1a1a1a;
            color: white;
            border: none;
        }

        .export-btn {
            background: white;
            color: #1a1a1a;
            border: 1px solid #ddd;
        }

        @media (max-width: 768px) {
            .charts-row {
                grid-template-columns: 1fr;
            }
            
            .filter-section {
                flex-direction: column;
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
            <li class="selected">
                <a href="/adpage/graphShop">주문 그래프</a>
            </li>
            <li>
                <a href="/adpage/shop">샵 관리</a>
            </li>
        </ul>
        
        <div class="content">
            <div class="sales-container">
                <div class="sales-header">
                    <h2>굿즈샵 매출 분석</h2>
                    <div class="filter-section">
                        <select id="periodSelect">
                            <option value="today">오늘</option>
                            <option value="week" selected>최근 7일</option>
                            <option value="month">최근 1개월</option>
                            <option value="quarter">최근 3개월</option>
                            <option value="custom">사용자 지정</option>
                        </select>
                        
                        <input type="date" id="startDate" style="display: none;">
                        <input type="date" id="endDate" style="display: none;">
                        <button class="refresh-btn" onclick="refreshSalesData()">새로고침</button>
                        <button class="export-btn" onclick="exportSalesData()">Excel 내보내기</button>
                    </div>
                </div>

                <div class="sales-summary">
                    <div class="summary-card">
                        <h3>총 매출</h3>
                        <div class="summary-value">₩2,450,000</div>
                        <div class="summary-change positive">+18.2% ↗</div>
                    </div>
                    <div class="summary-card">
                        <h3>당일 주문 건수</h3>
                        <div class="summary-value">347</div>
                        <div class="summary-change positive">+12.1% ↗</div>
                    </div>
                </div>

                <div class="charts-row">
                    <div class="chart-container">
                        <div class="chart-title">일별 매출 추이</div>
                        <div class="chart-wrapper">
                            <canvas id="salesChart"></canvas>
                        </div>
                    </div>
                    
                </div>
                </div>
            </div>
        </div>
    </div>


    <script>
        // 일별 매출 추이 차트
        const salesCtx = document.getElementById('salesChart').getContext('2d');
        const salesChart = new Chart(salesCtx, {
            type: 'line',
            data: {
                labels: ['3/9', '3/10', '3/11', '3/12', '3/13', '3/14', '3/15'],
                datasets: [{
                    label: '매출 (만원)',
                    data: [180, 220, 150, 350, 280, 400, 245],
                    borderColor: '#1a1a1a',
                    backgroundColor: 'rgba(26, 26, 26, 0.1)',
                    tension: 0.4,
                    fill: true
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true,
                        ticks: {
                            callback: function(value) {
                                return value + '만원';
                            }
                        }
                    }
                },
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                }
            }
        });

        
        // 기간 선택 변경
        document.getElementById('periodSelect').addEventListener('change', function() {
            const startDate = document.getElementById('startDate');
            const endDate = document.getElementById('endDate');
            
            if (this.value === 'custom') {
                startDate.style.display = 'inline-block';
                endDate.style.display = 'inline-block';
            } else {
                startDate.style.display = 'none';
                endDate.style.display = 'none';
            }
        });

        // 정렬 기준 변경
        document.getElementById('sortBy').addEventListener('change', function() {
            // 정렬 로직
            console.log('정렬 기준 변경:', this.value);
        });

        function refreshSalesData() {
            alert('매출 데이터를 새로고침합니다.');
            location.reload();
        }

        function exportSalesData() {
            if(confirm("엑셀 파일을 다운로드하시겠습니까?")){
            	window.location.href = "/adpage/excelShop";
            }
        }
    </script>
</body>
</html>
