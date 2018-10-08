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

//600. Non-negative Integers without Consecutive Ones
public int findIntegers(int num) {
        StringBuilder sb = new StringBuilder(Integer.toBinaryString(num)).reverse();
        int n = sb.length();
        int[] zeroes = new int[n];
        int[] ones = new int[n];
        zeroes[0] = 1;
        ones[0] = 1;
        for(int i = 1; i < n; i++){
            zeroes[i] = zeroes[i-1]+ones[i-1];
            ones[i] = zeroes[i-1];
        }
        int res = zeroes[n-1]+ones[n-1];
        for(int i = n-2; i >=0; i--){
            if(sb.charAt(i)== '1' && sb.charAt(i+1) == '1') break;
            if(sb.charAt(i)== '0' && sb.charAt(i+1) == '0') res -= ones[i];
        }
        return res;
}

//629. K Inverse Pairs Array
public int kInversePairs(int n, int k) {
    if(k > (n-1)*n/2 || k < 0) return 0;
    if(k == 0 || k == (n-1)*n/2) return 1;
    long mod = 1000000007;
    long[][] dp = new long[n+1][k+1];
    dp[2][0] = 1;
    dp[2][1] = 1;
    for(int i = 3; i <= n; i++){
        dp[i][0] = 1;
        for(int j = 1; j <= Math.min(k,(i-1)*i/2);j++){
            dp[i][j] = dp[i][j-1] + dp[i-1][j];
            if(j >= i){
                dp[i][j] -= dp[i-1][j-i];
            }
            dp[i][j] = (dp[i][j]+mod)%mod;
        }
    }
    return (int) dp[n][k];
}

//638. Shopping Offers
public int shoppingOffers(List<Integer> price, List<List<Integer>> special, List<Integer> needs) {
        return helper(price,special,needs,0);
}
    
private int helper(List<Integer> price, List<List<Integer>> special, List<Integer> needs, int pos){
        int local_min = directPurchase(price,needs);
        for(int i = pos; i < special.size(); i++){
            List<Integer> offer = special.get(i);
            List<Integer> tmp = new ArrayList<Integer>();
            for(int j = 0; j < needs.size(); j++){
                if(needs.get(j) < offer.get(j)){
                    tmp = null;
                    break;
                }
                tmp.add(needs.get(j)-offer.get(j));
            }
            if(tmp != null){
                local_min = Math.min(local_min,offer.get(offer.size()-1)+helper(price,special,tmp,i));
            }
        }
        return local_min;
}
    
private int directPurchase(List<Integer> price, List<Integer> needs){
        int total = 0;
        for(int i = 0; i < needs.size(); i++){
            total += needs.get(i) * price.get(i);
        }
        return total;
}

//639. Decode Ways II
public int numDecodings(String s) {
        long[] dp = new long[s.length()+1];
        if(s.charAt(0) == '0') return 0;
        dp[0] = 1;
        dp[1] = s.charAt(0) == '*' ? 9 : 1;
        for(int i = 2; i <= s.length(); i++){
            char first = s.charAt(i-2);
            char second = s.charAt(i-1);
            
            if(second == '*'){
                dp[i] += 9*dp[i-1];
            }else if(second > '0'){
                dp[i] += dp[i-1];
            }
            
            if(first == '*'){
                if(second == '*'){
                    dp[i] += 15*dp[i-2];
                }else if(second <= '6'){
                    dp[i] += 2*dp[i-2];
                }else{
                    dp[i] += dp[i-2];
                }
            }else if(first == '1' || first == '2'){
                if(second == '*'){
                    if(first == '1'){
                        dp[i] += 9*dp[i-2];
                    }else{
                        dp[i] += 6*dp[i-2];
                    }
                }else if((first-'0')*10+(second-'0')<=26){
                    dp[i] += dp[i-2];
                }
            }
            dp[i] %= 1000000007;
        }
        return (int)dp[s.length()];
}

