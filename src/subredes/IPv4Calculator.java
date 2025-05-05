package subredes;

public class IPv4Calculator {

    public static String calcularMascaraDecimal(int cidr) {
        String binario = "1".repeat(cidr) + "0".repeat(32 - cidr);
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            String bloco = binario.substring(i * 8, (i + 1) * 8);
            resultado.append(Integer.parseInt(bloco, 2));
            if (i < 3) resultado.append(".");
        }
        return resultado.toString();
    }

    public static String calcularMascaraBinaria(int cidr) {
        String binario = "1".repeat(cidr) + "0".repeat(32 - cidr);
        return binario.replaceAll("(.{8})(?!$)", "$1.").trim();
    }

    public static long calcularHosts(int cidr) {
        if (cidr >= 32) return 0;
        return (long) Math.pow(2, 32 - cidr) - 2;
    }

    public static long calcularSubRedes(int cidr, int bitsClasse) {
        if (cidr < bitsClasse) return 0;
        return (long) Math.pow(2, cidr - bitsClasse);
    }

    public static String identificarClasse(int primeiroOcteto) {
        if (primeiroOcteto >= 0 && primeiroOcteto <= 127) return "A";
        if (primeiroOcteto >= 128 && primeiroOcteto <= 191) return "B";
        if (primeiroOcteto >= 192 && primeiroOcteto <= 223) return "C";
        return "Fora do Range";
    }

    public static int getBitsClasse(String classe) {
        return switch (classe) {
            case "A" -> 8;
            case "B" -> 16;
            case "C" -> 24;
            default -> 0;
        };
    }
}
