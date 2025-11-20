package thareesha.campustalk.notification_ws_gateway.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import thareesha.campustalk.notification_ws_gateway.event.NotificationEvent;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationEventListener {

    private final SimpMessagingTemplate template;
    private final ObjectMapper mapper = new ObjectMapper();

    @RabbitListener(queues = "campustalk.notifications.queue")
    public void receiveNotification(NotificationEvent event) {

        // Push to the correct user’s WebSocket queue
        template.convertAndSend(
                "/queue/notifications-" + event.getUserId(),
                event
        );
    }
}
