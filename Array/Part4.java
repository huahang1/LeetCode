//219. Contains Duplicate II
public boolean containsNearbyDuplicate(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();
        for(int i = 0; i < nums.length; i++){
            if(i > k) set.remove(nums[i-k-1]);
            if(!set.add(nums[i])) return true;
        }
        return false;
 }
 
 //228. Summary Ranges
 public List<String> summaryRanges(int[] nums) {
     List<String> res = new ArrayList<String>();
     for(int i = 0; i < nums.length;i++){
         int curr = nums[i];
         while(i+1<nums.length && nums[i] +1 == nums[i+1]){
             i++;
         }
         if(curr != nums[i]){
             res.add(curr+"->"+nums[i]);
         }else{
             res.add(curr+"");
         }
     }
     return res;
 }

//229. Majority Element II
public List<Integer> majorityElement(int[] nums) {
    List<Integer> res = new ArrayList<Integer>();
    if(nums.length==0 ||nums==null) return res;
    int n1 = nums[0], n2 = nums[0], count1 = 0, count2=0,len = nums.length;
    for(int i = 0; i < len;i++){
        int curr = nums[i];
        if(curr == n1){
            count1++;
        }else if(curr == n2){
            count2++;
        }else if(count1==0){
            n1 = curr;
            count1 = 1;
        } else if(count2==0){
            n2 = curr;
            count2=1;
        }else{
            count1--;
            count2--;
        }
    }
    int f1 = 0, f2=0;
    for(int i =0;i<len;i++){
        int curr = nums[i];
        if(curr == n1){
            f1++;
        }else if(curr == n2){
            f2++;
        }
    }
    if(f1>len/3) res.add(n1);
    if(f2>len/3) res.add(n2);
    return res;
}

//238. Product of Array Except Self
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] res = new int[n];
    res[0] = 1;
    for (int i = 1; i < n; i++) {
        res[i] = res[i - 1] * nums[i - 1];
    }
    int right = 1;
    for (int i = n - 1; i >= 0; i--) {
        res[i] *= right;
        right *= nums[i];
    }
    return res;
}

//243. Shortest Word Distance
public int shortestDistance(String[] words, String word1, String word2) {
    int p1 = -1, p2 = -1, min = Integer.MAX_VALUE;
    
    for (int i = 0; i < words.length; i++) {
        if (words[i].equals(word1)) 
            p1 = i;

        if (words[i].equals(word2)) 
            p2 = i;
            
        if (p1 != -1 && p2 != -1)
            min = Math.min(min, Math.abs(p1 - p2));
    }
    
    return min;
}

//245. Shortest Word Distance III
public int shortestWordDistance(String[] words, String word1, String word2) {
    int dist = Integer.MAX_VALUE, p1 = -1, p2 = -1;
    for(int i = 0; i < words.length;i++){
        if(words[i].equals(word1)){
            p1 = i;
        }
        if(words[i].equals(word2)){
            if(word1.equals(word2)){
                p1 = p2;
            }
            p2 = i;
        }
        if(p1!=-1 && p2!=-1){
            dist = Math.min(dist,Math.abs(p1-p2));
        }
    }
    return dist;
}

//259. 3Sum Smaller
public int threeSumSmaller(int[] nums, int target) {
        int count = 0;
        Arrays.sort(nums);
        int len = nums.length;
    
        for(int i=0; i<len-2; i++) {
            int left = i+1, right = len-1;
            while(left < right) {
                if(nums[i] + nums[left] + nums[right] < target) {
                    count += right-left;
                    left++;
                } else {
                    right--;
                }
            }
        }
        
        return count;
}

//268. Missing Number
public int missingNumber(int[] nums) {

    int xor = 0, i = 0;
	for (i = 0; i < nums.length; i++) {
		xor = xor ^ i ^ nums[i];
	}

	return xor ^ i;
}

//277. Find the Celebrity
public int findCelebrity(int n) {
       int candidate = 0;
       for(int i = 1; i < n; i++){
           if(knows(candidate, i))
               candidate = i;
       }
       for(int i = 0; i < n; i++){
           if(i != candidate && (knows(candidate, i) || !knows(i, candidate))) return -1;
       }
       return candidate;
}

//280. Wiggle Sort
public void wiggleSort(int[] nums) {
    for(int i = 0; i < nums.length;i++){
        if(i%2==1){
            if(nums[i-1]>nums[i]) swap(nums,i-1,i);
        }else{
            if(i!=0 && nums[i-1]<nums[i]) swap(nums,i-1,i);
        }
    }    
}

private void swap(int[] nums,int i, int j){
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
}

//283. Move Zeroes
public void moveZeroes(int[] nums) {
    if (nums == null || nums.length == 0) return;        

    int insertPos = 0;
    for (int num: nums) {
        if (num != 0) nums[insertPos++] = num;
    }        

    while (insertPos < nums.length) {
        nums[insertPos++] = 0;
    }
}

//287. Find the Duplicate Number
public int findDuplicate3(int[] nums)
{
	if (nums.length() > 1)
	{
		int slow = nums[0];
		int fast = nums[nums[0]];
		while (slow != fast)
		{
			slow = nums[slow];
			fast = nums[nums[fast]];
		}

		fast = 0;
		while (fast != slow)
		{
			fast = nums[fast];
			slow = nums[slow];
		}
		return slow;
	}
	return -1;
}

