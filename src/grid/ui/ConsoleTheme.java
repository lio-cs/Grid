package grid.ui;

import grid.model.CarComponent;

/**
 * Utility class responsible for all Terminal UI (TUI) styling:
 * - Manufacturer ANSI Brand Colors (Ferrari Red, Porsche Gold, Mercedes Teal, McLaren Papaya)
 * - Breadcrumb navigation trails
 * - Proportional ASCII performance gauges (Horsepower, Top Speed, Weight)
 * - Side-by-side Head-to-Head Car Comparison tables with delta calculation
 *
 * OOP Principle: Single Responsibility Principle (SRP) — separates presentation formatting
 * from domain models and menu controller logic.
 */
public final class ConsoleTheme {

    // Standard ANSI Escape Codes supported by Windows 10/11 Terminal, PowerShell, and IntelliJ
    public static final String RESET   = "\u001B[0m";
    public static final String BOLD    = "\u001B[1m";
    public static final String DIM     = "\u001B[2m";

    public static final String RED     = "\u001B[91m";       // Ferrari Rosso Corsa
    public static final String GOLD    = "\u001B[93m";       // Porsche Racing Yellow / Gold
    public static final String TEAL    = "\u001B[96m";       // Mercedes-AMG Petronas Teal
    public static final String PAPAYA  = "\u001B[38;5;208m"; // McLaren Papaya Orange
    public static final String GREEN   = "\u001B[92m";       // Positive Delta / Success
    public static final String BLUE    = "\u001B[94m";       // Info / Track Accent
    public static final String WHITE   = "\u001B[97m";

    private ConsoleTheme() {
        // Utility class
    }

    /**
     * Returns the signature motorsport brand color for a given manufacturer ID.
     */
    public static String getBrandColor(String manufacturerId) {
        if (manufacturerId == null) return WHITE;
        switch (manufacturerId.trim().toUpperCase()) {
            case "M-FER":
                return RED;
            case "M-POR":
                return GOLD;
            case "M-MER":
                return TEAL;
            case "M-MCL":
                return PAPAYA;
            default:
                return CYAN_DEFAULT();
        }
    }

    private static String CYAN_DEFAULT() {
        return TEAL;
    }

    /**
     * Prints a breadcrumb navigation bar at the top of a view.
     * Example: [ GRID ] > [ Manufacturers ] > [ Ferrari ] > [ Ferrari 499P ]
     */
    public static void printBreadcrumb(String... segments) {
        StringBuilder sb = new StringBuilder("\n  ");
        for (int i = 0; i < segments.length; i++) {
            boolean isLast = (i == segments.length - 1);
            if (isLast) {
                sb.append(BOLD).append(WHITE).append("[ ").append(segments[i]).append(" ]").append(RESET);
            } else {
                sb.append(DIM).append("[ ").append(segments[i]).append(" ] > ").append(RESET);
            }
        }
        System.out.println(sb);
    }

    /**
     * Prints a framed banner with the given accent color.
     */
    public static void printBanner(String title, String subtitle, String accentColor) {
        String line = "+======================================================================+";
        System.out.println(accentColor + BOLD + line + RESET);
        System.out.printf("%s| %-68s |%s%n", accentColor + BOLD, title, RESET);
        if (subtitle != null && !subtitle.isEmpty()) {
            System.out.printf("%s| %-68s |%s%n", accentColor, subtitle, RESET);
        }
        System.out.println(accentColor + BOLD + line + RESET);
    }

    /**
     * Renders a visual progress bar gauge: e.g. [################....]
     */
    public static String renderBar(int value, int maxValue, int width, String color) {
        int clamped = Math.max(0, Math.min(value, maxValue));
        int filled = (int) Math.round((clamped / (double) maxValue) * width);
        StringBuilder bar = new StringBuilder("[");
        bar.append(color);
        for (int i = 0; i < width; i++) {
            bar.append(i < filled ? '#' : '.');
        }
        bar.append(RESET).append("]");
        return bar.toString();
    }

    /**
     * Calculates power-to-weight ratio in Horsepower per metric tonne (1000 kg).
     */
    public static int calculateHpPerTonne(int horsepower, int weightKg) {
        if (weightKg <= 0) return 0;
        return (int) Math.round((horsepower * 1000.0) / weightKg);
    }

