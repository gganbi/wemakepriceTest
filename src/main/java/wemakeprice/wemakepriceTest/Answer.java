package wemakeprice.wemakepriceTest;


import lombok.Getter;
import lombok.Setter;
import javax.persistence.Embeddable;

// 2020.06.25  김성민  최초작성

@Embeddable
@Getter
public class Answer {

    private String share;
    private String remainder;
    public Answer(String share, String remainder) {
        this.share = share;
        this.remainder = remainder;
    }

}
