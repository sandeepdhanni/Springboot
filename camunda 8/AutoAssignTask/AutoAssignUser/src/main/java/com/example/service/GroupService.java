package com.example.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.GroupTable;
import com.example.entity.UserTable;
import com.example.repository.GroupRepository;

@Service
public class GroupService {

    @Autowired
    private GroupRepository groupRepository;

    // Create operation
    public GroupTable createGroup(GroupTable group) {
        if(groupRepository.existsByGroupName(group.getGroupName())){
            GroupTable groupTable = groupRepository.findByGroupName(group.getGroupName()).get();
            List<UserTable> groupTableUsers = groupTable.getUsers();
            Set<String> userAlreadyPresent = groupTableUsers.stream().map(u -> u.getUserName())
                    .collect(Collectors.toSet());
            List<UserTable> newUsers = group.getUsers().stream().filter(u -> !(userAlreadyPresent.contains(u.getUserName()))).toList();
            groupTableUsers.addAll(newUsers);
            groupTable.setUsers(groupTableUsers);
            return groupRepository.save(groupTable);
        }
        return groupRepository.save(group);
    }

    // Read operation (Get all groups)
    public List<GroupTable> getAllGroups() {
        return groupRepository.findAll();
    }

    // Read operation (Get group by ID)
    public Optional<GroupTable> getGroupById(Long id) {
        return groupRepository.findById(id);
    }

    // Update operation
    public GroupTable updateGroup(Long id, GroupTable groupDetails) {
        return groupRepository.findById(id).map(group -> {
            group.setGroupName(groupDetails.getGroupName());
            List<UserTable> users= group.getUsers();
            if(users==null) {
            	List<UserTable> newUsers = new ArrayList<>();
            	newUsers.addAll(groupDetails.getUsers());
            	group.setUsers(newUsers);
            }else {
            	users.addAll(groupDetails.getUsers());
            group.setUsers(users);
            }
            return groupRepository.save(group);
        }).orElseThrow(() -> new RuntimeException("Group not found"));
    }

    // Delete operation
    public void deleteGroup(Long id) {
        groupRepository.deleteById(id);
    }
}