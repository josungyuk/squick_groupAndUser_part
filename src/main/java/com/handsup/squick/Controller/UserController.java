package com.handsup.squick.Controller;

import com.handsup.squick.Dto.UserDto.UserAddDto;
import com.handsup.squick.Dto.UserDto.UserDeleteDto;
import com.handsup.squick.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody UserAddDto userAddDto){
        userService.userAdd(userAddDto);

        return ResponseEntity.ok("Success");
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody UserDeleteDto userDeleteDto){
        if(!userService.userDelete(userDeleteDto))
            return ResponseEntity.ok("Fail");

        return ResponseEntity.ok("Succece");
    }

    @PostMapping("/expulsion") //추방
    public ResponseEntity expulsion(@RequestBody UserDeleteDto userDeleteDto){
        if(!userService.userDelete(userDeleteDto))
            return ResponseEntity.ok("Fail");

        return ResponseEntity.ok("Succece");
    }
}
