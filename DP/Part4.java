//552. Student Attendance Record II
public int checkRecord(int n) {
        int m = 1000000007;
        int[] A = new int[n];
        int[] P = new int[n];
        int[] L = new int[n];
        
        if(n == 1) return 3;
        if(n == 2) return 8;
        
        A[0] = 1;
        A[1] = 2;
        A[2] = 4;
        P[0] = 1;
        L[0] = 1;
        L[1] = 3;
        
        for(int i = 1; i < n; i++){
            
            A[i-1] %= m;
            P[i-1] %= m;
            L[i-1] %= m;
            
            P[i] = ((A[i-1]+P[i-1])%m + L[i-1])%m;
            if(i > 1){
                L[i] = ((A[i-1]+P[i-1])%m + (A[i-2]+P[i-2])%m)%m;
            }
            if(i > 2){
                A[i] = ((A[i-1]+A[i-2])%m + A[i-3])%m;
            }
        }
        return ((A[n-1]%m+P[n-1]%m)%m + L[n-1]%m)%m;
}

//576. Out of Boundary Paths
public int findPaths(int m, int n, int N, int i, int j) {
       int mod = 1000000007;
       int[][] count = new int[m][n];
       count[i][j] = 1;
       int res = 0;
       int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
       for(int k = 0; k < N; k++){
           int[][] tmp = new int[m][n];
           for(int r = 0; r < m; r++){
               for(int c = 0; c < n; c++){
                   for(int[] d : dirs){
                       int rp = r + d[0];
                       int cp = c + d[1];
                       if(rp < 0 || rp >= m || cp < 0 || cp >= n){
                           res = (res+count[r][c])%mod;
                       }else{
                           tmp[rp][cp] = (tmp[rp][cp]+count[r][c])%mod;
                       }
                   }
               }
           }
           count = tmp;
       }
       return res;
}