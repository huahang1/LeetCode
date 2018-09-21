//377. Combination Sum IV
private int[] dp;

public int combinationSum4(int[] nums, int target) {
    dp = new int[target+1];
    Arrays.fill(dp,-1);
    return helper(dp,nums,target);
}

private int helper(int[] dp, int[] nums, int target){
    int res = 0;
    if(dp[target] != -1){
        return dp[target];
    }
    if(target == 0){
        return 1;
    }
    for(int i = 0; i < nums.length;i++){
        if(target >= nums[i]){
            res += helper(dp,nums,target-nums[i]);
        }
    }
    dp[target] = res;
    return res;
}

//392. Is Subsequence
public boolean isSubsequence(String s, String t) {
    List<Integer>[] list = new List[256];
    for(int i = 0; i < t.length();i++){
        if(list[t.charAt(i)] == null){
            list[t.charAt(i)] = new ArrayList<Integer>();
        }
        list[t.charAt(i)].add(i);
    }
    int prev = 0;
    for(int i = 0; i < s.length(); i++){
        if(list[s.charAt(i)] == null) return false;
        int j = Collections.binarySearch(list[s.charAt(i)],prev);
        if(j < 0) j = -j-1;
        if(j == list[s.charAt(i)].size()) return false;
        prev = list[s.charAt(i)].get(j) + 1;
    }
    return true;
}