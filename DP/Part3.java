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

//446. Arithmetic Slices II - Subsequence
public int numberOfArithmeticSlices(int[] A) {
    Map<Integer,Integer>[] map = new Map[A.length];
    int res = 0;
    for(int i = 0; i < A.length; i++){
        map[i] = new HashMap<Integer,Integer>();
        for(int j = 0; j < i; j++){
            long diff = (long) A[i] - A[j];
            if(diff <= Integer.MIN_VALUE || diff >= Integer.MAX_VALUE) continue;
            int d = (int) diff;
            int c1 = map[i].getOrDefault(d,0);
            int c2 = map[j].getOrDefault(d,0);
            res += c2;
            map[i].put(d,c1+c2+1);
        }
    }
    return res;
}

//464. Can I Win
public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
    if(desiredTotal <= 0) return true;
    if((1+maxChoosableInteger)*maxChoosableInteger/2 < desiredTotal) return false;
    return helper(desiredTotal, new int[maxChoosableInteger], new HashMap<>());
}

private boolean helper(int total, int[] state, HashMap<String,Boolean> map){
    String key = Arrays.toString(state);
    if(map.containsKey(key)) return map.get(key);
    for(int i = 0; i < state.length; i++){
        if(state[i] == 0){
            state[i] = 1;
            if(total <= i+1 || !helper(total-(i+1),state,map)){
                map.put(key,true);
                state[i] = 0;
                return true;
            }
            state[i] = 0;
        }
    }
    map.put(key,false);
    return false;
}

//466. Count The Repetitions
public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        char[] array1 = s1.toCharArray(), array2 = s2.toCharArray();
        int count1 = 0, i = 0, count2 = 0, j = 0;
        while(count1 < n1){
            if(array1[i] == array2[j]){
                j++;
                if(j == array2.length){
                    j = 0;
                    count2++;
                }
            }
            i++;
            if(i == array1.length){
                i = 0;
                count1++;
            }
        }
        return count2 / n2;
}

//467. Unique Substrings in Wraparound String
public int findSubstringInWraproundString(String p) {
    int[] count = new int[26];
    int maxLength = 0, res = 0;
    for(int i = 0; i < p.length(); i++){
        if(i > 0 && (p.charAt(i)-p.charAt(i-1) == 1 || p.charAt(i-1) - p.charAt(i) == 25)){
            maxLength++;
        }else{
            maxLength = 1;
        }
        int index = p.charAt(i) - 'a';
        count[index] = Math.max(count[index],maxLength);
    }
    for(int i = 0; i < 26; i++){
        res += count[i];
    }
    return res;
}

//471. Encode String with Shortest Length
public String encode(String s) {
    String[][] dp = new String[s.length()][s.length()];
    for(int l = 0; l < s.length(); l++){
        for(int i = 0; i < s.length()-l;i++){
            int j = i+l;
            String substr = s.substring(i,j+1);
            if(j-i < 4){
                dp[i][j] = substr;
            }else{
                dp[i][j] = substr;
                for(int k = i; k < j; k++){
                    if((dp[i][k]+dp[k+1][j]).length() < dp[i][j].length()){
                        dp[i][j] = dp[i][k]+dp[k+1][j];
                    }
                }
                
                for(int k = 0; k < substr.length(); k++){
                    String ss = substr.substring(0,k+1);
                    if(ss != null && substr.length() % ss.length() == 0 && substr.replaceAll(ss,"").length() == 0){
                        ss = substr.length() / ss.length() + "[" + dp[i][i+k] + "]";
                        dp[i][j] = ss.length() < dp[i][j].length() ? ss : dp[i][j];
                    }
                }
            }
        }
    }
    return dp[0][s.length()-1];
}

//472. Concatenated Words
public List<String> findAllConcatenatedWordsInADict(String[] words) {
    List<String> res = new ArrayList<String>();
    Set<String> dict = new HashSet<String>();
    Arrays.sort(words, new Comparator<String>(){
        public int compare(String s1, String s2){
            return s1.length() - s2.length();
        }
    });
    for(int i = 0; i < words.length; i++){
        if(helper(words[i],dict)){
            res.add(words[i]);
        }
        dict.add(words[i]);
    }
    return res;
}

private boolean helper(String word,Set<String> dict){
    if(dict.isEmpty()) return false;
    boolean[] dp = new boolean[word.length()+1];
    dp[0] = true;
    for(int i = 1; i <= word.length(); i++){
        for(int j = 0; j < i; j++){
            if(dp[j] && dict.contains(word.substring(j,i))){
                dp[i] = true;
                break;
            }
        }
    }
    return dp[word.length()];
}