package development.tdd.c_mokito;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.willThrow;
import static org.mockito.Mockito.mock;

public class VoidMethodStubTest {

    @DisplayName("BDDMockito.given()을 이용한 스텁 설정")
    @Test
    void voidMethodWillStubTest() {
        List<String> mockList = mock(List.class);
        willThrow(UnsupportedOperationException.class)
                .given(mockList) // 객체를 전달 받는다. 메서드 실행이 아닌 모의 객체임에 유의하자.
                .clear(); // 메서드를 호출한다.

        assertThatThrownBy(() -> {
            mockList.clear();
        }).isInstanceOf(UnsupportedOperationException.class);
    }
}
