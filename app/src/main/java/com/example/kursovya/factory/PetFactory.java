package com.example.kursovya.factory;

import com.example.kursovya.model.Pet;

import java.util.UUID;

public class PetFactory {

    public static Pet createPet(String type, String name, String age) {

        if (type.equalsIgnoreCase("Кот")) {
            return new Pet(
                    UUID.randomUUID().toString(),
                    name,
                    "Кот",
                    "Домашний",
                    age,
                    "Любит спать",
                    null
            );
        }

        if (type.equalsIgnoreCase("Собака")) {
            return new Pet(
                    UUID.randomUUID().toString(),
                    name,
                    "Собака",
                    "Дворняга",
                    age,
                    "Очень дружелюбный",
                    null
            );
        }

        return new Pet(
                UUID.randomUUID().toString(),
                name,
                type,
                "Неизвестно",
                age,
                "Описание отсутствует",
                null
        );
    }
}
