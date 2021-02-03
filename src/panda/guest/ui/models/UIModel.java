package panda.guest.ui.models;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.jetbrains.annotations.NotNull;
import panda.guest.ui.models.filtering.PostFilterByFileExt;
import panda.guest.ui.models.sorting.*;

import java.util.ArrayList;

public class UIModel {

    public static @NotNull ObservableList<PostSorting> getPostSortingTypes() {
        return FXCollections.observableArrayList(new ArrayList<>(){{
            add(new UploadDateSorting());
            add(new LastUpdateSorting());
            add(new FileExtSorting());
            add(new FileSizeSorting());
        }});
    }

    public static @NotNull ObservableList<PostFilterByFileExt> getPostFilteringExtensionTypes() {
        return FXCollections.observableArrayList(new ArrayList<>(){{
            add(new PostFilterByFileExt("All"));
            add(new PostFilterByFileExt("Archives", "RAR", "ZIP", "ISO"));
            add(new PostFilterByFileExt("Images", "PNG", "JPG", "JPEG", "GIF", "BMP"));
            add(new PostFilterByFileExt("Musics", "MP3", "WAV", "FLAC", "M4A"));
            add(new PostFilterByFileExt("Videos", "MP4", "AVI", "WMV", "MKV"));
            add(new PostFilterByFileExt("Texts", "TXT"));
            add(new PostFilterByFileExt("PDF Files", "PDF"));
            add(new PostFilterByFileExt("Excel Files", "XLSX"));
            add(new PostFilterByFileExt("Word Files", "DOCX"));
            add(new PostFilterByFileExt("PwP Files", "PPTX"));
        }});
    }

    public static void showInfoAlert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(message);
        alert.show();
    }
    public static void showInfoAlert(String title, String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.show();
    }
    public static void showErrorAlert(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(message);
        alert.show();
    }
}
