package ua.training.controller.commands.manager;

import ua.training.controller.commands.Command;
import ua.training.controller.validation.InputValidation;
import ua.training.model.entity.Request;
import ua.training.model.entity.User;
import ua.training.model.service.manager.ManagerService;
import ua.training.model.service.manager.ManagerServiceImpl;
import ua.training.model.types.RequestStatus;
import ua.training.model.utils.AttributesBinder;
import ua.training.model.utils.URIBinder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

import static ua.training.model.utils.AttributesBinder.getProperty;

public class DeclineRequestCommand implements Command {
    private ManagerService managerService;
    private InputValidation inputValidation;

    public DeclineRequestCommand() {
        this.managerService = new ManagerServiceImpl();
        this.inputValidation = new InputValidation();
    }

    @Override
    public String execute(HttpServletRequest httpRequest, HttpServletResponse httpResponse) {
        String managerComment = httpRequest.getParameter(getProperty("parameter.manager.decline.commentary"));

        List<String> wrongInputMessages = new ArrayList<>();
        if (!inputValidation.isCommentaryValid(managerComment, wrongInputMessages)) {
            log.info("Specified description is not valid");
            httpRequest.setAttribute(getProperty("attribute.error.message"),
                    wrongInputMessages);
            return URIBinder.getProperty("path.manager.active.request");
        }

        Request request = new Request();
        request.setManagerComment(managerComment);
        setManagerParameters(httpRequest, request);
        managerService.declineRequest(request);
        return URIBinder.getProperty("redirect") + URIBinder.getProperty("path.manager.active.request");
    }

    private void setManagerParameters(HttpServletRequest httpRequest, Request request) {
        HttpSession session = httpRequest.getSession();
        request.setId(Integer.valueOf(httpRequest.getParameter(getProperty(("parameter.id")))));
        User currentManager = (User) session.getAttribute(AttributesBinder.getProperty("parameter.user"));
        request.setManager_id(currentManager.getId());
        request.setStatus(RequestStatus.DECLINED);
    }
}
