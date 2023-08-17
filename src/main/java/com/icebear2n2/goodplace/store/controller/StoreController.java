package com.icebear2n2.goodplace.store.controller;

import com.icebear2n2.goodplace.store.service.RestTemplateService;
import com.icebear2n2.goodplace.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class StoreController {

    private final RestTemplateService restTemplateService;
    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<String> getData(@RequestParam(value = "query") String query) throws Exception {
        return restTemplateService.getSearchPlaceByKeyword(query);
    }

    @PostMapping
    public void insert(@RequestParam(value = "query") String query) throws Exception {
        storeService.insert(query);
    }

    @CrossOrigin("*")
    @PostMapping("/submit-selected-places")
    public ResponseEntity<String> submitSelectedPlaces(@RequestParam("selectedPlacesData") String selectedPlacesData) {
        try {
            storeService.saveSelectedPlaces(selectedPlacesData);
            return new ResponseEntity<>("Selected places submitted successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to submit selected places.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}