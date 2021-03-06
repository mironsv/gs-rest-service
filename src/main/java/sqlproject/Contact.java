package sqlproject;

// модель контакта
public class Contact {

    private long id; // SQL field contact_id INT NOT NULL AUTO_INCREMENT (PRIMARY KEY)
    private String name; // SQL field contact_name VARCHAR(50) NOT NULL
    private Integer age; // SQL field contact_age SMALLINT NOT NULL
    private String content; // SQL field contact_content VARCHAR(1000)

    public Contact(String name, Integer age, String content) {

        this.name = name;
        this.age = age;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() { return age; }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
