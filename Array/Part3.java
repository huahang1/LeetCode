//106. Construct Binary Tree from Inorder and Postorder Traversal
public TreeNode buildTree(int[] inorder, int[] postorder) {
    return helper(postorder.length-1,inorder.length-1,0,inorder,postorder);
}

private TreeNode helper(int poEnd,int inStart, int inEnd,int[] inorder,int[] postorder){
    if(poEnd < 0 || inStart < inEnd) return null;
    TreeNode root = new TreeNode(postorder[poEnd]);
    int inIndex = 0;
    //get index from right to left
    for(int i = inStart; i >= inEnd; i--){
        if(inorder[i] == root.val){
            inIndex = i;
            break;
        }
    }
    root.right = helper(poEnd-1,inStart,inIndex+1,inorder,postorder);
    root.left = helper(poEnd-1-(inStart-inIndex),inIndex-1,inEnd,inorder,postorder);
    return root;
}

//118. Pascal's Triangle
public List<List<Integer>> generate(int numRows)
{
	List<List<Integer>> allrows = new ArrayList<List<Integer>>();
	ArrayList<Integer> row = new ArrayList<Integer>();
	for(int i=0;i<numRows;i++)
	{
		row.add(0, 1);
		for(int j=1;j<row.size()-1;j++){
      row.set(j, row.get(j)+row.get(j+1));
    }
    //because arguments in method at java is passed by value instead of reference, so we need to create new space for each row
    //here the row value will get effected because each time we pass argument, it copies the old value and pass it.
		allrows.add(new ArrayList<Integer>(row));
	}
	return allrows;
	
}

//119. Pascal's Triangle II
public List<Integer> getRow(int rowIndex) {
	List<Integer> list = new ArrayList<Integer>();
	if (rowIndex < 0)
		return list;

	for (int i = 0; i < rowIndex + 1; i++) {
		list.add(0, 1);
		for (int j = 1; j < list.size() - 1; j++) {
			list.set(j, list.get(j) + list.get(j + 1));
		}
	}
	return list;
}

//120. Triangle
public int minimumTotal(List<List<Integer>> triangle) {
    int[] dp = new int[triangle.size()+1];
    for(int layer=triangle.size()-1;layer>=0;i--){
        for(int i=0;i<triangle.get(layer).size();i++){
            dp[i] = Math.min(dp[i],dp[i+1])+triangle.get(layer).get(i);
        }
    }
    return dp[0];
}

