/* (C)2023 */
package com.studies.zamproject.controllers;

import com.studies.zamproject.dtos.TagDTO;
import com.studies.zamproject.services.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping()
    public ResponseEntity<List<TagDTO>> getTags() {
        return ResponseEntity.ok(tagService.getTags());
    }
}
