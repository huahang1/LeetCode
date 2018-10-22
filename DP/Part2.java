//264. Ugly Number II
public int nthUglyNumber(int n) {
    if(n <= 0) return n;
    int[] u = new int[n];
    u[0] = 1;
    int p2 = 0, p3 = 0, p5 = 0;
    for(int i = 1; i < n; i++){
        u[i] = Math.min(u[p2]*2,Math.min(u[p3]*3,u[p5]*5));
        if(u[i] == u[p2]*2) p2++;
        if(u[i] == u[p3]*3) p3++;
        if(u[i] == u[p5]*5) p5++;
    }
    return u[n-1];
}

//276. Paint Fence
public int numWays(int n, int k) {
    if(n == 0) return 0;
    if(n == 1) return k;
    int sameColorCounts = k;
    int diffColorCounts = k * (k-1);
    for(int i = 2; i < n; i++){
        int tmp = diffColorCounts;
        diffColorCounts = (sameColorCounts+diffColorCounts)*(k-1);
        sameColorCounts = tmp;
    }
    return (sameColorCounts+diffColorCounts);
}

//279. Perfect Squares
public int numSquares(int n) {
    int[] dp = new int[n+1];
    Arrays.fill(dp,Integer.MAX_VALUE);
    dp[0] = 0;
    for(int i = 1; i <= n; i++){
        for(int j = 1; j*j <= i; j++){
            dp[i] = Math.min(dp[i],dp[i-j*j]+1);
        }
    }
    return dp[n];
}

//300. Longest Increasing Subsequence
public int lengthOfLIS(int[] nums) {
    int[] tails = new int[nums.length];
    int size = 0;
    for(int x : nums){
        int i = 0, j = size;
        while(i != j){
            int mid = (i+j)/2;
            if(x > tails[mid]){
               i = mid+1;
            }else{
               j = mid;
            }
        }
        tails[i] = x;
        if(i == size) size++;
    }
    return size;
}

//303. Range Sum Query - Immutable
class NumArray {
    
    int[] nums;
    
    public NumArray(int[] nums) {
        for(int i = 1; i < nums.length;i++){
            nums[i] += nums[i-1];
        }    
        this.nums = nums;
    }
    
    public int sumRange(int i, int j) {
        if(i == 0) return nums[j];
        return nums[j] - nums[i-1];
    }
}

//304. Range Sum Query 2D - Immutable
class NumMatrix {
    int[][] sums;
    public NumMatrix(int[][] matrix) {
        int m = matrix.length;
        int n = m >0 ? matrix[0].length : 0;
        sums = new int[m+1][n+1];
        for(int i = 1; i <= m; i++){
            for(int j = 1; j <= n; j++){
                sums[i][j] = sums[i-1][j] + sums[i][j-1] - sums[i-1][j-1] + matrix[i-1][j-1];
            }
        }
    }
    
    public int sumRegion(int row1, int col1, int row2, int col2) {
        //the reason why doesn't puls 1 for row1 and col1 is we need value included row1, col1; if we add plus 1, the ans will be the pure gap/difference
        return sums[row2+1][col2+1] - sums[row1][col2+1] - sums[row2+1][col1]+ sums[row1][col1];
    }
}

//309. Best Time to Buy and Sell Stock with Cooldown
public int maxProfit(int[] prices) {
    if(prices == null || prices.length == 0) return 0;
    int sell = 0, prev_sell = 0, buy = -prices[0], prev_buy = 0;
    for(int price : prices){
        prev_buy = buy;
        buy = Math.max(prev_sell-price,prev_buy);
        prev_sell = sell;
        sell = Math.max(prev_buy+price,prev_sell);
    }
    return sell;
}

//312. Burst Balloons
public int maxCoins(int[] nums) {
    int[] n_nums = new int[nums.length+2];
    int n = 1;
    for(int num:nums){
        n_nums[n++] = num;
    }
    n_nums[0]=n_nums[n++]=1;
    
    int[][] dp = new int[n][n];
    //k refers to the len, first loop list all the possible lenght of combination, left and right are the start and end point
    for(int k = 2; k<n;k++){
        for(int left = 0; left < n-k; left++){
            int right = left+k;
            for(int i = left+1; i < right; i++){
                dp[left][right] = Math.max(dp[left][right],n_nums[left]*n_nums[i]*n_nums[right]+dp[left][i]+dp[i][right]);
            }
        }
    }
    return dp[0][n-1];
}

