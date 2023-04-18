package pl.zajecia.dodawanie;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DodawanieApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testAdded() {
        DodawanieApplication app = new DodawanieApplication();
        int result = app.added(2, 3);
        assert result == 5;
    }

}
