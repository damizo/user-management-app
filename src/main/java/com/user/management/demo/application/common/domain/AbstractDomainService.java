package com.user.management.demo.application.common.domain;

import com.user.management.demo.application.common.exception.ErrorType;
import com.user.management.demo.application.common.exception.ParameterizedException;
import com.user.management.demo.infrastructure.annotations.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@DomainService
@RequiredArgsConstructor
public abstract class AbstractDomainService<T, TId, R extends JpaRepository<T, TId>> {

	protected final R r;

	public T get(TId tid) {
		return r.findById(tid)
				.orElseThrow(() -> new ParameterizedException(ErrorType.ENTITY_NOT_FOUND, Collections.singletonMap("id", tid)));
	}

	public Optional<T> find(TId tid) {
		return r.findById(tid);
	}

	public T createOrUpdate(T t) {
		return r.save(t);
	}

	public void delete(T t) {
		r.delete(t);
	}

	public void deleteById(TId tid) {
		r.deleteById(tid);
	}

	public List<T> findAll() {
		return r.findAll();
	}

	//TODO:
	public T getActive(TId id) {
		throw new UnsupportedOperationException();
	}

}
