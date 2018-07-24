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
    int[] A = new int[triangle.size()+1];
    for(int i=triangle.size()-1;i>=0;i--){
        for(int j=0;j<triangle.get(i).size();j++){
            A[j] = Math.min(A[j],A[j+1])+triangle.get(i).get(j);
        }
    }
    return A[0];
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
       int hold1 = Integer.MIN_VALUE, hold2 = Integer.MIN_VALUE;
       int release1 = 0, release2 = 0;
       for(int i:prices){                              // Assume we only have 0 money at first
           release2 = Math.max(release2, hold2+i);     // The maximum if we've just sold 2nd stock so far.
           hold2    = Math.max(hold2,    release1-i);  // The maximum if we've just buy  2nd stock so far.
           release1 = Math.max(release1, hold1+i);     // The maximum if we've just sold 1nd stock so far.
           hold1    = Math.max(hold1,    -i);          // The maximum if we've just buy  1st stock so far. 
       }
       return release2; ///Since release1 is initiated as 0, so release2 will always higher than release1.
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
            int left = (map.containsKey(n - 1)) ? map.get(n - 1) : 0;
            int right = (map.containsKey(n + 1)) ? map.get(n + 1) : 0;
            // sum: length of the sequence n is in
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
int maxProduct(int A[], int n) {
    // store the result that is the max we have found so far
    int r = A[0];

    // imax/imin stores the max/min product of
    // subarray that ends with the current number A[i]
    for (int i = 1, imax = r, imin = r; i < n; i++) {
        // multiplied by a negative makes big number smaller, small number bigger
        // so we redefine the extremums by swapping them
        if (A[i] < 0)
            swap(imax, imin);

        // max/min product for the current number is either the current number itself
        // or the max/min by the previous number times the current one
        imax = max(A[i], imax * A[i]);
        imin = min(A[i], imin * A[i]);

        // the newly computed max value is a candidate for our global result
        r = max(r, imax);
    }
    return r;
}

//153. Find Minimum in Rotated Sorted Array
int findMin(int[] num) {
       int start=0,end=num.length()-1;
       
       while (start<end) {
           if (num[start]<num[end])
               return num[start];
           
           int mid = (start+end)/2;
           
           if (num[mid]>=num[start]) {
               start = mid+1;
           } else {
               end = mid;
           }
       }
       
       return num[start];
   }
   
//154. Find Minimum in Rotated Sorted Array II
int findMin(vector<int> &num) {
       int lo = 0;
       int hi = num.size() - 1;
       int mid = 0;
       
       while(lo < hi) {
           mid = lo + (hi - lo) / 2;
           
           if (num[mid] > num[hi]) {
               lo = mid + 1;
           }
           else if (num[mid] < num[hi]) {
               hi = mid;
           }
           else { // when num[mid] and num[hi] are same
               hi--;
           }
       }
       return num[lo];
   }
   
//162. Find Peak Element
public int findPeakElement(int[] num) {    
    return helper(num,0,num.length-1);
}

public int helper(int[] num,int start,int end){
    if(start == end){
        return start;
    }else if(start+1 == end){
        if(num[start] > num[end]) return start;
        return end;
    }else{
        
        int m = (start+end)/2;
        
        if(num[m] > num[m-1] && num[m] > num[m+1]){

            return m;

        }else if(num[m-1] > num[m] && num[m] > num[m+1]){

            return helper(num,start,m-1);

        }else{

            return helper(num,m+1,end);

        }
        
    }
}

//163. Missing Ranges
public List<String> findMissingRanges(int[] a, int lo, int hi) {
  List<String> res = new ArrayList<String>();
  
  // the next number we need to find
  int next = lo;
  
  for (int i = 0; i < a.length; i++) {
    // not within the range yet
    if (a[i] < next) continue;
    
    // continue to find the next one
    if (a[i] == next) {
      next++;
      continue;
    }
    
    // get the missing range string format
    res.add(getRange(next, a[i] - 1));
    
    // now we need to find the next number
    next = a[i] + 1;
  }
  
  // do a final check
  if (next <= hi) res.add(getRange(next, hi));

  return res;
}

String getRange(int n1, int n2) {
  return (n1 == n2) ? String.valueOf(n1) : String.format("%d->%d", n1, n2);
}

//167. Two Sum II - Input array is sorted
public int[] twoSum(int[] num, int target) {
    int[] indice = new int[2];
    if (num == null || num.length < 2) return indice;
    int left = 0, right = num.length - 1;
    while (left < right) {
        int v = num[left] + num[right];
        if (v == target) {
            indice[0] = left + 1;
            indice[1] = right + 1;
            break;
        } else if (v > target) {
            right --;
        } else {
            left ++;
        }
    }
    return indice;
}

//169. Majority Element
public class Solution {
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
public int minSubArrayLen(int s, int[] a) {
  if (a == null || a.length == 0)
    return 0;
  
  int i = 0, j = 0, sum = 0, min = Integer.MAX_VALUE;
  
  while (j < a.length) {
    sum += a[j++];
    
    while (sum >= s) {
      min = Math.min(min, j - i);
      sum -= a[i++];
    }
  }
  
  return min == Integer.MAX_VALUE ? 0 : min;
}

//216. Combination Sum III
public List<List<Integer>> combinationSum3(int k, int n) {
   List<List<Integer>> ans = new ArrayList<>();
   combination(ans, new ArrayList<Integer>(), k, 1, n);
   return ans;
}

private void combination(List<List<Integer>> ans, List<Integer> comb, int k,  int start, int n) {
 if (comb.size() == k && n == 0) {
   List<Integer> li = new ArrayList<Integer>(comb);
   ans.add(li);
   return;
 }
 for (int i = start; i <= 9; i++) {
   comb.add(i);
   combination(ans, comb, k, i+1, n-i);
   comb.remove(comb.size() - 1);
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