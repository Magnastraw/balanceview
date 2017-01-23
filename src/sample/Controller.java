package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.IndexRange;
import javafx.scene.control.TextField;

public class Controller {

    private BalanceModel model;
    private double[] U;
    private double[] X;

    @FXML
    private LineChart<Double, Double> lineChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private TextField textEditAB;

    @FXML
    private TextField textEditSigma;

    @FXML
    private TextField textEditN;

    @FXML
    private TextField textEditEps;

    @FXML
    private TextField textEditK1;

    @FXML
    private TextField textEditK2;

    @FXML
    private void paintGraph() {
        model = new BalanceModel();
        model.setSigma(Double.valueOf(textEditSigma.getText()));
        model.setEps(Double.valueOf(textEditEps.getText()));
        model.setN(Integer.valueOf(textEditN.getText()));
        model.setK1(Double.valueOf(textEditK1.getText()));
        model.setK2(Double.valueOf(textEditK2.getText()));
        model.setA(Double.valueOf(textEditAB.getText().split(",")[0]));
        model.setB(Double.valueOf(textEditAB.getText().split(",")[1]));

        model.balance();
        U = model.getU();
        X = model.getX();

        XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();

        for (int i = 0; i < U.length; i++) {
            series.getData().add(new XYChart.Data<Double, Double>(X[i], U[i]));
        }

        lineChart.getData().add(series);

    }

    @FXML
    private void clearGraph() {
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(1);
        yAxis.setTickUnit(3);
        lineChart.getData().retainAll();

    }

}