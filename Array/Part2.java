//55. Jump Game
public boolean canJump(int[] nums) {
       int maxReach = 0, i =0;
       for(i = 0; i <= maxReach;i++){
           maxReach = Math.max(maxReach,i+nums[i]);
           if(maxReach >= nums.length-1) return true;
       }
       return false;
}

//56. Merge Intervals
public List<Interval> merge(List<Interval> intervals) {
    if (intervals.size() <= 1)
        return intervals;

    // Sort by ascending starting point using an anonymous Comparator
    intervals.sort((i1, i2) -> Integer.compare(i1.start, i2.start));

    List<Interval> result = new LinkedList<Interval>();
    int start = intervals.get(0).start;
    int end = intervals.get(0).end;

    for (Interval interval : intervals) {
        if (interval.start <= end) // Overlapping intervals, move the end if needed
            end = Math.max(end, interval.end);
        else {
            //add the old value first, then update new value
            result.add(new Interval(start, end));
            start = interval.start;
            end = interval.end;
        }
    }

    // Add the last interval
    result.add(new Interval(start, end));
    return result;
}

//57. Insert Interval
public List<Interval> insert(List<Interval> intervals, Interval newInterval) {
    List<Interval> result = new LinkedList<>();
    int i = 0;
    // add all the intervals ending before newInterval starts
    while (i < intervals.size() && intervals.get(i).end < newInterval.start)
        result.add(intervals.get(i++));
    // merge all overlapping intervals to one considering newInterval
    while (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
        newInterval = new Interval( // we could mutate newInterval here also
                Math.min(newInterval.start, intervals.get(i).start),
                Math.max(newInterval.end, intervals.get(i).end));
        i++;
    }
    result.add(newInterval); // add the union of intervals we got
    // add all the rest
    while (i < intervals.size()) result.add(intervals.get(i++));
    return result;
}

//59. Spiral Matrix II
public int[][] generateMatrix(int n) {
        // Declaration
        int[][] matrix = new int[n][n];

        // Edge Case
        if (n == 0) {
            return matrix;
        }

        // Normal Case
        int rowStart = 0;
        int rowEnd = n-1;
        int colStart = 0;
        int colEnd = n-1;
        int num = 1; //change

        while (rowStart <= rowEnd && colStart <= colEnd) {
            for (int i = colStart; i <= colEnd; i ++) {
                matrix[rowStart][i] = num ++; //change
            }
            rowStart ++;

            for (int i = rowStart; i <= rowEnd; i ++) {
                matrix[i][colEnd] = num ++; //change
            }
            colEnd --;

            for (int i = colEnd; i >= colStart; i --) {
                if (rowStart <= rowEnd)
                    matrix[rowEnd][i] = num ++; //change
            }
            rowEnd --;

            for (int i = rowEnd; i >= rowStart; i --) {
                if (colStart <= colEnd)
                    matrix[i][colStart] = num ++; //change
            }
            colStart ++;
        }

        return matrix;
    }

//62. Unique Paths
public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for(int i = 0; i < m; i++){
            dp[i][0] = 1;
        }
        for(int j = 0; j < n; j++){
            dp[0][j] = 1;
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = dp[i-1][j]+dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
    }

//63. Unique Paths II
public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        int m = obstacleGrid.length, n = obstacleGrid[0].length;
        int[][] dp = new int[m][n];
        //if anything blocks, this whole column will be not accessable, so mark all elements 0
        for(int i = 0; i < m; i++){
            if(obstacleGrid[i][0] == 1){
                while(i<m){
                    dp[i++][0] = 0;
                }
            }else{
                dp[i][0] = 1;
            }
        }
        for(int j = 0; j < n; j++){
           if(obstacleGrid[0][j] == 1){
                while(j<n){
                    dp[0][j++] = 0;
                }
            }else{
                dp[0][j] = 1;
            }
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = obstacleGrid[i][j] == 1 ? 0 : dp[i-1][j] + dp[i][j-1];
            }
        }
        return dp[m-1][n-1];
}

