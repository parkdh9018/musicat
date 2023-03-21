package com.musicat.controller.user;


import com.musicat.data.dto.user.UserDetailDto;
import com.musicat.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserService userService;

    @GetMapping("/{userSeq}")
    public ResponseEntity<?> getUserDetail(@PathVariable("userSeq") long userSeq)  {

        UserDetailDto userDetailDto = userService.getUserDetail(userSeq);
        return ResponseEntity.ok(userDetailDto);

    }




}
