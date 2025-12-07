package thareesha.campustalk.notification_ws_gateway.event;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEvent {
    private Long id;
    private Long userId;
    private String title;
    private String message;
    private String type;
    private Long referenceId;
}
