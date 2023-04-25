package development.tdd.chap07;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class UserRegisterMockTest {

    private UserRegister userRegister;
    private WeakPasswordChecker mockPasswordChecker = mock(WeakPasswordChecker.class);
    private MemoryUserRepository fakeRepository = new MemoryUserRepository();
    private EmailNotifier mockEmailNotifier = mock(EmailNotifier.class);

    @BeforeEach
    void setUp() {
        userRegister = new UserRegister(mockPasswordChecker,
                fakeRepository,
                mockEmailNotifier);
    }

    @DisplayName("약한 암호면 가입 실패")
    @Test
    void weakPassword() {
        /*
        정확하게 일치하는 값으로 모의 객체 설정하지 않기
        "pw" -> Mockito.anyString()
         */
        given(mockPasswordChecker.checkPasswordWeak(Mockito.anyString())).willReturn(true);

        assertThatThrownBy(() -> {
            userRegister.register("id", "pw", "email");
        }).isInstanceOf(WeakPasswordException.class);
    }

    @DisplayName("모의 객체가 기대한 대로 불렸는지 검증하는 코드")
    @Test
    void checkPassword() {
        userRegister.register("id", "pw", "email");

        then(mockPasswordChecker)
                .should()
                .checkPasswordWeak(anyString());
    }

    @DisplayName("가입하면 메일을 전송함")
    @Test
    void whenRegisterThenSendMail() {
        userRegister.register("id", "pw", "email@email.com");

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        then(mockEmailNotifier)
                .should()
                .sendRegisterEmail(captor.capture());

        String realEmail = captor.getValue();
        assertThat("email@email.com").isEqualTo(realEmail);
    }
}
