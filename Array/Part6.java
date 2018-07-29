//667. Beautiful Arrangement II
public int[] constructArray(int n, int k) {
        int[] res = new int[n];
        for (int i = 0, l = 1, r = n; l <= r; i++)
            res[i] = k > 1 ? (k-- % 2 != 0 ? l++ : r--) : l++;
        return res;
}

//670. Maximum Swap
public int maximumSwap(int num) {
        char[] digits = Integer.toString(num).toCharArray();
        
        int[] buckets = new int[10];
        for (int i = 0; i < digits.length; i++) {
            buckets[digits[i] - '0'] = i;
        }
        
        for (int i = 0; i < digits.length; i++) {
            for (int k = 9; k > digits[i] - '0'; k--) {
                if (buckets[k] > i) {
                    char tmp = digits[i];
                    digits[i] = digits[buckets[k]];
                    digits[buckets[k]] = tmp;
                    return Integer.valueOf(new String(digits));
                }
            }
        }
        
        return num;
}

//674. Longest Continuous Increasing Subsequence
public int findLengthOfLCIS(int[] nums) {
        int res = 0, cnt = 0;
        for(int i = 0; i < nums.length; i++){
            if(i == 0 || nums[i-1] < nums[i]) res = Math.max(res, ++cnt);
            else cnt = 1;
        }
        return res;
}

//683. K Empty Slots
public int kEmptySlots(int[] flowers, int k) {
    int[] days = new int[flowers.length];
    for (int i = 0; i < days.length; i++) {
        days[flowers[i] - 1] = i + 1;
    }
    int left = 0;
    int right = k + 1;
    int res = Integer.MAX_VALUE;
    for (int i = 1; right < days.length; i++) {
        // current days[i] is valid, continue scanning
        if (days[i] > days[left] && days[i] > days[right]) {
            continue;
        }
       // reach boundary of sliding window, since previous number are all valid, record result  
        if (i == right) {
            res = Math.min(res, Math.max(days[left],days[right]));
        }
        // not valid, move the sliding window
        left = i;
        right = left + k + 1;
    }
    return res == Integer.MAX_VALUE ? -1 : res;
}

//689. Maximum Sum of 3 Non-Overlapping Subarrays
public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
       int n = nums.length, maxsum = 0;
       int[] sum = new int[n+1], posLeft = new int[n], posRight = new int[n], ans = new int[3];
       for (int i = 0; i < n; i++) sum[i+1] = sum[i]+nums[i];
       // DP for starting index of the left max sum interval
       for (int i = k, tot = sum[k]-sum[0]; i < n; i++) {
           if (sum[i+1]-sum[i+1-k] > tot) {
               posLeft[i] = i+1-k;
               tot = sum[i+1]-sum[i+1-k];
           }
           else
               posLeft[i] = posLeft[i-1];
       }
       // DP for starting index of the right max sum interval
      // caution: the condition is ">= tot" for right interval, and "> tot" for left interval
       posRight[n-k] = n-k;
       for (int i = n-k-1, tot = sum[n]-sum[n-k]; i >= 0; i--) {
           if (sum[i+k]-sum[i] >= tot) {
               posRight[i] = i;
               tot = sum[i+k]-sum[i];
           }
           else
               posRight[i] = posRight[i+1];
       }
       // test all possible middle interval
       for (int i = k; i <= n-2*k; i++) {
           int l = posLeft[i-1], r = posRight[i+k];
           int tot = (sum[i+k]-sum[i]) + (sum[l+k]-sum[l]) + (sum[r+k]-sum[r]);
           if (tot > maxsum) {
               maxsum = tot;
               ans[0] = l; ans[1] = i; ans[2] = r;
           }
       }
       return ans;
}

