package development.tdd.c_mokito;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class AnyMatcherTest {
    @DisplayName("ArgumentMatchers.any() 메서드로 인자 값 매칭 정리")
    @Test
    void anyMatchTest() {
        GameNumGen genMock = mock(GameNumGen.class);
        given(genMock.generate(any())).willReturn("456");

        String num = genMock.generate(GameLevel.EASY);
        assertThat("456").isEqualTo(num);

        String num2 = genMock.generate(GameLevel.NORMAL);
        assertThat("456").isEqualTo(num2);
    }

    @DisplayName("실패하는 테스트")
    @Test
    void mixAnyTest() {
        List<String> mockList = mock(List.class);

        given(mockList.set(anyInt(), "123")).willReturn("456");
        // => eq 메서드를 사용해야 된다.

        String old = mockList.set(5, "123");
        System.out.println("old = " + old);
    }

    @DisplayName("임의 값 일치와 정확한 값 일치가 필요한 경우 eq() 메서드를 사용")
    @Test
    void mixAnyAndEq() {
        List<String> mockList = mock(List.class);

        given(mockList.set(anyInt(), eq("123"))).willReturn("456");

        String old = mockList.set(5, "123");
        assertThat("456").isEqualTo(old);
    }
}
