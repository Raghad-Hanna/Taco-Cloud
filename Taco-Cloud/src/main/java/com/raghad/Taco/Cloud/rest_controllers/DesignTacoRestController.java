package com.raghad.Taco.Cloud.rest_controllers;


import com.raghad.Taco.Cloud.models.Taco;
import com.raghad.Taco.Cloud.repositories.JPATacoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * we can use spring data rest dependency to generate APIS for spring data repositories
 * automatically, to get a list of all the exposed endpoints that spring data rest has generated
 * we can call /, the APIs are hyperlink enabled.
 *
 * the endpoints it generates are based on the repositories we have.
 *
 * to set a base path for the generated APIs, we can set a property spring.data.rest.base-path
 * to customize the relation name (hypermedia specific thing) and the path name (endpoint) for an entity,
 * we can use @RestResource(rel = "relationName", path = "pathName") above the entity class
 * if spring data rest can't generate all the needed endpoints or some of its generated endpoints
 * are not preferred, we can implement our custom endpoints along the generated endpoints.
 * to let both the generated endpoints and our custom endpoints have the same base path
 * we can use @RepositoryRestController above the controller that will implement the custom endpoints
 * this annotations makes all of the controller action mappings assume the same base path that's
 * been configured for spring data rest, all the mappings will be prefixed with the value of
 * spring.data.rest.base-path.
 *
 * @RepositoryRestController is sufficient to annotate the controller class with, there's no need
 * for @RestController, but it's not composed of @ResponseBody, so we either need to use ResponseEntity
 * or annotate the controller actions with @ResponseBody.
 *
 * reminder: we use @ResponseBody or EntityResponse to bypass the model and the view
 * and write the data directly to the response.
 * 
 * the custom endpoints we create are not included in the hyperlinks list when requesting
 * a generated endpoint (there is a fix for this, we can add custom endpoints to the
 * hyperlink enabled spring data rest generated endpoints)
 */



@RestController
/**
 * the produces attribute says that all the controller actions produce a json-formatted output
 * and therefore this controller only accepts requests that contain a accept header that has the
 * value application/json, this limits the controller to only producing json output as well as
 * possibly having other controllers handle requests at the same path as long as they don't
 * produce json output (and the requests don't require json output)
 * we can set the produces attribute to an array of output formats
 */
@RequestMapping(path = "/design", produces = "application/json")
/**
 * allows the clients from any domain to consume the API
 * this will include a CORS header in the server response and will overcome the browser
 * restrictions on calling an API on a different IP and/or port
 */
@CrossOrigin(origins = "*")
public class DesignTacoRestController {
    @Autowired
    private JPATacoRepository tacoRepository;

    @GetMapping(path = "/recent")
    public List<Taco> recentTacos() {
        Pageable page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        return this.tacoRepository.findAll(page).getContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable(name = "id") Long id) {
        Optional<Taco> taco = this.tacoRepository.findById(id);
        if (taco.isPresent()) {
            return new ResponseEntity<>(taco.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Taco saveTaco(@RequestBody Taco taco) {
        return this.tacoRepository.save(taco);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteTaco(@PathVariable(name = "id") Long id) {
        try {
            this.tacoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            /**
             * this exception will be thrown if the resource did not exist in the first place
             * we could set a status code of 404 but we can think about it like this:
             * it's the same result whether the resource did exist and got deleted
             * or the resource did not exist in the first place
             */
        }
    }
}
