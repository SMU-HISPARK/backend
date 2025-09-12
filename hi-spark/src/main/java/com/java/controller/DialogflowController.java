package com.java.controller;

import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.QueryResult;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.protobuf.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class DialogflowController {

    private final String PROJECT_ID = "a--extt";

    @PostMapping("/message")
    public ResponseEntity<?> detectIntent(@RequestBody Map<String, String> body) {
        String message = body.get("message");
        String sessionId = body.getOrDefault("sessionId", UUID.randomUUID().toString());

        if (message == null || message.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "error", "message is required"));
        }

        try (SessionsClient sessionsClient = SessionsClient.create()) {
            SessionName session = SessionName.of(PROJECT_ID, sessionId);

            TextInput textInput = TextInput.newBuilder()
                    .setText(message)
                    .setLanguageCode("ko")
                    .build();

            QueryInput queryInput = QueryInput.newBuilder()
                    .setText(textInput)
                    .build();

            DetectIntentResponse response = sessionsClient.detectIntent(session, queryInput);
            QueryResult queryResult = response.getQueryResult();

            // Parameters를 Map<String, Object>로 변환
            Map<String, Object> params = new HashMap<>();
            queryResult.getParameters().getFieldsMap().forEach((k, v) -> params.put(k, convertValue(v)));

            Map<String, Object> resultMap = Map.of(
                    "queryText", queryResult.getQueryText(),
                    "intent", queryResult.hasIntent() ? queryResult.getIntent().getDisplayName() : null,
                    "parameters", params,
                    "fulfillmentText", queryResult.getFulfillmentText()
            );

            return ResponseEntity.ok(Map.of("success", true, "result", resultMap));
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
