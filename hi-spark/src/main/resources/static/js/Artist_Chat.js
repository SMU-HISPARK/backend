const textarea = document.querySelector('.chat_text');
const sendBtn = document.querySelector('.sendChatBtn');
const middleDiv = document.querySelector('.middle_div');

let lastChatDate = null;

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

  if (lastChatDate !== currentDate) {
    const options = { year: "numeric", month: "long", day: "numeric", weekday: "long" };
    const dateText = now.toLocaleDateString("ko-KR", options);

    middleDiv.insertAdjacentHTML("beforeend", `
      <hr class="date_line"/>
      <p class="date">${dateText}</p>
    `);

    lastChatDate = currentDate;
  }

  if (sender === "me") {
    middleDiv.insertAdjacentHTML("beforeend", `
      <div class="chat ch2">
        <div class="textbox">${escapeHtml(message)}</div>
        <p class="chat_time">${now.toLocaleTimeString("ko-KR", { hour: "2-digit", minute: "2-digit" })}</p>
      </div>
    `);
  } else {
    middleDiv.insertAdjacentHTML("beforeend", `
      <div class="chat ch1">
        <div class="icon"><img class="profile_img" src="../images/artistimage/지온_셀카.png" style="border-radius: 50%;"></div>
        <div class="chat_col">
          <p class="chat_name">박지온</p>
          <div class="textbox">${escapeHtml(message)}</div>
        </div>
        <p class="chat_time">${now.toLocaleTimeString("ko-KR", { hour: "2-digit", minute: "2-digit" })}</p>
      </div>
    `);
  }

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
      body: JSON.stringify({ message: text, sessionId: 'session-1' })
    });
    const json = await res.json();

    if (json.success) {
      // fulfillmentText 직접 사용
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
