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
    <title>관리자페이지 - 커뮤니티 활성지수</title>
    <style>
    	.menu{margin-top : 50px;}
        .stats-container {
            padding: 20px;
            max-width: 1230px;
            margin: 0 auto;
        }

        .stats-header {
            margin-bottom: 30px;
            text-align: center;
        }

        .stats-header h2 {
            font-size: 24px;
            color: #1a1a1a;
            margin-bottom: 10px;
        }

        .date-filter {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-bottom: 20px;
            align-items: center;
        }

        .date-filter select, .date-filter input {
            padding: 10px 12px;
            border: 1px solid #ddd;
            font-size: 14px;
        }

        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }

        .stat-card {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            text-align: center;
        }

        .stat-card h3 {
            font-size: 16px;
            color: #666;
            margin-bottom: 10px;
        }

        .stat-value {
            font-size: 32px;
            font-weight: bold;
            color: #1a1a1a;
            margin-bottom: 5px;
        }

        .stat-change {
            font-size: 14px;
            font-weight: 500;
        }

        .stat-change.positive {
            color: #28a745;
        }

        .stat-change.negative {
            color: #dc3545;
        }

        .chart-container {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 30px;
        }

        .chart-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 20px;
            text-align: center;
        }

        .chart-wrapper {
            position: relative;
            height: 400px;
        }

        .activity-table {
            background: white;
            border: 1px solid #e0e0e0;
            border-radius: 8px;
            overflow: hidden;
        }

        .table-header {
            background: #f8f9fa;
            padding: 15px 20px;
            font-weight: bold;
            border-bottom: 1px solid #e0e0e0;
        }

        .table-content {
            max-height: 400px;
            overflow-y: auto;
        }

        table {
            width: 100%;
            border-collapse: collapse;
        }

        th, td {
            padding: 12px 20px;
            text-align: left;
            border-bottom: 1px solid #f0f0f0;
        }

        th {
            background: #f8f9fa;
            font-weight: 600;
            color: #333;
        }


        .refresh-btn {
            background: #1a1a1a;
            color: white;
            border: none;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin: 0 5px;
        }


        .export-btn {
            background: white;
            color: #1a1a1a;
            border: 1px solid #ddd;
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="container">
        <ul class="menu">
            <li class="selected">
                <a href="/adpage/graphCommu">커뮤니티 그래프</a>
            </li>
            <li>
                <a href="/adpage/graphShop">주문 그래프</a>
            </li>
            <li>
                <a href="/adpage/shop">샵 관리</a>
            </li>
        </ul>
        
        <div class="content">
            <div class="stats-container">
                <div class="stats-header">
                    <h2>커뮤니티 활동 분석</h2>
                    <div class="date-filter">
                        <select id="periodSelect">
                            <option value="today">오늘</option>
                            <option value="week" selected>최근 7일</option>
                            <option value="month">최근 1개월</option>
                            <option value="custom">사용자 지정</option>
                        </select>
                        <input type="date" id="startDate" style="display: none;">
                        <input type="date" id="endDate" style="display: none;">
                        <button class="refresh-btn" onclick="refreshSalesData()">새로고침</button>
                        <button class="export-btn" onclick="exportData()">Excel 내보내기</button>
                    </div>
                </div>

                <div class="stats-grid">
                    <div class="stat-card">
                        <h3>신규 게시물 수</h3>
                        <div class="stat-value">89</div>
                        <div class="stat-change positive">+8.1% ↗</div>
                    </div>
                    <div class="stat-card">
                        <h3>신규 댓글 수</h3>
                        <div class="stat-value">456</div>
                        <div class="stat-change negative">-3.2% ↘</div>
                    </div>
                </div>

                <div class="chart-container">
                    <div class="chart-title">일별 커뮤니티 활동 추이</div>
                    <div class="chart-wrapper">
                        <canvas id="activityChart"></canvas>
                    </div>
                </div>

                
                </div>
            </div>
        </div>
    </div>


    <script>
        // Chart.js를 사용한 활동 추이 차트
        const ctx = document.getElementById('activityChart').getContext('2d');
        const activityChart = new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['3/9', '3/10', '3/11', '3/12', '3/13', '3/14', '3/15'],
                datasets: [{
                    label: '새 글',
                    data: [65, 72, 58, 89, 76, 95, 89],
                    borderColor: '#28a745',
                    backgroundColor: 'rgba(40, 167, 69, 0.1)',
                    tension: 0.4
                }, {
                    label: '댓글',
                    data: [320, 380, 290, 456, 420, 500, 456],
                    borderColor: '#17a2b8',
                    backgroundColor: 'rgba(23, 162, 184, 0.1)',
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                scales: {
                    y: {
                        beginAtZero: true
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

        function refreshSalesData() {
            alert('매출 데이터를 새로고침합니다.');
            location.reload();
        }
        
        function exportData() {
            if(confirm("엑셀 파일을 다운로드하시겠습니까?")){
            	window.location.href = "/adpage/excelCommu";
            }
        }
    </script>
</body>
</html>