//695. Max Area of Island
public int maxAreaOfIsland(int[][] grid) {
    int max = 0;
    for(int i = 0; i < grid.length; i++){
        for(int j = 0; j < grid[0].length;j++){
            if(grid[i][j] == 1){
                max = Math.max(max,helper(grid,i,j));
            }
        }
    }
    return max;
}
private int helper(int[][] grid, int i, int j){
    if(i>=0 && i < grid.length && j>=0 && j < grid[0].length && grid[i][j] == 1){
        grid[i][j] = 0;
        return 1 + helper(grid,i-1,j) + helper(grid,i+1,j) + helper(grid,i,j-1)+helper(grid,i,j+1);
    }
    return 0;
}

//697. Degree of an Array
public int findShortestSubArray(int[] nums) {
       if (nums.length == 0 || nums == null) return 0;
       Map<Integer, int[]> map = new HashMap<>();
       for (int i = 0; i < nums.length; i++){
          if (!map.containsKey(nums[i])){
              map.put(nums[i], new int[]{1, i, i});  // the first element in array is degree, second is first index of this key, third is last index of this key
          } else {
              int[] temp = map.get(nums[i]);
              temp[0]++;
              temp[2] = i;
          }
       }
       int degree = Integer.MIN_VALUE, res = Integer.MAX_VALUE;
       for (int[] value : map.values()){
           if (value[0] > degree){
               degree = value[0];
               res = value[2] - value[1] + 1;
           } else if (value[0] == degree){
               res = Math.min( value[2] - value[1] + 1, res);
           } 
       }
       return res;
}

//713. Subarray Product Less Than K
public int numSubarrayProductLessThanK(int[] nums, int k) {
        if (k == 0) return 0;
        int cnt = 0;
        int pro = 1;
        for (int i = 0, j = 0; j < nums.length; j++) {
            pro *= nums[j];
            while (i <= j && pro >= k) {
                pro /= nums[i++];
            }
            cnt += j - i + 1;
        }
        return cnt;        
}

//714. Best Time to Buy and Sell Stock with Transaction Fee
public int maxProfit(int[] prices, int fee) {
       if(prices.length <= 1) return 0;
       int[] buy = new int[prices.length];
       int[] hold = new int[prices.length];
       int[] skip = new int[prices.length];
       int[] sell = new int[prices.length];
       // the moment we buy a stock, our balance should decrease
       buy[0] = 0 - prices[0]; 
       // assume if we have stock in the first day, we are still in deficit
       hold[0] = 0 - prices[0];
       for(int i = 1; i < prices.length; i++){
           // We can only buy on today if we sold stock
           // or skipped with empty portfolio yesterday
           buy[i] = Math.max(skip[i-1], sell[i-1]) - prices[i]; 
           // Can only hold if we bought or already holding stock yesterday
           hold[i] = Math.max(buy[i-1], hold[i-1]);
           // Can skip only if we skipped, or sold stock yesterday
           skip[i] = Math.max(skip[i-1], sell[i-1]);
           // Can sell only if we bought, or held stock yesterday
           sell[i] = Math.max(buy[i-1], hold[i-1]) + prices[i] - fee;
       }
       // Get the max of all the 4 actions on the last day.
       int max = Math.max(buy[prices.length - 1], hold[prices.length - 1]);
       max = Math.max(skip[prices.length - 1], max);
       max = Math.max(sell[prices.length - 1], max);
       return Math.max(max, 0);
}

//715. Range Module
class RangeModule {
    TreeMap<Integer, Integer> map;
    public RangeModule() {
        map = new TreeMap<>();
    }
    
    public void addRange(int left, int right) {
        if (right <= left) return;
        Integer start = map.floorKey(left);
        Integer end = map.floorKey(right);
        if (start == null && end == null) {
            map.put(left, right);
        } else if (start != null && map.get(start) >= left) {
            map.put(start, Math.max(map.get(end), Math.max(map.get(start), right)));
    	} else {
    	    map.put(left, Math.max(map.get(end), right));
    	}
        // clean up intermediate intervals
        Map<Integer, Integer> subMap = map.subMap(left, false, right, true);
        Set<Integer> set = new HashSet(subMap.keySet());
        map.keySet().removeAll(set);
    }
    
