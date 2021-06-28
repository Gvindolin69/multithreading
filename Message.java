import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;

public class Message implements Serializable {
    private String text;
    protected String sender;
    protected LocalDateTime dateTime;
    private byte[] image;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "SimpleMessage{" +
                "sender='" + sender + '\'' +
                ", text='" + text + '\'' +
                ", dateTime=" + dateTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return Objects.equals(text, message.text) && Objects.equals(sender, message.sender) && Objects.equals(dateTime, message.dateTime) && Arrays.equals(image, message.image);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(text, sender, dateTime);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    public static Message getMessage(String sender, String text){
        return new Message(sender, text);
    }
}