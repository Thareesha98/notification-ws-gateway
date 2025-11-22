package thareesha.campustalk.notification_ws_gateway.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import thareesha.campustalk.notification_ws_gateway.event.NotificationEvent;

@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "campustalk.notifications.queue")
    public void receiveNotification(String json) throws Exception {

        // Convert raw JSON string to NotificationEvent
        NotificationEvent event = mapper.readValue(json, NotificationEvent.class);

        System.out.println("📩 WS-GATEWAY RECEIVED EVENT: " + event);

        // Push immediately to WebSocket user queue
        template.convertAndSend(
                "/queue/notifications-" + event.getUserId(),
                event
        );
    }
}