//321. Create Maximum Number
class Solution {
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] res = new int[k];
        int m = nums1.length, n = nums2.length;
        for(int i = Math.max(0,k-n); i<=m&&i<=k;i++){
            int[] candidate = merge(maxArray(nums1,i),maxArray(nums2,k-i),k);
            if(greater(candidate,0,res,0)) res = candidate;
        }
        return res;
    }
    
    private int[] merge(int[] nums1, int[] nums2, int k){
        int[] res = new int[k];
        for(int i = 0, j= 0, r = 0; r < k; r++){
            res[r] = greater(nums1,i,nums2,j) ? nums1[i++] : nums2[j++];
        }
        return res;
    }
    
    private boolean greater(int[] nums1, int i, int[] nums2, int j){
        while(i < nums1.length && j < nums2.length && nums1[i] == nums2[j]){i++;j++;}
        return j == nums2.length || (i < nums1.length && nums1[i]>nums2[j]);
    }
    
    private int[] maxArray(int[] nums, int k){
        int n = nums.length;
        int[] res = new int[k];
        for(int i = 0, j = 0; i < n; i++){
            while(n-i>k-j && j>0 && res[j-1]<nums[i]) j--;
            if(j < k){res[j++] = nums[i];}
        }
        return res;
    }
}

//322. Coin Change
public int coinChange(int[] coins, int amount) {
    int[] dp = new int[amount+1];
    //fill in impossible values
    Arrays.fill(dp,amount+1);
    //no money is 0
    dp[0] = 0;
    for(int coin : coins){
        for(int i = coin; i <= amount; i++){
            // i-coin is for combinations
            dp[i] = Math.min(dp[i],dp[i-coin]+1);
        }
    }
    return dp[amount] == amount +1 ? -1 : dp[amount];
}

//338. Counting Bits
public int[] countBits(int num) {
    int[] dp = new int[num+1];
    for(int i = 1; i <= num; i++){
        dp[i] = dp[i>>1]+(i&1);
    }
    return dp;
}

//343. Integer Break
public int integerBreak(int n) {
    if(n==2) return 1;
    if(n==3) return 2;
    int res = 1;
    //If an optimal product contains a factor f >= 4, then you can replace it with factors 2 and f-2 without losing optimality, as 2*(f-2) = 2f-4 >= f. So you never need a factor greater than or equal to 4, meaning you only need factors 1, 2 and 3 (and 1 is of course wasteful and you'd only use it for n=2 and n=3, where it's needed).
    while(n>4){
        res *= 3;
        n-=3;
    }
    res *= n;
    return res;
}

//351. Android Unlock Patterns
public int numberOfPatterns(int m, int n) {
    int[][] skip = new int[10][10];
    skip[1][3] = skip[3][1] = 2;
    skip[1][7] = skip[7][1] = 4;
    skip[3][9] = skip[9][3] = 6;
    skip[7][9] = skip[9][7] = 8;
    skip[1][9] = skip[9][1] = skip[2][8] = skip[8][2] = skip[3][7] = skip[7][3] = skip[4][6] = skip[6][4] = 5;
    boolean[] vis = new boolean[10];
    int res = 0;
    for(int i = m; i <= n; ++i) {
        res += DFS(vis, skip, 1, i - 1)*4;    // 1, 3, 7, 9 are symmetric
        res += DFS(vis, skip, 2, i - 1)*4;    // 2, 4, 6, 8 are symmetric
        res += DFS(vis, skip, 5, i - 1);        // 5
    }
    return res;
}

 private int DFS(boolean[] visited, int[][] skip, int cur, int remain){
    if(remain < 0) return 0;
    if(remain == 0) return 1;
    visited[cur] = true;
    int res = 0;
    for(int i = 1; i <= 9; i++){
        if(!visited[i] && (skip[cur][i] == 0 || visited[skip[cur][i]])){
            res += DFS(visited,skip,i,remain-1);
        }
    }
    visited[cur] = false;
    return res;
}

//354. Russian Doll Envelopes
public int maxEnvelopes(int[][] envelopes) {
        if(envelopes.length == 0) return 0;
        Arrays.sort(envelopes,new Comparator<int[]>(){
            public int compare(int[] arr1, int[] arr2){
                if(arr1[0] == arr2[0]){
                    return arr2[1] - arr1[1];
                }else{
                    return arr1[0] - arr2[0];
                }
            }
        });
        int[] tmp = new int[envelopes.length];
        int i = 0;
        for(int[] envelope : envelopes){
            tmp[i++] = envelope[1];
        }
        int count = LIS(tmp);
        return count;
    }
    
    private int LIS(int[] nums){
        if(nums.length <= 1 ) return nums.length;
        int[] tails = new int[nums.length];
        int size = 0;
        for(int x : nums){
            int i = 0, j = size;
            while(i != j){
                int m = (i+j)/2;
                if(tails[m] < x){
                    i = m+1;
                }else{
                    j = m;
                }
            }
            tails[i] = x;
            if(i == size) size++;
        }
        return size;
    }
    
