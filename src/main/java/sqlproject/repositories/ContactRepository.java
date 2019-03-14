package sqlproject.repositories;

import org.springframework.stereotype.Repository;
import sqlproject.Contact;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ContactRepository extends BaseRepository implements TableOperations {

    // конструктор
    public ContactRepository() throws SQLException {
        super("contactstable");
        // Создание таблиц
        createTable();
        // Создание внешних ключей (связи между таблицами)
        createForeignKeys();
    }

    // метод создает таблицу в БД
    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "contact_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "contact_name VARCHAR(50) NOT NULL," +
                "contact_age SMALLINT NOT NULL,"+
                "contact_content VARCHAR(1000))", "Table " + tableName + " is created!");
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }

    @Override
    public String updateTable() throws SQLException {
        return "Give arguments: String name, short age, String content!";
    }

    // метод удаляет запись из БД по id
    @Override
    public boolean deleteRecordTable(int id) throws SQLException {
        return super.executeSqlStatement("DELETE FROM " + this.tableName +
                " WHERE contact_id=" + id + ";");
    }

    @Override
    public boolean readTable() throws SQLException {
        return false;
    }

    // метод запрашивает данные из БД по id и полю name
    public String readTable(int id, String name) throws SQLException {

        StringBuilder string = new StringBuilder();
        ResultSet resultSet = super.readSQLTable("SELECT " + name + " FROM " + this.tableName +
                " WHERE contact_id = " + id + ";");

        // Количество колонок в результирующем запросе
        int columns = resultSet.getMetaData().getColumnCount();
        // Перебор строк с данными
        if (resultSet.next()) {
            do {
                for (int i = 1; i <= columns; i++){string.append(resultSet.getString(i) + "<br>");
                }
            } while ( resultSet.next() );
            return string.toString();
        } else return "Contact with id=" + id + " doesn't exist!";
    }

    // метод добавляет запись в БД и возвращает ID добавленной записи
    public Long save(Contact contact) {
        try {
            String name = contact.getName();
            Integer age = contact.getAge();
            String content = contact.getContent();
            if (super.executeSqlStatement("INSERT INTO " + this.tableName +
                    " (contact_name, contact_age, contact_content)" +
                    " VALUES ('" + name + "', '" + age + "', '" + content + "');",
                    name + " " + age + " was inserted into " + this.tableName)) {
                ResultSet resultSet = super.readSQLTable("SELECT contact_id FROM " + this.tableName +
                        " WHERE contact_name = '" + name + "' AND contact_age = '" + age +
                        "' AND contact_content = '" + content + "';");
                if (resultSet.next()) {
                    return resultSet.getLong("contact_id");
                } else return -1L;
            } else return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
