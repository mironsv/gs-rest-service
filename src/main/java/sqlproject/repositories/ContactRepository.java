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
        // Создает все таблицы и ключи между таблицами
        // Создание таблиц
        createTable();
        // Создание внешних ключей (связи между таблицами)
        createForeignKeys();
    }

    @Override
    public void createTable() throws SQLException {
        super.executeSqlStatement("CREATE TABLE IF NOT EXISTS " + tableName + "(" +
                "ContactID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                "ContactName VARCHAR(50) NOT NULL," +
                "ContactAge SMALLINT NOT NULL,"+
                "ContactContent VARCHAR(1000))", "Table " + tableName + " is created!");
    }

    @Override
    public void createForeignKeys() throws SQLException {
    }

    @Override
    public String updateTable() throws SQLException {
        return "Give arguments: String name, short age, String content!";
    }

    @Override
    public boolean deleteRecordTable(int id) throws SQLException {
        return super.executeSqlStatement("DELETE FROM " + this.tableName +
                " WHERE ContactID=" + id + ";");
    }

    @Override
    public boolean readTable() throws SQLException {
        return false;
    }

    // запрашиваем данные из БД
    public String readTable(int id, String name) throws SQLException {

        String string = "";
        ResultSet resultSet = super.readSQLTable("SELECT " + name + " FROM " + this.tableName +
                " WHERE ContactID = " + id + ";");
        if (resultSet.next()) {
            do {
                string += (resultSet.getString(name) + "<br>");
            } while ( resultSet.next() );
            return string;
        } else return "Contact with id=" + id + " doesn't exist!";
    }

    public Long save(Contact contact) {
        try {
            String name = contact.getName();
            Integer age = contact.getAge();
            String content = contact.getContent();
            if (super.executeSqlStatement("INSERT INTO " + this.tableName +
                    " (ContactName, ContactAge, ContactContent)" +
                    " VALUES ('" + name + "', '" + age + "', '" + content + "');",
                    name + " " + age + " was inserted into " + this.tableName)) {
                ResultSet resultSet = super.readSQLTable("SELECT ContactID FROM " + this.tableName +
                        " WHERE ContactName = '" + name + "' AND ContactAge = '" + age +
                        "' AND ContactContent = '" + content + "';");
                if (resultSet.next()) {
                    return resultSet.getLong("ContactID");
                } else return -1L;
            } else return -1L;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
