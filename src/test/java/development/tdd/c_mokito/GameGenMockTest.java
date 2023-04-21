package development.tdd.c_mokito;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class GameGenMockTest {
    @Mock
    private GameNumGen genMock;

    @DisplayName("Mock 테스트")
    @Test
    void mockTest() {
        genMock = mock(GameNumGen.class); // mock 메서드를 이용하면 특정 타입의 모의 객체를 생성할 수 있다.
        given(genMock.generate(GameLevel.EASY)).willReturn("123");
        // given 메서드는 스텁을 정의할 모의 객체의 메서드 호출을 전달한다.
        // genMock.generate(GameLevel.EASY) 메서드가 불리면 "123"을 리턴하라고 설정한다.




        String num = genMock.generate(GameLevel.EASY);
        assertThat("123").isEqualTo(num);
    }

    @DisplayName("특정 타입의 익셉션을 발생하도록 스텁 설정")
    @Test
    void mockThrowTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(null)).willThrow(IllegalArgumentException.class);

        assertThatThrownBy(() -> {
            genMock.generate(null);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}