    public boolean queryRange(int left, int right) {
        Integer start = map.floorKey(left);
        if (start == null) return false;
        return map.get(start) >= right;
    }
    
    public void removeRange(int left, int right) {
        if (right <= left) return;
        Integer start = map.floorKey(left);
        Integer end = map.floorKey(right);
    	if (end != null && map.get(end) > right) {
            map.put(right, map.get(end));
    	}
    	if (start != null && map.get(start) > left) {
            map.put(start, left);
    	}
        // clean up intermediate intervals
        Map<Integer, Integer> subMap = map.subMap(left, true, right, false);
        Set<Integer> set = new HashSet(subMap.keySet());
        map.keySet().removeAll(set);
        
    }
}

//717. 1-bit and 2-bit Characters
public boolean isOneBitCharacter(int[] bits) {
    int ones = 0;
    for(int i = bits.length-2;i>=0&&bits[i]!=0;i--){
        ones++;
    }
    return ones%2>0 ? false : true;
}

//718. Maximum Length of Repeated Subarray
public int findLength(int[] A, int[] B) {
        if(A == null||B == null) return 0;
        int m = A.length;
        int n = B.length;
        int max = 0;
        //dp[i][j] is the length of longest common subarray ending with nums[i] and nums[j]
        int[][] dp = new int[m + 1][n + 1];
        for(int i = 0;i <= m;i++){
            for(int j = 0;j <= n;j++){
                if(i == 0 || j == 0){
                    dp[i][j] = 0;
                }
                else{
                    if(A[i - 1] == B[j - 1]){
                        dp[i][j] = 1 + dp[i - 1][j - 1];
                        max = Math.max(max,dp[i][j]);
                    }
                }
            }
        }
        return max;
}

//719. Find K-th Smallest Pair Distance
public int smallestDistancePair(int[] nums, int k) {
    Arrays.sort(nums);
    
    int n = nums.length;
    int l = 0;
    int r = nums[n - 1] - nums[0];
    
    for (int cnt = 0; l < r; cnt = 0) {
        int m = l + (r - l) / 2;
        
        for (int i = 0, j = 0; i < n; i++) {
            while (j < n && nums[j] <= nums[i] + m) j++;
            cnt += j - i - 1;
        }
        
        if (cnt < k) {
            l = m + 1;
        } else {
            r = m;
        }
    }
    
    return l;
}

//723. Candy Crush
public int[][] candyCrush(int[][] board) {
        Set<Coordinates> set = new HashSet<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int cur = board[i][j];
                if (cur == 0) continue;
                if ((i - 2 >= 0 && board[i - 1][j] == cur && board[i - 2][j] == cur) ||                                                 // check left 2 candies
                   (i + 2 <= board.length - 1 && board[i + 1][j] == cur && board[i + 2][j] == cur) ||                                   // check right 2 candies
                   (j - 2 >= 0 && board[i][j - 1] == cur && board[i][j - 2] == cur) ||                                                 // check 2 candies top
                   (j + 2 <= board[i].length - 1 && board[i][j + 1] == cur && board[i][j + 2] == cur) ||                               // check 2 candies below
                   (i - 1 >= 0 && i + 1 <= board.length - 1 && board[i - 1][j] == cur && board[i + 1][j] == cur) ||                    // check if it is a mid candy in row
                   (j - 1 >= 0 && j + 1 <= board[i].length - 1 && board[i][j - 1] == cur && board[i][j + 1] == cur)) {                // check if it is a mid candy in column
                    set.add(new Coordinates(i, j));
                }
            }
        }
        if (set.isEmpty()) return board;      //stable board
        for (Coordinates c : set) {
            int x = c.i;
            int y = c.j;
            board[x][y] = 0;      // change to "0"
        }
        drop(board);
        return candyCrush(board);
    }
    private void drop(int[][] board) {                                          // using 2-pointer to "drop"
        for (int j = 0; j < board[0].length; j++) {
            int bot = board.length - 1;
            int top = board.length - 1;
            while (top >= 0) {
                if (board[top][j] == 0) {
                    top--;
                }
                else {
                    board[bot--][j] = board[top--][j];
                }
            }
            while (bot >= 0) {
                board[bot--][j] = 0;
            }
        }
    }
    
