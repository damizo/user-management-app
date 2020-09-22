package com.user.management.demo.domain.common;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@MappedSuperclass
public abstract class TimeAuditableEntity {

	@Id
	@Column(name = "id", updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	@Column(name = "creation_time", updatable = false)
	protected LocalDateTime creationTime;

	@Column(name = "modification_time")
	protected LocalDateTime modificationTime;

	@PrePersist
	public void prePersist() {
		this.creationTime = LocalDateTime.now();
		this.modificationTime = LocalDateTime.now();
	}

	@PreUpdate
	public void preUpdate() {
		this.modificationTime = LocalDateTime.now();
	}

}