//646. Maximum Length of Pair Chain
public int findLongestChain(int[][] pairs) {
        if(pairs.length == 0 || pairs == null) return 0;
        Arrays.sort(pairs,(a,b)->(a[0]-b[0]));
        int[] dp = new int[pairs.length];
        Arrays.fill(dp,1);
        for(int i = 0; i < pairs.length; i++){
            for(int j = 0; j < i; j++){
                dp[i] = Math.max(dp[i],pairs[i][0] > pairs[j][1] ? dp[j]+1 : dp[j]);
            }
        }
        return dp[pairs.length-1];
}

//647. Palindromic Substrings
class Solution {
    int count = 0;
    public int countSubstrings(String s) {
        for(int i = 0; i < s.length();i++){
            helper(s,i,i);
            helper(s,i,i+1);
        }
        return count;
    }
    private int helper(String s, int left, int right){
        while(left >= 0 && right < s.length() && s.charAt(left) == s.charAt(right)){
            count++;left--;right++;
        }
        return count;
    }
}

//650. 2 Keys Keyboard
public int minSteps(int n) {
        int[] dp = new int[n+1];
        for(int i = 2; i <= n; i++){
            dp[i] = i;
            for(int j = i-1; j >= 1; j--){
                if(i%j == 0){
                    dp[i] = dp[j] + (i/j);
                    break;
                }
            }
        }
        return dp[n];
}

//664. Strange Printer
public int strangePrinter(String s) {
        if(s.length() == 0 || s == null){
            return 0;
        }
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = 1;
            if(i+1 < n){
                dp[i][i+1] = s.charAt(i) == s.charAt(i+1) ? 1 : 2;
            } 
        }
        for(int len = 2; len < n; len++){
            for(int start = 0; start+len < n; start++){
                dp[start][start+len] = len+1;
                for(int k = 0; k < len; k++){
                    int tmp = dp[start][start+k] + dp[start+k+1][start+len];
                    dp[start][start+len] = Math.min(dp[start][start+len],s.charAt(start+k) == s.charAt(start+len) ? tmp-1 : tmp);
                }
            }
        }
        return dp[0][n-1];
}

//673. Number of Longest Increasing Subsequence
public int findNumberOfLIS(int[] nums) {
       int n = nums.length, res = 0, max_len = 0;
       int[] len = new int[n];
       int[] count = new int[n];
       for(int i = 0; i < n; i++){
           len[i] = count[i] = 1;
           for(int j = 0; j < i; j++){
               if(nums[i] > nums[j]){
                   if(len[i] == len[j]+1){
                       count[i] += count[j];
                   }
                   if(len[i] < len[j]+1){
                       len[i] = len[j]+1;
                       count[i] = count[j];
                   }
               }
           }
           if(max_len == len[i]){
               res += count[i];
           }
           if(max_len < len[i]){
               max_len = len[i];
               res = count[i];
           }
       }
       return res;
}

//688. Knight Probability in Chessboard
public double knightProbability(int N, int K, int r, int c) {
       int[][] moves = {{1,2},{1,-2},{-1,2},{-1,-2},{2,1},{2,-1},{-2,-1},{-2,1}};
       double[][] dp0 = new double[N][N];
       for(double[] row: dp0){
           Arrays.fill(row,1);
       }
       for(int l = 0; l < K;l++){
           double[][] dp1 = new double[N][N];
           for(int i = 0; i < N; i++){
               for(int j = 0; j < N; j++){
                   for(int[] move : moves){
                       int row = i + move[0];
                       int col = j + move[1];
                       if(valid(row,col,N)){
                           dp1[i][j] += dp0[row][col];
                       }
                   }
               }
           }
           dp0 = dp1;
       }
       return dp0[r][c] / Math.pow(8,K);
   }
private boolean valid(int r, int c, int len){
       return r >=0 && r < len && c>=0 && c <len;
}

