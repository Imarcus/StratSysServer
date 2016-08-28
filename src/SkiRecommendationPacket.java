import java.io.Serializable;

/**
 * Created by Marcus on 2016-08-23.
 */
public class SkiRecommendationPacket implements Serializable {
    private int skiLengthFrom;
    private int skiLengthTo;
    private String warning;

    public SkiRecommendationPacket(int pSkiLengthFrom, int pSkiLengthTo, String pWarning) {
        skiLengthFrom = pSkiLengthFrom;
        skiLengthTo = pSkiLengthTo;
        warning = pWarning;
    }

    public int getSkiLengthTo() {
        return skiLengthTo;
    }

    public int getSkiLengthFrom() {
        return skiLengthFrom;
    }

    public String getWarning() {
        return warning;
    }
}
