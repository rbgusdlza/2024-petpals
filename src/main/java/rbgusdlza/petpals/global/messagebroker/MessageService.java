package rbgusdlza.petpals.global.message;

public interface MessageService {
    void sendMessage(String exchange, String routingKey, Object message);
}
