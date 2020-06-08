package com.example.demo.Controller;

import com.example.demo.DTO.ErrorDTO;
import com.example.demo.DTO.SuccessDTO;
import com.example.demo.Domain.User;
import com.example.demo.Services.UserService;
import org.springframework.web.bind.annotation.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping(path = "/api/users")
public class BaseController {

    @Autowired
    UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping(path = "/registration")
    public @ResponseBody
    ResponseEntity<Object> addNewUser(@RequestParam String name, @RequestParam String lastname,
                                      @RequestParam String email, @RequestParam String password) {

        User u = new User();
        u.setName(name);
        u.setLastname(lastname);
        u.setEmail(email);
        u.setPassword(passwordEncoder.encode(password));

        ErrorDTO error = new ErrorDTO();
        SuccessDTO success = new SuccessDTO();

        if (userService.create(u)) {

            success.setCode(201);
            success.setMessage("El Usuario se creado correctamente");
            success.setData(u);

            return new ResponseEntity<>(success, HttpStatus.CREATED);
        }

        error.setCode(400);
        error.setMessage("Hubo un error con el request enviado");
        error.setData(null);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/login")
    public @ResponseBody
    ResponseEntity<Object> login(@RequestBody String userEmail, String userPassword) {

        ErrorDTO error = new ErrorDTO();
        SuccessDTO success = new SuccessDTO();

                String email = userEmail;
                String password = userPassword;

                User u = userService.findByEmail(email);
                User u2 = userService.findByPassword(password);

                if (u == null || u2 == null) {
                    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
                }
                if (u == u2) {

                    success.setCode(200);
                    success.setMessage("El Usuario se logeo correctamente");
                    success.setData(u);

                    return new ResponseEntity<>(success, HttpStatus.OK);
                }

        error.setCode(404);
        error.setMessage("El usuario no se encuentra");
        error.setData(null);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
