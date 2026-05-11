import java.util.Objects;

/**
 * Класс Пароход
 */
public class Steamship extends Ship {
    private String purpose;
    private double maxSpeed;

    public Steamship() {
        this(1, "Пароход", "пассажирский", 15.0);
    }

    public Steamship(int id, String name, String purpose, double maxSpeed) {
        super(id, name);
        setPurpose(purpose);
        setMaxSpeed(maxSpeed);
    }

    public String getPurpose() {
        return purpose;
    }

    public void setPurpose(String purpose) {
        this.purpose = validateString(purpose, "Назначение парохода");
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        if (Double.isNaN(maxSpeed) || Double.isInfinite(maxSpeed) || maxSpeed < 1 || maxSpeed > 45) {
            throw new IllegalArgumentException("Максимальная скорость парохода должна быть от 1 до 45 морских узлов.");
        }
        this.maxSpeed = maxSpeed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        Steamship steamship = (Steamship) o;
        return Double.compare(steamship.maxSpeed, maxSpeed) == 0 && Objects.equals(purpose, steamship.purpose);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), purpose, maxSpeed);
    }

    @Override
    public String toString() {
        String ln = System.lineSeparator();
        String speedText = formatSpeed(maxSpeed);
        return "Тип корабля: пароход" + ln +
                getBaseInfo() + ln +
                "Назначение: " + purpose + ln +
                "Максимальная скорость: " + speedText + getKnotsWord(speedText);
    }

    private String formatSpeed(double value) {
        if (value == (long) value) {
            return Long.toString((long) value);
        }
        return Double.toString(value);
    }

    private String getKnotsWord(String speedText) {
        if (speedText.endsWith("1")) {
            return " морской узел";
        }
        if (speedText.endsWith("2") || speedText.endsWith("3") || speedText.endsWith("4")) {
            return " морских узла";
        }
        return " морских узлов";
    }
}
