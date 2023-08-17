package com.icebear2n2.goodplace.store.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URLEncoder;

@Service
public class RestTemplateService {

    @Value("${kakao.restapi.key}")
    private String restApiKey;

    private static final String API_SERVER_HOST  = "https://dapi.kakao.com";
    private static final String SEARCH_PLACE_KEYWORD_PATH = "/v2/local/search/keyword.json";

    public ResponseEntity<String> getSearchPlaceByKeyword(String query) throws Exception {
        String queryString = "?query="+URLEncoder.encode(query, "UTF-8");
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Authorization", "KakaoAK " + this.restApiKey);
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");

        URI url = URI.create(API_SERVER_HOST+SEARCH_PLACE_KEYWORD_PATH+queryString);
        RequestEntity<String> req = new RequestEntity<>(headers, HttpMethod.GET, url);
        ResponseEntity<String> res = restTemplate.exchange(req, String.class);

        return res;
    }
}
