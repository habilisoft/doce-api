package com.habilisoft.doce.api.domain.services;

import com.habilisoft.doce.api.domain.commands.CreateEmployee;
import com.habilisoft.doce.api.domain.commands.UpdateEmployee;
import com.habilisoft.doce.api.domain.events.EmployeeCreatedEvent;
import com.habilisoft.doce.api.domain.events.EmployeeEditedEvent;
import com.habilisoft.doce.api.domain.exceptions.DuplicatedEnrollIdException;
import com.habilisoft.doce.api.domain.exceptions.EmployeeNotFoundException;
import com.habilisoft.doce.api.domain.model.Group;
import com.habilisoft.doce.api.domain.model.Employee;
import com.habilisoft.doce.api.domain.model.Location;
import com.habilisoft.doce.api.domain.model.LocationType;
import com.habilisoft.doce.api.domain.repositories.EmployeeRepository;
import com.habilisoft.doce.api.persistence.repositories.EmployeeJpaRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Created on 2019-04-22.
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;
    private final ApplicationEventPublisher eventPublisher;
    private final ModelMapper modelMapper;
    private final EmployeeJpaRepo employeeJpaRepo;

    @Transactional
    public Employee create(CreateEmployee request) {
        final Optional<Employee> employeeExists = repository.findByEnrollId(request.getEnrollId());

        if (employeeExists.isPresent()) {
            throw new DuplicatedEnrollIdException(request.getEnrollId());
        }
        Employee employee = Employee.builder()
                .fullName(request.getFullName())
                .documentNumber(request.getDocumentNumber())
                .enrollId(request.getEnrollId())
                .location(
                        Optional.ofNullable(request.getLocationId())
                                .map(Location::ofId)
                                .orElse(null)
                )
                .group(request.getGroup())
                .workShift(request.getWorkShift())
                .locationType(request.getLocationType())
                .build();

        employee = repository.save(employee);

        eventPublisher.publishEvent(
                EmployeeCreatedEvent.builder()
                        .employee(employee)
                        .source(this)
                        .build());
        return employee;
    }

    @Transactional
    public Employee update(Long employeeId, UpdateEmployee request) {
        Employee employee = repository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));

        final Optional<Employee> employeeExists = repository.findByEnrollId(request.getEnrollId());

        if(employeeExists.isPresent() && !employeeExists.get().getId().equals(employeeId)) {
            throw new DuplicatedEnrollIdException(request.getEnrollId());
        }

        Employee oldEmployee = modelMapper.map(employee, Employee.class);

        employee.setFullName(request.getFullName());
        employee.setDocumentNumber(request.getDocumentNumber());
        employee.setLocation(request.getLocation());
        employee.setWorkShift(request.getWorkShift());
        employee.setEnrollId(request.getEnrollId());
        employee.setLocationType(
                Optional.ofNullable(request.getLocation())
                        .map((e) -> LocationType.FIXED)
                        .orElse(LocationType.AMBULATORY));

        employee.setGroup(request.getGroup());
        employee = repository.save(employee);

        eventPublisher.publishEvent(
                EmployeeEditedEvent.builder()
                        .oldEmployee(oldEmployee)
                        .newEmployee(employee)
                        .source(this)
                        .build());
        return employee;
    }

/*    @Transactional
    public void delete(Long id) {
        final Employee employee = this.repository.findById(id)
                .orElseThrow(() -> {
                    throw new EntityNotFoundException(String.format("No employee found with id %s", id));
                });

        this.deviceService.deleteUser(employee.getLocation(), employee.getEnrollId());
        super.delete(id);
    }*/

