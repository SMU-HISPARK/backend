const textarea = document.querySelector('.chat_text');
const sendBtn = document.querySelector('.sendChatBtn');
const middleDiv = document.querySelector('.middle_div');

let lastChatDate = null;

// 팝업에서 URL 파라미터로 전달된 ano 읽기
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
  "1": { name: "이승민", img: "../images/artistimage/승민_셀카.png" },
  "2": { name: "공유현", img: "../images/artistimage/유현_셀카.png" },
  "3": { name: "윤경", img: "../images/artistimage/경_셀카.png" },
  "4": { name: "박지온", img: "../images/artistimage/지온_셀카.png" },
  "5": { name: "한정훈", img: "../images/artistimage/정훈_셀카.png" }
};


// HTML 이스케이프
function escapeHtml(unsafe) {
  return unsafe.replace(/[&<"'>]/g, function(m) {
    return ({ '&':'&amp;', '<':'&lt;', '>':'&gt;', '"':'&quot;', "'":'&#039;' })[m];
  });
}

// 날짜 표시 + 채팅 말풍선 추가
function addChat(message, sender = "me") {
  const now = new Date();
  const currentDate = now.toISOString().split("T")[0];

  // 날짜 변경 시 날짜 라인 출력
  if (lastChatDate !== currentDate) {
    const options = { year: "numeric", month: "long", day: "numeric", weekday: "long" };
    const dateText = now.toLocaleDateString("ko-KR", options);

    middleDiv.insertAdjacentHTML("beforeend", `
      <hr class="date_line"/>
      <p class="date">${dateText}</p>
    `);

    lastChatDate = currentDate;
  }

  const time = now.toLocaleTimeString("ko-KR", { hour: "2-digit", minute: "2-digit" });


  if (sender === "me") {
      middleDiv.insertAdjacentHTML("beforeend", `
        <div class="chat ch2">
          <div class="textbox">${escapeHtml(message)}</div>
          <p class="chat_time">${time}</p>
        </div>
      `);
    } else {
      const artist = artists[artistAno];
      if (artist) {
        middleDiv.insertAdjacentHTML("beforeend", `
          <div class="chat ch1">
            <div class="icon"><img class="profile_img" src="${artist.img}" style="border-radius: 50%;"></div>
            <div class="chat_col">
              <p class="chat_name">${artist.name}</p>
              <div class="textbox">${escapeHtml(message)}</div>
            </div>
            <p class="chat_time">${time}</p>
          </div>
        `);
      }
    }

    // 스크롤 항상 최신 메시지로 이동
    middleDiv.scrollTop = middleDiv.scrollHeight;
  }

  // 메시지 전송
  async function sendMessage() {
    const text = textarea.value.trim();
    if (!text) return;

    addChat(text, "me");
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
        const reply = json.result.fulfillmentText || '응답이 없습니다.';
        addChat(reply, "other");
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