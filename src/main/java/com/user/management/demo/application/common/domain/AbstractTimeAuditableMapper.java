package com.user.management.demo.application.common.domain;

import com.user.management.demo.application.common.dto.TimeAuditableDTO;
import com.user.management.demo.domain.common.TimeAuditableEntity;

public interface AbstractTimeAuditableMapper<S extends TimeAuditableEntity, T extends TimeAuditableDTO> {

	S toDomain(T t);

	T fromDomain(S s);
}
