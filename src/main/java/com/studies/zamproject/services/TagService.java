/* (C)2023 */
package com.studies.zamproject.services;

import com.studies.zamproject.dtos.TagDTO;
import com.studies.zamproject.mappers.TagMapper;
import com.studies.zamproject.repositories.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagMapper tagMapper;
    private final TagRepository tagRepository;

    public List<TagDTO> getTags() {
        return tagRepository.findAll().stream().map(tagMapper::tagToTagDTO).toList();
    }
}
