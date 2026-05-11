import java.util.Objects;

/**
 * Класс Корвет
 */
public class Corvette extends Ship {
    private String weaponName;
    private int mastCount;

    public Corvette() {
        this(1, "Корвет", "артиллерийская установка", 1);
    }

    public Corvette(int id, String name, String weaponName, int mastCount) {
        super(id, name);
        setWeaponName(weaponName);
        setMastCount(mastCount);
    }

    public String getWeaponName() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName = validateString(weaponName, "Название оружия");
    }

    public int getMastCount() {
        return mastCount;
    }

    public void setMastCount(int mastCount) {
        if (mastCount < 1 || mastCount > 3) {
            throw new IllegalArgumentException("Количество мачт должно быть от 1 до 3.");
        }
        this.mastCount = mastCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!super.equals(o)) {
            return false;
        }
        Corvette corvette = (Corvette) o;
        return mastCount == corvette.mastCount && Objects.equals(weaponName, corvette.weaponName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weaponName, mastCount);
    }

    @Override
    public String toString() {
        String ln = System.lineSeparator();
        return "Тип корабля: корвет" + ln +
                getBaseInfo() + ln +
                "Название основного оружия: " + weaponName + ln +
                "Количество мачт: " + mastCount;
    }
}
