package wemakeprice.wemakepriceTest.service;

import wemakeprice.wemakepriceTest.Answer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeServiceTest {
    @Autowired
    HomeService homeService;
    @Test
    public void 부정확한url() throws IOException {
        //부정확한 url 입력 시 null값 반환
        //Given
        String url="www.naver.comsdf";
        String paramType="all";
        int paramOutUnit=4;

        //When
        Answer test=homeService.findItems(url,paramType,paramOutUnit);

        //Then
        assertEquals(null, test);
    }
}