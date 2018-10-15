//5. Longest Palindromic Substring
public String longestPalindrome(String s) {
    if(s.length() < 2) return s;
    int start = 0, end = 0;
    for(int i = 0; i < s.length(); i++){
        //search for two situations: odd and even
        int len1 = dp(s,i,i);
        int len2 = dp(s,i,i+1);
        int len = Math.max(len1,len2);
        if(len > end-start){
            //here put len-1 to avoid start below 0 
            start = i - (len-1)/2;
            end = i + len/2;
        }
    }
    return s.substring(start,end+1);
}
//expand the string by searching left and right
private int dp(String s, int l, int r){
    while(l>=0 && r < s.length() && s.charAt(l)==s.charAt(r)){
        l--;
        r++;
    }
    return r-1-l;
}

//10. Regular Expression Matching
public boolean isMatch(String s, String p) {
        if(s == null || p == null) return false;
        boolean[][] dp = new boolean[s.length()+1][p.length()+1];
        dp[0][0] = true;
        for(int j = 1; j <= p.length(); j++){
            if(p.charAt(j-1) == '*'){
                if(dp[0][j-1] || (j > 1 && dp[0][j-2])){
                    dp[0][j] = true;
                }
            }
        }
        for(int i = 1; i <= s.length(); i++){
            for(int j = 1; j <= p.length(); j++){
                if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.'){
                    dp[i][j] = dp[i-1][j-1];
                }
                if(p.charAt(j-1) == '*'){
                    if(s.charAt(i-1) != p.charAt(j-2) && p.charAt(j-2) != '.'){
                        dp[i][j] = dp[i][j-2];
                    }else{
                        dp[i][j] = dp[i-1][j] || dp[i][j-1] || dp[i][j-2];
                    }
                }
            }
        }
        return dp[s.length()][p.length()];
}

//32. Longest Valid Parentheses
public int longestValidParentheses(String s) {
    int[] dp = new int[s.length()];
    int max = 0, open = 0;
    for(int i = 0; i < s.length();i++){
        if(s.charAt(i) == '(') open++;
        if(s.charAt(i) == ')' && open > 0){
            dp[i] = 2 + dp[i-1];
            //check the symmetric postiton for parenthese count
            if(i - dp[i] >= 0){
                dp[i] += dp[i-dp[i]];
            }
            max = Math.max(max,dp[i]);
            open--;
        }
    }
    return max;
}

//44. Wildcard Matching
public boolean isMatch(String str, String pattern) {
    int s = 0, p = 0, starIndex = -1, match = 0;
    while(s < str.length()){
        //current char match, moves both pointer
        if(p < pattern.length() && (str.charAt(s) == pattern.charAt(p) || pattern.charAt(p) == '?')){
            s++;
            p++;
        }else if(p < pattern.length() && pattern.charAt(p) == '*'){
            //record star index for next not-matched element
            starIndex = p;
            match = s;
            p++;
        }else if(starIndex != -1){
            //contains *, skip current not matched element and move to the next
            p = starIndex+1;
            match++;
            s = match;
        }else{
            return false;
        }
    }
    while(p < pattern.length() && pattern.charAt(p) == '*'){
        p++;
    }
    return p == pattern.length();
}

//53. Maximum Subarray
public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int res = dp[0];
        for(int i = 1; i < nums.length;i++){
            dp[i] = nums[i] + (dp[i-1] > 0 ? dp[i-1] : 0);
            res = Math.max(res,dp[i]);
        }
        return res;
}

//62. Unique Paths
public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i],1);
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
}

//63. Unique Paths II
public int uniquePathsWithObstacles(int[][] o) {
        int n = o[0].length;
        int[] dp = new int[n];
        dp[0] = 1;
        for(int[] row : o){
            for(int j = 0; j < n; j++){
                if(row[j] == 1){
                    dp[j] = 0;
                }else if(j > 0){
                    dp[j] += dp[j-1];
                }
            }
        }
        return dp[n-1];
}

//64. Minimum Path Sum
public int minPathSum(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            Arrays.fill(dp[i],grid[0][0]);
        }
        for(int i = 1; i < m; i++){
            dp[i][0] = dp[i-1][0] + grid[i][0];
        }
        for(int j = 1; j < n; j++){
            dp[0][j] = dp[0][j-1] + grid[0][j];
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1])+grid[i][j];
            }
        }
        return dp[m-1][n-1];
}

