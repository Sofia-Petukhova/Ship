import java.util.Objects;

/**
 * Класс Парусник
 */
public class SailBoat extends Ship {
    private String sailColor;
    private int sailCount;

    public SailBoat() {
        this(1, "Парусник", "белый", 2);
    }

    public SailBoat(int id, String name, String sailColor, int sailCount) {
        super(id, name);
        setSailColor(sailColor);
        setSailCount(sailCount);
    }

    public String getSailColor() {
        return sailColor;
    }

    public void setSailColor(String sailColor) {
        this.sailColor = validateString(sailColor, "Цвет парусов");
    }

    public int getSailCount() {
        return sailCount;
    }

    public void setSailCount(int sailCount) {
        if (sailCount < 1 || sailCount > 40) {
            throw new IllegalArgumentException("Количество парусов должно быть от 1 до 40.");
        }
        this.sailCount = sailCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        SailBoat sailBoat = (SailBoat) o;
        return sailCount == sailBoat.sailCount && Objects.equals(sailColor, sailBoat.sailColor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), sailColor, sailCount);
    }

    @Override
    public String toString() {
        String ln = System.lineSeparator();
        return "Тип корабля: парусник" + ln +
                getBaseInfo() + ln +
                "Цвет парусов: " + sailColor + ln +
                "Количество парусов: " + sailCount;
    }
}