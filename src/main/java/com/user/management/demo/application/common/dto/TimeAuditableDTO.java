package com.user.management.demo.application.common.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class TimeAuditableDTO {

	protected Long id;
	protected LocalDateTime creationTime;
	protected LocalDateTime modificationTime;

}
