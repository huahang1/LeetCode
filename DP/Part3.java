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

//410. Split Array Largest Sum
public int splitArray(int[] nums, int m) {
    int left = 0, right = 0;
    for(int num : nums){
        left = Math.max(left,num);
        right += num;
    }
    while(left < right){
        int mid = left + (right-left)/2;
        if(canCut(nums,m-1,mid)){
            right = mid;
        }else{
            left = mid+1;
        }
    }
    return left;
}

private boolean canCut(int[] nums, int cuts, int max){
    int acc = 0;
    for(int num : nums){
        if(num > max){
            return false;
        }else if(num + acc <= max){
            acc += num;
        }else{
            --cuts;
            acc = num;
            if(cuts < 0) return false;
        }
    }
    return true;
}

//413. Arithmetic Slices
public int numberOfArithmeticSlices(int[] A) {
    int curr = 0, sum = 0;
    for(int i = 2; i < A.length; i++){
        if(A[i-1] - A[i-2] == A[i] - A[i-1]){
            curr++;
            sum += curr;
        }else{
            curr = 0;
        }
    }
    return sum;
}

//416. Partition Equal Subset Sum
public boolean canPartition(int[] nums) {
    int sum = 0;
    for(int num : nums){
        sum += num;
    }
    if(sum % 2 == 1){
        return false;
    }
    sum /= 2;
    int n = nums.length;
    boolean[][] dp = new boolean[n+1][sum+1];
    dp[0][0] = true;
    for(int i = 1; i < n+1; i++){
        dp[i][0] = true;
    }
    for(int i = 1; i < n+1; i++){
        for(int j = 1; j < sum+1; j++){
            dp[i][j] = dp[i-1][j];
            if(j >= nums[i-1]){
                dp[i][j] = (dp[i][j] || dp[i-1][j-nums[i-1]]);
            }
        }
    }
    return dp[n][sum];
}

//418. Sentence Screen Fitting
public int wordsTyping(String[] sentence, int rows, int cols) {
    String words = String.join(" ",sentence) + " ";
    int pos = 0;
    int l = words.length();
    for(int i = 0; i < rows;i++){
        pos += cols;
        if(words.charAt(pos%l) == ' '){
            pos++;
        }else while(pos > 0 && words.charAt((pos-1)%l) != ' '){
            pos--;
        }
    }
    return pos / l;
}