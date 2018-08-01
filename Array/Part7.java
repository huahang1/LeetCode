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
    
    int count = 0, max = 0;
    for (int i = 0; i < arr.length; i++) {
        max = Math.max(max, arr[i]);
        if (max == i) {
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
public int movesToChessboard(int[][] board) {
    int N = board.length;
    for(int i = 0; i < N;i++){
        for(int j = 0; j < N;j++){
            if((board[0][0] ^ board[0][j] ^ board[i][0] ^ board[i][j]) == 1){
                return -1;
            }
        }
    }
    int colSum = 0, rowSum = 0, colSwap = 0, rowSwap = 0;
    for(int i = 0; i < N;i++){
        rowSum += board[0][i];
        colSum += board[i][0];
        //two ones can not stay together
        if(board[0][i] == i%2) colSwap++;
        if(board[i][0] == i%2) rowSwap++;
    }
    if(N/2 > rowSum || (N+1)/2 < rowSum) return -1;
    if(N/2 > colSum || (N+1)/2 < colSum) return -1;
    if(N%2 == 1){
        if(colSwap % 2 == 1) colSwap = N-colSwap;
        if(rowSwap % 2 == 1) rowSwap = N-rowSwap;
    }else{
        colSwap = Math.min(colSwap,N-colSwap);
        rowSwap = Math.min(rowSwap,N-rowSwap);
    }
    return (colSwap+rowSwap)/2;
}

//792. Number of Matching Subsequences
public int numMatchingSubseq(String S, String[] words) {
    Map<Character,Deque<String>> map = new HashMap<>();
    for(char c='a';c<='z';c++){
        map.putIfAbsent(c,new LinkedList<String>());
    }
    for(String word:words){
        map.get(word.charAt(0)).addLast(word);
    }
    int count = 0;
    for(char c : S.toCharArray()){
        Deque<String> queue = map.get(c);
        int size = queue.size();
        for(int i = 0; i < size;i++){
            String word = queue.removeFirst();
            if(word.length() == 1){
                count++;
            }else{
                map.get(word.charAt(1)).addLast(word.substring(1));
            }
        }
    }
    return count;
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
    int[] freq = new int[121];
    int requests = 0;
    for(int age:ages){
        freq[age]++;
    }
    for(int i = 15; i <= 120; i++){
        for(int j = i/2 + 8; j<= i; j++){
            requests += freq[j] * (freq[i] - (i==j ? 1 : 0));
        }
    }
    return requests;
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