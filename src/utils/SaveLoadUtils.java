package utils;

import save.*;

import java.io.*;
import java.nio.file.*;

public class SaveLoadUtils {
	
	private static final Path SAVE_FILE = Paths.get("saved_game.dat");
	private static final Path SINGLE_PLAYER_SAVE_FILE = Paths.get("single_player_saved_game.dat");
	private static final Path TWO_PLAYER_SAVE_FILE = Paths.get("two_player_saved_game.dat");
	private static final Path SETTINGS_FILE = Paths.get("user_settings.dat");

	public static void saveGame(GameState gameState) throws IOException {
		saveGame(gameState, false);
	}

	public static void saveGame(GameState gameState, boolean singlePlayer) throws IOException {
		try(ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(getSaveFile(singlePlayer).toFile()))) {
			outputStream.writeObject(gameState);
		}
	}

	public static GameState loadGame() throws IOException, ClassNotFoundException {
		return loadGame(false);
	}

	public static GameState loadGame(boolean singlePlayer) throws IOException, ClassNotFoundException {
		try(ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(getReadableSaveFile(singlePlayer).toFile()))) {
			return (GameState) inputStream.readObject();
		}
	}

	public static boolean saveExists() {
		return saveExists(false);
	}

	public static boolean saveExists(boolean singlePlayer) {
		if(singlePlayer) {
			return Files.exists(SINGLE_PLAYER_SAVE_FILE);
		}

		return Files.exists(TWO_PLAYER_SAVE_FILE) || Files.exists(SAVE_FILE);
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

	private static Path getSaveFile(boolean singlePlayer) {
		return singlePlayer ? SINGLE_PLAYER_SAVE_FILE : TWO_PLAYER_SAVE_FILE;
	}

	private static Path getReadableSaveFile(boolean singlePlayer) {
		if(singlePlayer || Files.exists(TWO_PLAYER_SAVE_FILE)) {
			return getSaveFile(singlePlayer);
		}

		return SAVE_FILE;
	}
}
