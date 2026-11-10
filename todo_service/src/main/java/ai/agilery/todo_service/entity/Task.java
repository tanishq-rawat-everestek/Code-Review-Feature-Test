package ai.agilery.todo_service.entity;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Entity representing a task in the todo service.
 *
 * <p>A task is a single to-do item that tracks something a user needs to accomplish.
 * It contains information about the task description, status, priority, and due date.</p>
 *
 * <p>The entity includes audit fields to track creation and modification metadata.</p>
 */
@Getter
@Setter
@Entity
@Table(name = "tasks", indexes = {
        @Index(name = "idx_task_status", columnList = "status"),
        @Index(name = "idx_task_priority", columnList = "priority"),
        @Index(name = "idx_task_due_date", columnList = "dueDate"),
        @Index(name = "idx_task_created_by", columnList = "createdBy")
})
public class Task {

    /**
     * Length constant for short text fields.
     */
    private static final int LEN_20 = 20;

    /**
     * Length constant for medium text fields.
     */
    private static final int LEN_200 = 200;

    /**
     * Length constant for long text fields.
     */
    private static final int LEN_1000 = 1000;

    /**
     * Unique identifier for the task.
     * Generated automatically using UUID strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /**
     * Title or short description of the task.
     * This field is mandatory and has a maximum length of 200 characters.
     */
    @Column(nullable = false, length = LEN_200)
    private String title;

    /**
     * Detailed description of the task.
     * This field is optional and can contain up to 1000 characters.
     */
    @Column(length = LEN_1000)
    private String description;

    /**
     * Current status of the task.
     * Possible values: PENDING, IN_PROGRESS, COMPLETED, CANCELLED.
     * This field is mandatory and defaults to PENDING.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = LEN_20)
    private TaskStatus status;

    /**
     * Priority level of the task.
     * Possible values: LOW, MEDIUM, HIGH, CRITICAL.
     * This field is optional.
     */
    @Enumerated(EnumType.STRING)
    @Column(length = LEN_20)
    private TaskPriority priority;

    /**
     * Due date and time for the task.
     * This field is optional and indicates when the task should be completed.
     */
    @Column
    private LocalDateTime dueDate;

    /**
     * Flag indicating whether the task is completed.
     * Defaults to FALSE. This field is mandatory.
     */
    @Column(nullable = false)
    private Boolean completed = Boolean.FALSE;

    /**
     * Timestamp when the task was created.
     * Automatically set by the system on creation.
     */
    @CreationTimestamp
    private LocalDateTime createdDateTime;

    /**
     * Timestamp when the task was last modified.
     * Automatically updated by the system on modification.
     */
    @UpdateTimestamp
    private LocalDateTime lastModifiedDateTime;

    /**
     * Identifier of the user who created the task.
     * Automatically set by the auditing system.
     */
    @CreatedBy
    private UUID createdBy;

    /**
     * Identifier of the user who last modified the task.
     * Automatically updated by the auditing system.
     */
    @LastModifiedBy
    private UUID modifiedBy;

    /**
     * Equals method based on ID only for JPA entity best practices.
     * Returns false for transient entities (id == null) to maintain
     * proper collection behavior before persistence.
     *
     * @param o the object to compare with
     * @return true if the objects are equal based on their ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task other = (Task) o;
        return id != null && id.equals(other.getId());
    }

    /**
     * HashCode method returns a constant value to maintain equals-hashCode contract.
     * This ensures stable behavior in collections even for transient entities.
     * Using a constant ensures hashCode remains stable throughout entity lifecycle.
     *
     * @return the hash code of the entity
     */
    @Override
    public int hashCode() {
        return 31;
    }
}
