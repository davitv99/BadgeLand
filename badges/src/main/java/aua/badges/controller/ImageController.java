package aua.badges.controller;

import aua.badges.model.Image;
import aua.badges.security.SecurityService;
import aua.badges.service.ImageService;
import aua.badges.utility.MapperUtility;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * @author davitv
 */


@RestController
@RequestMapping("/api/images")
@Slf4j
public class ImageController {
    @Autowired
    private SecurityService securityService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private MapperUtility mapperUtility;
    @PostMapping(path = "/upload/badge/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity uploadBadgeLogo(
            final @RequestParam(required = true) String token,
            final @RequestParam(required = true) MultipartFile logo) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        boolean status;
        try {
            status = imageService.uploadLogo(logo);
            if(status){
                return ResponseEntity.ok().body("Logo is uploaded");
            }
            return ResponseEntity.badRequest().body("There was an error while uploading logo");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("There was an error while uploading logo:" + e.getLocalizedMessage());
        }


    }

    @GetMapping(path = "/get/badge/logo/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getAllBadgeLogos(
            final @RequestParam(name = "Authorization",required = true) String token
            ) {
        if(!securityService.validateToken(token)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
        }
        List<Image> logos = imageService.getAllBadgeLogos();
        if(!logos.isEmpty()){
            return ResponseEntity.ok().body(mapperUtility.imagesToImageDTOS(logos));
        }
        return ResponseEntity.badRequest().body("There are no available badge logos");
    }
}