//724. Find Pivot Index
public int pivotIndex(int[] nums) {
       int sum = 0, left = 0;
       for (int i = 0; i < nums.length; i++) sum += nums[i];
       
       for (int i = 0; i < nums.length; i++) {
           if (i != 0) left += nums[i - 1];
           if (sum - left - nums[i] == left) return i;
       }
       
       return -1;
   }
   
//729. My Calendar I   
class MyCalendar {

    TreeMap<Integer, Integer> calendar;

    public MyCalendar() {
        calendar = new TreeMap<>();
    }

    public boolean book(int start, int end) {
        Integer floorKey = calendar.floorKey(start);
        if (floorKey != null && calendar.get(floorKey) > start) return false;
        Integer ceilingKey = calendar.ceilingKey(start);
        if (ceilingKey != null && ceilingKey < end) return false;

        calendar.put(start, end);
        return true;
    }
}

//731. My Calendar II
class MyCalendarTwo {
    private List<int[]> books = new ArrayList<>();    
    public boolean book(int s, int e) {
        MyCalendar overlaps = new MyCalendar();
        for (int[] b : books)
            if (Math.max(b[0], s) < Math.min(b[1], e)) // overlap exist
                if (!overlaps.book(Math.max(b[0], s), Math.min(b[1], e))) return false; // overlaps overlapped
        books.add(new int[]{ s, e });
        return true;
    }

    private static class MyCalendar {
        List<int[]> books = new ArrayList<>();
        public boolean book(int start, int end) {
            for (int[] b : books)
                if (Math.max(b[0], start) < Math.min(b[1], end)) return false;
            books.add(new int[]{ start, end });
            return true;
        }
    }
}

//746. Min Cost Climbing Stairs
public int minCostClimbingStairs(int[] cost) {
        int n=(int)cost.length();
        int[] dp = new int[n];
        dp[0]=cost[0];
        dp[1]=cost[1];
        for (int i=2; i<n; ++i)
            dp[i]=cost[i]+min(dp[i-2],dp[i-1]);
        return min(dp[n-2],dp[n-1]);
}

//747. Largest Number At Least Twice of Others
public int dominantIndex(int[] nums) {
        if(nums == null || nums.length == 0){
            return -1;
        }
        
        if(nums.length == 1){
            return 0;
        }
        int max = Integer.MIN_VALUE + 1;
        int secondMax = Integer.MIN_VALUE;
        int index = 0;
        
        for(int i = 0; i < nums.length; i++){
            if(nums[i] > max){
                secondMax = max;
                max = nums[i];
                index = i;
            } else if(nums[i] != max && nums[i] > secondMax){
                secondMax = nums[i];
            }
        }
        if(secondMax * 2 <= max){
            return index;
        }
        return -1;
}

//755. Pour Water
public int[] pourWater(int[] heights, int V, int K) {
        if (heights == null || heights.length == 0 || V == 0) {
            return heights;
        }
        int index;
        while (V > 0) {
            index = K;
            for (int i = K - 1; i >= 0; i--) {
                if (heights[i] > heights[index]) {
                    break;
                } else if (heights[i] < heights[index]) {
                    index = i;
                }
            }
            if (index != K) {
                heights[index]++;
                V--;
                continue;
            }
            for (int i = K + 1; i < heights.length; i++) {
                if (heights[i] > heights[index]) {
                    break;
                } else if (heights[i] < heights[index]) {
                    index = i;
                }
            }
            heights[index]++;
            V--;
        }
        return heights;
}

