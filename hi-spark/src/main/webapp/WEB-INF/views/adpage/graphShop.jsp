<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        .export-btn {
            padding: 10px 20px;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
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
                <a href="/adpage/graphCommu">커뮤니티 지수</a>
            </li>
            <li class="selected">
                <a href="/adpage/graphShop">주문 통계</a>
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
                            <option value="today" <c:if test="${currentPeriod == 'today'}">selected</c:if>>오늘</option>
                            <option value="week" <c:if test="${currentPeriod == 'week'}">selected</c:if>>최근 7일</option>
                            <option value="month" <c:if test="${currentPeriod == 'month'}">selected</c:if>>최근 1개월</option>
                            <option value="custom" <c:if test="${currentPeriod == 'custom'}">selected</c:if>>사용자 지정</option>
                        </select>
                        
                        <input type="date" id="startDate" style="display: none;">
                        <input type="date" id="endDate" style="display: none;">
                        <button class="export-btn" onclick="exportSalesData()">Excel 내보내기</button>
                    </div>
                </div>

                <div class="sales-summary">
                    <div class="summary-card">
                        <h3>총 매출</h3>
                        <div class="summary-value">
                            <c:choose>
                                <c:when test="${not empty totalSales}">
                                    <fmt:formatNumber value="${totalSales}" pattern="#,##0" />원
                                </c:when>
                                <c:otherwise>0원</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="summary-change positive">데이터 로드됨</div>
                    </div>
                    <div class="summary-card">
                        <h3>총 주문 건수</h3>
                        <div class="summary-value">
                            <c:choose>
                                <c:when test="${not empty totalOrders}">
                                    <fmt:formatNumber value="${totalOrders}" pattern="#,##0" />건
                                </c:when>
                                <c:otherwise>0건</c:otherwise>
                            </c:choose>
                        </div>
                        <div class="summary-change positive">데이터 로드됨</div>
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

    <script>
    function fetchSalesChart(period, startDate='', endDate='') {
        const params = { period };
        if(period === 'custom'){
            params.startDate = startDate;
            params.endDate = endDate;
        }

        $.ajax({
            url: '/adpage/graphShop/data',
            type: 'GET',
            data: params,
            dataType: 'json',
            success: function(data){
                console.log('받은 데이터:', data); // 디버깅용
                
                const salesData = data.sales || {};
                const orderData = data.orders || {};

                const labels = Object.keys(salesData).sort();
                const sales = labels.map(d => salesData[d] || 0);
                const orders = labels.map(d => orderData[d] || 0);

                console.log('차트 데이터:', { labels, sales, orders }); // 디버깅용

                // Chart.js 갱신
                const ctx = document.getElementById('salesChart').getContext('2d');
                
                // 기존 차트가 있다면 삭제
                try {
                    if(window.salesChart && typeof window.salesChart.destroy === 'function') {
                        window.salesChart.destroy();
                    }
                } catch(e) {
                    console.log('차트 삭제 중 오류 (무시 가능):', e);
                }

                window.salesChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: labels,
                        datasets: [
                            { 
                                label: '매출 (원)', 
                                data: sales, 
                                borderColor:'#1a1a1a', 
                                backgroundColor:'rgba(26,26,26,0.1)', 
                                tension:0.4, 
                                fill:true,
                                yAxisID: 'y'
                            },
                            { 
                                label: '주문 건수', 
                                data: orders, 
                                borderColor:'#28a745', 
                                backgroundColor:'rgba(40,167,69,0.1)', 
                                tension:0.4, 
                                fill:true,
                                yAxisID: 'y1'
                            }
                        ]
                    },
                    options:{
                        responsive:true,
                        maintainAspectRatio:false,
                        interaction: {
                            mode: 'index',
                            intersect: false,
                        },
                        scales:{
                            y: {
                                type: 'linear',
                                display: true,
                                position: 'left',
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: '매출 (원)'
                                }
                            },
                            y1: {
                                type: 'linear',
                                display: true,
                                position: 'right',
                                beginAtZero: true,
                                title: {
                                    display: true,
                                    text: '주문 건수'
                                },
                                grid: {
                                    drawOnChartArea: false,
                                },
                            }
                        },
                        plugins:{ 
                            legend:{ display:true, position:'top' }
                        }
                    }
                });
            },
            error: function(xhr, status, err){
                console.error('주문 그래프 로드 실패:', xhr.responseText, status, err);
                alert('데이터 로드에 실패했습니다: ' + (xhr.responseJSON?.message || err));
            }
        });
    }
    
    $(document).ready(function(){
        // 1. 페이지 로드 시 기본 기간 'week'로 차트 렌더링
        fetchSalesChart('week');

        // 2. 기간 선택 변경
        $('#periodSelect').on('change', function(){
            const period = $(this).val();
            if(period === 'custom'){
                $('#startDate, #endDate').show();
            } else {
                $('#startDate, #endDate').hide();
                fetchSalesChart(period);
            }
        });

        // 3. custom 날짜 변경 시
        $('#startDate, #endDate').on('change', function(){
            const period = $('#periodSelect').val();
            const start = $('#startDate').val();
            const end = $('#endDate').val();
            if(period === 'custom' && start && end){
                fetchSalesChart('custom', start, end);
            }
        });
    });

    // 엑셀 내보내기
    function exportSalesData(){
        const period = $('#periodSelect').val();
        let url = `/adpage/excelShop?period=${period}`;
        if(period === 'custom'){
            const start = $('#startDate').val();
            const end = $('#endDate').val();
            url += `&startDate=${start}&endDate=${end}`;
        }
        window.location.href = url;
    }
    </script>
</body>
</html>