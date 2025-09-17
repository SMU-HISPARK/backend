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