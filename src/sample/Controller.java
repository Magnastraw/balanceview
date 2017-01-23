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
    private TextField textEditA;

    @FXML
    private TextField textEditB;

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
    private TextField textEditUa;

    @FXML
    private TextField textEditUb;

    @FXML
    private void paintGraph() {
        model = new BalanceModel();
        model.setSigma(Double.valueOf(textEditSigma.getText()));
        model.setEps(Double.valueOf(textEditEps.getText()));
        model.setN(Integer.valueOf(textEditN.getText()));
        model.setK1(Double.valueOf(textEditK1.getText()));
        model.setK2(Double.valueOf(textEditK2.getText()));
        model.setA(Double.valueOf(textEditA.getText()));
        model.setB(Double.valueOf(textEditB.getText()));
        model.setUa(Double.valueOf(textEditUa.getText()));
        model.setUb(Double.valueOf(textEditUb.getText()));

        yAxis.setLowerBound(Double.valueOf(textEditUa.getText()));
        yAxis.setUpperBound(Double.valueOf(textEditUb.getText()));

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
        yAxis.setLowerBound(Double.valueOf(textEditUa.getText()));
        yAxis.setUpperBound(Double.valueOf(textEditUb.getText()));
        yAxis.setTickUnit(0.1);
        lineChart.getData().retainAll();

    }

}