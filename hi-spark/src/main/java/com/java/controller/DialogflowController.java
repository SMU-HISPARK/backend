package com.java.controller;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.protobuf.Value;
import com.java.dto.Artist;
import com.java.dto.Chat;
import com.java.entity.Member;
import com.java.service.ArtistService;
import com.java.service.ChatService;
import com.java.service.MemberService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class DialogflowController {

    @Autowired MemberService memberService;
    @Autowired ChatService chatService;
    @Autowired ArtistService artistService;
    @Autowired HttpSession httpSession;

    @PostMapping("/message")
    public ResponseEntity<?> detectIntent(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String sessionId = body.getOrDefault("sessionId", UUID.randomUUID().toString());
        String anoStr = body.get("ano");
        String projectId = body.get("projectId");

        if (message == null || message.isEmpty() || anoStr == null || projectId == null) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "message, ano or projectId required"));
        }

        int ano = Integer.parseInt(anoStr);

        try {
            // 챗봇별 JSON 파일 경로
            String resourcePath = switch (projectId) {
            case "s--ntkk" -> "/keys/dialogflow-key_sm.json";
            case "f--bnrd" -> "/keys/dialogflow-key_yh.json";
            case "k--kmfq" -> "/keys/dialogflow-key_k.json";
            case "a--extt" -> "/keys/dialogflow-key_jo.json";
            case "u--dk9h" -> "/keys/dialogflow-key_jh.json";
            default -> throw new RuntimeException("Unknown projectId");
            };

            // resources에서 InputStream 가져오기
            InputStream credentialsStream = getClass().getResourceAsStream(resourcePath);
            if (credentialsStream == null) {
                throw new RuntimeException("키 파일을 찾을 수 없습니다: " + resourcePath);
            }

            GoogleCredentials credentials = GoogleCredentials.fromStream(credentialsStream)
                    .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));

            SessionsSettings sessionsSettings = SessionsSettings.newBuilder()
                    .setCredentialsProvider(FixedCredentialsProvider.create(credentials))
                    .build();

            try (SessionsClient sessionsClient = SessionsClient.create(sessionsSettings)) {
                SessionName session = SessionName.of(projectId, sessionId);

                TextInput textInput = TextInput.newBuilder()
                        .setText(message)
                        .setLanguageCode("ko")
                        .build();

                QueryInput queryInput = QueryInput.newBuilder().setText(textInput).build();
                DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
                String botReply = response.getQueryResult().getFulfillmentText();

                // DB 저장
                Artist artist = artistService.findById(ano);
                String loginId = (String) httpSession.getAttribute("session_id");
                Member member = memberService.findById(loginId);

                chatService.save(Chat.builder()
                        .member(member)
                        .artist(artist)
                        .send(1)
                        .message(message)
                        .build());

                chatService.save(Chat.builder()
                        .member(member)
                        .artist(artist)
                        .send(0)
                        .message(botReply)
                        .build());

                return ResponseEntity.ok(Map.of(
                    "success", true,
                    "result", Map.of(
                        "queryText", response.getQueryResult().getQueryText(),
                        "intent", response.getQueryResult().hasIntent() ? response.getQueryResult().getIntent().getDisplayName() : null,
                        "fulfillmentText", botReply
                    )
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("success", false, "error", e.getMessage()));
        }
    }


    // Protobuf Value를 Object로 변환
    private Object convertValue(Value value) {
        switch (value.getKindCase()) {
            case STRING_VALUE: return value.getStringValue();
            case NUMBER_VALUE: return value.getNumberValue();
            case BOOL_VALUE:   return value.getBoolValue();
            case NULL_VALUE:   return null;
            case STRUCT_VALUE: 
                Map<String, Object> map = new HashMap<>();
                value.getStructValue().getFieldsMap().forEach((k, v) -> map.put(k, convertValue(v)));
                return map;
            case LIST_VALUE:
                return value.getListValue().getValuesList().stream().map(this::convertValue).toList();
            default: return null;
        }
    }
}