//70. Climbing Stairs
public int climbStairs(int n) {
        int[] dp = new int[n+1];
        if(n == 1) return 1;
        if(n == 2) return 2;
        dp[1] = 1;
        dp[2] = 2;
        for(int i = 3; i <= n; i++){
            dp[i] = dp[i-1] + dp[i-2];
        }
        return dp[n];
}

//72. Edit Distance
public int minDistance(String s1, String s2) {
    int l1 = s1.length(), l2 = s2.length();
    int[][] dp = new int[l1+1][l2+1];
    for(int i = 1; i <= l1;i++){
        dp[i][0] = i;
    }
    for(int j = 1; j <= l2;j++){
        dp[0][j] = j;
    }
    for(int i = 1; i <= l1; i++){
        for(int j = 1; j <= l2; j++){
            if(s1.charAt(i-1) == s2.charAt(j-1)){
                dp[i][j] = dp[i-1][j-1];
            }else{
                dp[i][j] = Math.min(dp[i-1][j],Math.min(dp[i-1][j-1],dp[i][j-1]))+1;
            }
        }
    }
    return dp[l1][l2];
}

//85. Maximal Rectangle
public int maximalRectangle(char[][] matrix) {
       if(matrix.length == 0 || matrix == null) return 0;
       int m = matrix.length; 
       int n = matrix[0].length;
       int[] left = new int[n];
       int[] right = new int[n];
       int[] height = new int[n];
       Arrays.fill(right,n);
       int res = 0;
       for(int i = 0; i < m; i++){
           int cur_left = 0, cur_right = n;
           for(int j = 0; j < n; j++){
               if(matrix[i][j] == '1'){
                   height[j]++;
               }else{
                   height[j] = 0;
               }
           }
           for(int j = 0; j < n;j++){
               if(matrix[i][j] == '1'){
                   left[j] = Math.max(left[j],cur_left);
               }else{
                   left[j] = 0; cur_left = j+1;
               }
           }
           for(int j = n-1; j >=0; j--){
               if(matrix[i][j] == '1'){
                   right[j] = Math.min(right[j],cur_right);
               }else{
                   right[j] = n; cur_right= j;
               }
           }
           for(int j = 0; j < n; j++){
               res = Math.max(res,height[j]*(right[j]-left[j]));
           }
       }
       return res;
}

//87. Scramble String
public boolean isScramble(String s1, String s2) {
        if(s1.equals(s2)) return true;
        if(s1.length() != s2.length()) return false;
        int[] count = new int[26];
        for(int i = 0; i < s1.length(); i++){
            count[s1.charAt(i) - 'a']++;
            count[s2.charAt(i) - 'a']--;
        }
        for(int i = 0; i < 26; i++){
            if(count[i] != 0){
                return false;
            }
        }
        for(int i = 1; i < s1.length(); i++){
            if(isScramble(s1.substring(0,i),s2.substring(0,i)) && isScramble(s1.substring(i),s2.substring(i))){
              return true;  
            } 
            if(isScramble(s1.substring(0,i),s2.substring(s1.length()-i)) && isScramble(s1.substring(i),s2.substring(0,s1.length()-i))){
              return true;  
            } 
        }
        return false;
}

//91. Decode Ways
public int numDecodings(String s) {
    int n = s.length();
    if(n == 0) return 0;
    int[] dp = new int[n+1];
    dp[0] = 1;
    dp[1] = s.charAt(0) == '0' ? 0 : 1;
    for(int i = 2; i <= n; i++){
        int first = Integer.parseInt(s.substring(i-1,i));
        int second = Integer.parseInt(s.substring(i-2,i));
        if(first >= 1 && first <= 9){
            dp[i] += dp[i-1];
        }
        if(second >= 10 && second <=26){
            dp[i] += dp[i-2];
        }
    }
    return dp[n];
}

//96. Unique Binary Search Trees
public int numTrees(int n) {
    int[] dp = new int[n+1];
    //empty tree
    dp[0] = 1;
    //root node
    dp[1] = 1;
    for(int i = 2; i <= n;i++){
        for(int j = 1; j <= i;j++){
            dp[i] += dp[j-1] * dp[i-j];
        }
    }
    return dp[n];
}