//121. Best Time to Buy and Sell Stock
public int maxProfit(int[] prices) {
        int maxCur = 0, maxSoFar = 0;
        for(int i = 1; i < prices.length; i++) {
            maxCur = Math.max(0, maxCur += prices[i] - prices[i-1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }
        return maxSoFar;
}
    
//122. Best Time to Buy and Sell Stock II
public int maxProfit(int[] prices) {
    int total = 0;
    for (int i=0; i< prices.length-1; i++) {
        if (prices[i+1]>prices[i]) total += prices[i+1]-prices[i];
    }
    
    return total;
}

//123. Best Time to Buy and Sell Stock III
public int maxProfit(int[] prices) {
       int buy1 = Integer.MIN_VALUE, buy2 = Integer.MIN_VALUE;
       int sell1 = 0, sell2 = 0;
       for(int i:prices){                              // Assume we only have 0 money at first
           sell2 = Math.max(sell2, buy2+i);     // The maximum if we've just sold 2nd stock so far.
           buy2 = Math.max(buy2, sell1-i);  // The maximum if we've just buy  2nd stock so far.
           sell1 = Math.max(sell1, buy1+i);     // The maximum if we've just sold 1nd stock so far.
           buy1 = Math.max(buy1, 0-i);          // The maximum if we've just buy  1st stock so far. 
       }
       return sell2; ///Since release1 is initiated as 0, so release2 will always higher than release1.
}

//126. Word Ladder II
public List<List<String>> findLadders(String start, String end, List<String> wordList) {
   HashSet<String> dict = new HashSet<String>(wordList);
   List<List<String>> res = new ArrayList<List<String>>();         
   HashMap<String, ArrayList<String>> nodeNeighbors = new HashMap<String, ArrayList<String>>();// Neighbors for every node
   HashMap<String, Integer> distance = new HashMap<String, Integer>();// Distance of every node from the start node
   ArrayList<String> solution = new ArrayList<String>();

   dict.add(start);          
   bfs(start, end, dict, nodeNeighbors, distance);                 
   dfs(start, end, dict, nodeNeighbors, distance, solution, res);   
   return res;
}

// BFS: Trace every node's distance from the start node (level by level).
private void bfs(String start, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance) {
  for (String str : dict)
      nodeNeighbors.put(str, new ArrayList<String>());

  Queue<String> queue = new LinkedList<String>();
  queue.offer(start);
  distance.put(start, 0);

  while (!queue.isEmpty()) {
      int count = queue.size();
      boolean foundEnd = false;
      for (int i = 0; i < count; i++) {
          String cur = queue.poll();
          int curDistance = distance.get(cur);                
          ArrayList<String> neighbors = getNeighbors(cur, dict);

          for (String neighbor : neighbors) {
              nodeNeighbors.get(cur).add(neighbor);
              if (!distance.containsKey(neighbor)) {// Check if visited
                  distance.put(neighbor, curDistance + 1);
                  if (end.equals(neighbor))// Found the shortest path
                      foundEnd = true;
                  else
                      queue.offer(neighbor);
                  }
              }
          }

          if (foundEnd)
              break;
      }
  }

// Find all next level nodes.    
private ArrayList<String> getNeighbors(String node, Set<String> dict) {
  ArrayList<String> res = new ArrayList<String>();
  char chs[] = node.toCharArray();

  for (char ch ='a'; ch <= 'z'; ch++) {
      for (int i = 0; i < chs.length; i++) {
          if (chs[i] == ch) continue;
          char old_ch = chs[i];
          chs[i] = ch;
          if (dict.contains(String.valueOf(chs))) {
              res.add(String.valueOf(chs));
          }
          chs[i] = old_ch;
      }

  }
  return res;
}

// DFS: output all paths with the shortest distance.
private void dfs(String cur, String end, Set<String> dict, HashMap<String, ArrayList<String>> nodeNeighbors, HashMap<String, Integer> distance, ArrayList<String> solution, List<List<String>> res) {
    solution.add(cur);
    if (end.equals(cur)) {
       res.add(new ArrayList<String>(solution));
    } else {
       for (String next : nodeNeighbors.get(cur)) {            
            if (distance.get(next) == distance.get(cur) + 1) {
                 dfs(next, end, dict, nodeNeighbors, distance, solution, res);
            }
        }
    }           
   solution.remove(solution.size() - 1);
}

//128. Longest Consecutive Sequence
public int longestConsecutive(int[] num) {
    int res = 0;
    HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
    for (int n : num) {
        if (!map.containsKey(n)) {
            int left = map.getOrDefault(n-1,0);
            int right = map.getOrDefault(n+1,0);
            int sum = left + right + 1;
            map.put(n, sum);
            
            // keep track of the max length 
            res = Math.max(res, sum);
            
            // extend the length to the boundary(s)
            // of the sequence
            // will do nothing if n has no neighbors
            map.put(n - left, sum);
            map.put(n + right, sum);
        }
        else {
            // duplicates
            continue;
        }
    }
    return res;
}

//152. Maximum Product Subarray
public int maxProduct(int[] nums) {
    int imin = 1, imax = 1, res = nums[0];
    for(int i : nums){
        if(i<=0){int tmp = imin; imin = imax;imax = tmp;}
        imax = Math.max(i,imax*i);
        imin = Math.min(i,imin*i);
        res = Math.max(res,imax);
    }
    return res;
}

//153. Find Minimum in Rotated Sorted Array
public int findMin(int[] nums) {
    int start = 0, end = nums.length-1;
    while(start < end){
        if(nums[start]<nums[end]) return nums[start];
        int mid = start + (end-start)/2;
        //compare nums[mid] to the biggest value, obviously here nums[start] already >= nums[end]
        if(nums[mid]>=nums[start]){
            start = mid+1;
        }else{
            end = mid;
        }
    }
    return nums[start];
}
   
//154. Find Minimum in Rotated Sorted Array II
public int findMin(int[] nums) {
    int start = 0, end = nums.length-1;
    while(start < end){
        if(nums[start]<nums[end]) return nums[start];
        int mid = start + (end-start)/2;
        if(nums[mid]>nums[start]){
            start = mid+1;
        }else if(nums[mid]<nums[start]){
            end = mid;
        }else{
            //skip duplicates
            start++;
        }
    }
    return nums[start];
}
   
//162. Find Peak Element
public int findPeakElement(int[] nums) {
    return helper(nums,0,nums.length-1);
}

private int helper(int[] nums,int start,int end){
    if(start == end){
        return start;
    }
    int mid1 = start+(end-start)/2;
    int mid2 = mid1+1;
    if(nums[mid1]>nums[mid2]){
        return helper(nums,start,mid1);
    }else{
        return helper(nums,mid2,end);
    }
}

//163. Missing Ranges
public List<String> findMissingRanges(int[] nums, int lower, int upper) {
    List<String> res = new ArrayList<String>();
    int next = lower;
    for(int i : nums){
        if(i < next) continue;
        if(i==next){
            next++;
            if(next == Integer.MIN_VALUE) return res;
            continue;
        }
        res.add(getRange(next,i-1));
        next = i+1;
        if(next == Integer.MIN_VALUE) return res;
    }
    if(next <= upper) res.add(getRange(next,upper));
    return res;
}

private String getRange(int n1, int n2) {
  return (n1 == n2) ? String.valueOf(n1) : String.format("%d->%d", n1, n2);
}

//167. Two Sum II - Input array is sorted
public int[] twoSum(int[] numbers, int target) {
    int[] res = new int[2];
    int left = 0, right = numbers.length-1;
    while(left < right){
        int sum = numbers[left]+numbers[right];
        if(sum == target){
            res[0] = left+1;
            res[1] = right+1;
            break;
        }else if(sum > target){
            right--;
        }else{
            left++;
        }
    }
    return res;
}

//169. Majority Element
public int majorityElement(int[] num) {
      int major=num[0], count = 1;
      for(int i=1; i<num.length;i++){
          if(count==0){
              count++;
              major=num[i];
          }else if(major==num[i]){
              count++;
          }else count--;      
      }
      return major;
}

//189. Rotate Array
public void rotate(int[] nums, int k) {
    k %= nums.length;
    reverse(nums, 0, nums.length - 1);
    reverse(nums, 0, k - 1);
    reverse(nums, k, nums.length - 1);
}

public void reverse(int[] nums, int start, int end) {
    while (start < end) {
        int temp = nums[start];
        nums[start] = nums[end];
        nums[end] = temp;
        start++;
        end--;
    }
}

//209. Minimum Size Subarray Sum
public int minSubArrayLen(int s, int[] nums) {
    int min = Integer.MAX_VALUE;
    int sum = 0, i = 0, j = 0;
    for(i = 0; i < nums.length; i++){
        sum += nums[i];
        while(sum >= s){
            min = Math.min(min,i-j+1);
            sum -= nums[j++];
        }
    }  
    return min == Integer.MAX_VALUE ? 0 : min;
}

//216. Combination Sum III
public List<List<Integer>> combinationSum3(int k, int n) {
    List<List<Integer>> res = new ArrayList<>();
    helper(res,new ArrayList<Integer>(),k,1,n);
    return res;
}
private void helper(List<List<Integer>> res, List<Integer> tmp, int size, int start, int target){
    if(tmp.size() == size && target == 0){
        res.add(new ArrayList<Integer>(tmp));
        return;
    }
    if(tmp.size() == size) return;
    for(int i = start; i <=9; i++){
        tmp.add(i);
        helper(res,tmp,size,i+1,target-i);
        tmp.remove(tmp.size()-1);
    }
}

//217. Contains Duplicate
public boolean containsDuplicate(int[] nums) {

      Arrays.sort(nums);
      for(int ind = 1; ind < nums.length; ind++) {
          if(nums[ind] == nums[ind - 1]) {
              return true;
          }
      }
      return false;
}