M
1516583739
tags: DP

DP. 找对方程f[x], 积累到amount x最少用多少个coin: #coin是value, index是 [0~x].
子问题的关系是: 如果用了一个coin, 那么就应该是f[x - coinValue]那个位置的#coins + 1

注意initialization: 
处理边界, 一开始0index的时候, 用value0. 
中间利用Integer.MAX_VALUE来作比较, initialize dp[x]
注意, 一旦 Integer.MAX_VALUE + 1 就会变成负数. 这种情况会在coin=0的时候发生.

方法1: 用Integer.MAX_VALUE
方法2: 用-1, 稍微简洁一点, 每次比较dp[i]和 dp[i - coin] + 1, 然后save. 不必要做多次min比较.

```
/*
You are given coins of different denominations and a total amount of money amount. 
Write a function to compute the fewest number of coins that you need to make up that amount. 
If that amount of money cannot be made up by any combination of the coins, return -1.

Example 1:
coins = [1, 2, 5], amount = 11
return 3 (11 = 5 + 5 + 1)

Example 2:
coins = [2], amount = 3
return -1.

Note:
You may assume that you have an infinite number of each kind of coin.

*/

/*
Thoughts:
count 'fewest' -> DP. 
11 = 5 + 5 + 1, but not = 1 + 1 + 1...+1.
f[x] = fewests num of coins need to accumulate amount x.
f[x] = f[x - coin1] + 1, or f[x - coin2] + 1, f[x - coin3] + 1
Boundary:
x = 1,2,5 -> return 1.
x < 1, return -1.
if can't work at a particular ( 0 ~ x), let's say: we only have 2 and 5 but we want to get f[3], put Integer.MAX_VALUE there.

step:
initialize f[0]
loop over 0 ~ amount, calculate f[x]
*/
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        final int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) { // 1: 1, 2: 2, 5
            dp[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != -1 && coin != 0) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);    
                }
            }
            dp[i] = dp[i] == Integer.MAX_VALUE? -1 : dp[i];
        }
        
        return dp[amount];
    }
}


// simplify:
// if dp[i] == -1, which is just initialized, so dp[i] has to accept dp[i - coin] + 1.
// i>= coin is to limit such that coin won't over-jump index.
class Solution {
    public int coinChange(int[] coins, int amount) {
        if (coins == null || coins.length == 0) {
            return -1;
        }
        final int[] dp = new int[amount + 1];
        dp[0] = 0;
        for (int i = 1; i < dp.length; i++) { // 1: 1, 2: 2, 5
            dp[i] = -1;
            for (int coin : coins) {
                if (i >= coin && dp[i - coin] != -1) {
                    if (dp[i] == -1 || dp[i - coin] + 1 < dp[i]) {
                        dp[i] = dp[i - coin] + 1;
                    }
                }
            }
        }
        return dp[amount];
    }
}

```