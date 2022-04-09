public class gh {
    public boolean isPrime(int n) throws Exception {
        boolean b = true;
        if (n < 0) {
            throw new Exception("input not valid");
        }
        if (n < 2) b = false;
        else {
            int i = 2;
            while (i < n / 2) {
                if (n % i == 0) b = false;
                else b = true;
                i++;
            }
        }
        return b;
    }
}
