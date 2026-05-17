package com.project.artconnect.ui;

import com.project.artconnect.model.Artwork;
import com.project.artconnect.service.ArtworkService;
import com.project.artconnect.util.ServiceProvider;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class ArtworkController {
    @FXML
    private TableView<Artwork> artworkTable;
    @FXML
    private TableColumn<Artwork, String> titleColumn;
    @FXML
    private TableColumn<Artwork, String> artistColumn;
    @FXML
    private TableColumn<Artwork, String> typeColumn;
    @FXML
    private TableColumn<Artwork, Double> priceColumn;
    @FXML
    private TableColumn<Artwork, String> statusColumn;

    private final ArtworkService artworkService = ServiceProvider.getArtworkService();

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // 1. Liaison sécurisée pour extraire le NOM de l'artiste au lieu de l'objet complet
        artistColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue().getArtist() != null ? cellData.getValue().getArtist().getName() : "Unknown"
        ));

        // 2. Formatage personnalisé du statut pour transformer FOR_SALE en texte propre
        statusColumn.setCellValueFactory(cellData -> {
            Artwork.Status status = cellData.getValue().getStatus();
            if (status == null) {
                return new SimpleStringProperty("Non défini");
            }
            switch (status) {
                case FOR_SALE:
                    return new SimpleStringProperty("Available");
                case SOLD:
                    return new SimpleStringProperty("Sold");
                case EXHIBITED:
                    return new SimpleStringProperty("Exhibited");
                default:
                    return new SimpleStringProperty(status.toString());
            }
        });

        // 3. Premier chargement immédiat au démarrage
        refreshData();

        // 4. Configuration de l'horloge pour un rafraîchissement automatique toutes les 3 secondes
        Timeline autoRefreshTimeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> refreshData())
        );
        autoRefreshTimeline.setCycleCount(Animation.INDEFINITE);
        autoRefreshTimeline.play();
    }

    private void refreshData() {
        if (artworkService != null) {
            artworkTable.setItems(FXCollections.observableArrayList(artworkService.getAllArtworks()));
        }
    }
}