//64. Minimum Path Sum
public int minPathSum(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int[][] dp = new int[m][n];
        dp[0][0] = grid[0][0];
        for(int i = 1; i < m; i++){
            dp[i][0] = dp[i-1][0]+grid[i][0];
        }
        for(int j = 1; j < n; j++){
            dp[0][j] = dp[0][j-1]+grid[0][j];
        }
        for(int i = 1; i < m; i++){
            for(int j = 1; j < n; j++){
                dp[i][j] = Math.min(dp[i-1][j],dp[i][j-1]) + grid[i][j]; 
            }
        }
        return dp[m-1][n-1];
    }

//66. Plus One
public int[] plusOne(int[] digits) {

    int n = digits.length;
    for(int i=n-1; i>=0; i--) {
        if(digits[i] < 9) {
            digits[i]++;
            return digits;
        }

        digits[i] = 0;
    }

    int[] newNumber = new int [n+1];
    newNumber[0] = 1;

    return newNumber;
}

//73. Set Matrix Zeroes
public void setZeroes(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    int col0 = 1;
    for(int i = 0; i < m; i++){
        if(matrix[i][0] == 0) col0 = 0;
        for(int j = 1; j < n; j++){
            if(matrix[i][j] == 0){
                matrix[i][0] = 0; 
                matrix[0][j] = 0;
            }
        }
    }
    
    for(int i = m-1;i>=0;i--){
        for(int j = n-1; j>=1;j--){
            if(matrix[i][0] == 0 || matrix[0][j] == 0) matrix[i][j] = 0;
        }
        if(col0==0) matrix[i][0] = 0;
    }
}

//74. Search a 2D Matrix
public boolean searchMatrix(int[][] matrix, int target) {
        if(matrix.length == 0 || matrix == null) return false;
        int m = matrix.length;
        int n = matrix[0].length;
        int start = 0, end = m*n-1;
        while(start<=end){
            int mid = start + (end-start)/2;
            if(matrix[mid/n][mid%n] == target){
                return true;
            }else if(matrix[mid/n][mid%n] < target){
                start = mid+1;
            }else{
                end = mid-1;
            }
        }
        return false;
}

//75. Sort Colors
public void sortColors(int[] nums) {
    int zero = 0, second = nums.length-1;
    for(int i = 0; i <= second; i++){
        //move 2 to the most right and move 0 to the most left
        while(nums[i] == 2 && i < second) swap(nums,i,second--);
        while(nums[i] == 0 && i > zero) swap(nums,i,zero++);
    }
}

private void swap(int[] nums, int i , int j){
    int tmp = nums[i];
    nums[i] = nums[j];
    nums[j] = tmp;
}

//78. Subsets
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list , List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
}


//79. Word Search
public boolean exist(char[][] board, String word) {
    for(int i = 0; i < board.length; i++){
        for(int j = 0; j < board[i].length;j++){
            if(check(board,i,j,word,0)) return true;
        }
    }
    return false;
}

private boolean check(char[][] board, int i , int j, String word, int index){
    if(index == word.length()) return true;
    if(i < 0 || j < 0 || i == board.length || j == board[i].length || board[i][j] != word.charAt(index)) return false;
    //mark current position as visited
    board[i][j] = '*';
    boolean res = check(board,i-1,j,word,index+1) || check(board,i+1,j,word,index+1) 
                ||check(board,i,j-1,word,index+1) || check(board,i,j+1,word,index+1);
    //change the input back
    board[i][j] = word.charAt(index);
    return res;
}

//80. Remove Duplicates from Sorted Array II
public int removeDuplicates(int[] nums) {
    int i = 0;
    for (int n : nums)
        if (i < 2 || n > nums[i-2])
            nums[i++] = n;
    return i;
}

