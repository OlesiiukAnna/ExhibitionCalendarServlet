package ua.external.service.validators;

public enum PhoneValidator {

    PHONE_VALIDATOR;

    public boolean isValid(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        return phoneNumber.matches("^[+]+?\\d{3,}$");
    }
}
