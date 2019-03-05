package sqlproject.repositories;

import java.io.Closeable;
import java.sql.*;

// Сервисный родительский класс, куда вынесена реализация общих действий для всех таблиц
public abstract class BaseRepository implements Closeable {

    // Блок объявления констант
    public static final String DB_URL = "jdbc:mariadb://localhost:3306/project_db?user=user&password=user";
    public static final String DB_Driver = "org.mariadb.jdbc.Driver";


    Connection connection;  // JDBC-соединение для работы с таблицей
    String tableName;       // Имя таблицы

    BaseRepository(String tableName) throws SQLException { // Для реальной таблицы передадим в конструктор её имя
        this.tableName = tableName;
        try {
            Class.forName(DB_Driver); //Проверяем наличие JDBC драйвера для работы с БД
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("JDBC driver for DataBase is not found!");
        }
        this.connection = DriverManager.getConnection(DB_URL); // Установим соединение с СУБД для дальнейшей работы
    }

    // Закрытие
    public void close() {
        try {
            if (connection != null && !connection.isClosed())
                connection.close();
        } catch (SQLException e) {
            System.out.println("Ошибка закрытия SQL соединения!");
        }
    }

    // Выполнить SQL команду без параметров в СУБД, по завершению выдать сообщение в консоль
    boolean executeSqlStatement(String sql, String description) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        statement.execute(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        if (description != null)
            System.out.println(description);
        return true;
    };

    boolean executeSqlStatement(String sql) throws SQLException {
        executeSqlStatement(sql, null);
        return true;
    };

    // Выполнить SQL команду и вернуть ответ
    ResultSet readSQLTable(String sql) throws SQLException {
        reopenConnection(); // переоткрываем (если оно неактивно) соединение с СУБД
        Statement statement = connection.createStatement();  // Создаем statement для выполнения sql-команд
        ResultSet resultSet = statement.executeQuery(sql); // Выполняем statement - sql команду
        statement.close();      // Закрываем statement для фиксации изменений в СУБД
        return resultSet;
    };

    // Активизация соединения с СУБД, если соединение не активно.
    void reopenConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
    }

}
