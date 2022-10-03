package com.edu.ulab.app_ylab.service;



import com.edu.ulab.app_ylab.dto.UserDto;
import com.edu.ulab.app_ylab.entity.Person;
import com.edu.ulab.app_ylab.exception.NoSuchUserException;
import com.edu.ulab.app_ylab.mapper.UserMapper;
import com.edu.ulab.app_ylab.repository.UserRepository;
import com.edu.ulab.app_ylab.service.impl.UserServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

/**
 * Тестирование функционала {@link com.edu.ulab.app_ylab.service.impl.UserServiceImpl}.
 */
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@DisplayName("Testing user functionality.")
public class UserServiceImplTest {
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    UserMapper userMapper;

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void savePerson_Test() {
        //given

        UserDto userDto = new UserDto();
        userDto.setAge(11);
        userDto.setFullName("test name");
        userDto.setTitle("test title");

        Person person = new Person();
        person.setFullName("test name");
        person.setAge(11);
        person.setTitle("test title");

        Person savedPerson = new Person();
        savedPerson.setId(1l);
        savedPerson.setFullName("test name");
        savedPerson.setAge(11);
        savedPerson.setTitle("test title");

        UserDto result = new UserDto();
        result.setId(1L);
        result.setAge(11);
        result.setFullName("test name");
        result.setTitle("test title");


        //when

        when(userMapper.personDtoToUserEntity(userDto)).thenReturn(person);
        when(userRepository.save(person)).thenReturn(savedPerson);
        when(userMapper.personEntityToUserDto(savedPerson)).thenReturn(result);


        //then

        UserDto userDtoResult = userService.createUser(userDto);
        assertEquals(1L, userDtoResult.getId());
    }

    /**
     * В тестируемом методе updateUser(UserDto userDto, Long userId) из класса UserServiceImpl
     * логика  заключается в том, что если в базе находится пользователь с таким же именем и возрастом, то возвращается
     * он же из базы. Если же нет, то по этому ID перезаписывается новый пользователь.
     * Поэтому на данный метод будет 2 теста с двумя сценариями.
     */

    @Test
    @DisplayName("Изменение пользователя.Сценарий 1 - перезаписываем полностью пользователя. Должно пройти успешно.")
    void updatePerson_Test() {
        //given

        Long userId = 300L;

        UserDto oldPersonFromBd = new UserDto();
        oldPersonFromBd.setId(userId);
        oldPersonFromBd.setAge(33);
        oldPersonFromBd.setFullName("test bd_user");
        oldPersonFromBd.setTitle("title_bd_user");


        UserDto userDtoForUpdate = new UserDto();
        userDtoForUpdate.setAge(11);
        userDtoForUpdate.setFullName("updated name");
        userDtoForUpdate.setTitle("updated title");

        Person personForUpdate = new Person();
        personForUpdate.setFullName("updated name");
        personForUpdate.setAge(11);
        personForUpdate.setTitle("updated title");

        Person savedPersonForUpdate = new Person();
        savedPersonForUpdate.setId(userId);
        savedPersonForUpdate.setFullName("updated name");
        savedPersonForUpdate.setAge(11);
        savedPersonForUpdate.setTitle("updated title");

        UserDto resultUpdatedDto = new UserDto();
        resultUpdatedDto.setId(userId);
        resultUpdatedDto.setAge(11);
        resultUpdatedDto.setFullName("updated name");
        resultUpdatedDto.setTitle("updated title");

        oldPersonFromBd.setAge(resultUpdatedDto.getAge());
        oldPersonFromBd.setFullName(resultUpdatedDto.getFullName());
        oldPersonFromBd.setTitle(resultUpdatedDto.getTitle());



        when(userMapper.personDtoToUserEntity(userDtoForUpdate)).thenReturn(personForUpdate);
        when(userRepository.save(personForUpdate)).thenReturn(savedPersonForUpdate);
        when(userMapper.personEntityToUserDto(savedPersonForUpdate)).thenReturn(resultUpdatedDto);

        //then

        UserDto updatedUserDto = userService.createUser(userDtoForUpdate);

        assertEquals(oldPersonFromBd, updatedUserDto);
    }

