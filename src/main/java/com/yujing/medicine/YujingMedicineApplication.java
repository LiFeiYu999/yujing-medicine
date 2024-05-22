package com.yujing.medicine;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan(basePackages = "com.yujing.medicine.mapper")
public class YujingMedicineApplication {

    public static void main(String[] args) {
        SpringApplication.run(YujingMedicineApplication.class, args);
    }

}
