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