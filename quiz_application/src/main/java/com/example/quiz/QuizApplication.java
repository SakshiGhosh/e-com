package com.example.quiz;

import java.util.Base64;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuizApplication {

	public static void main(String[] args) {

 	//	String secret = Base64.getEncoder()
      //   .encodeToString("yourSecretKeyMakeItLongEnough123".getBytes());
      //  System.out.println("JWT SECRET============================================: " + secret);

		SpringApplication.run(QuizApplication.class, args);
	}

}
