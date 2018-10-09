//790. Domino and Tromino Tiling
public int numTilings(int N) {
        if(N == 0) return 0;
        long mod = 1000000007;
        long[] dp = new long[1001];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 5;
        for(int i = 4; i <= N; i++){
            dp[i] = 2 * dp[i-1] + dp[i-3];
            dp[i] %= mod;
        }
        return (int)dp[N];
}

//801. Minimum Swaps To Make Sequences Increasing
public int minSwap(int[] A, int[] B) {
        int swap = 1, fix = 0;
        for(int i = 1; i < A.length; i++){
            if(A[i-1] >= B[i] || B[i-1] >= A[i]){
                swap++;
            }else if(A[i-1] >= A[i] || B[i-1] >= B[i]){
                int tmp = swap;
                swap = fix+1;
                fix = tmp;
            }else{
                int min = Math.min(swap,fix);
                swap = min+1;
                fix = min;
            }
        }
        return Math.min(swap,fix);
}

//808. Soup Servings
double[][] cache = new double[200][200];
    
public double soupServings(int N) {
    return N > 4800 ? 1.0 : helper((N+24)/25,(N+24)/25);
}
    
public double helper(int a, int b){
        if(a<=0 && b<=0) return 0.5;
        if(a<=0) return 1;
        if(b<=0) return 0;
        if(cache[a][b] > 0) return cache[a][b];
        cache[a][b] = 0.25 * (helper(a-4,b)+helper(a-3,b-1)+helper(a-2,b-2)+helper(a-1,b-3));
        return cache[a][b];
}

//813. Largest Sum of Averages
public double largestSumOfAverages(int[] A, int K) {
        double cur = 0;
        int N = A.length;
        double[][] dp = new double[N+1][N+1];
        for(int i = 0; i < N; i++){
            cur += A[i];
            dp[i+1][1] = cur / (i+1); 
        }
        return search(N,K,A,dp);
    }
    
private double search(int n, int k, int[] A, double[][] dp){
        if(n < k) return 0;
        if(dp[n][k] > 0) return dp[n][k];
        double cur = 0;
        for(int i = n-1; i > 0; i--){
            cur += A[i];
            dp[n][k] = Math.max(dp[n][k],search(i,k-1,A,dp)+cur/(n-i));
        }
        return dp[n][k];
}