//289. Game of Life
public void gameOfLife(int[][] board) {
    if (board == null || board.length == 0) return;
    int m = board.length, n = board[0].length;

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            int lives = liveNeighbors(board, m, n, i, j);

            // In the beginning, every 2nd bit is 0;
            // So we only need to care about when will the 2nd bit become 1.
            if (board[i][j] == 1 && lives >= 2 && lives <= 3) {  
                board[i][j] = 3; // Make the 2nd bit 1: 01 ---> 11
            }
            if (board[i][j] == 0 && lives == 3) {
                board[i][j] = 2; // Make the 2nd bit 1: 00 ---> 10
            }
        }
    }

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            board[i][j] >>= 1;  // Get the 2nd state.
        }
    }
}

public int liveNeighbors(int[][] board, int m, int n, int i, int j) {
    int lives = 0;
    for (int x = Math.max(i - 1, 0); x <= Math.min(i + 1, m - 1); x++) {
        for (int y = Math.max(j - 1, 0); y <= Math.min(j + 1, n - 1); y++) {
            lives += board[x][y] & 1;
        }
    }
    lives -= board[i][j] & 1;
    return lives;
}

//370. Range Addition
public int[] getModifiedArray(int length, int[][] updates) {

    int[] res = new int[length];
     for(int[] update : updates) {
        int value = update[2];
        int start = update[0];
        int end = update[1];
        
        res[start] += value;
        //avoid adding value to the element out of range
        if(end < length - 1)
            res[end + 1] -= value;
        
    }
    
    int sum = 0;
    for(int i = 0; i < length; i++) {
        sum += res[i];
        res[i] = sum;
    }
    
    return res;
}

//380. Insert Delete GetRandom O(1)
public class RandomizedSet {
    ArrayList<Integer> nums;
    HashMap<Integer, Integer> locs;
    java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */
    public RandomizedSet() {
        nums = new ArrayList<Integer>();
        locs = new HashMap<Integer, Integer>();
    }
    
    /** Inserts a value to the set. Returns true if the set did not already contain the specified element. */
    public boolean insert(int val) {
        boolean contain = locs.containsKey(val);
        if ( contain ) return false;
        locs.put( val, nums.size());
        nums.add(val);
        return true;
    }
    
    /** Removes a value from the set. Returns true if the set contained the specified element. */
    public boolean remove(int val) {
        boolean contain = locs.containsKey(val);
        if ( ! contain ) return false;
        int loc = locs.get(val);
        if (loc < nums.size() - 1 ) { // not the last one than swap the last one with this val
            int lastone = nums.get(nums.size() - 1 );
            nums.set( loc , lastone );
            locs.put(lastone, loc);
        }
        locs.remove(val);
        nums.remove(nums.size() - 1);
        return true;
    }
    
    /** Get a random element from the set. */
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}

//381. Insert Delete GetRandom O(1) - Duplicates allowed
public class RandomizedCollection {
    ArrayList<Integer> nums;
	HashMap<Integer, Set<Integer>> locs;
	java.util.Random rand = new java.util.Random();
    /** Initialize your data structure here. */
    public RandomizedCollection() {
        nums = new ArrayList<Integer>();
	    locs = new HashMap<Integer, Set<Integer>>();
    }
    
    /** Inserts a value to the collection. Returns true if the collection did not already contain the specified element. */
    public boolean insert(int val) {
      boolean contain = locs.containsKey(val);
	    if ( !contain ) locs.put( val, new LinkedHashSet<Integer>() ); 
	    locs.get(val).add(nums.size());        
	    nums.add(val);
	    return ! contain ;
    }
    
    /** Removes a value from the collection. Returns true if the collection contained the specified element. */
    public boolean remove(int val) {
      boolean contain = locs.containsKey(val);
	    if ( !contain ) return false;
	    int loc = locs.get(val).iterator().next();
	    locs.get(val).remove(loc);
	    if (loc < nums.size() - 1 ) {
	       int lastone = nums.get( nums.size()-1 );
	       nums.set( loc , lastone );
	       locs.get(lastone).remove( nums.size()-1);
	       locs.get(lastone).add(loc);
	    }
	    nums.remove(nums.size() - 1);
	   
	    if (locs.get(val).isEmpty()) locs.remove(val);
	    return true;
    }
    
    /** Get a random element from the collection. */
    public int getRandom() {
        return nums.get( rand.nextInt(nums.size()) );
    }
}

//414. Third Maximum Number
public int thirdMax(int[] nums) {
        Integer max1 = null;
        Integer max2 = null;
        Integer max3 = null;
        for (Integer n : nums) {
            if (n.equals(max1) || n.equals(max2) || n.equals(max3)) continue;
            if (max1 == null || n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (max2 == null || n > max2) {
                max3 = max2;
                max2 = n;
            } else if (max3 == null || n > max3) {
                max3 = n;
            }
        }
        return max3 == null ? max1 : max3;
}

//442. Find All Duplicates in an Array
public List<Integer> findDuplicates(int[] nums) {
    List<Integer> res = new ArrayList<Integer>();
    for(int i = 0; i < nums.length;i++){
        int index = Math.abs(nums[i])-1;
        if(nums[index]<0) res.add(index+1);
        nums[index] = -nums[index];
    }
    return res;
}
   
//448. Find All Numbers Disappeared in an Array
public List<Integer> findDisappearedNumbers(int[] nums) {
    List<Integer> res = new ArrayList<Integer>();
    for(int i = 0; i < nums.length;i++){
        int index = Math.abs(nums[i])-1;
        if(nums[index] > 0){
            nums[index] = -nums[index];
        }
    }
    
    for(int i = 0; i < nums.length;i++){
        if(nums[i]>0) res.add(i+1);
    }
    return res;
}

//485. Max Consecutive Ones
public int findMaxConsecutiveOnes(int[] nums) {
        int maxHere = 0, max = 0;
        for (int n : nums)
            max = Math.max(max, maxHere = n == 0 ? 0 : maxHere + 1);
        return max; 
} 