package wemakeprice.wemakepriceTest.service;


import wemakeprice.wemakepriceTest.Answer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;

// 2020.06.25  김성민  최초작성

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class HomeService {

    public Answer findItems(String url, String paramType, int paramOutUnit) throws IOException {

        String connUrl = "";
        if(!url.substring(0,4).equals("http")){
            connUrl="https://";
        }
        connUrl+=url;

        // 2. HTML 가져오기
        try {
        org.jsoup.nodes.Document doc = Jsoup.connect(connUrl).get();
        // 3. 가져온 HTML Document 를 확인하기

        // 전체 문제열
        String urlString=doc.toString();

        System.out.println();

        String type=paramType; //태그 및 전체 타입선택

        if(type.equals("HTMLExclude")) {  //html 태그제외
            System.out.println("태그제외 선택");
            urlString=urlString.replaceAll("<(/)?([a-zA-Z]*)(\\s[a-zA-Z]*=[^>]*)?(\\s)*(/)?>", "");
        }else if(type.equals("all")) {
            System.out.println("전체문자열 선택");
        }
        //System.out.println(urlString);


        char urlStringToCharArray[]=urlString.toCharArray(); // char[]로 변환

        // 각 문자 또는 숫자 별 갯수
        int cntLower[]= new int[26]; //소문자   a : 0 , b : 1 ~
        int cntUpper[]= new int[26]; // 대문자  A : 0 , B : 1
        int cntNum[] =new int[10];    //숫자    0 : 0 ,  1 : 1

        // 해당 알파벳,숫자에 대한 갯수
        for(int i=0;i<urlStringToCharArray.length;i++) {
            if(urlStringToCharArray[i]>='a' && urlStringToCharArray[i]<='z') {
                cntLower[urlStringToCharArray[i]-'a']++;
            }else if(urlStringToCharArray[i]>='A' && urlStringToCharArray[i]<='Z') {
                cntUpper[urlStringToCharArray[i]-'A']++;
            }else if(urlStringToCharArray[i]>='0' && urlStringToCharArray[i]<='9') {
                cntNum[urlStringToCharArray[i]-'0']++;
            }
        }
        // 문자열에서 알파멧리스트 저장 ex) AaBbCc......
        ArrayList<Character> alphabetList=new ArrayList();

        for(int i=0;i<26;i++) {
            if(cntUpper[i]!=0 ||cntLower[i]!=0 ) {
                if(cntUpper[i]!=0) {
                    for(int j=0;j<cntUpper[i];j++) {
                        alphabetList.add((char)(i+'A'));
                    }
                }
                if(cntLower[i]!=0) {
                    for(int j=0;j<cntLower[i];j++) {
                        alphabetList.add((char)(i+'a'));
                    }
                }
            }
        }
        // 문자열에서 숫자리스트 저장ex) 0001112233......
        ArrayList<Integer> numList=new ArrayList();

        for(int i=0;i<10;i++) {
            if(cntNum[i]!=0) {
                for(int j=0;j<cntNum[i];j++) {
                    numList.add(i);
                }
            }
        }


        int sumSize=alphabetList.size()+numList.size(); // 총 문자갯수
        int outUnit=paramOutUnit; //출력 묶음단위
        int  remainder=sumSize%outUnit; //나머지

        String lastShare=""; // 최종 몫 문자열
        String lastRemainder=""; //최종 나머지 문자열

        // 알파벳 갯수, 숫자 갯수  둘중 작은 것을 기준으로 먼저 나타내줌
        if(alphabetList.size()>=numList.size()) { //알파벳 갯수가 많음
            for(int i=0;i<numList.size();i++) {  // 알바펫, 숫자 같이저장
                lastShare+=alphabetList.get(i);
                lastShare+=numList.get(i);
            }
            for(int i=numList.size();i<alphabetList.size()-remainder;i++) { // 나머지 제외 한 남은 알파벳 전부 저장
                lastShare+=alphabetList.get(i);
            }
            for(int i=alphabetList.size()-remainder;i<alphabetList.size();i++) { // 나머지 저장
                lastRemainder+=alphabetList.get(i);
            }

        }else { //숫자 갯수가 많음
            for(int i=0;i<alphabetList.size();i++) {  // 알바펫, 숫자 같이저장
                lastShare+=alphabetList.get(i);
                lastShare+=numList.get(i);
            }
            for(int i=alphabetList.size();i<numList.size()-remainder;i++) { // 나머지 제외 한 남은 숫자 전부 저장
                lastShare+=numList.get(i);
            }
            for(int i=numList.size()-remainder;i<numList.size();i++) { // 나머지 저장
                lastRemainder+=numList.get(i);
            }
        }

        Answer lastValue=new Answer(lastShare,lastRemainder);


        return lastValue;
        }
        catch(IOException e) {
            System.out.println("제대로 된 주소 입력하시오");
            return null;
        }
    }



}
