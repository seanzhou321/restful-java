package base.donor.controller;

import static org.hamcrest.Matchers.containsString;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import base.donor.assembler.DonorModelAssembler;
import base.donor.model.Donor;
import base.donor.service.DonorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * @WebMvcTest(class) tests only the restfull web layer. It allows to inject the mocks of the dependent services to the api layer.
 * Specifying the classes of this test suite
 *
 * @Import imports the assembler bean into the HateoasDonorController to be tested).
 */

@WebMvcTest(HateoasDonorController.class)
@Import(DonorModelAssembler.class)
class HateoasDonorControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DonorRepository donorRepository;

  private ObjectMapper objectMapper = new ObjectMapper();

  private Donor testDonor = new Donor("hateoastest1", "hateoastest2");

  @Test
  void create() {
    try {
      Donor donorIn = new Donor("Jackhateos", "Deanhateoas");
      Donor donorSaved = donorIn.clone();
      donorSaved.setId(1);
      when(donorRepository.save(eq(donorIn))).thenReturn(donorSaved);
      mockMvc.perform(post("/hateoas/donor")
      .content(objectMapper.writeValueAsString(new Donor("Jackhateos", "Deanhateoas")))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print()).andExpect(status().isCreated())
          .andExpect(content().string(containsString(donorIn.getLastName())))
          .andExpect(jsonPath("$._links.self.href").exists())
          .andExpect(jsonPath("$._links.donors.href").exists());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void view() {
    try {
      List<Donor> donors = new ArrayList<>();
      for (int i=1; i<4; i++) {
        Donor t = new Donor("first"+i, "last"+i);
        t.setId(i);
        donors.add(t);
      }
      when(donorRepository.findAll()).thenReturn(donors);
      mockMvc.perform(get("/hateoas/donor")).andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$._embedded.donorList[*].id").isNotEmpty())
          .andExpect(jsonPath("$._embedded.donorList[*]._links.self").isNotEmpty())
          .andExpect(jsonPath("$._embedded.donorList[*]._links.donors").isNotEmpty())
          .andExpect(jsonPath("$._links.self").isNotEmpty());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void findById() {
    try {
      Donor donor = testDonor.clone();
      donor.setId(5);
      when(donorRepository.findById(eq(donor.getId()))).thenReturn(Optional.of(donor));
      mockMvc.perform(get("/hateoas/donor/{id}", donor.getId())).andDo(print())
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.firstName").value(testDonor.getFirstName()))
          .andExpect(jsonPath("$._links.self").isNotEmpty())
          .andExpect(jsonPath("$._links.donors").isNotEmpty());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void update() {
    try {
      Donor donor = testDonor.clone();
      donor.setId(3);
      when(donorRepository.save(eq(donor))).thenReturn(donor);
      mockMvc.perform(put("/hateoas/donor")
          .content(objectMapper.writeValueAsString(donor))
          .contentType(MediaType.APPLICATION_JSON)
          .accept(MediaType.APPLICATION_JSON))
          .andDo(print()).andExpect(status().isCreated())
          .andExpect(jsonPath("$.firstName").value(testDonor.getFirstName()))
          .andExpect(jsonPath("$._links.self").isNotEmpty())
          .andExpect(jsonPath("$._links.donors").isNotEmpty());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void delete() {
    try {
      Donor donor = testDonor.clone();
      donor.setId(1);
      mockMvc.perform(MockMvcRequestBuilders.delete("/hateoas/donor/{id}", donor.getId()))
          .andDo(print()).andExpect(status().isOk());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}