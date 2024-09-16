package rbgusdlza.petpals.global.messagebroker;

public interface MessageService {
    void sendMessage(String exchange, String routingKey, Object message);
}
