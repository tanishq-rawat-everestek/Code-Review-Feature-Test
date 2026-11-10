package ai.agilery.todo_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.agilery.todo_service.dto.TaskDto;
import ai.agilery.todo_service.service.TaskService;
import ai.agilery.todo_service.validation.OnCreate;
import ai.agilery.todo_service.validation.OnPatch;
import ai.agilery.todo_service.validation.OnUpdate;
import ai.agilery.todo_service.validation.impl.service.TaskValidationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * REST controller for managing Task resources.
 * <p>
 * Provides endpoints for CRUD operations on tasks including create, read,
 * update, patch, and delete operations. All operations include semantic
 * validation before execution.
 * </p>
 */
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Task Management", description = "APIs for managing tasks in the todo service")
public class TaskController {

    /**
     * Service for task business logic operations.
     */
    private final TaskService taskService;

    /**
     * Validation service for task operations.
     */
    private final TaskValidationService taskValidationService;

    /**
     * Creates a new task.
     *
     * @param taskDto the task data to create
     * @return ResponseEntity containing the created task
     */
    @PostMapping
    @Operation(summary = "Create a new task", description = "Creates a new task with the provided details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task created successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<TaskDto> createTask(
            @Validated(OnCreate.class) @RequestBody TaskDto taskDto) {
        log.info("POST /api/v1/tasks - Create task request received");
        taskValidationService.onCreate(taskDto);
        TaskDto createdTask = taskService.create(taskDto);
        log.info("Task created successfully with ID: {}", createdTask.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

    /**
     * Updates an existing task.
     *
     * @param id the task ID
     * @param taskDto the updated task data
     * @return ResponseEntity containing the updated task
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update a task", description = "Updates all fields of an existing task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task updated successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<TaskDto> updateTask(
            @Parameter(description = "Task ID", required = true) @PathVariable UUID id,
            @Validated(OnUpdate.class) @RequestBody TaskDto taskDto) {
        log.info("PUT /api/v1/tasks/{} - Update task request received", id);
        taskDto.setId(id);
        taskValidationService.onUpdate(taskDto);
        TaskDto updatedTask = taskService.update(taskDto);
        log.info("Task with ID: {} updated successfully", id);
        return ResponseEntity.ok(updatedTask);
    }

    /**
     * Partially updates an existing task.
     *
     * @param id the task ID
     * @param taskDto the partial task data to update
     * @return ResponseEntity containing the patched task
     */
    @PatchMapping("/{id}")
    @Operation(summary = "Partially update a task", description = "Updates only the provided fields of a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task patched successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<TaskDto> patchTask(
            @Parameter(description = "Task ID", required = true) @PathVariable UUID id,
            @Validated(OnPatch.class) @RequestBody TaskDto taskDto) {
        log.info("PATCH /api/v1/tasks/{} - Patch task request received", id);
        taskDto.setId(id);
        taskValidationService.onPatch(taskDto);
        TaskDto patchedTask = taskService.patch(taskDto);
        log.info("Task with ID: {} patched successfully", id);
        return ResponseEntity.ok(patchedTask);
    }

    /**
     * Retrieves a task by ID.
     *
     * @param id the task ID
     * @return ResponseEntity containing the task
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a task by ID", description = "Retrieves a single task by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Task retrieved successfully",
                    content = @Content(schema = @Schema(implementation = TaskDto.class))),
            @ApiResponse(responseCode = "404", description = "Task not found")
    })
    public ResponseEntity<TaskDto> getTask(
            @Parameter(description = "Task ID", required = true) @PathVariable UUID id) {
        log.info("GET /api/v1/tasks/{} - Get task request received", id);
        taskValidationService.onGet(id);
        TaskDto task = taskService.get(id);
        log.info("Task with ID: {} retrieved successfully", id);
        return ResponseEntity.ok(task);
    }

    /**
     * Retrieves all tasks with pagination.
     *
     * @param pageable pagination parameters
     * @return ResponseEntity containing a page of tasks
     */
    @GetMapping
    @Operation(summary = "Get all tasks", description = "Retrieves a paginated list of all tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tasks retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid pagination parameters")
    })
    public ResponseEntity<Page<TaskDto>> getAllTasks(
            @Parameter(description = "Pagination parameters")
            @PageableDefault(size = 20, sort = "createdDateTime") Pageable pageable) {
        log.info("GET /api/v1/tasks - Get all tasks request received with pagination: {}", pageable);
        taskValidationService.onGetAll(pageable);
        Page<TaskDto> tasks = taskService.getAll(pageable);
        log.info("Retrieved {} tasks (page {} of {})", tasks.getNumberOfElements(),
                tasks.getNumber() + 1, tasks.getTotalPages());
        return ResponseEntity.ok(tasks);
    }

    /**
     * Deletes a task by ID.
     *
     * @param id the task ID
     * @return ResponseEntity with no content
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task", description = "Deletes a task by its unique identifier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Task not found"),
            @ApiResponse(responseCode = "422", description = "Validation error")
    })
    public ResponseEntity<Void> deleteTask(
            @Parameter(description = "Task ID", required = true) @PathVariable UUID id) {
        log.info("DELETE /api/v1/tasks/{} - Delete task request received", id);
        taskValidationService.onDelete(id);
        taskService.delete(id);
        log.info("Task with ID: {} deleted successfully", id);
        return ResponseEntity.noContent().build();
    }
}
