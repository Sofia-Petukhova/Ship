import java.util.ArrayList;
import java.nio.charset.Charset;
import java.util.Scanner;

/**
 * Консольное приложение для работы с кораблями.
 */
public class Main {
    private static final Scanner scanner = createScanner();
    private static final ArrayList<Ship> ships = new ArrayList<>();

    private static final int MIN_MENU_CHOICE = 1;
    private static final int MAX_MENU_CHOICE = 5;
    private static final int MIN_SHIP_TYPE = 1;
    private static final int MAX_SHIP_TYPE = 3;
    private static final int MIN_ID = 1;
    private static final int MAX_ID = 10000;
    private static final int MIN_SPEED = 1;
    private static final int MAX_SPEED = 45;
    private static final int MIN_SAIL_COUNT = 1;
    private static final int MAX_SAIL_COUNT = 40;
    private static final int MIN_MAST_COUNT = 1;
    private static final int MAX_MAST_COUNT = 3;

    private static final String INVALID_NUMBER_MESSAGE = "Пожалуйста, введите корректное число. Например: 1";
    private static final String MENU_ERROR_MESSAGE = "Пожалуйста, выберите число от 1 до 5";
    private static final String SHIP_TYPE_ERROR_MESSAGE = "Пожалуйста, выберите число от 1 до 3";
    private static final String LETTER_REQUIRED_MESSAGE = "Поле должно содержать хотя бы одну букву. Попробуйте ещё раз";
    private static final String ID_RANGE_ERROR_MESSAGE = "Id должен быть числом от 1 до 10000. Попробуйте ещё раз";
    private static final String EMPTY_LIST_MESSAGE = "Список кораблей пуст.";

        public Main() {
            this(new ArrayList<Ship>()); 
    }

    public Main(ArrayList<Ship> initialShips) {
        if (initialShips != null) {
            ships.addAll(initialShips);
        }
    }

    private static Scanner createScanner() {
        try {
            if (System.console() != null) {
                return new Scanner(System.in, System.console().charset());
            }
            return new Scanner(System.in, Charset.defaultCharset());
        } catch (IllegalArgumentException e) {
            return new Scanner(System.in);
        }
    }

    public static void main(String[] args) {
        boolean isRunning = true;

        System.out.println("Добро пожаловать в программу учёта кораблей.");

        while (isRunning) {
            printMenu();
            int choice = readMenuChoice();

            switch (choice) {
                case 1:
                    addShip();
                    break;
                case 2:
                    removeShipById();
                    break;
                case 3:
                    printAllShips();
                    break;
                case 4:
                    compareShips();
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    // Сюда не попадём благодаря валидации readMenuChoice.
                    break;
            }
        }

        System.out.println("Работа программы завершена.");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("=== МЕНЮ ===");
        System.out.println("1. Добавить новый элемент");
        System.out.println("2. Удалить элемент по id");
        System.out.println("3. Вывести все элементы");
        System.out.println("4. Сравнить два элемента на равенство по id");
        System.out.println("5. Завершить работу приложения");
    }

    private static int readMenuChoice() {
        return readIntInRange(
                "Введите пункт меню (1-5): ",
                MIN_MENU_CHOICE,
                MAX_MENU_CHOICE,
                MENU_ERROR_MESSAGE,
                MENU_ERROR_MESSAGE
        );
    }

    private static void addShip() {
        System.out.println();
        System.out.println("Выберите тип корабля:");
        System.out.println("1. Пароход");
        System.out.println("2. Парусник");
        System.out.println("3. Корвет");

        int type = readShipTypeChoice();

        int id = readUniqueId("Введите id корабля (1-10000): ");
        String name = readString("Введите название корабля: ", "Название");
        Ship newShip = createShipByType(type, id, name);

        ships.add(newShip);
        System.out.println("Корабль успешно добавлен.");
    }

    private static int readShipTypeChoice() {
        return readIntInRange(
                "Введите номер типа (1-3): ",
                MIN_SHIP_TYPE,
                MAX_SHIP_TYPE,
                SHIP_TYPE_ERROR_MESSAGE,
                SHIP_TYPE_ERROR_MESSAGE
        );
    }

    private static Ship createShipByType(int type, int id, String name) {
        switch (type) {
            case 1:
                String purpose = readString("Введите назначение парохода: ", "Назначение");
                double maxSpeed = readDoubleInRange(
                        "Введите максимальную скорость (1-45 узлов): ",
                        MIN_SPEED,
                        MAX_SPEED,
                        "Максимальная скорость парохода должна быть от 1 до 45 морских узлов."
                );
                return new Steamship(id, name, purpose, maxSpeed);
            case 2:
                String sailColor = readString("Введите цвет парусов: ", "Цвет парусов");
                int sailCount = readIntInRange(
                        "Введите количество парусов (1-40): ",
                        MIN_SAIL_COUNT,
                        MAX_SAIL_COUNT,
                        INVALID_NUMBER_MESSAGE,
                        "Количество парусов должно быть от 1 до 40."
                );
                return new SailBoat(id, name, sailColor, sailCount);
            case 3:
                String weaponName = readString("Введите название основного оружия: ", "Название оружия");
                int mastCount = readIntInRange(
                        "Введите количество мачт (1-3): ",
                        MIN_MAST_COUNT,
                        MAX_MAST_COUNT,
                        INVALID_NUMBER_MESSAGE,
                        "Количество мачт должно быть от 1 до 3."
                );
                return new Corvette(id, name, weaponName, mastCount);
            default:
                // Сюда не попадём благодаря валидации.
                throw new IllegalStateException("Неизвестный тип корабля: " + type);
        }
    }

