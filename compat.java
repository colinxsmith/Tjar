import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import com.bitaplus.BitaModel.Optimisation.OptimiserController;

public class compat {
    static double[] s2d(String key, HashMap<String, String[]> D) {
        String[] back = D.get(key);
        double[] backd = null;
        if (back != null && (back[0]).length() > 0) {
            backd = new double[back.length];
            for (int i = 0; i < back.length; ++i)
                backd[i] = Double.parseDouble(back[i]);
        }
        return backd;
    }

    static int[] s2i(String key, HashMap<String, String[]> D) {
        String[] back = D.get(key);
        int[] backd = null;
        if (back != null && (back[0]).length() > 0) {
            backd = new int[back.length];
            for (int i = 0; i < back.length; ++i)
                backd[i] = Integer.parseInt(back[i]);
        }
        return backd;
    }

    static double[][] single2double(int m, int n, double[] singleArray) {
        if(singleArray==null)return null;
        double[][] back = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                back[i][j] = singleArray[i + m * j];
            }
        }
        return back;
    }

    static { // Need this if it is not part of class OptimiserController
        try {
            System.loadLibrary("OptimiserController");
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            System.err.println("Native code library OptimiserController failed to load.\n" + e);
            // System.exit(1);
        }
    }

    public static void main(String args[]) {
        System.out.println(OptimiserController.version());

        System.out.println(OptimiserController.expire_date());

        String file = "prob20june.log";
        if (args.length == 1)
            file = args[0];
        String[] keys = "SV FC FL n nfac names m A L soft_m soft_A soft_L soft_U soft_b soft_l U alpha shortalphacost bench Q gamma initial delta buy sell qbuy qsell kappa basket longbasket downrisk downfactor shortbasket tradebuy tradesell tradenum revise costs min_holding min_trade ls full minRisk maxRisk rmin rmax round min_lot size_lot ncomp Composites value valuel npiece hpiece pgrad nabs A_abs mabs I_A Abs_U Abs_L ShortCostScale mask"
                .split(" ");
        String delimiter = " ";
        String line = "";
        String lastKey = "";
        BufferedReader br = null;
        HashMap<String, String[]> DATA = new HashMap<String, String[]>();
        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
                // System.out.println(line);
                if (line.contains("--------"))
                    break;
                String[] values;
                if (Arrays.asList(keys).contains(line)) {
                    lastKey = line;
                    line = br.readLine();
                    if (line == null)
                        break;
                    values = line.split(delimiter);
                    DATA.put(lastKey, values);
                } else {// We get here if array data goes over one line or unrecognised key word
                    if (!lastKey.matches("names") && line.matches("^[A-Z,a-z].*")) {
                        lastKey = line;
                        continue;
                    }
                    values = line.split(delimiter);
                    String[] here = DATA.get(lastKey);
                    if (here != null) {// Add the values in line to values in lastKey
                        int newLength = values.length + here.length;
                        String[] newValues = new String[newLength];
                        System.arraycopy(here, 0, newValues, 0, here.length);
                        System.arraycopy(values, 0, newValues, here.length, values.length);
                        DATA.put(lastKey, newValues);
                    } else {
                        DATA.put(lastKey, values);// Parts of the data file have what look like unknown keys
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(lastKey + " " + line + ":" + e);
        } finally {
            if (br != null)
                br = null;
        }
        int n = s2i("n", DATA)[0];
        int m = s2i("m", DATA)[0];
        int nfac = s2i("nfac", DATA)[0];
        int basket = s2i("basket", DATA)[0];
        int trades = s2i("tradenum", DATA)[0];
        int revise = s2i("revise", DATA)[0];
        int costs = s2i("costs", DATA)[0];
        int ls = s2i("ls", DATA)[0];
        int full = s2i("full", DATA)[0];
        double[] A = s2d("A", DATA);
        double[] A_abs = null;
        try {
            A_abs = s2d("A_abs", DATA);
        } catch (Exception jj) {
            ;
        }
        int[] I_Ai = null;
        long[] I_A = null;
        try {
            I_Ai = s2i("I_A", DATA);
            I_A = new long[I_Ai.length];
            for (int i = 0; i < I_Ai.length; ++i) {
                I_A[i] = (long) I_Ai[i];
            }
        } catch (Exception e) {
            ;
        }
        int round = s2i("round", DATA)[0];
        int ncomp = s2i("ncomp", DATA)[0];
        int npiece = s2i("npiece", DATA)[0];
        int longbasket = s2i("longbasket", DATA)[0];
        int shortbasket = s2i("shortbasket", DATA)[0];
        int tradebuy = s2i("tradebuy", DATA)[0];
        int tradesell = s2i("tradesell", DATA)[0];
        int nabs = s2i("nabs", DATA)[0];
        int mabs = s2i("mabs", DATA)[0];
        double[][] AA = single2double(m, n, A);
        double[][] Abs_A = single2double(nabs, n, A_abs);
        double[] L = s2d("L", DATA);
        double[] U = s2d("U", DATA);
        double[] Abs_L = null;
        try {
            Abs_L = s2d("Abs_L", DATA);
        } catch (Exception e) {
            ;
        }
        double[] Abs_U = null;
        try {
            Abs_U = s2d("Abs_U", DATA);
        } catch (Exception e) {
            ;
        }
        double[] initial = s2d("initial", DATA);
        double[] alpha = s2d("alpha", DATA);
        double[] bench = s2d("bench", DATA);
        double[] Q = s2d("Q", DATA);
        double[] SV = null;
        double[] FC = null;
        double[][] FL = null;
        if (nfac > 0) {
            SV = s2d("SV", DATA);
            FC = s2d("FC", DATA);
            double[] FLf = s2d("FL", DATA);
            FL = single2double(nfac, n, FLf);
        }
        if (SV != null)
            Q = null;
        double[] buy = null;
        try {
            buy = s2d("buy", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] sell = null;
        try {
            sell = s2d("sell", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] min_lot = null;
        try {
            min_lot = s2d("min_lot", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] size_lot = null;
        try {
            size_lot = s2d("size_lot", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] Composites = null;
        try {
            Composites = s2d("Composites", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] hpiece = null;
        try {
            hpiece = s2d("hpiece", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] pgrad = null;
        try {
            pgrad = s2d("pgrad", DATA);
        } catch (Exception e) {
            // System.out.println(e);
        }
        double[] mask = null;
        double gamma = s2d("gamma", DATA)[0];
        double delta = s2d("delta", DATA)[0];
        double kappa = s2d("kappa", DATA)[0];
        double min_hold = s2d("min_holding", DATA)[0];
        double min_trade = s2d("min_trade", DATA)[0];
        double minRisk = s2d("minRisk", DATA)[0];
        double maxRisk = s2d("maxRisk", DATA)[0];
        double rmin = s2d("rmin", DATA)[0];
        double rmax = s2d("rmax", DATA)[0];
        int downrisk = s2i("downrisk", DATA)[0];
        double downfactor = s2d("downfactor", DATA)[0];
        double value = s2d("value", DATA)[0];
        double valuel = s2d("valuel", DATA)[0];
        double ShortCostScale = s2d("ShortCostScale", DATA)[0];
        double zetaF = 1;
        double zetaS = 1;

        double[] w = new double[n];
        int[] shake = new int[n];
        double[] ogamma = new double[1];
        OptimiserController.Optimise_internalCVPAFbl((long) n, nfac, DATA.get("names"), w, (long) m, AA, L, U,
                alpha, bench, Q, gamma, initial, delta, buy, sell, kappa, basket, trades, revise, costs, min_hold,
                min_trade, ls, full, rmin, rmax, round, min_lot, size_lot, shake, (long) ncomp, Composites, value,
                (long) npiece, hpiece, pgrad, (long) nabs, Abs_A, (long) mabs, I_A, Abs_U, FC, FL, SV,
                minRisk, maxRisk, ogamma, mask, 2, "OptJava.log", downrisk, downfactor, longbasket, shortbasket,
                tradebuy, tradesell, zetaS, zetaF, ShortCostScale, valuel, Abs_L);
    }

}
