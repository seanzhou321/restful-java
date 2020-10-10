package base.donor.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import base.donor.assembler.DonorModelAssembler;
import base.donor.exception.DonorNotFoundException;
import base.donor.model.Donor;
import base.donor.service.DonorRepository;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HateoasDonorController {

  private final DonorRepository donorRepository;
  private final DonorModelAssembler donorModelAssembler;

  public HateoasDonorController(DonorRepository donorRepository,
      DonorModelAssembler donorModelAssembler) {
    this.donorRepository = donorRepository;
    this.donorModelAssembler = donorModelAssembler;
  }

  @PostMapping("/hateoas/donor")
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<Donor> create(@NotNull @RequestBody Donor donor) {

    Donor donor1 = donorRepository.save(donor);
    return donorModelAssembler.toModel(donor1);
  }

  @GetMapping("/hateoas/donor")
  public CollectionModel<EntityModel<Donor>> view() {

    List<EntityModel<Donor>> donors = donorRepository.findAll().stream()
        .map(donorModelAssembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(donors,
        linkTo(methodOn(HateoasDonorController.class).view()).withSelfRel());
  }

  @GetMapping("/hateoas/donor/{id}")
  public EntityModel<Donor> findById(@Parameter(description = "ID of book to be searched") @PathVariable int id) {
    Donor donor = donorRepository.findById(id).orElseThrow(() -> new DonorNotFoundException());
    return donorModelAssembler.toModel(donor);
  }

  @PutMapping("/hateoas/donor")
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<Donor> update(@RequestBody Donor donor) {

    donorRepository.save(donor);
    return donorModelAssembler.toModel(donor);
  }

  @DeleteMapping("/hateoas/donor/{id}")
  public void delete(@PathVariable Integer id) {
    donorRepository.deleteById(id);
  }
}
