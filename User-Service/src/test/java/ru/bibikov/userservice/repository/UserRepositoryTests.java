package ru.bibikov.userservice.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.util.CollectionUtils;
import ru.bibikov.userservice.entity.User;
import ru.bibikov.userservice.util.DataUtils;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void setUp(){
        repository.deleteAll();
    }
    @Test
    @DisplayName("Test save user functionality")
    public void givenUserObject_whenSave_thenUserIsCreated(){
        //given
        User userToSave= DataUtils.getJohnTransient();
        //when
        User savedUser=repository.save(userToSave);
        //then
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test update user functionality")
    public void givenUserToUpdate_whenSave_thenEmailIsChanged(){
        //given
        String updatedEmail="updated@gmail.com";
        User userToCreate=DataUtils.getJohnTransient();
        repository.save(userToCreate);
        //when
        User userToUpdate=repository.findById(userToCreate.getId()).orElseThrow(()->new RuntimeException("User not found"));
        userToUpdate.setEmail(updatedEmail);
        User userUpdated=repository.save(userToUpdate);
        //then
        assertThat(userUpdated).isNotNull();
        assertThat(userUpdated.getEmail()).isEqualTo(updatedEmail);
    }

    @Test
    @DisplayName("Test get by id user")
    public void givenUserCreated_whenGetById_thenUserIsReturned(){
        //given
        User userToCreate=DataUtils.getJohnTransient();
        repository.save(userToCreate);
        //when
        User obtainedUser=repository.findById(userToCreate.getId()).orElseThrow(()->new RuntimeException("User not found"));
        //then
        assertThat(obtainedUser).isNotNull();
        assertThat(obtainedUser.getEmail()).isEqualTo("john@gmail.com");
    }

    @Test
    @DisplayName("Test user not found functionality")
    public void givenUserIsNotCreated_whenGetById_thenOptionalIsEmpty(){
        //given

        //when
        User obtainedUser=repository.findById(1L).orElse(null);
        //then
        assertThat(obtainedUser).isNull();
    }

    @Test
    @DisplayName("Test get all user functionality")
    public void givenThreeUserAreStored_whenFindAll_thenAllUserAreReturned(){
        //given
        User user1=DataUtils.getJohnTransient();
        User user2=DataUtils.getMikeTransient();
        User user3=DataUtils.getFrankTransient();

        repository.saveAll(List.of(user1,user2,user3));
        //when
        List<User> obtainedUser=repository.findAll();
        //then
        assertThat(CollectionUtils.isEmpty(obtainedUser)).isFalse();
    }
}