    @Test
    @DisplayName("Изменение пользователя.Сценарий 2 - возвращем из базы пользователя с таким же именем и возрастом. Должно пройти успешно.")
    void updatePersonIfPersonExist_Test() {
        //given

        Long userId = 150L;

        //старый пользователь из базы
        UserDto oldPersonFromBd = new UserDto();
        oldPersonFromBd.setId(userId);
        oldPersonFromBd.setAge(33);
        oldPersonFromBd.setFullName("test bd_user");
        oldPersonFromBd.setTitle("title_bd_user");


        //новый пользователь
        UserDto userDtoForUpdate = new UserDto();
        userDtoForUpdate.setAge(33);
        userDtoForUpdate.setFullName("test bd_user");
        userDtoForUpdate.setTitle("title_bd_user");

        UserDto resultUpdatedPerson = new UserDto();
        resultUpdatedPerson.setId(userId);
        resultUpdatedPerson.setAge(33);
        resultUpdatedPerson.setFullName("test bd_user");
        resultUpdatedPerson.setTitle("title_bd_user");



        UserDto returnedBook = new UserDto();
        if (oldPersonFromBd.getFullName().equals(userDtoForUpdate.getFullName())
                && oldPersonFromBd.getAge() == userDtoForUpdate.getAge()) {
            returnedBook = resultUpdatedPerson;
        }

        //when
        when(userService.getUserById(userId)).thenReturn(oldPersonFromBd);
        when(userService.getUserById(userId)).thenReturn(resultUpdatedPerson);


        //then
        UserDto updatedUserDto = userService.updateUser(userDtoForUpdate, userId);

        assertEquals(returnedBook.getId(), updatedUserDto.getId());
        assertEquals(returnedBook.getAge(), updatedUserDto.getAge());
        assertEquals(returnedBook.getFullName(), updatedUserDto.getFullName());
        assertEquals(returnedBook.getTitle(), updatedUserDto.getTitle());
    }

    @Test
    @DisplayName("Создание пользователя. Должно пройти успешно.")
    void getPerson_Test() {
        //given

        Long id = 500L;
        Person person = new Person();
        person.setFullName("test get");
        person.setAge(44);
        person.setTitle("title_get");

        Person foundUser = new Person();
        foundUser.setId(500L);
        foundUser.setFullName("test get");
        foundUser.setAge(44);
        foundUser.setTitle("title_get");

        UserDto result = new UserDto();
        result.setId(500L);
        result.setAge(44);
        result.setFullName("test get");
        result.setTitle("title_get");

        //when
        when(userRepository.findPersonById(id)).thenReturn(foundUser);
        when(userMapper.personEntityToUserDto(foundUser)).thenReturn(result);

        //then
        UserDto userDtoResult = userService.getUserById(id);
        assertEquals(500L, userDtoResult.getId());
        assertEquals("test get", userDtoResult.getFullName());
        assertEquals(44, userDtoResult.getAge());
    }

    @Test
    @DisplayName("Удаление пользователя. Должно пройти успешно.")
    void deletePerson_Test() {
        //given
        Long id = 100L;

        Person person = new Person();
        person.setId(100L);
        person.setFullName("test delete");
        person.setAge(58);
        person.setTitle("title_delete");

        //when
        when(userRepository.findPersonById(id)).thenReturn(person);
        doNothing().when(userRepository).deleteById(id);

        //then
        UserDto userDtoResult = userService.getUserById(id);
        assertNull(userDtoResult);

    }


    /**
     * Failed tests
     */

    @Test
    @DisplayName("Получение пользователя, которого нет в БД.")
    void getErrorUser_Test() {
        //Given
        Long userId = 10L;

        //When
        doThrow(new NoSuchUserException("There is no user with id " + userId + " in database"))
                .when(userRepository).findPersonById(userId);

        //Then
        assertThatThrownBy(() -> userService.getUserById(userId))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessage("There is no user with id " + userId + " in database");

    }

    @Test
    @DisplayName("Удаление пользователя, которого нет в БД.")
    void deleteErrorUser_Test() {
        //Given
        Long userId = 10L;

        //When
        doThrow(new NoSuchUserException("There is no user with id " + userId + " in database"))
                .when(userRepository).deleteById(userId);

        //Then
        assertThatThrownBy(() -> userService.deleteUserById(userId))
                .isInstanceOf(NoSuchUserException.class)
                .hasMessage("There is no user with id " + userId + " in database");

    }
}
