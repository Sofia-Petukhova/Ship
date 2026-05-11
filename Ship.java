import java.util.Objects;

/**
 * Базовый класс Корабль
 */
public class Ship {
    private String name;
    private int id;

    public Ship() {
        this(1, "Корабль");
    }

    public Ship(int id, String name) {
        setId(id);
        setName(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = validateString(name, "Название");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id < 1 || id > 10000) {
            throw new IllegalArgumentException("Id должен быть в диапазоне от 1 до 10000.");
        }
        this.id = id;
    }

    protected static String validateString(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " не может быть null.");
        }

        String trimmed = value.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException(fieldName + " не может быть пустым.");
        }
        if (trimmed.length() < 2) {
            throw new IllegalArgumentException(fieldName + " должно содержать минимум 2 символа.");
        }
        if (trimmed.length() > 50) {
            throw new IllegalArgumentException(fieldName + " должно содержать не больше 50 символов.");
        }
        if (!containsLetter(trimmed)) {
            throw new IllegalArgumentException(fieldName + " должно содержать хотя бы одну букву.");
        }

        return trimmed;
    }

    protected static boolean containsLetter(String value) {
        for (int i = 0; i < value.length(); i++) {
            if (Character.isLetter(value.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    protected String getBaseInfo() {
        return "ID корабля: " + id + System.lineSeparator() +
                "Имя корабля: " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ship ship = (Ship) o;
        return id == ship.id && Objects.equals(name, ship.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return "Тип корабля: корабль" + System.lineSeparator() + getBaseInfo();
    }
}
