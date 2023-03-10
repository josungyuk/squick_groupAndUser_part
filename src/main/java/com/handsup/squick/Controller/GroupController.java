package com.handsup.squick.Controller;

import com.handsup.squick.Dao.GroupDao;
import com.handsup.squick.Dto.GroupDto.GroupCreateDto;
import com.handsup.squick.Dto.GroupDto.GroupDeleteDto;
import com.handsup.squick.Dto.GroupDto.GroupModifyDto;
import com.handsup.squick.Dto.GroupDto.GroupReadDto;
import com.handsup.squick.Service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/groups")
public class GroupController {
    private final GroupService groupService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody GroupCreateDto dto){
        groupService.groupCreate(dto);

        return ResponseEntity.ok("Create Success");
    }

    @PostMapping("/delete")
    public ResponseEntity delete(@RequestBody GroupDeleteDto dto){
        if(groupService.groupDelete(dto))
            return ResponseEntity.ok("Delete Success");
        return ResponseEntity.ok("Different HostId");
    }

    @PostMapping("/modify")
    public ResponseEntity modify(@RequestBody GroupModifyDto dto){
        groupService.groupModify(dto);

        return ResponseEntity.ok("Modify Success");
    }

    @GetMapping("/read")
    public GroupDao read(@RequestBody GroupReadDto dto){

        return groupService.groupRead(dto.getGroupName());
    }
}
