package ai.agilery.todo_service.enumeration;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Validator implementation for the {@link ValueOfEnum} custom constraint.
 * <p>
 * This validator checks whether a given string value matches any of the
 * constant names defined in the specified enumeration. During initialization,
 * it extracts and stores all enum constant names of the target enum so
 * validation can be performed efficiently.
 * </p>
 */
public class EnumValidator implements ConstraintValidator<ValueOfEnum, String> {

    /**
     * Set of accepted enum constant names derived from the target enumeration
     * provided via the {@link ValueOfEnum} annotation.
     */
    private Set<String> acceptedValues;

    /**
     * Initializes the validator by extracting all enum constant names from the
     * enumeration specified in the {@link ValueOfEnum} annotation.
     *
     * @param annotation the annotation instance containing the target enum class
     */
    @Override
    public void initialize(ValueOfEnum annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants()).map(Enum::name)
                .collect(Collectors.toSet());
    }

    /**
     * Validates whether the given value is a valid enum constant name of the target
     * enumeration.
     *
     * @param value   the string value to validate
     * @param context the context in which the constraint is evaluated
     * @return {@code true} if the value is {@code null} or matches an enum constant
     *         name; {@code false} otherwise
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        boolean valid = acceptedValues.stream().anyMatch(acceptedValue -> acceptedValue.equals(value));

        if (!valid) {
            // Build custom error message with accepted values
            context.disableDefaultConstraintViolation();
            String message = String.format("Invalid value '%s'. Accepted values are: %s",
                    value, String.join(", ", acceptedValues));
            context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        }

        return valid;
    }
}
