package com.rhisco.book.Services;

import java.util.Optional;

import com.rhisco.book.Models.User;
import com.rhisco.book.Repositorys.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(User user) {
        try {
            // Primer filtro para ver si el nombre ya existe
            Optional<User> users = userRepository.findAll().stream().filter(u -> u.getName().equals(user.getName()))
                    .findFirst();

            // Segundo filtro en caso de que el nombre ya exista pero no sea del mismo apellido
            users = users.filter(u -> u.getLast_name().equals(user.getLast_name()));

            // Si el nombre ya existe y el apellido es el mismo, no se agrega
            if (users.isPresent()) {
                return true;
            }
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        // Si no existe, se agrega
        userRepository.save(user);
        return false;
    }

    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    public User findUser(String name, String last_name) {
        // Primer filtro para ver si el nombre existe
        Optional<User> users = userRepository.findAll().stream().filter(u -> u.getName().equals(name))
                .findFirst();

        // Segundo filtro en caso de que el nombre ya exista pero no sea del mismo apellido
        users = users.filter(u -> u.getLast_name().equals(last_name));

        // Si el nombre ya existe y el apellido es el mismo, regresa el usuario
        if (users.isPresent()) {
            return users.get();
        }
        return null;
    }
    
    public long getLastId(){
        return userRepository.getLastId();
    }

}