//691. Stickers to Spell Word
public int minStickers(String[] stickers, String target) {
        int m = stickers.length;
        int[][] mp = new int[m][26];
        for(int i = 0; i < m; i++){
            for(char c :stickers[i].toCharArray()){
                mp[i][c-'a']++;
            }
        }
        Map<String,Integer> dp = new HashMap<String,Integer>();
        dp.put("",0);
        return helper(mp,dp,target);
    }
    
    private int helper(int[][] mp, Map<String,Integer> dp, String target){
        if(dp.containsKey(target)) return dp.get(target);
        int m = mp.length;
        int res = Integer.MAX_VALUE;
        int[] tar = new int[26];
        for(int i = 0; i < target.length();i++){
            tar[target.charAt(i)-'a']++;
        }
        for(int i = 0; i < m; i++){
            if(mp[i][target.charAt(0)-'a'] == 0){
                continue;
            }
            StringBuilder sb = new StringBuilder();
            for(int j = 0; j < 26; j++){
                if(tar[j]>0){
                    for(int k = 0; k < Math.max(0,tar[j]-mp[i][j]);k++){
                        sb.append((char)('a'+j));
                    }
                }
            }
            String s = sb.toString();
            int tmp = helper(mp,dp,s);
            if(tmp != -1){
                res = Math.min(res,tmp+1);
            }
        }
        if(res == Integer.MAX_VALUE){
            dp.put(target,-1);
        }else{
            dp.put(target,res);
        }
        return dp.get(target);
}

//698. Partition to K Equal Sum Subsets
public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        for(int num:nums) sum += num;
        if(k < 0 || sum % k != 0) return false;
        int[] visited = new int[nums.length];
        return helper(nums,visited,0,k,0,sum/k);
    }
    
    private boolean helper(int[] nums,int[] visited,int start_index,int k, int curr_sum,int target){
        if(k == 1) return true;
        if(curr_sum == target){
            return helper(nums,visited,0,k-1,0,target);
        }
        for(int i = start_index; i < nums.length; i++){
            if(visited[i] == 0){
                visited[i] =1;
                if(helper(nums,visited,i+1,k,curr_sum+nums[i],target)){
                    return true;
                }
                visited[i] = 0;
            }
        }
        return false;
}

//712. Minimum ASCII Delete Sum for Two Strings
public int minimumDeleteSum(String s1, String s2) {
       int m = s1.length(), n = s2.length();
       int[][] dp = new int[m+1][n+1];
       for(int j = 1; j <= n; j++){
           dp[0][j] = dp[0][j-1] + (int) s2.charAt(j-1);
       }
       for(int i = 1; i <= m; i++){
           dp[i][0] = dp[i-1][0] + (int) s1.charAt(i-1);
           for(int j = 1; j <= n; j++){
               if(s1.charAt(i-1) == s2.charAt(j-1)){
                   dp[i][j] = dp[i-1][j-1];
               }else{
                   dp[i][j] = Math.min(dp[i-1][j] + (int) s1.charAt(i-1),dp[i][j-1] + (int) s2.charAt(j-1));
               }
           }
       }
       return dp[m][n];
}

//730. Count Different Palindromic Subsequences
public int countPalindromicSubsequences(String s) {
        int n = s.length();
        int[][] dp = new int[n][n];
        for(int i = 0; i < n; i++){
            dp[i][i] = 1;
        }
        for(int distance = 1; distance < n; distance++){
            for(int i = 0; i+distance<n;i++){
                int j = i+distance;
                if(s.charAt(i) == s.charAt(j)){
                    int low = i+1;
                    int high = j-1;
                    while(low <= high && s.charAt(low) != s.charAt(j)){
                        low++;
                    }
                    while(low <=high && s.charAt(high) != s.charAt(j)){
                        high--;
                    }
                    if(low > high){
                        dp[i][j] = dp[i+1][j-1]*2 +2;
                    }else if(low == high){
                        dp[i][j] = dp[i+1][j-1]*2+1;
                    }else{
                        dp[i][j] = dp[i+1][j-1]*2-dp[low+1][high-1];
                    }
                }else{
                    dp[i][j] = dp[i+1][j] + dp[i][j-1] - dp[i+1][j-1];
                }
                dp[i][j] = dp[i][j] < 0 ? dp[i][j] + 1000000007 : dp[i][j] % 1000000007;
            }
        }
        return dp[0][n-1];
}

//740. Delete and Earn
public int deleteAndEarn(int[] nums) {
        int[] values = new int[10001];
        for(int num : nums){
            values[num] += num;
        }
        int take = 0, skip = 0;
        for(int i = 0; i < 10001; i++){
            int takei = values[i] + skip;
            int skipi = Math.max(skip,take);
            take = takei;
            skip = skipi;
        }
        return Math.max(take,skip);
}