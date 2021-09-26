package org.example.servlets.services;

import org.example.servlets.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class UserStorageService {

    private final Map<UUID, User> userMap = new ConcurrentHashMap<>();

    public UserStorageService() {
        this.init();
    }

    public List<User> getUsers() {
        return new ArrayList<>(userMap.values());
    }

    public void addUser(User user) {
        if (!userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        }
    }

    public void updateUser(User user) {
        if (userMap.containsKey(user.getId())) {
            userMap.put(user.getId(), user);
        }
    }

    public void deleteUser(UUID userId) {
        userMap.remove(userId);
    }

    private void init() {
        for (int i = 0; i < 15; i++) {
            UUID id = UUID.randomUUID();
            userMap.put(id, new User(id, "name" + i, "surname" + i, 20 + i, "test" + i + "@test.ru"));
        }
    }
}
