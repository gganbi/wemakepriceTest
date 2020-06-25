package wemakeprice.wemakepriceTest.controller;

import wemakeprice.wemakepriceTest.Answer;
import wemakeprice.wemakepriceTest.service.HomeService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;


@RequiredArgsConstructor
@Controller
public class HomeController {
    private final HomeService homeService;

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST)
    @ResponseBody
    public Answer outPut(@RequestParam("url")String url, @RequestParam("type")String type
            , @RequestParam("outUnit")int outUnit) throws IOException {
        Answer answerValue = homeService.findItems(url,type,outUnit);
        System.out.println("text_edit : "+url);
        System.out.println("text_edit : "+type);
        System.out.println("text_edit : "+outUnit);
        return answerValue;
    }
}
