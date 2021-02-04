package panda.guest.ui.controllers;

import javafx.event.ActionEvent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import panda.guest.ui.models.filtering.PostFilterByFileExt;
import panda.guest.ui.scenes.HomeScene;
import panda.host.utils.Current;

import static panda.host.utils.Panda.switchScene;

public class StatsController {

    public Button btn_back;
    public LineChart<String, Integer> lchart_file_stats;

    public void initialize(){
        initChart();
    }

    public void backHome(ActionEvent event) {
        try{
            switchScene(btn_back.getScene(), new HomeScene());

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    void initChart(){
        XYChart.Series<String, Integer> fileStatSeries = new XYChart.Series<>();

        // PDF Files
        int numberOfPdfFiles = new PostFilterByFileExt("PDF Files", "PDF")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("PDF", numberOfPdfFiles));

        // WORD Files
        int numberOfWordFiles = new PostFilterByFileExt("Word Files", "DOCX")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Word Files", numberOfWordFiles));


        // EXCEL Files
        int numberOfExcelFiles = new PostFilterByFileExt("Excel Files", "XLSX")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Excel Files", numberOfExcelFiles));


        // POWERPOINT Files
        int numberOfPPtFiles = new PostFilterByFileExt("PwP Files", "PPTX")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("PwP", numberOfPPtFiles));


        // ARCHIVE Files
        int numberOfArchFiles = new PostFilterByFileExt("Archives", "RAR", "ZIP", "ISO")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Archives", numberOfArchFiles));


        // IMAGE Files
        int numberOfImgFiles = new PostFilterByFileExt("Images", "PNG", "JPG", "JPEG", "GIF", "BMP")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Images", numberOfImgFiles));


        // MUSIC Files
        int numberOfMusicFiles = new PostFilterByFileExt("Musics", "MP3", "WAV", "FLAC", "M4A")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Musics", numberOfMusicFiles));


        // VIDEO Files
        int numberOfVdoFiles = new PostFilterByFileExt("Videos", "MP4", "AVI", "WMV", "MKV")
                .filter(Current.postList).size();
        fileStatSeries.getData().add(new XYChart.Data<>("Videos", numberOfVdoFiles));


        // OTHER Files
        int numberOfOtherFiles = Current.postList.size() - (numberOfArchFiles + numberOfExcelFiles + numberOfImgFiles +
                numberOfMusicFiles + numberOfPdfFiles + numberOfPPtFiles + numberOfVdoFiles + numberOfWordFiles);
        fileStatSeries.getData().add(new XYChart.Data<>("Others", numberOfOtherFiles));

        // Adding the series to the chart
        lchart_file_stats.getData().add(fileStatSeries);
    }
}
