package io.github.cepr0.demo.api;

import io.github.cepr0.demo.model.Model;
import io.github.cepr0.demo.service.ModelService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static io.github.cepr0.demo.api.ModelController.MODELS;

@RestController
@RequestMapping(MODELS)
public class ModelController {

	static final String MODELS = "/models";

	private final ModelService service;

	public ModelController(final ModelService service) {
		this.service = service;
	}

	@PostMapping
	public ResponseEntity create(@RequestBody Model model) {
		service.create(model);
		return ResponseEntity.created(URI.create(MODELS + "/" + model.getId())).body(model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PatchMapping("/{id}")
	public void update(@PathVariable Integer id, @RequestBody Model model) {
		service.update(id, model);
	}

	@ResponseStatus(HttpStatus.NO_CONTENT)
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Integer id) {
		service.delete(id);
	}

	@GetMapping("/{id}")
	public ResponseEntity get(@PathVariable Integer id) {
		return service.get(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/paged")
	public Page<Model> getAllPaged(Pageable pageable) {
		return service.getAll(pageable);
	}

	@GetMapping
	public List<Model> getAll() {
		return service.getAll();
	}
}
