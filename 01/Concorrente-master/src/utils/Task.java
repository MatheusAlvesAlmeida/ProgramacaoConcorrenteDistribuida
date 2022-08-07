package utils;

import java.io.File;
import java.nio.file.Files;

public class Task implements Runnable {

	private String[] fromPaths;
	private String targetFolder = "assets/f2/";

	public Task(String[] fromPaths) {
		this.fromPaths = fromPaths;
	}

	@Override
	public void run() {
		// System.out.println("Thread começando...");
		// Executa a cópia de todos os arquivos da pasta de origem para a pasta de
		// destino
		for (String path : fromPaths) {
			File src = new File(path);
			File dest = new File(targetFolder + src.getName());
			try {
				Files.copy(src.toPath(), dest.toPath());
			} catch (Exception e) {
				System.out.println("Erro: " + e);
			}
		}
		// System.out.println("Thread terminando...");
	}

}
