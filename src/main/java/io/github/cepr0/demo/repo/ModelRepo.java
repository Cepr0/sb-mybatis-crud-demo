package io.github.cepr0.demo.repo;

import io.github.cepr0.demo.exception.EntityNotFoundException;
import io.github.cepr0.demo.exception.OptimisticLockException;
import io.github.cepr0.demo.model.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Transactional
@Repository
public class ModelRepo {

	private final ModelDBMapper db;

	public ModelRepo(final ModelDBMapper db) {
		this.db = db;
	}

	public void create(@NonNull Model model) {
		int rows = db.insert(model);
		log.debug("[d] {} inserted. Number of inserted rows: {}", model, rows);
	}

	public void update(int id, Model source) {
		checkVersionAndProcess(id, (version) -> db.update(id, version, source));
		log.debug("[d] Model updated with {} by id {}", source, id);
	}

	public void delete(int id) {
		checkVersionAndProcess(id, (version) -> db.delete(id, version));
		log.debug("[d] Model deleted by id {}", id);
	}

	private void checkVersionAndProcess(int id, Function<Integer, Integer> operation) {
		Integer version = db.getVersion(id);
		if (version != null) {
			int rows = operation.apply(version);
			if (rows == 0) {
				log.error("[!] Model with id {} and version {} not updated", id, version);
				throw new OptimisticLockException("Model", id, version);
			}
			log.debug("[d] Number of processed rows: {}", rows);
		} else {
			log.debug("[d] Model not found");
			throw new EntityNotFoundException("Model", id);
		}
	}

	public Optional<Model> get(int id) {
		Model model = db.get(id);
		if (model != null) {
			return Optional.of(model);
		} else {
			log.debug("[d] Model not found");
			return Optional.empty();
		}
	}

	public List<Model> getAll() {
		List<Model> models = db.getAll();
		log.info("[i] Number of read records: {}", models.size());
		return models;
	}

	public Page<Model> getAll(Pageable pageable) {
		List<Model> chunk = db.getAllPaged(pageable);
		long total = chunk.size();
		if (total == pageable.getPageSize() || pageable.getPageNumber() != 0) {
			total = db.getTotal();
		}
		log.info("[i] Number of read records: {}", chunk.size());
		return new PageImpl<>(chunk, pageable, total);
	}
}
