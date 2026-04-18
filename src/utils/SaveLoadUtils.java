package utils;

import save.*;

import java.io.*;
import java.nio.file.*;

public class SaveLoadUtils {
	
	private static final Path SAVE_FILE = Paths.get("saved_game.dat");
	private static final Path SETTINGS_FILE = Paths.get("user_settings.dat");

	public static void saveGame(GameState gameState) throws IOException {
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SAVE_FILE.toFile()))) {
			outputStream.writeObject(gameState);
		}
	}

	public static GameState loadGame() throws IOException, ClassNotFoundException {
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SAVE_FILE.toFile()))) {
			return (GameState) inputStream.readObject();
		}
	}

	public static boolean saveExists() {
		return Files.exists(SAVE_FILE);
	}

	public static void saveSettings(UserSettings settings) throws IOException {
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(SETTINGS_FILE.toFile()))) {
			outputStream.writeObject(settings);
		}
	}

	public static UserSettings loadSettings() throws IOException, ClassNotFoundException {
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(SETTINGS_FILE.toFile()))) {
			return (UserSettings) inputStream.readObject();
		}
	}

	public static boolean settingsExist() {
		return Files.exists(SETTINGS_FILE);
	}
}

