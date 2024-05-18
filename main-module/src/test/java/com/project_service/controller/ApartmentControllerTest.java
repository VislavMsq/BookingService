//package com.project_service.controller;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.project_service.dto.ApartmentDto;
//import com.project_service.service.ApartmentService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.security.test.context.support.WithUserDetails;
//import org.springframework.test.context.jdbc.Sql;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Sql("/database/schema-cleanup.sql")
//@Sql("/database/create_tables.sql")
//@Sql("/database/add_test_data.sql")
//@AutoConfigureMockMvc
//class ApartmentControllerTest {
//
//    @Autowired
//    ApartmentService apartmentService;
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    ObjectMapper objectMapper;
//
//    @Test
//    @WithUserDetails(value = "aloha.test@gmail.com")
//    void createApartment() throws Exception {
//        ApartmentDto expected = getApartmentDTO();
//
//        String toCreate = objectMapper.writeValueAsString(expected);
//
//        String apartmentDtoCreatedJson = mockMvc.perform(MockMvcRequestBuilders.post("/apartments")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(toCreate))
//                .andExpect(status().isCreated())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        ApartmentDto created = objectMapper.readValue(apartmentDtoCreatedJson, ApartmentDto.class);
//        String id = created.getId();
//        expected.setId(id);
//        assertEquals(expected, created);
//
//        String apartmentDtoJson = mockMvc.perform(
//                        MockMvcRequestBuilders.get("/apartments/" + id))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        ApartmentDto actual = objectMapper.readValue(apartmentDtoJson, ApartmentDto.class);
//
//        assertEquals(expected, actual);
//    }
//
//    @Test
//    @WithUserDetails(value = "user1@example.com")
//    void findAllApartments() throws Exception {
//        List<ApartmentDto> expectedList = expectListFindAllApartments();
//
//        String apartmentsListJson = mockMvc.perform(MockMvcRequestBuilders.get("/apartments"))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<ApartmentDto> actualList = objectMapper.readValue(apartmentsListJson, new TypeReference<>() {
//        });
//
//        assertEquals(expectedList, actualList);
//    }
//
//    @Test
//    @WithUserDetails(value = "user1@example.com")
//    void findApartmentByCountry() throws Exception {
//        List<ApartmentDto> expectedList = expectListFindApartmentsByCountry();
//        String country = "USA";
//
//        String apartmentsListJson = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/country/" + country))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<ApartmentDto> actualList = objectMapper.readValue(apartmentsListJson, new TypeReference<>() {
//        });
//
//        assertEquals(expectedList, actualList);
//    }
//
//
//    @Test
//    @WithUserDetails(value = "user1@example.com")
//    void findApartmentByCity() throws Exception {
//
//        List<ApartmentDto> expectedList = expectListFindApartmentsByCity();
//        String city = "New York";
//
//        String apartmentsListJson = mockMvc.perform(MockMvcRequestBuilders.get("/apartments/city/" + city))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        List<ApartmentDto> actualList = objectMapper.readValue(apartmentsListJson, new TypeReference<>() {
//        });
//
//        assertEquals(expectedList, actualList);
//    }
//
//    @Test
//    @WithUserDetails(value = "user1@example.com")
//    void setApartmentsCategory() throws Exception {
//        //given
//        String categoryId = "ad99034d-4a69-492f-b65f-4aef01d21ee6";
//        String apartmentId1 = "3f120739-8a84-4e21-84b3-7a66358157bf";
//        String apartmentId2 = "8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2";
//        String apartmentId3 = "f47ac10b-58cc-4372-a567-0e02b2c3d479";
//        List<String> listApartmentIds = List.of(apartmentId1, apartmentId2, apartmentId3);
//
//        String listApartmentIdsJson = objectMapper.writeValueAsString(listApartmentIds);
//
//        //when
//        mockMvc.perform(MockMvcRequestBuilders.put("/apartments/set-apartment-category/" + categoryId)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(listApartmentIdsJson))
//                .andExpect(status().isOk());
//
//        String apartmentDtoResponseJson = mockMvc.perform(
//                        MockMvcRequestBuilders.get("/apartments/" + apartmentId2))
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        ApartmentDto apartmentDtoResponse = objectMapper.readValue(apartmentDtoResponseJson, ApartmentDto.class);
//        assertEquals(categoryId, apartmentDtoResponse.getApartmentCategoryId());
//    }
//
//    private List<ApartmentDto> expectListFindAllApartments() {
//        List<ApartmentDto> apartments = new ArrayList<>();
//
//        apartments.add(createApartment(
//                "3f120739-8a84-4e21-84b3-7a66358157bf",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "123 Main St",
//                "5",
//                "true",
//                "false",
//                "1",
//                "Lorem ipsum",
//                "ad99034d-4a69-492f-b65f-4aef01d21ee6"
//        ));
//        apartments.add(createApartment(
//                "8c5fcf45-8e6d-42cd-8da3-c978c8cc58b2",
//                "Apartment 2",
//                "ROOM",
//                "UK",
//                "London",
//                "456 Oak St",
//                "3",
//                "false",
//                "true",
//                "2",
//                "Dolor sit amet",
//                "be2f0f46-9e36-4b99-8d62-8e498b783c38"
//        ));
//        apartments.add(createApartment(
//                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "Broadway",
//                "2",
//                "true",
//                "false",
//                "1",
//                "Cozy studio apartment in the heart of New York City",
//                null
//        ));
//        apartments.add(createApartment(
//                "1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d",
//                "Apartment 2",
//                "APARTMENT",
//                "USA",
//                "Los Angeles",
//                "Hollywood Blvd",
//                "3",
//                "false",
//                "true",
//                "2",
//                "Spacious apartment with Hollywood sign view",
//                null
//        ));
//        apartments.add(createApartment(
//                "eccbc87e-4b5c-4331-a025-6545673431ef",
//                "Apartment 3",
//                "ROOM",
//                "Canada",
//                "Toronto",
//                "Yonge Street",
//                "4",
//                "true",
//                "true",
//                "0",
//                "Modern house with a beautiful view of Lake Ontario",
//                null
//        ));
//
//        return apartments;
//    }
//
//    private List<ApartmentDto> expectListFindApartmentsByCountry() {
//        List<ApartmentDto> apartments = new ArrayList<>();
//
//        apartments.add(createApartment(
//                "3f120739-8a84-4e21-84b3-7a66358157bf",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "123 Main St",
//                "5",
//                "true",
//                "false",
//                "1",
//                "Lorem ipsum",
//                "ad99034d-4a69-492f-b65f-4aef01d21ee6"
//        ));
//        apartments.add(createApartment(
//                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "Broadway",
//                "2",
//                "true",
//                "false",
//                "1",
//                "Cozy studio apartment in the heart of New York City",
//                null
//        ));
//        apartments.add(createApartment(
//                "1ac2ab88-4efc-4ea7-a6d7-9738c7b0ca5d",
//                "Apartment 2",
//                "APARTMENT",
//                "USA",
//                "Los Angeles",
//                "Hollywood Blvd",
//                "3",
//                "false",
//                "true",
//                "2",
//                "Spacious apartment with Hollywood sign view",
//                null
//        ));
//
//        return apartments;
//    }
//
//    private List<ApartmentDto> expectListFindApartmentsByCity() {
//        List<ApartmentDto> apartments = new ArrayList<>();
//
//        apartments.add(createApartment(
//                "3f120739-8a84-4e21-84b3-7a66358157bf",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "123 Main St",
//                "5",
//                "true",
//                "false",
//                "1",
//                "Lorem ipsum",
//                "ad99034d-4a69-492f-b65f-4aef01d21ee6"
//        ));
//        apartments.add(createApartment(
//                "f47ac10b-58cc-4372-a567-0e02b2c3d479",
//                "Apartment 1",
//                "APARTMENT",
//                "USA",
//                "New York",
//                "Broadway",
//                "2",
//                "true",
//                "false",
//                "1",
//                "Cozy studio apartment in the heart of New York City",
//                null
//        ));
//
//        return apartments;
//    }
//
//    private ApartmentDto createApartment(String id, String name, String type, String country, String city, String street,
//                                         String floor, String pet, String smoking, String parkingPlace, String description,
//                                         String apartmentCategoryId) {
//        ApartmentDto apartment = new ApartmentDto();
//        apartment.setId(id);
//        apartment.setName(name);
//        apartment.setType(type);
//        apartment.setCountry(country);
//        apartment.setCity(city);
//        apartment.setStreet(street);
//        apartment.setFloor(floor);
//        apartment.setPet(pet);
//        apartment.setSmoking(smoking);
//        apartment.setParkingPlace(parkingPlace);
//        apartment.setDescription(description);
//        apartment.setApartmentCategoryId(apartmentCategoryId);
//        apartment.setParentId(null);
//        return apartment;
//    }
//
//    private static ApartmentDto getApartmentDTO() {
//        ApartmentDto expected = new ApartmentDto();
//        expected.setName("Apartment 1");
//        expected.setType("APARTMENT");
//        expected.setCountry("USA");
//        expected.setCity("New York");
//        expected.setStreet("123 Main St");
//        expected.setFloor("5");
//        expected.setPet("true");
//        expected.setSmoking("false");
//        expected.setParkingPlace("1");
//        expected.setDescription("Lorem ipsum");
//        expected.setApartmentCategoryId("be2f0f46-9e36-4b99-8d62-8e498b783c38");
//        expected.setParentId("3f120739-8a84-4e21-84b3-7a66358157bf");
//        return expected;
//    }
//
//
//}
