package ai.agilery.todo_service.repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ai.agilery.todo_service.entity.Task;
import ai.agilery.todo_service.entity.TaskPriority;
import ai.agilery.todo_service.entity.TaskStatus;

/**
 * Repository interface for Task entity.
 * Provides CRUD operations and custom query methods for managing tasks.
 * All query methods use pagination to prevent performance issues with large datasets.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, UUID> {

    /**
     * Finds all tasks with the specified status.
     *
     * @param status the task status to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching the status
     */
    Page<Task> findByStatus(TaskStatus status, Pageable pageable);

    /**
     * Finds all tasks with the specified priority.
     *
     * @param priority the task priority to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching the priority
     */
    Page<Task> findByPriority(TaskPriority priority, Pageable pageable);

    /**
     * Finds all tasks with the specified completion status.
     *
     * @param completed the completion status to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching the completion status
     */
    Page<Task> findByCompleted(Boolean completed, Pageable pageable);

    /**
     * Finds all tasks created by a specific user.
     *
     * @param createdBy the UUID of the user who created the tasks
     * @param pageable pagination parameters
     * @return page of tasks created by the specified user
     */
    Page<Task> findByCreatedBy(UUID createdBy, Pageable pageable);

    /**
     * Finds all tasks with due date between the specified date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @param pageable pagination parameters
     * @return page of tasks with due dates within the specified range
     */
    Page<Task> findByDueDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    /**
     * Finds all tasks with due date before the specified date.
     *
     * @param dueDate the date to compare against
     * @param pageable pagination parameters
     * @return page of tasks with due dates before the specified date
     */
    Page<Task> findByDueDateBefore(LocalDateTime dueDate, Pageable pageable);

    /**
     * Finds all tasks with due date after the specified date.
     *
     * @param dueDate the date to compare against
     * @param pageable pagination parameters
     * @return page of tasks with due dates after the specified date
     */
    Page<Task> findByDueDateAfter(LocalDateTime dueDate, Pageable pageable);

    /**
     * Finds all tasks by status and priority.
     *
     * @param status the task status to filter by
     * @param priority the task priority to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching both status and priority
     */
    Page<Task> findByStatusAndPriority(TaskStatus status, TaskPriority priority, Pageable pageable);

    /**
     * Finds all tasks by status and completion status.
     *
     * @param status the task status to filter by
     * @param completed the completion status to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching both status and completion status
     */
    Page<Task> findByStatusAndCompleted(TaskStatus status, Boolean completed, Pageable pageable);

    /**
     * Finds a task by ID and created by user.
     *
     * @param id the task ID
     * @param createdBy the UUID of the user who created the task
     * @return optional containing the task if found
     */
    Optional<Task> findByIdAndCreatedBy(UUID id, UUID createdBy);

    /**
     * Finds all tasks created by a user with a specific status.
     *
     * @param createdBy the UUID of the user who created the tasks
     * @param status the task status to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching both created by and status
     */
    Page<Task> findByCreatedByAndStatus(UUID createdBy, TaskStatus status, Pageable pageable);

    /**
     * Finds all tasks created by a user with a specific completion status.
     *
     * @param createdBy the UUID of the user who created the tasks
     * @param completed the completion status to filter by
     * @param pageable pagination parameters
     * @return page of tasks matching both created by and completion status
     */
    Page<Task> findByCreatedByAndCompleted(UUID createdBy, Boolean completed, Pageable pageable);

    /**
     * Finds all tasks with title containing the specified text (case-insensitive).
     *
     * @param title the text to search for in task titles
     * @param pageable pagination parameters
     * @return page of tasks with titles containing the specified text
     */
    Page<Task> findByTitleContainingIgnoreCase(String title, Pageable pageable);

    /**
     * Finds all tasks created within a specific date range.
     *
     * @param startDate the start date of the range
     * @param endDate the end date of the range
     * @param pageable pagination parameters
     * @return page of tasks created within the specified date range
     */
    Page<Task> findByCreatedDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);
}
