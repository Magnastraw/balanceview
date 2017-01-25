package sample;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Controller {

    private BalanceModel model;
    private int number=1;
    private double[] U;
    private double[] X;
    private double[] U_exact;


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
    private TextField textEditT;

    @FXML
    private Label textLabelT;

    @FXML
    private Label textLabelExact;

    @FXML
    private void paintGraph() {
        model = new BalanceModel();
        model.setSigma(Double.valueOf(textEditSigma.getText()));
        model.setEps(Double.valueOf(textEditEps.getText()));
        model.setN(Integer.valueOf(textEditN.getText()));
        model.setT(Double.valueOf(textEditT.getText()));
        model.setK1(Double.valueOf(textEditK1.getText()));
        model.setK2(Double.valueOf(textEditK2.getText()));
        model.setA(Double.valueOf(textEditA.getText()));
        model.setB(Double.valueOf(textEditB.getText()));
        model.setUa(Double.valueOf(textEditUa.getText()));
        model.setUb(Double.valueOf(textEditUb.getText()));

        if (Integer.valueOf(textEditN.getText()) > 20) {
            lineChart.setCreateSymbols(false);
        } else {
            lineChart.setCreateSymbols(true);
        }

        yAxis.setLowerBound(Double.valueOf(textEditUa.getText()));
        yAxis.setUpperBound(Double.valueOf(textEditUb.getText()));

        model.balance();

        U = model.getU();
        X = model.getX();

        textLabelT.setText("Решение найдено на итерации: " + model.getIteration() + " Время: " + model.getT());

        XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        series.setName("Приближенное решение "+number);
        for (int i = 0; i < U.length; i++) {
            series.getData().add(new XYChart.Data<Double, Double>(X[i], U[i]));
        }

        lineChart.getData().add(series);

        number++;
    }

    @FXML
    private void clearGraph() {
        number=1;
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(Double.valueOf(textEditUa.getText()));
        yAxis.setUpperBound(Double.valueOf(textEditUb.getText()));
        yAxis.setTickUnit(0.1);
        lineChart.getData().retainAll();
    }

    @FXML
    private void exactSolution(){
        model.exact_solution();
        X = model.getX();
        U_exact = model.getU_exact();
        XYChart.Series<Double, Double> series = new XYChart.Series<Double, Double>();
        series.setName("Точное решение");
        for (int i = 0; i < U.length; i++) {
            series.getData().add(new XYChart.Data<Double, Double>(X[i], U_exact[i]));
        }

        lineChart.getData().add(series);

        textLabelExact.setText("Максимальная разница с точным решением: "+model.getMeps());
    }
}