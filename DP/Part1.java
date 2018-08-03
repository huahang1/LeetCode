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