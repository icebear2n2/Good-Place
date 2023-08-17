package com.icebear2n2.goodplace.store.service;

import com.icebear2n2.goodplace.store.domain.entity.Store;
import com.icebear2n2.goodplace.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StoreService {
    private final StoreRepository repository;
    private final RestTemplateService restTemplateService;

    public void insert(String query) throws Exception {
        try {
            ResponseEntity<String> searchPlaceByKeyword = restTemplateService.getSearchPlaceByKeyword(query);
        String body = searchPlaceByKeyword.getBody();

        JSONParser jsonParser = new JSONParser();
        JSONObject responseObject = (JSONObject) jsonParser.parse(body);
        JSONArray documents = (JSONArray) responseObject.get("documents");

        for (Object doc : documents) {
            JSONObject document = (JSONObject) doc;
            String  placeId = (String) document.get("id");
            String placeName = (String) document.get("place_name");
            String roadAddressName = (String) document.get("road_address_name");
            String phone = (String) document.get("phone");
            String categoryGroupName = (String) document.get("category_group_name");
            String placeUrl = (String) document.get("place_url");
            String x = (String) document.get("x");
            String y = (String) document.get("y");
            Store store = new Store(null, placeId, placeName, roadAddressName, phone, categoryGroupName, placeUrl, x, y);
            if (!repository.existsByPlaceId(store.getPlaceId())) {
                repository.save(store);
            } else {
                System.out.println("Store with placeId " + placeId + " already exists.");
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void saveSelectedPlaces(String selectedPlacesData) {
        JSONParser parser = new JSONParser();
        try {
            JSONArray selectedPlaces = (JSONArray) parser.parse(selectedPlacesData);
            for (Object obj : selectedPlaces) {
                JSONObject placeJson = (JSONObject) obj;

                Store store = Store.builder()
                        .placeId((String) placeJson.get("id"))
                        .name((String) placeJson.get("place_name"))
                        .address((String) placeJson.get("address_name"))
                        .phone((String) placeJson.get("phone"))
                        .foodType((String) placeJson.get("category_name"))
                        .storeImgUrl((String) placeJson.get("place_url"))
                        .coordinateX((String) placeJson.get("x"))
                        .coordinateY((String) placeJson.get("y"))
                        .build();

                if (!repository.existsByPlaceId(store.getPlaceId())) {
                    repository.save(store);
                } else {
                    System.out.println("Store with placeId " + store.getPlaceId() + " already exists.");
                }
            }
        } catch (ParseException e) {
            // Handle parsing error
            e.printStackTrace();
            throw new RuntimeException("Failed to parse selectedPlacesData");
        }
    }

}
