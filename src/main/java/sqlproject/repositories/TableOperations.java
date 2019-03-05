package sqlproject.repositories;

import java.sql.SQLException;

// Операции с таблицами
public interface TableOperations {
    void createTable() throws SQLException; // создание таблицы
    void createForeignKeys() throws SQLException; // создание связей между таблицами
    String updateTable() throws SQLException; // редактирование таблицы
    boolean deleteRecordTable(int id) throws SQLException; // удаление записи таблицы
    boolean readTable() throws SQLException; // чтение из таблицы
}
