//495. Teemo Attacking
public int findPosisonedDuration(int[] timeSeries, int duration) {
        if (timeSeries == null || timeSeries.length == 0 || duration == 0) return 0;
        
        int result = 0, start = timeSeries[0], end = timeSeries[0] + duration;
        for (int i = 1; i < timeSeries.length; i++) {
            if (timeSeries[i] > end) {
                result += end - start;
                start = timeSeries[i];
            }
            end = timeSeries[i] + duration;
        }
        result += end - start;
        
        return result;
}

//531. Lonely Pixel I
public int findLonelyPixel(char[][] picture) {
    int n = picture.length, m = picture[0].length;
    
    int[] rowCount = new int[n], colCount = new int[m];
    for (int i=0;i<n;i++) 
        for (int j=0;j<m;j++) 
            if (picture[i][j] == 'B') { rowCount[i]++; colCount[j]++; }

    int count = 0;
    for (int i=0;i<n;i++) 
        for (int j=0;j<m;j++) 
            if (picture[i][j] == 'B' && rowCount[i] == 1 && colCount[j] == 1) count++;
                
    return count;
}

//532. K-diff Pairs in an Array
public int findPairs(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   return 0;
        
        Map<Integer, Integer> map = new HashMap<>();
        int count = 0;
        for (int i : nums) {
            map.put(i, map.getOrDefault(i, 0) + 1);
        }
        
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (k == 0) {
                //count how many elements in the array that appear more than twice.
                if (entry.getValue() >= 2) {
                    count++;
                } 
            } else {
                if (map.containsKey(entry.getKey() + k)) {
                    count++;
                }
            }
        }
        
        return count;
}

//533. Lonely Pixel II
public int findBlackPixel(char[][] picture, int N) {
        int m = picture.length;
        if (m == 0) return 0;
        int n = picture[0].length;
        if (n == 0) return 0;
        
        Map<String, Integer> map = new HashMap<>();
        int[] colCount = new int[n];
        
        for (int i = 0; i < m; i++) {
            String key = scanRow(picture, i, N, colCount);
            if (key.length() != 0) {
                map.put(key, map.getOrDefault(key, 0) + 1);
            }
        }
        
        int result = 0;
        for (String key : map.keySet()) {
            //guarantee each row has the same amount of B and the amount is N
            if (map.get(key) == N) {
                for (int j = 0; j < n; j++) {
                    if (key.charAt(j) == 'B' && colCount[j] == N) {
                        result += N;
                    }
                }
            }
        }
        
        return result;
}
    
private String scanRow(char[][] picture, int row, int target, int[] colCount) {
        int n = picture[0].length;
        int rowCount = 0;
        StringBuilder sb = new StringBuilder();
        
        for (int j = 0; j < n; j++) {
            if (picture[row][j] == 'B') {
                rowCount++;
                colCount[j]++;
            }
            sb.append(picture[row][j]);
        }
        
        if (rowCount == target) return sb.toString();
        return "";
}
    
