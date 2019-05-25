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
            return ResponseEntity.ok(courseDAO.findAll());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "DataBase is empty.", "/read")));

        }
    }

    @RequestMapping(value = "/read/usr", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByFullName(@RequestParam FullNameForm form) {
        try {
            return ResponseEntity.ok(courseDAO.findAllByCreatorFullName(form.getFullName()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/usr")));

        }
    }

    @RequestMapping(value = "/read/rtg", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity readByRating(@RequestParam RatingForm form) {
        try {
            return ResponseEntity.ok(courseDAO.findAllByRating(form.getRating()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/read/rtg")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/sb", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity subscribeUser(@RequestParam SubscribeForm form) {
        try {
            return ResponseEntity.ok(courseDAO.subscibeUser(form.getId(), form.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/sb")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/usb", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity unsubscribeUser(@RequestParam SubscribeForm form) {
        try {
            return ResponseEntity.ok(courseDAO.unsubscibeUser(form.getId(), form.getUserId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/usb")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/upd", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity updateAll(@RequestParam UpdateAllForm form) {
        try {
            return ResponseEntity.ok(courseDAO.updateCourse(form.getId(), form.getName(), form.getDescription(),
                    form.getAdminsId(), form.getUsersId(), form.getTags(), form.getOpen()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/upd")));

        }
    }

    @PreAuthorize("@securityService.hasPermission('ADMIN,TEACHER,STUDENT')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity deleteById(@RequestParam MinimalForm form) {
        try {
            courseDAO.delete(form.getId());
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(gson.toJson(new ExceptionModel(400, "Bad Request",
                    "Bad Request with: " + gson.toJson(form), "/delete")));

        }
    }


}
