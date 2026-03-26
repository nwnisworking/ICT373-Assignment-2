package ict373.assignment2.controllers;

import ict373.assignment2.App;
import ict373.assignment2.events.WindowEvent;
import ict373.assignment2.services.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * <strong>LoaderController</strong>
 * 
 * <p>The LoaderController is responsible for loading the necessary services when the application starts.</p>
 *
 * @author nwnisworking
 * @date 4/3/2026
 * @filename LoaderController.java
 */
public class LoaderController implements Initializable{
	/**
	 * The name of the file used to store serialized data for customers, publications, and subscriptions.
	 */
	public static final String DATA = "data.ser";

	/** 
	 * The progress bar to indicate loading progress
	 */
	private ProgressBar progress_bar = new ProgressBar();

	/** The content area to display the progress bar */
	@FXML
	private StackPane content;

	/**
	 * Initialize the controller by setting up the progress bar and starting the loading process.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb){
		progress_bar.setId("progress-bar");
		progress_bar.setPrefWidth(300);

		content.getChildren().add(progress_bar);

		loadServices();

		content.getParent().addEventHandler(WindowEvent.CLOSE, this::saveData);
	}

	/**
	 * Load the necessary services for the application, updating the progress bar as each service is loaded.
	 */
	private void loadServices(){
		Task<Void> task = new Task<Void>(){
			@Override
			protected Void call(){
				try{
					File file = new File(DATA);

					if(!file.exists()){
						CustomerService.getInstance();
						updateProgress(1, 3);

						// Thread.sleep(1000);
						PublicationService.getInstance();

						updateProgress(2, 3);
						// Thread.sleep(1000);

						SubscriptionService.getInstance();
						updateProgress(3, 3);

						file.createNewFile();
            System.out.println("[Loader]: File does not exist. Creating " + DATA);
					}
					else{
						ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));

						// Thread.sleep(800);
						CustomerService.setInstance(App.read(input, CustomerService.class));
						updateProgress(1, 3);
						// Thread.sleep(800);

            
						PublicationService.setInstance(App.read(input, PublicationService.class));
						updateProgress(2, 3);
						// Thread.sleep(800);

						SubscriptionService.setInstance(App.read(input, SubscriptionService.class));
						updateProgress(3, 3);

						input.close();

						System.out.println("[Loader]: File exist. Loading data from " + DATA);
					}

				}
				catch(IOException ex){
					updateProgress(1, 1);
          System.out.println("[Loader]: File exist but is corrupted or empty.");
				}

        Platform.runLater(() -> loadContent());
        
				return null;
			}
		};
    
		progress_bar.progressProperty().bind(task.progressProperty());

		new Thread(task).start();
	}

	/**
	 * Save the current state of the services to a file when the application is closed.
	 * @param event The window event that triggered the save action
	 */
	private void saveData(WindowEvent event){
    try{
      File file = new File(DATA);
      ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
      
			App.write(output, CustomerService.getInstance());
      App.write(output, PublicationService.getInstance());
      App.write(output, SubscriptionService.getInstance());
      
      output.flush();
      output.close();
      System.out.println("[Loader]: Write to file.");
    }
    catch(IOException ex){
      System.out.println("[Loader]: Unable to write to file.");
    }
	}

	/**
	 * Load the main content of the application after the services have been loaded.
	 */
	private void loadContent(){
		FadeTransition fade_transition = new FadeTransition(Duration.millis(800), progress_bar);

		fade_transition.setFromValue(1);
		fade_transition.setToValue(0);
		fade_transition.play();

		fade_transition.setOnFinished(e -> {
			content.getChildren().remove(progress_bar);
			content.getChildren().add(App.loadFXML("Home", null));
		});
	}
}
