package com.edu.ulab.app_ylab.repository;

import com.edu.ulab.app_ylab.config.SystemJpaTest;
import com.edu.ulab.app_ylab.entity.Person;
import com.vladmihalcea.sql.SQLStatementCountValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.jdbc.Sql;

import static com.vladmihalcea.sql.SQLStatementCountValidator.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Тесты репозитория {@link UserRepository}.
 * Количество селектов в assertSelectCount() зависит от того как мы запускаем тесты.
 * Если запускаем сразу весь класс со всеми тестами, то select из sequence выполнится только на тесте, который запустится первым.
 * Если запускать каждый тест по отдельности , то необходимо в  assertSelectCount() дописать +1 select из sequence
 * В данном случае я написала вариант - запускать сразу целиком весь класс.
 */
@SystemJpaTest
public class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        SQLStatementCountValidator.reset();
    }

    @DisplayName("Сохранить юзера. Число select должно равняться 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void insertPerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setFullName("Test Test");
        person.setTitle("reader");
        person.setAge(105);


        //When
        Person result = userRepository.save(person);

        //Then
        assertThat(result.getAge()).isEqualTo(105);
        assertSelectCount(0);
        assertInsertCount(0);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }


    @DisplayName("Изменить юзера. Число select должно равняться 2, insert 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void updatePerson_thenAssertDmlCount() {
        //Given
        Person updatePerson = new Person();
        updatePerson.setFullName("Test Test");
        updatePerson.setTitle("readerrr");
        updatePerson.setAge(34);

        //When
        Person savedPerson = userRepository.save(updatePerson);
        Person updatedUser = userRepository.findPersonById(savedPerson.getId());


        //Then
        assertThat(savedPerson.getAge()).isEqualTo(34);
        assertThat(updatedUser.getAge()).isEqualTo(savedPerson.getAge());
        assertSelectCount(1);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }


    @DisplayName("Получить юзера. Число select должно равняться 2, insert 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void getPerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setFullName("Test Test");
        person.setTitle("readdder");
        person.setAge(105);;

        //When
        Person savedPerson = userRepository.save(person);
        Person result = userRepository.findPersonById(savedPerson.getId());

        //Then
        assertThat(savedPerson.getAge()).isEqualTo(105);
        assertThat(result.getAge()).isEqualTo(105);
        assertSelectCount(2);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(0);
    }

    @DisplayName("Удалить юзера. Число select должно равняться 2, insert 1, delete 1")
    @Test
    @Rollback
    @Sql({"classpath:sql/1_clear_schema.sql",
            "classpath:sql/2_insert_person_data.sql",
            "classpath:sql/3_insert_book_data.sql"
    })
    void deletePerson_thenAssertDmlCount() {
        //Given
        Person person = new Person();
        person.setFullName("Test User");
        person.setTitle("rrreader");
        person.setAge(58);;

        //When
        Person savedPerson = userRepository.save(person);
        userRepository.deleteById(savedPerson.getId());
        Person deletedPerson = userRepository.findPersonById(savedPerson.getId());

        //Then
        assertThat(savedPerson.getAge()).isEqualTo(58);
        assertThat(deletedPerson).isNull();
        assertSelectCount(1);
        assertInsertCount(1);
        assertUpdateCount(0);
        assertDeleteCount(1);
    }
}
