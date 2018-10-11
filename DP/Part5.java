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

//873. Length of Longest Fibonacci Subsequence
public int lenLongestFibSubseq(int[] A) {
        Set<Integer> set = new HashSet<>();
        for(int a : A){
            set.add(a);
        }
        int res = 2;
        for(int i = 0; i < A.length; i++){
            for(int j = i+1; j < A.length;j++){
                int a = A[i], b = A[j], l = 2;
                while(set.contains(a+b)){
                    b = a+b;
                    a = b-a;
                    l++;
                }
                res = Math.max(res,l);
            }
        }
        return res > 2 ? res : 0;
}

//877. Stone Game
public boolean stoneGame(int[] p) {
        int n = p.length;
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = p[i];
        }
        for(int d = 1; d < n; d++){
            for(int i = 0; i+d < n; i++){
                dp[i][i+d] = Math.max(p[i] - dp[i+1][i+d], p[i+d]-dp[i][i+d-1]);
            }
        }
        return dp[0][n-1]>0;
}

//879. Profitable Schemes
public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        int[][] dp = new int[P+1][G+1];
        dp[0][0] = 1;
        int mod = (int)1e9+7;
        int res = 0;
        for(int k = 0; k < group.length; k++){
            int g = group[k], p = profit[k];
            for(int i = P; i >= 0; i--){
                for(int j = G-g; j >=0; j--){
                    dp[Math.min(i+p,P)][j+g] = (dp[Math.min(i+p,P)][j+g]+dp[i][j]) % mod;
                }
            }
        }
        for(int x : dp[P]){
            res = (res+x)%mod;
        }
        return res;
}

//887. Super Egg Drop
public int superEggDrop(int K, int N) {
        int[][] dp = new int[N+1][K+1];
        int m = 0;
        while(dp[m][K]<N){
            m++;
            for(int k = 1; k <= K; k++){
                dp[m][k] = dp[m-1][k-1]+dp[m-1][k]+1;
            }
        }
        return m;
}

//898. Bitwise ORs of Subarrays
public int subarrayBitwiseORs(int[] A) {
       Set<Integer> res = new HashSet<>(), cur = new HashSet<>(), cur2;
       for(Integer i : A){
           cur2 = new HashSet<>();
           cur2.add(i);
           for(Integer j : cur) cur2.add(i|j);
           cur = cur2;
           res.addAll(cur);
       }
       return res.size();
}

//902. Numbers At Most N Given Digit Set
public int atMostNGivenDigitSet(String[] D, int N) {
        String target = Integer.toString(N);
        int ln = target.length();
        int ld = D.length;
        int res = 0;
        for(int i = 1; i < ln; i++){
            res += Math.pow(ld,i);
        }
        for(int i = 0; i < ln; i++){
            boolean hasSame = false;
            for(int j = 0; j < ld; j++){
                if(D[j].charAt(0) < target.charAt(i)){
                    res += Math.pow(ld,ln-1-i);
                }else if(D[j].charAt(0) == target.charAt(i)){
                    hasSame = true;
                }
            }
            if(!hasSame) return res;
        }
        return res+1;
}

//903. Valid Permutations for DI Sequence
public int numPermsDISequence(String S) {
        int n = S.length(), mod = (int)1e9+7;
        int[][] dp = new int[n+1][n+1];
        for(int i = 0; i <= n; i++) dp[0][i] = 1;
        for(int i = 0; i < n; i++){
            if(S.charAt(i) == 'I'){
                for(int j = 0,cur = 0; j < n-i; j++){
                    dp[i+1][j] = cur = (cur+dp[i][j])%mod;
                }
            }else{
                for(int j = n-1-i,cur = 0; j >=0; j--){
                    dp[i+1][j] = cur = (cur+dp[i][j+1])%mod;
                }
            }
        }
        return dp[n][0];
}