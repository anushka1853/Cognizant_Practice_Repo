public class Main {
    public static void main(String[] args) {
        double initial = 1000.0;        // Starting value
        double growth = 0.10;           // 10% annual growth
        int years = 5;

        // Recursive Forecast
        double forecast = ForecastCalculator.calculateFutureValue(initial, growth, years);
        System.out.printf("Predicted future value (recursive): %.2f\n", forecast);

        // Memoized Forecast (Optimized)
        double[] memo = new double[years + 1];
        double forecastMemo = ForecastCalculator.calculateFutureValueMemo(initial, growth, years, memo);
        System.out.printf("Predicted future value (memoized): %.2f\n", forecastMemo);
    }
}
