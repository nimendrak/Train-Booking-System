import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class ButtonFX {

    void closeBtn(Button closeBtn) {
        closeBtn.setPrefWidth(75);
        closeBtn.setPadding(new Insets(8));
        closeBtn.setFont(new Font("Arial Bold", 16));
        closeBtn.setTextFill(Paint.valueOf("f4f4f4"));
        closeBtn.setStyle("-fx-background-color: rgba(227,35,109,0.8); -fx-background-radius: 20;");
        closeBtn.setOnMouseEntered(event -> {
            closeBtn.setStyle("-fx-background-color: rgba(175,33,90,0.8); -fx-background-radius: 20;");
        });
        closeBtn.setOnMouseExited(event -> {
            closeBtn.setStyle("-fx-background-color: rgba(227,35,109,0.8); -fx-background-radius: 20;");
        });
        closeBtn.setCursor(Cursor.HAND);
    }

    void addBtn(Button addBtn) {
        addBtn.setPrefWidth(75);
        addBtn.setPadding(new Insets(8));
        addBtn.setFont(new Font("Arial Bold", 16));
        addBtn.setTextFill(Paint.valueOf("f4f4f4"));
        addBtn.setStyle("-fx-background-color: rgba(0,166,156,0.8); -fx-background-radius: 20;");
        addBtn.setOnMouseEntered(event -> {
            addBtn.setStyle("-fx-background-color: rgb(0,155,146); -fx-background-radius: 20;");
        });
        addBtn.setOnMouseExited(event -> {
            addBtn.setStyle("-fx-background-color: rgba(0,166,156,0.8); -fx-background-radius: 20;");
        });
        addBtn.setCursor(Cursor.HAND);
    }

    void selectionPanel(HBox selection, ToggleButton btnOne, ToggleButton btnTwo) {
        selection.getChildren().addAll(btnOne, btnTwo);
        selection.setPadding(new Insets(10));

        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.grayRgb(10, 0.3));
        shadow.setRadius(8);
        shadow.setOffsetY(1);
        shadow.setOffsetX(1);
        shadow.setBlurType(BlurType.GAUSSIAN);

        selection.setPadding(new Insets(5));
        selection.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 30");

        btnOne.setStyle("-fx-background-radius: 30");
        btnOne.setPrefSize(30, 10);
        btnOne.setCursor(Cursor.HAND);
        btnOne.setStyle("-fx-background-color: white; -fx-background-radius: 30;");
        btnOne.setEffect(shadow);

        btnTwo.setStyle("-fx-background-radius: 30");
        btnTwo.setPrefSize(30, 10);
        btnTwo.setCursor(Cursor.HAND);
        btnTwo.setStyle("-fx-background-color: null; -fx-background-radius: 30;");

        btnTwo.setOnMouseClicked(event -> {
            if (btnTwo.isSelected()) {
                btnTwo.setStyle("-fx-background-color: white; -fx-background-radius: 30;");
                btnTwo.setEffect(shadow);
                btnOne.setStyle("-fx-background-color: null; -fx-background-radius: 30;");
                selection.setStyle("-fx-background-color: #6edc5f; -fx-background-radius: 30");
            }
        });

        btnOne.setOnMouseClicked(event -> {
            btnOne.setStyle("-fx-background-color: white; -fx-background-radius: 30;");
            btnOne.setEffect(shadow);
            btnTwo.setStyle("-fx-background-color: null; -fx-background-radius: 30;");
            selection.setStyle("-fx-background-color: #dddddd; -fx-background-radius: 30");
        });
    }
}
