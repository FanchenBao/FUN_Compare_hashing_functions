# FUN_Compare_hashing_functions
Compare two open addressing strategies: linear vs. quadratic probing
## Introduction
Two common strategies for open addressing are linear probing and quadratic probing. Generally, quadratic is better than linear because, on average, it produces shorter chain length. This project attempts to directly compare the performance of linear and quadratic probing to examine how much better quadratic probing appears to be. Specifically, the project tries to answer the following questions:
1. Under the same loading factor (alpha, it is the ratio of number of elements to be put in a hash table and the size of the hash table. The larger alpha is, the more filled a hash table), how much better (i.e. shorter) the average chain length is in quadratic probing compared to linear probing, given some arbitrary coefficient pairs for quadratic probing?
2. What is the optimal coefficient pair under a given alpha in terms of average chain length (i.e. the shorter the average chain length, the better the coefficient pair)?
3. Given alpha = 1, how efficient is a quadratic coefficient pair to probe all slots in the hash table? In other words, we want to find out what are the most inefficient coefficient pairs that take forever to probe the next empty slot.
## Method
The object and hash table are implemented as a class called ```Student``` and a ```Student``` array wrapped in a class called ```StudentSet```. ```Student``` has fields such as first and last name, major, GPA, etc., which can all be randomly generated upon calling its constructor. Hash code of ```Student``` is generated from all its fields by summing the numeric representation of the value of each field.

Two hash functions are defined within ```StudentSet```, one for linear and the other quadratic probing. Additional functions for computing average chain length is included in ```StudentSet``` as well.

When a ```StudentSet``` is created, based on the values of alpha, size of hash table, hash function, etc. passed into the constructor, numerous ```Student``` will be randomly generated and inserted into ```StudentSet``` according to the hash code and hash function. The insertion step can be timed to evaluate the efficiency of a given quadratic coefficient pair. That is, one can set a time limit (default = 10 second) under which the next empty slot must be found. If a coefficient pair cannot find the next empty slot within the time limit, the pair is considered "bad". Upon completion of all insertions, the average chain length of the hash table can be computed. 

To answer question 1, a function called ```compareLinearQuadratic()``` is provided in the Main class. It computes the average chain length for both linear and quadratic probing (quadratic coefficient arbitrarily set as c1 = 0.2, c2 = 0.6; and c1 = 0.4, c2 = 0.4) for any alpha from 0.1 to 0.9, with 0.1 increment. For each alpha, the average chain length of both linear and quadratic probing is the average after 1000 repetitions.

To answer question 2, a function called ```optimalQuadraticCoefficient()``` is provided in the Main class. For each alpha (0.1 to 0.9 with 0.1 increment), 100 quadratic coefficient pairs (0.1 ≤ c1, c2 ≤ 1, with 0.1 increment) are tried to produce average chain length (averaged over 1000 repetitions for each quadratic coefficient pair), and the pair that produces the smallest average chain length is chosen as the optimal quadratic coefficient pair for the given alpha.

To answer question 3, a function called ```findBadQuadraticPairs()``` is provided in the Main class. Alpha is set to 1 to ensure that all slots must be probed by the coefficient pair. Each one of the 81 coefficient pairs (0.1 ≤ c1, c2 < 1, with 0.1 increment) is tried to probe all slots. If any probing step takes more than 10 seconds, the corresponding coefficient pair is deemed "bad" and recorded in its frequency count. This process is repeated for 100 times, and eventually a frequency chart can be produced to evaluate what coefficient pair has been "bad" the most often throughout the 100 repetitions.
## Result
### Outcome from question 1:
* Average chain length comparison. Repetition: 1000; Quadratic c1 = 0.2, c2 = 0.6

alpha | Linear | Quadratic
--- | --- | ---
0.1 | 1.167 | 1.110
0.2 | 1.382 | 1.251
0.3 | 1.654 | 1.429
0.4 | 2.022 | 1.662
0.5 | 2.538 | 1.993
0.6 | 3.315 | 2.492
0.7 | 4.631 | 3.312
0.8 | 7.199 | 4.930
0.9 | 14.763 | 9.798

* Average chain length comparison. Repetition: 1000; Quadratic c1 = 0.4, c2 = 0.4

alpha | Linear | Quadratic
--- | --- | ---
0.1 | 1.168 | 1.112
0.2 | 1.376 | 1.249
0.3 | 1.653 | 1.427
0.4 | 2.018 | 1.663
0.5 | 2.540 | 1.995
0.6 | 3.314 | 2.500
0.7 | 4.613 | 3.323
0.8 | 7.183 | 4.935
0.9 | 14.865 | 9.726

### Outcome from question 2:
Optimal c1, c2 pair in terms of average chain length. Repetition: 1000

alpha | c1 | c2 | min_ave_chain_len
--- | --- | --- | ---
0.1 | 0.1 | 0.8 | 1.110
0.2 | 0.1 | 0.6 | 1.247
0.3 | 0.4 | 0.4 | 1.425
0.4 | 0.4 | 0.4 | 1.661
0.5 | 0.4 | 0.4 | 1.996
0.6 | 0.2 | 0.6 | 2.491
0.7 | 0.4 | 0.4 | 3.312
0.8 | 0.4 | 0.4 | 4.935
0.9 | 0.4 | 0.4 | 9.749

### Outcome from question 3:
Frequencies of "bad" pairs:

(c1, c2) | Frequency
--- | ---
(0.8, 0.8) | 82
(0.2, 0.2) | 78
(0.4, 0.4) | 77
(0.5, 0.5) | 65
(0.6, 0.1) | 36
(0.8, 0.4) | 27
(0.4, 0.1) | 26
(0.8, 0.2) | 21
(0.2, 0.1) | 19
(0.8, 0.1) | 15
(0.4, 0.2) | 8

## Discussion
The benefits of quadratic probing in reducing average chain length is pretty obvious, and they are more pronounced when alpha gets big.

From outcome from question 2, it seems that quadratic coefficient pair (0.4, 0.4) seems to be a very good choice to produce short average chain length across multiple alpha values. However, interestingly, from the outcome from question 3, we also notice that (0.4, 0.4) ranks very high on being a "bad" pair. This suggests that (0.4, 0.4) is a good pair to make average chain length short, but probably at a cost that it probes the next empty slot very slowly. Therefore, when choosing a quadratic coefficient pair, one has to consider not only its performance on average chain length, but also weighing how efficient the pair might be, especially when alpha becomes big. In our case here, (0.4, 0.4) might not be a good choice due to its poor efficiency. And given that the difference in average chain length across alpha between (0.4, 0.4) and (0.2, 0.6) is not that big, it might be wise to prioritize probing efficiency rather than average chain length when heuristically choosing a coefficient pair for quadratic probing.
