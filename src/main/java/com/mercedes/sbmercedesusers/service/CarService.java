package com.mercedes.sbmercedesusers.service;


import com.mercedes.sbmercedesusers.dto.Car;
import com.mercedes.sbmercedesusers.entity.UserInfo;
import com.mercedes.sbmercedesusers.repository.UserInfoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class CarService {

    List<Car> carList = null;

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void loadCarsFromDB() {
        carList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Car.builder()
                        .carId(i)
                        .name("car " + i)
                        .build()
                ).collect(Collectors.toList());
    }


    public List<Car> getCars() {
        return carList;
    }

    public Car getCar(int id) {
        return carList.stream()
                .filter(car -> car.getCarId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("car " + id + " not found"));
    }


    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
        return "user added to system ";
    }
}
