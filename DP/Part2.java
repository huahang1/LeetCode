//264. Ugly Number II
public int nthUglyNumber(int n) {
    if(n <= 0) return n;
    int[] u = new int[n];
    u[0] = 1;
    int p2 = 0, p3 = 0, p5 = 0;
    for(int i = 1; i < n; i++){
        u[i] = Math.min(u[p2]*2,Math.min(u[p3]*3,u[p5]*5));
        if(u[i] == u[p2]*2) p2++;
        if(u[i] == u[p3]*3) p3++;
        if(u[i] == u[p5]*5) p5++;
    }
    return u[n-1];
}

//276. Paint Fence
public int numWays(int n, int k) {
    if(n == 0) return 0;
    if(n == 1) return k;
    int sameColorCounts = k;
    int diffColorCounts = k * (k-1);
    for(int i = 2; i < n; i++){
        int tmp = diffColorCounts;
        diffColorCounts = (sameColorCounts+diffColorCounts)*(k-1);
        sameColorCounts = tmp;
    }
    return (sameColorCounts+diffColorCounts);
}