import java.io.Serializable;

/**
 * Created by Marcus on 2016-08-21.
 */
public class SkiRequestPacket implements Serializable{

    public enum Age {
        ZEROFOUR ("0-4 år"),
        FIVEEIGHT ("5-8 år"),
        NINEPLUS ("9+ år");

        private String description;

        Age(String text){
            description = text;
        }

        public String getDescription(){
            return description;
        }
    }

    public enum Style {
        KLASSISK,
        FRISTIL;
    }

    private Age age;
    private int length;
    private Style style;

    public SkiRequestPacket(Age pAge, Style pStyle, int pLength){
        age = pAge;
        length = pLength;
        style = pStyle;
    }

    public Style getStyle() {
        return style;
    }

    public Age getAge() {
        return age;
    }

    public int getLength() {
        return length;
    }
}