//95. Unique Binary Search Trees II
public List<TreeNode> generateTrees(int n) {
    if(n == 0) return new LinkedList<>();
    return generateSubTree(1,n);
}
private List<TreeNode> generateSubTree(int start, int end){
    List<TreeNode> res = new LinkedList<>();
    if(start > end){
        //empty tree
        res.add(null);
        return res;
    }
    for(int i = start; i <= end; i++){
        List<TreeNode> left = generateSubTree(start,i-1);
        List<TreeNode> right = generateSubTree(i+1,end);
        for(TreeNode l : left){
            for(TreeNode r : right){
                TreeNode root = new TreeNode(i);
                root.left = l;
                root.right = r;
                res.add(root);
            }
        }
    }
    return res;
}

//97. Interleaving String
public boolean isInterleave(String s1, String s2, String s3) {
    int l1 = s1.length(), l2 = s2.length(), l3 = s3.length();
    if(l1+l2 != l3) return false;
    boolean[][] dp = new boolean[l1+1][l2+1];
    for(int i = 0; i <= l1; i++){
        for(int j = 0; j <= l2; j++){
            if(i==0 && j==0){
                dp[i][j] = true;
            }else if(i == 0){
                dp[i][j] = (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1));
            }else if(j == 0){
                dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1));
            }else{
                dp[i][j] = (dp[i-1][j] && s1.charAt(i-1) == s3.charAt(i+j-1)) || (dp[i][j-1] && s2.charAt(j-1) == s3.charAt(i+j-1));
            }
        }
    }
    return dp[l1][l2];
}

//115. Distinct Subsequences
public int numDistinct(String s, String t) {
    int m = s.length(), n = t.length();
    int[][] dp = new int[n+1][m+1];
    for(int j = 0; j <= m;j++){
        dp[0][j] = 1;
    }
    for(int i = 1; i <= n; i++){
        for(int j = 1; j <= m; j++){
            if(t.charAt(i-1) == s.charAt(j-1)){
                dp[i][j] = dp[i-1][j-1] + dp[i][j-1];
            }else{
                dp[i][j] = dp[i][j-1];
            }
        }
    }
    return dp[n][m];
}

//132. Palindrome Partitioning II
public int minCut(String s) {
    int n = s.length();
    int[] dp = new int[n+1];
    for(int i = 0; i <= n; i++){
        dp[i] = i-1;
    }
    for(int i = 0; i <= n; i++){
        for(int j = 0; i-j >= 0 && i+j < n && s.charAt(i-j) == s.charAt(i+j); j++){
            dp[i+j+1] = Math.min(dp[i+j+1],1+dp[i-j]);
        }
        for(int j = 1; i-j+1 >=0 && i+j < n && s.charAt(i-j+1) == s.charAt(i+j); j++){
            dp[i+j+1] = Math.min(dp[i+j+1],1+dp[i-j+1]);
        }
    }
    return dp[n];
}

//139. Word Break
public boolean wordBreak(String s, List<String> wordDict) {
    int n = s.length();
    boolean[] dp = new boolean[n+1];
    dp[0] = true;
    for(int i = 0; i <= n; i++){
        for(int j = 0; j <= i; j++){
            if(dp[j] && wordDict.contains(s.substring(j,i))){
                dp[i] = true;
                break;
            }
        }
    }
    return dp[n];
}

//140. Word Break II
public List<String> wordBreak(String s, List<String> wordDict) {
    return DFS(s,wordDict,new HashMap<String,LinkedList<String>>());
}
    
private List<String> DFS(String s, List<String> wordDict, HashMap<String,LinkedList<String>> map){
    if(map.containsKey(s)){
        return map.get(s);
    }
    LinkedList<String> res = new LinkedList<String>();
    if(s.length() == 0){
       res.add("");
        return res;
    }
    for(String word : wordDict){
        if(s.startsWith(word)){
            List<String> sublist = DFS(s.substring(word.length()),wordDict,map);
            for(String sub : sublist){
                res.add(word+(sub.isEmpty()? "" : " ")+sub);
            }
        }
    }
    map.put(s,res);
    return res;
}

//174. Dungeon Game
public int calculateMinimumHP(int[][] dungeon) {
    int m = dungeon.length;
    int n = dungeon[0].length;
    int[][] dp = new int[m+1][n+1];
    for(int[] row : dp){
        Arrays.fill(row,Integer.MAX_VALUE);
    }
    dp[m][n-1] = 1;
    dp[m-1][n] = 1;
    for(int i = m-1; i>=0; i--){
        for(int j = n-1; j>=0; j--){
            int need = Math.min(dp[i+1][j],dp[i][j+1]) - dungeon[i][j];
            dp[i][j] = need > 0 ? need : 1;
        }
    }
    return dp[0][0];
}

