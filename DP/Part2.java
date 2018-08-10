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