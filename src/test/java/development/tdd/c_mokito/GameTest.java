package development.tdd.c_mokito;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.only;

public class GameTest {

    @DisplayName("모의 객체의 메서드가 불렸는지 여부를 검증하는 코드")
    @Test
    void init() {
        GameNumGen genMock = mock(GameNumGen.class);
        Game game = new Game(genMock);
        game.init(GameLevel.EASY);

        then(genMock).should().generate(GameLevel.EASY);
        /*
        then()은 메서드 호출 여부를 검증할 모의 객체를 전달받는다.
        should() 메서드는 모의 객체의 메서드가 불려야 한다는 것을 설정하고 should() 메서드 다음에 실제로 불러야 할 메서드를 지정한다.
         */

        then(genMock).should().generate(any());

        then(genMock).should(only()).generate(any()); // 정확하게 한 번만 호출된 것을 검증하고 싶다면

    }
}
