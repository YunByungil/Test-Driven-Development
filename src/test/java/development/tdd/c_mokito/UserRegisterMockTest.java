package development.tdd.c_mokito;

import development.tdd.pr.EmailNotifier;
import development.tdd.pr.UserRegister;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.then;

public class UserRegisterMockTest {
    private UserRegister userRegister;
    private EmailNotifier mockEmailNotifier = mock(EmailNotifier.class);

    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        then(mockEmailNotifier)
                .should().sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        Assertions.assertThat("email@email.com").isEqualTo(realEmail);
    }
}