    private static void removeShipById() {
        if (isShipsEmpty()) {
            return;
        }

        int id = readIdInRange("Введите id корабля для удаления (1-10000): ");

        Ship ship = findById(id);
        if (ship == null) {
            System.out.println("Корабль с таким id не найден.");
            return;
        }

        ships.remove(ship);
        System.out.println("Корабль успешно удалён.");
    }

    private static void printAllShips() {
        if (isShipsEmpty()) {
            return;
        }

        System.out.println();
        System.out.println("Список кораблей:");

        for (int i = 0; i < ships.size(); i++) {
            System.out.println(ships.get(i));
            if (i < ships.size() - 1) {
                System.out.println();
            }
        }
    }

    private static void compareShips() {
        if (ships.isEmpty()) {
            System.out.println("Список кораблей пуст. Сравнение невозможно.");
            return;
        }

        int firstId = readIdInRange("Введите id первого корабля (1-10000): ");
        int secondId = readIdInRange("Введите id второго корабля (1-10000): ");

        Ship firstShip = findById(firstId);
        Ship secondShip = findById(secondId);

        if (firstShip == null && secondShip == null) {
            System.out.println("Оба корабля не найдены.");
            return;
        }
        if (firstShip == null) {
            System.out.println("Первый корабль с id = " + firstId + " не найден.");
            return;
        }
        if (secondShip == null) {
            System.out.println("Второй корабль с id = " + secondId + " не найден.");
            return;
        }

        if (firstShip.equals(secondShip)) {
            System.out.println("Корабли равны.");
        } else {
            System.out.println("Корабли не равны.");
        }
    }

    private static Ship findById(int id) {
        for (Ship ship : ships) {
            if (ship.getId() == id) {
                return ship;
            }
        }
        return null;
    }

    private static boolean isShipsEmpty() {
        if (ships.isEmpty()) {
            System.out.println(EMPTY_LIST_MESSAGE);
            return true;
        }
        return false;
    }

    private static int readUniqueId(String prompt) {
        while (true) {
            int id = readIdInRange(prompt);

            if (findById(id) != null) {
                System.out.println("Корабль с таким id уже существует. Введите другой id.");
                continue;
            }

            return id;
        }
    }

    private static int readIdInRange(String prompt) {
        return readIntInRange(
                prompt,
                MIN_ID,
                MAX_ID,
                INVALID_NUMBER_MESSAGE,
                ID_RANGE_ERROR_MESSAGE
        );
    }

    private static int readIntInRange(
            String prompt,
            int min,
            int max,
            String parseErrorMessage,
            String rangeErrorMessage
    ) {
        while (true) {
            System.out.print(prompt);
            String input = readLineSafe();

            if (input == null) {
                System.out.println(parseErrorMessage);
                continue;
            }

            String trimmed = input.trim();
            if (trimmed.isEmpty()) {
                System.out.println(parseErrorMessage);
                continue;
            }

            int value;
            try {
                value = Integer.parseInt(trimmed);
            } catch (NumberFormatException e) {
                System.out.println(parseErrorMessage);
                continue;
            }

            if (value < min || value > max) {
                System.out.println(rangeErrorMessage);
                continue;
            }

            return value;
        }
    }

    private static double readDoubleInRange(String prompt, double min, double max, String rangeErrorMessage) {
        while (true) {
            System.out.print(prompt);
            String input = readLineSafe();

            if (input == null) {
                System.out.println(INVALID_NUMBER_MESSAGE);
                continue;
            }

            String trimmed = input.trim();
            if (trimmed.isEmpty()) {
                System.out.println(INVALID_NUMBER_MESSAGE);
                continue;
            }

            if (!trimmed.matches("^[+-]?\\d+([.,]\\d+)?$")) {
                System.out.println(INVALID_NUMBER_MESSAGE);
                continue;
            }

            double value;
            try {
                value = Double.parseDouble(trimmed.replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println(INVALID_NUMBER_MESSAGE);
                continue;
            }

            if (Double.isNaN(value) || Double.isInfinite(value) || value < min || value > max) {
                System.out.println(rangeErrorMessage);
                continue;
            }

            return value;
        }
    }

    private static String readString(String prompt, String fieldName) {
        while (true) {
            System.out.print(prompt);
            String input = readLineSafe();

            if (input == null) {
                System.out.println(fieldName + " не может быть пустым. Введите хотя бы 2 символа.");
                continue;
            }

            String trimmed = input.trim();

            if (trimmed.isEmpty()) {
                System.out.println(fieldName + " не может быть пустым. Введите хотя бы 2 символа.");
                continue;
            }
            if (trimmed.length() < 2) {
                System.out.println(fieldName + " должно содержать минимум 2 символа.");
                continue;
            }
            if (trimmed.length() > 50) {
                System.out.println(fieldName + " должно содержать не больше 50 символов.");
                continue;
            }
            if (!Ship.containsLetter(trimmed)) {
                System.out.println(LETTER_REQUIRED_MESSAGE);
                continue;
            }

            return trimmed;
        }
    }

    private static String readLineSafe() {
        if (!scanner.hasNextLine()) {
            System.out.println("Ввод завершён. Программа остановлена.");
            scanner.close();
            System.exit(0);
        }
        return scanner.nextLine();
    }
}
