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

//818. Race Car
public int racecar(int target) {
       int[] dp = new int[target+1];
       Arrays.fill(dp,1,dp.length,-1);
       return helper(target,dp);
}
   
private int helper(int i, int[] dp){
       if(dp[i] >= 0) return dp[i];
       dp[i] = Integer.MAX_VALUE;
       int m = 1, j = 1;
       for(; j < i; j = (1 << ++m)-1){
           for(int p = 0, q= 0; p < j; p = (1 << ++q)-1){
               dp[i] = Math.min(dp[i],m+1+q+1+helper(i-(j-p),dp));
           }
       }
       dp[i] = Math.min(dp[i], m + (i==j ? 0 :1+helper(j-i,dp)));
       return dp[i];
}

//837. New 21 Game
public double new21Game(int N, int K, int W) {
    if(K == 0 || N >= K+W) return 1;
    double[] dp = new double[N+1];
    double Wsum = 1, res = 0;
    dp[0] = 1;
    for(int i = 1; i <= N; i++){
        dp[i] = Wsum / W;
        if(i < K){
            Wsum += dp[i];
        }else{
            res += dp[i];
        }
        if(i-W >= 0){
            Wsum -= dp[i-W];
        }
    }
    return res;
}

//838. Push Dominoes
public String pushDominoes(String d) {
        d = 'L'+ d + 'R';
        StringBuilder res = new StringBuilder();
        for(int i = 0, j = 1; j < d.length(); j++){
            if(d.charAt(j) == '.') continue;
            int middle = j-i-1;
            if(i>0) res.append(d.charAt(i));
            if(d.charAt(i) == d.charAt(j)){
                for(int k = 0; k < middle;k++) res.append(d.charAt(i));
            }else if(d.charAt(i) == 'L' && d.charAt(j) == 'R'){
                for(int k = 0; k < middle;k++) res.append('.');
            }else{
                for(int k = 0; k < middle/2;k++) res.append(d.charAt(i));
                if(middle%2 == 1) res.append('.');
                for(int k = 0; k < middle/2;k++) res.append(d.charAt(j));
            }
            i = j;
        }
        return res.toString();
}

//871. Minimum Number of Refueling Stops
public int minRefuelStops(int target, int startFuel, int[][] stations) {
        long[] dp = new long[stations.length+1];
        dp[0] = startFuel;
        for(int i = 0; i < stations.length;i++){
            for(int j = i; j >=0 && dp[j] >= stations[i][0]; j--){
                dp[j+1] = Math.max(dp[j+1],dp[j]+stations[i][1]);
            }
        }
        for(int i = 0; i < dp.length; i++){
            if(dp[i] >= target) return i;
        }
        return -1;
}