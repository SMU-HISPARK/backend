function preserveCurrentPage() {
    const currentUrl = window.location.href;
    // 정확한 action으로 폼 선택
    const forms = document.querySelectorAll('form[action="/member/login"]');

    forms.forEach(form => {
        let hiddenInput = form.querySelector('input[name="redirectTo"]');
        if (!hiddenInput) {
            hiddenInput = document.createElement('input');
            hiddenInput.type = 'hidden';
            hiddenInput.name = 'redirectTo';
            form.appendChild(hiddenInput);
        }
        hiddenInput.value = currentUrl;
    });
}

// 페이지 로드 시 실행
document.addEventListener('DOMContentLoaded', preserveCurrentPage);


const logoutLink = document.querySelector('a[href*="/member/logout"]');
if(logoutLink) {
    logoutLink.href = logoutLink.href + '?redirectTo=' + encodeURIComponent(window.location.href);
}