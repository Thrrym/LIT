package de.tuberlin.tkn.lit.util;

public class DamerauLevenshteinDistance {
    private String src;
    
    public DamerauLevenshteinDistance(String source) {
        this.src = source;
    }

    public int getDistanceTo(String target) {
        int m = src.length();
        int n = target.length();

        if (n > m)
            n = m;

        if (n == 0 || m == 0)
            return m+n;

        if (src.equals(target)) {
            return 0;
        }

        int[][] v = new int[3][n+1];

        for (int i = 0; i < n+1; i++)
            v[0][i] = i;

        for (int i = 1; i < m+1; i++) {
            for (int j = 0; j < n+1; j++) {
                if (j == 0) {
                    v[i % 3][0] = i;
                }
                else if (src.charAt(i-1) == target.charAt(j-1)) {
                    v[i % 3][j] = v[(i-1) % 3][j-1];
                }
                else {
                    int d = Math.min(v[(i-1) % 3][j-1], Math.min(v[i % 3][j-1], v[(i-1) % 3][j]));
                    if (i > 1 && j > 1 && src.charAt(i-1) == target.charAt(j-2) && src.charAt(i-2) == target.charAt(j-1)) {
                        v[i % 3][j] = 1 + Math.min(v[(i-2) % 3][j-2], d);
                    }
                    else {
                        v[i % 3][j] = 1 + d;
                    }
                }
            }
        }

        return v[m % 3][n];
    }
}