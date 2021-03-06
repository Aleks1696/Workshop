package ua.training.controller.validation;

import ua.training.model.entity.*;
import static ua.training.model.utils.RegexBinder.*;
import java.util.List;

public class InputValidation {
    private List<String> wrongInputMessages;

    public boolean isLoginAndPasswordValid(String login, String password, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        boolean isLoginValid = isParameterValid(login, getProperty("regex.login"),
                                                "input.wrong.login.format");
        boolean isPasswordValid = isParameterValid(password, getProperty("regex.password"),
                                                   "input.wrong.password.format");
        return isLoginValid && isPasswordValid;
    }

    public boolean isUserValid(User user, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;

        boolean isLoginValid = isParameterValid(user.getLogin(), getProperty("regex.login"),
                "input.wrong.login.format");
        boolean isPasswordValid = isParameterValid(user.getPassword(), getProperty("regex.password"),
                "input.wrong.password.format");
        boolean isNameValid = isParameterValid(user.getName(), getProperty("regex.name"),
                "input.wrong.name.format");
        boolean isName_uaValid = isParameterValid(user.getName_ua(), getProperty("regex.name_ua"),
               "input.wrong.name_ua.format");
        boolean isSurnameValid = isParameterValid(user.getSurname(), getProperty("regex.surname"),
                "input.wrong.surname.format");
        boolean isSurname_uaValid = isParameterValid(user.getSurname_ua(), getProperty("regex.surname_ua"),
                "input.wrong.surname_ua.format");
        boolean isEmailValid = isParameterValid(user.getEmail(), getProperty("regex.email"),
                "input.wrong.email.format");
        boolean isPhoneNumberValid = isParameterValid(user.getPhoneNumber(), getProperty("regex.phoneNumber"),
                "input.wrong.phoneNumber.format");

        return isLoginValid && isPasswordValid && isNameValid && isName_uaValid && isSurnameValid &&
                isSurname_uaValid && isEmailValid && isPhoneNumberValid;
    }

    private boolean isParameterValid(String parameter, String regex, String errorMessage) {
        if (parameter == null || !(parameter.matches(regex))) {
            wrongInputMessages.add(errorMessage);
            return false;
        } else {
            return true;
        }
    }

    public boolean isCustomerRequestValid(Request request, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        boolean isDeviceCategoryValid = isParameterValid(request.getProductCategory().toString(),
                getProperty("regex.device.category"), "input.wrong.device.category");
        boolean isDeviceValid = isParameterValid(request.getDevice(), getProperty("regex.device"),
                "input.wrong.device.model");
        boolean isDeviceDescriptionValid = isParameterValid(request.getDescription(),
                getProperty("regex.description"), "input.wrong.request.description.format");
        return isDeviceCategoryValid && isDeviceValid && isDeviceDescriptionValid;
    }

    public boolean isPriceAndDescriptionValid(String price, String managerComment, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        boolean isPriceValid = isParameterValid(price, getProperty("regex.price"),
                "input.wrong.request.price.format");
        boolean isDescriptionValid = isParameterValid(managerComment, getProperty("regex.description"),
                "input.wrong.request.description.format");
        return isPriceValid && isDescriptionValid;
    }

    public boolean isCommentaryValid(String managerComment, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        return isParameterValid(managerComment, getProperty("regex.description"),
                "input.wrong.request.description.format");
    }

    public boolean isFeedbackValid(Feedback feedback, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        boolean isRateValid = isParameterValid(feedback.getMark().toString(), getProperty("regex.mark"),
                "input.wrong.feedback.rate");
        boolean isCommentaryValid = isParameterValid(feedback.getCommentary(), getProperty("regex.commentary"),
                "input.wrong.feedback.commentary.format");
        return isRateValid && isCommentaryValid;
    }

    public boolean isIdValid(String id, List<String> wrongInputMessages) {
        this.wrongInputMessages = wrongInputMessages;
        return isParameterValid(id, getProperty("regex.id"), "input.wrong.id.format");
    }





}
