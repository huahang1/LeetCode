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
    for(int j = 1; j < dp[0].length;j++){
        if(p.charAt(j-1) == '*'){
            if(dp[0][j-1] || j > 0 && dp[0][j-2]){
                dp[0][j] = true;
            }
        }
    }
    for(int i = 1; i < dp.length;i++){
        for(int j = 1; j < dp[0].length;j++){
            if(s.charAt(i-1) == p.charAt(j-1) || p.charAt(j-1) == '.'){
                dp[i][j] = dp[i-1][j-1];
            }
            if(p.charAt(j-1) == '*'){
                if(s.charAt(i-1) != p.charAt(j-2) && p.charAt(j-2) != '.'){
                    dp[i][j] = dp[i][j-2];
                }else{
                  //dp[i-1][j] is for a* count as multiple a (in this case p.charAt(j-2) == '.'), dp[i][j-1] for a* count as single a, dp[i][j-2] for a* count as empty
                    dp[i][j] = dp[i-1][j] || dp[i][j-1] || dp[i][j-2];
                }
            }
        }
    }
    return dp[s.length()][p.length()];
}

//32. Longest Valid Parentheses
public int longestValidParentheses(String s) {
    int max = 0, open = 0;
    char[] p = s.toCharArray();
    int[] dp = new int[s.length()];
    for(int i = 0; i < p.length;i++){
        if(p[i] == '(') open++;
        if(p[i] == ')' && open > 0){
            dp[i] = 2 + dp[i-1];
            if(i-dp[i] >= 0){
                //here i-dp[i] is i-(dp[i-1]+2), add previous matched parenthese dp[i-(dp[i-1]+2)], here i-dp[i] is for symmetric position
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