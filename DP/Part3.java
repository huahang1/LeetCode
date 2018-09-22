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

//403. Frog Jump
public boolean canCross(int[] stones) {
        if(stones.length == 0) return false;
        HashMap<Integer,HashSet<Integer>> map = new HashMap<Integer,HashSet<Integer>>();
        map.put(0,new HashSet<Integer>());
        map.get(0).add(1);
        for(int i = 1; i < stones.length -1; i++){
            map.put(stones[i],new HashSet<Integer>());
        }
        for(int i = 0; i < stones.length -1; i++){
            int stone = stones[i];
            for(int step : map.get(stone)){
                int reach = stone + step;
                if(reach == stones[stones.length-1]) return true;
                if(map.get(reach) != null){
                    map.get(reach).add(step);
                    map.get(reach).add(step+1);
                    if(step-1 > 0){
                        map.get(reach).add(step-1);
                    }
                }
            }
        }
        return false;
}