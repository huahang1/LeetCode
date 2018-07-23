//766. Toeplitz Matrix
public boolean isToeplitzMatrix(int[][] matrix) {
    for (int i = 0; i < matrix.length - 1; i++) {
        for (int j = 0; j < matrix[i].length - 1; j++) {
            if (matrix[i][j] != matrix[i + 1][j + 1]) return false;
        }
    }
    return true;
}

//768. Max Chunks To Make Sorted II
public int maxChunksToSorted(int[] arr) {
        int n = arr.length;
        int[] maxOfLeft = new int[n];
        int[] minOfRight = new int[n];

        maxOfLeft[0] = arr[0];
        for (int i = 1; i < n; i++) {
            maxOfLeft[i] = Math.max(maxOfLeft[i-1], arr[i]);
        }

        minOfRight[n - 1] = arr[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            minOfRight[i] = Math.min(minOfRight[i + 1], arr[i]);
        }

        int res = 0;
        for (int i = 0; i < n - 1; i++) {
            if (maxOfLeft[i] <= minOfRight[i + 1]) res++;
        }

        return res + 1;
}

//769. Max Chunks To Make Sorted
public int maxChunksToSorted(int[] arr) {
        if (arr == null || arr.length == 0) return 0;
        
        int[] max = new int[arr.length];
        max[0] = arr[0];
        for (int i = 1; i < arr.length; i++) {
            max[i] = Math.max(max[i - 1], arr[i]);
        }
        
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (max[i] == i) {
                count++;
            }
        }
        
        return count;
}

//775. Global and Local Inversions
public boolean isIdealPermutation(int[] A) {
        int cmax = 0;
        for (int i = 0; i < A.size() - 2; ++i) {
            cmax = Math.max(cmax, A[i]);
            if (cmax > A[i + 2]) return false;
        }
        return true;
}

//782. Transform to Chessboard
public int movesToChessboard(int[][] b) {
    int N = b.length, rowSum = 0, colSum = 0, rowSwap = 0, colSwap = 0;
    for (int i = 0; i < N; ++i) for (int j = 0; j < N; ++j)
            if ((b[0][0] ^ b[i][0] ^ b[0][j] ^ b[i][j]) == 1) return -1;
    for (int i = 0; i < N; ++i) {
        rowSum += b[0][i];
        colSum += b[i][0];
        if (b[i][0] == i % 2) rowSwap ++;
        if (b[0][i] == i % 2) colSwap ++ ;
    }
    if (N / 2 > rowSum || rowSum > (N + 1) / 2) return -1;
    if (N / 2 > colSum || colSum > (N + 1) / 2) return -1;
    if (N % 2 == 1) {
        if (colSwap % 2 == 1) colSwap = N - colSwap;
        if (rowSwap % 2 == 1) rowSwap = N - rowSwap;
    } else {
        colSwap = Math.min(N - colSwap, colSwap);
        rowSwap = Math.min(N - rowSwap, rowSwap);
    }
    return (colSwap + rowSwap) / 2;
}

//792. Number of Matching Subsequences
public int numMatchingSubseq(String S, String[] words) {
    List<Integer[]>[] waiting = new List[128];
    for (int c = 0; c <= 'z'; c++)
        waiting[c] = new ArrayList();
    for (int i = 0; i < words.length; i++)
        waiting[words[i].charAt(0)].add(new Integer[]{i, 1});
    for (char c : S.toCharArray()) {
        List<Integer[]> advance = new ArrayList(waiting[c]);
        waiting[c].clear();
        for (Integer[] a : advance)
            waiting[a[1] < words[a[0]].length() ? words[a[0]].charAt(a[1]++) : 0].add(a);
    }
    return waiting[0].size();
}

//795. Number of Subarrays with Bounded Maximum
public int numSubarrayBoundedMax(int[] A, int L, int R) {
        int j=0,count=0,res=0;
        
        for(int i=0;i<A.length;i++){
            if(A[i]>=L && A[i]<=R){
                res+=i-j+1;count=i-j+1;
            }
            else if(A[i]<L){
                res+=count;
            }
            else{
                j=i+1;
                count=0;
            }
        }
        return res;
}

