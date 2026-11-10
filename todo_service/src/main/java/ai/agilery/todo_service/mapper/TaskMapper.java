package ai.agilery.todo_service.mapper;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.entity.Task;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

/**
 * Mapper interface for converting between Task entity and TaskDto.
 * Uses MapStruct for automatic mapping generation with custom enum conversions.
 */
@Mapper(componentModel = "spring")
public abstract class TaskMapper implements EntityMapper<TaskDto, Task> {

    /**
     * Converts a TaskDto to a Task entity.
     * Ignores audit fields as they are managed by JPA.
     *
     * @param dto the TaskDto to convert
     * @return the converted Task entity
     */
    @Override
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapDtoStatusToEntity")
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapDtoPriorityToEntity")
    public abstract Task toEntity(TaskDto dto);

    /**
     * Converts a Task entity to a TaskDto.
     *
     * @param entity the Task entity to convert
     * @return the converted TaskDto
     */
    @Override
    @Mapping(target = "status", source = "status", qualifiedByName = "mapEntityStatusToDto")
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapEntityPriorityToDto")
    public abstract TaskDto toDto(Task entity);

    /**
     * Updates an existing Task entity with values from a TaskDto.
     * Ignores id and audit fields to preserve entity identity and audit trail.
     *
     * @param entity the Task entity to update
     * @param dto the TaskDto containing new values
     */
    @Override
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapDtoStatusToEntity")
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapDtoPriorityToEntity")
    public abstract void update(@MappingTarget Task entity, TaskDto dto);

    /**
     * Partially updates an existing Task entity with non-null values from a TaskDto.
     * Ignores id and audit fields to preserve entity identity and audit trail.
     * Only updates fields that are not null in the DTO.
     *
     * @param entity the Task entity to patch
     * @param dto the TaskDto containing new values
     */
    @Override
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDateTime", ignore = true)
    @Mapping(target = "lastModifiedDateTime", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "modifiedBy", ignore = true)
    @Mapping(target = "status", source = "status", qualifiedByName = "mapDtoStatusToEntity")
    @Mapping(target = "priority", source = "priority", qualifiedByName = "mapDtoPriorityToEntity")
    public abstract void patch(@MappingTarget Task entity, TaskDto dto);

    /**
     * Maps TaskStatus string from DTO to entity enum.
     *
     * @param dtoStatus the status string from DTO
     * @return the corresponding entity TaskStatus enum, or null if input is null
     */
    @Named("mapDtoStatusToEntity")
    protected ai.agilery.todo_service.entity.TaskStatus mapDtoStatusToEntity(String dtoStatus) {
        return dtoStatus != null ? ai.agilery.todo_service.entity.TaskStatus.valueOf(dtoStatus) : null;
    }

    /**
     * Maps TaskStatus entity enum to DTO string.
     *
     * @param entityStatus the entity TaskStatus enum
     * @return the corresponding DTO status string, or null if input is null
     */
    @Named("mapEntityStatusToDto")
    protected String mapEntityStatusToDto(ai.agilery.todo_service.entity.TaskStatus entityStatus) {
        return entityStatus != null ? entityStatus.name() : null;
    }

    /**
     * Maps TaskPriority string from DTO to entity enum.
     *
     * @param dtoPriority the priority string from DTO
     * @return the corresponding entity TaskPriority enum, or null if input is null
     */
    @Named("mapDtoPriorityToEntity")
    protected ai.agilery.todo_service.entity.TaskPriority mapDtoPriorityToEntity(String dtoPriority) {
        return dtoPriority != null ? ai.agilery.todo_service.entity.TaskPriority.valueOf(dtoPriority) : null;
    }

    /**
     * Maps TaskPriority entity enum to DTO string.
     *
     * @param entityPriority the entity TaskPriority enum
     * @return the corresponding DTO priority string, or null if input is null
     */
    @Named("mapEntityPriorityToDto")
    protected String mapEntityPriorityToDto(ai.agilery.todo_service.entity.TaskPriority entityPriority) {
        return entityPriority != null ? entityPriority.name() : null;
    }
}