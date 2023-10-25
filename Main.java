package com;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void start(Stage primaryStage) throws Exception {
		TableView<Activity> activities;
		TableColumn<Activity, String> nomColumn;
		TableColumn<Activity, String> descriptionColumn;
		
		nomColumn = new TableColumn<>("Nom des tâches");
		nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
		nomColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		nomColumn.setOnEditCommit(new EventHandler<CellEditEvent<Activity,String>>(){

			@Override
			public void handle(CellEditEvent<Activity, String> arg0) {
				Activity ac = arg0.getRowValue();
				ac.setNom(arg0.getNewValue());				
			}
			
		});
		
		descriptionColumn = new TableColumn<>("Description des tâches");
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionColumn.setOnEditCommit(new EventHandler<CellEditEvent<Activity,String>>(){

			@Override
			public void handle(CellEditEvent<Activity, String> arg0) {
				Activity ac = arg0.getRowValue();
				ac.setDescription(arg0.toString());				
			}
			
		});
		
		activities = new TableView<>();
		activities.getColumns().addAll(nomColumn,descriptionColumn);
		activities.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		activities.getItems().addAll(new Activity("Cours","Je suis un étudiant"),new Activity("Jeu","J'aime parfois jouer"),new Activity("Un nom","Une description"));
		activities.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		activities.setEditable(true);
		
		Button delete = new Button("Suppimer");
		Button deleteAll = new Button("Tout suppimer");
		deleteAll.setOnAction(e->{
			try {
				(activities.getItems()).forEach(activities.getItems()::remove);
			}catch (Exception ex) {
			}
		});
		
		delete.setOnAction(e->{
			try {
				ObservableList<Activity> liste = activities.getSelectionModel().getSelectedItems();
				liste.forEach((activities.getItems())::remove);
			}catch (Exception ex) {
				
			}
		});
		
		delete.disableProperty().bind(activities.getSelectionModel().selectedItemProperty().isNull());
		
		HBox box = new HBox();
		HBox box1 = new HBox();
		
		TextField nom = new TextField("Nom");
		nom.setTooltip(new Tooltip("Nom de la nouvelle activité"));
		TextField description = new TextField("Description");
		description.setTooltip(new Tooltip("Description de la nouvelle activité"));
		
		Button ajouter = new Button("Ajouter");
		ajouter.setOnAction(e->{
			activities.getItems().add(new Activity(nom.getText(), description.getText()));
			nom.clear();
			description.clear();
		});
		
		box.getChildren().addAll(nom,description,ajouter);
		box.setSpacing(20);
		
		box1.getChildren().addAll(delete,deleteAll);
		box1.setSpacing(20);
		
		VBox root = new VBox(10);
		root.setPadding(new Insets(40));
		
		root.getChildren().addAll(activities,box,box1);
		
		Scene scene = new Scene(root);
		
		primaryStage.setScene(scene);
		primaryStage.show();          
		
	}

}
