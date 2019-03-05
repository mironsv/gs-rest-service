package sqlproject.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sqlproject.Contact;
import sqlproject.repositories.ContactRepository;
import java.sql.SQLException;

@Service
public class ContactService {

    private static int maxIdDB = 32767; // максимальный id контакта в БД

    // Объявляем и инициализируем таблицу БД
    @Autowired
    private ContactRepository contactRepository;

    // сервис добавляет контакт в БД
    public Long add(Contact contact) {
        // записываем данные в таблицу БД
        return contactRepository.save(contact);
    }

    // сервис получает информацию о контакте из БД по id
    public String getInfo(int id, String name){
        // проверка корректности id и name для СУБД
        if ((id<=maxIdDB)&(id>0)) {
            if (name.equals("ContactName") || name.equals("ContactAge") ||
                    name.equals("ContactContent")) {
                try {
                    return contactRepository.readTable(id, name);
                } catch (SQLException e) {
                    e.printStackTrace();
                    return "SQL Error!";
                }
            } else return "unhandled request";
        }
        return "id=" + id + " is incorrect!";
    }

    // сервис удаляет контакт из БД по id
    public String delete(int id) {
        // удаляем данные из таблицы БД
        try {
            if (contactRepository.deleteRecordTable(id)) return "Contact with <b>id " + id + "</b> is deleted";
            else return "Contact with <b>id " + id + "</b> is NOT deleted";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error SQL !";
        }
    }

}