//357. Count Numbers with Unique Digits
public int countNumbersWithUniqueDigits(int n) {
    if(n < 1) return 1;
    int res = 10;
    int uniqueNumbers = 9;
    int count = 9;
    while(n-- > 1 && uniqueNumbers >= 0){
        count *= uniqueNumbers;
        res += count;
        uniqueNumbers--;
    }
    return res;
}

//361. Bomb Enemy
public int maxKilledEnemies(char[][] grid) {
    if(grid.length == 0) return 0;
    int m = grid.length, n = grid[0].length;
    int[] colcache = new int[grid[0].length];
    int rowHits = 0;
    int res = 0;
    for(int i = 0; i < grid.length; i++){
        for(int j = 0; j < grid[0].length;j++){
            if(j == 0 || grid[i][j-1] == 'W'){
                rowHits = 0;
                for(int k = j; k < n && grid[i][k] != 'W'; k++){
                    if(grid[i][k] == 'E'){
                        rowHits++;
                    }
                }
            }
            if(i == 0 || grid[i-1][j] == 'W'){
                colcache[j] = 0;
                for(int k = i; k < m && grid[k][j] != 'W'; k++){
                    if(grid[k][j] == 'E'){
                        colcache[j]++;
                    }
                }
            }
            if(grid[i][j] == '0'){
                res = Math.max(res,rowHits+colcache[j]);
            }
        }
    }
    return res;
}

//363. Max Sum of Rectangle No Larger Than K
public int maxSumSubmatrix(int[][] matrix, int k) {
    if(matrix.length == 0) return 0;
    int m = matrix.length, n = matrix[0].length;
    int res = Integer.MIN_VALUE;
    for(int left = 0; left < n; left++){
        int[] sums = new int[m];
        for(int right = left; right < n; right++){
            for(int i = 0; i < m; i++){
                sums[i] += matrix[i][right];
            }
            TreeSet<Integer> set = new TreeSet<Integer>();
            set.add(0);
            int curSum = 0;
            for(int sum : sums){
                curSum += sum;
                Integer num = set.ceiling(curSum-k);
                if(num != null){
                    res = Math.max(res, curSum-num);
                }
                set.add(curSum);
            }
        }
    }
    return res;
}

//368. Largest Divisible Subset
public List<Integer> largestDivisibleSubset(int[] nums) {
    List<Integer> res = new ArrayList<Integer>();
    if(nums.length == 0) return res;
    int[] count = new int[nums.length];
    int[] pre = new int[nums.length];
    int max = Integer.MIN_VALUE, index = -1;
    Arrays.sort(nums);
    for(int i = 0; i < nums.length;i++){
        count[i] = 1;
        pre[i] = -1;
        for(int j = i-1; j >= 0; j--){
            if(nums[i] % nums[j] == 0){
                if(count[j] + 1 > count[i]){
                count[i] = count[j] + 1;
                pre[i] = j;
                }
            }
        }
        if(count[i] > max){
            max = count[i];
            index = i;
        }
    }
    while(index != -1){
        res.add(nums[index]);
        index = pre[index];
    }
    return res;
}

//375. Guess Number Higher or Lower II
public int getMoneyAmount(int n) {
    int[][] dp = new int[n+1][n+1];
    return helper(dp,1,n);
}

private int helper(int[][] dp, int s, int e){
    if(s >= e) return 0;
    if(dp[s][e] != 0) return dp[s][e];
    int res = Integer.MAX_VALUE;
    for(int i = s; i <= e; i++){
        int tmp = i + Math.max(helper(dp,s,i-1),helper(dp,i+1,e));
        res = Math.min(res,tmp);
    }
    dp[s][e] = res;
    return res;
}

//376. Wiggle Subsequence
public int wiggleMaxLength(int[] nums) {
    if(nums.length == 0) return 0;
    int[] down = new int[nums.length];
    int[] up = new int[nums.length];
    down[0] = 1;
    up[0] = 1;
    for(int i = 1; i < nums.length;i++){
        if(nums[i] > nums[i-1]){
            up[i] = down[i-1]+1;
            down[i] = down[i-1];
        }else if(nums[i] < nums[i-1]){
            down[i] = up[i-1]+1;
            up[i] = up[i-1];
        }else{
            up[i] = up[i-1];
            down[i] = down[i-1];
        }
    }
    return Math.max(up[nums.length-1],down[nums.length-1]);
}