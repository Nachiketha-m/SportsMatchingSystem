package com.example.ooadtest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import com.example.UserBet;
import com.example.UserBetRepository;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
@Rollback(false)
public class UserBetRepositoryTest {

    @Autowired
    private UserBetRepository userBetRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testSaveUserBet() {
        UserBet userBet = new UserBet();
        userBet.setUserId(10L);
        userBet.setVenue("Monarch");
        userBet.setBet(1);
        userBet.setTimings("Evening");

        UserBet savedUserBet = userBetRepository.save(userBet);

        assertThat(savedUserBet.getId()).isNotNull();

        UserBet retrievedUserBet = entityManager.find(UserBet.class, savedUserBet.getId());

        assertThat(retrievedUserBet).isNotNull();
        assertThat(retrievedUserBet.getUserId()).isEqualTo(userBet.getUserId());
        assertThat(retrievedUserBet.getVenue()).isEqualTo(userBet.getVenue());
        assertThat(retrievedUserBet.getBet()).isEqualTo(userBet.getBet());
        assertThat(retrievedUserBet.getTimings()).isEqualTo(userBet.getTimings());
    }
}