//825. Friends Of Appropriate Ages
public int numFriendRequests(int[] ages) {
        int res = 0;
        int[] numInAge = new int[121], sumInAge = new int[121];
        
        for(int i : ages) 
            numInAge[i] ++;
        
        for(int i = 1; i <= 120; ++i) 
            sumInAge[i] = numInAge[i] + sumInAge[i - 1];
        
        for(int i = 15; i <= 120; ++i) {
            if(numInAge[i] == 0) continue;
            int count = sumInAge[i] - sumInAge[i / 2 + 7];
            res += count * numInAge[i] - numInAge[i]; //people will not friend request themselves, so  - numInAge[i]
        }
        return res;
}

//830. Positions of Large Groups
public List<List<Integer>> largeGroupPositions(String S) {
        int i = 0, j = 0, N = S.length();
        List<List<Integer>> res = new ArrayList<>();
        while (j < N) {
            while (j < N && S.charAt(j) == S.charAt(i)) ++j;
            if (j - i >= 3) res.add(Arrays.asList(i, j - 1));
            i = j;
        }
        return res;
}

//832. Flipping an Image
public int[][] flipAndInvertImage(int[][] A) {
       int n = A.length;
       for (int[] row : A)
           for (int i = 0; i * 2 < n; i++)
               if (row[i] == row[n - i - 1])
                   row[i] = row[n - i - 1] ^= 1;
       return A;
}

//835. Image Overlap
public int largestOverlap(int[][] A, int[][] B) {
        int N = A.length;
        List<Integer> LA = new ArrayList<>();
        List<Integer> LB = new ArrayList<>();
        HashMap<Integer, Integer> count = new HashMap<>();
        for (int i = 0; i < N * N; ++i) if (A[i / N][i % N] == 1) LA.add(i / N * 100 + i % N);
        for (int i = 0; i < N * N; ++i) if (B[i / N][i % N] == 1) LB.add(i / N * 100 + i % N);
        for (int i : LA) for (int j : LB)
                count.put(i - j, count.getOrDefault(i - j, 0) + 1);
        int res = 0;
        for (int i : count.values()) res = Math.max(res, i);
        return res;
}

//840. Magic Squares In Grid
public int numMagicSquaresInside(int[][] grid) {
        int cnt=0;
        for(int i=0;i<=grid.length-3;i++)
            for(int j=0;j<=grid[0].length-3;j++)
                if(helper(i,j,grid)) cnt++;
            
        return cnt;
    }  
    
    private boolean helper(int x,int y,int[][] grid){
        if(grid[x+1][y+1]!=5) return false;
        
        int[] valid=new int[16];
        
        for(int i=x;i<=x+2;i++)
            for(int j=y;j<=y+2;j++)
                valid[grid[i][j]]++;
            
        for (int v = 1; v <= 9; v++)
            if (valid[v] != 1) return false;
        
        if((grid[x][y]+grid[x][y+1]+grid[x][y+2])!=15)         return false;
        if((grid[x][y]+grid[x+1][y+1]+grid[x+2][y+2])!=15)     return false;
        if((grid[x][y]+grid[x+1][y]+grid[x+2][y])!=15)         return false;
        if((grid[x+2][y]+grid[x+2][y+1]+grid[x+2][y+2])!=15)   return false;
        if((grid[x][y+2]+grid[x+1][y+2]+grid[x+2][y+2])!=15)   return false;
        if((grid[x][y+2]+grid[x+1][y+1]+grid[x+2][y])!=15)     return false;
        return true;
}

//849. Maximize Distance to Closest Person
public int maxDistToClosest(int[] seats) {
        int res = 0, n = seats.length;
        for (int i = 0, zero = 0; i < n; ++i) if (seats[i] == 1) zero = 0; else res = Math.max(res, (++zero + 1) / 2);
        for (int i = 0, zero = 0; seats[i] == 0; ++i) res = Math.max(res, ++zero);
        for (int i = n - 1, zero = 0; seats[i] == 0; --i) res = Math.max(res, ++zero);
        return res;
}