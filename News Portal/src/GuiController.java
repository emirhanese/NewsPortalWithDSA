import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class GuiController {

    private Business business = new Business("business");
    private Health health = new Health("health");
    private Technology technology = new Technology("technology");
    private Science science = new Science("science_and_environment");
    private Entertainment entertainment = new Entertainment("entertainment_and_arts");

    private ArrayList<Label> newsLabels = new ArrayList<>();
    private Stack<Object> history = new Stack<>();
    private BinarySearchTree bst = new BinarySearchTree();
    private ArrayList<String> viewedNews = new ArrayList<>();
    private Object category = null;

    @FXML
    private Button healthButton;

    @FXML
    private Button favoriteButton;

    @FXML
    private Button historyButton;

    @FXML
    private Label titleLabel;

    @FXML
    private Label detailedText;

    @FXML
    private Button backButton;

    @FXML
    private VBox buttonsBox;

    @FXML
    private Label imageLabel;

    @FXML
    private VBox newsBox;

    @FXML
    private Button entertainmentButton;

    @FXML
    private Button scienceButton;

    @FXML
    private AnchorPane newsPane;

    @FXML
    private AnchorPane detailedTextPane;

    @FXML
    private AnchorPane newsDetailPane;

    @FXML
    private ScrollPane newsDetailScrollPane;

    @FXML
    private ScrollPane newsScrollPane;

    @FXML
    private Button technologyButton;

    @FXML
    private Button businessButton;

    @FXML
    void entertainmentButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.entertainmentButton.setDisable(true);
        checkButtonState(this.entertainmentButton);
        LinkedList<String> imageLinks = entertainment.getImages();
        LinkedList<String> newsTitles = entertainment.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = entertainment;
        this.newsPane.setVisible(true);
    }

    @FXML
    void healthButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.healthButton.setDisable(true);
        checkButtonState(this.healthButton);
        LinkedList<String> imageLinks = health.getImages();
        LinkedList<String> newsTitles = health.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = health;
        this.newsPane.setVisible(true);
    }

    @FXML
    void scienceButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.scienceButton.setDisable(true);
        checkButtonState(this.scienceButton);
        LinkedList<String> imageLinks = science.getImages();
        LinkedList<String> newsTitles = science.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = science;
        this.newsPane.setVisible(true);
    }

    @FXML
    void technologyButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.technologyButton.setDisable(true);
        checkButtonState(this.technologyButton);
        LinkedList<String> imageLinks = technology.getImages();
        LinkedList<String> newsTitles = technology.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = technology;
        this.newsPane.setVisible(true);
    }

    @FXML
    void businessButtonClicked(MouseEvent event) {

        if (this.newsPane.isVisible()) {
            this.newsPane.setVisible(false);
            this.clearLabels();
        }

        this.businessButton.setDisable(true);
        checkButtonState(this.businessButton);
        LinkedList<String> imageLinks = business.getImages();
        LinkedList<String> newsTitles = business.getTitles();
        this.setImageAndTitle(imageLinks, newsTitles);
        this.adjustSize(imageLinks.length());
        this.category = business;
        this.newsPane.setVisible(true);
    }

    @FXML
    void backButtonClicked(MouseEvent event) {

        this.history.push(this.titleLabel.getText());
        this.history.push(this.detailedText.getText());
        this.history.push(this.imageLabel.getGraphic());

        if(!viewedAlready(this.titleLabel.getText())) {

            this.viewedNews.add(this.titleLabel.getText());
            ((News)this.category).increaseViewsCounter();
            this.bst.updateTree();
        }
        
        historyButtonController();

        this.newsDetailScrollPane.setVisible(false);
        this.newsPane.setVisible(true);
    }

    @FXML
    void favoriteButtonClicked(MouseEvent event) {

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Favorite Category");
        alert.setHeaderText("Your favorite news category is:  " + this.bst.getMostViewedCategory(this.bst.root));
        alert.setContentText("This message shows the most read news category by you.");
        alert.showAndWait();
    }

    @FXML
    void historyButtonClicked(MouseEvent event) {

        this.imageLabel.setGraphic(null);
        this.detailedText.setText("");
        this.titleLabel.setText("");

        this.imageLabel.setGraphic((Node)history.pop());
        this.detailedText.setText((String)history.pop());
        this.titleLabel.setText((String)history.pop());
        historyButtonController();
        this.detailedTextPane.prefHeightProperty().bind(this.detailedText.prefHeightProperty());
        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(true);
    }

    private void clearLabels() {

        for (int i = 0; i < this.newsLabels.size(); i++) {

            this.newsLabels.get(i).setText("");
            this.newsLabels.get(i).setGraphic(null);
        }
    }

    private void setImageAndTitle(LinkedList<String> imageLinks, LinkedList<String> newsTitles) {

        for (int i = 0; i < imageLinks.length(); i++) {

            String url = imageLinks.get(i);
            Image image = new Image(url);
            ImageView view = new ImageView(image);
            view.setFitHeight(190);
            view.setFitWidth(285);
            this.newsLabels.get(i).setGraphic(view);
            this.newsLabels.get(i).setContentDisplay(ContentDisplay.LEFT);
            this.newsLabels.get(i).setText(newsTitles.get(i));
            this.newsLabels.get(i).setGraphicTextGap(90);
        }

        sortNewsLabels(imageLinks);
    }

    // Bubble sort algorithm to sort news according to their titles.
    private void sortNewsLabels(LinkedList<String> imageLinks) {

        for(int i = 0; i < imageLinks.length() - 1; i++) {

            for(int j = 0; j < imageLinks.length() - i - 1; j++) {

                if((this.newsLabels.get(j)).getText().compareTo(((this.newsLabels.get(j + 1))).getText()) > 0) {

                    String tempText = this.newsLabels.get(j).getText();
                    ImageView tempGraphic = (ImageView) this.newsLabels.get(j).getGraphic();
                    this.newsLabels.get(j).setGraphic((ImageView)this.newsLabels.get(j + 1).getGraphic());
                    this.newsLabels.get(j).setText(this.newsLabels.get(j + 1).getText());
                    this.newsLabels.get(j + 1).setGraphic(tempGraphic);
                    this.newsLabels.get(j + 1).setText(tempText);
                }
            }
        }
    }

    private void mouseEntered(MouseEvent event) {

        Label label = (Label) event.getSource();
        label.setTextFill(Color.rgb(26, 13, 190));
        label.setCursor(Cursor.HAND);

    }

    private void mouseExited(MouseEvent event) {

        Label label = (Label) event.getSource();
        label.setTextFill(Color.BLACK);
    }

    private void adjustSize(int newsCount) {

        this.newsPane.setPrefSize(1280, (newsCount * 200.0) + ((newsCount - 1) * 15));
    }

    private boolean viewedAlready(String newsTitle) {

        return this.viewedNews.contains(newsTitle);
    }

    private void checkButtonState(Button clickedButton) {

        List<Node> buttons = this.buttonsBox.getChildren().stream().collect(Collectors.toList());

        for(Node button : buttons) {

            if((Button) button == clickedButton) 
                continue;

            ((Button) button).setDisable(false);
        }
    }

    private void labelClicked(MouseEvent event) {
        this.imageLabel.setGraphic(null);
        this.detailedText.setText("");
        this.titleLabel.setText("");

        Label label = (Label) event.getSource();
        String title = label.getText();
        int index = ((News)this.category).getTitles().indexOf(title);

        String url = ((News)this.category).getImages().get(index);
        Image image = new Image(url);
        ImageView view = new ImageView(image);
        view.setFitHeight(227);
        view.setFitWidth(354);

        String link = ((News)this.category).getLinks().get(index);
        String text = ((News)this.category).getDetailedText(link);

        this.imageLabel.setGraphic(view);
        this.detailedText.setText(text);
        this.titleLabel.setText(title);
        this.detailedTextPane.prefHeightProperty().bind(this.detailedText.prefHeightProperty());
        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(true);
        
    }

    private void hisfavMouseEntered(MouseEvent event) {

        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle().replace("black", "red"));
    }

    private void hisfavMouseExited(MouseEvent event) {

        Button button = (Button) event.getSource();
        button.setStyle(button.getStyle().replace("red", "black"));
    }

    private void historyButtonController() {

        if(this.history.isEmpty()) {

            this.historyButton.setDisable(true);
        }

        else {

            this.historyButton.setDisable(false);
        }
    }

    @FXML
    void initialize() {

        this.bst.insert(this.business);
        this.bst.insert(this.entertainment);
        this.bst.insert(this.health);
        this.bst.insert(this.science);
        this.bst.insert(this.technology);

        this.newsPane.setVisible(false);
        this.newsDetailScrollPane.setVisible(false);

        Image favoriteImage = new Image("file:icons/favorite.png");
        Image historyImage = new Image("file:icons/history.png");

        this.favoriteButton.setGraphic(new ImageView(favoriteImage));
        this.historyButton.setGraphic(new ImageView(historyImage));

        this.favoriteButton.setTooltip(new Tooltip("Show the most viewed category."));
        this.historyButton.setTooltip(new Tooltip("Back to the recently viewed news."));

        this.favoriteButton.setOnMouseEntered(this::hisfavMouseEntered);
        this.historyButton.setOnMouseEntered(this::hisfavMouseEntered);
        this.favoriteButton.setOnMouseExited(this::hisfavMouseExited);
        this.historyButton.setOnMouseExited(this::hisfavMouseExited);
        this.historyButton.setOnMouseClicked(this::historyButtonClicked);

        this.historyButton.setDisable(true);

        for (Node child : newsBox.getChildren()) {

            Pane pane = (Pane) child;
            Label label = (Label) pane.getChildren().get(0);
            this.newsLabels.add(label);
            label.setOnMouseEntered(this::mouseEntered);
            label.setOnMouseExited(this::mouseExited);
            label.setOnMouseClicked(this::labelClicked);
        }
    }

}
