package com.project_service.bookingservice.service.impl;

import com.project_service.bookingservice.dto.ApartmentCategoryDTO;
import com.project_service.bookingservice.entity.ApartmentCategory;
import com.project_service.bookingservice.entity.User;
import com.project_service.bookingservice.exception.ApartmentCategoryNotFoundException;
import com.project_service.bookingservice.mapper.ApartmentCategoryMapper;
import com.project_service.bookingservice.repository.ApartmentCategoryRepository;
import com.project_service.bookingservice.security.UserProvider;
import com.project_service.bookingservice.service.ApartmentCategoryService;
import com.project_service.bookingservice.service.UtilsService;
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
    public ApartmentCategoryDTO createApartmentCategory(ApartmentCategoryDTO apartmentCategoryCreateDTO) {
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
    public ApartmentCategoryDTO getDTO(String uuid) {
        return apartmentCategoryMapper.toApartmentDTO(getApartmentCategory(uuid));
    }

    @Override
    @Transactional
    public List<ApartmentCategoryDTO> getList() {
        User user = userProvider.getCurrentUser();
        UUID id = user.getOwner() == null ? user.getId() : user.getOwner().getId();
        List<ApartmentCategory> categories = apartmentCategoryRepository.findByOwnerId(id);
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
