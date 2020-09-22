package com.user.management.demo.application.employee.web;

import com.user.management.demo.application.employee.web.dto.EmployeeDTO;
import com.user.management.demo.application.employee.web.dto.EmployeeDetailsDTO;
import com.user.management.demo.application.employee.web.dto.EmployeeSearchParamsDTO;
import com.user.management.demo.application.employee.domain.EmployeeApplicationService;
import com.user.management.demo.domain.employee.Grade;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/employees")
@RequiredArgsConstructor
public class EmployeeRestController {

	private final EmployeeApplicationService employeeApplicationService;

	@GetMapping("/{id}")
	public EmployeeDTO get(@PathVariable Long id) {
		return employeeApplicationService.getDetails(id);
	}

	@GetMapping
	public Page<EmployeeDTO> search(@RequestParam(required = false) String firstName,
	                                @RequestParam(required = false) String surname,
	                                @RequestParam(required = false) Grade grade,
	                                @RequestParam(required = false) BigDecimal salaryFrom,
	                                @RequestParam(required = false) BigDecimal salaryTo,
	                                @RequestParam(defaultValue = "20") Integer size,
	                                @RequestParam(defaultValue = "0") Integer page,
	                                @RequestParam(defaultValue = "id") String sortBy,
	                                @RequestParam(defaultValue = "ASC") Sort.Direction order) {
		return employeeApplicationService.search(
				new EmployeeSearchParamsDTO(
						firstName,
						surname,
						grade,
						salaryFrom,
						salaryTo
				),
				PageRequest.of(indexPageSize(page), size, Sort.by(order, sortBy))
		);
	}

	@PostMapping
	public EmployeeDTO create(@RequestBody EmployeeDetailsDTO dto) {
		return employeeApplicationService.create(dto);
	}

	@PutMapping("/{id}")
	public EmployeeDTO update(@PathVariable Long id, @RequestBody EmployeeDetailsDTO dto) {
		return employeeApplicationService.update(id, dto);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		employeeApplicationService.delete(id);
	}

	private Integer indexPageSize(Integer page) {
		if (page > 0) {
			page = page - 1;
		} else {
			page = 0;
		}
		return page;
	}
}
