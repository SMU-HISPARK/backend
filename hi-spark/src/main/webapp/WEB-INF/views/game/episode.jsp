<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>정훈과의 연애 시뮬레이션</title>
    <style>
        /* ---- 초기화 ---- */
        *{ margin:0;
           padding:0;
           box-sizing:border-box; }
           
       body {
        background-image: url("/images/episode/background.png");
        background-size: cover;
        background-position: center;
        background-repeat: no-repeat;
        background-attachment: fixed;
      }
          
        .game-container { width:100vw; 
        height:100vh; position:relative; }
        
        /* ---- 캐릭터 ---- */
        .character-area { 
          position:absolute;
          bottom:0;
          left:50%;
          transform:translateX(-50%);
          height:900px;
          z-index:2; 
          border:none;
          overflow:hidden;
      }
      .character-image { 
          width:100%;
          height:100%;
          object-fit: cover;
      }
        /* ---- 대화창 ---- */
        .dialogue-box {
         position:absolute;
         bottom:20px;
         left:20px;
         right:20px;
         background:rgba(255,255,255,.95);
         border-radius:20px;
         padding:25px;
         z-index:3;
         backdrop-filter:blur(10px); }
        .speaker-name { 
        font-weight:bold;
        color:#667eea;
        margin-bottom:10px;
        font-size:18px;
        display:flex;
        align-items:center; }
        .speaker-name.junghoon { color:#4facfe; }
        .dialogue-text { 
        font-size:16px;
        line-height:1.6;
        color:#333;
        margin-bottom:20px;
        min-height:60px; }
        .choices {
         display:flex;
         flex-direction:column;
         gap:10px; }
        .choice-button,.continue-button {
         border:none;
         padding:15px 20px;
         border-radius:15px;
         font-size:14px;
         cursor:pointer;
         font-weight:500;}
        .choice-button {
         background:linear-gradient(45deg,#ff9a9e,#fecfef);
         color:#333;
         text-align:center; }
        .continue-button {
         background:linear-gradient(45deg,#667eea,#764ba2);
         color:#fff;
         padding:12px 25px;
         text-align:center; }
        /* ---- 호감도 ---- */
        .affection-meter { 
        position:absolute;
        top:20px;
        left:20px;
        background:rgba(255,255,255,.9);
        padding:10px 15px;
        border-radius:20px;
        z-index:4;
        box-shadow:0 4px 15px rgba(0,0,0,.1); }
        .affection-bar { 
        width:150px;
        height:10px;
        background:#ddd;
        border-radius:5px;
        overflow:hidden;
        margin-top:5px; }
        .affection-fill { height:100%;
        background:linear-gradient(90deg,#ff9a9e,#fecfef);
        width:0%;
        transition:width .5s; }
        /* ---- 엔딩 ---- */
        .ending-screen { position:absolute;
        top:0;
        left:0;
        width:100%;
        height:100%;
        background:rgba(0,0,0,.8);
        color:#fff;
        display:flex;
        justify-content:center;
        align-items:center;
        flex-direction:column;
        z-index:10;
        text-align:center;
        padding:20px; }
        .ending-screen h1 { margin-bottom:20px; }
        .restart-button {
         padding:12px 20px;
         border:none;
         background:#ff6b9d;
         color:#fff;
         border-radius:10px;
         cursor:pointer; }
        .name-input { 
        background:rgba(255,255,255,.9);
        border:2px solid #667eea;
        padding:10px 15px;
        border-radius:10px;
        font-size:16px;
        margin:10px 0;
        width:100%; }
        .hidden { display: none; }
    </style>
</head>
<body>
<div class="game-container">
    <div class="affection-meter">
        <div>정훈과의 호감도</div>
        <div id="affectionText">0%</div>
        <div class="affection-bar"><div class="affection-fill" id="affectionFill"></div></div>
    </div>
    <div class="character-area">
       <img class="character-image" id="characterImage">
   </div>
    <div class="dialogue-box">
        <div class="speaker-name junghoon" id="speakerName"><span class="basketball-emoji">🏀</span>정훈</div>
        <div class="dialogue-text" id="dialogueText">체육관 문을 조심스럽게 열고 들어선다...</div>
        <div class="choices" id="choicesContainer"><button class="continue-button" onclick="nextDialogue()">계속</button></div>
    </div>
</div>
<div id="endingContainer" class="ending-screen hidden"></div>
   <script>
      let currentScene = 0;
      let isTyping = false;
      let playerName = "";
      let affectionLevel = 0;
      let pendingNextScene = 0; // 이름 입력 후 이동할 씬 번호를 저장
      
      const gameData = [
          {
              speaker: "나레이션",
              text: "체육관 문을 조심스럽게 열고 들어선다. 안쪽에서는 정훈이 연습 슛을 넣고 있고, 잠깐 숨을 고르며 물을 마시던 중 누군가 들어왔다는 것을 알아차리고 고개를 든다.",
              type: "continue",
              next: 1
          },
          {
              speaker: "정훈",
              text: "어? 새로 들어온다더니, 너구나?",
              type: "continue",
              next: 2,
              characterImage: "/images/episode/hi.png" 
          },
          {
              speaker: "나레이션",
              text: "정훈이 웃음이 먼저 앞서는 톤으로 멀리서부터 다가오며 손을 흔든다.",
              type: "choice",
              choices: [
                  { text: "(순간 살짝 당황하며) 안녕하세요…", next: 3, affectionChange: 0 },
                  { text: "(반갑게 웃으며) 안녕하세요!", next: 4, affectionChange: 5 }
              ]
          },
          {
              speaker: "정훈",
              text: "긴장했어? 괜찮아, 다들 처음엔 그랬어. 나도 그랬고.",
              type: "continue",
              affectionChange: 3,
              next: 5,
              characterImage: "/images/episode/smile.png" 
          },
          {
              speaker: "정훈",
              text: "오, 웃는다. 괜찮네. 첫날이면 다들 좀 얼어있기 마련인데.",
              type: "continue",
              next: 5,
              characterImage: "/images/episode/smile.png" 
          },
          {
              speaker: "정훈",
              text: "나는 한정훈이고, 2학년이야. 농구부는… 음, 생각보다 안 무서워. 내가 있어서. 너 이름은?",
              type: "input",
              inputPrompt: "당신의 이름을 입력해주세요:",
              next: 6,
              characterImage: "/images/episode/wink.png" 
          },
          {
              speaker: "정훈",
              text: "playerName? 이름 예쁘다. 그럼 이제부터 우리 부원이네?",
              type: "continue",
              affectionChange: 2,
              next: 7,
              characterImage: "/images/episode/wink.png" 
          },
          {
              speaker: "정훈",
              text: "근데 너, 운동 좀 해봤지? 왠지 딱 그런 느낌인데? 슛 한 번 해 볼래?",
              characterImage: "/images/episode/think.png" ,
              type: "choice",
              choices: [
                  { text: "한 번 해볼게요.", next: 8,affectionChange: 3 },
                  { text: "전 운동 잘 못하는데요…", next: 9, affectionChange: 2 },
                  { text: "제 전문이죠.", next: 10, affectionChange: 4 }
              ]
          },
          {
              speaker: "정훈",
              text: "오, 좋아. 골대는 저쪽이야. 자, 자신 있게 던져봐. 안 들어가면 내가 다시 주워줄게.",
              type: "continue",
              next: 11,
              characterImage: "/images/episode/smile.png" 
          },
          {
              speaker: "정훈",
              text: "괜찮아, 나도 처음엔 골대도 못 맞추고 그랬어. 그냥 가볍게 한 번 던져 봐. 내가 알려줄게.",
              type: "continue",
              next: 12,
              characterImage: "/images/episode/smile.png" 
          },
          {
              speaker: "정훈",
              text: "헐, 뭐야. 말투가 프로인데? 그럼 나 방해 안 할게. 바로 데뷔전 가시죠, playerName 선수.",
              type: "continue",
              next: 13,
              characterImage: "/images/episode/hands.png" 
          },
          {
              speaker: "나레이션",
              text: "슛을 쏜다. 공은 백보드를 맞고 깔끔하게 들어갔다.",
              type: "continue",
              next: 14
          },
          {
              speaker: "나레이션",
              text: "슛을 쏜다. 공은 림을 빙그르르 돌다가 들어간다.",
              type: "continue",
              next: 15
          },
          {
              speaker: "나레이션",
              text: "자연스럽게 드리블을 하며 골대 밑까지 달려가 레이업 슛을 한다. 공은 가볍게 골대에 들어갔다.",
              type: "continue",
              next: 16
          },
          {
              speaker: "정훈",
              text: "올~ 좀 하는데? 역시 운동 좀 해봤구나? 다음에 나랑 같이 연습할래?",
              type: "choice",
              affectionChange: 5,
              characterImage: "/images/episode/thumbsup.png" ,
              choices: [
                  { text: "네, 같이 연습해요!", next: 17, affectionChange: 7 },
                  { text: "시간 되면요.", next: 18, affectionChange: 3 }
              ]
          },
          {
              speaker: "정훈",
              text: "잠깐만… 너 못한다며? 나 완전 속았는데? 조금만 연습하면 진짜 금방 늘겠다. 시간 나면 내가 봐줄게.",
              type: "choice",
              characterImage: "/images/episode/laugh.png" ,
              affectionChange: 6,
              choices: [
                  { text: "정말요? 감사해요!", next: 19, affectionChange: 8 },
                  { text: "부담 주고 싶지 않은데…", next: 20, affectionChange: 4 }
              ]
          },
          {
              speaker: "정훈",
              text: "축하합니다, playerName 선수. 오늘의 MVP 인터뷰가 있겠습니다. 소감이 어떠신가요?",
              type: "choice",
              characterImage: "/images/episode/laugh.png" ,
              affectionChange: 8,
              choices: [
                  { text: "좋은 팀원들 덕분이에요.", next: 21, affectionChange: 10 },
                  { text: "아직 배울 게 많아요.", next: 22, affectionChange: 6 }
              ]
          },
          {
              speaker: "정훈",
              text: "좋아! 그럼 내일부터 방과 후에 같이 연습하자. 나도 혼자 하는 것보다 둘이서 하는 게 더 재밌거든.",
              type: "continue",
              characterImage: "/images/episode/hi.png" ,
              affectionChange: 5,
              next: 23
          },
          {
              speaker: "정훈",
              text: "그래도 괜찮아. 언제든지 마음 바뀌면 말해. 나는 여기서 거의 매일 연습하거든.",
              type: "continue",
              characterImage: "/images/episode/depressed.png" ,
              next: 23
          },
          {
              speaker: "정훈",
              text: "하하, 부담 갖지 마. 나도 누군가 가르쳐주니까 더 재밌어. 서로 도움이 되는 거야.",
              type: "continue",
              affectionChange: 4,
              characterImage: "/images/episode/smile.png" ,
              next: 23
          },
          {
              speaker: "정훈",
              text: "어우, 겸손하기까지 하네. 정말 부담스럽지 않아. 오히려 내가 더 기대돼.",
              type: "continue",
              affectionChange: 3,
              characterImage: "/images/episode/laugh.png" ,
              next: 23
          },
          {
              speaker: "정훈",
              text: "와 진짜 겸손한데? 팀워크도 좋고, 실력도 있고. 완벽하네! 잘 부탁해!",
              type: "continue",
              affectionChange: 8,
              characterImage: "/images/episode/thumbsup.png" ,
              next: 23
          },
          {
              speaker: "정훈",
              text: "그런 마음가짐이 더 좋아. 같이 성장해나가는 거지. 다음주에 3대3으로 연습경기도 해보자!",
              type: "continue",
              characterImage: "/images/episode/hi.png" ,
              affectionChange: 5,
              next: 23
          }
          
      ];
      
      function typeWriter(text, element, callback) {
          if (isTyping) return;
          isTyping = true;
          element.innerHTML = '';
          let i = 0;
          
          function type() {
              if (i < text.length) {
                  element.innerHTML += text.charAt(i);
                  i++;
                  setTimeout(type, 50);
              } else {
                  isTyping = false;
                  if (callback) callback();
              }
          }
          type();
      }
      
      
      function updateAffection(change) {
          affectionLevel = Math.max(0, Math.min(100, affectionLevel + change));
          document.getElementById('affectionFill').style.width = affectionLevel + '%';
          document.getElementById('affectionText').textContent = affectionLevel + '%';
      }
      
      function updateCharacterImage(imagePath) {
          const characterImage = document.getElementById('characterImage');
          console.log('updateCharacterImage called with:', imagePath);
          
          if (imagePath && characterImage) {
              characterImage.src = imagePath;
              console.log('Image src set to:', characterImage.src);
          }
      }
      
      function showScene(sceneIndex) {
          console.log('showScene called with index:', sceneIndex, 'gameData length:', gameData.length);
          
          if (sceneIndex >= gameData.length || sceneIndex < 0) {
              console.log('Scene index out of range, showing ending');
              showEnding();
              return;
          }
      
          const scene = gameData[sceneIndex];
          if (!scene) {
              console.log('Scene is undefined, showing ending');
              showEnding();
              return;
          }
          
          console.log('Current scene:', scene);
      
          const speakerElement = document.getElementById('speakerName');
          const dialogueElement = document.getElementById('dialogueText');
          const choicesElement = document.getElementById('choicesContainer');
      
          // 화자 설정
          if (scene.speaker === "정훈") {
              speakerElement.innerHTML = '<span class="basketball-emoji">🏀</span>정훈';
              speakerElement.className = 'speaker-name junghoon';
          } else if (scene.speaker === "나레이션") {
              speakerElement.innerHTML = '📖 나레이션';
              speakerElement.className = 'speaker-name';
          }
      
          // 플레이어 이름 교체
          let displayText = scene.text;
          if (playerName && displayText.includes('playerName')) {
              displayText = displayText.replace(/playerName/g, playerName);
          }
      
      
          if (scene.characterImage) {
              updateCharacterImage(scene.characterImage);
          }

          if (scene.affectionChange) updateAffection(scene.affectionChange);
          if (scene.skillTest) showSkillIndicator(playerSkillLevel);
      
          typeWriter(displayText, dialogueElement, () => {
              if (scene.type === 'choice') {
                  showChoices(scene.choices);
              } else if (scene.type === 'input') {
                  showInput(scene.inputPrompt, scene.next);
              } else if (scene.type === 'ending') {
                  showEnding();
              } else {
                  showContinueButton();
              }
          });
      }
      
      function showChoices(choices) {
          const choicesElement = document.getElementById('choicesContainer');
          choicesElement.innerHTML = '';
      
          choices.forEach(choice => {
              const button = document.createElement('button');
              button.className = 'choice-button';
              button.textContent = choice.text;
              button.onclick = () => {
                  if (choice.skill !== undefined) playerSkillLevel = choice.skill;
                  if (choice.affectionChange) updateAffection(choice.affectionChange);
                  currentScene = choice.next;
                  showScene(currentScene);
              };
              choicesElement.appendChild(button);
          });
      }
      
      function showInput(prompt, nextScene) {
          pendingNextScene = nextScene; // 다음 씬 번호를 저장
          const choicesElement = document.getElementById('choicesContainer');
          choicesElement.innerHTML = `
              <div style="margin-bottom: 10px;">${prompt}</div>
              <input type="text" class="name-input" id="nameInput" placeholder="이름을 입력하세요" maxlength="10">
              <button class="continue-button" onclick="submitName()">확인</button>
          `;
          
          document.getElementById('nameInput').focus();
          document.getElementById('nameInput').addEventListener('keypress', function(e) {
              if (e.key === 'Enter') {
                  submitName();
              }
          });
      }
      
      function submitName() {
          const nameInput = document.getElementById('nameInput');
          if (nameInput && nameInput.value.trim()) {
              playerName = nameInput.value.trim();
              console.log('Player name set to:', playerName, 'Next scene:', pendingNextScene);
              currentScene = pendingNextScene;
              
              // 씬 번호가 유효한 범위인지 확인
              if (pendingNextScene >= 0 && pendingNextScene < gameData.length) {
                  showScene(currentScene);
              } else {
                  console.error('Invalid nextScene:', pendingNextScene);
                  showEnding();
              }
          } else {
              alert('이름을 입력해주세요!');
          }
      }
      
      function showContinueButton() {
          const choicesElement = document.getElementById('choicesContainer');
          choicesElement.innerHTML = '<button class="continue-button" onclick="nextDialogue()">계속</button>';
      }
      
      function nextDialogue() {
          if (isTyping) return;
          
          const scene = gameData[currentScene];
          if (scene && scene.next !== undefined) {
              currentScene = scene.next;
          } else {
              currentScene++;
          }
          
          if (currentScene < gameData.length) {
              showScene(currentScene);
          } else {
              showEnding();
          }
      }
      
      function showEnding() {
          let endingMessage = "";
          if (affectionLevel >= 70) {
              endingMessage = "정훈과 아주 가까워졌습니다! 🏀💕";
          } else if (affectionLevel >= 40) {
              endingMessage = "정훈과 좋은 친구가 되었습니다! 🏀😊";
          } else {
              endingMessage = "정훈과 농구부 동료가 되었습니다. 🏀";
          }
          
          const endingContainer = document.getElementById("endingContainer");
          endingContainer.classList.remove("hidden");  // 보이도록
          endingContainer.innerHTML =
              "<h1>" + endingMessage + "</h1>" +
              '<button class="restart-button" onclick="location.reload()" style="margin-bottom:10px;">다시 시작</button>' +
              '<button class="restart-button" onclick="location.href=\'/\'">메인으로</button>';
      }
      
      // 키보드 단축키
      document.addEventListener('keydown', function(e) {
          if (e.code === 'Space' && !isTyping) {
              e.preventDefault();
              nextDialogue();
          }
          
          if (e.key >= '1' && e.key <= '9') {
              const choiceButtons = document.querySelectorAll('.choice-button');
              const index = parseInt(e.key) - 1;
              if (choiceButtons[index] && !isTyping) {
                  choiceButtons[index].click();
              }
          }
      });
      
      // 게임 시작
      showScene(currentScene);
   </script>
</body>
</html>