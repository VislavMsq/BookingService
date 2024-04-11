package com.project_service.service.impl;


import com.project_service.dto.ApartmentCategoryDto;
import com.project_service.entity.ApartmentCategory;
import com.project_service.entity.User;
import com.project_service.entity.enums.Role;
import com.project_service.exception.ApartmentCategoryNotFoundException;
import com.project_service.mapper.ApartmentCategoryMapper;
import com.project_service.repository.ApartmentCategoryRepository;
import com.project_service.security.UserProvider;
import com.project_service.service.ApartmentCategoryService;
import com.project_service.service.UtilsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ApartmentCategoryImpl implements ApartmentCategoryService {
    private final ApartmentCategoryMapper apartmentCategoryMapper;
    private final ApartmentCategoryRepository apartmentCategoryRepository;
    private final UserProvider userProvider;

    @Override
    @Transactional
    public ApartmentCategoryDto createApartmentCategory(ApartmentCategoryDto apartmentCategoryCreateDTO) {
        User owner = userProvider.getCurrentUser();
        ApartmentCategory apartmentCategory = apartmentCategoryMapper.toEntity(apartmentCategoryCreateDTO);
        apartmentCategory.setOwner(owner);
        apartmentCategoryRepository.save(apartmentCategory);
        return apartmentCategoryMapper.toApartmentDTO(apartmentCategory);
    }

    @Override
    public ApartmentCategory getApartmentCategory(String uuid) {
        return apartmentCategoryRepository.findById(UUID.fromString(uuid))
                .orElseThrow(() -> new ApartmentCategoryNotFoundException("Category not found!"));
    }

    @Override
    @Transactional
    public ApartmentCategoryDto getDTO(String uuid) {
        return apartmentCategoryMapper.toApartmentDTO(getApartmentCategory(uuid));
    }

    @Override
    @Transactional
    public List<ApartmentCategoryDto> getList() {
        User user = userProvider.getCurrentUser();
        User owner = user.getRole().equals(Role.OWNER) ? user : user.getOwner();
        List<ApartmentCategory> categories = apartmentCategoryRepository.findByOwner(owner);
        return apartmentCategoryMapper.toListDTO(categories);
    }

    @Override
    @Transactional
    public void deleteApartmentCategory(String id) {
        User user = userProvider.getCurrentUser();
        ApartmentCategory apartmentCategory = getApartmentCategory(id);
        UtilsService.checkOwner(apartmentCategory, user);
        apartmentCategory.setIsDeleted(true);
    }

}