//548. Split Array with Equal Sum
public boolean splitArray(int[] nums) {
        if (nums.length < 7)
            return false;
        int[] sum = new int[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        for (int j = 3; j < nums.length - 3; j++) {
            HashSet<Integer> set = new HashSet<>();
            for (int i = 1; i < j - 1; i++) {
                if (sum[i - 1] == sum[j - 1] - sum[i])
                    set.add(sum[i - 1]);
            }
            for (int k = j + 2; k < nums.length - 1; k++) {
                if (sum[nums.length - 1] - sum[k] == sum[k - 1] - sum[j] && set.contains(sum[k - 1] - sum[j]))
                    return true;
            }
        }
        return false;
}

//560. Subarray Sum Equals K
public int subarraySum(int[] nums, int k) {
    Map<Integer,Integer> map = new HashMap<Integer,Integer>();
    //if current sum matches target, count 1
    map.put(0,1);
    int sum = 0, count = 0;
    for(int i = 0; i < nums.length;i++){
        sum += nums[i];
        count += map.getOrDefault(sum-k,0);
        map.put(sum,map.getOrDefault(sum,0)+1);
    }
    return count;
}

//561. Array Partition I
public int arrayPairSum(int[] nums) {
       Arrays.sort(nums);
       int result = 0;
       for (int i = 0; i < nums.length; i += 2) {
           result += nums[i];
       }
       return result;
   }
   
//562. Longest Line of Consecutive One in Matrix
public int longestLine(int[][] M) {
    int n = M.length, max = 0;
    if (n == 0) return max;
    int m = M[0].length;
    int[][][] dp = new int[n][m][4];
    for (int i=0;i<n;i++) 
        for (int j=0;j<m;j++) {
            if (M[i][j] == 0) continue;
            for (int k=0;k<4;k++) dp[i][j][k] = 1;
            if (j > 0) dp[i][j][0] += dp[i][j-1][0]; // horizontal line
            if (j > 0 && i > 0) dp[i][j][1] += dp[i-1][j-1][1]; // anti-diagonal line
            if (i > 0) dp[i][j][2] += dp[i-1][j][2]; // vertical line
            if (j < m-1 && i > 0) dp[i][j][3] += dp[i-1][j+1][3]; // diagonal line
            max = Math.max(max, Math.max(dp[i][j][0], dp[i][j][1]));
            max = Math.max(max, Math.max(dp[i][j][2], dp[i][j][3]));
        }
    return max;
}

//565. Array Nesting
public int arrayNesting(int[] a) {
        int maxsize = 0;
        for (int i = 0; i < a.length; i++) {
            int size = 0;
            for (int k = i; a[k] >= 0; size++) {
                int tmp = a[k];
                a[k] = -1; // mark a[k] as visited;
                k = tmp;
            }
            maxsize = Integer.max(maxsize, size);
        }

        return maxsize;
}

//566. Reshape the Matrix
public int[][] matrixReshape(int[][] nums, int r, int c) {
    if(nums.length == 0 || nums == null) return null;
    int m = nums.length, n = nums[0].length;
    if(m*n != r*c) return nums;
    int[][] res = new int[r][c];
    for(int i = 0; i < r*c; i++){
        res[i/c][i%c] = nums[i/n][i%n];
    }
    return res;
}

//581. Shortest Unsorted Continuous Subarray
public int findUnsortedSubarray(int[] A) {
    int n = A.length, beg = -1, end = -2, min = A[n-1], max = A[0];
    for (int i=1;i<n;i++) {
      max = Math.max(max, A[i]);
      min = Math.min(min, A[n-1-i]);
      if (A[i] < max) end = i;
      if (A[n-1-i] > min) beg = n-1-i; 
    }
    return end - beg + 1;
}

//605. Can Place Flowers
public boolean canPlaceFlowers(int[] flowerbed, int n) {
       int count = 0;
       for(int i = 0; i < flowerbed.length && count < n; i++) {
           if(flowerbed[i] == 0) {
      //get next and prev flower bed slot values. If i lies at the ends the next and prev are considered as 0. 
              int next = (i == flowerbed.length - 1) ? 0 : flowerbed[i + 1]; 
              int prev = (i == 0) ? 0 : flowerbed[i - 1];
              if(next == 0 && prev == 0) {
                  flowerbed[i] = 1;
                  count++;
              }
           }
       }
       
       return count == n;
   }
   
//611. Valid Triangle Number
public int triangleNumber(int[] nums) {
    Arrays.sort(nums);
    int count = 0;
    if(nums.length <3) return 0;
    for(int i = nums.length-1;i>=2;i--){
        int left = 0, right = i-1;
        while(left < right){
            if(nums[left]+nums[right]>nums[i]){
                count+= right-left;
                right--;
            }else{
                left++;
            }
        }
    }
    return count;
}

//621. Task Scheduler
public int leastInterval(char[] tasks, int n) {

        int[] c = new int[26];
        for(char t : tasks){
            c[t - 'A']++;
        }
        Arrays.sort(c);
        int i = 25;
        while(i >= 0 && c[i] == c[25]) i--;

        return Math.max(tasks.length, (c[25] - 1) * (n + 1) + 25 - i);
    }

//624. Maximum Distance in Arrays
public int maxDistance(List<List<Integer>> arrays) {
        int result = Integer.MIN_VALUE;
        int max = arrays.get(0).get(arrays.get(0).size() - 1);
        int min = arrays.get(0).get(0);
        
        for (int i = 1; i < arrays.size(); i++) {
            result = Math.max(result, Math.abs(arrays.get(i).get(0) - max));
            result = Math.max(result, Math.abs(arrays.get(i).get(arrays.get(i).size() - 1) - min));
            max = Math.max(max, arrays.get(i).get(arrays.get(i).size() - 1));
            min = Math.min(min, arrays.get(i).get(0));
        }
        
        return result;
}

//628. Maximum Product of Three Numbers
public int maximumProduct(int[] nums) {
        int max1 = Integer.MIN_VALUE, max2 = Integer.MIN_VALUE, max3 = Integer.MIN_VALUE, min1 = Integer.MAX_VALUE, min2 = Integer.MAX_VALUE;
        for (int n : nums) {
            if (n > max1) {
                max3 = max2;
                max2 = max1;
                max1 = n;
            } else if (n > max2) {
                max3 = max2;
                max2 = n;
            } else if (n > max3) {
                max3 = n;
            }

            if (n < min1) {
                min2 = min1;
                min1 = n;
            } else if (n < min2) {
                min2 = n;
            }
        }
        return Math.max(max1*max2*max3, max1*min1*min2);
    }

//643. Maximum Average Subarray I
public double findMaxAverage(int[] nums, int k) {
        long sum = 0;
        for (int i = 0; i < k; i++) sum += nums[i];
        long max = sum;
        
        for (int i = k; i < nums.length; i++) {
            sum += nums[i] - nums[i - k];
            max = Math.max(max, sum);
        }
        
        return max / 1.0 / k;
}

//644. Maximum Average Subarray II
public double findMaxAverage(int[] nums, int k) {
    double left = Integer.MIN_VALUE, right = Integer.MAX_VALUE;
    while(right - left > 0.000001){
        double mid = (left+right)/2;
        if(check(nums,k,mid)){
            left = mid;
        }else{
            right = mid;
        }
    }
    return left;
}

private boolean check(int[] nums, int k, double x){
    double sum = 0, tmp = 0;
    double[] a = new double[nums.length];
    for(int i = 0; i < a.length;i++){
        a[i] = nums[i]-x;
    }
    for(int i = 0; i < k; i++){
        sum += a[i];
    }
    if(sum>=0) return true;
    for(int i = k; i<nums.length;i++){
        sum+=a[i];
        tmp += a[i-k];
        //keep the length greater than k and remove smaller value
        if(tmp < 0){
            sum -= tmp;
            tmp = 0;
        }
        if(sum >=0) return true;
    }
    return false;
}
   
//661. Image Smoother
public int[][] imageSmoother(int[][] M) {
    if(M == null || M.length == 0) return null;
    int m = M.length;
    if(M[0] == null || M[0].length == 0) return null;
    int n = M[0].length;
    int[][] res = new int[m][n];
    for(int i = 0; i < m; i++){
        for(int j = 0; j < n; j++){
            int count = 0, sum = 0;
            for(int x : new int[]{-1,0,1}){
                for(int y : new int[]{-1,0,1}){
                    if(isValid(i+x,j+y,m,n)){
                        count++;
                        sum+=M[i+x][j+y];
                    }
                }
            }
            res[i][j] = sum / count;
        }
    }
    return res;
}
private boolean isValid(int i, int j, int m, int n){
    return i>=0 && i<m && j>=0 && j<n;
}
    
//665. Non-decreasing Array
public boolean checkPossibility(int[] nums) {
    int count = 0;
    for(int i = 1; i < nums.length && count<=1; i++){
        if(nums[i-1]>nums[i]){
            count++;
            if(i-2<0 || nums[i-2]<nums[i]){
                nums[i-1]=nums[i];
            }else{
                nums[i] = nums[i-1];
            }
        }
    }
    return count<=1;
} 