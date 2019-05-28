package io.github.cepr0.demo.service;

import io.github.cepr0.demo.model.Model;
import io.github.cepr0.demo.model.ModelCreatedEvent;
import io.github.cepr0.demo.repo.ModelRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class ModelService {

	private final ModelRepo repo;
	private final ApplicationEventPublisher publisher;

	public ModelService(final ModelRepo repo, final ApplicationEventPublisher publisher) {
		this.repo = repo;
		this.publisher = publisher;
	}

	public Model create(Model model) {
		repo.create(model);
		publisher.publishEvent(new ModelCreatedEvent(model));
		return model;
	}

	public void update(int id, Model source) {
		repo.update(id, source);
	}

	public void delete(int id) {
		repo.delete(id);
	}

	public Optional<Model> get(int id) {
		return repo.get(id);
	}

	public List<Model> getAll() {
		return repo.getAll();
	}

	public Page<Model> getAll(Pageable pageable) {
		return repo.getAll(pageable);
	}

	@EventListener
	@Transactional(propagation = Propagation.MANDATORY)
	public void handleModelCreatedEvent(ModelCreatedEvent e) {
		Model model = e.getModel();
		log.info("[i] Model created: {}", model);
	}

}