//188. Best Time to Buy and Sell Stock IV
public int maxProfit(int k, int[] prices) {
    int n = prices.length;
    if(k >= n/2) return quickSolve(prices);
    
    int[][] dp = new int[k+1][n];
    for(int i = 1; i <= k; i++){
        int tmpMax = -prices[0];
        for(int j = 1; j < n; j++){
            dp[i][j] = Math.max(dp[i][j-1],prices[j]+tmpMax);
            tmpMax = Math.max(tmpMax,dp[i-1][j-1]-prices[j]);
        }
    }
    return dp[k][n-1];
    
}
private int quickSolve(int[] prices){
    int res = 0;
    for(int i = 0; i < prices.length-1; i++){
        if(prices[i+1]-prices[i]>0){
            res += prices[i+1]-prices[i];
        }
    }
    return res;
}

//198. House Robber
public int rob(int[] nums) {
    int left = 0, right = nums.length-1;
    int include = 0, exclude = 0;
    for(int j = left; j <= right; j++){
        int i = include, e = exclude;
        include = e + nums[j];
        exclude = Math.max(e,i);
    }
    return Math.max(include,exclude);
}

//213. House Robber II
public int rob(int[] nums) {
    if(nums.length == 1) return nums[0];
    return Math.max(helper(nums,0,nums.length-2),helper(nums,1,nums.length-1));
}

private int helper(int[] nums, int left, int right){
    int include = 0, exclude = 0;
    for(int j = left; j <= right; j++){
        int i = include, e = exclude;
        include = e + nums[j];
        exclude = Math.max(i,e);
    }
    return Math.max(include,exclude);
}

//221. Maximal Square
public int maximalSquare(char[][] matrix) {
    if(matrix.length == 0 || matrix == null) return 0;
    int m = matrix.length, n = matrix[0].length;
    int[][] dp = new int[m][n];
    int res = 0;
    for(int i = 0; i < m; i++){
        dp[i][0] = matrix[i][0] -'0';
        res = Math.max(res,dp[i][0]);
    }
    for(int j = 0; j < n; j++){
        dp[0][j] = matrix[0][j] - '0';
        res = Math.max(res,dp[0][j]);
    }
    for(int i = 1; i < m; i++){
        for(int j = 1; j < n;j++){
            if(matrix[i][j] == '1'){
                dp[i][j] = Math.min(dp[i-1][j],Math.min(dp[i-1][j-1],dp[i][j-1])) + 1;
            }
            res = Math.max(res,dp[i][j]);
        }
    }
    return res * res;
}

//256. Paint House
public int minCost(int[][] costs) {
    if(costs == null || costs.length == 0) return 0;
    int[][] ccosts = costs.clone();
    for(int i = 1; i < ccosts.length;i++){
        ccosts[i][0] += Math.min(ccosts[i-1][1],ccosts[i-1][2]);
        ccosts[i][1] += Math.min(ccosts[i-1][0],ccosts[i-1][2]);
        ccosts[i][2] += Math.min(ccosts[i-1][0],ccosts[i-1][1]);
    }
    int n = costs.length-1;
    return Math.min(ccosts[n][0],Math.min(ccosts[n][1],ccosts[n][2]));
}

//265. Paint House II
public int minCostII(int[][] costs) {
    if(costs == null || costs.length == 0) return 0;
    int[][] dp = costs.clone();
    int n = costs.length;
    int k = costs[0].length;
    int min1 = -1, min2 = -1;
    for(int i = 0; i < n; i++){
        int last1 = min1, last2 = min2;
        min1 = -1; min2 = -1;
        for(int j = 0; j < k; j++){
            if(j != last1){
                dp[i][j] += last1 < 0 ? 0 : dp[i-1][last1];
            }else{
                dp[i][j] += last2 < 0 ? 0 : dp[i-1][last2];
            }
            if(min1 < 0 || dp[i][j]<dp[i][min1]){
                min2 = min1; min1 = j;
            }else if(min2 < 0 || dp[i][j]<dp[i][min2]){
                min2 = j;
            }
        }
    }
    return dp[n-1][min1];
}