//81. Search in Rotated Sorted Array II
public boolean search(int[] nums, int target) {
        if(nums.length == 0 || nums == null) return false;
        int left = 0, right = nums.length-1;
        while(left <= right){
            int mid = left + (right-left)/2;
            if(nums[mid] == target) return true;
            if(nums[left] == nums[mid] && nums[right] == nums[mid]){
                left++; right--;
            }else{
                if(nums[left] <= nums[mid]){
                    if(nums[left] <= target && target <nums[mid]){
                        right = mid-1;
                    }else{
                        left = mid+1;
                    }
                }else{
                    if(nums[mid] < target && target <= nums[right]){
                        left = mid+1;
                    }else{
                        right = mid-1;
                    }
                }
            }
        }
        return false;
}

//84. Largest Rectangle in Histogram
public int largestRectangleArea(int[] heights) {
    if(heights.length == 0) return 0;
    if(heights.length == 1) return heights[0];
    int res = 0, i = 0, j = 0;
    Stack<Integer> stack = new Stack<Integer>();
    while(i <= heights.length){
        int height = i == heights.length ? 0 : heights[i];
        if(stack.isEmpty() || height >heights[stack.peek()]){
            stack.push(i++);
        }else{
            height = heights[stack.pop()];
            j = stack.isEmpty() ? -1 : stack.peek();
            res = Math.max(res,height*(i-j-1));
        }
    }
    return res;
}

//85. Maximal Rectangle
public int maximalRectangle(char[][] matrix) {
       if(matrix.length == 0 || matrix == null) return 0;
       int m = matrix.length; 
       int n = matrix[0].length;
       int[] left = new int[n];
       int[] right = new int[n];
       int[] height = new int[n];
       Arrays.fill(right,n);
       int res = 0;
       for(int i = 0; i < m; i++){
           int cur_left = 0, cur_right = n;
           for(int j = 0; j < n; j++){
               if(matrix[i][j] == '1'){
                   height[j]++;
               }else{
                   height[j] = 0;
               }
           }
           for(int j = 0; j < n;j++){
               if(matrix[i][j] == '1'){
                   left[j] = Math.max(left[j],cur_left);
               }else{
                   left[j] = 0; cur_left = j+1;
               }
           }
           for(int j = n-1; j >=0; j--){
               if(matrix[i][j] == '1'){
                   right[j] = Math.min(right[j],cur_right);
               }else{
                   right[j] = n; cur_right= j;
               }
           }
           for(int j = 0; j < n; j++){
               res = Math.max(res,height[j]*(right[j]-left[j]));
           }
       }
       return res;
}

//88. Merge Sorted Array
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int i = m-1, j = n-1, k = m+n-1;
    while(i>=0 && j>=0){
        if(nums1[i]>nums2[j]){
            nums1[k--] = nums1[i--];
        }else{
            nums1[k--] = nums2[j--];
        }
    }
    while(j>=0){
        nums1[k--]=nums2[j--];
    }
}

//90. Subsets II
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> list = new ArrayList<>();
    Arrays.sort(nums);
    backtrack(list, new ArrayList<>(), nums, 0);
    return list;
}

private void backtrack(List<List<Integer>> list, List<Integer> tempList, int [] nums, int start){
    list.add(new ArrayList<>(tempList));
    for(int i = start; i < nums.length; i++){
        if(i > start && nums[i] == nums[i-1]) continue; // skip duplicates
        tempList.add(nums[i]);
        backtrack(list, tempList, nums, i + 1);
        tempList.remove(tempList.size() - 1);
    }
}

//105. Construct Binary Tree from Preorder and Inorder Traversal
public TreeNode buildTree(int[] preorder, int[] inorder) {
    return helper(0, 0, inorder.length - 1, preorder, inorder);
}

public TreeNode helper(int preStart, int inStart, int inEnd, int[] preorder, int[] inorder) {
    if (preStart > preorder.length - 1 || inStart > inEnd) {
        return null;
    }
    TreeNode root = new TreeNode(preorder[preStart]);
    int inIndex = 0; // Index of current root in inorder
    for (int i = inStart; i <= inEnd; i++) {
        if (inorder[i] == root.val) {
            inIndex = i;
        }
    }
    root.left = helper(preStart + 1, inStart, inIndex - 1, preorder, inorder);
    root.right = helper(preStart + inIndex - inStart + 1, inIndex + 1, inEnd, preorder, inorder);
    return root;
}
