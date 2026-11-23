package grid.ui;

import grid.model.CarComponent;

/**
 * Simple helper class for Terminal UI styling:
 * - Manufacturer ANSI Brand Colors (Ferrari Red, Porsche Gold, Mercedes Teal, McLaren Papaya)
 * - Breadcrumb navigation trails
 * - Framed section banners and polymorphic ASCII car silhouettes
 */
public final class ConsoleTheme {

    public static final String RESET  = "\u001B[0m";
    public static final String BOLD   = "\u001B[1m";
    public static final String DIM    = "\u001B[2m";

    public static final String RED    = "\u001B[91m";       // Ferrari Rosso Corsa
    public static final String GOLD   = "\u001B[93m";       // Porsche Racing Gold
    public static final String TEAL   = "\u001B[96m";       // Mercedes-AMG Petronas Teal
    public static final String PAPAYA = "\u001B[38;5;208m"; // McLaren Papaya Orange
    public static final String GREEN  = "\u001B[92m";       // Observer Alert / Success
    public static final String WHITE  = "\u001B[97m";

    private ConsoleTheme() {
    }

    /**
     * Returns the signature brand color for a given manufacturer ID.
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
                return TEAL;
        }
    }

    /**
     * Prints a breadcrumb navigation trail at the top of a screen.
     * Example: [ GRID ] > [ Manufacturers ] > [ Ferrari ] > [ Ferrari 499P ]
     */
    public static void printBreadcrumb(String... segments) {
        StringBuilder sb = new StringBuilder("\n  ");
        for (int i = 0; i < segments.length; i++) {
            if (i == segments.length - 1) {
                sb.append(BOLD).append(WHITE).append("[ ").append(segments[i]).append(" ]").append(RESET);
            } else {
                sb.append(DIM).append("[ ").append(segments[i]).append(" ] > ").append(RESET);
            }
        }
        System.out.println(sb);
    }

    /**
     * Prints a framed header banner in the given accent color.
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
     * Prints the polymorphic ASCII silhouette of a CarComponent in its brand color.
     */
    public static void printCarSilhouette(CarComponent car, String brandColor) {
        System.out.println(brandColor + BOLD + car.getAsciiSilhouette() + RESET);
    }
}
