document.addEventListener('DOMContentLoaded', () => {
    const voteEndTimeElement = document.getElementById('voteEndTime');
    const voteEndStatusElement = document.getElementById('voteEndStatus');
    const voteForm = document.getElementById('voteForm');
    const submitButton = document.getElementById('submitButton');
    const voteOptionsContainer = document.querySelector('.vote-options');
    const voteItems = document.querySelectorAll('.vote-item');
    const hiddenVoteInput = document.getElementById('selectedItemNo');

    // 투표 종료 날짜를 가져와서 Date 객체로 변환
    const pollEndDateString = document.querySelector('.view-header').getAttribute('data-poll-end-date');
    if (!pollEndDateString) return; // 종료일이 없는 경우 함수 종료
    const pollEndDate = new Date(pollEndDateString);

    const updateTime = () => {
        const now = new Date();
        const diffMs = pollEndDate.getTime() - now.getTime();
        
        if (diffMs <= 0) {
            voteEndStatusElement.textContent = '(투표 종료)';
            voteEndStatusElement.classList.remove('imminent');
            voteEndStatusElement.classList.add('closed');
            submitButton.style.display = 'none';
            voteItems.forEach(item => item.style.cursor = 'default');
            return;
        }
        
        const diffHours = Math.floor(diffMs / (1000 * 60 * 60));
        const diffMinutes = Math.floor((diffMs % (1000 * 60 * 60)) / (1000 * 60));
        const diffSeconds = Math.floor((diffMs % (1000 * 60)) / 1000);

        voteEndStatusElement.textContent = `투표 진행중 (${diffHours}시간 ${diffMinutes}분 ${diffSeconds}초 남음)`;
    };

    updateTime();
    setInterval(updateTime, 1000);
    
    // 투표 항목 클릭 이벤트 리스너 (이벤트 위임 사용)
    voteOptionsContainer.addEventListener('click', (e) => {
        // 투표가 종료되었거나 이미 투표를 했다면 아무 동작도 하지 않음
        if (voteEndStatusElement.classList.contains('closed')) {
            return;
        }

        const clickedItem = e.target.closest('.vote-item');
        if (!clickedItem) {
            return; // .vote-item이 아닌 다른 영역 클릭 시 무시
        }
        
        const isSelected = clickedItem.classList.contains('selected');
        
        // 모든 투표 항목의 강조 효과 제거
        voteItems.forEach(item => item.classList.remove('selected'));
        
        if (isSelected) {
            // 이미 선택된 항목을 다시 클릭한 경우, 선택 해제
            hiddenVoteInput.value = ''; // 숨겨진 input 값 초기화
            submitButton.style.display = 'none'; // 제출 버튼 숨기기
            voteEndStatusElement.style.display = 'block'; // 상태 메시지 다시 표시
        } else {
            // 새로운 항목을 선택한 경우, 강조 효과 적용 및 값 업데이트
            const optionId = clickedItem.getAttribute('data-option-id');
            clickedItem.classList.add('selected');
            hiddenVoteInput.value = optionId;
            submitButton.style.display = 'block';
            voteEndStatusElement.style.display = 'none';
        }
    });

    // 폼 제출 이벤트 핸들러
    voteForm.addEventListener('submit', (e) => {
        if (!hiddenVoteInput.value) {
            e.preventDefault();
            alert('투표 항목을 선택해주세요.');
        } else if (!confirm('투표를 제출하시겠습니까? 한 번 선택시 변경 불가합니다.')) {
            e.preventDefault();
        }
    });

    // 초기 상태 설정
    submitButton.style.display = 'none';
    voteEndStatusElement.style.display = 'block';
});