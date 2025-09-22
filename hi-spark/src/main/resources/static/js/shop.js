$(document).ready(function(){
	
	updateCartBadge();
	
	function updateCartBadge() {
	    // main/detail에서는 DOM이 없으므로 세션 값으로 가져오기
	    var count = parseInt($(".cartBadge").attr("data-count")) || 0;
		console.log(count)
	    if (count === 0) {
	        $(".cartBadge").hide();
	    } else {
	        $(".cartBadge").text(count).show();
	    }
	}
});

// 탭 기능 구현을 위한 스크립트
document.addEventListener('DOMContentLoaded', () => {
	const tabs = document.querySelectorAll('.product-tabs li');
	const contents = document.querySelectorAll('.tab-content');

	tabs.forEach(tab => {
		tab.addEventListener('click', (event) => {
			event.preventDefault(); // 페이지 새로고침 방지

			// 모든 탭과 콘텐츠에서 'is-active' 클래스 제거
			tabs.forEach(t => t.classList.remove('is-active'));
			contents.forEach(c => c.classList.remove('is-active'));

			// 클릭된 탭과 그에 맞는 콘텐츠에 'is-active' 클래스 추가
			tab.classList.add('is-active');
			const targetId = tab.getAttribute('data-tab');
			document.getElementById(targetId).classList.add('is-active');
		});
	});
});
