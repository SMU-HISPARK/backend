const textarea = document.querySelector('.chat_text');
const sendBtn = document.querySelector('.sendChatBtn');
const middleDiv = document.querySelector('.middle_div');

let lastChatDate = null;

// URL 파라미터로 전달된 artist ano
const urlParams = new URLSearchParams(window.location.search);
const artistAno = urlParams.get('ano');

const projectIds = {
  "1": "s--ntkk",
  "2": "f--bnrd",
  "3": "k--kmfq",
  "4": "a--extt",
  "5": "u--dk9h"
};
const projectId = projectIds[artistAno];

const artists = {
  "1": { name: "이승민", img: "../images/artistimage/이승민_셀카.png" },
  "2": { name: "공유현", img: "../images/artistimage/공유현_셀카.png" },
  "3": { name: "윤경", img: "../images/artistimage/윤경_셀카.png" },
  "4": { name: "박지온", img: "../images/artistimage/박지온_셀카.png" },
  "5": { name: "한정훈", img: "../images/artistimage/한정훈_셀카.png" }
};

// HTML 이스케이프
function escapeHtml(unsafe) {
  return unsafe.replace(/[&<"'>]/g, function(m) {
    return ({ '&':'&amp;', '<':'&lt;', '>':'&gt;', '"':'&quot;', "'":'&#039;' })[m];
  });
}

// Chat 객체 기준으로 채팅 추가
function addChat(chat, sender) {
  const createdAt = chat.createdAt ? new Date(chat.createdAt) : new Date();
  const currentDate = createdAt.toISOString().split("T")[0];

  // 날짜 변경 시 라인 출력
  if (lastChatDate !== currentDate) {
    const options = { year: "numeric", month: "long", day: "numeric", weekday: "long" };
    const dateText = createdAt.toLocaleDateString("ko-KR", options);

    middleDiv.insertAdjacentHTML("beforeend", `
      <hr class="date_line"/>
      <p class="date">${dateText}</p>
    `);

    lastChatDate = currentDate;
  }

  const time = createdAt.toLocaleTimeString("ko-KR", { hour: "2-digit", minute: "2-digit" });

  if (sender === "me" || chat.send === 1) {
    middleDiv.insertAdjacentHTML("beforeend", `
      <div class="chat ch2">
        <div class="textbox">${escapeHtml(chat.message)}</div>
        <p class="chat_time">${time}</p>
      </div>
    `);
  } else {
    const artist = artists[artistAno];
    middleDiv.insertAdjacentHTML("beforeend", `
      <div class="chat ch1">
        <div class="icon"><img class="profile_img" src="${artist.img}" style="border-radius:50%;"></div>
        <div class="chat_col">
          <p class="chat_name">${artist.name}</p>
          <div class="textbox">${escapeHtml(chat.message)}</div>
        </div>
        <p class="chat_time">${time}</p>
      </div>
    `);
  }

  middleDiv.scrollTop = middleDiv.scrollHeight;
}

// 메시지 전송
async function sendMessage() {
  const text = textarea.value.trim();
  if (!text) return;

  addChat({ message: text, send: 1, createdAt: new Date() }, "me");
  textarea.value = "";

  try {
    const res = await fetch('/api/message', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        message: text,
        sessionId: 'session-' + artistAno,
        ano: artistAno,
        projectId
      })
    });

    if (!res.ok) throw new Error("서버 응답 실패");

    const json = await res.json();

    if (json.success) {
      const replyText = json.result.fulfillmentText || '응답이 없습니다.';
      addChat({ message: replyText, send: 0, createdAt: new Date() }, "other");
    } else {
      console.error('서버 응답 오류', json);
    }
  } catch (err) {
    console.error('fetch 에러:', err);
  }
}

// 이벤트 등록
sendBtn.addEventListener("click", sendMessage);
textarea.addEventListener("keydown", (e) => {
  if (e.key === "Enter" && !e.shiftKey) {
    e.preventDefault();
    sendMessage();
  }
});

// 페이지 로드 시 서버에서 받아온 채팅 history 출력
// window.historyData = [{message:"안녕", send:1, createdAt:"2025-09-17T02:35:00"}, ...]
if (window.historyData) {
    // 시간 순 정렬 (createdAt 기준)
    window.historyData.sort((a, b) => new Date(a.createdAt) - new Date(b.createdAt));
    window.historyData.forEach(chat => {
        addChat(chat, chat.send === 1 ? "me" : "other");
    });
}

