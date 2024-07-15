package com.game.rockpaperscissor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The main class for the Rock Paper Scissor application.
 */
@SpringBootApplication
public class RockPaperScissorApplication {

	private static final Logger logger = LoggerFactory.getLogger(RockPaperScissorApplication.class);

	/**
	 * The entry point of the application.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		try {
			logger.info("Starting Rock Paper Scissor application");
			SpringApplication.run(RockPaperScissorApplication.class, args);
			logger.info("Rock Paper Scissor application started successfully");
		} catch (Exception e) {
			logger.error("Application failed to start", e);
		}
	}
}
