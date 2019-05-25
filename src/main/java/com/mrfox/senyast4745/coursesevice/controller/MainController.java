package com.mrfox.senyast4745.coursesevice.controller;

import com.google.gson.Gson;
import com.mrfox.senyast4745.coursesevice.dao.CourseDAO;
import com.mrfox.senyast4745.coursesevice.forms.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final CourseDAO courseDAO;

    private Gson gson = new Gson();

    @Autowired
    public MainController(CourseDAO courseDAO) {
        this.courseDAO = courseDAO;
    }


    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity create(@RequestBody CreateForm jsonForm) {
        try {
            return ResponseEntity.ok(courseDAO.createCourse(jsonForm.getCreatorId(), jsonForm.getCourseName()
                    , jsonForm.getCourseDescription(), jsonForm.getAdminIds(), jsonForm.getUserIds()
                    , jsonForm.getTags(), true));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(jsonForm), "/create")));

        }
    }

    @RequestMapping(value = "/read", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readAll() {
        try {
            return ResponseEntity.ok(new ResponseJsonForm(courseDAO.findAll()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "DataBase is empty.", "/read")));

        }
    }

    @RequestMapping(value = "/read/user", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByFullName(@RequestBody FullNameForm form) {
        try {
            return ResponseEntity.ok(new ResponseJsonForm(courseDAO.findAllByCreatorFullName(form.getFullName())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/user")));

        }
    }

    @RequestMapping(value = "/read/rating", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByRating(@RequestBody RatingForm form) {
        try {
            return ResponseEntity.ok(new ResponseJsonForm(courseDAO.findAllByRating(form.getRating())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/rating")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity subscribeUser(@RequestBody SubscribeForm form) {
        try {
            return ResponseEntity.ok(courseDAO.subscibeUser(form.getId(), form.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/subscribe")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/unsubscribe", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity unsubscribeUser(@RequestBody SubscribeForm form) {
        try {
            return ResponseEntity.ok(courseDAO.unsubscibeUser(form.getId(), form.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/unsubscribe")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity updateAll(@RequestBody UpdateAllForm form) {
        try {
            return ResponseEntity.ok(courseDAO.updateCourse(form.getId(), form.getName(), form.getDescription(),
                    form.getAdminsId(), form.getUsersId(), form.getTags(), form.getOpen()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/update")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity deleteById(@RequestBody MinimalForm form) {
        try {
            courseDAO.delete(form.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/delete")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/change", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity changeState(@RequestBody ChangeStateForm form) {
        try {
            return ResponseEntity.ok(courseDAO.changeState(form.getId(), form.getOpen()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/change")));

        }
    }


}