/*    @Transactional
    public Employee update(Long employeeId, UpdateEmployee employee) {
        Employee current = repository.findById(employeeId)
                .orElseThrow(EmployeeNotFoundException::new);
        Employee old = current;

        Location oldLocation = current.getLocation();
        final WorkShift currentWorkShift = current.getWorkShift();

        current.setFirstName(employee.getFirstName());
        current.setLastName(employee.getLastName());
        current.setLocation(employee.getLocation());
        current.setExternalId(employee.getExternalId());
        current.setDepartment(employee.getDepartment());
        current.setWorkShift(employee.getWorkShift());
        current.setLocationType(employee.getLocationType());

        repository.save(current);

        eventPublisher.publishEvent(
                EmployeeEditedEvent.builder()
                        .newEmployee(current)
                        .oldEmployee(old)
                        .source(this)
                        .build());

        return current;
    }*/

   /* public void importEmployees(List<EmployeeCsvRequest> employeeList) {

        AtomicReference<Integer> count = new AtomicReference<>(0);

        List<Employee> employees = employeeList.stream()
                .map(employeeCsvRequest -> new Employee(employeeCsvRequest, repository.getNextEnrollId() + count.getAndSet(count.get() + 1)))
                .collect(Collectors.toList());

        repository.saveAll(employees);
    }*/

/*    public void validateEmployeeHaveFingerPrintData(final Device device, final List<Employee> employees) {
        final String tenant = TenantContext.getCurrentTenant();
        Executors.newSingleThreadExecutor().submit(() -> employees.forEach(e -> {

                    TenantContext.setCurrentTenant(tenant);
                    final Employee employee = repository.findById(e.getId()).orElse(null);

                    if (employee != null && (employee.getFingerprintData() == null || employee.getFingerprintData().trim().isEmpty())) {
                        deviceService.getUserInfo(device, employee);

                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException ex) {
                        }
                    }
                }
        ));
    }*/

    public Optional<Employee> findByEnrollId(final Integer enrollId) {
        return this.repository.findByEnrollId(enrollId);
    }

    public Optional<Employee> findById(final Long id) {
        return this.repository.findById(id);
    }

    /*public WorkShift getEmployeeWorkShift(final Long employeeId) {
        final Optional<Employee> optional = this.repository.findById(employeeId);

        if (optional.isPresent()) {
            return optional.get().getWorkShift();
        }

        return null;
    }*/

   /* public void updateEmployeeWorkShift(final WorkShift oldWorkShift, final WorkShift newWorkShift) {
        this.repository.updateEmployeesWorkShift(oldWorkShift.getId(), newWorkShift.getId());
    }*/

    /*public void sendAllAmbulatoryEmployeesDeviceInfo(final Device device) {
        final String tenant = TenantContext.getCurrentTenant();
        Executors.newSingleThreadExecutor().submit(() -> {

            TenantContext.setCurrentTenant(tenant);
            this.repository.findByLocationType(LocationType.AMBULATORY)
                    .forEach(e -> {
                        deviceService.saveUser(device, e);
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e1) {
                        }
                    });
        });
    }*/

   /* private void validateLocationAndWorkShift(LocationRequest location, WorkShiftRequest workShift) {
        if (location == null) {
            throw new EmployeeMissingDataException(
                    String.format("El campo localidad es requerido para los empleados tipo %s",
                            LocationType.FIXED.getDisplayName()));
        }

        if (workShift == null) {
            throw new EmployeeMissingDataException(
                    String.format("El turno de trabajo es requerido para los empleados tipo %s",
                            LocationType.FIXED.getDisplayName()));
        }
    }*/

  /*  private Employee getEmployee(Long id) {
        Employee employee = this.getRepository().findById(id)
                .orElseThrow(() -> {
                    throw new EmployeeNotFoundException(id);
                });

        return employee;
    }*/


   /* @Transactional
    public EmployeeChangeDetail changeWorkShift(Employee employee, @Valid WorkShiftRequest request, final EmployeeTransfer employeeTransfer) {
        EmployeeChangeDetail employeeChangeDetail = EmployeeChangeDetail.builder()
                .documentType(ChangeDocumentType.WORK_SHIFT)
                .oldValue((employee.getWorkShift() == null) ? null : employee.getWorkShift().getName())
                .value(this.workShiftRepository.getOne(request.getId()).getName())
                .build();

        employee.setWorkShift(request.to());
        this.employeePublisher.changeWorkShift(request, employee, employeeTransfer);

        this.getRepository().save(employee);
        return employeeChangeDetail;
    }*/

}