    /**
     * Prints the visual telemetry stat gauges for any CarComponent (stock or Decorator-tuned).
     */
    public static void printTelemetryGauges(CarComponent car, String brandColor) {
        int hp = car.getHorsepower();
        int speed = car.getTopSpeedKph();
        int weight = car.getWeightKg();
        int ptw = calculateHpPerTonne(hp, weight);

        System.out.printf("    * Engine       : %s%s%s%n", BOLD, car.getEngineSpec(), RESET);
        System.out.printf("    * Power Output : %s %s%4d HP%s   (Max scale: 1200 HP)%n",
                renderBar(hp, 1200, 20, brandColor), BOLD, hp, RESET);
        System.out.printf("    * Top Speed    : %s %s%4d km/h%s (Max scale:  380 km/h)%n",
                renderBar(speed, 380, 20, brandColor), BOLD, speed, RESET);
        System.out.printf("    * Minimum Wt.  : %s %s%4d kg%s   (Power/Weight: %s%d HP/t%s)%n",
                renderBar(weight, 1400, 20, BLUE), BOLD, weight, RESET, GREEN, ptw, RESET);
    }

    /**
     * Renders a Side-by-Side Head-to-Head Comparison table between two CarComponents
     * (either two different catalog cars OR a Stock Car vs. a Decorator-Tuned Garage Car).
     */
    public static void printComparisonTable(CarComponent carA, CarComponent carB) {
        String nameA = truncate(carA.getName(), 28);
        String nameB = truncate(carB.getName(), 28);

        int hpA = carA.getHorsepower(), hpB = carB.getHorsepower();
        int spdA = carA.getTopSpeedKph(), spdB = carB.getTopSpeedKph();
        int wtA = carA.getWeightKg(), wtB = carB.getWeightKg();
        int ptwA = calculateHpPerTonne(hpA, wtA), ptwB = calculateHpPerTonne(hpB, wtB);

        System.out.println("\n+------------------------------------------------------------------------------+");
        System.out.println("|                     HEAD-TO-HEAD TELEMETRY COMPARISON                        |");
        System.out.println("+----------------------+---------------------------+---------------------------+");
        System.out.printf("| %-20s | %-25s | %-25s |%n", "METRIC", nameA, nameB);
        System.out.println("+----------------------+---------------------------+---------------------------+");
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Regulation Class", carA.getCarClass(), carB.getCarClass());
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Engine Spec", truncate(carA.getEngineSpec(), 25), truncate(carB.getEngineSpec(), 25));
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Horsepower (HP)",
                formatHigherIsBetter(hpA, hpB, "HP"),
                formatHigherIsBetter(hpB, hpA, "HP"));
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Top Speed (km/h)",
                formatHigherIsBetter(spdA, spdB, "km/h"),
                formatHigherIsBetter(spdB, spdA, "km/h"));
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Minimum Weight (kg)",
                formatLowerIsBetter(wtA, wtB, "kg"),
                formatLowerIsBetter(wtB, wtA, "kg"));
        System.out.printf("| %-20s | %-25s | %-25s |%n",
                "Power-to-Weight",
                formatHigherIsBetter(ptwA, ptwB, "HP/t"),
                formatHigherIsBetter(ptwB, ptwA, "HP/t"));
        System.out.println("+----------------------+---------------------------+---------------------------+");
    }

    private static String formatHigherIsBetter(int myVal, int otherVal, String unit) {
        int diff = myVal - otherVal;
        if (diff > 0) {
            return String.format("%d %s (+%d %s)", myVal, unit, diff, unit);
        }
        return String.format("%d %s", myVal, unit);
    }

    private static String formatLowerIsBetter(int myVal, int otherVal, String unit) {
        int diff = myVal - otherVal;
        if (diff < 0) {
            return String.format("%d %s (%d %s lighter)", myVal, unit, diff, unit);
        }
        return String.format("%d %s", myVal, unit);
    }

    public static String truncate(String text, int maxLen) {
        if (text == null) return "";
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen - 3) + "...";
    }
}
